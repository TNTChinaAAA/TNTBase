package net.tntchina.inputFix.impl;

import org.lwjgl.input.Keyboard;

import net.tntchina.inputFix.interfaces.IGuiScreen;
import net.tntchina.inputFix.interfaces.IGuiScreenFix;

public class GuiScreenFixWindows implements IGuiScreenFix {

	public void handleKeyboardInput(IGuiScreen gui) {
		char c = Keyboard.getEventCharacter();
		int k = Keyboard.getEventKey();
		
		if (Keyboard.getEventKeyState() || (k == 0 && Character.isDefined(c)) && k != Keyboard.KEY_NEXT) {
			gui.keyTyped(c, k);
		}
	}
}