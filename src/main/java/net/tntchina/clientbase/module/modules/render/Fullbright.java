package net.tntchina.clientbase.module.modules.render;

import net.minecraft.client.Minecraft;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;

public class Fullbright extends Module {

	public Fullbright(String name, ModuleCategory Categorys) {
		super(name, 0, Categorys);
	}
	
	public void onUpdate() {
		if (this.getState()) {
			Minecraft.getMinecraft().gameSettings.gammaSetting = 100.0F;
		}
	}
	
	public void onDisable() {
		Minecraft.getMinecraft().gameSettings.gammaSetting = 0.0F;
	}
}
