package Mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.util.ResourceLocation;
import net.tntchina.util.GuiUtil;

@Mixin(GuiLanguage.class)
public abstract class MixinGuiLanguage {
	
	@Inject(method = "drawScreen", at = @At("HEAD"))
	public void drawMyScreen(CallbackInfo callbackInfo) {
		GuiUtil.drawBackground(false, true, new ResourceLocation("background/bg.jpg"));
	}
}
