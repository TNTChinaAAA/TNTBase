package net.tntchina.clientbase.module.modules.movement;

import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.clientbase.value.values.NumberValue;

public class HighJump extends Module {
	
	public NumberValue jumpHeight = new NumberValue(5.0, "JumpHeight", 555, 0.42 * 5);
	
	public HighJump(String name, ModuleCategory categorys) {
		super(name, categorys);
	}

	public float getJumpHigh() {
		return (float) (this.jumpHeight.getObject().doubleValue() / 5);
	}
}
