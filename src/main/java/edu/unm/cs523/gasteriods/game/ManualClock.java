package edu.unm.cs523.gasteriods.game;

/**
 * A Clock that is manually updated.  This allows the system to calculate the game state based on a set time period,
 * without having to wait the given amount of time.
 *
 * @author John Ericksen
 */
public class ManualClock implements Clock{

    private long time;

    public ManualClock(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    /**
     * Increase the clock by the given amount.
     * @param add increase by
     */
    public void addTime(long add){
        time += add;
    }
}
