package Mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.entity.Entity;
import net.tntchina.clientbase.event.EventManager;
import net.tntchina.clientbase.event.events.Render3DEvent;
import net.tntchina.clientbase.main.TNTBase;
import net.tntchina.clientbase.module.ModuleManager;
import net.tntchina.clientbase.module.modules.render.NoHurtCamera;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer implements IResourceManagerReloadListener {

	@Shadow
	private Minecraft mc;
	
	@Shadow
	private Entity pointedEntity;

	@Inject(method = "renderWorldPass", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/ForgeHooksClient;renderFirstPersonHand(Lnet/minecraft/client/renderer/RenderGlobal;FI)Z", shift = At.Shift.BEFORE))
	private void renderWorldPass(int pass, float partialTicks, long finishTimeNano, CallbackInfo callbackInfo) {
		EventManager.callEvent(new Render3DEvent());
		TNTBase.onRender();
	}

	@Inject(method = "hurtCameraEffect", at = @At("HEAD"), cancellable = true)
	public void NoHurtCamera(CallbackInfo callbackInfo) {
		if (this.mc.getRenderViewEntity() == null) {
			return;
		}

		if (this.mc.getRenderViewEntity().equals(this.mc.thePlayer) && ModuleManager.getModuleState(NoHurtCamera.class)) {
			callbackInfo.cancel();
		}
	}

/*	@Overwrite
	public void getMouseOver(float partialTicks) {
		Entity entity = this.mc.getRenderViewEntity();
		final Reach reach = ModuleManager.getModule(Reach.class);
		
		if (entity != null) {
			if (this.mc.theWorld != null) {
				this.mc.mcProfiler.startSection("pick");
				this.mc.pointedEntity = null;
				double d0 = (double) this.mc.playerController.getBlockReachDistance();
				this.mc.objectMouseOver = entity.rayTrace(d0, partialTicks);
				double d1 = d0;
				Vec3 vec3 = entity.getPositionEyes(partialTicks);
				boolean flag = false;
				int i = 3;

				if (this.mc.playerController.extendedReach()) {
					d0 = 6.0D;
					d1 = 6.0D;
				} else {
					if (d0 > 3.0D && !reach.getState()) {
						flag = true;
					}
				}

				if (this.mc.objectMouseOver != null) {
					d1 = this.mc.objectMouseOver.hitVec.distanceTo(vec3);
				}

				Vec3 vec31 = entity.getLook(partialTicks);
				Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
				this.pointedEntity = null;
				Vec3 vec33 = null;
				float f = 1.0F;
				List<Entity> list = this.mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double) f, (double) f, (double) f),
					Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>() {
						public boolean apply(Entity p_apply_1_) {
							return p_apply_1_.canBeCollidedWith();
						}
					}
				));
				
				double d2 = d1;

				for (int j = 0; j < list.size(); ++j) {
					Entity entity1 = (Entity) list.get(j);
					float f1 = entity1.getCollisionBorderSize();
					AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double) f1, (double) f1, (double) f1);
					MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

					if (axisalignedbb.isVecInside(vec3)) {
						if (d2 >= 0.0D) {
							this.pointedEntity = entity1;
							vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
							d2 = 0.0D;
						}
					} else if (movingobjectposition != null) {
						double d3 = vec3.distanceTo(movingobjectposition.hitVec);

						if (d3 < d2 || d2 == 0.0D) {
							if (entity1 == entity.ridingEntity && !entity.canRiderInteract()) {
								if (d2 == 0.0D) {
									this.pointedEntity = entity1;
									vec33 = movingobjectposition.hitVec;
								}
							} else {
								this.pointedEntity = entity1;
								vec33 = movingobjectposition.hitVec;
								d2 = d3;
							}
						}
					}
				}

				if (this.pointedEntity != null && flag && vec3.distanceTo(vec33) > 3.0D) {
					this.pointedEntity = null;
					this.mc.objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec33, (EnumFacing) null, new BlockPos(vec33));
				}

				if (this.pointedEntity != null && (d2 < d1 || this.mc.objectMouseOver == null || vec3.distanceTo(vec33) > reach.getValue())) {
					this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, vec33);

					if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame) {
						this.mc.pointedEntity = this.pointedEntity;
					}
				}

				this.mc.mcProfiler.endSection();
			}
		}
	}*/
}