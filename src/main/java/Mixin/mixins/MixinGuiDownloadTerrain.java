package Mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.tntchina.util.GuiUtil;

@Mixin(GuiDownloadTerrain.class)
public abstract class MixinGuiDownloadTerrain extends GuiScreen {
	
	@Overwrite
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		GuiUtil.drawBackground(false, true, new ResourceLocation("background/bg.jpg"));
		this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingTerrain", new Object[0]), this.width / 2, this.height / 2 - 50, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}