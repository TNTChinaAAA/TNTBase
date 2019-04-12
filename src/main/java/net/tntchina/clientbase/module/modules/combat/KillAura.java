package net.tntchina.clientbase.module.modules.combat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import Mixin.impl.IEntityPlayerSP;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.tntchina.clientbase.event.EventManager;
import net.tntchina.clientbase.event.events.AttackEntityEvent;
import net.tntchina.clientbase.event.events.CancellableEvent;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.clientbase.value.values.BooleanValue;
import net.tntchina.clientbase.value.values.IntegerValue;
import net.tntchina.clientbase.value.values.NumberValue;
import net.tntchina.util.MathHelper;
import net.tntchina.util.RotationUtils2;

public class KillAura extends Module {

	private static KillAura killaura;
	private NumberValue cps = new NumberValue(10, "CPS", 20, 1);
	public NumberValue range = new NumberValue(4.5, "Range", 6.0, 1.0);
	private IntegerValue attackTargetNumber = new IntegerValue(1, "AttackNumber", 4L, 1L);
	private IntegerValue hurtTime = new IntegerValue(5, "HurtTime", 10, 1);
	private BooleanValue canAttackMob = new BooleanValue(true, "AttackMob");
	private BooleanValue autoBlock = new BooleanValue(false, "AutoBlock");
	private BooleanValue aimEntity = new BooleanValue(true, "AimEntity");
	
	public KillAura(String name, int key, ModuleCategory Categorys) {
		super(name, key, Categorys);
		KillAura.setKillAura(this);
	}

	public boolean hasTimeReached() {
		return this.timeUtil.hasTimeReached(MathHelper.div(1000, KillAura.this.cps.getObject().doubleValue()));
	}
	
	public void onUpdate() {
		if (!this.getState() | this.mc.thePlayer == null | this.mc.theWorld == null) {
			return;
		}
		
		List<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
		
		synchronized (this.getLoaedEntityList()) {
			for (Entity entity : this.getLoaedEntityList()) {
				if (!(entity instanceof EntityLivingBase)) {
					continue;
				}
				
				try {
					if (entity.getEntityId() == -123) {
						continue;
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (!(entity instanceof EntityPlayer) && !this.canAttackMob.getObject().booleanValue()) {
						continue;
					}
					
					EntityLivingBase Living = (EntityLivingBase) entity;
					
					if (!this.isValid(Living)) {
						continue;
					}
					
					if (Living.getName().length() > 16) {
						continue;
					}

					targets.add(Living);
				}
			}
		}
		
		if (this.hasTimeReached()) {
			if (this.sort(targets) && !targets.isEmpty()) {
				for (int i = 0; i < attackTargetNumber.getObject().intValue(); i++) {
					synchronized (targets) {
						if (targets.size() < i + 1) {
							return;
						}

						EntityLivingBase living = targets.get(i);

						if (!this.isValid(living)) {
							return;
						}
						
						if (living.hurtTime < this.hurtTime.getValue()) {
							if (this.aimEntity.getBooleanValue()) {
								float[] facing = RotationUtils2.getRotations(living);
								((IEntityPlayerSP) this.mc.thePlayer).setYaw(facing[0]);
								((IEntityPlayerSP) this.mc.thePlayer).setPitch(facing[1]);
								//this.mc.thePlayer.setRotationYawHead(facing[0]);
							}
							
							this.attackEntity(living);
						}
					}
				}
				
				this.timeUtil.setLastMS();
			}
		}
	}

	public strictfp boolean isValid(EntityLivingBase Living) {
		return !Living.equals(this.getPlayer()) && (Living.canAttackWithItem() | Living instanceof EntityPlayer) && Living.isEntityAlive() && !this.isLong(Living);
	}

	public strictfp boolean sort(List<EntityLivingBase> targets) {
		Collections.sort(targets, new Comparator<EntityLivingBase>() {

			public int compare(EntityLivingBase o1, EntityLivingBase o2) {
				return Float.valueOf(KillAura.this.mc.thePlayer.getDistanceToEntity(o1)).compareTo(Float.valueOf(KillAura.this.mc.thePlayer.getDistanceToEntity(o2)));
			}
		});
		
		return true;
	}

	public strictfp boolean isLong(EntityLivingBase Living) {
		double range = this.range.getValue();
		return this.mc.thePlayer.getDistanceToEntity(Living) >= range;
	}
	
    public static float[] getNeededRotations(Vec3 vec) {
    	final Vec3 eyesPos = new Vec3(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight(), Minecraft.getMinecraft().thePlayer.posZ);
        final double diffX = vec.xCoord - eyesPos.xCoord;
        final double diffY = vec.yCoord - eyesPos.yCoord;
        final double diffZ = vec.zCoord - eyesPos.zCoord;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = Float.parseFloat(String.format("%.2f", (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f));
        final float pitch = Float.parseFloat(String.format("%.2f", (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)))));
        return new float[]{ MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch) };
    }
    
    public static Vec3 getRandomCenter(AxisAlignedBB bb) {
        return new Vec3(bb.minX + (bb.maxX - bb.minX) * 0.8 * Math.random(), bb.minY + (bb.maxY - bb.minY) * 0.8 * Math.random(), bb.minZ + (bb.maxZ - bb.minZ) * 0.8 * Math.random());
    }
    
    public static Vec3 getRandomCenter_static(AxisAlignedBB bb) {
    	return new Vec3(bb.minX + (bb.maxX - bb.minX) * 0.8 * 0.8, bb.minY + (bb.maxY - bb.minY) * 1 * 0.8, bb.minZ + (bb.maxZ - bb.minZ) * 0.8 * 0.8);
    }

	public strictfp void attackEntity(EntityLivingBase Living) {
		int i = 0;

		CancellableEvent event = new AttackEntityEvent(this.mc, Living, this.mc.thePlayer, AttackEntityEvent.State.PRE);
		EventManager.callEvent(event);
		
		if (event.isCancelled()) {
			return;
		}
		
		if (!this.isValid(Living)) {
			return;
		}
		
		this.toggleSword();
		this.autoBlock();
		super.attackEntity(Living);
		
		if (this.mc.thePlayer.isSprinting()) {
			++i;
		}
		
	    Living.addVelocity(-MathHelper.sin((float) (this.mc.thePlayer.rotationYaw * Math.PI / 180.0F)) * i * 0.5F, 0.1D, MathHelper.cos((float) (this.mc.thePlayer.rotationYaw * Math.PI / 180.0F)) * i * 0.5F);
	    this.mc.thePlayer.onCriticalHit(Living);
		this.autoBlock();
	}
	
	public void autoBlock() {
		if (this.autoBlock.getValue()) {
			ItemStack itemstack1 = null;
			
			try {
				itemstack1 = this.mc.thePlayer.inventory.getCurrentItem();
			} catch (Exception e) {
				if (e instanceof NullPointerException) {
					;
				} else {
					e.printStackTrace();
				}
			}
			
			try {
				if (itemstack1 != null) {
					if (itemstack1.getItem() instanceof ItemSword) {
						 this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, itemstack1);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void toggleSword() {
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

	public static KillAura getKillAura() {
		return KillAura.killaura;
	}

	public static void setKillAura(KillAura killaura) {
		KillAura.killaura = killaura;
	}
	
	public double getRange() {
		return this.range.getDoubleValue();
	}
}