package Generators;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class EmpiricDiscreteTest {

    @Test
    void testConstructorWithValidProbabilities() {

        ArrayList<EmpiricData<Integer>> list = new ArrayList<>();
        list.add(new EmpiricData<>(1, 2, 0.2));
        list.add(new EmpiricData<>(2, 3, 0.2));
        list.add(new EmpiricData<>(4, 5, 0.6));

        assertDoesNotThrow(() -> new EmpiricDiscrete(list));
    }

    @Test
    void testConstructorWithInvalidProbabilities() {
        ArrayList<EmpiricData<Integer>> list = new ArrayList<>();
        list.add(new EmpiricData<>(1, 2, 0.3));
        list.add(new EmpiricData<>(2, 3, 0.3));
        list.add(new EmpiricData<>(4, 5, 0.3)); // Sum = 0.9 (invalid)

        Exception exception = assertThrows(IllegalArgumentException.class, () -> new EmpiricDiscrete(list));
        assertEquals("Probabilities are not correct, not Equals to 1.", exception.getMessage());
    }

    @Test
    void testGenerateValue() {

        ArrayList<EmpiricData<Integer>> list = new ArrayList<>();
        list.add(new EmpiricData<>(1, 2, 0.2));
        list.add(new EmpiricData<>(2, 3, 0.2));
        list.add(new EmpiricData<>(4, 6, 0.6));

        EmpiricDiscrete empiricContinuous = new EmpiricDiscrete(list);


        double value = empiricContinuous.sample();

        assertTrue(value >= 1 && value < 5, "Generated value should be within the defined range");
    }

    @Test
    void testGenerateValueWithSeed() {

        ArrayList<EmpiricData<Integer>> list = new ArrayList<>();
        list.add(new EmpiricData<>(0, 10000000, 1.0));

        EmpiricDiscrete empiricContinuous1 = new EmpiricDiscrete(list, 42); // Seed = 42
        EmpiricDiscrete empiricContinuous2 = new EmpiricDiscrete(list, 42); // Same seed

        double value1 = empiricContinuous1.sample();
        double value2 = empiricContinuous2.sample();


        assertNotEquals(value1, value2, "Values should not be the same, because we have more than one Random generator");
    }
}