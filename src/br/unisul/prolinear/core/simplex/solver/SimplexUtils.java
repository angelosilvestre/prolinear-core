package br.unisul.prolinear.core.simplex.solver;

import java.math.BigDecimal;

public class SimplexUtils {
	
	public static Double round(Double d, int casas){
		if(d ==null) d = 0.0;		
		return new BigDecimal(d).setScale(casas, BigDecimal.ROUND_HALF_EVEN).doubleValue();
	}
	
	public static double NullZero(Double d){
		return d != null? d :0;
	}
	
	
}
