package edu.unm.cs523.gasteriods.game;

/**
 * Clock that uses the System.currentTimeMillis() clock as a source for time.
 *
 * @author John Ericksen
 */
public class SystemClock implements Clock {

    public long getTime() {
        return System.currentTimeMillis();
    }
}
