package ui;

import Clases.Ficha;
import Motor.Tablero;
import Parser.Codificador;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class VentanaAjedrez extends JFrame  {
    private Tablero tablero;
    private JButton[][] casillas;
    private JTextField areaMovimientos;
    private final Codificador codificador;
    private boolean esTurnoBlancas = true;

    public VentanaAjedrez() {
        tablero = new Tablero();
        codificador = new Codificador();
        initUI();
    }

    private void initUI() {
        setTitle("Tablero de Ajedrez");
        setSize(700, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panelTablero = new JPanel(new GridLayout(9,9));
        casillas = new JButton[8][8];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (i == 0 && j == 0) {
                    JButton esquinaVacia = new JButton();
                    esquinaVacia.setBackground(Color.DARK_GRAY);
                    esquinaVacia.setSize(new Dimension(50,50));
                    esquinaVacia.setBorderPainted(false);
                    panelTablero.add(esquinaVacia);
                } else if (i == 0) {
                    // Fila superior (etiquetas de columnas)
                    JButton etiqueta = new JButton(String.valueOf((char)('A' + j - 1)));
                    etiqueta.setSize(new Dimension(50, 50));
                    etiqueta.setFont(new Font("Arial", Font.BOLD, 16));
                    etiqueta.setBackground(new Color(139, 69, 19));
                    etiqueta.setBorderPainted(false);
                    panelTablero.add(etiqueta);
                } else if (j == 0) {
                    // Columna izquierda (etiquetas de filas)
                    JButton etiqueta = new JButton(String.valueOf(9 - i));
                    etiqueta.setSize(new Dimension(50, 50));
                    etiqueta.setFont(new Font("Arial", Font.BOLD, 16));
                    etiqueta.setBackground(new Color(139, 69, 19));
                    etiqueta.setBorderPainted(false);
                    panelTablero.add(etiqueta);
                } else { 
                    int filaTablero = i - 1;
                    int columnaTablero = j - 1;

                    casillas[filaTablero][columnaTablero] = new JButton();
                    casillas[filaTablero][columnaTablero].setFocusPainted(false);
                    casillas[filaTablero][columnaTablero].setFocusable(false);
                    casillas[filaTablero][columnaTablero].setFont(new Font("Arial Unicode MS", Font.PLAIN, 38));
                    casillas[filaTablero][columnaTablero].setPreferredSize(new Dimension(80, 80));
                    
                    casillas[filaTablero][columnaTablero].setMargin(new Insets(0, 0, 0, 0));
                    casillas[filaTablero][columnaTablero].setBorderPainted(false);
                    Color color = (filaTablero + columnaTablero) % 2 == 0 ? new Color(222, 184, 135) : new Color(139, 69, 19);

                    casillas[filaTablero][columnaTablero].setBackground(color);
                    panelTablero.add(casillas[filaTablero][columnaTablero]);
                }

            }
        }
        // Crear panel para movimientos
        JPanel panelMovimientos = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelMovimientos.setMaximumSize(new Dimension(getWidth() / 2, 30));
        panelMovimientos.setAlignmentX(CENTER_ALIGNMENT);
        panelMovimientos.setBackground(Color.LIGHT_GRAY);
        panelMovimientos.setAlignmentX(CENTER_ALIGNMENT);
        panelMovimientos.setFont(new Font("Arial", Font.BOLD, 15));
        String tituloTurno = esTurnoBlancas ? "Turno de las Blancas" : "Turno de las Negras";
        
        panelMovimientos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), 
            tituloTurno, 
            TitledBorder.CENTER,     
            TitledBorder.DEFAULT_POSITION  
        ));

        // Área de texto para movimientos
        areaMovimientos = new JTextField(25);
        areaMovimientos.setEditable(true);
        areaMovimientos.setFont(new Font("Arial", Font.PLAIN, 14));
        areaMovimientos.addActionListener(e -> {
            procesarMovimiento(areaMovimientos, esTurnoBlancas);
        });
        SwingUtilities.invokeLater(() -> areaMovimientos.requestFocus());

        panelMovimientos.add(areaMovimientos, BorderLayout.PAGE_END);

        JPanel guardarYCargarPanel = new JPanel();
        guardarYCargarPanel.setMaximumSize(new Dimension(20,20));

        JButton botonGuardar = new JButton("Guardar");
        botonGuardar.setFocusPainted(false);
        botonGuardar.addActionListener( e -> {
            String nombreArchivo = JOptionPane.showInputDialog(this, "Ingrese nombre del archivo para guardar la notación BNF:", "Guardar Partida", JOptionPane.PLAIN_MESSAGE);
            if (nombreArchivo != null && !nombreArchivo.trim().isEmpty()) {
                nombreArchivo = "partidas/" + nombreArchivo + ".bnf";
            } else {
                JOptionPane.showMessageDialog(this, "Nombre de archivo inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(codificador.archivoExiste(nombreArchivo)) {
                JOptionPane.showMessageDialog(this, "El archivo " + nombreArchivo + " ya existe. No se sobrescribirá.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            codificador.guardarPartidaBNF(nombreArchivo);
            JOptionPane.showMessageDialog(this, "Partida guardada exitosamente.", "Guardar Partida", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton botonCargar = new JButton("Cargar");
        botonCargar.setFocusPainted(false);
        botonCargar.addActionListener(e -> {
        String nombreArchivo = JOptionPane.showInputDialog(this, "Ingrese nombre del archivo a cargar:", "Cargar Partida", JOptionPane.PLAIN_MESSAGE);
        if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre de archivo inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        nombreArchivo = nombreArchivo.trim();
        cargarPartida(nombreArchivo);
    });
    guardarYCargarPanel.add(botonGuardar);
    guardarYCargarPanel.add(botonCargar);

        add(guardarYCargarPanel, BorderLayout.NORTH);
        add(panelTablero, BorderLayout.CENTER);
        add(panelMovimientos, BorderLayout.SOUTH);
        actualizarTablero();
        setLocationRelativeTo(null);
    }
    private void cargarPartida(String nombreArchivo) {
        String rutaCompleta = "partidas/" + nombreArchivo + ".bnf";
        List<String> movimientosCargados = codificador.cargarPartidaBNF(rutaCompleta);
        
        if (movimientosCargados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar movimientos del archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Reset board to initial position
        tablero = new Tablero(); // Reset to initial state
        esTurnoBlancas = true;   // Reset turn to white
        
        // Apply each movement to the board
        boolean todosAplicados = true;
        for (String movimiento : movimientosCargados) {
            if (movimiento.length() == 4) { // e2e4 format
                String origen = movimiento.substring(0, 2);
                String destino = movimiento.substring(2, 4);
                
                if (!tablero.mover(origen, destino)) {
                    System.err.println("Error aplicando movimiento: " + movimiento);
                    todosAplicados = false;
                    break;
                }
                esTurnoBlancas = !esTurnoBlancas; // Alternate turns
            }
        }
        
        if (todosAplicados) {
            actualizarTablero();
            actualizarTituloTurno();
            JOptionPane.showMessageDialog(this, 
                "Partida cargada exitosamente. " + movimientosCargados.size() + " movimientos aplicados.", 
                "Cargar Partida", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error al aplicar algunos movimientos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void actualizarTablero() {
        Ficha[][] tableroFichas = tablero.getTablero();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(tableroFichas[i][j] != null) {
                    Image icono = cargarIconoFicha(tableroFichas[i][j]);
                    if (icono != null) {
                        casillas[i][j].setIcon(new ImageIcon(icono));
                        casillas[i][j].setText(""); // Clear text when using icon
                    } else {
                        // Fallback to Unicode symbols if image loading fails
                        casillas[i][j].setIcon(null);
                    }
                } else {
                    casillas[i][j].setText("");
                    casillas[i][j].setIcon(null);
                }
            }
        }
    }

    

    private void cambiarTurno() {
        esTurnoBlancas = !esTurnoBlancas;
        actualizarTituloTurno();
    }

    private Image cargarIconoFicha(Ficha ficha) {
        String rutaBase = "src/public/pieces/";
        String color = ficha.getColor().equals("blanco") ? "W_" : "B_";
        String tipo = switch (ficha.getTipo()) {
            case "peon" -> "Pawn";
            case "torre" -> "Rook";
            case "caballo" -> "Knight";
            case "alfil" -> "Bishop";
            case "reina" -> "Queen";
            case "rey" -> "King";
            default -> "";
        };

        String rutaArchivo = rutaBase + color + tipo + ".png";
        ImageIcon originalIcon = new ImageIcon(rutaArchivo);
        Image scaledImage = originalIcon.getImage().getScaledInstance(32, 64, Image.SCALE_SMOOTH);
        
        return scaledImage;
    }

    private void procesarMovimiento(JTextField campo, boolean esBlanca) {
        String movimiento = campo.getText().trim();
        if (movimiento.isEmpty()) return;

        if (!codificador.validarBNF(movimiento)) {
            JOptionPane.showMessageDialog(this, "Movimiento inválido según BNF: " + movimiento, "Error", JOptionPane.ERROR_MESSAGE);
            campo.setText("");
            campo.requestFocusInWindow();
            return;
        }
        boolean aplicado = intentarAplicarMovimiento(movimiento, esBlanca);

        if (aplicado) {
            campo.setText("");
            actualizarTablero();
            cambiarTurno();
        } else {
            JOptionPane.showMessageDialog(this, "Movimiento no permitido: " + movimiento, "Error", JOptionPane.ERROR_MESSAGE);
            campo.setText("");
        }
    }

    private boolean intentarAplicarMovimiento(String movimiento, boolean esBlanca) {
        String origen = movimiento.substring(0, 2);
        String destino = movimiento.substring(2, 4);

        if (!tablero.esTurnoCorrecto(origen, esBlanca)) {
            return false;
        }
        boolean exito = tablero.mover(origen, destino);

        if (exito) {
            codificador.registrarMovimiento(movimiento);
            ganador(esBlanca ? "blanco" : "negro");
            return true;
        }

        return false;
    }
    private void ganador(String color) {
        if(tablero.esJaqueMate(color)) {
            JOptionPane.showMessageDialog(this, "¡Las " + color + " han ganado!", "Juego Terminado", JOptionPane.INFORMATION_MESSAGE);
        } 
    }
    private void actualizarTituloTurno() {
        String tituloTurno = esTurnoBlancas ? "Turno de las Blancas" : "Turno de las Negras";
        
        // Find the movements panel and update its border
        Container contentPane = getContentPane();
        for (Component comp : contentPane.getComponents()) {
            if (comp instanceof JPanel panel) {
                if (panel.getBorder() instanceof TitledBorder) {
                    panel.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(), 
                        tituloTurno, 
                        TitledBorder.CENTER,     
                        TitledBorder.DEFAULT_POSITION  
                    ));
                    panel.repaint();
                    break;
                }
            }
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater( () -> {
            new VentanaAjedrez().setVisible(true);
        });
    }
}
