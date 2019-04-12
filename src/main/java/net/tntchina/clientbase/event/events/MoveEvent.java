package net.tntchina.clientbase.event.events;

import net.tntchina.clientbase.event.*;

/**
 * on the player move invoke the event(在玩家移动的时候调用的事件)
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
     * @return the player motionX(玩家实体的X)
     */
    public double getMotionX() {
        return this.x;
    }

    /**
     * @return the player motionY(玩家实体的T)
     */
    public double getMotionY() {
        return this.y;
    }

    /**
     * @return the player motionZ(玩家实体的Z)
     */
    public double getMotionZ() {
        return this.z;
    }

    /**
     * set the player motionX(设置玩家的实体X)
     * @param motionX
     */
    public void setMotionX(final double motionX) {
        this.x = motionX;
    }

    /**
     * set the player motionY(设置玩家的实体Y)
     * @param motionY
     */
    public void setMotionY(final double motionY) {
        this.y = motionY;
    }

    /**
     * set the player motionZ(设置玩家的实体Z)
     * @param motionZ
     */
    public void setMotionZ(final double motionZ) {
        this.z = motionZ;
    }
}