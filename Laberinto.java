import java.lang.Math;

public class Laberinto {
	private int filas ;
	private int columnas;
	private char[][] x  ;
    char obs = 'x';
    char ini = 'I';
    char fin = 'F';
	
	
	
	public Laberinto(int m ,int n,int iniX,int iniY,int finX,int finY,int numObstaculos){
		filas =m;
		columnas = n;
		this.x = new char[filas][columnas];
		x = aniadeObjetos(iniX,iniY,finX,finY,numObstaculos);		
	}
	public char[][] aniadeObjetos(int iniX,int iniY,int finX,int finY,int numObstaculos){
		int current =0 ;
		x[iniX][iniY]= ini;
		x[finX][finY]= fin;
		while(current < numObstaculos){ // mientras no se haya llegado al numObstaculos requerido
			int	varX = (int) (Math.random() * (this.filas-1)) ; 
			int varY = (int) (Math.random() * (this.columnas-1)) ;
			colocar(varX,varY);  // añadimos aleatoriaamente un obstaculo
			current++; // incrementa el num obstaculos
			if(x[varX][varY]!= ini && x[varX][varY] != fin ){
				
				x[varX][varY] = obs;
			}
		}
		return x;
	}
	private void colocar(int i , int  j){
		if(x[i][j]== ini || x[i][j] == fin ||  x[i][j]== obs ){ // si es objetivo o hay obstaculo
		//recorremos la matriz en busca de hueco libre desde la coordenada dada hasta el final de la matriz
			if(i==filas-1 && j == columnas-1 ){ // si esta al final de la matriz volvemos al principio
				colocar(0,0);
			}else if (j == columnas-1){ // si no hay mas columnas pasamos a la fila siguiente
				colocar(i+1,0);
			}else {
				colocar(i,j+1); // pasamos a la siguiente columna
			}
			}
		 else{ // si esta libre colocamos
			 x[i][j]= obs;
		 }
		}
		
	public void Print(){
		System.out.print("  ");
		for(int j=0;j<columnas ;j++){
		
		System.out.print("- ")	;
		
		}
		System.out.println(" ");
		for(int i =0 ;i<filas;i++){
			System.out.print("| ");
			for(int j=0;j<columnas;j++){
				System.out.print(x[i][j]);
				if(j!=columnas-1)
					System.out.print(" ");
				
			}System.out.println(" |");
			
		}
		System.out.print("  ");
		for(int j=0;j<columnas ;j++){
		
		System.out.print("- ")	;
		}
		
		
	}
}

