package net.tntchina.clientbase.module.modules.combat;

import java.util.Arrays;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.tntchina.clientbase.event.EventTarget;
import net.tntchina.clientbase.event.events.PacketEvent;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.clientbase.value.values.ModeValue;
import net.tntchina.util.PlayerUtils;

public class Critical extends Module {

	public ModeValue mode = new ModeValue("Packet", "Mode", Arrays.asList("Packet", "MiniJump"));
	
	public Critical(String name, int key, ModuleCategory Categorys) {
		super(name, key, Categorys);
	}
	
	public String getMode() {
		return this.mode.getObject();
	}
	
	public boolean hasMode() {
		return true;
	}
	
    @EventTarget
    public void onSendPacket(PacketEvent event) {
    	if (this.getState() && this.mc.thePlayer != null && !this.mc.thePlayer.dead) {
    		String mode = this.mode.getObject();

    		if (this.canCrit()) {
    			if (event.getPacket() instanceof C02PacketUseEntity) {
    				C02PacketUseEntity packet = (C02PacketUseEntity) event.getPacket();

    				if (packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
    					if (mode.equalsIgnoreCase("Packet")) {
                        	this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + .1625, this.mc.thePlayer.posZ, false));
                            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
                            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 4.0E-6, this.mc.thePlayer.posZ, false));
                            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
                            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0E-6, this.mc.thePlayer.posZ, false));
                            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
                            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
    					}
    				}
    			}

    			if (mode.equalsIgnoreCase("MiniJump")) {
    				this.mc.thePlayer.jump();
    				this.mc.thePlayer.motionY -= 0.30000001192092879;
    			}
    		}
        }
	}

	private boolean canCrit() {
		return this.mc.thePlayer != null && !PlayerUtils.isInLiquid() && this.mc.thePlayer.onGround && this.getState();
	}

	public void Criticals() {
		if (this.getState()) {
			if (this.mc.objectMouseOver.typeOfHit == MovingObjectType.ENTITY) {
				this.mc.thePlayer.setSprinting(true);
			}
			
			if (this.mc.thePlayer.onGround) {
				this.mc.thePlayer.onGround = false;
				this.mc.thePlayer.motionY = 0.100000001490116;
				this.mc.thePlayer.onGround = true;
			}

			if (this.mc.thePlayer.onGround && this.mc.gameSettings.keyBindAttack.isPressed()) {
				C03PacketPlayer packet = new C03PacketPlayer();
				packet.y += 0.2;
				this.mc.getNetHandler().addToSendQueue(packet);

				if (this.timeUtil.hasTimeReached(100F)) {
					C03PacketPlayer pack = new C03PacketPlayer();
					pack.y -= 0.1;
					this.mc.getNetHandler().addToSendQueue(pack);
					this.mc.thePlayer.motionY -= 0.1;
					this.mc.thePlayer.setSprinting(true);
				}

				this.timeUtil.reset();
			}
		}
	}
}