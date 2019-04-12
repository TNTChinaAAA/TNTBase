package net.tntchina.util;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class RotationUtils {
	
	public static float pitch() {
		return Helper.mc.thePlayer.rotationPitch;
	}

	public static void pitch(float pitch) {
		Helper.mc.thePlayer.rotationPitch = pitch;
	}

	public static float yaw() {
		return Helper.mc.thePlayer.rotationYaw;
	}

	public static void yaw(float yaw) {
		Helper.mc.thePlayer.rotationYaw = yaw;
	}
	
    public static float[] getRotations(EntityLivingBase ent) {
        double x = ent.posX;
        double z = ent.posZ;
        double y = ent.posY + (double)(ent.getEyeHeight() / 2.0f);
        return RotationUtils.getRotationFromPosition(x, z, y);
    }

    public static float[] getAverageRotations(List<EntityLivingBase> targetList) {
        double posX = 0.0;
        double posY = 0.0;
        double posZ = 0.0;
        for (Entity ent : targetList) {
            posX += ent.posX;
            posY += ent.boundingBox.maxY - 2.0;
            posZ += ent.posZ;
        }
        return new float[]{RotationUtils.getRotationFromPosition(posX /= (double)targetList.size(), posZ /= (double)targetList.size(), posY /= (double)targetList.size())[0], RotationUtils.getRotationFromPosition(posX, posZ, posY)[1]};
    }

    public static float[] getRotationFromPosition(double x, double z, double y) {
        double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
        double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
        double yDiff = y - Minecraft.getMinecraft().thePlayer.posY - 1.2;
        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(- Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793);
        return new float[]{yaw, pitch};
    }

    public static float getTrajAngleSolutionLow(float d3, float d1, float velocity) {
        float g = 0.006f;
        float sqrt = velocity * velocity * velocity * velocity - g * (g * (d3 * d3) + 2.0f * d1 * (velocity * velocity));
        return (float)Math.toDegrees(Math.atan(((double)(velocity * velocity) - Math.sqrt(sqrt)) / (double)(g * d3)));
    }

    public static float getYawChange(double posX, double posZ) {
        double deltaX = posX - Minecraft.getMinecraft().thePlayer.posX;
        double deltaZ = posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double yawToEntity = deltaZ < 0.0 && deltaX < 0.0 ? 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)) : (deltaZ < 0.0 && deltaX > 0.0 ? -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)) : Math.toDegrees(- Math.atan(deltaX / deltaZ)));
        return MathHelper.wrapAngleTo180_float(- Minecraft.getMinecraft().thePlayer.rotationYaw - (float)yawToEntity);
    }

    public static float getPitchChange(Entity entity, double posY) {
        double deltaX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        double deltaZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double deltaY = posY - 2.2 + (double)entity.getEyeHeight() - Minecraft.getMinecraft().thePlayer.posY;
        double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        double pitchToEntity = - Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return - MathHelper.wrapAngleTo180_float(Minecraft.getMinecraft().thePlayer.rotationPitch - (float)pitchToEntity) - 2.5f;
    }

    public static float getNewAngle(float angle) {
        if ((angle %= 360.0f) >= 180.0f) {
            angle -= 360.0f;
        }
        if (angle < -180.0f) {
            angle += 360.0f;
        }
        return angle;
    }

	public static float[] getBowAngles(Entity entity) {
        double xDelta = entity.posX - entity.lastTickPosX;
        double zDelta = entity.posZ - entity.lastTickPosZ;
        double d = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity);
        d -= d % 0.8;
        double xMulti = 1.0;
        double zMulti = 1.0;
        boolean sprint = entity.isSprinting();
        xMulti = d / 0.8 * xDelta * (sprint ? 1.25 : 1.0);
        zMulti = d / 0.8 * zDelta * (sprint ? 1.25 : 1.0);
        double x = entity.posX + xMulti - Minecraft.getMinecraft().thePlayer.posX;
        double z = entity.posZ + zMulti - Minecraft.getMinecraft().thePlayer.posZ;
        double y = Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight() - (entity.posY + entity.getEyeHeight());
        double dist = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity);
        float yaw = (float) Math.toDegrees(Math.atan2(z, x)) - 90.0f;
        float pitch = (float) Math.toDegrees(Math.atan2(y, dist));
        return new float[]{yaw, pitch};
    }

	public static float[] faceTarget(Entity target, float p_706252, float p_706253, boolean miss) {
		double var4 = target.posX - Helper.mc.thePlayer.posX;
		double var8 = target.posZ - Helper.mc.thePlayer.posZ;
		double var6;
		if ((target instanceof EntityLivingBase)) {
			EntityLivingBase var10 = (EntityLivingBase) target;
			var6 = var10.posY + var10.getEyeHeight() - (Helper.mc.thePlayer.posY + Helper.mc.thePlayer.getEyeHeight());
		} else {
			var6 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0D
					- (Helper.mc.thePlayer.posY + Helper.mc.thePlayer.getEyeHeight());
		}
		
		//Random rnd = new Random();
		double var14 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
		float var12 = (float) (Math.atan2(var8, var4) * 180.0D / 3.141592653589793D) - 90.0F;
		float var13 = (float) -(Math.atan2(var6 - ((target instanceof EntityPlayer) ? 0.25D : 0.0D), var14) * 180.0D
				/ 3.141592653589793D);
		float pitch = changeRotation(Helper.mc.thePlayer.rotationPitch, var13, p_706253);
		float yaw = changeRotation(Helper.mc.thePlayer.rotationYaw, var12, p_706252);
		return new float[] { yaw, pitch };
	}

	public static float changeRotation(float p_706631, float p_706632, float p_706633) {
		float var4 = MathHelper.wrapAngleTo180_float(p_706632 - p_706631);
		if (var4 > p_706633) {
			var4 = p_706633;
		}
		if (var4 < -p_706633) {
			var4 = -p_706633;
		}
		return p_706631 + var4;
	}

	public static double[] getRotationToEntity(Entity entity) {
		double pX = Helper.mc.thePlayer.posX;
		double pY = Helper.mc.thePlayer.posY + Helper.mc.thePlayer.getEyeHeight();
		double pZ = Helper.mc.thePlayer.posZ;
		double eX = entity.posX;
		double eY = entity.posY + entity.height / 2.0F;
		double eZ = entity.posZ;
		double dX = pX - eX;
		double dY = pY - eY;
		double dZ = pZ - eZ;
		double dH = Math.sqrt(Math.pow(dX, 2.0D) + Math.pow(dZ, 2.0D));
		double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0D;
		double pitch = Math.toDegrees(Math.atan2(dH, dY));
		return new double[] { yaw, 90.0D - pitch };
	}

//	public static float[] getRotations(Entity entity) {
//		if (Helper.mc.isGamePaused()) {
//			return new float[] { 0.0F, 90.0F};
//		}
//		
//		if (entity == null) {
//			return null;
//		}
//		
//		double diffX = entity.posX - Helper.mc.thePlayer.posX;
//		double diffZ = entity.posZ - Helper.mc.thePlayer.posZ;
//		double diffY;
//		if (entity instanceof EntityLivingBase) {
//			EntityLivingBase elb = (EntityLivingBase) entity;
//
//			diffY = elb.posY + (elb.getEyeHeight() - 0.4D) - (Helper.mc.thePlayer.posY + Helper.mc.thePlayer.getEyeHeight());
//		} else {
//			diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D - (Helper.mc.thePlayer.posY + Helper.mc.thePlayer.getEyeHeight());
//		}
//		
//		double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
//		float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
//		float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D);
//		return new float[] { yaw, pitch };
//	}

	public static float getDistanceBetweenAngles(float angle1, float angle2) {
		float angle3 = Math.abs(angle1 - angle2) % 360.0F;
		if (angle3 > 180.0F) {
			angle3 = 0.0F;
		}
		return angle3;
	}

	public static float[] grabBlockRotations(BlockPos pos) {
		return getVecRotation(Helper.mc.thePlayer.getPositionVector().addVector(0.0D, Helper.mc.thePlayer.getEyeHeight(), 0.0D), new Vec3(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D));
	}

	public static float[] getVecRotation(Vec3 position) {
		return getVecRotation(
				Helper.mc.thePlayer.getPositionVector().addVector(0.0D, Helper.mc.thePlayer.getEyeHeight(), 0.0D),
				position);
	}

	public static float[] getVecRotation(Vec3 origin, Vec3 position) {
		Vec3 difference = position.subtract(origin);
		double distance = new Vec3(difference.xCoord, 0.0D, difference.zCoord).lengthVector();
		float yaw = (float) Math.toDegrees(Math.atan2(difference.zCoord, difference.xCoord)) - 90.0F;
		float pitch = (float) -Math.toDegrees(Math.atan2(difference.yCoord, distance));
		return new float[] { yaw, pitch };
	}

	public static int wrapAngleToDirection(float yaw, int zones) {
		int angle = (int) (yaw + 360 / (2 * zones) + 0.5D) % 360;
		if (angle < 0) {
			angle += 360;
		}
		return angle / (360 / zones);
	}
}