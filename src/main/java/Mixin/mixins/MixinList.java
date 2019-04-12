package Mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSelectWorld.List;
import net.minecraft.util.ResourceLocation;
import net.tntchina.util.GuiUtil;
import net.minecraft.client.gui.GuiSlot;

@Mixin(List.class)
public abstract class MixinList extends GuiSlot {

	public MixinList(Minecraft mcIn, int width, int height, int topIn, int bottomIn, int slotHeightIn) {
		super(mcIn, width, height, topIn, bottomIn, slotHeightIn);
	}
	
	@Overwrite
	protected void drawBackground() {
		GuiUtil.drawBackground(false, false, new ResourceLocation("background/bg.jpg"));
	}
}
