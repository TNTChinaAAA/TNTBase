package Mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.GuiSpectator;
import net.tntchina.clientbase.event.EventManager;
import net.tntchina.clientbase.event.events.Render2DEvent;

@Mixin(GuiSpectator.class)
public abstract class MixinGuiSpectator {

    @Inject(method = "renderTooltip", at = @At("RETURN"))
    private void render2D(CallbackInfo callbackInfo) {
        EventManager.callEvent(new Render2DEvent());
    }
}