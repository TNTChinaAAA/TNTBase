package net.tntchina.util.injectorUtil;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.Display;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.Util;
import net.tntchina.util.GuiUtil;

public class MinecraftUtil {

	public static void setWindowIcon() {
        if (Util.getOSType() == null) {
        	return;
        }
		
		if (Util.getOSType() == Util.EnumOS.OSX) {
        	return;
        }
		
		Display.setIcon(new ByteBuffer[] { MinecraftUtil.readImageToBuffer(Util.class.getResourceAsStream("/assets/tnt/icon_16x16.png")), MinecraftUtil.readImageToBuffer(Util.class.getResourceAsStream("/assets/tnt/icon_32x32.png")) });
	}
	
	public static void render() {
		final ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
		GuiUtil.drawRect(0, 0, scaled.getScaledWidth(), scaled.getScaledHeight(), Color.WHITE.hashCode());
	}

	public static ByteBuffer readImageToBuffer(InputStream imageStream) {
		BufferedImage bufferedimage = null;

		try {
			bufferedimage = ImageIO.read(imageStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
		ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);

		for (int i : aint) {
			bytebuffer.putInt(i << 8 | i >> 24 & 255);
		}

		bytebuffer.flip();
		return bytebuffer;
	}
}