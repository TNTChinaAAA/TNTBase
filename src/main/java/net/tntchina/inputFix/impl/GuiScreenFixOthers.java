package net.tntchina.inputFix.impl;

import javax.swing.JOptionPane;
import org.lwjgl.input.Keyboard;
import com.google.common.base.Strings;

import net.tntchina.inputFix.interfaces.IGuiScreen;
import net.tntchina.inputFix.interfaces.IGuiScreenFix;

public class GuiScreenFixOthers implements IGuiScreenFix {

	@Override
	public void handleKeyboardInput(IGuiScreen gui) {
		char c = Keyboard.getEventCharacter();
		int k = Keyboard.getEventKey();
		
		if (Keyboard.getEventKeyState() || (k == 0 && Character.isDefined(c))) {
			if (k == 88) {
				for (char c1 : Strings.nullToEmpty(JOptionPane.showInputDialog("")).toCharArray()) {
					gui.keyTyped(c1, 0);
				}
				
				return;
			}
			
			gui.keyTyped(c, k);
		}
	}
}