package Mixin.mixins;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.tntchina.clientbase.event.EventManager;
import net.tntchina.clientbase.event.events.BlockBBEvent;
import net.tntchina.clientbase.module.ModuleManager;
import net.tntchina.clientbase.module.modules.render.XRay;
import net.tntchina.util.XRayUtil;

@Mixin(Block.class)
public abstract class MixinBlock {
	
	protected Block block;
	
	@Inject(method = "<init>", at = @At("RETURN"))
	public void setup(CallbackInfo callbackInfo) {
		this.block = (Block)((Object) this);
	}
	
	@Inject(method = "getMixedBrightnessForBlock", at = @At("HEAD"), cancellable = true)
	private void XRayBlocks(CallbackInfoReturnable<Integer> callbackInfo) {
		if (ModuleManager.getModuleState(XRay.class)) {
			callbackInfo.setReturnValue(Integer.valueOf(2147483647));
		}
	}
	
	@Inject(method = "shouldSideBeRendered", at = @At("HEAD"), cancellable = true)
	private void XRayBlock(CallbackInfoReturnable<Boolean> callbackInfo) {
        if (ModuleManager.getModuleState(XRay.class)) {
        	callbackInfo.setReturnValue(XRayUtil.isXRayBlock(this.block));
        }
	}
	
	@Overwrite
    public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
        AxisAlignedBB axisalignedbb = this.getCollisionBoundingBox(worldIn, pos, state);
        BlockBBEvent event = new BlockBBEvent(pos, state.getBlock(), axisalignedbb);
        EventManager.callEvent(event);
        axisalignedbb = event.getAxisAlignedBB();
        
        if (axisalignedbb != null && mask.intersectsWith(axisalignedbb)) {
        	list.add(axisalignedbb);
        	list.addAll(event.getAxisAlignedBBList());
        }
    }

	@Shadow
	public abstract void setBlockBounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ);

	@Shadow
	public abstract AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state);
}