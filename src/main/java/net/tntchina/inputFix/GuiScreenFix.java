package net.tntchina.inputFix;

import net.minecraft.client.gui.GuiScreen;
import net.tntchina.inputFix.interfaces.IGuiScreen;
import org.lwjgl.input.Keyboard;

public class GuiScreenFix {

	private static class Proxy implements IGuiScreen {

		public GuiScreen gui;

		public void keyTyped(char c, int k) {
			Mixin.impl.IGuiScreen s;
			
			try {
				if (this.gui != null) {
					s = (Mixin.impl.IGuiScreen) this.gui;
					s.onKeyTyped(c, k);
				}
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public Proxy setGui(GuiScreen gui) {
			this.gui = gui;
			return this;
		}
	}

	private static final ThreadLocal<Proxy> proxies = new ThreadLocal<Proxy>() {

		protected Proxy initialValue() {
			return new Proxy();
		}
	};

	public static void handleKeyboardInput(GuiScreen gui) {
		Proxy p = proxies.get().setGui(gui);

		if (InputFixSetup.impl != null) {
			InputFixSetup.impl.handleKeyboardInput(p);
		} else if (Keyboard.getEventKeyState()) {
			p.keyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
		}
	
		gui.mc.dispatchKeypresses();
	}
}