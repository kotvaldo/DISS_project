package Generators;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

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
        list.add(new EmpiricData<>(0.4, 0.5, 0.6));

        EmpiricContinuous empiricContinuous = new EmpiricContinuous(list, 0);


        double value = empiricContinuous.sample();

        assertTrue(value >= 0.1 && value < 1000.0, "Generated value should be within the defined range");
    }

    @Test
    void testGenerateValueWithSeed() {

        ArrayList<EmpiricData<Double>> list = new ArrayList<>();
        list.add(new EmpiricData<>(0.1, 0.2, 0.2));
        list.add(new EmpiricData<>(0.2, 0.4, 0.2));
        list.add(new EmpiricData<>(0.4, 1000.0, 0.6));

        EmpiricContinuous empiricContinuous1 = new EmpiricContinuous(list, 42); // Seed = 42
        EmpiricContinuous empiricContinuous2 = new EmpiricContinuous(list, 42); // Same seed

        double value1 = empiricContinuous1.sample();
        double value2 = empiricContinuous2.sample();


        assertNotEquals(value1, value2, "Values should not be the same, because we have more than one Random generator");
    }
}