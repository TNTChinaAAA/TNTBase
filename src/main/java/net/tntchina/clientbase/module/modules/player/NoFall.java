package net.tntchina.clientbase.module.modules.player;

import java.util.Arrays;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.tntchina.clientbase.event.EventTarget;
import net.tntchina.clientbase.event.events.MotionUpdateEvent;
import net.tntchina.clientbase.event.events.PacketEvent;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.clientbase.value.values.ModeValue;

public class NoFall extends Module {
	
	private final ModeValue mode = new ModeValue("Vanalia", "Mode", Arrays.asList("Vanalia", "Packet", "Hypixel", "AAC", "NoGround"));
	
	public NoFall(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	public String getMode() {
		return this.mode.getObject();
	}
	
	public boolean hasMode() {
		return true;
	}
	
	@EventTarget
	public void onMotion(MotionUpdateEvent event) {
		if (!this.getState() | !this.isModeEqual("AAC")) {
			return;
		}
		
		if (this.mc.thePlayer.onGround && !this.mc.thePlayer.isInLava() && !this.mc.thePlayer.isOnLadder() && !this.mc.thePlayer.isInWater()) {
			this.mc.thePlayer.motionY = -256;
		}
	}
	 
	@EventTarget
	public void onPacket(PacketEvent event) {
		if (!this.getState() | (!this.isModeEqual("Packet") && !this.isModeEqual("Hypixel"))) {
			return;
		}
		
		if (this.mc.thePlayer == null | this.mc.theWorld == null) {
			return;
		}
		
		if (this.mc.thePlayer.fallDistance > 3.0F && !this.mc.thePlayer.onGround) {
			if (event.getPacket() instanceof C03PacketPlayer) {
				C03PacketPlayer packet = (C03PacketPlayer) event.getPacket();
				packet.onGround = true;
			}
		}
	}
	
	public void onUpdate() {
		if (this.getState()) {
			switch (this.mode.getObject()) {
				case "AAC": {
					break;
				}
				
				case "Packet": {
					break;
				}
				
				case "Vanalia": {
					this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
					break;
				}
				
				case "NoGround": {
					this.onGround();
					break;
				}
				
				default: {
					break;
				}
			}
		}
	}
	
	public void noGround() {
		if (!this.onGround()) {
			this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
		}
	}
	
	public boolean onGround() {
		return this.mc.thePlayer.onGround && !this.mc.thePlayer.isOnLadder() && !this.mc.thePlayer.isInLava() && !this.mc.thePlayer.isInWater();
	}
	
	public boolean isModeEqual(String mode) {
		if (this.mode.getObject().equalsIgnoreCase(mode)) {
			return true;
		} else {
			return false;
		}
	}
}