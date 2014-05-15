package br.unisul.prolinear.core.simplex.equacao;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public class Equacao {
	protected ArrayList<Variavel> variaveisTableau;
	protected ArrayList<Variavel> variaveisOriginais;
	protected HashMap<Variavel,Double> coeficientesOriginais;
	protected HashMap<Variavel, Double> coeficientesTableau;
	protected Double valorEquacaoOriginal;
	protected Double valorEquacaoTableau;
	protected SinalEquacao sinal; 


	public Equacao() {
		variaveisOriginais = new ArrayList<Variavel>();
		coeficientesOriginais = new HashMap<Variavel, Double>();
		variaveisTableau = new ArrayList<Variavel>();
		coeficientesTableau = new HashMap<Variavel, Double>();
		valorEquacaoTableau =  0.0;
		valorEquacaoOriginal = 0.0;
	}


	public Equacao clone(){
		Equacao e = new Equacao();
		e.sinal = sinal;
		e.valorEquacaoOriginal = valorEquacaoOriginal.doubleValue();
		e.valorEquacaoTableau = valorEquacaoTableau.doubleValue();
		e.variaveisOriginais = (ArrayList<Variavel>) variaveisOriginais.clone();
		e.variaveisTableau = (ArrayList<Variavel>) variaveisTableau.clone();
		e.coeficientesOriginais = (HashMap<Variavel, Double>) coeficientesOriginais.clone();
		e.coeficientesTableau = (HashMap<Variavel, Double>) coeficientesTableau.clone();
		return e;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		//	sb.append(valorEquacao+ " ");
		int i = 0;
		for (Variavel v : variaveisOriginais) {
			double valor = coeficientesOriginais.get(v);
			String sinal = "";
			if(valor >= 0){
				if(i > 0){
					sinal = " + ";
				}
			}else{
				sinal = " - ";
			}
			sb.append(sinal + valor + v.getNome());
		}
		sb.append(" " + sinal + " "+valorEquacaoOriginal);
		return sb.toString();
	}

	public String getLadoEsquerdo(){
		StringBuilder sb = new StringBuilder();
		for(Variavel v: variaveisOriginais){
			double valor = coeficientesOriginais.get(v);
			if(valor != 0){
				String sinal = "";
				if(valor >= 0){
					sinal = " + ";
				}else{
					sinal = "  ";
				}
				sb.append(sinal + (valor != 1?valor:"") + v.getNome());
			}
		}
		return sb.toString();
	}

	public Equacao multiplicaEquacao(double valor){
		Equacao e = new Equacao();
		for(Variavel t: variaveisTableau){
			e.coeficientesTableau.put(t, coeficientesTableau.get(t) * valor);
		}
		e.valorEquacaoTableau = valorEquacaoTableau * valor;
		return e;
	}


	public void adicionaVariavelOriginal(Variavel v){
		variaveisOriginais.add(v);
		coeficientesOriginais.put(v, 0.00);
		adicionaVariavel(v);
	}

	public void adicionaVariavel(Variavel v){
		variaveisTableau.add(v);
		coeficientesTableau.put(v, 0.0);
	}

	public void setaVariavel(Variavel v, Double valor){
		coeficientesTableau.put(v, valor);
	}

	public void setaVariavelOriginal(Variavel v, Double valor){
		coeficientesOriginais.put(v, valor);
		setaVariavel(v, valor);
	}

	public void multiplicar(double valor){
		for (Variavel t : variaveisTableau) {
			coeficientesTableau.put(t, coeficientesTableau.get(t) * valor);
		}
		valorEquacaoTableau = valorEquacaoTableau * valor;
	}

	public void somar(double valor){
		for (Variavel t : variaveisTableau) {
			coeficientesTableau.put(t, coeficientesTableau.get(t) + valor);
		}
		valorEquacaoTableau = valorEquacaoTableau + valor;
	}

	public void somar(Equacao e){
		for(Variavel v: variaveisTableau){
			coeficientesTableau.put(v,getValor(coeficientesTableau.get(v)) + getValor(e.coeficientesTableau.get(v)));
		}
		valorEquacaoTableau += e.valorEquacaoTableau;
	}

	public double getValor(Double d){
		return d == null ? 0 : d;
	}

	public void subtrair(Equacao e){
		e.multiplicar(-1);
		somar(e);
	}

	public void dividir(double valor){
		multiplicar(1/valor);
	}


	public static enum SinalEquacao{
		MaiorIgual {
			@Override
			public String toString() {
				// TODO Auto-generated method stub
				return ">=";
			}
		}
		, 
		MenorIgual{
			@Override
			public String toString() {
				// TODO Auto-generated method stub
				return "<=";
			}
		}

		,  
		Igual{
			@Override
			public String toString() {
				// TODO Auto-generated method stub
				return "=";
			}
		}
	}

	public ArrayList<Variavel> getVariaveisTableau() {
		return variaveisTableau;
	}

	public void setVariaveisTableau(ArrayList<Variavel> variaveis) {
		this.variaveisTableau = variaveis;
	}

	public HashMap<Variavel, Double> getCoeficientesTableau() {
		return coeficientesTableau;
	}

	public void setCoeficientesTableau(HashMap<Variavel, Double> coeficientes) {
		this.coeficientesTableau = coeficientes;
	}

	public Double getValorEquacaoTableau() {
		return valorEquacaoTableau;
	}

	public void setValorEquacaoTableau(Double valorEquacao) {
		this.valorEquacaoTableau = valorEquacao;
	}

	public SinalEquacao getSinal() {
		return sinal;
	}

	public void setSinal(SinalEquacao sinal) {
		this.sinal = sinal;
	}

	public ArrayList<Variavel> getVariaveisOriginais() {
		return variaveisOriginais;
	}

	public void setVariaveisOriginais(ArrayList<Variavel> variaveisOriginais) {
		this.variaveisOriginais = variaveisOriginais;
	}

	public HashMap<Variavel, Double> getCoeficientesOriginais() {
		return coeficientesOriginais;
	}

	public void setCoeficientesOriginais(
			HashMap<Variavel, Double> coeficientesOriginais) {
		this.coeficientesOriginais = coeficientesOriginais;
	}

	public Double getValorEquacaoOriginal() {
		return valorEquacaoOriginal;
	}

	public void setValorEquacaoOriginal(Double valorEquacaoOriginal) {
		this.valorEquacaoOriginal = valorEquacaoOriginal;
		valorEquacaoTableau = valorEquacaoOriginal.doubleValue();
	}



}

