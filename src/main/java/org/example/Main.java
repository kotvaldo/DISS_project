package org.example;


import GUI.MonteCarloGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MonteCarloGUI gui = new MonteCarloGUI();
            gui.setVisible(true);
            System.out.println("GUI executed");
        });
    }
}



