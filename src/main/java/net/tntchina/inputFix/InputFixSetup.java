package net.tntchina.inputFix;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLCallHook;
import net.tntchina.inputFix.impl.GuiScreenFixOthers;
import net.tntchina.inputFix.impl.GuiScreenFixWindows;
import net.tntchina.inputFix.interfaces.IGuiScreenFix;
import net.tntchina.inputFix.utils.OSDetector;

public class InputFixSetup implements IFMLCallHook {

	public static IGuiScreenFix impl;

	public Void call() throws Exception {
		OSDetector.OS OS = OSDetector.detectOS();
		switch (OS) {
		case Windows:
			impl = new GuiScreenFixWindows();
			break;
		case Linux:
		case Mac:
			try {
				impl = new GuiScreenFixOthers();
			} catch (Throwable t) {
				impl = new GuiScreenFixWindows();
			}

			break;
		case Unknown:

		default:
			break;
		}

		return null;
	}

	public void injectData(Map<String, Object> arg0) {

	}
}