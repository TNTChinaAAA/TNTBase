package net.tntchina.clientbase.clickgui.other;

import net.tntchina.gui.Button;
import net.tntchina.gui.Label;

public class BoundButton extends Button {

	private int ComponentX = 0, ComponentY = 0;
	
	public BoundButton(String name, int x, int y) {
		super(new Label(name));
		this.ComponentX = x;
		this.ComponentY = y;
	}

	public int getComponentX() {
		return this.ComponentX;
	}

	public void setComponentX(int componentX) {
		this.ComponentX = componentX;
	}

	public int getComponentY() {
		return this.ComponentY;
	}

	public void setComponentY(int componentY) {
		this.ComponentY = componentY;
	}
}