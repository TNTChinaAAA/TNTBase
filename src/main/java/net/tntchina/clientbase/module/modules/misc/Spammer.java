package net.tntchina.clientbase.module.modules.misc;

import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.tntchina.clientbase.event.EventTarget;
import net.tntchina.clientbase.event.events.UpdateEvent;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.clientbase.value.values.NumberValue;
import net.tntchina.util.TimeHelper;

public class Spammer extends Module {
	
	private String message = "Debug aren't strong, but TNT are strong client!";
	public TimeHelper time = new TimeHelper();
	private NumberValue delay = new NumberValue(1000.0, "Delay", 3000.0, 0.0);
	
	public Spammer(String name, ModuleCategory categorys) {
		super(name, categorys);
		this.time.setLastMS();
	}

	public static int getRandom(int min, int max) {
		Random rand = new Random();
		return min + rand.nextInt(max - min + 1);
	}

	public String getMessage() {
		return this.message + " | >" + RandomStringUtils.randomAlphanumeric(8) + "<.";
	}

	@EventTarget
	public void onUpdate(UpdateEvent event) {
		if (this.getState() && event.getState() == UpdateEvent.State.PRE) {
			if (this.time.hasTimeReached(this.delay.getValue())) {
				this.mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(this.getMessage()));
				this.time.setLastMS();
			}
		}
	}
}