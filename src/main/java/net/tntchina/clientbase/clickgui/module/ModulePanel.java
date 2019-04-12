package net.tntchina.clientbase.clickgui.module;

import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.gui.Panel;

public class ModulePanel extends Panel<ModuleButton> {

	private boolean state;
	
	public ModulePanel(ModuleCategory category) {
		super(category.getName());
		this.state = false;
	}

	public boolean getState() {
		return this.state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
}