package Generators;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class ExponentialTest {
    @Test
    void generateExponential() {
        Exponential exp = new Exponential(10.0);
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
        }
    }
}
