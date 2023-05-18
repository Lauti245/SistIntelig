package Sudo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Gen implements Comparable<Gen>{
	private List<List<Integer>> gen;
	private static Random rand = new Random();
	private int fitness;
	 int col = 0;
	 int cuad = 0;
	
	public Gen(SudokuBase sudo) {
		gen = representaSudoku(sudo);
		fitness=0;
	}
	
	//representa el sudoku como listas de lista con posibles dominios
	//cambiar
	private List<List<Integer>> representaSudoku(SudokuBase sudo) {
		int fil = 0;
		int[] completo = sudo.getPuzle();
		List<List<Integer>> sol=new ArrayList<>();
		sol.add(new ArrayList<>());
		List<Integer> dominioFila=generarDominio(sudo, 0);
		for(int i=0; i<SudokuBase.getTotal();i++) {
			if(i%9==0 && i!=0) {
				fil++;
				sol.add(new ArrayList<>());
				dominioFila=generarDominio(sudo,fil);	
			}
			if(completo[i]==0) {
				sol.get(fil).add(dominioFila.get(0));
				dominioFila.remove(0);
			}	
		}
		return sol;
	}
	
	//genera dominio de posibles soluciones a una fila del sudoku 
	private List<Integer> generarDominio(int fil) {
	    List<Integer> sol = new ArrayList<>();
	    sol.addAll(gen.get(fil));
	    return sol;
	}

	
	public Gen(Gen other, boolean mix) {
		gen = other.copy().getGen();
		if(mix)permutaDigito();
		fitness=0;
	}
	private Gen (List<List<Integer>> genCopy) {
		gen=genCopy;
		fitness=0;
	}
	
	//coge un elemento de una fila de gen y lo inercambia por otro elemento de esa misma fila, lo hace para todas las filas
	private void permutaDigito() {
		for(int f=0; f<gen.size();f++) {
			List<Integer> dominioFila=generarDominio(f);
			for(int c=0; c<gen.get(f).size();c++) {
				int pos=rand.nextInt(0,dominioFila.size());
				gen.get(f).add(c, dominioFila.get(pos));
				gen.get(f).remove(c+1);
				dominioFila.remove(pos);
			}
		}
	}
	


	private List<Integer> generarDominio(SudokuBase sudo, int fil) {
		List<Integer> sol=new ArrayList<>(List.of(1,2,3,4,5,6,7,8,9));
		int[] puzzle=sudo.getPuzle();
		for(int i=9*fil;i<9*(fil+1);i++) {
			int pos=sol.indexOf(puzzle[i]);
			if(pos>=0) {
				sol.remove(pos);
			}
		}
		
		return sol;
	}
	public Gen copy() {
		List<List<Integer>> genCopy=copyGen(this.gen);
		return new Gen(genCopy);
	}
	public List<List<Integer>> getGen() {
		return gen;
	}
	public Integer get(int f, int c) {
		return gen.get(f).get(c);
	}
	
	//convierte el gen en un string
	@Override
	public String toString() {
		return gen.toString();
	}
	
	//compara si un gen es igual que otro 
	@Override
	public boolean equals(Object obj) {
		boolean equal = false;
		if(obj instanceof Gen) {
			equal =((Gen) obj).getGen().equals(getGen());
		}
		return equal;
	}
	
	@Override
	public int hashCode() {
		return gen.hashCode();
	}
	
	
	//funcion de reproduccion coge dos filas (padres) y las reproduce
	public static Gen[] Reproduce(Gen[] individuos) {
	    int nCruces = individuos[0].getGen().size() - 1;
	    if (nCruces > 0) {
	        for (int i = 0; i < individuos.length; i += 2) {
	            int corte = rand.nextInt(0, nCruces);
	            List<List<Integer>> gen1 = new ArrayList<>(individuos[i].getGen());
	            List<List<Integer>> gen2 = new ArrayList<>(individuos[i + 1].getGen());
	            for (int j = corte + 1; j <= nCruces; j++) {
	                List<Integer> temp = gen1.get(j);
	                gen1.set(j, gen2.get(j));
	                gen2.set(j, temp);
	            }
	            individuos[i] = new Gen(gen1);
	            individuos[i + 1] = new Gen(gen2);
	        }
	    }
	    return individuos;
	}
	
	
	// copiamos la lista que recibe como parametro (other)
	
	private static List<List<Integer>> copyGen(List<List<Integer>> other) {
	    List<List<Integer>> sol = new ArrayList<>(other.size());
	    for (List<Integer> row : other) {
	        List<Integer> newRow = new ArrayList<>(row);
	        sol.add(newRow);
	    }
	    return sol;
	}

	
	
	// mutamos la poblacion (crossover) intercambia dos elementos de una fila 
	public static Gen[] mutacion(Gen[] poblacion) {
	    for(int i = 0; i < poblacion.length; i++) {
	        int eleccionFila = rand.nextInt(poblacion[0].getGen().size());
	        List<Integer> fila = poblacion[i].getGen().get(eleccionFila);
	        
	        int intercambio1 = rand.nextInt(fila.size());
	        int intercambio2 = rand.nextInt(fila.size());
	        
	        while(intercambio1 == intercambio2) {
	            intercambio2 = rand.nextInt(fila.size());
	        }
	        
	        Collections.swap(fila, intercambio1, intercambio2);
	    }
	    
	    return poblacion;
	}

	
	
	
	
	
	@Override
	public int compareTo(Gen o) {
		return ((Integer)fitness).compareTo(o.fitness);
	}
	
	
	//calculamos fitness
	public void nuevoFitness(SudokuBase sudo) {
		int[] puzzle=toArray(sudo);
		fitness = comprobarColumna(puzzle)+comprobarCasillas(puzzle);
		
	}
	

	private int comprobarColumna(int[] puzzle) {
	    int sol = 0;
	    for (int col = 0; col < SudokuBase.getFilaCol(); col++) {
	        Set<Integer> unicos = new HashSet<>();
	        Set<Integer> repetidos = new HashSet<>();
	        for (int row = col; row < SudokuBase.getTotal(); row += SudokuBase.getFilaCol()) {
	            int num = puzzle[row];
	            if (!unicos.contains(num) && !repetidos.contains(num)) {
	                unicos.add(num);
	            } else {
	                if (unicos.contains(num)) {
	                    repetidos.add(num);
	                    unicos.remove(num);
	                }
	            }
	        }
	        sol += unicos.size();
	    }
	    return sol;
	}


	
	
	//cambiar
	private int comprobarCasillas(int[] puzzle) {
		int sol=0;
		int desp=-SudokuBase.getFilaCol()*SudokuBase.getFilaSeccion();
		for(int i=0; i<SudokuBase.getFilaCol()/SudokuBase.getFilaSeccion();i++) {
			int lim=0;
			desp+=SudokuBase.getFilaCol()*SudokuBase.getFilaSeccion();
			for(int g=0; g<SudokuBase.getFilaCol()/SudokuBase.getFilaSeccion();g++) {
				lim+=SudokuBase.getFilaSeccion();
				List<Integer> unicos=new ArrayList<>(), repetidos=new ArrayList<>();
				for(int f=lim-SudokuBase.getFilaSeccion();f<SudokuBase.getFilaCol()*SudokuBase.getFilaSeccion();f+=SudokuBase.getFilaCol()) {
					for(int c=f+desp;c<f+desp+SudokuBase.getFilaSeccion();c++) {
						int num=puzzle[c];
						if(unicos.indexOf(num)<0 && repetidos.indexOf(num)<0) unicos.add(num);
						else if(unicos.indexOf(num)>=0 && repetidos.indexOf(num)<0) {
							repetidos.add(num);
							unicos.remove(unicos.indexOf(num));
						}else if(unicos.indexOf(num)>=0 && repetidos.indexOf(num)>=0) {
							throw new RuntimeException("Numero en conjunto de unicos y repetidos");
						}
					}
				}
				sol+=unicos.size();
			}
		}
		return sol;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public int getFitness() {
		return fitness;
	}
	
	// transformamos el sudoku en una serie de enteros que almacenamos en un array
	public int[] toArray(SudokuBase sudo) {
	    int[] sol = Arrays.copyOf(sudo.getPuzle(), SudokuBase.getTotal());
	    List<List<Integer>> genAux = copyGen(gen);
	    List<Integer> flattenedGen = genAux.stream().flatMap(List::stream).collect(Collectors.toList());
	    int f = 0;
	    for (int i = 0; i < sol.length; i++) {
	        if (sol[i] == 0) {
	            sol[i] = flattenedGen.get(f);
	            f++;
	        }
	    }
	    return sol;
	}

	
}