package org.example;


import GUI.MonteCarloGUI;
import Generators.Exponential;
import Generators.Triangular;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        /*SwingUtilities.invokeLater(() -> {
            MonteCarloGUI gui = new MonteCarloGUI();
            gui.setVisible(true);
            System.out.println("GUI executed");
        });*/


        /**
         * Tests of expo and triangular
         */

        /*Exponential exp = new Exponential(10.0);
        ArrayList<Double> values = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            values.add(exp.sample());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("triangular_data.txt"))) {
            for (double value : values) {
                writer.write(Double.toString(value));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        /*Triangular triangular = new Triangular(10.0, 100.0, 50.0);
        ArrayList<Double> values = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            values.add(triangular.sample());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("triangular_data.txt"))) {
            for (double value : values) {
                writer.write(Double.toString(value));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
}



