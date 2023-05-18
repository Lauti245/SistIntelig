package Sudo;


import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;


import com.qqwing.Difficulty;
import com.qqwing.QQWing;

public class SudokuBase {
	
	List<Integer> columnas = new ArrayList<Integer>();
	private static final int TOTAL = 81;
	private static final int FILA_COL = 9;
    private static final int SECCION = 27;
    private static final int FIL_SEC = 3;
    private static final String SPACE = "\n";
	private static int[] puzzle;
	  
	  public int[] getPuzle() {
		  return puzzle;
	  }
	  
	  public void setPuzzle(int [] sol) {
		  puzzle = sol;
	  }
	  
	//create sudoku of specific difficulty level
	public static int[] computePuzzleByDifficulty(Difficulty d) {
		QQWing qq = new QQWing();
			qq.setRecordHistory(true);
		qq.setLogHistory(false);
		boolean go_on = true;
		while (go_on) {
			qq.generatePuzzle();
			qq.solve();
			Difficulty actual_d = qq.getDifficulty();
			System.out.println("Difficulty: "+actual_d.getName());
			go_on = !actual_d.equals(d);
		}
		puzzle = qq.getPuzzle();
		return puzzle;
	}
	
	//cheat by creating absurdly simple sudoku, with a given number of holes per row
	public static int[] computePuzzleWithNHolesPerRow(int numHolesPerRow) {
		Random rnd = new Random();
		QQWing qq = new QQWing();

		qq.setRecordHistory(true);
		qq.setLogHistory(false);
		qq.generatePuzzle();
		qq.solve();
		
		int []solution = qq.getSolution();
		HashSet<Integer> set = new HashSet<Integer>();
		for (int i=0; i<9; i++) {
			set.clear();
			while(set.size()<numHolesPerRow) {
				int n = rnd.nextInt(9);
				if (set.contains(n)) continue;
				set.add(n);
			}
			for (Integer hole_idx : set) {
				solution[i*9+hole_idx] = 0;
			}
		}
		return solution;
	}

	public SudokuBase(int option) {
		//option = 1;
		//int option = 2;
		//int option = 3;
		//int option = 4;
		//int option = 5;
        switch (option) {
        case 1:
            //Extremely easy puzzle, should be solvable without tuning the parameters of the genetic algorithm
            puzzle = computePuzzleWithNHolesPerRow(3);
            break;
        case 2:
            //Puzzle with difficulty SIMPLE as assessed by QQWing.
            //Should require just minimal tuning of the parameters of the genetic algorithm
            puzzle = computePuzzleByDifficulty(Difficulty.SIMPLE);
            break;
        case 3:
            //Puzzle with difficulty EASY as assessed by QQWing.
            //Should require some tuning of the parameters of the genetic algorithm
            puzzle = computePuzzleByDifficulty(Difficulty.EASY);
            break;
        case 4:
            //Puzzle with difficulty INTERMEDIATE as assessed by QQWing.
            //Should require serious effort tuning the parameters of the genetic algorithm
            puzzle = computePuzzleByDifficulty(Difficulty.INTERMEDIATE);
            break;
        case 5:
            //Puzzle with difficulty EXPERT as assessed by QQWing.
            //Should require great effort tuning the parameters of the genetic algorithm
            puzzle = computePuzzleByDifficulty(Difficulty.EXPERT);
            break;
        }
        
        //IMPORTANT: QQWing returns the puzzle as a single-dimensional array of size 81, row by row.
        //           Holes (cells without a number from 1 to 9) are represented by the value 0.
        //           It is advisable to convert this array to a data structure more amenable to manipulation.
	
	
	
	}
	



	
	public static int[] algoritmogenetico(SudokuBase sudo, int poblacionInicial) {
        Gen inicial = new Gen(sudo);
        Gen[] poblacion = inicializaPoblacion(inicial, poblacionInicial);

        int FitnessGoal = 162;
        List<Integer> fitnessLista = new ArrayList<>();

        for (int i = 0; i < poblacion.length || poblacion[poblacion.length - 1].getFitness() != FitnessGoal; i++) {
            poblacion = Gen.Reproduce(poblacion);
            poblacion = Gen.mutate(poblacion);
            weight(poblacion, sudo);
            Arrays.sort(poblacion);

//            int fitnessMejorIndividuo = poblacion[poblacion.length - 1].getFitness();
//            fitnessLista.add(fitnessMejorIndividuo);
//            int fitnessMejorIndividuo = poblacion[poblacion.length - 1].getFitness();
//            fitnessLista.add(fitnessMejorIndividuo);
            double mediaFitness = calcularMediaFitness(poblacion);
            System.out.println("Media Fitness de la poblacion: " + mediaFitness);
            double fitnessSolucion = poblacion[poblacion.length - 1].getFitness();
            System.out.println("Mejor Fitness de la solucion: " + fitnessSolucion);
        }

        int[] resultado = poblacion[poblacion.length - 1].toArray(sudo);

//        // Calcular la media de fitness de la población
//        double mediaFitness = calcularMediaFitness(fitnessLista);
//        //Calcular fitness de la solucion
//        double fitnessSolucion = poblacion[poblacion.length - 1].getFitness();
        
//        for (int fitness : fitnessLista) {
//        	System.out.println("Fitnes de la solucion: " + fitness);
//        }
//        System.out.println("Media de fitness de la población: " + mediaFitness);

        return resultado;
    }

	
	

	 private static double calcularMediaFitness(Gen[] fitnessLista) {
	        int sumaFitness = 0;
	        for (Gen fitness : fitnessLista) {
	            sumaFitness+=fitness.getFitness();
	        }
	        return (double) sumaFitness / fitnessLista.length;
	    }
	
	 private static double calcularMediaFitness(List<Integer> fitnessLista) {
	        int sumaFitness = 0;
	        for (int fitness : fitnessLista) {
	            sumaFitness += fitness;
	        }
	        return (double) sumaFitness / fitnessLista.size();
	    }
	
	
	private static void weight(Gen[] poblacion, SudokuBase sudo) {
		for (int i = 0; i < poblacion.length; i++) {
			Gen x = poblacion[i];
			x.nuevoFitness(sudo);
		}
	}
	
	
	private static Gen[] inicializaPoblacion(Gen inicial, int poblacionInicial) {
	    Gen[] solucion = new Gen[poblacionInicial];
	    solucion[0] = inicial;
	    Set<Gen> poblacionSet = new HashSet<>();
	    poblacionSet.add(inicial);
	    for (int i = 1; i < poblacionInicial; i++) {
	        Gen gen1 = new Gen(inicial, true); // se aplica una mutacion
	        if (!poblacionSet.contains(gen1)) {
	            solucion[i] = gen1;
	            poblacionSet.add(gen1);
	        } else {
	            i--;
	        }
	    }
	    return solucion;
	}
	
	
	
	
	@Override
    public String toString (){
        return imprimirSudoku(puzzle);
    }
	
	private String imprimirSudoku(int[] sudoku) {
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < TOTAL; i++) {
	        sb.append(sudoku[i] == 0 ? "." : sudoku[i]);
	        sb.append(" ");
	        if ((i+1) % FILA_COL == 0) {
	            sb.append("\n");
	            if ((i+1) % SECCION == 0) {
	                sb.append("\n");
	            }
	        } else if ((i+1) % FIL_SEC == 0) {
	            sb.append("  ");
	        }
	    }
	    return sb.toString();
	}



	public static int getTotal() {
		return TOTAL;
	}
	public static int getFilaCol() {
		return FILA_COL;
	}
	public static int getSeccion() {
		return SECCION;
	}
	public static int getFilaSeccion() {
		return FIL_SEC;
	}
	public static String getSpace() {
		return SPACE;
	}
    


}