package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;
import javax.swing.border.TitledBorder;
public class Menu extends JFrame {

    public Menu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setVisible(true);

        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Menu - Opciones",
                TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION
            ),
            BorderFactory.createEmptyBorder(20, 50, 20, 50)
        ));
        panel.setBackground(panel.getBackground().brighter());
        panel.setBackground(Color.white);        
        add(panel, BorderLayout.CENTER);

        JButton startButton = new JButton("Iniciar Nueva Partida");
        startButton.setFocusPainted(false);
        startButton.setBackground(Color.lightGray);
        startButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "",
                TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION
            ),
            BorderFactory.createEmptyBorder(20, 50, 20, 50)
        ));
        startButton.addActionListener(e -> {
            VentanaAjedrez ventana = new VentanaAjedrez();
            ventana.setVisible(true);
            dispose();
        });
   
        panel.add(startButton, BorderLayout.CENTER);
        
        JButton loadButton = new JButton("Cargar Partida");
        loadButton.setFocusPainted(false);
        loadButton.setBackground(Color.lightGray);
        loadButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "",
                TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION
            ),
            BorderFactory.createEmptyBorder(20, 50, 20, 50)
        ));

        
        loadButton.addActionListener(e -> {
            VentanaAjedrez ventana = new VentanaAjedrez();
            String nombreArchivo = JOptionPane.showInputDialog(this, "Ingrese nombre del archivo a cargar:", "Cargar Partida", JOptionPane.PLAIN_MESSAGE);
            if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre de archivo inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            nombreArchivo = nombreArchivo.trim();
            ventana.cargarPartida(nombreArchivo);
            ventana.setVisible(true);
            dispose();
        });

        panel.add(loadButton, BorderLayout.NORTH);

        JButton exitButton = new JButton("Salir");
        exitButton.setFocusPainted(false);
        exitButton.setBackground(Color.lightGray);
        exitButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "",
                TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION
            ),
            BorderFactory.createEmptyBorder(20, 50, 20, 50)
        ));
        exitButton.addActionListener(e -> {
            System.exit(0);
        }); 

        JButton loadWithFEN = new JButton("Cargar Partida con FEN");
        loadWithFEN.setFocusPainted(false);
        loadWithFEN.setBackground(Color.lightGray);
        loadWithFEN.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "",
                TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION
            ),
            BorderFactory.createEmptyBorder(20, 50, 20, 50)
        ));
        loadWithFEN.addActionListener(e -> {
            VentanaAjedrez ventana = new VentanaAjedrez();
            String fenInput = JOptionPane.showInputDialog(this, "Ingrese la cadena FEN para cargar la partida:", "Cargar Partida con FEN", JOptionPane.PLAIN_MESSAGE);
            if (fenInput == null || fenInput.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Entrada FEN inválida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            fenInput = fenInput.trim();
            boolean exito = ventana.cargarPartidaDesdeFEN(fenInput);

            if (!exito) {
                JOptionPane.showMessageDialog(this, "Error al cargar la partida desde la cadena FEN.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ventana.setVisible(true);
            dispose();
        });

        panel.add(loadWithFEN, BorderLayout.PAGE_START);
        panel.add(exitButton , BorderLayout.SOUTH);
        setVisible(true);
        setLocationRelativeTo(null);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater( () -> {
            new Menu().setVisible(true);
        });
    }

}
