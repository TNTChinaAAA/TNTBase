package net.tntchina.clientbase.module.modules.player;

import net.minecraft.network.play.client.*;
import net.tntchina.clientbase.module.*;

public class AntiFire extends Module {

	public AntiFire(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	public void onUpdate() {
		if (!this.getState() | (this.mc.thePlayer.capabilities.isCreativeMode && !this.mc.thePlayer.onGround && !this.mc.thePlayer.isBurning())) {
			return;
		}
		
		for (int i = 0; i < 100; i++) {
			this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
		}
	}
}