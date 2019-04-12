package Mixin.mixins;

import org.spongepowered.asm.mixin.*;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.tntchina.util.GuiUtil;

@Mixin(GuiMultiplayer.class)
public abstract class MixinGuiMultiplayer extends GuiScreen implements GuiYesNoCallback {
	
	@Shadow
	private ServerSelectionList serverListSelector;
	
	@Shadow
	private String hoveringText;
	
	@Overwrite
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.hoveringText = null;
        this.drawBackground(false, true);
        this.serverListSelector.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.title", new Object[0]), this.width / 2, 20, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (this.hoveringText != null) {
            this.drawHoveringText(Lists.newArrayList(Splitter.on("\n").split(this.hoveringText)), mouseX, mouseY);
        }
        
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	private void drawBackground(boolean b, boolean c) {
		GuiUtil.drawBackground(b, c, new ResourceLocation("background/bg.jpg"));
	}
}
