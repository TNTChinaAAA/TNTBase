package net.tntchina.clientbase.module.modules.movement;

import net.minecraft.block.BlockWeb;
import net.minecraft.util.AxisAlignedBB;
import net.tntchina.clientbase.event.EventTarget;
import net.tntchina.clientbase.event.events.BlockBBEvent;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;

public class NoWeb extends Module {

	public NoWeb(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	public void onUpdate() {
		if (this.getState()) {
			if (this.mc.thePlayer.isInWeb) {
				this.mc.thePlayer.onGround = false;
				this.mc.thePlayer.isInWeb = false;
			}
		}
	}
	
	@EventTarget
	public void onBB(BlockBBEvent event) {
		if (this.getState() && event.getBlock() instanceof BlockWeb) {
			event.setAxisAlignedBB(new AxisAlignedBB(event.getPos(), event.getPos().add(1, 0.75, 1)));
		}
	}
}
