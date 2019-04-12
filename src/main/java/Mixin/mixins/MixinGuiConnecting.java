package Mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.util.ResourceLocation;
import net.tntchina.util.GuiUtil;

@Mixin(GuiConnecting.class)
public abstract class MixinGuiConnecting extends GuiScreen {
	
	public void drawDefaultBackground() {
		GuiUtil.drawBackground(false, true, new ResourceLocation("background/bg.jpg"));
	}
}
