package Mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import Mixin.impl.IGuiIngame;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.tntchina.clientbase.event.EventManager;
import net.tntchina.clientbase.event.events.Render2DEvent;

@Mixin(GuiIngame.class)
public abstract class MixinGuiIngame implements IGuiIngame {

	@Shadow
	public GuiNewChat persistantChatGUI;
	
	@Inject(method = "renderTooltip", at = @At("RETURN"))
	public void render2D(CallbackInfo callbackInfo) {
		EventManager.callEvent(new Render2DEvent());
	}

	public GuiNewChat getPersistantChatGUI() {
		return this.persistantChatGUI;
	}
}