package Mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import Mixin.impl.IPlayerControllerMP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldSettings;
import net.tntchina.clientbase.event.EventManager;
import net.tntchina.clientbase.event.events.AttackEntityEvent;
import net.tntchina.clientbase.module.ModuleManager;
import net.tntchina.clientbase.module.modules.combat.Reach;

@Mixin(PlayerControllerMP.class)
public abstract class MixinPlayerControllerMP implements IPlayerControllerMP {

	@Shadow
	private float curBlockDamageMP;
	
	@Shadow
	private WorldSettings.GameType currentGameType;

	@Inject(method = "getBlockReachDistance", at = @At("HEAD"), cancellable = true)
	private void reach(CallbackInfoReturnable<Float> callbackInfo) {
		final Reach reach = ModuleManager.getModule(Reach.class);
		final float a = this.currentGameType.isCreative() ? 5.0F : 4.5F;
		
		if (reach.getState()) {
			callbackInfo.setReturnValue(Math.max(a, reach.getValue()));
		}
	}
	
/*	@Inject(method = "extendedReach", at = @At("HEAD"), cancellable = true)
	private void extendedReach(CallbackInfoReturnable<Boolean> callbackInfo) {
		if (ModuleManager.getModuleState(Reach.class)) {
			callbackInfo.setReturnValue(true);
		}
	}*/
	
	@Inject(method = "attackEntity", at = @At(value = "HEAD"), cancellable = true)
	public void attackEntity(EntityPlayer playerIn, Entity targetEntity, CallbackInfo callbackInfo) {
		AttackEntityEvent event = new AttackEntityEvent(Minecraft.getMinecraft(), targetEntity, playerIn, AttackEntityEvent.State.PRE);
		EventManager.callEvent(event);
		
		if (event.isCancelled()) {
			callbackInfo.cancel();
		}
	}
	
	@Inject(method = "attackEntity", at = @At(value = "RETURN"), cancellable = true)
	public void attackEntity233(EntityPlayer playerIn, Entity targetEntity, CallbackInfo callbackInfo) {
		AttackEntityEvent event = new AttackEntityEvent(Minecraft.getMinecraft(), targetEntity, playerIn, AttackEntityEvent.State.POST);
		EventManager.callEvent(event);
	}
	
	public void setCurBlockDamageMP(float curBlockDamageMP) {
		this.curBlockDamageMP = curBlockDamageMP;
	}
	
	public float getCurBlockDamageMP() {
		return this.curBlockDamageMP;
	}
}
