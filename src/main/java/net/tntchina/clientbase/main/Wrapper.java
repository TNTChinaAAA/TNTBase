package net.tntchina.clientbase.main;

import net.tntchina.clientbase.clickgui.CustomGUI;

public class Wrapper {
    
	private static final EnableClickGui clickGUI = new EnableClickGui("ClickGui", 54, null);
	private static final CustomGUI customUI = new CustomGUI();
	
	public static final EnableClickGui getClickGUIModule() {
		return Wrapper.clickGUI;
	}
	
	public static final CustomGUI getCustomUI() {
		return Wrapper.customUI;
	}
}