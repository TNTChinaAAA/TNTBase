package Mixin.mixins;

import java.io.IOException;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import Mixin.impl.IGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.tntchina.clientbase.main.TNTBase;
import net.tntchina.inputFix.GuiScreenFix;

@SideOnly(Side.CLIENT)
@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen implements IGuiScreen {

    @Shadow 
    public Minecraft mc;
    
    public GuiScreen gui;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void onInit(CallbackInfo callbackInfo) {
    	this.gui = (GuiScreen)(Object) (this);
    }
    
    @Inject(method = "sendChatMessage(Ljava/lang/String;Z)V", at = @At("HEAD"), cancellable = true)
    private void chatMessage(String msg, boolean addToChat, CallbackInfo callbackInfo) {
        if(msg.startsWith(".") && !msg.equals(".")) {
        	TNTBase.callCommand(msg);
        	callbackInfo.cancel();
        }
    }
    
    @Inject(method = "handleKeyboardInput", at = @At("HEAD"), cancellable = true)
    public void handleKeyboardInput(CallbackInfo callbackInfo) {
    	GuiScreenFix.handleKeyboardInput(this.gui);
    	callbackInfo.cancel();
    }

	public void onKeyTyped(char typedChar, int keyCode) {
		try {
			this.keyTyped(typedChar, keyCode);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Shadow 
	public abstract void keyTyped(char typedChar, int keyCode) throws IOException;
}