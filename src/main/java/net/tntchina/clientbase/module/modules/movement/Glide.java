package net.tntchina.clientbase.module.modules.movement;

import net.tntchina.clientbase.event.EventTarget;
import net.tntchina.clientbase.event.events.MotionUpdateEvent;
import net.tntchina.clientbase.event.events.MoveEvent;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.clientbase.module.ModuleManager;
import net.tntchina.clientbase.value.values.BooleanValue;
import net.tntchina.clientbase.value.values.NumberValue;
import net.tntchina.util.Helper;

public class Glide extends Module {
   
	public NumberValue glideSpeed = new NumberValue(0.0, "Speed", 10.0, 0.0);
    public NumberValue verticalSpeed = new NumberValue(0.0, "Vertical Speed", 10.0, 0.0);
    public NumberValue horizontalSpeed = new NumberValue(0.301, "Horizontal Speed", 10.0, 0.0);
    public BooleanValue lock = new BooleanValue(true, "Lock");
    public BooleanValue lemon = new BooleanValue(true, "Lemon");
    private double maxPosY = 0.0;

	public Glide(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
    
    public void onEnable() {
		if (this.lemon.getObject().booleanValue()) {
			this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.2D, this.mc.thePlayer.posZ);
		}
		
        this.maxPosY = this.mc.thePlayer.posY;
    }

    @EventTarget
    public void onEvent(MotionUpdateEvent pre) {
    	//boolean isGliding = false;
        
        if (!this.mc.thePlayer.isEntityAlive() | !this.getState()) {
            return;
        }
        
        boolean shouldBlock = this.mc.thePlayer.posY + 0.1 >= this.maxPosY && this.mc.gameSettings.keyBindJump.isPressed() && this.lock.getObject().booleanValue();
       // isGliding = !this.mc.thePlayer.onGround && !this.mc.thePlayer.isCollidedVertically;
        
        if (this.mc.thePlayer.isSneaking()) {
            this.mc.thePlayer.motionY = - this.verticalSpeed.getValue();
        } else if (this.mc.gameSettings.keyBindJump.isPressed() && !shouldBlock) {
            this.mc.thePlayer.motionY = this.verticalSpeed.getValue();
        } else {
            double speed = this.glideSpeed.getValue();
           // double x = 0.0;
           // x += 1.0;
            
            if (Helper.blockUtils().isInsideBlock()) {
                speed = 0.0;
            }
            
            this.mc.thePlayer.motionY = - speed;
            
            if (this.lemon.getObject().booleanValue() && this.mc.thePlayer.ticksExisted % 2 == 0) {
                this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0E-12, this.mc.thePlayer.posZ);
            }
        }
    }

    @EventTarget
    public void onMove(MoveEvent event) {
        if (ModuleManager.getModule(Speed.class).getState() | !this.getState()) {
            return;
        }
        
        double forward = this.mc.thePlayer.movementInput.moveForward;
        double strafe = this.mc.thePlayer.movementInput.moveStrafe;
        float yaw = this.mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            event.x = 0.0;
            event.z = 0.0;
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            event.x = forward * this.horizontalSpeed.getValue() * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * this.horizontalSpeed.getValue() * Math.sin(Math.toRadians(yaw + 90.0f));
            event.z = forward * this.horizontalSpeed.getValue() * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * this.horizontalSpeed.getValue() * Math.cos(Math.toRadians(yaw + 90.0f));
        }
    }
}