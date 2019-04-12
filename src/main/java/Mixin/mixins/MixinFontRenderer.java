package Mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.minecraft.client.gui.FontRenderer;
import net.tntchina.clientbase.event.EventManager;
import net.tntchina.clientbase.event.events.TextEvent;

@Mixin(FontRenderer.class)
public abstract class MixinFontRenderer {

	@ModifyVariable(method = "renderString", at = @At("HEAD"), ordinal = 0)
	private String renderString(String string) {
		if (string == null) {
			return string;
		}
		
		TextEvent textEvent = new TextEvent(string);
		EventManager.callEvent(textEvent);
		return textEvent.getStr();
	}

	@ModifyVariable(method = "getStringWidth", at = @At("HEAD"), ordinal = 0)
	private String getStringWidth(String string) {
		if (string == null) {
			return string;
		}
		
		TextEvent textEvent = new TextEvent(string);
		EventManager.callEvent(textEvent);
		return textEvent.getStr();
	}
}