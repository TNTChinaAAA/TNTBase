package net.tntchina.clientbase.clickgui.other;

import net.tntchina.clientbase.value.values.ClickValue;
import net.tntchina.gui.Button;
import net.tntchina.gui.Label;

public class ClickValueButton extends Button {
	
	private ClickValue value;
	
	public ClickValueButton(ClickValue value) {
		super(new Label(value.getName()));
		this.value = value;
	}
	
	public String getName() {
		return this.value.getName();
	}
	
	public Enum<?> getType() {
		return this.value.getType();
	}
	
	public void onTickButton() {
		this.value.getEvent().onClick();
	}
}