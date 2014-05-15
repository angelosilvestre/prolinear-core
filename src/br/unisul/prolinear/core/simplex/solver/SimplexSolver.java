package br.unisul.prolinear.core.simplex.solver;

import java.io.StringReader;
import java.util.ArrayList;

import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;

import br.unisul.prolinear.core.compilador.AnalisadorLexico;
import br.unisul.prolinear.core.compilador.AnalisadorSemantico;
import br.unisul.prolinear.core.compilador.AnalisadorSintatico;
import br.unisul.prolinear.core.compilador.LexicalError;
import br.unisul.prolinear.core.compilador.SemanticError;
import br.unisul.prolinear.core.compilador.SyntaticError;
import br.unisul.prolinear.core.exceptions.InfeasibleModelException;
import br.unisul.prolinear.core.exceptions.UnboundedModelException;
import br.unisul.prolinear.core.simplex.equacao.CampoCalculado;
import br.unisul.prolinear.core.simplex.equacao.Equacao;
import br.unisul.prolinear.core.simplex.equacao.Equacao.SinalEquacao;
import br.unisul.prolinear.core.simplex.equacao.FuncaoObjetivo;
import br.unisul.prolinear.core.simplex.equacao.Restricao;
import br.unisul.prolinear.core.simplex.equacao.Variavel;
import br.unisul.prolinear.core.simplex.equacao.Variavel.TipoVariavel;
import br.unisul.prolinear.core.simplex.solver.Problema.TipoProblema;

@SuppressWarnings("unchecked")
public class SimplexSolver {

	Problema problema;	
	FuncaoObjetivo objetivoPrimeiraFase;
	boolean primeiraFase;
	private boolean dualSimplex;
	private int iteracoes;
	private Solucao melhorSolucao;

	public Solucao solve(String modelo) throws  UnboundedModelException, InfeasibleModelException, LexicalError, SyntaticError, SemanticError{		
		AnalisadorLexico lexico = new AnalisadorLexico();
		AnalisadorSintatico sintatico = new AnalisadorSintatico();
		AnalisadorSemantico semantico = new AnalisadorSemantico();
		lexico.setInput(new StringReader(modelo.toUpperCase()));
		sintatico.parse(lexico, semantico);
		return solve(semantico.getP());
	}


	public Solucao solve(Problema p) throws  InfeasibleModelException, SyntaticError, UnboundedModelException{
		primeiraFase = false;
		dualSimplex = false;
		iteracoes = 0;
		problema = p;

		if(p.getTipo() == TipoProblema.MAX){
			p.getFuncao().multiplicar(-1);
		}
		adicionaVariaveisFolga();
		if(dualSimplex){
			fase1();
		}
		while(!solucaoOtimaEncontrada()){
			//if(iteracoes > 1000) throw new UnboundedModelException("Não foi possível resolver este problema, entre em contato com o desenvolvimento e informe o problema ocorrido");
			iteracoes++;
			recalculaValores();
		}
		if(p.getTipo() == TipoProblema.MIN){
			p.getFuncao().multiplicar(-1);
		}
		Solucao s = new Solucao(p);


		for(Restricao r : p.getRestricoes()){
			s.getResultSet().put(r.getVariavelBasica(), r.getValorEquacaoTableau());
		}

		s.setValorOtimo(problema.getFuncao().getValorEquacaoTableau());
		
		s = branchAndBound(s);
		s.setIteracoes(iteracoes);
		for(Restricao r : p.getRestricoes()){
			Variavel var = r.getVariavelFolga();
			Double folga = s.getResultSet().get(var);
			if((folga != null) && (SimplexUtils.round(folga, p.getCasasDecimais()) > 0)){
				p.setFolga(true);
				r.setFolga(folga);
				Double conversao = r.getConversao();
				if(conversao != null){
					folga = SimplexUtils.round(conversao * (int)(folga / conversao),p.getCasasDecimais());
				}
				r.setValorOtimizado(r.getValorEquacaoOriginal() - (r.getSinal() == Equacao.SinalEquacao.MenorIgual ? folga : -folga));
			}
		}
		
		
		for(CampoCalculado c : p.getCamposCalculados()){

			JEP j = new JEP();
			String expressao = " " +c.getExpressao() + " ";
			expressao = expressao 
					.replace("+", " + ")
					.replace("-", " - ")
					.replace("*", " * ")
					.replace("/", " / ")
					.replace("^", " ^ ")
					.replace("(", " ( ")
					.replace(")", " ) ");
			try{
				for(Variavel v: p.getVariaveis()){
					if(expressao.indexOf(" "+v.getNome()+" ") > -1){					
						expressao = expressao.replace(" " +v.getNome()+ " ", s.getValorVariavel(v)+"");
					}
				}

				for(CampoCalculado campo: p.getCamposCalculados()){
					if(expressao.indexOf(campo.getNome()) > -1){
						expressao = expressao.replace(" " +campo.getNome()+ " ",s.getValorVariavel(campo)+"");
					}
				}


				Node n = j.parse(expressao);
				Double valor = (Double)j.evaluate(n);
				s.getResultSet().put(c, valor);
			}catch (Exception e) {
				throw new SyntaticError("Erro resolvendo campos calculados");
			}

		}
		return s;
	}


	private boolean solucaoOtimaEncontrada(){
		FuncaoObjetivo f;
		if(!primeiraFase)
			f = problema.getFuncao();
		else
			f = objetivoPrimeiraFase;
		for(Variavel v:f.getVariaveisTableau()){
			double coef = getCoeficienteObjetivo(f,v);
			if(coef < 0)return false;
		}
		return true;
	}


	private void adicionaVariavelFolgaOuArificial(Restricao r,boolean folga, int numero){
		Variavel v = new Variavel((folga ? "$F":"$A")+numero,folga ? TipoVariavel.FOLGA : TipoVariavel.ARTIFICIAL);
		double valor = 1.0;
		double valorObjetivo = 0.0;
		if(folga){
			if(r.getSinal() != SinalEquacao.MenorIgual) {
				valor *= -1;
			}
			problema.getVariaveisFolga().add(v);
		} else {
			dualSimplex = true;
			problema.getVariaveisArtificiais().add(v);
			valorObjetivo = 0;
		}
		r.setVariavelBasica(v);
		r.adicionaVariavel(v);
		if(folga){
			r.setVariavelFolga(v);
		}
		r.setaVariavel(v, valor);		
		problema.getFuncao().adicionaVariavel(v);
		problema.getFuncao().setaVariavel(v, valorObjetivo);
	}

	private void fase1() throws  InfeasibleModelException{
		primeiraFase = true;
		Restricao aux = null;
		for(Variavel v: problema.getVariaveisArtificiais()){
			if(aux == null){
				aux = getRestricao(v).clone();
			}else{
				aux.somar(getRestricao(v));
			}
			aux.setaVariavel(v,0.0);
		}
		aux.multiplicar(-1);
		objetivoPrimeiraFase = new FuncaoObjetivo();
		for(Variavel v: aux.getVariaveisTableau()){
			//	for(Variavel v:aux.getVariaveisOriginais()){
			objetivoPrimeiraFase.adicionaVariavel(v);
			objetivoPrimeiraFase.setaVariavel(v, aux.getCoeficientesTableau().get(v));
		}
		objetivoPrimeiraFase.setValorEquacaoTableau(aux.getValorEquacaoTableau());
		problema.setObjetivoPrimeiraFase(objetivoPrimeiraFase);
		while(!solucaoOtimaEncontrada()){
			iteracoes++;
			recalculaValores();
		}
		for(Variavel v: problema.getVariaveisArtificiais()){
			System.out.println(objetivoPrimeiraFase.getCoeficientesTableau().get(v));
			for(Restricao r : problema.getRestricoes()){
				if(r.getVariavelBasica().getTipo() == TipoVariavel.ARTIFICIAL)
					throw new InfeasibleModelException("O problema não possui solução viável.");
				if(r.getVariaveisTableau().contains(v)){
					r.getVariaveisTableau().remove(v);
					r.getCoeficientesTableau().remove(v);
				}
			}
			if(problema.getFuncao().getVariaveisTableau().contains(v)){
				problema.getFuncao().getVariaveisTableau().remove(v);
				problema.getFuncao().getCoeficientesTableau().remove(v);
			}
			v = null;
		}
		problema.setVariaveisArtificiais(new ArrayList<Variavel>());
		problema.setObjetivoPrimeiraFase(null);
		System.out.println(problema);
		objetivoPrimeiraFase = null;
		primeiraFase = false;
		System.out.println("Iniciando Fase 2");
	}




	private void adicionaVariaveisFolga(){
		problema.setVariaveis((ArrayList<Variavel>) problema.getFuncao().getVariaveisTableau().clone());
		int variaveisFolga =0;
		int variaveisArtificiais = 0;
		for(Restricao r: problema.getRestricoes()){
			/* se for uma inequação é necessário adicionar uma variável de folga 
			 	e transformá-la numa equação */
			if(r.getSinal() != SinalEquacao.Igual){
				variaveisFolga++;
				adicionaVariavelFolgaOuArificial(r, true, variaveisFolga);
			}

			/*
			 * Quando o sinal for diferente de menor igual 
			 * é necessário incluir variáveis artificiais
			 */
			if(r.getSinal() != SinalEquacao.MenorIgual){
				variaveisArtificiais++;
				adicionaVariavelFolgaOuArificial(r, false, variaveisArtificiais);
			}	

		}


		/*
		 * adiciona os coeficientes de todas as variáveis do problema
		 * para cada restrição, para a construção do tableau
		 */
		for(Variavel var:problema.getFuncao().getVariaveisTableau()){
			for(Restricao r:problema.getRestricoes()){
				if(!r.getVariaveisTableau().contains(var)){
					r.adicionaVariavel(var);
					r.setaVariavel(var, 0.0);
				}
			}
		}

	}


	private Restricao getRestricao(Variavel basica){
		for(Restricao r: problema.getRestricoes()){
			if(r.getVariavelBasica().equals(basica)) return r;
		}
		return null;
	}

	public Double getNumeroPivo(Variavel t, Restricao r){
		return r.getCoeficientesTableau().get(t);
	}


	/***
	 * Recalcula os valores do tableau
	 */
	private void recalculaValores(){
		FuncaoObjetivo f ;
		if(!primeiraFase){
			f = problema.getFuncao();
		}else{
			f = objetivoPrimeiraFase;
		}
		Variavel colunaPivo = getColunaPivo();
		System.out.println("\nColuna Pivô : " + colunaPivo+"\n");
		Restricao linhaPivo = getLinhaPivo(colunaPivo);		
		System.out.println("\nLinha Pivô : " + linhaPivo+"\n");
		double numeroPivo = getNumeroPivo(colunaPivo, linhaPivo);
		linhaPivo.dividir(numeroPivo);
		linhaPivo.setVariavelBasica(colunaPivo);

		Equacao linhaPivoModificada = linhaPivo.clone();
		linhaPivoModificada.multiplicar(getCoeficienteObjetivo(f,colunaPivo));
		f.subtrair(linhaPivoModificada);

		if(f != problema.getFuncao()){
			linhaPivoModificada = linhaPivo.clone();
			linhaPivoModificada.multiplicar(getCoeficienteObjetivo(problema.getFuncao(), colunaPivo));
			problema.getFuncao().subtrair(linhaPivoModificada);

		}


		/*para cada restrição calcula a linha pivo modificada
		e recalcula os valores does coeficientes*/ 
		for(Restricao r: problema.getRestricoes()){
			if(r != linhaPivo){
				linhaPivoModificada = linhaPivo.clone();
				linhaPivoModificada.multiplicar(getCoeficienteRestricao(r, colunaPivo));
				r.subtrair(linhaPivoModificada);
			}
		}

	}

	/***
	 * Busca o coeficiente de uma variavel na função objetivo
	 * @param t Termo a ser buscado o coeficiente
	 * @return o coeficiente do termo t
	 */
	private double getCoeficienteObjetivo(FuncaoObjetivo f, Variavel v){
		return f.getCoeficientesTableau().get(v);
	}

	/**
	 * Busca o coeficiente de uma variável em uma restrição
	 * @param r Restrição que contém a variável
	 * @param v Variável a ser buscado o termo
	 * @return Valor do coeficiente
	 */
	private double getCoeficienteRestricao(Restricao r, Variavel v){
		return r.getCoeficientesTableau().get(v);		
	}

	/***
	 * 
	 * @return Coluna que será base para os cálculos do tableau
	 */
	private Variavel getColunaPivo(){
		FuncaoObjetivo f;
		if(primeiraFase){
			f = objetivoPrimeiraFase; 
		}else {
			f = problema.getFuncao();
		}
		Variavel pivo = null;//problema.getVariaveis().get(0);
		boolean positivos = true;
		double min = Double.MAX_VALUE;
		for(Variavel v: f.getVariaveisTableau()){
			double valor = getCoeficienteObjetivo(f, v);
			if(valor < min){
				pivo = v;
				min = valor;
			} 
			if(valor < 0) positivos = false;	

		}
		return positivos ? null : pivo;
	}


	private Restricao getLinhaPivo(Variavel pivo){
		Restricao linhaPivo = null;// problema.getRestricoes().get(0);
		double minimo = Double.MAX_VALUE;
		double aux = 0;
		for(Restricao r: problema.getRestricoes()){
			double coeficiente = getCoeficienteRestricao(r, pivo);
			if(coeficiente > 0){
				aux = r.getValorEquacaoTableau() / coeficiente;
				if(aux <= minimo){
					minimo = aux;
					linhaPivo = r;
				}
			}
		}
		return linhaPivo;
	}


	/***
	 * 
	 * @param p Problema a ser resolvido
	 * @return Um objeto da classe solução, que representa a solução ótima do problema
	 */


	private Solucao branchAndBound(Solucao s){
		Variavel v;
		while((v = variavelSeparacao(s)) != null){
			iteracoes++;
			double valor = s.getValorVariavel(v);
			int valorSuperior = (int)(valor + 1);
			int valorInferior = (int) valor;
			Solucao solucaoSuperior = null;
			Solucao solucaoInferior = null;

			Problema problemaSuperior = s.getProblema().getCleanCopy();
			problemaSuperior.removeUltimaRestricaoArtificial(v);
			Restricao r = new Restricao();
			r.adicionaVariavelOriginal(v);
			r.setaVariavelOriginal(v, 1.0);
			r.setSinal(SinalEquacao.MaiorIgual);
			r.setValorEquacaoOriginal((double) valorSuperior);
			r.setArtificial(true);
			problemaSuperior.getRestricoes().add(r);
			Solucao candidata = null;
			SimplexSolver solverBranchAndBound = new SimplexSolver();			
			try {
				solucaoSuperior = solverBranchAndBound.solve(problemaSuperior);
			} catch (Exception e) {
			}
			//solucaoSuperior = solverBranchAndBound.branchAndBound(s);
			Problema problemaInferior = s.getProblema().getCleanCopy();			

			problemaInferior.removeUltimaRestricaoArtificial(v);
			r = new Restricao();
			r.adicionaVariavelOriginal(v);
			r.setaVariavelOriginal(v, 1.0);
			r.setSinal(SinalEquacao.MenorIgual);
			r.setValorEquacaoOriginal((double)valorInferior);
			r.setArtificial(true);
			problemaInferior.getRestricoes().add(r);
			try {
				solucaoInferior = solverBranchAndBound.solve(problemaInferior);
			} catch (Exception e) {
			}				
			//solucaoInferior = solverBranchAndBound.branchAndBound(candidata);
			candidata = melhorSolucaoInteira(solucaoSuperior,solucaoInferior);
			if(candidata != null){
				melhorSolucao = melhorSolucaoInteira(melhorSolucao,candidata);
				s = melhorSolucao;
			}
		}
		melhorSolucao = melhorSolucaoInteira(s, melhorSolucao);
		s.getProblema().melhorSolucao = melhorSolucao;
		return melhorSolucao;
	}


	private Solucao melhorSolucaoInteira(Solucao s1, Solucao s2){
		if(s1 == null){
			return s2;
		}else if(s2 == null){
			return s1;
		} else{
			return s1.melhorSolucaoInteiraQue(s2) ? s1: s2;
		}
	}


	private Variavel variavelSeparacao(Solucao s){
		Variavel sep = null;
		double valorMaximo = 0;
		for(Variavel v: problema.getVariaveis()){
			if(v.isInteira()){
				Double valor = s.getValorVariavel(v);
				if(valor != null && SimplexUtils.round(valor,problema.getCasasDecimais()) % 1 !=0 && valor > valorMaximo){
					sep = v;
					valorMaximo = valor;
				}
			}
		}
		return sep;
	}

}
