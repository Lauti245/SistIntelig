import java.util.List;
import java.util.Random;

public class Laberinto {
	private Posicion start;
	private Posicion finish;
	private List<Posicion> solucion;

	public Laberinto(int numObstaculos) {
		// Crear la matriz de 60 filas y 80 columnas
		char[][] matriz = new char[60][80];
		// Inicializar la matriz con espacios en blanco

		generar(matriz, numObstaculos);
	}

	public void generar(char[][] matriz, int num) {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				matriz[i][j] = ' ';
			}
		}
		// Generar 1200 asteriscos aleatorios
		Random random = new Random();
		int asteriscosGenerados = 0;
		while (asteriscosGenerados < num) {
			int fila = random.nextInt(60);
			int columna = random.nextInt(80);
			if (matriz[fila][columna] == ' ') {
				matriz[fila][columna] = '*';
				asteriscosGenerados++;
			}
		}

		// Generar ubicación aleatoria de la letra I
		Random ini = new Random();
		int inicio = 0;

		while (inicio < 1) {
			int fila = ini.nextInt(60);
			int columna = ini.nextInt(80);
			if (matriz[fila][columna] == ' ') {
				matriz[fila][columna] = 'I';
				start = new Posicion(fila, columna);
				inicio++;
			}
		}

		// Generar ubicación aleatoria de la letra F
		Random fin = new Random();
		int fina = 0;
		while (fina < 1) {
			int fila = fin.nextInt(60);
			int columna = fin.nextInt(80);
			if (matriz[fila][columna] == ' ') {
				matriz[fila][columna] = 'F';
				finish = new Posicion(fila, columna);
				fina++;
			}
		}

		// Imprimir la matriz
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				System.out.print(matriz[i][j]);
			}
			System.out.println();
		}
	}
	public int dist(Posicion ini, Posicion fin){
		int distancia = (Math.abs(ini.getY() - fin.getY()) + Math.abs(ini.getX() - fin.getX()) - 1);
		System.out.println("La distancia (h) es :" + distancia);
		return distancia;

	}
	
	public List<Posicion> AEstrella(){
		List<Posicion> openset = null	;
		List<Posicion> aux = AlgAux(new List<Posicion> closedset,new List<Posicion> parent)
		return null;
	}

	private List<Posicion> AlgAux(List<Posicion> openset, new List<Posicion> openset closedset,List<Posicion>parent){
		if (parent.isEmpty()) return parent;
		Posicion posAct = parent.get(parent.size()-1);
		if (posAct.equals(this.finish)) return parent;
		while()
		return null;
	}
	
	private boolean solucion(List<Posicion> lista){
		if (lista.isEmpty()) return false;
		Posicion posAct = lista.get(lista.size()-1);
		if (posAct.equals(salida)) return true;
		for (int i=0; i<4; i++){
		Posicion posSig = posAct.siguiente(i);
		if (!lista.contains(posSig)&& // no hay ciclos en el camino
		estaEnLaberinto(posSig)&& // la nueva posición es correcta
		laberinto[posSig.x()][posSig.y()]!=1){
		lista.add(posSig);
		if (! solucion(lista)) lista.remove(lista.size()-1);
		else return true;
		}
		}
		return false;
		}
}
