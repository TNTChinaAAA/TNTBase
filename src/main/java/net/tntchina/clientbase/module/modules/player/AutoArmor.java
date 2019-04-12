package net.tntchina.clientbase.module.modules.player;

import java.util.Random;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.tntchina.clientbase.event.EventTarget;
import net.tntchina.clientbase.event.events.UpdateEvent;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.clientbase.value.values.NumberValue;
import net.tntchina.util.TimeUtil;

public class AutoArmor extends Module {
	
	private NumberValue delay = new NumberValue(50.0, "Delay", 1000.0, 0.0);
	private TimeUtil timer = new TimeUtil();
	private int[] boots = new int[] { 313, 309, 317, 305, 301 };
	private int[] chestplate = new int[] { 311, 307, 315, 303, 299 };
	private int[] helmet = new int[] { 310, 306, 314, 302, 298 };
	private int[] leggings = new int[] { 312, 308, 316, 304, 300 };
	private int slot = 5;
	private double enchantmentValue = -1.0;
	private double protectionValue = 0.0;
	private int item = -1;

	public AutoArmor(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	@EventTarget
	private void onPre(UpdateEvent e) {
		if (this.getState() && e.getState() == UpdateEvent.State.PRE) {
			if (this.mc.thePlayer.capabilities.isCreativeMode || this.mc.thePlayer.openContainer != null && this.mc.thePlayer.openContainer.windowId != 0) {
				return;
			}
			
			if (this.timer.hasTimeReached(this.delay.getValue() + (double) new Random().nextInt(4))) {
				this.enchantmentValue = -1.0;
				this.item = -1;
				int i = 9;
				
				while (i < 45) {
					if (this.mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null && this.canEquip(this.mc.thePlayer.inventoryContainer.getSlot(i).getStack()) != -1 && this.canEquip(this.mc.thePlayer.inventoryContainer.getSlot(i).getStack()) == this.slot) {
						this.change(this.slot, i);
					}
					
					++i;
				}
				
				if (this.item != -1) {
					if (this.mc.thePlayer.inventoryContainer.getSlot(this.item).getStack() != null) {
						this.mc.playerController.windowClick(0, this.slot, 0, 1, this.mc.thePlayer);
					}
					
					this.mc.playerController.windowClick(0, this.item, 0, 1, this.mc.thePlayer);
				}
				
				this.slot = this.slot == 8 ? 5 : ++this.slot;
				this.timer.reset();
			}
		}
	}

	private int canEquip(ItemStack stack) {
		int id;
		int[] arrn = this.boots;
		int n = arrn.length;
		int n2 = 0;
		
		while (n2 < n) {
			id = arrn[n2];
			stack.getItem();
			
			if (Item.getIdFromItem(stack.getItem()) == id) {
				return 8;
			}
			
			++n2;
		}
		
		arrn = this.leggings;
		n = arrn.length;
		n2 = 0;
		
		while (n2 < n) {
			id = arrn[n2];
			stack.getItem();
			
			if (Item.getIdFromItem(stack.getItem()) == id) {
				return 7;
			}
			
			++n2;
		}
		
		arrn = this.chestplate;
		n = arrn.length;
		n2 = 0;
		
		while (n2 < n) {
			id = arrn[n2];
			stack.getItem();
			
			if (Item.getIdFromItem(stack.getItem()) == id) {
				return 6;
			}
			
			++n2;
		}
		
		arrn = this.helmet;
		n = arrn.length;
		n2 = 0;
		
		while (n2 < n) {
			id = arrn[n2];
			stack.getItem();
			
			if (Item.getIdFromItem(stack.getItem()) == id) {
				return 5;
			}
			
			++n2;
		}
		
		return -1;
	}

	private void change(int numy, int i) {
		this.protectionValue = this.enchantmentValue == -1.0 ? (this.mc.thePlayer.inventoryContainer.getSlot(numy).getStack() != null ? this.getProtValue(this.mc.thePlayer.inventoryContainer.getSlot(numy).getStack()) : this.enchantmentValue) : this.enchantmentValue;
		
		if (this.protectionValue <= this.getProtValue(this.mc.thePlayer.inventoryContainer.getSlot(i).getStack())) {
			if (this.protectionValue == this.getProtValue(this.mc.thePlayer.inventoryContainer.getSlot(i).getStack())) {
				int newD = 0;
				int currentD = this.mc.thePlayer.inventoryContainer.getSlot(numy).getStack() != null ? this.mc.thePlayer.inventoryContainer.getSlot(numy).getStack().getItemDamage() : 999;
				newD = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null ? this.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItemDamage() : 500;
				
				if (newD <= currentD && newD != currentD) {
					this.item = i;
					this.enchantmentValue = this.getProtValue(this.mc.thePlayer.inventoryContainer.getSlot(i).getStack());
				}
			} else {
				this.item = i;
				this.enchantmentValue = this.getProtValue(this.mc.thePlayer.inventoryContainer.getSlot(i).getStack());
			}
		}
	}

	private double getProtValue(ItemStack stack) {
		if (stack != null && stack.getItem() instanceof ItemArmor) {
			return ((ItemArmor) stack.getItem()).damageReduceAmount + (double) ((100 - ((ItemArmor) stack.getItem()).damageReduceAmount * 4) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 4) * 0.0075;
		}
		
		return 0.0;
	}
}
