import java.util.Random;

public class Main {

    public static void main(String[] args) {
        char[][] matriz = new char[60][80];
        Random rand = new Random();
        
        // Generar 1200 asteriscos aleatorios
        for (int i = 0; i < 1200; i++) {
            int fila = rand.nextInt(60);
            int columna = rand.nextInt(80);
            matriz[fila][columna] = '*';
        }
        
        // Generar ubicación aleatoria de la letra I
        int iFila = rand.nextInt(60);
        int iColumna = rand.nextInt(80);
        matriz[iFila][iColumna] = 'I';
        
        // Generar ubicación aleatoria de la letra F
        int fFila = rand.nextInt(60);
        int fColumna = rand.nextInt(80);
        while (fFila == iFila && fColumna == iColumna) { // Asegurarse de que la F no esté en la misma ubicación que la I
            fFila = rand.nextInt(60);
            fColumna = rand.nextInt(80);
        }
        matriz[fFila][fColumna] = 'F';
        
        // Imprimir la matriz
        for (int i = 0; i < 60; i++) {
            for (int j = 0; j < 80; j++) {
                if (matriz[i][j] == 0) {
                    System.out.print(' '); // Imprimir un espacio en blanco si no hay ningún carácter en la ubicación
                } else {
                    System.out.print(matriz[i][j]);
                }
            }
            System.out.println();
        }
    }
}
