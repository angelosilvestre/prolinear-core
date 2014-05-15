package br.unisul.prolinear.core.simplex.equacao;

import java.util.ArrayList;
import java.util.HashMap;

import br.unisul.prolinear.core.simplex.equacao.Variavel.TipoVariavel;

@SuppressWarnings("unchecked")
public class Restricao extends Equacao{

	private Variavel variavelBasica;
	private Variavel variavelFolga;
	private String descricao;
	private String identificador;
	private Double folga;
	private Double valorOtimizado;
	private Double conversao;
	private String identificadorFolga;
	private boolean artificial;

	public Restricao() {
		super();
		descricao = "";
		identificadorFolga = "";
		identificador = "";
		artificial = false;
	}


	@Override
	public Restricao clone() {
		Restricao r = new Restricao();
		r.variaveisOriginais = (ArrayList<Variavel>) variaveisOriginais.clone();
		r.variaveisTableau = (ArrayList<Variavel>) variaveisTableau.clone();
		r.coeficientesTableau = (HashMap<Variavel, Double>) coeficientesTableau.clone();
		r.coeficientesOriginais = (HashMap<Variavel, Double>) coeficientesOriginais.clone();
		r.valorEquacaoOriginal = valorEquacaoOriginal.doubleValue();
		r.valorEquacaoTableau = valorEquacaoTableau.doubleValue();
		r.sinal = sinal;
		return r;
	}

	public Restricao getCleanCopy(){
		Restricao r = new Restricao();
		for(Variavel v: variaveisOriginais ){
			if(v.getTipo() == TipoVariavel.NORMAL){
				Variavel aux = v.clone();
				r.variaveisOriginais.add(aux);
				r.variaveisTableau.add(aux);
				Double coef = coeficientesOriginais.get(v);
				r.coeficientesOriginais.put(aux, coef);
				r.coeficientesTableau.put(aux, coef);
			}
		}
		r.valorEquacaoOriginal = valorEquacaoOriginal.doubleValue();
		r.valorEquacaoTableau = valorEquacaoOriginal.doubleValue();
		r.sinal = sinal;
		r.artificial = artificial;
		return r;
	}

	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append(identificador+")");
		s.append(getLadoEsquerdo());
		s.append(" " + sinal.toString() + " ");
		s.append(valorEquacaoOriginal);

		return s.toString();
	}

	public void setVariavelBasica(Variavel variavelBasica) {
		this.variavelBasica = variavelBasica;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public Variavel getVariavelBasica() {
		return variavelBasica;
	}

	public Variavel getVariavelFolga() {
		return variavelFolga;
	}

	public void setVariavelFolga(Variavel variavelFolga) {
		this.variavelFolga = variavelFolga;
	}


	public Double getConversao() {
		return conversao;
	}


	public void setConversao(Double conversao) {
		this.conversao = conversao;
	}


	public Double getFolga() {
		return folga;
	}


	public void setFolga(Double folga) {
		this.folga = folga;
	}


	public Double getValorOtimizado() {
		return valorOtimizado;
	}


	public void setValorOtimizado(Double valorOtimizado) {
		this.valorOtimizado = valorOtimizado;
	}


	public String getIdentificadorFolga() {
		return identificadorFolga;
	}


	public void setIdentificadorFolga(String identificadorFolga) {
		this.identificadorFolga = identificadorFolga;
	}


	public boolean isArtificial() {
		return artificial;
	}


	public void setArtificial(boolean artificial) {
		this.artificial = artificial;
	}	




}
