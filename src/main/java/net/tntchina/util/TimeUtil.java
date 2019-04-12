package net.tntchina.util;

public class TimeUtil {
	
	private long lastMS = 0L;
	private long prevMS = 0L;
	
	public TimeUtil(){
		this.prevMS = 0L;
	}
	
    public boolean hasTimeReached(double delay) {
        return System.currentTimeMillis() - this.lastMS >= delay;
    }
    
	public boolean isDelayComplete(double delay) {
		if(System.currentTimeMillis() - this.lastMS >= delay) {
			return true;
		}
		
		return false;
	}
	
	public long getCurrentMS(){
		return System.nanoTime() / 1000000L;
	}
	
	public void setLastMS(long lastMS) {
		this.lastMS = lastMS;
	}
	
	public void setLastMS() {
		this.lastMS = System.currentTimeMillis();
	}
	
	public int convertToMS(int d) {
		return 1000 / d;
	}

	public void reset(){
		this.lastMS = this.getCurrentMS();
	}
	
	public boolean delay(float milliSec){
		return (float)(this.getTime() - this.prevMS) >= milliSec;
	}
	
	private long getTime(){
		return System.nanoTime() / 1000000L;
	}

	public long getLastMS() {
		return this.lastMS;
	}
}
