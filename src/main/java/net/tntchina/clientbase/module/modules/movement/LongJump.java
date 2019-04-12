package net.tntchina.clientbase.module.modules.movement;

import net.minecraft.potion.Potion;
import net.tntchina.clientbase.event.EventTarget;
import net.tntchina.clientbase.event.events.MotionUpdateEvent;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;

public class LongJump extends Module {

	public LongJump(String name, ModuleCategory categorys) {
		super(name, categorys);
	}

	@EventTarget
	public void onPostMotion(MotionUpdateEvent event) {
		if (event.getState() != MotionUpdateEvent.State.POST | !this.getState() | this.mc.thePlayer == null | this.mc.theWorld == null) {
			return;
		}

		if ((this.mc.gameSettings.keyBindForward.isKeyDown() || this.mc.gameSettings.keyBindLeft.isKeyDown() || this.mc.gameSettings.keyBindRight.isKeyDown() || this.mc.gameSettings.keyBindBack.isKeyDown()) && this.mc.gameSettings.keyBindJump.isKeyDown()) {
			float dir = this.mc.thePlayer.rotationYaw + ((this.mc.thePlayer.moveForward < 0) ? 180 : 0) + ((this.mc.thePlayer.moveStrafing > 0) ? (-90F * ((this.mc.thePlayer.moveForward < 0) ? -.5F : ((this.mc.thePlayer.moveForward > 0) ? .4F : 1F))) : 0);
			float xDir = (float) Math.cos((dir + 90F) * Math.PI / 180);
			float zDir = (float) Math.sin((dir + 90F) * Math.PI / 180);
			
			if (this.mc.thePlayer.isCollidedVertically && (this.mc.gameSettings.keyBindForward.isKeyDown() || this.mc.gameSettings.keyBindLeft.isKeyDown() || this.mc.gameSettings.keyBindRight.isKeyDown() || this.mc.gameSettings.keyBindBack.isKeyDown()) && this.mc.gameSettings.keyBindJump.isKeyDown()) {
				this.mc.thePlayer.motionX = xDir * .29F;
				this.mc.thePlayer.motionZ = zDir * .29F;
			}
			
			if (this.mc.thePlayer.motionY == 0.33319999363422365 && (this.mc.gameSettings.keyBindForward.isKeyDown() || this.mc.gameSettings.keyBindLeft.isKeyDown() || this.mc.gameSettings.keyBindRight.isKeyDown() || this.mc.gameSettings.keyBindBack.isKeyDown())) {
				if (this.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
					this.mc.thePlayer.motionX = xDir * 1.34;
					this.mc.thePlayer.motionZ = zDir * 1.34;
				} else {
					this.mc.thePlayer.motionX = xDir * 1.261;
					this.mc.thePlayer.motionZ = zDir * 1.261;
				}
			}
		}
	}
}
