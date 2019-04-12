package net.tntchina.clientbase.module.modules.movement;

import java.util.Arrays;
import java.util.Random;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.tntchina.clientbase.event.EventTarget;
import net.tntchina.clientbase.event.events.MotionUpdateEvent;
import net.tntchina.clientbase.event.events.UpdateEvent;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.clientbase.value.values.BooleanValue;
import net.tntchina.clientbase.value.values.ModeValue;
import net.tntchina.clientbase.value.values.NumberValue;
import net.tntchina.util.MathHelper;
import net.tntchina.util.TimeUtil;

public class Fly extends Module {

	private static Fly fly;
	private ModeValue value = new ModeValue("Vanalia", "Mode", Arrays.asList("AAC", "Vanalia", "Hypixel", "AirJump", "LatestHypixel", "Zoom")) {
		
		public void setObject(String object) {
			super.setObject(object);
			
			if (Fly.this.getState()) {
				if (object.equalsIgnoreCase("Zoom")) {
					Fly.this.onZoomEnable();
				} else if (object.equalsIgnoreCase("Hypixel")) {
					Fly.this.onHypixelEnable();
				}
			};
		};
	};
	public NumberValue speed = new NumberValue(1.01, "Speed", 2.0, 1.0);
	public int delay1 = 0, delay2 = 0, delay3 = 0, delay4 = 0;
	public int counter, level;
	public double moveSpeed, lastDist;
	public BooleanValue viewbob = new BooleanValue(true, "ViewBob");
	public TimeUtil timer = new TimeUtil();
	public boolean b2;
	
	public Fly(String name, int key, ModuleCategory Categorys) {
		super(name, key, Categorys);
		Fly.fly = this;
	}
	
	public void onHypixelEnable() {
		this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.2D, this.mc.thePlayer.posZ);
	}

	public void onEnable() {
		if (this.getMode().equalsIgnoreCase("Zoom")) {
			this.onZoomEnable();
		}
	}
	
	public void onZoomEnable() {
		if (this.mc.theWorld != null) {
			if (this.mc.thePlayer == null || this.mc.theWorld == null) {
				return;
			}
			
			this.level = 1;
			this.moveSpeed = 0.1D;
			this.b2 = true;
			this.lastDist = 0.0D;
			this.timer.reset();
			this.damagePlayer(1);
			
			if (this.mc.thePlayer.onGround) {
				this.mc.thePlayer.motionY = 0.42f;
			}
		}
	}
	public void damagePlayer(int damage) {
		double offset = 0.0625;
		
		if (damage < 1) {
			damage = 1;
		}
		
		if (damage > MathHelper.floor_double(this.mc.thePlayer.getMaxHealth())) {
			damage = MathHelper.floor_double(this.mc.thePlayer.getMaxHealth());
		}

		if (this.mc.thePlayer != null && this.mc.getNetHandler() != null && this.mc.thePlayer.onGround) {
			for (int i = 0; i <= ((3 + damage) / offset); i++) {
				this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + offset, this.mc.thePlayer.posZ, false));
				this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, (i == ((3 + damage) / offset))));
			}
		}
	}

	public void onUpdate() {
		if (!this.getState() | this.mc.thePlayer == null | this.mc.theWorld == null) {
			return;
		}

		if (this.getMode().equalsIgnoreCase("Vanalia")) {
			this.onVanalia();
		}

		if (this.getMode().equalsIgnoreCase("AAC")) {
			this.onAAC();
		}

		if (this.getMode().equalsIgnoreCase("Hypixel")) {
			this.onHypixel();
		}
		
		if (this.getMode().equalsIgnoreCase("AirJump")) {
			this.onAirJump();
		}
		
		if (this.getMode().equalsIgnoreCase("LatestHypixel")) {
			this.onLatestHypixel();
		} else {
			this.mc.timer.timerSpeed = 1.0F;
		}
	}
	
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
		if (!this.getState() | this.mc.thePlayer == null | this.mc.theWorld == null) {
			return;
		}
		
		if (this.getMode().equalsIgnoreCase("Zoom")) {
			switch (event.getState()) {
				case PRE: {
					this.onZoom();
				};
				
				case POST: {
					double xDist = this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX;
					double zDist = this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ;
					this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
				};
				
				default: {
					;
				};
			};
		}
	}
	
	@EventTarget
	public void onMotionUpdate(MotionUpdateEvent event) {
		if (!this.getState() | this.mc.thePlayer == null | this.mc.theWorld == null) {
			return;
		}
		
		if (this.getMode().equalsIgnoreCase("Zoom")) {
			float forward = this.mc.thePlayer.movementInput.moveForward;
			float strafe = this.mc.thePlayer.movementInput.moveStrafe;
			float yaw = this.mc.thePlayer.rotationYaw;
			double mx = Math.cos(Math.toRadians((double) (yaw + 90.0F)));
			double mz = Math.sin(Math.toRadians((double) (yaw + 90.0F)));
			
			if (forward == 0.0F && strafe == 0.0F) {
				event.setX(0);
				event.setZ(0);
			} else if (forward != 0.0F) {
				if (strafe >= 1.0F) {
					yaw += (float) (forward > 0.0F ? -45 : 45);
					strafe = 0.0F;
				} else if (strafe <= -1.0F) {
					yaw += (float) (forward > 0.0F ? 45 : -45);
					strafe = 0.0F;
				}

				if (forward > 0.0F) {
					forward = 1.0F;
				} else if (forward < 0.0F) {
					forward = -1.0F;
				}
			}
			
			if (this.b2) {
				if (this.level != 1 || this.mc.thePlayer.moveForward == 0.0F && this.mc.thePlayer.moveStrafing == 0.0F) {
					if (this.level == 2) {
						this.level = 3;
						this.moveSpeed *= 2.1499999D;
					} else if (this.level == 3) {
						this.level = 4;
						double difference = (this.mc.thePlayer.ticksExisted % 2 == 0 ? 0.0103D : 0.0123D) * (this.lastDist - getBaseMoveSpeed());
						this.moveSpeed = this.lastDist - difference;
					} else {
						if (this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.boundingBox.offset(0.0D, this.mc.thePlayer.motionY, 0.0D)).size() > 0 || this.mc.thePlayer.isCollidedVertically) {
							this.level = 1;
						}
						
						this.moveSpeed = this.lastDist - this.lastDist / 159.0D;
					}
				} else {
					this.level = 2;
					//int amplifier = this.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? this.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1 : 0;
					double boost = this.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 1.56 : 2.034;
					this.moveSpeed = boost * getBaseMoveSpeed();
				}

				this.moveSpeed = Math.max(this.moveSpeed, getBaseMoveSpeed());
				event.setX(forward * this.moveSpeed * mx + (double) strafe * this.moveSpeed * mz);
				event.setZ(forward * this.moveSpeed * mz - (double) strafe * this.moveSpeed * mx);

				if (forward == 0.0F && strafe == 0.0F) {
					event.setX(0);
					event.setZ(0);
				}
			}
		}
	}
	
	public double getBaseMoveSpeed() {
		double n = 0.2873;
		
		if (this.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
			n *= 1.0 + 0.2 * (this.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
		}
		
		return n;
	}

	public void onDisable() {
		if (this.mc.thePlayer == null | this.mc.theWorld == null) {
			return;
		}

		this.onVanaliaDisable();
		this.onAACDisable();
		this.onHypixelDisable();
		this.mc.timer.timerSpeed = 1.0F;
	}
	
	public void onZoomDisable() {
		this.mc.timer.timerSpeed = 1.0f;
		this.mc.thePlayer.motionX = 0;
		this.mc.thePlayer.motionZ = 0;
		this.level = 1;
		this.moveSpeed = 0.1D;
		this.b2 = false;
		this.lastDist = 0.0D;
		this.timer.reset();
	}
	
	private void onZoom() {
		if (this.viewbob.getValue()) {
			this.mc.thePlayer.cameraYaw = 0.15f;
		}
		
		++this.counter;
		
		if (this.mc.thePlayer.moveForward == 0 && this.mc.thePlayer.moveStrafing == 0) {
			this.mc.thePlayer.setPosition(this.mc.thePlayer.posX + 1.0D, this.mc.thePlayer.posY + 1.0D, this.mc.thePlayer.posZ + 1.0D);
			this.mc.thePlayer.setPosition(this.mc.thePlayer.prevPosX, this.mc.thePlayer.prevPosY, this.mc.thePlayer.prevPosZ);
			this.mc.thePlayer.motionX = 0.0D;
			this.mc.thePlayer.motionZ = 0.0D;
		}
		
		this.mc.thePlayer.motionY = 0.0D;
		if (this.mc.gameSettings.keyBindJump.isPressed())
			this.mc.thePlayer.motionY += 0.5f;
		if (this.mc.gameSettings.keyBindSneak.isPressed())
			this.mc.thePlayer.motionY -= 0.5f;
		if (this.counter != 1 && this.counter == 2) {
			this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0E-10D, this.mc.thePlayer.posZ);
			this.counter = 0;
		}
	}
	
	private void onLatestHypixel() {
		this.onHypixel();
		this.mc.timer.timerSpeed = (float) (this.speed.getObject().doubleValue() * 2);
	}

	private void onHypixelDisable() {
		;
	}

	private void onAACDisable() {
		this.mc.timer.timerSpeed = 1.0F;
	}

	private void onVanaliaDisable() {
		this.mc.thePlayer.capabilities.isFlying = false;
		this.mc.thePlayer.capabilities.setFlySpeed(0.05F);
	}

	public void setPosition(double posX, double posY, double posZ) {
		this.mc.thePlayer.setPosition(posX, posY, posZ);
	}
	
	public void onAirJump() {
		if (!this.mc.thePlayer.onGround) {
			this.mc.thePlayer.onGround = true;
		}
	}

	public void onAAC() {
		this.mc.thePlayer.motionY = 0.0D;
        
		if (this.mc.gameSettings.keyBindJump.pressed) {
			this.mc.thePlayer.motionY = +0.2D;
		}
        
		if (this.mc.gameSettings.keyBindSneak.pressed) {
			this.mc.thePlayer.motionY -= 0.1D;
		}
		
		if (this.timeUtil.hasTimeReached(50L)) {
			this.mc.timer.timerSpeed = 1.0F;
			this.mc.thePlayer.motionY = -0.01D;
			this.timeUtil.setLastMS();
		} else {
			this.mc.timer.timerSpeed = 0.5F;
		}
	}
	
	public void setSpeed(double speed) {
		final EntityPlayerSP player = this.mc.thePlayer;
		double yaw = player.rotationYaw;
		final boolean isMoving = player.moveForward != 0.0f || player.moveStrafing != 0.0f;
		final boolean isMovingForward = player.moveForward > 0.0f;
		final boolean isMovingBackward = player.moveForward < 0.0f;
		final boolean isMovingRight = player.moveStrafing > 0.0f;
		final boolean isMovingLeft = player.moveStrafing < 0.0f;
		final boolean isMovingSideways = isMovingLeft || isMovingRight;
		final boolean isMovingStraight = isMovingForward || isMovingBackward;
		
		if (isMoving) {
			if (isMovingForward && !isMovingSideways) {
				yaw += 0.0;
			} else if (isMovingBackward && !isMovingSideways) {
				yaw += 180.0;
			} else if (isMovingForward && isMovingLeft) {
				yaw += 45.0;
			} else if (isMovingForward) {
				yaw -= 45.0;
			} else if (!isMovingStraight && isMovingLeft) {
				yaw += 90.0;
			} else if (!isMovingStraight && isMovingRight) {
				yaw -= 90.0;
			} else if (isMovingBackward && isMovingLeft) {
				yaw += 135.0;
			} else if (isMovingBackward) {
				yaw -= 135.0;
			}
			
			yaw = Math.toRadians(yaw);
			player.motionX = -Math.sin(yaw) * speed;
			player.motionZ = Math.cos(yaw) * speed;
		}
	}

	public void onHypixel() {
		this.mc.thePlayer.onGround = true;
		
		if (this.mc.thePlayer.moveForward != 0.0F && this.mc.thePlayer.moveStrafing != 0.0F) {
			this.mc.thePlayer.motionY = 0.0D;
		}
			
		if (!(this.mc.thePlayer.isJumping || this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 0.00000001D, this.mc.thePlayer.posZ)).getBlock() instanceof BlockAir)) {
			return;
		}
		
		for (int i = 0; i < 3; i++) {
			this.mc.thePlayer.motionY = 0;
			this.mc.thePlayer.onGround = true;

			final EntityPlayerSP thePlayer = this.mc.thePlayer;
			final double posX3 = this.mc.thePlayer.posX;
			final double y = this.mc.thePlayer.posY + 1.0E-10;
			thePlayer.setPosition(posX3, y, this.mc.thePlayer.posZ);

			if (this.mc.thePlayer.ticksExisted % 3 == 0) {
				final double posX2 = this.mc.thePlayer.posX;
				final double posY2 = this.mc.thePlayer.posY - 1.0E-10;
				thePlayer.setPosition(posX2, posY2, this.mc.thePlayer.posZ);
			}

		}
		
		final double posX4 = this.mc.thePlayer.posX;
		final double posX = posX4 - MathHelper.cos(this.mc.thePlayer.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
		final double posY = this.mc.thePlayer.posY;
		final double posZ2 = this.mc.thePlayer.posZ;
		final double posZ = posZ2 - MathHelper.sin(this.mc.thePlayer.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
		final float f = 0.4f;
		final float n2 = -MathHelper.sin(this.mc.thePlayer.rotationYaw / 180.0f * 3.1415927f);
		final double motionX = n2 * MathHelper.cos(this.mc.thePlayer.rotationPitch / 180.0f * 3.1415927f) * f;
		final float cos = MathHelper.cos(this.mc.thePlayer.rotationYaw / 180.0f * 3.1415927f);
		final double motionZ = cos * MathHelper.cos(this.mc.thePlayer.rotationPitch / 180.0f * 3.1415927f) * f;
		final double motionY = -MathHelper.sin((this.mc.thePlayer.rotationPitch + 2.0f) / 180.0f * 3.1415927f) * f;
		
		for (int i = 0; i < 2; ++i) {
			final WorldClient theWorld = this.mc.theWorld;
			final EnumParticleTypes particleType = (i % 4 == 0) ? (new Random().nextBoolean() ? EnumParticleTypes.CRIT_MAGIC : EnumParticleTypes.CRIT_MAGIC) : (new Random().nextBoolean() ? EnumParticleTypes.HEART : EnumParticleTypes.ENCHANTMENT_TABLE);
			final double xCoord = posX + motionX;
			final double yCoord = posY + motionY;
			final double zCoord = posZ + motionZ;
			final double motionX2 = this.mc.thePlayer.motionX;
			final double motionY2 = this.mc.thePlayer.motionY;
			theWorld.spawnParticle(particleType, xCoord, yCoord, zCoord, motionX2, motionY2, this.mc.thePlayer.motionZ, new int[0]);
		}
	}

	public void onVanalia() {
		this.mc.thePlayer.capabilities.isFlying = true;

		if (this.mc.gameSettings.keyBindJump.isPressed()) {
			this.mc.thePlayer.motionY += 0.2;
		}

		if (this.mc.gameSettings.keyBindSneak.isPressed()) {
			this.mc.thePlayer.motionY -= 0.2;
		}

		if (this.mc.gameSettings.keyBindForward.isPressed()) {
			this.mc.thePlayer.capabilities.setFlySpeed(0.5F + (float) (this.speed.getObject().doubleValue() - 1.0F));
		}
	}

	public String getMode() {
		return this.value.getObject();
	}

	public boolean hasMode() {
		return true;
	}

	public static Fly getInstance() {
		return Fly.fly;
	}
}