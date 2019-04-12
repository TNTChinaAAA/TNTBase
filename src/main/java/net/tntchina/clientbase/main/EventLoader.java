package net.tntchina.clientbase.main;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.tntchina.clientbase.event.EventListener;
import net.tntchina.clientbase.event.EventManager;
import net.tntchina.clientbase.event.EventTarget;
import net.tntchina.clientbase.event.events.AttackEntityEvent;

public class EventLoader implements EventListener {
	
	public Minecraft mc;
	
	public EventLoader() {
		EventLoader.this.mc = Minecraft.getMinecraft();
		EventManager.registerListener(EventLoader.this);
	}
	
	@EventTarget
	public void onAttack(AttackEntityEvent event) {
		if (event.getEntity().equals(this.mc.thePlayer)) {
			if (event.getAttackTarget() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.getAttackTarget();
				
				if (player.getGameProfile() != null) {
					GameProfile profile = player.getGameProfile();
					
					if (profile.equals(this.mc.thePlayer.getGameProfile())) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
}