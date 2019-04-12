package net.tntchina.clientbase.module.modules.movement;

import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.clientbase.module.ModuleManager;
import net.tntchina.clientbase.value.values.BooleanValue;

public class NoSlow extends Module {

	public BooleanValue b = new BooleanValue(false, "Squat");
	
	public NoSlow(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	public static boolean li1i1ii1lilil() {
		return ModuleManager.getModule(NoSlow.class).b.getBooleanValue();
	}
}
