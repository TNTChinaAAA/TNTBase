package Mixin.mixins;

import java.util.UUID;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import Mixin.impl.IEntityPlayerMP;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.tntchina.clientbase.module.ModuleManager;
import net.tntchina.clientbase.module.modules.exploit.CanPVP;

@Mixin(EntityPlayerMP.class)
public abstract class MixinEntityPlayerMP implements IEntityPlayerMP {
	
	private EntityPlayerMP theMP;
	
	@Inject(method = "canPlayersAttack", at = @At("HEAD"), cancellable = true)
	private void CanPvP(CallbackInfoReturnable<Boolean> callbackInfo) {
		if (ModuleManager.getModuleState(CanPVP.class) && this.isServer()) {
			callbackInfo.setReturnValue(true);
		}
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	private void setup(CallbackInfo callbackInfo) {
		this.theMP = (EntityPlayerMP) ((Object) this);
	}
	
	public boolean isServer() {
		if (MinecraftServer.getServer() == null) {
			return false;
		}
		
		UUID uuid = Minecraft.getMinecraft().getSession().getProfile().getId();
		ServerConfigurationManager config = MinecraftServer.getServer().getConfigurationManager();
		EntityPlayerMP playerMP = config.getPlayerByUUID(uuid);
		return this.theMP.equals(playerMP);
	}
}
