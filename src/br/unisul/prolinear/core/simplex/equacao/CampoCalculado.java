package br.unisul.prolinear.core.simplex.equacao;

public class CampoCalculado extends Variavel {
	private String expressao;
	private double valor;
	
	public CampoCalculado(String nome) {
		super(nome);
		// TODO Auto-generated constructor stub
	}

	public String getExpressao() {
		return expressao;
	}

	public void setExpressao(String expressao) {
		this.expressao = expressao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	
	
}
