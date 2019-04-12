package net.tntchina.clientbase.event.events;

/**
 * on has other entity attack the player invoke event.(在有其他实体击退玩家的时候响应的事件)
 * @author TNTChina
 */
public class VelocityEvent extends CancellableEvent {
	
	private double x, y, z;// Then attack add x y z.(攻击的时候增加的x y z)
	
	public VelocityEvent(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * @return the entity attack the player add x(实体攻击玩家的时候增加的x)
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * set entity attack the player add x(设置被攻击时增加的X)
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return the entity attack the player add y(实体攻击玩家的时候增加的y)
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * set entity attack the player add y(设置被攻击时增加的y)
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * @return the entity attack the player add z(实体攻击玩家的时候增加的z)
	 */
	public double getZ() {
		return this.z;
	}

	/**
	 * set entity attack the player add z(设置被攻击时增加的z)
	 * @param z
	 */
	public void setZ(double z) {
		this.z = z;
	}
}