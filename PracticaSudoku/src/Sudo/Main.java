package Sudo;


public class Main {

	
	
	public static final int POBLACION_INICIAL = 20;
	public static void main (String[] args) {
		SudokuBase sudo = new SudokuBase(1);
		System.out.println(sudo.toString());
		int [] sol = SudokuBase.algoritmogenetico(sudo, POBLACION_INICIAL);
		sudo.setPuzzle(sol);
		System.out.println(sudo.toString());
		
		
		
		
		
		
	}
}
