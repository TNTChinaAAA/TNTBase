package net.tntchina.util;

public class Timer2 {
	
    private long prevMS = 0;

    public boolean delay(float milliSec) {
        return (float)(this.getTime() - this.prevMS) >= milliSec;
    }

    public void reset() {
        this.prevMS = this.getTime();
    }

    public long getTime() {
        return System.nanoTime() / 1000000;
    }

    public long getDifference() {
        return this.getTime() - this.prevMS;
    }

    public void setDifference(long difference) {
        this.prevMS = this.getTime() - difference;
    }
    public boolean hasReach(float mil) {
        return getTime() >= (mil);
    }

    public boolean hasReach(double mil) {
        return getTime() >= (mil);
    }
}