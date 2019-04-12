package net.tntchina.clientbase.clickgui.module;

import net.tntchina.clientbase.clickgui.other.Option;
import net.tntchina.clientbase.module.Module;
import net.tntchina.gui.Button;
import net.tntchina.gui.Label;

public class ModuleButton extends Button {

	private Module m;
	private Option option;
	
	public ModuleButton(Module m) {
		super(new Label(m.getName()));
		this.m = m;
	}

	public Module getModule() {
		return this.m;
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
