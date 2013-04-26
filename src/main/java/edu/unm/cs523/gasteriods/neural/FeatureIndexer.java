package edu.unm.cs523.gasteriods.neural;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Stores and iterates a unique index.  Each call to getIndex() returns a unique value.
 *
 * @author John Ericksen
 */
public class FeatureIndexer {

    private static final AtomicInteger value = new AtomicInteger(0);

    private FeatureIndexer(){
        // private utility class constructor.
    }

    public static int getIndex(){
        return value.addAndGet(1);
    }
}
