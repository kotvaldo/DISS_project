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


        StrategyB strategyB = new StrategyB();
        System.out.println("Spúšťam GUI...");
        SwingUtilities.invokeLater(() -> {
            MonteCarloGUI gui = new MonteCarloGUI(strategyB);
            gui.setVisible(true);
            System.out.println("GUI spustené.");
        });

    }
}



