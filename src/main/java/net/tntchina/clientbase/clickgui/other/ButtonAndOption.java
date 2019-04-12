package net.tntchina.clientbase.clickgui.other;

import net.tntchina.gui.Button;
import net.tntchina.gui.Label;

public class ButtonAndOption extends Button {

	private Option option;
	
	public ButtonAndOption(Label label) {
		super(label);
	}
	
	public ButtonAndOption(String message) {
		this(new Label(message));
	}
	
	public void setOption(Option option) {
		this.option = option;
	}
	
	public Option getOption() {
		return this.option;
	}
	
	public boolean hasOption() {
		return this.option != null;
	}
}