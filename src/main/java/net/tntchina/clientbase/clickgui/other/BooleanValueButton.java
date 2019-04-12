package net.tntchina.clientbase.clickgui.other;

import net.tntchina.clientbase.value.impl.*;
import net.tntchina.gui.*;

public class BooleanValueButton extends Button {
	
	private IBooleanValue value;
	
	public BooleanValueButton(IBooleanValue value) {
		super(new Label(value.getValueName()));
		this.value = value;
	}
	
	public void setValue(boolean value) {
		this.value.setBoolean(value);
	}
	
	public boolean getValue() {
		return this.value.getBooleanValue();
	}
	
	public void Toggled() {
		this.value.Toggled();
	}
	
	public boolean getState() {
		return this.value.getBooleanValue();
	}
}
