package net.tntchina.clientbase.event.events;

/**
 * on has other entity attack the player invoke event.(��������ʵ�������ҵ�ʱ����Ӧ���¼�)
 * @author TNTChina
 */
public class VelocityEvent extends CancellableEvent {
	
	private double x, y, z;// Then attack add x y z.(������ʱ�����ӵ�x y z)
	
	public VelocityEvent(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * @return the entity attack the player add x(ʵ�幥����ҵ�ʱ�����ӵ�x)
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * set entity attack the player add x(���ñ�����ʱ���ӵ�X)
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return the entity attack the player add y(ʵ�幥����ҵ�ʱ�����ӵ�y)
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * set entity attack the player add y(���ñ�����ʱ���ӵ�y)
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * @return the entity attack the player add z(ʵ�幥����ҵ�ʱ�����ӵ�z)
	 */
	public double getZ() {
		return this.z;
	}

	/**
	 * set entity attack the player add z(���ñ�����ʱ���ӵ�z)
	 * @param z
	 */
	public void setZ(double z) {
		this.z = z;
	}
}