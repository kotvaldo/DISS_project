package org.example;


import GUI.MonteCarloGUI;
import MonteCarlo.MonteCarlo;
import Strategy.StrategyA;
import Strategy.StrategyB;
import Strategy.StrategyC;

import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {


        SwingUtilities.invokeLater(() -> {
            MonteCarloGUI gui = new MonteCarloGUI();
            gui.setVisible(true);
            System.out.println("GUI executed");
        });

    }
}



