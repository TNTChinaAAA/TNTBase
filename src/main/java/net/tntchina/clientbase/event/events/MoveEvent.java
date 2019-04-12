package net.tntchina.clientbase.event.events;

import net.tntchina.clientbase.event.*;

/**
 * on the player move invoke the event(������ƶ���ʱ����õ��¼�)
 * @author TNTChina
 */
public class MoveEvent extends Event {
    
	public double x;
	public double y;
	public double z;

    public MoveEvent(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @return the player motionX(���ʵ���X)
     */
    public double getMotionX() {
        return this.x;
    }

    /**
     * @return the player motionY(���ʵ���T)
     */
    public double getMotionY() {
        return this.y;
    }

    /**
     * @return the player motionZ(���ʵ���Z)
     */
    public double getMotionZ() {
        return this.z;
    }

    /**
     * set the player motionX(������ҵ�ʵ��X)
     * @param motionX
     */
    public void setMotionX(final double motionX) {
        this.x = motionX;
    }

    /**
     * set the player motionY(������ҵ�ʵ��Y)
     * @param motionY
     */
    public void setMotionY(final double motionY) {
        this.y = motionY;
    }

    /**
     * set the player motionZ(������ҵ�ʵ��Z)
     * @param motionZ
     */
    public void setMotionZ(final double motionZ) {
        this.z = motionZ;
    }
}