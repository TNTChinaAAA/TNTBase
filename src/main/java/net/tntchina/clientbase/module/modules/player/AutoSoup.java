package net.tntchina.clientbase.module.modules.player;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;

public class AutoSoup extends Module {

	public AutoSoup(String name, ModuleCategory categorys) {
		super(name, categorys);
	}

	public void onUpdate() {
		if (!this.getState()) {
			return;
		}
		
		if (this.mc.thePlayer.getFoodStats().getFoodLevel() < 20 && !this.mc.thePlayer.capabilities.isCreativeMode) {
			if (!this.mc.thePlayer.inventory.hasItem(Item.getItemById(282))) {
				return;
			}
			
			this.Soup();
		}
	}

	public void Sword() {
		for (int slot = 44; slot >= 9; slot--) {
			ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(slot).getStack();

			if (stack != null) {
				if (this.mc.thePlayer.inventory.currentItem != 276 && this.mc.thePlayer.inventory.hasItem(Item.getItemById(276))) {
					if (slot >= 36 && slot <= 44) {
						if (Item.getIdFromItem(stack.getItem()) == 276) {
							this.mc.thePlayer.inventory.currentItem = slot - 36;
						}
					} else if (Item.getIdFromItem(stack.getItem()) == 276) {
						this.mc.playerController.windowClick(0, slot, 0, 0, this.mc.thePlayer);
						this.mc.playerController.windowClick(0, 37, 0, 0, this.mc.thePlayer);
					}
				} else {
					if (this.mc.thePlayer.inventory.currentItem != 267
							&& this.mc.thePlayer.inventory.hasItem(Item.getItemById(267))) {
						if (slot >= 36 && slot <= 44) {
							if (Item.getIdFromItem(stack.getItem()) == 267) {
								this.mc.thePlayer.inventory.currentItem = slot - 36;
							}
						} else if (Item.getIdFromItem(stack.getItem()) == 267) {
							this.mc.playerController.windowClick(0, slot, 0, 0, this.mc.thePlayer);
							this.mc.playerController.windowClick(0, 37, 0, 0, this.mc.thePlayer);
						}
					} else {
						if (this.mc.thePlayer.inventory.currentItem != 283 && this.mc.thePlayer.inventory.hasItem(Item.getItemById(283))) {
							if (slot >= 36 && slot <= 44) {
								if (Item.getIdFromItem(stack.getItem()) == 283) {
									this.mc.thePlayer.inventory.currentItem = slot - 36;
								}
							} else if (Item.getIdFromItem(stack.getItem()) == 283) {
								this.mc.playerController.windowClick(0, slot, 0, 0, this.mc.thePlayer);
								this.mc.playerController.windowClick(0, 37, 0, 0, this.mc.thePlayer);
							}
						} else {
							if (this.mc.thePlayer.inventory.currentItem != 272
									&& this.mc.thePlayer.inventory.hasItem(Item.getItemById(272))) {
								if (slot >= 36 && slot <= 44) {
									if (Item.getIdFromItem(stack.getItem()) == 272) {
										this.mc.thePlayer.inventory.currentItem = slot - 36;
									}
								} else if (Item.getIdFromItem(stack.getItem()) == 272) {
									this.mc.playerController.windowClick(0, slot, 0, 0, this.mc.thePlayer);
									this.mc.playerController.windowClick(0, 37, 0, 0, this.mc.thePlayer);
								}
							} else {
								if (this.mc.thePlayer.inventory.currentItem != 268
										&& this.mc.thePlayer.inventory.hasItem(Item.getItemById(268))) {
									if (slot >= 36 && slot <= 44) {
										if (Item.getIdFromItem(stack.getItem()) == 268) {
											this.mc.thePlayer.inventory.currentItem = slot - 36;
										}
									} else if (Item.getIdFromItem(stack.getItem()) == 268) {
										this.mc.playerController.windowClick(0, slot, 0, 0, this.mc.thePlayer);
										this.mc.playerController.windowClick(0, 37, 0, 0, this.mc.thePlayer);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public void Soup() {
		for (int slot = 44; slot >= 9; slot--) {
			ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(slot).getStack();

			if (stack != null) {
				if (this.mc.thePlayer.inventory.currentItem != 282 && this.mc.thePlayer.inventory.hasItem(Item.getItemById(282))) {
					if (slot >= 36 && slot <= 44) {
						if (Item.getIdFromItem(stack.getItem()) == 282) {
							this.mc.thePlayer.inventory.currentItem = slot - 36; 
							this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.inventory.getCurrentItem()));
						}
					}
				}

			}
		}
	}
}
