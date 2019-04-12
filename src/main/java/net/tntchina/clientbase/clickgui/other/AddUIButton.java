package net.tntchina.clientbase.clickgui.other;

import net.tntchina.clientbase.clickgui.CustomGUI;
import net.tntchina.clientbase.clickgui.CustomGUI.CustomCategory;
import net.tntchina.clientbase.customUI.CustomUI;
import net.tntchina.clientbase.main.Wrapper;
import net.tntchina.gui.Button;

public class AddUIButton<T extends CustomUI> extends Button {

	private T customUI;
	
	public AddUIButton(String name, T customUI) {
		super(name);
		this.customUI = customUI;
	}
	
	public CustomUI getCustomUI() {
		return this.customUI;
	}
	
	public void setCustomUI(T customUI) {
		this.customUI = customUI;
	}
	
	public void onTickButton() {
		CustomGUI customGUI = Wrapper.getCustomUI();
		
		customGUI.getPanels().forEach(panel -> {
			if (panel.getPanelName() == CustomCategory.CUSTOMUI.getName()) {
				customGUI.addCustomUI(this.customUI);
			} 
		});
	}
}
