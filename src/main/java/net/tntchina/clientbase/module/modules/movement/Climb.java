package net.tntchina.clientbase.module.modules.movement;

import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;

public class Climb extends Module {

	private static Climb climb;
	
	public Climb(String name, ModuleCategory categorys) {
		super(name, categorys);
		Climb.climb = this;
	}
	
	public static Climb getInstance() {
		return Climb.climb;
	}
	
	public boolean canClimb() {
		return this.mc.thePlayer.isCollidedHorizontally && this.getState();
	}
}
