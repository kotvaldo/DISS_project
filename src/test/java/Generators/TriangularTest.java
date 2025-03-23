package Generators;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TriangularTest {
    @Test
    void generateTriangularFile() {
        Triangular triangular = new Triangular(10.0, 100.0, 50.0);
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
        }
    }

}
