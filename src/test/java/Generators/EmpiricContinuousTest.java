package Generators;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class EmpiricContinuousTest {

    @Test
    void testConstructorWithValidProbabilities() {

        ArrayList<EmpiricData<Double>> list = new ArrayList<>();
        list.add(new EmpiricData<>(0.1, 0.2, 0.2));
        list.add(new EmpiricData<>(0.2, 0.4, 0.2));
        list.add(new EmpiricData<>(0.4, 1000.0, 0.6));

        assertDoesNotThrow(() -> new EmpiricContinuous(list, 0));
    }

    @Test
    void testConstructorWithInvalidProbabilities() {
        ArrayList<EmpiricData<Double>> list = new ArrayList<>();
        list.add(new EmpiricData<>(0.1, 0.2, 0.3));
        list.add(new EmpiricData<>(0.2, 0.4, 0.3));
        list.add(new EmpiricData<>(0.4, 1000.0, 0.3)); // Sum = 0.9 (invalid)

        Exception exception = assertThrows(IllegalArgumentException.class, () -> new EmpiricContinuous(list, 0));
        assertEquals("Probabilities are not correct, not Equals to 1.", exception.getMessage());
    }

    @Test
    void testGenerateValue() {
        ArrayList<EmpiricData<Double>> list = new ArrayList<>();
        list.add(new EmpiricData<>(0.1, 0.2, 0.2));
        list.add(new EmpiricData<>(0.2, 0.4, 0.2));
        list.add(new EmpiricData<>(0.4, 180.0, 0.6));

        EmpiricContinuous empiricContinuous = new EmpiricContinuous(list, 0);

        for (int i = 0; i < 1000; i++) {
            double value = empiricContinuous.sample();
            assertTrue(value >= 0.1 && value <= 180.0, "Generated value should be within the defined range");
        }
    }

    @Test
    void testGenerateValueWithProbabilities() {
        ArrayList<EmpiricData<Double>> list = new ArrayList<>();
        list.add(new EmpiricData<>(0.1, 0.2, 0.2));
        list.add(new EmpiricData<>(0.2, 0.4, 0.2));
        list.add(new EmpiricData<>(0.4, 0.5, 0.6));

        EmpiricContinuous empiricContinuous = new EmpiricContinuous(list, 0);

        for (int i = 0; i < 1000; i++) {
            double value = empiricContinuous.sampleWithProb(0.2);
            assertTrue(value >= 0.2 && value <= 0.4, "Generated value should be within the defined range");
        }


    }



}