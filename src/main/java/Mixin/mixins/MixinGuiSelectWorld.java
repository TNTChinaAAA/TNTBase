package Mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import Mixin.impl.IGuiSelectWorld;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.util.ResourceLocation;
import net.tntchina.util.GuiUtil;

@Mixin(GuiSelectWorld.class)
public abstract class MixinGuiSelectWorld extends GuiScreen implements GuiYesNoCallback, IGuiSelectWorld {
	
	@Inject(method = "drawScreen", at = @At("HEAD"))
	public void drawScreen(CallbackInfo callbackInfo) {
		this.drawBackground(false, true);
	}
	
	public void drawBackground(boolean b, boolean c) {
		GuiUtil.drawBackground(b, c, new ResourceLocation("background/bg.jpg"));
	}
}