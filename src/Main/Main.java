package Main;

import Motor.Tablero;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Tablero tablero = new Tablero();
		System.out.println("Tablero inicial:");
		tablero.mostrarTablero();

		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.print("Ingresa movimiento (ej: e2 e4) o 'salir': ");
			String line = sc.nextLine().trim();
			if (line.equalsIgnoreCase("salir") || line.equalsIgnoreCase("exit")) break;
			String[] parts = line.split("\\s+");
			if (parts.length != 2) {
				System.out.println("Formato inválido. Usa: origen destino (p.e. e2 e4)");
				continue;
			}
			boolean ok = tablero.mover(parts[0], parts[1]);
			tablero.mostrarTablero();
			System.out.println(ok ? "Movimiento realizado" : "Movimiento rechazado");
		}
		sc.close();
		System.out.println("Adiós");
	}
}
