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

        EmpiricDiscrete empiricDiscrete = new EmpiricDiscrete(list);

        for (int i = 0; i < 1000; i++) {
            int value = empiricDiscrete.sample();
            assertTrue(value >= 1 && value <= 6, "Generated value should be within the defined range");
        }
    }

    @Test
    void testGenerateValueWithProbabilities() {
        ArrayList<EmpiricData<Integer>> list = new ArrayList<>();
        list.add(new EmpiricData<>(1, 2, 0.2));
        list.add(new EmpiricData<>(3, 5, 0.2));
        list.add(new EmpiricData<>(6, 7, 0.6));

        EmpiricDiscrete empiricDiscrete = new EmpiricDiscrete(list);

        for (int i = 0; i < 1000; i++) {
            int value = empiricDiscrete.sampleWithProb(0.4);
            assertTrue(value >= 6 && value <= 7, "Generated value should be within the defined range");
        }


    }


}