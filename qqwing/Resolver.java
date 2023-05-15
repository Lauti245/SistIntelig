package com.qqwing;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Resolver {
	
	
	public static int[] resolverSudoku(SudokuBase sudo, int poblacionInicial) {

	    Gen inicial = new Gen(sudo);
	    Gen[] poblacion = generarPoblacionInicial(inicial, poblacionInicial);

	    int objetivoFitness = SudokuBase.getTotal() * 2;
	    for (int i = 0; i < poblacion.length || poblacion[poblacion.length - 1].getFitness() != objetivoFitness; i++) {
	        poblacion = Gen.Reproduce(poblacion);
	        poblacion = Gen.mutacion(poblacion);
	        determinarFitness(poblacion, sudo);
	        Arrays.sort(poblacion);
	    }

	    int[] resultado = poblacion[poblacion.length - 1].toArray(sudo);
	    return resultado;
	}

	private static void determinarFitness(Gen[] poblacion, SudokuBase sudo) {
		for (int i = 0; i < poblacion.length; i++) {
			Gen x = poblacion[i];
			x.refreshFitness(sudo);
		}
	}
	
	
	private static Gen[] generarPoblacionInicial(Gen inicial, int poblacionInicial) {
	    Gen[] sol = new Gen[poblacionInicial];
	    sol[0] = inicial;
	    Set<Gen> poblacionSet = new HashSet<>();
	    poblacionSet.add(inicial);
	    for (int i = 1; i < poblacionInicial; i++) {
	        Gen aux = new Gen(inicial, true);
	        if (!poblacionSet.contains(aux)) {
	            sol[i] = aux;
	            poblacionSet.add(aux);
	        } else {
	            i--;
	        }
	    }
	    return sol;
	}

	
}
