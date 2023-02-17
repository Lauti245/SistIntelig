import java.lang.Math;

public class Laberinto {
	private int filas ;
	private int columnas;
	private char[][] x  ;
    char obs = 'x';
    char ini = 'I';
    char fin = 'F';
	
	
	
	//Laberinto aux = new Laberinto(3,4,1,1,2,3,8);
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
		while(current < numObstaculos){
			int	varX = (int) (Math.random() * (this.filas-1)) ;
			int varY = (int) (Math.random() * (this.columnas-1)) ;
			colocar(varX,varY);
			current++;
			if(x[varX][varY]!= ini && x[varX][varY] != fin ){
				
				x[varX][varY] = obs;
			}
		}
		return x;
	}
	private void colocar(int i , int  j){
		if(x[i][j]== ini || x[i][j] == fin ||  x[i][j]== obs ){ // si es objetivo o hay obstaculo
			if(i==filas-1 && j == columnas-1 ){
				colocar(0,0);
			}else if (j == columnas-1){
				colocar(i+1,0);
			}else {
				colocar(i,j+1);
			}
			}
		 else{ // si esta vacio
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

