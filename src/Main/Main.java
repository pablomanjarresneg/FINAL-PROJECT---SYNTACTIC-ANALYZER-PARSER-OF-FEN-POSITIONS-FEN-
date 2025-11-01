package Main;

import Motor.Tablero;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Nueva partida con validación BNF");
            System.out.println("2. Jugar partida normal y generar BNF");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = sc.nextInt();
            sc.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    jugarPartidaConValidacionBNF();
                    break;
                case 2:
                    jugarPartidaYGenerarBNF();
                    break;
                case 3:
                    System.out.println("¡Hasta pronto!");
                    sc.close();
                    return;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    private static void jugarPartidaConValidacionBNF() {
        Tablero tablero = new Tablero();
        Codificador codificador = new Codificador();
        Scanner sc = new Scanner(System.in);

        System.out.println("Tablero inicial:");
        tablero.mostrarTablero();

        while (true) {
            System.out.print("Ingresa movimiento (ej: e2 e4) o 'salir': ");
            String line = sc.nextLine().trim();

            if (line.equalsIgnoreCase("salir")) break;

            if (!codificador.validarBNF(line)) {
                System.out.println("Movimiento no válido según la notación BNF");
                continue;
            }

            String[] parts = line.split("\\s+");
            boolean ok = tablero.mover(parts[0], parts[1]);

            if (ok) {
                codificador.registrarMovimiento(line);
            }

            tablero.mostrarTablero();
            System.out.println(ok ? "Movimiento realizado" : "Movimiento rechazado");
        }

        System.out.print("Ingrese nombre del archivo para guardar la notación BNF: ");
        String nombreArchivo = sc.nextLine();
        codificador.guardarPartidaBNF("src/partidas/" + nombreArchivo);
    }

    private static void jugarPartidaYGenerarBNF() {
        Tablero tablero = new Tablero();
        Codificador codificador = new Codificador();
        Scanner sc = new Scanner(System.in);

        System.out.println("Tablero inicial:");
        tablero.mostrarTablero();

        while (true) {
            System.out.print("Ingresa movimiento (ej: e2 e4) o 'salir': ");
            String line = sc.nextLine().trim();

            if (line.equalsIgnoreCase("salir")) break;

            String[] parts = line.split("\\s+");
            if (parts.length != 2) {
                System.out.println("Formato inválido. Usa: origen destino (p.e. e2 e4)");
                continue;
            }

            boolean ok = tablero.mover(parts[0], parts[1]);
            if (ok) {
                codificador.registrarMovimiento(line);
            }

            tablero.mostrarTablero();
            System.out.println(ok ? "Movimiento realizado" : "Movimiento rechazado");
        }

        System.out.print("Ingrese nombre del archivo para guardar la notación BNF: ");
        String nombreArchivo = sc.nextLine();
        codificador.guardarPartidaBNF(nombreArchivo);
    }
}
