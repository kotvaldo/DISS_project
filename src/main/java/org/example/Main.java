package org.example;


import GUI.EventSimulationGUI;
//import GUI.MonteCarloGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EventSimulationGUI gui = new EventSimulationGUI();
            gui.setVisible(true);
            System.out.println("GUI executed");
        });
    }
}



