package Mixin.impl;

public interface IEntityPlayerSP {
	
	void onMoveEntity(double x, double y, double z);
	
	boolean moving();
	
	float getSpeed();
	
	void setSpeed(double speed);

	void setMoveSpeed(double speed);
	
	void setYaw(double yaw);
	
	void setPitch(double pitch);
	
	float getDirection();
}
