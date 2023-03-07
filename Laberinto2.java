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
       Random ini = new Random();
        int inicio = 0;
        while (inicio <1 ) {
            int fila = ini.nextInt(60);
            int columna = ini.nextInt(80);
            if (matriz[fila][columna] == ' ') {
                matriz[fila][columna] = 'I';
                inicio++;
            }
        }
        
        // Generar ubicación aleatoria de la letra F
        Random fin = new Random();
        int fina = 0;
        while (fina <1 ) {
            int fila = fin.nextInt(60);
            int columna = fin.nextInt(80);
            if (matriz[fila][columna] == ' ') {
                matriz[fila][columna] = 'F';
                fina++;
            }
        }
        
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
