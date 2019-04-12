package net.tntchina.clientbase.module.modules.movement;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.tntchina.clientbase.event.EventTarget;
import net.tntchina.clientbase.event.events.MotionUpdateEvent;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.util.MovementUtil;

public class Step extends Module {

	private MovementUtil movementUtil;

	public Step(String name, ModuleCategory categorys) {
		super(name, categorys);
		this.movementUtil = new MovementUtil();
	}
	
	public void onDisable() {
		this.mc.thePlayer.stepHeight = 0.5F;
	}

	@EventTarget
	public void onPostUpdate(MotionUpdateEvent event) {
		if (!this.getState() | this.mc.thePlayer == null | this.mc.theWorld == null | event.getState() != MotionUpdateEvent.State.POST) {
			return;
		}
		
		if (this.movementUtil.shouldStep() && this.mc.thePlayer.fallDistance < 0.1F) {
			this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.42F, this.mc.thePlayer.posZ, this.mc.thePlayer.onGround));
			this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.75F, this.mc.thePlayer.posZ, this.mc.thePlayer.onGround));
			this.mc.thePlayer.stepHeight = 1.0F;
		} else {
			this.mc.thePlayer.stepHeight = 0.5F;
		}
	}
}
