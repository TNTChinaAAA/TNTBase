package net.tntchina.clientbase.module.modules.render;

import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;

public class NotViewBob extends Module {

	public NotViewBob(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	public void onUpdate() {
		if (this.getState()) {
			this.mc.gameSettings.viewBobbing = false;
		}
	}
}
