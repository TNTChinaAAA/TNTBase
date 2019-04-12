package Mixin.mixins;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import Mixin.impl.IEntityPlayerSP;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ReportedException;
import net.minecraftforge.common.ForgeHooks;
import net.tntchina.clientbase.event.EventManager;
import net.tntchina.clientbase.event.events.LivingUpdateEvent;
import net.tntchina.clientbase.event.events.MotionUpdateEvent;
import net.tntchina.clientbase.event.events.MoveEvent;
import net.tntchina.clientbase.event.events.SafeWalkEvent;
import net.tntchina.clientbase.event.events.UpdateEvent;
import net.tntchina.clientbase.event.events.UpdatePostEvent;
import net.tntchina.clientbase.event.events.UpdatePreEvent;
import net.tntchina.clientbase.event.events.VelocityEvent;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleManager;
import net.tntchina.clientbase.module.modules.combat.Velocity;
import net.tntchina.clientbase.module.modules.movement.Climb;
import net.tntchina.clientbase.module.modules.movement.HighJump;
import net.tntchina.clientbase.module.modules.movement.KeepSprint;
import net.tntchina.clientbase.module.modules.movement.NoSlow;
import net.tntchina.clientbase.module.modules.player.HitBox;
import net.tntchina.clientbase.module.modules.player.NoHungry;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer implements IEntityPlayerSP {

	private int cacheSprintToggleTimer = 0;
	private float cacheStrafe = 0.0F, cacheForward = 0.0F, cacheYaw = 0.0F, cachePitch = 0.0F;

	@Shadow
	protected int sprintToggleTimer;

	@Shadow
	public MovementInput movementInput;
	
	public MixinEntityPlayerSP() {
		super(null, null);
	}

	@Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"))
	private void onMotionPre(CallbackInfo callbackInfo) {
		this.cacheYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
		this.cachePitch = Minecraft.getMinecraft().thePlayer.rotationPitch;
		MotionUpdateEvent event = new MotionUpdateEvent(MotionUpdateEvent.State.PRE, Minecraft.getMinecraft().thePlayer.rotationYaw, Minecraft.getMinecraft().thePlayer.rotationPitch, Minecraft.getMinecraft().thePlayer.onGround, Minecraft.getMinecraft().thePlayer.motionX, Minecraft.getMinecraft().thePlayer.motionY, Minecraft.getMinecraft().thePlayer.motionZ);
		EventManager.callEvent(event);

		for (Module m : ModuleManager.getModules()) {
			m.onMotion(event);
		}

		Minecraft.getMinecraft().thePlayer.motionX = event.getX();
		Minecraft.getMinecraft().thePlayer.motionY = event.getY();
		Minecraft.getMinecraft().thePlayer.motionZ = event.getZ();
		Minecraft.getMinecraft().thePlayer.onGround = event.onGround();
		Minecraft.getMinecraft().thePlayer.rotationYaw = event.getYaw();
		Minecraft.getMinecraft().thePlayer.rotationPitch = event.getPitch();
	}

	@Inject(method = "onUpdateWalkingPlayer", at = @At("RETURN"))
	private void onMotionPost(CallbackInfo callbackInfo) {
		MotionUpdateEvent event = new MotionUpdateEvent(MotionUpdateEvent.State.PRE, Minecraft.getMinecraft().thePlayer.rotationYaw, Minecraft.getMinecraft().thePlayer.rotationPitch, Minecraft.getMinecraft().thePlayer.onGround, Minecraft.getMinecraft().thePlayer.motionX, Minecraft.getMinecraft().thePlayer.motionY, Minecraft.getMinecraft().thePlayer.motionZ);
		EventManager.callEvent(event);

		for (Module m : ModuleManager.getModules()) {
			m.onMotion(event);
		}

		Minecraft.getMinecraft().thePlayer.motionX = event.getX();
		Minecraft.getMinecraft().thePlayer.motionY = event.getY();
		Minecraft.getMinecraft().thePlayer.motionZ = event.getZ();
		Minecraft.getMinecraft().thePlayer.onGround = event.onGround();
		Minecraft.getMinecraft().thePlayer.rotationYaw = this.cacheYaw;
		Minecraft.getMinecraft().thePlayer.rotationPitch = this.cachePitch;
	}

	@Inject(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isBlockLoaded(Lnet/minecraft/util/BlockPos;)Z", shift = At.Shift.AFTER))
	private void onUpdate(CallbackInfo callbackInfo) {
		EventManager.callEvent(new UpdatePreEvent());
		EventManager.callEvent(new UpdateEvent(UpdateEvent.State.PRE));

		for (Module m : ModuleManager.getModules()) {
			m.onUpdate();
		}
	}

	@Inject(method = "onUpdate", at = @At("RETURN"))
	private void postUpdate(CallbackInfo callbackInfo) {
		if (!this.worldObj.isBlockLoaded(new BlockPos(this.posX, 0.0D, this.posZ))) {
			return;
		}

		EventManager.callEvent(new UpdatePostEvent());
		EventManager.callEvent(new UpdateEvent(UpdateEvent.State.POST));
	}

	@Inject(method = "onLivingUpdate", at = @At("HEAD"))
	private void onLivingUpdate(CallbackInfo callbackInfo) {
		EventManager.callEvent(new LivingUpdateEvent(LivingUpdateEvent.State.PRE));
	}

	@Inject(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isSprinting()Z", shift = At.Shift.AFTER))
	private void onLivingUpdate_INIT(CallbackInfo callbackInfo) {
		EventManager.callEvent(new LivingUpdateEvent(LivingUpdateEvent.State.INIT));
	}

	@Inject(method = "onLivingUpdate", at = @At("RETURN"))
	private void onLivingUpdate_POST(CallbackInfo callbackInfo) {
		EventManager.callEvent(new LivingUpdateEvent(LivingUpdateEvent.State.POST));
	}

	@Inject(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/MovementInput;updatePlayerMoveState()V", shift = At.Shift.AFTER))
	private void onNoSlowEnable(CallbackInfo callbackInfo) {
		if (!this.isSlow()) {
			return;
		}

		if (ModuleManager.getModuleState(NoSlow.class)) {
			this.cacheStrafe = Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe;
			this.cacheForward = Minecraft.getMinecraft().thePlayer.movementInput.moveForward;
			this.cacheSprintToggleTimer = Minecraft.getMinecraft().thePlayer.sprintToggleTimer;
		}
	}

	@Inject(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;pushOutOfBlocks(DDD)Z", shift = At.Shift.BEFORE))
	public void onToggledTimerZero(CallbackInfo callbackInfo) {
		if (!this.isSlow()) {
			return;
		}

		if (ModuleManager.getModuleState(NoSlow.class)) {
			Minecraft.getMinecraft().thePlayer.sprintToggleTimer = this.cacheSprintToggleTimer;
			Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe = this.cacheStrafe;
			Minecraft.getMinecraft().thePlayer.movementInput.moveForward = this.cacheForward;
		}
	}

	public final boolean isSlow() {
		return Minecraft.getMinecraft().thePlayer.isUsingItem() && !Minecraft.getMinecraft().thePlayer.isRiding();
	}

	@Shadow
	protected void sendHorseJump() {

	}

	@Shadow
	public boolean isCurrentViewEntity() {
		return false;
	}

	@Shadow
	protected boolean pushOutOfBlocks(double x, double y, double z) {
		return false;
	}
	
	public void attackTargetEntityWithCurrentItem(Entity targetEntity) {
		if (!ForgeHooks.onPlayerAttackTarget(this, targetEntity)) { 
			return;
		}
		
        if (targetEntity.canAttackWithItem()) {
            if (!targetEntity.hitByEntity(this)) {
                float f = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
                int i = 0;
                float f1 = 0.0F;

                if (targetEntity instanceof EntityLivingBase) {
                    f1 = EnchantmentHelper.getModifierForCreature(this.getHeldItem(), ((EntityLivingBase)targetEntity).getCreatureAttribute());
                } else {
                    f1 = EnchantmentHelper.getModifierForCreature(this.getHeldItem(), EnumCreatureAttribute.UNDEFINED);
                }

                i = i + EnchantmentHelper.getKnockbackModifier(this);

                if (this.isSprinting()) {
                    ++i;
                }

                if (f > 0.0F || f1 > 0.0F) {
                    boolean flag = this.fallDistance > 0.0F && !this.onGround && !this.isOnLadder() && !this.isInWater() && !this.isPotionActive(Potion.blindness) && this.ridingEntity == null && targetEntity instanceof EntityLivingBase;

                    if (flag && f > 0.0F)
                    {
                        f *= 1.5F;
                    }

                    f = f + f1;
                    boolean flag1 = false;
                    int j = EnchantmentHelper.getFireAspectModifier(this);

                    if (targetEntity instanceof EntityLivingBase && j > 0 && !targetEntity.isBurning()) {
                        flag1 = true;
                        targetEntity.setFire(1);
                    }

                    double d0 = targetEntity.motionX;
                    double d1 = targetEntity.motionY;
                    double d2 = targetEntity.motionZ;
                    boolean flag2 = targetEntity.attackEntityFrom(DamageSource.causePlayerDamage(this), f);

                    if (flag2) {
                        if (i > 0) {
                            targetEntity.addVelocity((double)(-MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));
                        
                            if (!ModuleManager.getModuleState(KeepSprint.class)) {
                                this.motionX *= 0.6D;
                                this.motionZ *= 0.6D;
                                this.setSprinting(false);
                            }
                        }

                        if (targetEntity instanceof EntityPlayerMP && targetEntity.velocityChanged) {
                            ((EntityPlayerMP) targetEntity).playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(targetEntity));
							targetEntity.velocityChanged = false;
							targetEntity.motionX = d0;
							targetEntity.motionY = d1;
							targetEntity.motionZ = d2;
						}

						if (flag) {
							this.onCriticalHit(targetEntity);
						}

						if (f1 > 0.0F) {
							this.onEnchantmentCritical(targetEntity);
						}

						if (f >= 18.0F) {
							this.triggerAchievement(AchievementList.overkill);
						}

						this.setLastAttacker(targetEntity);

						if (targetEntity instanceof EntityLivingBase) {
							EnchantmentHelper.applyThornEnchantments((EntityLivingBase) targetEntity, this);
						}

                        EnchantmentHelper.applyArthropodEnchantments(this, targetEntity);
                        ItemStack itemstack = this.getCurrentEquippedItem();
                        Entity entity = targetEntity;

                        if (targetEntity instanceof EntityDragonPart) {
							IEntityMultiPart ientitymultipart = ((EntityDragonPart) targetEntity).entityDragonObj;

							if (ientitymultipart instanceof EntityLivingBase) {
								entity = (EntityLivingBase) ientitymultipart;
							}
                        }

                        if (itemstack != null && entity instanceof EntityLivingBase)  {
                            itemstack.hitEntity((EntityLivingBase) entity, this);

							if (itemstack.stackSize <= 0) {
								this.destroyCurrentEquippedItem();
							}
						}

						if (targetEntity instanceof EntityLivingBase) {
							this.addStat(StatList.damageDealtStat, Math.round(f * 10.0F));

							if (j > 0) {
								targetEntity.setFire(j * 4);
							}
						}

						this.addExhaustion(0.3F);
					} else if (flag1) {
						targetEntity.extinguish();
					}
				}
            }
        }
	}

	public float getCollisionBorderSize() {
		HitBox hitBox = ModuleManager.getModule(HitBox.class);
		return hitBox.getState() ? hitBox.getValue() : super.getCollisionBorderSize();
	}

	public boolean isOnLadder() {
		Climb climb = ModuleManager.getModule(Climb.class);

		if (this.isCollidedHorizontally && climb.getState()) {
			return true;
		}

		return super.isOnLadder();
	}

	protected void setBeenAttacked() {
		Velocity module = ModuleManager.getModule(Velocity.class);

		if (module.getState()) {
			return;
		}

		super.setBeenAttacked();
	}

	public void setVelocity(double x, double y, double z) {
		VelocityEvent event = new VelocityEvent(this.motionX, this.motionY, this.motionZ);
		EventManager.callEvent(event);

		if (event.isCancelled()) {
			return;
		}

		x = event.getX();
		y = event.getY();
		z = event.getZ();

		super.setVelocity(x, y, z);
	}

	public void addVelocity(double x, double y, double z) {
		VelocityEvent event = new VelocityEvent(this.motionX, this.motionY, this.motionZ);
		EventManager.callEvent(event);

		if (event.isCancelled()) {
			return;
		}

		x = event.getX();
		y = event.getY();
		z = event.getZ();
		super.addVelocity(x, y, z);
	}

	public void addExhaustion(float hungryLevel) {
		final NoHungry noHurgry = ModuleManager.getModule(NoHungry.class);

		if (noHurgry.getState()) {
			return;
		} else {
			super.addExhaustion(hungryLevel);
		}
	}

	public float getJumpUpwardsMotion() {
		final HighJump highJump = ModuleManager.getModule(HighJump.class);

		if (highJump.getState()) {
			return highJump.getJumpHigh();
		}

		return 0.42F;
	}

	public void moveEntity(double x, double y, double z) {
		final MoveEvent event = new MoveEvent(x, y, z);
		EventManager.callEvent(event);
		this.onMoveEntity(event.getMotionX(), event.getMotionY(), event.getMotionZ());
	}
	
	public void onMoveEntity(double x, double y, double z) {
		if (this.noClip) {
			this.setEntityBoundingBox(this.getEntityBoundingBox().offset(x, y, z));
	        this.posX = (this.getEntityBoundingBox().minX + this.getEntityBoundingBox().maxX) / 2.0D;
	        this.posY = this.getEntityBoundingBox().minY;
	        this.posZ = (this.getEntityBoundingBox().minZ + this.getEntityBoundingBox().maxZ) / 2.0D;
		} else {
			boolean safeMode;
			this.worldObj.theProfiler.startSection("move");
			double d0 = this.posX;
			double d1 = this.posY;
			double d2 = this.posZ;

			if (this.isInWeb) {
				this.isInWeb = false;
				x *= 0.25D;
				y *= 0.05000000074505806D;
				z *= 0.25D;
				this.motionX = 0.0D;
				this.motionY = 0.0D;
				this.motionZ = 0.0D;
			}
			
			SafeWalkEvent event = new SafeWalkEvent(false);
			EventManager.callEvent(event);
			double d3 = x;
			double d4 = y;
			double d5 = z;
			boolean flag = this.onGround && this.isSneaking() && this instanceof EntityPlayer;
			safeMode = this.onGround && event.getSafe() && this instanceof EntityPlayer;
			
			if (flag || safeMode) {
				double d6;

				for (d6 = 0.05D; x != 0.0D && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().offset(x, -1.0D, 0.0D)).isEmpty(); d3 = x) {
					if (x < d6 && x >= -d6) {
						x = 0.0D;
					} else if (x > 0.0D) {
						x -= d6;
					} else {
						x += d6;
					}
				}

				for (; z != 0.0D && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().offset(0.0D, -1.0D, z)).isEmpty(); d5 = z) {
					if (z < d6 && z >= -d6) {
						z = 0.0D;
					} else if (z > 0.0D) {
						z -= d6;
					} else {
						z += d6;
					}
				}

				for (; x != 0.0D && z != 0.0D && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().offset(x, -1.0D, z)).isEmpty(); d5 = z) {
					if (x < d6 && x >= -d6) {
						x = 0.0D;
					} else if (x > 0.0D) {
						x -= d6;
					} else {
						x += d6;
					}

					d3 = x;

					if (z < d6 && z >= -d6) {
						z = 0.0D;
					} else if (z > 0.0D) {
						z -= d6;
					} else {
						z += d6;
					}
				}
			}

			List<AxisAlignedBB> list1 = this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().addCoord(x, y, z));
			AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();

			for (AxisAlignedBB axisalignedbb1 : list1) {
				y = axisalignedbb1.calculateYOffset(this.getEntityBoundingBox(), y);
			}

			this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0D, y, 0.0D));
			boolean flag1 = this.onGround || d4 != y && d4 < 0.0D;

			for (AxisAlignedBB axisalignedbb2 : list1) {
				x = axisalignedbb2.calculateXOffset(this.getEntityBoundingBox(), x);
			}

			this.setEntityBoundingBox(this.getEntityBoundingBox().offset(x, 0.0D, 0.0D));

			for (AxisAlignedBB axisalignedbb13 : list1) {
				z = axisalignedbb13.calculateZOffset(this.getEntityBoundingBox(), z);
			}

			this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0D, 0.0D, z));

			if (this.stepHeight > 0.0F && flag1 && (d3 != x || d5 != z)) {
				double d11 = x;
				double d7 = y;
				double d8 = z;
				AxisAlignedBB axisalignedbb3 = this.getEntityBoundingBox();
				this.setEntityBoundingBox(axisalignedbb);
				y = (double) this.stepHeight;
				List<AxisAlignedBB> list = this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().addCoord(d3, y, d5));
				AxisAlignedBB axisalignedbb4 = this.getEntityBoundingBox();
				AxisAlignedBB axisalignedbb5 = axisalignedbb4.addCoord(d3, 0.0D, d5);
				double d9 = y;

				for (AxisAlignedBB axisalignedbb6 : list) {
					d9 = axisalignedbb6.calculateYOffset(axisalignedbb5, d9);
				}

				axisalignedbb4 = axisalignedbb4.offset(0.0D, d9, 0.0D);
				double d15 = d3;

				for (AxisAlignedBB axisalignedbb7 : list) {
					d15 = axisalignedbb7.calculateXOffset(axisalignedbb4, d15);
				}

				axisalignedbb4 = axisalignedbb4.offset(d15, 0.0D, 0.0D);
				double d16 = d5;

				for (AxisAlignedBB axisalignedbb8 : list) {
					d16 = axisalignedbb8.calculateZOffset(axisalignedbb4, d16);
				}

				axisalignedbb4 = axisalignedbb4.offset(0.0D, 0.0D, d16);
				AxisAlignedBB axisalignedbb14 = this.getEntityBoundingBox();
				double d17 = y;

				for (AxisAlignedBB axisalignedbb9 : list) {
					d17 = axisalignedbb9.calculateYOffset(axisalignedbb14, d17);
				}

				axisalignedbb14 = axisalignedbb14.offset(0.0D, d17, 0.0D);
				double d18 = d3;

				for (AxisAlignedBB axisalignedbb10 : list) {
					d18 = axisalignedbb10.calculateXOffset(axisalignedbb14, d18);
				}

				axisalignedbb14 = axisalignedbb14.offset(d18, 0.0D, 0.0D);
				double d19 = d5;

				for (AxisAlignedBB axisalignedbb11 : list) {
					d19 = axisalignedbb11.calculateZOffset(axisalignedbb14, d19);
				}

				axisalignedbb14 = axisalignedbb14.offset(0.0D, 0.0D, d19);
				double d20 = d15 * d15 + d16 * d16;
				double d10 = d18 * d18 + d19 * d19;

				if (d20 > d10) {
					x = d15;
					z = d16;
					y = -d9;
					this.setEntityBoundingBox(axisalignedbb4);
				} else {
					x = d18;
					z = d19;
					y = -d17;
					this.setEntityBoundingBox(axisalignedbb14);
				}

				for (AxisAlignedBB axisalignedbb12 : list) {
					y = axisalignedbb12.calculateYOffset(this.getEntityBoundingBox(), y);
				}

				this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0D, y, 0.0D));

				if (d11 * d11 + d8 * d8 >= x * x + z * z) {
					x = d11;
					y = d7;
					z = d8;
					this.setEntityBoundingBox(axisalignedbb3);
				}
			}

			this.worldObj.theProfiler.endSection();
			this.worldObj.theProfiler.startSection("rest");
	        this.posX = (this.getEntityBoundingBox().minX + this.getEntityBoundingBox().maxX) / 2.0D;
	        this.posY = this.getEntityBoundingBox().minY;
	        this.posZ = (this.getEntityBoundingBox().minZ + this.getEntityBoundingBox().maxZ) / 2.0D;
			this.isCollidedHorizontally = d3 != x || d5 != z;
			this.isCollidedVertically = d4 != y;
			this.onGround = this.isCollidedVertically && d4 < 0.0D;
			this.isCollided = this.isCollidedHorizontally || this.isCollidedVertically;
			int i = MathHelper.floor_double(this.posX);
			int j = MathHelper.floor_double(this.posY - 0.20000000298023224D);
			int k = MathHelper.floor_double(this.posZ);
			BlockPos blockpos = new BlockPos(i, j, k);
			Block block1 = this.worldObj.getBlockState(blockpos).getBlock();

			if (block1.getMaterial() == Material.air) {
				Block block = this.worldObj.getBlockState(blockpos.down()).getBlock();

				if (block instanceof BlockFence || block instanceof BlockWall || block instanceof BlockFenceGate) {
					block1 = block;
					blockpos = blockpos.down();
				}
			}

			this.updateFallState(y, this.onGround, block1, blockpos);

			if (d3 != x) {
				this.motionX = 0.0D;
			}

			if (d5 != z) {
				this.motionZ = 0.0D;
			}

			if (d4 != y) {
				block1.onLanded(this.worldObj, this);
			}

			if (this.canTriggerWalking() && !flag && this.ridingEntity == null) {
				double d12 = this.posX - d0;
				double d13 = this.posY - d1;
				double d14 = this.posZ - d2;

				if (block1 != Blocks.ladder) {
					d13 = 0.0D;
				}

				if (block1 != null && this.onGround) {
					block1.onEntityCollidedWithBlock(this.worldObj, blockpos, this);
				}

				this.distanceWalkedModified = (float) ((double) this.distanceWalkedModified + (double) MathHelper.sqrt_double(d12 * d12 + d14 * d14) * 0.6D);
				this.distanceWalkedOnStepModified = (float) ((double) this.distanceWalkedOnStepModified + (double) MathHelper.sqrt_double(d12 * d12 + d13 * d13 + d14 * d14) * 0.6D);

				if (this.distanceWalkedOnStepModified > (float) this.nextStepDistance && block1.getMaterial() != Material.air) {
					this.nextStepDistance = (int) this.distanceWalkedOnStepModified + 1;

					if (this.isInWater()) {
						float f = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224D + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224D) * 0.35F;

						if (f > 1.0F) {
							f = 1.0F;
						}

						this.playSound(this.getSwimSound(), f, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
					}

					this.playStepSound(blockpos, block1);
				}
			}

			try {
				this.doBlockCollisions();
			} catch (Throwable throwable) {
				CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
				CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
				this.addEntityCrashInfo(crashreportcategory);
				throw new ReportedException(crashreport);
			}

			boolean flag2 = this.isWet();

			if (this.worldObj.isFlammableWithin(this.getEntityBoundingBox().contract(0.001D, 0.001D, 0.001D))) {
				this.dealFireDamage(1);

				if (!flag2) {
					++this.fire;

					if (this.fire == 0) {
						this.setFire(8);
					}
				}
			} else if (this.fire <= 0) {
				this.fire = -this.fireResistance;
			}

			if (flag2 && this.fire > 0) {
				this.playSound("random.fizz", 0.7F, 1.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
				this.fire = -this.fireResistance;
			}

			this.worldObj.theProfiler.endSection();
		}
	}
	
	public void jump() {
		this.motionY = this.getJumpUpwardsMotion();

		if (this.isPotionActive(Potion.jump)) {
			this.motionY += (this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F;
		}

		if (this.isSprinting()) {
			float f = this.rotationYaw * 0.017453292F;
			this.motionX -= MathHelper.sin(f) * 0.2F;
			this.motionZ += MathHelper.cos(f) * 0.2F;
		}

		this.isAirBorne = true;
		ForgeHooks.onLivingJump(this);
		this.triggerAchievement(StatList.jumpStat);
	}
	
	public boolean moving() {
		return this.moveForward > 0.0 | this.moveStrafing > 0.0;
	}

	public float getSpeed() {
		final float vel = (float) Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		return vel;
	}
	
	public void setSpeed(final double speed) {
        this.motionX = -MathHelper.sin(this.getDirection()) * speed;
        this.motionZ = MathHelper.cos(this.getDirection()) * speed;
    }
	
    public float getDirection() {
        float yaw = this.rotationYaw;
        final float forward = this.moveForward;
        final float strafe = this.moveStrafing;
        yaw += ((forward < 0.0f) ? 180 : 0);
        
        if (strafe < 0.0f) {
            yaw += ((forward < 0.0f) ? -45.0f : ((forward == 0.0f) ? 90.0f : 45.0f));
        }
        
        if (strafe > 0.0f) {
            yaw -= ((forward < 0.0f) ? -45.0f : ((forward == 0.0f) ? 90.0f : 45.0f));
        }
        
        return yaw * 0.017453292f;
    }
    
    public void setMoveSpeed(final double speed) {
        double forward = this.movementInput.moveForward;
        double strafe = this.movementInput.moveStrafe;
        float yaw = this.rotationYaw;
        
        if (forward == 0.0 && strafe == 0.0) {
        	this.motionX = 0.0;
        	this.motionZ = 0.0;
        } else {
			if (forward != 0.0) {
				if (strafe > 0.0) {
					yaw += ((forward > 0.0) ? -45 : 45);
				} else if (strafe < 0.0) {
					yaw += ((forward > 0.0) ? 45 : -45);
				}
				strafe = 0.0;
				if (forward > 0.0) {
					forward = 1.0;
				} else if (forward < 0.0) {
					forward = -1.0;
				}
			}
			
            this.motionX = forward * speed * Math.cos(Math.toRadians((double)(yaw + 90.0f))) + strafe * speed * Math.sin(Math.toRadians((double)(yaw + 90.0f)));
            this.motionZ = forward * speed * Math.sin(Math.toRadians((double)(yaw + 90.0f))) - strafe * speed * Math.cos(Math.toRadians((double)(yaw + 90.0f)));
        }
    }
    
    public void setYaw(double yaw) {
    	this.rotationYaw = (float) yaw;
    }
    
    public void setPitch(double pitch) {
    	this.rotationPitch = (float) pitch;
    }
}