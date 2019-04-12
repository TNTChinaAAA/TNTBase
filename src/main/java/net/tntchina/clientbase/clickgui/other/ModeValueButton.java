package net.tntchina.clientbase.clickgui.other;

import java.util.ArrayList;
import java.util.List;

import net.tntchina.clientbase.value.values.ModeValue;
import net.tntchina.gui.Button;
import net.tntchina.gui.Label;

public class ModeValueButton extends Button {
	
	private List<ModeButton> buttons = new ArrayList<ModeButton>();
	private ModeValue value;
	private boolean state = false;
	
	public ModeValueButton(ModeValue value) {
		super(new Label("+"));
		this.value = value;
	}
	
	public ModeValue getValue() {
		return this.value;
	}
	
	public List<ModeButton> getButtons() {
		return this.buttons;
	}
	
	public void Toggled() {
		this.state = !this.state;
	}
	
	public boolean getState() {
		return this.state;
	}

	public void addButtons(ModeButton modeButton) {
		this.buttons.add(modeButton);
	}

	public void setValue(String name) {
		this.value.setObject(name);
	}
}