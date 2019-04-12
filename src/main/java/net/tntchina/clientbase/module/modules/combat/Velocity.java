package net.tntchina.clientbase.module.modules.combat;

import Mixin.impl.IS12PacketEntityVelocity;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.tntchina.clientbase.event.EventTarget;
import net.tntchina.clientbase.event.events.PacketEvent;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;

public class Velocity extends Module {

	public Velocity(String name, int key, ModuleCategory Categorys) {
		super(name, key, Categorys);
	}
	
	public void onUpdate() {
		if (this.getState()) {
			if (this.mc.thePlayer.hurtTime > 5) {
				this.mc.thePlayer.onGround = true;
			}
		}
	}
	
	@EventTarget
	public void onVelocity(PacketEvent event) {
		if (this.getState() && this.mc.thePlayer != null && this.mc.theWorld != null) { 
			if (event.getPacket() instanceof S12PacketEntityVelocity) {
				S12PacketEntityVelocity velocity = (S12PacketEntityVelocity) event.getPacket();
				
				if (velocity.getEntityID() == this.mc.thePlayer.getEntityId()) {
					IS12PacketEntityVelocity packet = (IS12PacketEntityVelocity) velocity;
					packet.setX(0);
					packet.setY(0);
					packet.setZ(0);
				}
			}
			
			if (event.getPacket() instanceof S27PacketExplosion) {
				S27PacketExplosion packetExplosion = (S27PacketExplosion) event.getPacket();
				packetExplosion.field_149152_f = packetExplosion.field_149153_g = packetExplosion.field_149159_h = 0;
			}
		}
	}
}