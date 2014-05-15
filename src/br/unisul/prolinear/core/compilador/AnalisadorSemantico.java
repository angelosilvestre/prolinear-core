package br.unisul.prolinear.core.compilador ;

import java.util.HashSet;

import br.unisul.prolinear.core.exceptions.UnboundedModelException;
import br.unisul.prolinear.core.simplex.equacao.Equacao.SinalEquacao;
import br.unisul.prolinear.core.simplex.equacao.CampoCalculado;
import br.unisul.prolinear.core.simplex.equacao.Restricao;
import br.unisul.prolinear.core.simplex.equacao.Variavel;
import br.unisul.prolinear.core.simplex.solver.Problema;
import br.unisul.prolinear.core.simplex.solver.Problema.TipoProblema;

public class AnalisadorSemantico implements Constants{
	private Problema p;
	private Contexto contexto;
	private double ultimoValor;
	private Restricao ultimaRestricao;
	private HashSet<String> identificadores; 
	private boolean inverterValor;
	private String ultimoIdentificador;
	private boolean variaveisInteiras;
	private CampoCalculado ultimoCampoCalculado;

	public void executeAction(int action, Token token)	throws SemanticError, UnboundedModelException
	{
		switch(action){

		//inicializa variáveis e define o tipo do problema
		case 1:
			p = new Problema();
			ultimoValor = 1;
			identificadores = new HashSet<String>();
			variaveisInteiras = false;
			if(token.getId() == t_MAX){
				p.setTipo(TipoProblema.MAX);
			} else {
				p.setTipo(TipoProblema.MIN);
			}
			break; 

			//seta o contexto como objetivo	
		case 2:
			contexto = Contexto.Objetivo;
			break;

			//armazena o valor para setar posteriormente o coeficiente da variável
		case 3:
			ultimoValor = Double.parseDouble(token.getLexeme());
			if (inverterValor) {
				ultimoValor *= -1;
				inverterValor = false;
			}
			break;
			//
		case 4:
			Variavel v = new Variavel(token.getLexeme());
			v.setInteira(variaveisInteiras);
			if(inverterValor){
				ultimoValor *= -1;
			}
			switch(contexto){
			//adiciona uma variável na função objetivo
			case Objetivo:
				p.getFuncao().adicionaVariavelOriginal(v);
				p.getFuncao().setaVariavelOriginal(v, ultimoValor);
				break;
			case Restricao:
				ultimaRestricao.adicionaVariavelOriginal(v);
				ultimaRestricao.setaVariavelOriginal(v, ultimoValor);
				break;
			}
			if(!p.getFuncao().getCoeficientesOriginais().containsKey(v)){
				p.getFuncao().adicionaVariavelOriginal(v);
				p.getFuncao().setaVariavelOriginal(v, 0.0);
			}
			if(!p.getVariaveis().contains(v)){
				p.getVariaveis().add(v);
			}

			ultimoValor = 1;
			break;
		case 5:
			ultimoIdentificador = token.getLexeme();
			break;
		case 6:
			contexto = Contexto.Restricao;
			break;
		case 7:
			ultimaRestricao = new Restricao();
			p.getRestricoes().add(ultimaRestricao);
			ultimaRestricao.setIdentificador((p.getRestricoes().size())+"");
			break;
			/* 
			 * verifica se o identificador já foi usado
			 * se já foi dispara uma exceção, senão seta o identificador da restrição
			 */
		case 8:
			if(identificadores.contains(ultimoIdentificador)){
				throw new SemanticError("Identificador de restrição duplicado");
			}else {
				ultimaRestricao.setIdentificador(ultimoIdentificador);
				identificadores.add(ultimoIdentificador);
			}
			break;

			/*
			 * seta o sinal da restrição
			 */
		case 9:
			switch(token.getId()){
			case 22:
				ultimaRestricao.setSinal(SinalEquacao.MenorIgual);
				break;
			case 23:
				ultimaRestricao.setSinal(SinalEquacao.MaiorIgual);
				break;
			case 26:
				ultimaRestricao.setSinal(SinalEquacao.Igual);
				break;
			}
			break;
			/*
			 * seta o lado direito da equação
			 */
		case 10:
			ultimaRestricao.setValorEquacaoOriginal(ultimoValor);
			ultimoValor = 1;
			break;		
		case 11:
			inverterValor = false;
			break;
		case 12:
			inverterValor = true;
			break;
		case 13:
			checkExists(token);
			if(!ultimaRestricao.getDescricao().isEmpty())
				throw new SemanticError("Restrição " + token.getLexeme()+ " já possui descrição");
			break;
		case 14:
			ultimaRestricao.setDescricao(token.getLexeme().replace('\'', ' ').trim());
			break;
		case 15:
			checkExists(token);			
			if(ultimaRestricao.getConversao() != null)
				throw new SemanticError("Restrição " + token.getLexeme()+ " já possui fator de conversão",token.getPosition());
			break;
		case 16:
			ultimaRestricao.setConversao(Double.parseDouble(token.getLexeme()));
			p.setConversao(true);
			break;
		case 17:
			ultimoCampoCalculado = new CampoCalculado(token.getLexeme());
			break;
		case 18:
			ultimoCampoCalculado.setExpressao(token.getLexeme().replaceAll("'", ""));
			p.getCamposCalculados().add(ultimoCampoCalculado);
			break;
		case 19:
			checkExists(token);
			if(!ultimaRestricao.getIdentificadorFolga().isEmpty()){
				throw new SemanticError("Restrição "+ token.getLexeme() + " já possui identificador de folga definido");
			}
			break;
		case 20:
			ultimaRestricao.setIdentificadorFolga(token.getLexeme().replace('\'', ' ').trim());
			break;
		case 21:			 
			p.setSomenteSolucao(new Boolean(token.getLexeme()));
			break;
		case 22:
			p.setExibirEstatisticas(new Boolean(token.getLexeme()));
			break;
		case 23:
			p.setCasasDecimais((int)Double.parseDouble(token.getLexeme()));
			break;
		case 24:
			p.setReescrever(new Boolean(token.getLexeme()));
			break;
		case 25:
			variaveisInteiras = true;
			break;
		case  26:
			p.getVariavel(token.getLexeme()).setInteira(true);
			break;
		case 99:
			boolean possuiMaiorIgual = false;
			boolean possuiMenorIgual = false;
			boolean possuiIgual = false;
			for(Restricao r:p.getRestricoes()){
				switch (r.getSinal()) {
				case MaiorIgual:
					possuiMaiorIgual = true;
					break;
				case Igual:
					possuiIgual = true;
					break;
				case MenorIgual:
					possuiMenorIgual = true;
					break;
				}				
			}
			if(p.getTipo() == TipoProblema.MAX){
				if(!(possuiIgual || possuiMenorIgual))
					throw new UnboundedModelException(
							"Você modelou um problema de maximização que não possui nenhuma restrição que limite seu crescimento!\n"+
							"Revise seu modelo.");
				
			}else{
				if(!(possuiIgual || possuiMaiorIgual))
					throw new UnboundedModelException(
							"Você modelou um problema de minimização que não possui nenhuma restrição que limite sua redução!\n"+
							"Revise seu modelo.");
			}
			break;
		}


	}	



	static enum Contexto{
		Objetivo,Restricao
	}


	private void checkExists(Token token) throws SemanticError{
		ultimaRestricao = p.getRestricao(token.getLexeme());
		if(ultimaRestricao == null)
			throw new SemanticError("Restrição "+ token.getLexeme() + " não existe");
	}

	public Problema getP() {
		return p;
	}



	public void setP(Problema p) {
		this.p = p;
	}
}
