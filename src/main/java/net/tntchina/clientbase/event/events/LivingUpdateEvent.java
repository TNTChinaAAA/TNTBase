package net.tntchina.clientbase.event.events;

import net.tntchina.clientbase.event.*;

/**
 * on the player update living invoke event.(在玩家更新会活动的实体时调用的事件)
 * @author TNTChina
 */
public class LivingUpdateEvent extends Event {
	
	private State state;// bound(位置)
	
	public LivingUpdateEvent(State state) {
		this.state = state;
	}
	
	/**
	 * @return invoke the event state.(获得调用此事件的位置)
	 */
	public State getState() {// 获得调用此事件的位置
		return this.state;
	}
	
	public static enum State {
		/**
		 *  the bound(位置 初期 中段 最后)
		 */
		PRE, INIT, POST;
	}
}
