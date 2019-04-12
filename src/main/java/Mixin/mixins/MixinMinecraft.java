package Mixin.mixins;

import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.tntchina.clientbase.event.EventManager;
import net.tntchina.clientbase.event.events.BreakBlockEvent;
import net.tntchina.clientbase.event.events.ClientPostStartingEvent;
import net.tntchina.clientbase.event.events.ClientPreStartingEvent;
import net.tntchina.clientbase.event.events.KeyEvent;
import net.tntchina.clientbase.event.events.RightEvent;
import net.tntchina.clientbase.main.TNTBase;
import net.tntchina.clientbase.module.ModuleManager;
import net.tntchina.clientbase.module.modules.world.FastPlace;
import net.tntchina.clientbase.tabgui.TabManager;
import net.tntchina.util.injectorUtil.MinecraftUtil;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

	@Shadow
	public static Logger logger;

	@Inject(method = "<init>", at = @At("RETURN"))
	private void setup(CallbackInfo callbackInfo) {
		TNTBase.InstanceClient();
		Minecraft.getMinecraft().displayWidth = 1300;
		Minecraft.getMinecraft().displayHeight = 705;
		// TODO: TNTChina delete the tow codelines can let window better small.
	}

	@Inject(method = "createDisplay", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V", shift = At.Shift.AFTER))
	private void createDisplay(CallbackInfo callbackInfo) {
		Display.setTitle(TNTBase.getFullName());
	}

	@Inject(method = "setWindowIcon", at = @At("HEAD"), cancellable = true)
	private void setWindowIcon(CallbackInfo callbackInfo) {
		MinecraftUtil.setWindowIcon();
		callbackInfo.cancel();
	}
	
	@Inject(method = "startGame", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;ingameGUI:Lnet/minecraft/client/gui/GuiIngame;", shift = At.Shift.AFTER))
	private void startGame(CallbackInfo callbackInfo) {
		try {
			TNTBase.startClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		EventManager.callEvent(new ClientPostStartingEvent());
	}

	@Inject(method = "startGame", at = @At("HEAD"))
	private void onGameStarting(CallbackInfo callbackInfo) {
		EventManager.callEvent(new ClientPreStartingEvent());
	}
	
	@Inject(method = "shutdown", at = @At("RETURN"))
	private void stopClient(CallbackInfo callbackInfo) {
		try {
			TNTBase.stopClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//EventManager.callEvent(new ClientStoppingEvent());
	}

	@Inject(method = "checkGLError", at = @At("HEAD"), cancellable = true)
	private void stopCheckGLError(String message, CallbackInfo callbackInfo) {
		callbackInfo.cancel();
	}

	@Inject(method = "runGameLoop", at = @At("HEAD"))
	private void runGameLoopManager(CallbackInfo callbackInfo) {
		TNTBase.loadModule();
	}

	@Inject(method = "dispatchKeypresses", at = @At("HEAD"))
	private void GameKeyPresses(CallbackInfo callbackInfo) {
		if (Keyboard.getEventKeyState()) {
			TNTBase.GameKeyPresses();
		}
	}

	@Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;dispatchKeypresses()V", shift = At.Shift.AFTER))
	private void getKey(CallbackInfo callbackInfo) {
		if (Keyboard.getEventKeyState()) {
			int key = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();
			EventManager.callEvent(new KeyEvent(key));
			TNTBase.isPresses(key);
			TabManager.onKeyPresses(key);

			if (key >= 256) {
				return;
			}

			logger.info("Key " + Keyboard.getKeyName(key) + "'s code: " + key);
		}
	}

	@Inject(method = "displayCrashReport", at = @At("HEAD"))
	private void onDisplayCrashReport(CallbackInfo callbackInfo) {
		try {
			TNTBase.stopClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Inject(method = "rightClickMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/InventoryPlayer;getCurrentItem()Lnet/minecraft/item/ItemStack;"), cancellable = true)
	public void rightClickMouse(CallbackInfo callbackInfo) {
		if (ModuleManager.getModuleState(FastPlace.class)) {
			Minecraft.getMinecraft().rightClickDelayTimer = 1;
		}
		
		if (Minecraft.getMinecraft().objectMouseOver != null) {
			EventManager.callEvent(new RightEvent());
		}
	}
	
	@Inject(method = "clickMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;clickBlock(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z", shift = At.Shift.BEFORE))
	public void onBlockBreak(CallbackInfo callbackInfo) {
		BlockPos blockpos = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
		EventManager.callEvent(new BreakBlockEvent(Minecraft.getMinecraft().theWorld.getBlockState(blockpos).getBlock(), blockpos, Minecraft.getMinecraft().objectMouseOver.sideHit));
	}
}