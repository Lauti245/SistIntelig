package com.qqwing;
import com.qqwing.SudokuBase;
import com.qqwing.Resolver;
import com.qqwing.Gen;

public class Main {

	public static final int POBLACION_INICIAL = 20;
	public static void main (String[] args) {
		
		SudokuBase sudoku = new SudokuBase(1);
		System.out.println(sudoku.toString());
		int [] sol = Resolver.resolverSudoku(sudoku, POBLACION_INICIAL);
		sudoku.setPuzzle(sol);
		System.out.println(sudoku.toString());
		
		
		
	}
}
