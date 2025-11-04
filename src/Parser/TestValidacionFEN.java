package Parser;

public class TestValidacionFEN {
    public static void main(String[] args) {
        Codificador codificador = new Codificador();

        System.out.println("=== PRUEBAS DE VALIDACIÓN FEN ===\n");

        // Prueba 1: FEN válido - posición inicial
        System.out.println("1. Posición inicial (VÁLIDO):");
        String fen1 = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        System.out.println("   FEN: " + fen1);
        System.out.println("   Resultado: " + (codificador.validarFEN(fen1) ? "✓ VÁLIDO" : "✗ INVÁLIDO"));
        System.out.println();

        // Prueba 2: FEN inválido - dos reyes blancos
        System.out.println("2. Dos reyes blancos (INVÁLIDO):");
        String fen2 = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKKNR w KQkq - 0 1";
        System.out.println("   FEN: " + fen2);
        System.out.println("   Resultado: " + (codificador.validarFEN(fen2) ? "✓ VÁLIDO" : "✗ INVÁLIDO"));
        System.out.println();

        // Prueba 3: FEN inválido - dos reyes negros
        System.out.println("3. Dos reyes negros (INVÁLIDO):");
        String fen3 = "rnbqkknr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        System.out.println("   FEN: " + fen3);
        System.out.println("   Resultado: " + (codificador.validarFEN(fen3) ? "✓ VÁLIDO" : "✗ INVÁLIDO"));
        System.out.println();

        // Prueba 4: FEN inválido - sin rey blanco
        System.out.println("4. Sin rey blanco (INVÁLIDO):");
        String fen4 = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQ1BNR w KQkq - 0 1";
        System.out.println("   FEN: " + fen4);
        System.out.println("   Resultado: " + (codificador.validarFEN(fen4) ? "✓ VÁLIDO" : "✗ INVÁLIDO"));
        System.out.println();

        // Prueba 5: FEN inválido - sin rey negro
        System.out.println("5. Sin rey negro (INVÁLIDO):");
        String fen5 = "rnbq1bnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        System.out.println("   FEN: " + fen5);
        System.out.println("   Resultado: " + (codificador.validarFEN(fen5) ? "✓ VÁLIDO" : "✗ INVÁLIDO"));
        System.out.println();

        // Prueba 6: FEN inválido - más de 8 peones blancos
        System.out.println("6. Más de 8 peones blancos (INVÁLIDO):");
        String fen6 = "rnbqkbnr/pppppppp/8/8/8/P7/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        System.out.println("   FEN: " + fen6);
        System.out.println("   Resultado: " + (codificador.validarFEN(fen6) ? "✓ VÁLIDO" : "✗ INVÁLIDO"));
        System.out.println();

        // Prueba 7: FEN inválido - más de 8 peones negros
        System.out.println("7. Más de 8 peones negros (INVÁLIDO):");
        String fen7 = "rnbqkbnr/pppppppp/p7/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        System.out.println("   FEN: " + fen7);
        System.out.println("   Resultado: " + (codificador.validarFEN(fen7) ? "✓ VÁLIDO" : "✗ INVÁLIDO"));
        System.out.println();

        // Prueba 8: FEN válido - posición en medio juego
        System.out.println("8. Posición en medio juego (VÁLIDO):");
        String fen8 = "r1bqkb1r/pppp1ppp/2n2n2/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R w KQkq - 0 1";
        System.out.println("   FEN: " + fen8);
        System.out.println("   Resultado: " + (codificador.validarFEN(fen8) ? "✓ VÁLIDO" : "✗ INVÁLIDO"));
        System.out.println();

        // Prueba 9: FEN inválido - más de 16 piezas blancas
        System.out.println("9. Más de 16 piezas blancas (INVÁLIDO):");
        String fen9 = "rnbqkbnr/pppppppp/8/8/8/PPPPPPPP/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        System.out.println("   FEN: " + fen9);
        System.out.println("   Resultado: " + (codificador.validarFEN(fen9) ? "✓ VÁLIDO" : "✗ INVÁLIDO"));
        System.out.println();

        // Prueba 10: FEN inválido - demasiadas reinas
        System.out.println("10. Demasiadas reinas blancas (INVÁLIDO):");
        String fen10 = "rnbqkbnr/pppppppp/8/8/8/QQQQQQQQ/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        System.out.println("   FEN: " + fen10);
        System.out.println("   Resultado: " + (codificador.validarFEN(fen10) ? "✓ VÁLIDO" : "✗ INVÁLIDO"));
        System.out.println();

        System.out.println("=== FIN DE PRUEBAS ===");
    }
}
