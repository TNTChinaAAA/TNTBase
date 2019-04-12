package net.tntchina.clientbase.event.events;

import net.tntchina.clientbase.event.Event;

/**
 * on the player update motion invoke event.(在玩家更新实体圆柱体的时候调用的事件)
 * @author TNTChina
 */
public class MotionUpdateEvent extends Event {

	private float yaw, pitch;
	private State state;
	private double x, y, z;
	public boolean onGround;
	
	public MotionUpdateEvent(State state, float yaw, float pitch, boolean onGround, double x, double y, double z) {
		this.state = state;
		this.yaw = yaw;
		this.pitch = pitch;
		this.x = x;
		this.y = y;
		this.z = z;
		this.onGround = onGround;
	}
	
	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public boolean onGround() {
		return this.onGround;
	}
	
	/**
	 * @return invoke the event state.(获得调用此事件的位置)
	 */
	public State getState() {
		return this.state;
	}
	
	public enum State {
		/**
		 *  the bound(位置 初期 最后)
		 */
		
		PRE, POST
	}

	public void setGround(boolean b) {
		this.onGround = b;
	}
	
	public boolean onPre() {
		return this.state == MotionUpdateEvent.State.PRE;
	}
	
	public boolean onPost() {
		return this.state == MotionUpdateEvent.State.POST;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setZ(double z) {
		this.z = z;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public double getZ() {
		return this.z;
	}
}
