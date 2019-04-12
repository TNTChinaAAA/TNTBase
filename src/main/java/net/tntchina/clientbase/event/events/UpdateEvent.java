package net.tntchina.clientbase.event.events;

import net.tntchina.clientbase.event.Event;

/**
 * on the player update invoke event.(����Ҹ�����Ϣ��ʱ����õ��¼�)
 * @author TNTChina
 */
public class UpdateEvent extends Event {
	
	private State state;
	
	public UpdateEvent(State state) {
		this.state = state;
	}
	
	public State getState() {
		return this.state;
	}
	
	public static enum State {
		PRE, POST;
	}

	public boolean onPost() {
		return this.state == State.POST;
	}
	
	public boolean onPre() {
		return this.state == State.PRE;
	}
}