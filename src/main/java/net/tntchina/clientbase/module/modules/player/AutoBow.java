package net.tntchina.clientbase.module.modules.player;

import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;

public class AutoBow extends Module {

	public AutoBow(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	public void onUpdate() {
		if (this.getState()) {
			for (int i = 0; i < 36; i++) {
				if (this.mc.thePlayer.inventory.getStackInSlot(i) == null) {
					continue;
				}
				
				ItemStack stack = this.mc.thePlayer.inventory.getStackInSlot(i);
				
				if (stack.getItem() instanceof ItemBow) {
					stack.getItem().onPlayerStoppedUsing(stack, this.mc.theWorld, this.mc.thePlayer, 4000);
				}
			}
		}
	}
}
