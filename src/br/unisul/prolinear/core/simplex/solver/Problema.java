package br.unisul.prolinear.core.simplex.solver;

import java.util.ArrayList;

import br.unisul.prolinear.core.simplex.equacao.CampoCalculado;
import br.unisul.prolinear.core.simplex.equacao.FuncaoObjetivo;
import br.unisul.prolinear.core.simplex.equacao.Restricao;
import br.unisul.prolinear.core.simplex.equacao.Variavel;
import br.unisul.prolinear.core.simplex.equacao.Variavel.TipoVariavel;

@SuppressWarnings("unchecked")
public class Problema {
	private FuncaoObjetivo funcao;
	private FuncaoObjetivo objetivoPrimeiraFase;	
	private Solucao solucao;	
	private TipoProblema tipo;
	private ArrayList<Restricao> restricoes;
	private ArrayList<Variavel> variaveisFolga;
	private ArrayList<Variavel> variaveisArtificiais;
	private ArrayList<Variavel> variaveis;
	private int casasDecimais;
	private boolean folga;
	private boolean conversao;
	private boolean somenteSolucao;
	private boolean exibirEstatisticas;
	private boolean reescrever;	
	protected Solucao melhorSolucao;
	private ArrayList<CampoCalculado> camposCalculados;
	

	public enum TipoProblema {
		MAX {@Override public String toString(){return "MAX";}},
		MIN {@Override public String toString(){return "MIN";}},	
	}


	@Override
	protected Problema clone(){
		Problema p = new Problema();
		p.funcao = funcao.clone();
		p.tipo = tipo;
		p.restricoes = (ArrayList<Restricao>) restricoes.clone();
		p.variaveis = (ArrayList<Variavel>) variaveis.clone();
		return p;
	}

	public Problema() {
		restricoes = new ArrayList<Restricao>();
		funcao =  new FuncaoObjetivo();
		variaveisFolga = new ArrayList<Variavel>();
		variaveis = new ArrayList<Variavel>();
		setVariaveisArtificiais(new ArrayList<Variavel>());
		casasDecimais = 2;
		folga = false;
		reescrever = true;
		camposCalculados = new ArrayList<CampoCalculado>();
	}


	public int getNumeroVariaveisInteiras(){
		int result = 0;
		for(Variavel v : variaveis){
			if(v.isInteira())
				result++;
		}
		return result;
	}
	
	public Problema getCleanCopy(){
		Problema p = new Problema();
		p.funcao = funcao.getCleanCopy();
		p.tipo = tipo;
		for(Restricao r: restricoes){
			p.restricoes.add(r.getCleanCopy());
		}
		for(Variavel v:variaveis){
			if(v.getTipo() == TipoVariavel.NORMAL){
				p.variaveis.add(v.clone());
			}
		}
		p.casasDecimais = casasDecimais;
		p.melhorSolucao = melhorSolucao;
		return p;
	}


	public void removeUltimaRestricaoArtificial(Variavel v){
		Restricao r = null;
		boolean encontrada = false;
		for(int i = restricoes.size() -1; i >= 0;i--){
			r = restricoes.get(i);
			if(r.isArtificial() && r.getVariaveisOriginais().contains(v)){
				encontrada = true;
				break;
			}
		}
		if (encontrada) {
			restricoes.remove(r);
			r = null;			
		}
	}

	public Restricao getRestricao(String identificador){
		for(Restricao r: restricoes){
			if(r.getIdentificador().equals(identificador)){
				return r;
			}
		}
		return null;
	}

	public String tableau(){
		StringBuilder sb = new StringBuilder();
		for(Variavel v :variaveis){
			sb.append(v+ "  ");
		} 
		for(Variavel v:variaveisFolga){
			sb.append(v+ "  ");
		}
		sb.append("-----------------------\n");
		for(Restricao r: restricoes){
			for(Variavel v :variaveis){
				sb.append(r.getCoeficientesTableau().get(v)+ "  ");
			} 
			for(Variavel v:variaveisFolga){
				sb.append(r.getCoeficientesTableau().get(v)+ "  ");
			}
			for(Variavel v:variaveisArtificiais){
				sb.append(r.getCoeficientesTableau().get(v)+ " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Função Objetivo:\n");		
		sb.append(funcao+"\n");
		if(objetivoPrimeiraFase != null){
			sb.append("Função objetivo Primeira Fase\n");
			sb.append(objetivoPrimeiraFase+"\n");
		}
		sb.append("Restrições:\n");
		for(Restricao r:restricoes){
			sb.append(r+"\n");
		}
		sb.append("Variáveis\n");
		sb.append(variaveis+"\n");
		sb.append("Variáveis Folga\n");
		sb.append(variaveisFolga+"\n");
		sb.append("Variáveis Artificiais\n");
		sb.append(variaveisArtificiais+"\n");
		return sb.toString();
	}

	public Variavel getVariavel(String variavel){
		for(Variavel v: variaveis){
			if(v.getNome().equals(variavel))
				return v;
		}
		return null;
	}
	
	//public bo 
	
	public FuncaoObjetivo getFuncao() {
		return funcao;
	}

	public void setFuncao(FuncaoObjetivo funcao) {
		this.funcao = funcao;
	}

	public ArrayList<Restricao> getRestricoes() {
		return restricoes;
	}

	public void setRestricoes(ArrayList<Restricao> restricoes) {
		this.restricoes = restricoes;
	}

	public Solucao getSolucao() {
		return solucao;
	}

	public void setSolucao(Solucao solucao) {
		this.solucao = solucao;
	}

	public TipoProblema getTipo() {
		return tipo;
	}

	public void setTipo(TipoProblema tipo) {
		this.tipo = tipo;
	}

	public ArrayList<Variavel> getVariaveisFolga() {
		return variaveisFolga;
	}

	public void setVariaveisFolga(ArrayList<Variavel> variaveisFolga) {
		this.variaveisFolga = variaveisFolga;
	}

	public ArrayList<Variavel> getVariaveis() {
		return variaveis;
	}

	public void setVariaveis(ArrayList<Variavel> variaveis) {
		this.variaveis = variaveis;
	}

	public FuncaoObjetivo getObjetivoPrimeiraFase() {
		return objetivoPrimeiraFase;
	}

	public void setObjetivoPrimeiraFase(FuncaoObjetivo objetivoPrimeiraFase) {
		this.objetivoPrimeiraFase = objetivoPrimeiraFase;
	}

	public int getCasasDecimais() {
		return casasDecimais;
	}

	public void setCasasDecimais(int casasDecimais) {
		this.casasDecimais = casasDecimais;
	}


	public ArrayList<Variavel> getVariaveisArtificiais() {
		return variaveisArtificiais;
	}

	public void setVariaveisArtificiais(ArrayList<Variavel> variaveisArtificiais) {
		this.variaveisArtificiais = variaveisArtificiais;
	}

	public boolean possuiFolga() {
		return folga;
	}

	public void setFolga(boolean folga) {
		this.folga = folga;
	}

	public boolean possuiConversao() {
		return conversao;
	}

	public void setConversao(boolean conversao) {
		this.conversao = conversao;
	}

	public boolean isSomenteSolucao() {
		return somenteSolucao;
	}

	public void setSomenteSolucao(boolean somenteSolucao) {
		this.somenteSolucao = somenteSolucao;
	}

	public boolean isExibirEstatisticas() {
		return exibirEstatisticas;
	}

	public void setExibirEstatisticas(boolean exibirEstatisticas) {
		this.exibirEstatisticas = exibirEstatisticas;
	}

	public boolean isReescrever() {
		return reescrever;
	}

	public void setReescrever(boolean reescrever) {
		this.reescrever = reescrever;
	}

	public ArrayList<CampoCalculado> getCamposCalculados() {
		return camposCalculados;
	}

	public void setCamposCalculados(ArrayList<CampoCalculado> camposCalculados) {
		this.camposCalculados = camposCalculados;
	}

}
