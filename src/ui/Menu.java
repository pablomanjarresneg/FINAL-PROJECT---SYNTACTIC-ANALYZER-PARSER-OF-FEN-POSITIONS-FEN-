package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.TitledBorder;
public class Menu extends JFrame {

    public Menu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500); // Increased height for 4 buttons
        setVisible(true);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Menu - Opciones",
                TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION
            ),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panel.setBackground(Color.white);        
        add(panel, BorderLayout.CENTER);

        // Create a button panel with GridLayout for all 4 buttons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBackground(Color.white);

        JButton startButton = new JButton("Iniciar Nueva Partida");
        startButton.setFocusPainted(false);
        startButton.setBackground(Color.lightGray);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "",
                TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION
            ),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        startButton.addActionListener(e -> {
            VentanaAjedrez ventana = new VentanaAjedrez();
            ventana.setVisible(true);
            dispose();
        });

        JButton loadButton = new JButton("Cargar Partida");
        loadButton.setFocusPainted(false);
        loadButton.setBackground(Color.lightGray);
        loadButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loadButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "",
                TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION
            ),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
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

        JButton loadWithFEN = new JButton("Cargar Partida con FEN");
        loadWithFEN.setFocusPainted(false);
        loadWithFEN.setBackground(Color.lightGray);
        loadWithFEN.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loadWithFEN.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "",
                TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION
            ),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
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

        JButton exitButton = new JButton("Salir");
        exitButton.setFocusPainted(false);
        exitButton.setSelected(true);
        exitButton.setBackground(Color.lightGray);
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "",
                TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION
            ),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        // Add all buttons to the button panel
        buttonPanel.add(startButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(loadWithFEN);
        buttonPanel.add(exitButton);

        // Add the button panel to the center of the main panel
        panel.add(buttonPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater( () -> {
            new Menu().setVisible(true);
        });
    }

}
