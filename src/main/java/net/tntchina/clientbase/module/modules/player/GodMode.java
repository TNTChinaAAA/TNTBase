package net.tntchina.clientbase.module.modules.player;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;

public class GodMode extends Module {

	public GodMode(String name, ModuleCategory Categorys) {
		super(name, 0, Categorys);
	}
	
	public void onUpdate() {
		if (this.getState()) {
			this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 4.0D, this.mc.thePlayer.posZ, true));
		}
	}
}
