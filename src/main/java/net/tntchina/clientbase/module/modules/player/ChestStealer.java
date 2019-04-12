package net.tntchina.clientbase.module.modules.player;

import net.minecraft.inventory.ContainerChest;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.clientbase.value.values.BooleanValue;
import net.tntchina.clientbase.value.values.NumberValue;
import net.tntchina.util.TimeHelper;

public class ChestStealer extends Module {

	public NumberValue delay = new NumberValue(200, "Delay", 1000, 0.0);
	public BooleanValue insant = new BooleanValue(false, "Insant");
	public TimeHelper time = new TimeHelper();
	
	public ChestStealer(String name, ModuleCategory categorys) {
		super(name, categorys);
	}

	public void onUpdate() {
		if (this.getState()) {
			if (this.mc.thePlayer.openContainer != null && this.mc.thePlayer.openContainer instanceof ContainerChest) {
				ContainerChest container = (ContainerChest) this.mc.thePlayer.openContainer;
				boolean empty = true;
				
				for (int i = 0; i < container.getLowerChestInventory().getSizeInventory(); i++) {
					if (container.getLowerChestInventory().getStackInSlot(i) != null) {
						empty = false;
						
						if (this.insant.getBooleanValue() || this.time.isDelayComplete(this.delay.getDoubleValue())) {
							this.mc.playerController.windowClick(container.windowId, i, 0, 1, this.mc.thePlayer);
							this.time.setLastMS();
						}
					}
				}
				
				if (empty) {
					this.mc.thePlayer.closeScreen();
				}
			}
		}
	}
}