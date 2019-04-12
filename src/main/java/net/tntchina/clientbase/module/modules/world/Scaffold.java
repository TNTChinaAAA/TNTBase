package net.tntchina.clientbase.module.modules.world;

import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.tntchina.clientbase.event.EventTarget;
import net.tntchina.clientbase.event.events.MotionUpdateEvent;
import net.tntchina.clientbase.event.events.SafeWalkEvent;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.clientbase.value.values.NumberValue;
import net.tntchina.util.BlockUtil;
import net.tntchina.util.TimeUtil;

public class Scaffold extends Module {

	public static Scaffold INSTANCE;
	private BlockPos currentPos;
	private EnumFacing currentFacing;
	private boolean rotated = false;
	private TimeUtil timer = new TimeUtil();
	private NumberValue delay = new NumberValue(0.1D, "Delay", 100.1D, 0.1D);
	
	public Scaffold(String name, ModuleCategory Categorys) {
		super(name, Categorys);
		Scaffold.INSTANCE = this;
	}
	
	@EventTarget
	public void onSafe(SafeWalkEvent event) {
		if (this.getState()) {
			event.setSafe(this.rotated);
		}
	}
	
	private void setBlockAndFacing(BlockPos var1) {
        if (this.mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, -1, 0);
            this.currentFacing = EnumFacing.UP;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(-1, 0, 0);
            this.currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(1, 0, 0);
            this.currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 0, -1);
            this.currentFacing = EnumFacing.SOUTH;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 0, 1);
            this.currentFacing = EnumFacing.NORTH;
        } else {
        	this.currentPos = null;
        	this.currentFacing = null;
        }
    }
	
	@EventTarget
	public void onPre(MotionUpdateEvent event) {
		if (this.getState() && this.mc.thePlayer != null && this.mc.theWorld != null && event.getState() == MotionUpdateEvent.State.PRE) {
			this.rotated = false;
			this.currentPos = null;
			this.currentFacing = null;
			BlockPos pos = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0D, this.mc.thePlayer.posZ);
            
            if (this.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) {
                this.setBlockAndFacing(pos);

                if (this.currentPos != null) {
                    float facing[] = BlockUtil.getDirectionToBlock(this.currentPos.getX(), this.currentPos.getY(), this.currentPos.getZ(), this.currentFacing);
                    float yaw = facing[0];
                    float pitch = Math.min(90, facing[1] + 9);
                    this.rotated = true;
                    this.mc.thePlayer.rotationYaw = yaw;
                    this.mc.thePlayer.rotationPitch = pitch;
                }
            }
            
            if (this.currentPos != null) {
            	if (this.timer.hasTimeReached(this.delay.getObject())) {
            		if (this.mc.thePlayer.getCurrentEquippedItem() != null && this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
            			if (this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem(), this.currentPos, this.currentFacing, new Vec3(this.currentPos.getX(), this.currentPos.getY(), this.currentPos.getZ()))) {
            				this.mc.thePlayer.swingItem();
            				this.mc.thePlayer.motionX = 0;
            				this.mc.thePlayer.motionZ = 0;
                        	this.timer.setLastMS();
                        }
                    }
                }
            }
		}
	}
}