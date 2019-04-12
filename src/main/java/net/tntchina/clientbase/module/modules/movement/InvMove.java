package net.tntchina.clientbase.module.modules.movement;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.GuiChat;
import net.tntchina.clientbase.module.*;

public class InvMove extends Module {

	public InvMove(String name, int key, ModuleCategory Categorys) {
		super(name, key, Categorys);
	}

	public void onUpdate() {
		if (this.getState() && this.mc.currentScreen != null && !(this.mc.currentScreen instanceof GuiChat)) {
			if (this.getCurrentKey() >= 256) {
				return;
			}
			
			if (Keyboard.isKeyDown(17)) {
				this.mc.gameSettings.keyBindForward.pressed = true;
			} else {
				this.mc.gameSettings.keyBindForward.pressed = false;
			}
			if (Keyboard.isKeyDown(31)) {
				this.mc.gameSettings.keyBindBack.pressed = true;
			} else {
				this.mc.gameSettings.keyBindBack.pressed = false;
			}
			if (Keyboard.isKeyDown(32)) {
				this.mc.gameSettings.keyBindRight.pressed = true;
			} else {
				this.mc.gameSettings.keyBindRight.pressed = false;
			}

			if (Keyboard.isKeyDown(30)) {
				this.mc.gameSettings.keyBindLeft.pressed = true;
			} else {
				this.mc.gameSettings.keyBindLeft.pressed = false;
			}
			
			if (Keyboard.isKeyDown(57)) {
				this.mc.gameSettings.keyBindJump.pressed = true;
			} else {
				this.mc.gameSettings.keyBindJump.pressed = false;
			}
		}
	}
}
