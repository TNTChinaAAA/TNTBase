package net.tntchina.clientbase.module.modules.player;

import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;

public class NoHungry extends Module {

	public NoHungry(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	public void onUpdate() {
		if (this.getState() && this.mc.thePlayer.getFoodStats().getFoodLevel() < 20) {
			this.mc.thePlayer.getFoodStats().setFoodLevel(20);
		}
	}
}
