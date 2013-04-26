package edu.unm.cs523.gasteriods.math;

import java.util.*;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Jason Loyd
 */
public class GenerateRandomInteger {
    private static final Random RAND = new Random(System.currentTimeMillis());

    @Test
    public void testRandomIntegerGeneration() {
        assertTrue(RAND.nextInt(5) <= 5);
    }
}
