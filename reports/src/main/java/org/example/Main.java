package org.example;

import org.example.ui.MainWindow;

import javax.swing.*;

/**
 * Clase principal de la aplicación
 */
public class Main {

    public static void main(String[] args) {
        // Configurar Look and Feel del sistema
        configureLookAndFeel();

        // Iniciar la aplicación en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            mainWindow.display();
        });
    }

    /**
     * Configura el Look and Feel del sistema operativo
     */
    private static void configureLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("No se pudo configurar el Look and Feel del sistema: " + e.getMessage());
        }
    }
}
