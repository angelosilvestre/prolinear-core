package br.unisul.prolinear.core.simplex.equacao;

import java.util.ArrayList;
import java.util.HashMap;

public class FuncaoObjetivo extends Equacao{
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Variavel v : variaveisOriginais) {
			double valor = coeficientesOriginais.get(v);
			sb.append((valor >= 0 ? "+":"") + valor+v.getNome()+ " ");
		}
		sb.append(" = "+valorEquacaoOriginal);
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public FuncaoObjetivo clone(){
		FuncaoObjetivo e = new FuncaoObjetivo();
		e.sinal = sinal;
		e.valorEquacaoOriginal = valorEquacaoOriginal.doubleValue();
		e.valorEquacaoTableau = valorEquacaoTableau.doubleValue();
		e.variaveisOriginais = (ArrayList<Variavel>) variaveisOriginais.clone();
		e.variaveisTableau = (ArrayList<Variavel>) variaveisTableau.clone();
		e.coeficientesOriginais = (HashMap<Variavel, Double>) coeficientesOriginais.clone();
		e.coeficientesTableau = (HashMap<Variavel, Double>) coeficientesTableau.clone();
		return e;
	}
	
	public FuncaoObjetivo getCleanCopy(){
		FuncaoObjetivo f = new FuncaoObjetivo();
		for(Variavel v: variaveisOriginais ){
			Variavel aux = v.clone();
			f.variaveisOriginais.add(aux);
			f.variaveisTableau.add(aux);
			Double coef = coeficientesOriginais.get(v);
			f.coeficientesOriginais.put(aux, coef);
			f.coeficientesTableau.put(aux, coef);
		}
		f.valorEquacaoOriginal = valorEquacaoOriginal.doubleValue();
		f.valorEquacaoTableau = valorEquacaoOriginal.doubleValue();


		return f;
	}
}
