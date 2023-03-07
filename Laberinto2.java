import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Crear la matriz de 60 filas y 80 columnas
        char[][] matriz = new char[60][80];

        // Inicializar la matriz con espacios en blanco
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = ' ';
            }
        }

        // Generar 1200 asteriscos aleatorios
        Random random = new Random();
        int asteriscosGenerados = 0;
        while (asteriscosGenerados < 1200) {
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
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j]);
            }
            System.out.println();
        }
    }
}
