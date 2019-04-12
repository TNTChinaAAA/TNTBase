package net.tntchina.clientbase.module.modules.movement;

import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;

public class Sprint extends Module {

	public Sprint(String name, int key, ModuleCategory Categorys) {
		super(name, key, Categorys);
	}

	public void onUpdate() {
		if (!this.getState() | this.mc.thePlayer == null) {
			return;
		}

		if (this.mc.thePlayer.moveForward > 0.0) {
			this.setSprinting();
		} else {
			this.setSprinting(false);
		}
	}
}
