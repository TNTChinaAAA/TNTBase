package net.tntchina.clientbase.module.modules.world;

import Mixin.impl.IPlayerControllerMP;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;

public class FastBreak extends Module {

	public FastBreak(String name, int key, ModuleCategory Categorys) {
		super(name, key, Categorys);
	}

	public void onUpdate() {
		if (!this.getState() | this.mc.playerController == null | this.mc.theWorld == null) {
			return;
		}
		
		final IPlayerControllerMP playerControllerMP = (IPlayerControllerMP) this.mc.playerController;
		final float dd = playerControllerMP.getCurBlockDamageMP();
		
		if (dd >= 0.2) {
			playerControllerMP.setCurBlockDamageMP(dd + 0.2F);
		}
	}
}