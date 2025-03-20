package Generators;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
class SeedGeneratorTest {

    @Test
    void testSeedsNotEqual() {
        ArrayList<Integer> listOfSeeds = new ArrayList<>();
        int numberOfSeeds = 20;
        SeedGenerator seedGenerator = new SeedGenerator();

        for (int i = 0; i < numberOfSeeds; i++) {
            listOfSeeds.add(seedGenerator.sampleSeed());
        }


        HashSet<Integer> uniqueSeeds = new HashSet<>(listOfSeeds);
        assertEquals(numberOfSeeds, uniqueSeeds.size(), "All seeds should be unique");
    }
}