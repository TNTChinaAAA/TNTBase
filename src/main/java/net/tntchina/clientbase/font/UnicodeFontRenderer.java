package net.tntchina.clientbase.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import java.awt.*;

public class UnicodeFontRenderer extends FontRenderer {
	
	private UnicodeFont font;

	@SuppressWarnings("unchecked")
	public UnicodeFontRenderer(Font awtFont) {
		super(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), false);
		this.font = new UnicodeFont(awtFont);
		this.font.addAsciiGlyphs();
		this.font.getEffects().add(new ColorEffect(Color.WHITE));
		
		try {
			this.font.loadGlyphs();
		} catch (SlickException exception) {
			throw new RuntimeException(exception);
		}
		
		String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
		this.FONT_HEIGHT = this.font.getHeight(alphabet) / 2;
	}

	public int drawString(String string, int x, int y, int color) {
		if (string == null) {
			return (int) 0.0f;
		}
		
		GL11.glPushMatrix();
		GL11.glScaled((double) 0.5, (double) 0.5, (double) 0.5);
		final boolean blend = GL11.glIsEnabled(3042);
		final boolean lighting = GL11.glIsEnabled(2896);
		final boolean texture = GL11.glIsEnabled(3553);
		
		if (!blend) {
			GL11.glEnable(3042);
		}
		
		if (lighting) {
			GL11.glDisable(2896);
		}
		
		if (texture) {
			GL11.glDisable(3553);
		}
		
		x *= 2.0f;
		y *= 2.0f;
		this.font.drawString(x, y, string, new org.newdawn.slick.Color(color));
		
		if (texture) {
			GL11.glEnable(3553);
		}
		
		if (lighting) {
			GL11.glEnable(2896);
		}
		
		if (!blend) {
			GL11.glDisable(3042);
		}
		
		GlStateManager.color(0.0f, 0.0f, 0.0f);
		GL11.glPopMatrix();
		GlStateManager.bindTexture(0);
		return (int) x;
	}

	public int getCharWidth(char c) {
		return this.getStringWidth(Character.toString(c));
	}

	public int getStringWidth(String string) {
		return this.font.getWidth(string) / 2;
	}

	public int drawStringWithShadow(String text, float x, float y, int color) {
		int a = this.drawString(text, x + 1.0f, y + 1.0f, -16777216);
		return Math.max(a, this.drawString(text, x, y, color));
	}

	public int getStringHeight(String string) {
		return this.font.getHeight(string) / 2;
	}

	public int drawCenteredString(String text, int x, int y, int color) {
		return this.drawString(text, x - this.getStringWidth(text) / 2 + 2, y, color);
	}

	public int getRealStringWidth(String string) {
		return this.font.getWidth(StringUtils.stripControlCodes(string)) / 2;
	}

	public void drawCenteredString(String text, float x, float y, int color) {
		this.drawString(text, x - (float) (this.getStringWidth(text) / 2), y, color);
	}

	public int drawString(final String string, float x, float y, final int color) {
		if (string == null) {
			return (int) 0.0f;
		}
		
		GL11.glPushMatrix();
		GL11.glScaled((double) 0.5, (double) 0.5, (double) 0.5);
		final boolean blend = GL11.glIsEnabled(3042);
		final boolean lighting = GL11.glIsEnabled(2896);
		final boolean texture = GL11.glIsEnabled(3553);
		
		if (!blend) {
			GL11.glEnable(3042);
		}
		
		if (lighting) {
			GL11.glDisable(2896);
		}
		
		if (texture) {
			GL11.glDisable(3553);
		}
		
		x *= 2.0f;
		y *= 2.0f;
		this.font.drawString(x, y, string, new org.newdawn.slick.Color(color));
		
		if (texture) {
			GL11.glEnable(3553);
		}
		
		if (lighting) {
			GL11.glEnable(2896);
		}
		
		if (!blend) {
			GL11.glDisable(3042);
		}
		
		GlStateManager.color(0.0f, 0.0f, 0.0f);
		GL11.glPopMatrix();
		GlStateManager.bindTexture(0);
		return (int) x;
	}

	public int getHeight() {
		return this.FONT_HEIGHT;
	}

	@SuppressWarnings("unchecked")
	public void setFont(Font font) {
		this.font = new UnicodeFont(font);
		this.font.addAsciiGlyphs();
		this.font.getEffects().add(new ColorEffect(Color.WHITE));
		
		try {
			this.font.loadGlyphs();
		} catch (SlickException exception) {
			throw new RuntimeException(exception);
		}
		
		String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
		this.FONT_HEIGHT = this.font.getHeight(alphabet) / 2;
	}

	public Font getFont() {
		return this.font.getFont();
	}

	public int getFontHeight() {
		return this.FONT_HEIGHT;
	}
}