package net.tntchina.clientbase.event.events;

import net.tntchina.clientbase.event.*;

/**
 * on the player update living invoke event.(����Ҹ��»���ʵ��ʱ���õ��¼�)
 * @author TNTChina
 */
public class LivingUpdateEvent extends Event {
	
	private State state;// bound(λ��)
	
	public LivingUpdateEvent(State state) {
		this.state = state;
	}
	
	/**
	 * @return invoke the event state.(��õ��ô��¼���λ��)
	 */
	public State getState() {// ��õ��ô��¼���λ��
		return this.state;
	}
	
	public static enum State {
		/**
		 *  the bound(λ�� ���� �ж� ���)
		 */
		PRE, INIT, POST;
	}
}
