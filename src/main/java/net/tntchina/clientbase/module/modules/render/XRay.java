package net.tntchina.clientbase.module.modules.render;

import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;

public class XRay extends Module {

	public XRay(String name, int key, ModuleCategory category) {
		super(name, key, category);
	}
	
	public void onEnable() {
		this.mc.renderGlobal.loadRenderers();
	}
	
	public void onDisable() {
		this.mc.renderGlobal.loadRenderers();
	}
}
