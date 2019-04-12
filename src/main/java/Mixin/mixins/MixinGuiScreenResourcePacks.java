package Mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import Mixin.impl.IGuiScreenResourcePacks;
import net.minecraft.client.gui.GuiResourcePackAvailable;
import net.minecraft.client.gui.GuiResourcePackSelected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.tntchina.util.GuiUtil;

@Mixin(GuiScreenResourcePacks.class)
public abstract class MixinGuiScreenResourcePacks extends GuiScreen implements IGuiScreenResourcePacks {
	
	@Shadow
    private GuiResourcePackAvailable availableResourcePacksList;

    @Shadow
    private GuiResourcePackSelected selectedResourcePacksList;
	
	@Overwrite
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawBackground(true, true);
    	this.availableResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
        this.selectedResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.title", new Object[0]), this.width / 2, 16, 16777215);
        this.drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.folderInfo", new Object[0]), this.width / 2 - 77, this.height - 26, 8421504);
        super.drawScreen(mouseX, mouseY, partialTicks);
	}

	public void drawBackground(boolean b, boolean c) {
		GuiUtil.drawBackground(b, c, new ResourceLocation("background/bg.jpg"));
	}
}
