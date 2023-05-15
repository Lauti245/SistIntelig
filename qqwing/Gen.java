package com.qqwing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Gen implements Comparable<Gen>{
	private List<List<Integer>> gen;
	private static Random rand = new Random();
	private int fitness;
	 int col = 0;
	 int cuad = 0;
	
	public Gen(SudokuBase sudo) {
		gen = generarFormato(sudo);
		fitness=0;
	}
	
	public Gen(Gen other, boolean mix) {
		gen=other.copy().getGen();
		if(mix)permutarGen();
		fitness=0;
	}
	private Gen (List<List<Integer>> genCopy) {
		gen=genCopy;
		fitness=0;
	}
	private void permutarGen() {
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
	
	private List<List<Integer>> generarFormato(SudokuBase sudo) {
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
	private List<Integer> generarDominio(int fil){
		List<Integer> sol=new ArrayList<>();
		for(int i=0; i<gen.get(fil).size();i++) {
			sol.add(gen.get(fil).get(i));
		}
		return sol;
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
	@Override
	public String toString() {
		return gen.toString();
	}
	@Override
	public boolean equals(Object o) {
		boolean ok=false;
		if(o instanceof Gen) {
			ok=((Gen) o).getGen().equals(getGen());
		}
		return ok;
	}
	@Override
	public int hashCode() {
		return gen.hashCode();
	}
	
	
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
	
	
	
	private static List<List<Integer>> copyGen(List<List<Integer>> other){
		List<List<Integer>> sol=new ArrayList<>();
		for(int f=0; f<other.size();f++) {
			sol.add(new ArrayList<>());
			for(int c=0; c<other.get(f).size();c++) {
				sol.get(f).add(other.get(f).get(c));
			}
		}
		return sol;
	}
	
	
	
	public static Gen[] mutacion(Gen[] poblacion) {
		for(int i=0; i<poblacion.length;i++) {
			int eleccionFila=rand.nextInt(0,poblacion[0].getGen().size());
			int intercambio1=rand.nextInt(0,poblacion[i].getGen().get(eleccionFila).size());
			int intercambio2=intercambio1;
			while(intercambio1==intercambio2) intercambio2=rand.nextInt(0,poblacion[i].getGen().get(eleccionFila).size());
			int aux=poblacion[i].getGen().get(eleccionFila).get(intercambio1);
			poblacion[i].getGen().get(eleccionFila).set(intercambio1,poblacion[i].getGen().get(eleccionFila).get(intercambio2));
			poblacion[i].getGen().get(eleccionFila).set(intercambio2,aux);
		}
		return poblacion;
	}
	
	
	
	
	
	@Override
	public int compareTo(Gen o) {
		return ((Integer)fitness).compareTo(o.fitness);
	}
	
	
	
	public void refreshFitness(SudokuBase sudo) {
		int[] puzzle=toArray(sudo);
		fitness = comprobarColumna(puzzle)+comprobarCasillas(puzzle);
		
	}
	
	

	
	
	/*private int comprobarColumna(int[] puzzle) {
	    int sol = 0;
	    for (int col = 0; col < SudokuBase.getBoardSize(); col += SudokuBase.getRowColSecSize()) {
	        Set<Integer> unicos = new HashSet<>();
	        Set<Integer> repetidos = new HashSet<>();
	        for (int row = col; row < col + SudokuBase.getBoardSize(); row += SudokuBase.getRowColSecSize()) {
	            int num = puzzle[row];
	            if (!repetidos.contains(num)) {
	                if (unicos.contains(num)) {
	                    unicos.remove(num);
	                    repetidos.add(num);
	                } else {
	                    unicos.add(num);
	                }
	            } else {
	                throw new RuntimeException("El número " + num + " está en ambos conjuntos");
	            }
	        }
	        sol += unicos.size();
	    }
	    return sol;
	}

	private int comprobarCasillas(int[] puzzle) {
	    int sol = 0;
	    int gridSize = SudokuBase.getGridSize();
	    int blockSize = SudokuBase.getRowColSecSize();
	    for (int row = 0; row < blockSize; row += gridSize) {
	        for (int col = 0; col < blockSize; col += gridSize) {
	            int endRow = row + gridSize;
	            int endCol = col + gridSize;
	            Set<Integer> unicos = new HashSet<>();
	            Set<Integer> repetidos = new HashSet<>();
	            for (int i = row; i < endRow; i++) {
	                for (int j = col; j < endCol; j++) {
	                    int num = puzzle[i * blockSize + j];
	                    if (!repetidos.contains(num)) {
	                        if (unicos.contains(num)) {
	                            unicos.remove(num);
	                            repetidos.add(num);
	                        } else {
	                            unicos.add(num);
	                        }
	                    } else {
	                        throw new RuntimeException("El número " + num + " está en ambos conjuntos");
	                    }
	                }
	            }
	            sol += unicos.size();
	        }
	    }
	    return sol;
	}*/

	private int comprobarColumna(int[] puzzle) {
		int sol=0;
		for(int f=0; f<SudokuBase.getFilaCol();f++) {
			List<Integer> unicos=new ArrayList<>(), repetidos=new ArrayList<>();
			for(int c=f; c<SudokuBase.getTotal();c+=SudokuBase.getFilaCol()) {
				int num=puzzle[c];
				if(unicos.indexOf(num)<0 && repetidos.indexOf(num)<0) unicos.add(num);
				else if(unicos.indexOf(num)>=0 && repetidos.indexOf(num)<0) {
					repetidos.add(num);
					unicos.remove(unicos.indexOf(num));
				}else if(unicos.indexOf(num)>=0 && repetidos.indexOf(num)>=0) {
					throw new RuntimeException("Numero en conjunto de unicos y repetidos");
				}
			}
			sol+=unicos.size();
		}
		return sol;
	}
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
	public int[] toArray(SudokuBase sudo) {
		int[] sol=Arrays.copyOf(sudo.getPuzle(),SudokuBase.getTotal());
		List<List<Integer>> genAux=copyGen(gen);
		for(int f=1;f<gen.size();f++) {
			for(int c=0; c<gen.get(f).size();c++) {
				genAux.get(0).add(genAux.get(f).get(c));
			}
		}
		int f=0;
		for(int i=0;i<sol.length;i++) {
			if(sol[i]==0) {
				sol[i]=genAux.get(0).get(f);
				f++;
			}
		}
		return sol;
	}
	
}