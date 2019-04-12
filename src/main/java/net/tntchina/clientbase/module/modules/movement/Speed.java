package net.tntchina.clientbase.module.modules.movement;

import java.util.Arrays;
import java.util.List;

import Mixin.impl.IEntityPlayerSP;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.clientbase.value.values.BooleanValue;
import net.tntchina.clientbase.value.values.ModeValue;
import net.tntchina.clientbase.value.values.NumberValue;
import net.tntchina.util.BlockUtils;
import net.tntchina.util.PlayerUtils;
import net.tntchina.util.TimeHelper;

public class Speed extends Module {

	public TimeHelper timer = new TimeHelper();
	public ModeValue mode = new ModeValue("Hypixel", "Mode", Arrays.asList("Hypixel", "NCP", "AAC"));
	public NumberValue speedValue = new NumberValue(1.2, "Speed", 2.0, 1.0);
	public BooleanValue ice = new BooleanValue(true, "Ice");
	public lllllll1l1l1l1l llll = new lllllll1l1l1l1l();
	
	public class lllllll1l1l1l1l {
		
		public final Minecraft mc = Minecraft.getMinecraft();
		public int stage = 0;
		public double movementSpeed = 0.0, distance = 0.0;
		
		public void onUpdate() {
            double xDist = this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX;
            double zDist = this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ;
            this.distance = Math.sqrt(xDist * xDist + zDist * zDist);
			
			if (this.canZoom() && this.stage == 1) {
				this.movementSpeed = 1.56 * PlayerUtils.getBaseMovementSpeed() - 0.05;
				this.mc.timer.timerSpeed = 1.16f;
			} else if (this.canZoom() && this.stage == 2) {
				this.mc.thePlayer.motionY = 0.3999;
				this.movementSpeed *= 1.58;
				this.mc.timer.timerSpeed = 1.2f;
			} else if (this.stage == 3) {
				double difference = 0.66 * (this.distance - PlayerUtils.getBaseMovementSpeed());
				this.movementSpeed = this.distance - difference;
				this.mc.timer.timerSpeed = 1.08f;
			} else {
				List<AxisAlignedBB> collidingList = this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.boundingBox.offset(0.0, this.mc.thePlayer.motionY, 0.0));

				if (collidingList.size() > 0 || this.mc.thePlayer.isCollidedVertically && this.stage > 0) {
					this.stage = ((IEntityPlayerSP) this.mc.thePlayer).moving() ? 1 : 0;
				}

				this.movementSpeed = this.distance - this.distance / 157.0;
			}

			this.movementSpeed = Math.max(this.movementSpeed, PlayerUtils.getBaseMovementSpeed());
			((IEntityPlayerSP) this.mc.thePlayer).setSpeed(this.movementSpeed);
			
			if (((IEntityPlayerSP) this.mc.thePlayer).moving()) {
				++this.stage;
			}
		}
		
		private boolean canZoom() {
			if (((IEntityPlayerSP) this.mc.thePlayer).moving() && this.mc.thePlayer.onGround) {
				return true;
			}
			
			return false;
		}
	}
	
	public Speed(String name, int key, ModuleCategory Categorys) {
		super(name, key, Categorys, "Can very fast walk and jump.");
	}
	
	public String getMode() {
		return this.mode.getObject();
	}
	
	public boolean isModeEqual(String mode) {
		return this.mode.getObject().equalsIgnoreCase(mode);
	}
	
	public void onToggled() {
		this.timer.reset();
	}
	
	public void onUpdate() {
		if (!this.getState() | !((IEntityPlayerSP) this.mc.thePlayer).moving()) {
			return;
		}
		
		if (this.ice.getObject().booleanValue()) {
			if (BlockUtils.getBlockUnderPlayer(this.mc.thePlayer, 0.001) instanceof BlockIce || BlockUtils.getBlockUnderPlayer(this.mc.thePlayer, 0.001) instanceof BlockPackedIce) {
				Blocks.ice.slipperiness = 0.39F;
				Blocks.packed_ice.slipperiness = 0.39F;
				return;
			}
		} else {
			Blocks.ice.slipperiness = 0.98F;
			Blocks.packed_ice.slipperiness = 0.98F;
		}
		
		if (this.isModeEqual("NCP")) {
			if (this.mc.thePlayer.onGround) {
				this.mc.timer.timerSpeed = 1F;
				this.mc.thePlayer.jump();
			} else {
				if (this.mc.thePlayer.ticksExisted % 4 == 0) {
					((IEntityPlayerSP) this.mc.thePlayer).setSpeed(this.speedValue.getDoubleValue() / 2);
					this.mc.timer.timerSpeed = (float) (this.speedValue.getDoubleValue());
					((IEntityPlayerSP) this.mc.thePlayer).setSpeed(this.speedValue.getDoubleValue() / 2);
				}
			}
		}
		
		if (this.isModeEqual("Hypixel")) {
			this.llll.onUpdate();
		}
	}
}