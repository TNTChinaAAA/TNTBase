package net.tntchina.util;

import org.lwjgl.opengl.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class RenderUtil {
	
	public static final int Enemy = 0;
	public static final int Friend = 1;
	public static final int Other = 2;
	public static final int Target = 3;
	public static final int Team = 4;
	public static final int Item = 5;
	public static final int Box = 6;
	
	public static void entityESPBox(Entity entity, int mode) {
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		
		if(mode == 0) {
			GL11.glColor4d(1 - 2.5, 2.5, 0, 0.5F);
		} else if(mode == 1) {
			GL11.glColor4d(0, 0, 1, 0.5F);
		} else if(mode == 2) {
			GL11.glColor4d(1, 1, 0, 0.5F);
		} else if(mode == 3) {
			GL11.glColor4d(1, 0, 0, 0.5F);
		} else if(mode == 4) {
			GL11.glColor4d(0, 1, 0, 0.5F);
		} else if (mode == 5) {
			GL11.glColor4d(255.0D, 32.0D, 133.0D, 1.0D);
		} else if (mode == 6) {
			GL11.glColor4d(67.0D, 63.0D, 224.0D, 0.5D);
		}

		Minecraft.getMinecraft().getRenderManager();
		RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(entity.boundingBox.minX - 0.05 - entity.posX + (entity.posX - Minecraft.getMinecraft().getRenderManager().renderPosX), entity.boundingBox.minY - entity.posY + (entity.posY - Minecraft.getMinecraft().getRenderManager().renderPosY), entity.boundingBox.minZ - 0.05 - entity.posZ + (entity.posZ - Minecraft.getMinecraft().getRenderManager().renderPosZ), entity.boundingBox.maxX + 0.05 - entity.posX + (entity.posX - Minecraft.getMinecraft() .getRenderManager().renderPosX), entity.boundingBox.maxY + 0.1 - entity.posY + (entity.posY - Minecraft.getMinecraft().getRenderManager().renderPosY), entity.boundingBox.maxZ + 0.05 - entity.posZ + (entity.posZ - Minecraft.getMinecraft().getRenderManager().renderPosZ)));
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public static void blockESPBox(BlockPos blockPos) {
		double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(2.0F);
		GL11.glColor4d(0, 1, 0, 0.15F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4d(0, 0, 1, 0.5F);
		RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
	}
}