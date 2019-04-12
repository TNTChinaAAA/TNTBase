package net.tntchina.clientbase.module.modules.combat;

import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.clientbase.value.values.NumberValue;

public class Reach extends Module {

	private NumberValue reach = new NumberValue(5.5, "Distance", 7.0, 5.0);
	
	public Reach(String name, ModuleCategory categorys) {
		super(name, categorys);
	}

	public float getValue() {
		return (float) this.reach.getValue();
	}
	
	public String getMode() {
		return ("" + this.getValue());
	}

	public boolean hasMode() {
		return true;
	}
}