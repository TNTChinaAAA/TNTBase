package net.tntchina.util;

import net.minecraft.util.BlockPos;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.Entity;
import java.util.HashSet;
import net.minecraft.world.World;

public class RaycastUtils {
	
    @SuppressWarnings("unused")
	public static MovingObjectPosition tracePath(final World world, final float x, final float y, final float z, final float tx, final float ty, final float tz, final float borderSize, final HashSet<Entity> excluded) {
        Vec3 startVec = new Vec3(x, y, z);
        final Vec3 lookVec = new Vec3(tx - x, ty - y, tz - z);
        Vec3 endVec = new Vec3(tx, ty, tz);
        final float minX = (x < tx) ? x : tx;
        final float minY = (y < ty) ? y : ty;
        final float minZ = (z < tz) ? z : tz;
        final float maxX = (x > tx) ? x : tx;
        final float maxY = (y > ty) ? y : ty;
        final float maxZ = (z > tz) ? z : tz;
        final AxisAlignedBB bb = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ).expand(borderSize, borderSize, borderSize);
        final List<Entity> allEntities = world.getEntitiesWithinAABBExcludingEntity(null, bb);
        MovingObjectPosition blockHit = world.rayTraceBlocks(startVec, endVec);
        startVec = new Vec3(x, y, z);
        endVec = new Vec3(tx, ty, tz);
        float maxDistance = (float)endVec.distanceTo(startVec);
        
        if (blockHit != null) {
            maxDistance = (float)blockHit.hitVec.distanceTo(startVec);
        }
        
        Entity closestHitEntity = null;
        float closestHit = Float.POSITIVE_INFINITY;
        float currentHit = 0.0f;
        
        for (final Entity ent : allEntities) {
            if (ent.canBeCollidedWith() && !excluded.contains(ent)) {
                final float entBorder = ent.getCollisionBorderSize();
                AxisAlignedBB entityBb = ent.getEntityBoundingBox();
                
                if (entityBb == null) {
                    continue;
                }
                
                entityBb = entityBb.expand(entBorder, entBorder, entBorder);
                final MovingObjectPosition intercept = entityBb.calculateIntercept(startVec, endVec);
                
                if (intercept == null) {
                    continue;
                }
                
                currentHit = (float)intercept.hitVec.distanceTo(startVec);
                
                if (currentHit >= closestHit && currentHit != 0.0f) {
                    continue;
                }
                
                closestHit = currentHit;
                closestHitEntity = ent;
            }
        }
        
        if (closestHitEntity != null) {
            blockHit = new MovingObjectPosition(closestHitEntity);
        }
        
        return blockHit;
    }
    
    public static AxisAlignedBB GetAABB(final EntityPlayer player, final int cur, final Vec3 look) {
        final boolean isXPos = look.xCoord >= 0.0;
        final boolean isYPos = look.yCoord >= 0.0;
        final boolean isZPos = look.zCoord >= 0.0;
        final int xAdd = isXPos ? 1 : -1;
        final int yAdd = isYPos ? 1 : -1;
        final int zAdd = isZPos ? 1 : -1;
        return new AxisAlignedBB(player.posX + look.xCoord * cur, player.posY + look.yCoord * cur, player.posZ + look.zCoord * cur, player.posX + look.xCoord * cur + xAdd, player.posY + look.yCoord * cur + yAdd, player.posZ + look.zCoord * cur + zAdd);
    }
    
    public static Entity rayCastEntity(final EntityLivingBase target) {
        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        final HashSet<Entity> excluded = new HashSet<Entity>();
        excluded.add(player);
        return tracePathD(Minecraft.getMinecraft().theWorld, player.posX, player.posY + (double)player.getEyeHeight(), player.posZ, target.posX, target.posY + (double)target.getEyeHeight(), target.posZ, 1.0f, excluded).entityHit;
    }
    
    public static EntityPlayer Raycast(final int range, final EntityPlayer player) {
        final Vec3 v = player.getLookVec().normalize();
        EntityPlayer playerRet = null;
        
        for (int i = 1; i < range; ++i) {
            final AxisAlignedBB aabb = GetAABB(player, i, v);
            final List<Entity> list = player.worldObj.<Entity>getEntitiesWithinAABB((Class<? extends Entity>)EntityLivingBase.class, aabb);
            
            if (list.iterator().hasNext()) {
                final EntityLivingBase el = (EntityLivingBase)list.get(0);
                if (el instanceof EntityPlayer && el != player) {
                    playerRet = (EntityPlayer)el;
                    break;
                }
            }
        }
        
        return playerRet;
    }
    
    public static MovingObjectPosition tracePathD(final World w, final double posX, final double posY, final double posZ, final double v, final double v1, final double v2, final float borderSize, final HashSet<Entity> exclude) {
        return tracePath(w, (float)posX, (float)posY, (float)posZ, (float)v, (float)v1, (float)v2, borderSize, exclude);
    }
    
    public static MovingObjectPosition rayCast(final EntityPlayerSP player, final double x, final double y, final double z) {
        final HashSet<Entity> excluded = new HashSet<Entity>();
        excluded.add(player);
        return tracePathD(player.worldObj, player.posX, player.posY + (double)player.getEyeHeight(), player.posZ, x, y, z, 1.0f, excluded);
    }
    
    public static boolean canSeeBlock(final BlockPos pos) {
        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        final MovingObjectPosition position = Minecraft.getMinecraft().theWorld.rayTraceBlocks(new Vec3(player.posX, player.posY + player.getEyeHeight(), player.posZ), new Vec3((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()));
        return position != null && position.getBlockPos() != null && position.getBlockPos().equals(pos);
    }
}
