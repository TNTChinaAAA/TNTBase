package net.tntchina.clientbase.value.values;

import java.util.*;

import net.tntchina.clientbase.value.Value;

public class ModeValue extends Value<String> {

	private List<String> strings = new ArrayList<String>();
	
	public ModeValue(String object, String name, List<String> strings) {
		super(object, name);
		
		if (strings == null) {
			return;
		}
		
		this.strings = strings;
	}
	
	public List<String> getStrings() {
		return this.strings;
	}

	public String getValue() {
		return this.getObject();
	}
}