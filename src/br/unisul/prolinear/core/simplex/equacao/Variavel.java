package br.unisul.prolinear.core.simplex.equacao;

public class Variavel {
	private String nome;
	private TipoVariavel tipo;
	private boolean inteira;
	
	public Variavel(String nome) {
		super();
		this.nome = nome;
		tipo = TipoVariavel.NORMAL;		
		inteira = false;
	}
	
	
	public Variavel clone(){
		Variavel v = new Variavel();
		v.nome = nome;
		v.tipo = tipo;
		v.inteira = inteira;
		return v;
	}
	
	public Variavel(String nome, TipoVariavel tipo) {
		super();
		this.nome = nome;
		this.setTipo(tipo);
	}



	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return nome.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return getNome().equals(((Variavel)obj).getNome());
	}

	public Variavel() {
		
	}
	
	public String toString(){
		return nome ;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}


	public TipoVariavel getTipo() {
		return tipo;
	}



	public void setTipo(TipoVariavel tipo) {
		this.tipo = tipo;
	}


	public static enum TipoVariavel { NORMAL,FOLGA,ARTIFICIAL}


	public boolean isInteira() {
		return inteira;
	}



	public void setInteira(boolean inteira) {
		this.inteira = inteira;
	}
}
