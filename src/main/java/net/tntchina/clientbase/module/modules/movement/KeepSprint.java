package net.tntchina.clientbase.module.modules.movement;

import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;

public class KeepSprint extends Module {

	public KeepSprint(String name, int key, ModuleCategory Categorys) {
		super(name, key, Categorys);
	}

	public void onUpdate() {
		if (!this.getState() | this.mc.thePlayer == null) {
			return;
		}
		
		if (this.mc.gameSettings.keyBindForward.isKeyDown()) {
			this.setSprinting();
		}
	}
}
