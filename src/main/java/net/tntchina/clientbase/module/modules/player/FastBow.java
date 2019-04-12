package net.tntchina.clientbase.module.modules.player;

import java.util.Arrays;

import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.tntchina.clientbase.event.EventTarget;
import net.tntchina.clientbase.event.events.RightEvent;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.clientbase.value.values.ModeValue;

public class FastBow extends Module {

	private ModeValue mode = new ModeValue("Fast", "Mode", Arrays.asList("Fast", "Single"));
	
	public FastBow(String name, ModuleCategory categorys) {
		super(name, categorys);
	}

	public void onUpdate() {
		if (!this.getState() | this.mc.thePlayer.inventory.getCurrentItem() == null | !this.mode.getObject().equals("Fast")) {
			return;
		}

		if (this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow && this.mc.gameSettings.keyBindUseItem.pressed) {
			this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem());
			this.mc.thePlayer.inventory.getCurrentItem().getItem().onItemRightClick(this.mc.thePlayer.inventory.getCurrentItem(), this.mc.theWorld, this.mc.thePlayer);

			for (int i = 0; i < 20; i++) {
				this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
			}

			this.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
			this.mc.thePlayer.inventory.getCurrentItem().getItem().onPlayerStoppedUsing(this.mc.thePlayer.inventory.getCurrentItem(), this.mc.theWorld, this.mc.thePlayer, 10);
		}
	}
	
	@EventTarget
	public void onRight(RightEvent event) {
		if (!this.mode.getObject().equals("Single") | !this.getState()) {
			return;
		}
		
		if (this.mc.thePlayer.inventory.getCurrentItem() == null) {
			return;
		}
		
		if (!this.mc.thePlayer.onGround) {
			return;
		}
		
		if (this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow && this.mc.gameSettings.keyBindUseItem.pressed) {
			this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem());
			this.mc.thePlayer.inventory.getCurrentItem().getItem().onItemRightClick(this.mc.thePlayer.inventory.getCurrentItem(), this.mc.theWorld, this.mc.thePlayer);

			for (int i = 0; i < 20; i++) {
				this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
			}

			this.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
			this.mc.thePlayer.inventory.getCurrentItem().getItem().onPlayerStoppedUsing(this.mc.thePlayer.inventory.getCurrentItem(), this.mc.theWorld, this.mc.thePlayer, 10);
		}
	}
	
	public String getMode() {
		return this.mode.getObject();
	}
}