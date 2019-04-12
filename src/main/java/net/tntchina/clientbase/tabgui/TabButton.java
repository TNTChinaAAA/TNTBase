package net.tntchina.clientbase.tabgui;

import net.tntchina.clientbase.module.Module;
import net.tntchina.gui.Button;
import net.tntchina.gui.Label;

/**
 * the tab 's button(这个Tab类别的按钮)
 * @author TNTChina
 */
public class TabButton extends Button {

	private volatile Module module;
	
	public TabButton(Module module) {
		super(new Label(module.getName()));
		this.module = module;
	}

	public Module getModule() {
		return this.module;
	}
}
