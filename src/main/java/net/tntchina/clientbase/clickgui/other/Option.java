package net.tntchina.clientbase.clickgui.other;

import java.util.HashMap;
import java.util.Map;

import net.tntchina.gui.Button;
import net.tntchina.gui.Panel;

public class Option extends Panel<Button> {
	
	public final Map<Button, IntegerUtil> YMap = new HashMap<Button, IntegerUtil>(); 
	
	boolean state = false;
	
	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
	public void Toggled() {
		this.setState(!this.getState());
	}

	public void put(Button button, IntegerUtil integerUtil) {
		this.YMap.put(button, integerUtil);
	}
	
	public Option() {
		super("+");
	}
	
	public boolean canShow() {
		return true;
	}
}
