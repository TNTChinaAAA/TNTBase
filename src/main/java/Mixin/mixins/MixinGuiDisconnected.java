package Mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.ResourceLocation;
import net.tntchina.util.GuiUtil;

@Mixin(GuiDisconnected.class)
public abstract class MixinGuiDisconnected extends GuiScreen {
	
	public void drawDefaultBackground() {
		GuiUtil.drawBackground(false, true, new ResourceLocation("background/bg.jpg"));
	}
}
