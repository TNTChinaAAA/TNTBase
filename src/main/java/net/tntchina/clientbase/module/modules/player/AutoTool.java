package net.tntchina.clientbase.module.modules.player;

import com.mojang.authlib.GameProfile;

import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.tntchina.clientbase.event.*;
import net.tntchina.clientbase.event.events.*;
import net.tntchina.clientbase.module.*;

public class AutoTool extends Module {

	public AutoTool(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	@EventTarget
	public void onAttack(AttackEntityEvent event) {
		if (!this.getState() | this.mc.thePlayer.capabilities.isCreativeMode | !(event.getAttackTarget() instanceof EntityLivingBase) | !event.getEntity().equals(this.mc.thePlayer)) {
			return;
		}
		
		if (event.getAttackTarget() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getAttackTarget();
			
			if (player.getGameProfile() != null) {
				GameProfile profile = player.getGameProfile();
				
				if (profile.equals(this.mc.thePlayer.getGameProfile())) {
					return;
				}
			}
		}
		
		AutoTool.toggledSword();
	}
	
	public static void toggledSword() {
		ItemSword lastItem = null;
		
		for (int i = 0; i < 9; i++) {
			ItemStack stack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i];
			
			if (stack == null) {
				continue;
			}
			
			if (stack.getItem() == null) {
				continue;
			}
			
			if (stack.getItem() instanceof ItemSword) {
				ItemSword currentItem = (ItemSword) stack.getItem();
				
				if (lastItem == null) {
					lastItem = currentItem;
					Minecraft.getMinecraft().thePlayer.inventory.currentItem = i;
				} else {
					if (lastItem.getDamageVsEntity() > currentItem.getDamageVsEntity()) {
						continue;
					} else {
						lastItem = currentItem;
						Minecraft.getMinecraft().thePlayer.inventory.currentItem = i;
					}
				}
			}
		}
	}
	
	@EventTarget
	public void onBreak(BreakBlockEvent event) {
		if (!this.getState() | this.mc.thePlayer.capabilities.isCreativeMode) {
			return;
		}
		
		if (event.getBlock() instanceof BlockOldLog) {
			ItemAxe lastItem = null;
			
			for (int i = 0; i < 9; i++) {
				ItemStack stack = this.mc.thePlayer.inventory.mainInventory[i];
				
				if (stack == null) {
					continue;
				}
				
				if (stack.getItem() == null) {
					continue;
				}
				
				if (stack.getItem() instanceof ItemAxe) {
					ItemAxe currentItem = (ItemAxe) stack.getItem();
					
					if (lastItem == null) {
						lastItem = currentItem;
						this.mc.thePlayer.inventory.currentItem = i;
						continue;
					} else {
						if (lastItem.getToolMaterial().getHarvestLevel() > currentItem.getToolMaterial().getHarvestLevel()) {
							continue;
						} else {
							lastItem = currentItem;
							this.mc.thePlayer.inventory.currentItem = i;
							continue;
						}
					}
				}
			}
			
			return;
		}
		
		if (event.getBlock() instanceof BlockSand | event.getBlock() instanceof BlockGrass | event.getBlock() instanceof BlockSnow | event.getBlock() instanceof BlockSnowBlock) {
			for (int i = 0; i < 9; i++) {
				ItemStack stack = this.mc.thePlayer.inventory.mainInventory[i];
				
				if (stack == null) {
					continue;
				}
				
				if (stack.getItem() == null) {
					continue;
				}
				
				if (stack.getItem() instanceof ItemSpade) {
					this.mc.thePlayer.inventory.currentItem = i;
					break;
				}
			}
			
			return;
		}
		
		if (event.getBlock() instanceof BlockStone) {
			ItemPickaxe lastItem = null;
			
			for (int i = 0; i < 9; i++) {
				ItemStack stack = this.mc.thePlayer.inventory.mainInventory[i];
				
				if (stack == null) {
					continue;
				}
				
				if (stack.getItem() == null) {
					continue;
				}
				
				if (stack.getItem() instanceof ItemPickaxe) {
					ItemPickaxe currentItem = (ItemPickaxe) stack.getItem();
					
					if (lastItem == null) {
						lastItem = currentItem;
						this.mc.thePlayer.inventory.currentItem = i;
						continue;
					} else {
						if (lastItem.getToolMaterial().getHarvestLevel() > currentItem.getToolMaterial().getHarvestLevel()) {
							continue;
						} else {
							lastItem = currentItem;
							this.mc.thePlayer.inventory.currentItem = i;
							continue;
						}
					}
				}
			}
			
			return;
		}
	}
}
