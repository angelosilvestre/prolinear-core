package br.unisul.prolinear.core.simplex.solver;

import java.util.HashMap;

import br.unisul.prolinear.core.simplex.equacao.CampoCalculado;
import br.unisul.prolinear.core.simplex.equacao.Equacao;
import br.unisul.prolinear.core.simplex.equacao.Restricao;
import br.unisul.prolinear.core.simplex.equacao.Variavel;
import br.unisul.prolinear.core.simplex.equacao.Variavel.TipoVariavel;
import br.unisul.prolinear.core.simplex.solver.Problema.TipoProblema;

@SuppressWarnings("unchecked")
public class Solucao {
	private Problema problema;
	private HashMap<Variavel, Double> resultSet;
	private HashMap<Variavel, Double> resultados;
	private int iteracoes;
	private double valorOtimo;

	public Solucao(Problema problema){
		this.problema = problema;
		resultSet = new HashMap<Variavel, Double>();
		resultados = new HashMap<Variavel, Double>();
	}


	@Override
	protected Solucao clone(){
		Solucao nova = new Solucao(problema);
		nova.resultSet = (HashMap<Variavel, Double>)resultSet.clone();
		nova.iteracoes = iteracoes;
		nova.valorOtimo = valorOtimo; 
		return nova;
	}

	public Double getValorVariavel(Variavel v){
		return resultSet.get(v);
	}


	public String toString(){
		StringBuilder r = new StringBuilder();
		if(!problema.isSomenteSolucao() && problema.isExibirEstatisticas()){
			r.append(
					"O problema possui " + problema.getVariaveis().size() + " variáveis"+
							" e "+ problema.getRestricoes().size() +" restrições."
					);
			int varFolga = problema.getVariaveisFolga().size() ;	
			if(varFolga > 0){
				r.append("\nPara sua resolução foram necessárias " + varFolga + 
						" variáveis de folga \n\n");
			}
		}		
		r.append("Solução ótima encontrada na iteração "+ iteracoes+"\n");
		r.append("Valor Encontrado: "+ SimplexUtils.round(problema.getFuncao().getValorEquacaoTableau(),problema.getCasasDecimais())+"\n");
		r.append("Variáveis\n");
		for(Variavel v: problema.getVariaveis()){
			r.append(v+" = "+ SimplexUtils.round(resultSet.get(v),problema.getCasasDecimais())+"\n");
		}
		
		for(CampoCalculado c: problema.getCamposCalculados()){
			r.append(c+" = "+ SimplexUtils.round(resultSet.get(c),problema.getCasasDecimais())+"\n");
		}
		if(!problema.isSomenteSolucao()){
			StringBuilder novo = new StringBuilder();
			novo.append("\n"+problema.getTipo()+ problema.getFuncao().getLadoEsquerdo());
			novo.append("\nST");
			StringBuilder folgas = new StringBuilder();	
			StringBuilder descricaoFolgas = new StringBuilder();
			if(problema.possuiFolga()){
				for(Restricao re:problema.getRestricoes()){			
					double folga = SimplexUtils.round(re.getFolga(),problema.getCasasDecimais()) ;
					if(folga != 0){
						folgas.append("\n"+re.getIdentificador() + ") " + folga + ((re.getSinal() == Equacao.SinalEquacao.MenorIgual)? " (excesso)":" (folga)"));
						if(!re.getIdentificadorFolga().isEmpty()){
							double valorFolga;
							if(re.getConversao() != null){
								valorFolga = (int)(re.getFolga() / re.getConversao()); 
							}else{
								valorFolga = SimplexUtils.round(re.getFolga(),problema.getCasasDecimais());
							}
							descricaoFolgas.append(
									"\nFoi possível detectar " + valorFolga +
									" " + re.getDescricao() + " " + re.getIdentificadorFolga()
									);							
						}
					}
					Double otimizado = re.getValorOtimizado();
					double valor;
					if(otimizado != null){
						valor = SimplexUtils.round(otimizado,problema.getCasasDecimais());
					}else{
						valor = re.getValorEquacaoOriginal();
					}
					novo.append("\n"+re.getLadoEsquerdo()+ " " + re.getSinal() + " " + valor);
				}
			}
			novo.append("\nEND");
			String f = folgas.toString();
			if(!f.isEmpty()){
				r.append("\nO problema possui folgas ou excessos nas restrições:");
				r.append(f);
				r.append("\n"+descricaoFolgas);
			}

			if(problema.isReescrever()){
				if(problema.possuiFolga()){
					r.append("\n\nO problema pode ser reescrito");
					if(problema.possuiConversao()){
						r.append(", respeitando os fatores de conversão,");
					}
					r.append(" da seguinte forma:");
					r.append(novo.toString());
					r.append("\nsem alterar a solução ótima.");
				}else{
					r.append("\nO problema não possui nenhuma folga em suas restrições.");
					r.append("\nQualquer alteração influenciará a solução ótima.");
				}
			}
		}
		return r.toString();
	}

	public boolean melhorSolucaoInteiraQue(Solucao s){
		int inteiras = 0;
		int inteirasS = 0;
		int variaveisInteirasProblema = s.getProblema().getNumeroVariaveisInteiras();
		for(Variavel v : s.getProblema().getVariaveis()){
			if(v.isInteira()){
				Double valor = resultSet.get(v);
				if(valor != null && valor % 1 == 0){
					inteiras++;
				}				
				valor = s.getResultSet().get(v); 
				if(valor != null && valor % 1 == 0){
					inteirasS++;
				}
			}
		}
		int difA = Math.abs(inteiras - variaveisInteirasProblema);
		int difB = Math.abs(inteirasS -variaveisInteirasProblema);
		if(problema.getTipo() == TipoProblema.MAX){
			return (valorOtimo >= s.getValorOtimo()) || difA <= difB ; 
		}else{
			return (valorOtimo <= s.getValorOtimo()) || difA <= difB;
		}
	}

	

	public HashMap<Variavel, Double> getResultSet() {
		return resultSet;
	}

	public void setResultSet(HashMap<Variavel, Double> resultSet) {
		this.resultSet = resultSet;
	}

	public int getIteracoes() {
		return iteracoes;
	}

	public void setIteracoes(int iteracoes) {
		this.iteracoes = iteracoes;
	}


	public double getValorOtimo() {
		return valorOtimo;
	}


	public void setValorOtimo(double valorOtimo) {
		this.valorOtimo = valorOtimo;
	}

	public Problema getProblema(){
		return problema;
	}


	public HashMap<Variavel, Double> getResultados() {
		resultados.clear();
		for(Variavel v:resultSet.keySet()){
			if(v.getTipo() == TipoVariavel.NORMAL || v instanceof CampoCalculado){
				resultados.put(v, resultSet.get(v));
			}
		}
		return resultados;
	}


	public void setResultados(HashMap<Variavel, Double> resultados) {
		this.resultados = resultados;
	}

}
