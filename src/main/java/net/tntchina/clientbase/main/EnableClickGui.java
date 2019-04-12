package net.tntchina.clientbase.main;

import net.tntchina.clientbase.clickgui.*;
import net.tntchina.clientbase.module.*;

public class EnableClickGui extends Module {

	public ClickGui clickGui = new ClickGui();
	
	public EnableClickGui(String name, int key, ModuleCategory category) {
		super(name, key, category);
	}
	
	public void onEnable() {
		if (this.mc.currentScreen == null) {
			this.displayClickGui();
		} else {
			if (!(this.mc.currentScreen instanceof ClickGui)) {
				this.displayClickGui();
			}
		}
	}
	
	public void displayClickGui() {
		this.mc.displayGuiScreen(this.clickGui);
	}
	
	public void setup() {
		this.clickGui.init();
	}
	
	public void setState(boolean state) {
		this.state = state;
		
		if (state) {
			this.onEnable();
		} else {
			this.onDisable();
		}
	}
	
	public ClickGui getClickGui() {
		return this.clickGui;
	}
}