package Mixin.mixins;

import java.io.File;
import java.io.IOException;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import Mixin.impl.IFallbackResourceManager;
import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.client.resources.IResource;

@Mixin(FallbackResourceManager.class)
public abstract class MixinFallbackResourceManager implements IFallbackResourceManager {

	public IResource getResource(File location) throws IOException {
		return null;
	}
	
	@Inject(method = "getResource(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/client/resources/IResource;", at = @At("HEAD"), cancellable = true)
	public void IiIII1III1(CallbackInfoReturnable<IResource> callbackInfoReturnable) {
		
	}
}
