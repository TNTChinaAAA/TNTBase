package net.tntchina.util;

public final class Timer {
	
	private long time;
	private long prevMS = 0;

	public Timer() {
		this.time = System.nanoTime() / 1000000L;
	}

	public boolean delay(float milliSec) {
		return (float) (this.getTime() - this.prevMS) >= milliSec;
	}

	public long getTime() {
		return System.nanoTime() / 1000000;
	}

	public boolean hasTimeElapsed(final long time, final boolean reset) {
		if (this.time() >= time) {
			if (reset)
				this.reset();
			return true;
		}
		return false;
	}

	public boolean hasTimeElapsed(final long time) {
		if (this.time() >= time)
			return true;
		return false;
	}

	public boolean hasTicksElapsed(int ticks) {
		if (this.time() >= (1000 / ticks) - 50)
			return true;
		return false;
	}

	public boolean hasTicksElapsed(int time, int ticks) {
		if (this.time() >= (time / ticks) - 50)
			return true;
		return false;
	}

	public long time() {
		return System.nanoTime() / 1000000L - this.time;
	}

	public void resetsig() {
		this.prevMS = this.getTime();
	}

	public void reset() {
		this.time = System.nanoTime() / 1000000L;
	}
}
