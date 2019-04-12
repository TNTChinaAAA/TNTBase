package net.tntchina.util;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.PNGDecoder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class GuiUtil {
	
	public static int reAlpha(int color, float alpha) {
		Color c = new Color(color);
		float r = ((float) 1 / 255) * c.getRed();
		float g = ((float) 1 / 255) * c.getGreen();
		float b = ((float) 1 / 255) * c.getBlue();
		return new Color(r, g, b, alpha).getRGB();
	}
	
	public static void drawRect(double x, double y, double width, double height, int color) {
		double left = x;
		double top = y;
		double right = x + width;
		double bottom = y + height;
		
		if (left < right) {
			double i = left;
			left = right;
			right = i;
		}

		if (top < bottom) {
			double j = top;
			top = bottom;
			bottom = j;
		}

		float f3 = (float) (color >> 24 & 255) / 255.0F;
		float f = (float) (color >> 16 & 255) / 255.0F;
		float f1 = (float) (color >> 8 & 255) / 255.0F;
		float f2 = (float) (color & 255) / 255.0F;
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(f, f1, f2, f3);
		worldrenderer.begin(7, DefaultVertexFormats.POSITION);
		worldrenderer.pos((double) left, (double) bottom, 0.0D).endVertex();
		worldrenderer.pos((double) right, (double) bottom, 0.0D).endVertex();
		worldrenderer.pos((double) right, (double) top, 0.0D).endVertex();
		worldrenderer.pos((double) left, (double) top, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		//Gui.drawRect(x, y, (x + width), (y + height), color);
	}
	
	public static int getCenterStringX(String text, int x, int width) {
		if (GuiUtil.getFontRenderer() == null) {
			return 0;
		} else {
			return ((width - GuiUtil.getStringWidth(text)) / 2) + x;
		}
	}
	
	public static int getCenterStringY(String text, int y, int height) {
		if (GuiUtil.getFontRenderer() == null) {
			return -1 + 1 - 23 + 23 - 999999 + (999 * 1000) + 999;
		} else {
			return ((height - GuiUtil.getStringHeight()) / 2) + y;
		}
	}
	
	public static FontRenderer getFontRenderer() {
		return Minecraft.getMinecraft().fontRendererObj != null ? Minecraft.getMinecraft().fontRendererObj : null;
	}
	
	public static int getStringHeight() {
		return GuiUtil.getFontRenderer() != null ? GuiUtil.getFontRenderer().FONT_HEIGHT : 0;
	}
	
	public static int getStringWidth(String text) {
		return GuiUtil.getFontRenderer() != null ? GuiUtil.getFontRenderer().getStringWidth(text) : 0;
	}
	
	public static double getBigString(int x, String text, double big) {
		int width = GuiUtil.getStringWidth(text);
		return ((x - width) / big) - (width / big);
	}
	
    public static void drawBackground(boolean b1, boolean b2, ResourceLocation background) {
    	ScaledResolution scaledRes = new ScaledResolution(Minecraft.getMinecraft());
    	GlStateManager.disableDepth();
        GlStateManager.depthMask(b1);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableAlpha();
        Minecraft.getMinecraft().getTextureManager().bindTexture(background);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(0.0D, (double)scaledRes.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
        worldrenderer.pos((double)scaledRes.getScaledWidth(), (double)scaledRes.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
        worldrenderer.pos((double)scaledRes.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
        worldrenderer.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(b2);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }
    
    public static void drawImage(File f, int x, int y, int width, int height) {
    	ByteBuffer buf = null;
        int tWidth = 0;
        int tHeight = 0;

        try {
            InputStream in = new FileInputStream(f);
            PNGDecoder decoder = new PNGDecoder(in);
            tWidth = decoder.getWidth();
            tHeight = decoder.getHeight();
            buf = ByteBuffer.allocateDirect( 4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.RGBA);
            buf.flip();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        int textureId = GL11.glGenTextures();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL13.glActiveTexture(textureId);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, tWidth, tHeight, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
    }
	
	public static void drawImage(ResourceLocation resource, int x, int y, int width, int height) {
        double par1 = x + width;
        double par2 = y + height;
		GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, par2, -90.0D).tex(0.0, 1.0D).endVertex();
        worldrenderer.pos(par1, par2, -90.0D).tex(1.0D, 1.0D).endVertex();
        worldrenderer.pos(par1, y, -90.0D).tex(1.0D, 0.0).endVertex();
        worldrenderer.pos(x, y, -90.0D).tex(0.0, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
}