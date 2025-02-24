package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.


public class GrafGUI extends JFrame {

    public GrafGUI(BuffonNeedle buffonNeedle) {
        setTitle("Stĺpcový graf");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JFreeChart chart = ChartFactory.createLineChart(
                "Moje dáta",    // Názov grafu
                "X os",         // Popis X osi
                "Hodnota",      // Popis Y osi
                buffonNeedle.dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        setContentPane(chartPanel);
    }

    public static void main(String[] args) {


            int expCount = 100000;
            Random rand = new Random();
            Random rand2 = new Random();
            int l = 5;
            int d = 10;
            BuffonNeedle buffonNeedle = new BuffonNeedle(rand, rand2, d, l);

            buffonNeedle.runSimulation(expCount);
            SwingUtilities.invokeLater(() -> {

                GrafGUI frame = new GrafGUI(buffonNeedle);
                frame.setVisible(true);
            });

    }
}



