package net.tntchina.clientbase.value.values;

import net.tntchina.clientbase.value.Value;
import net.tntchina.clientbase.value.impl.IBooleanValue;

public class BooleanValue extends Value<Boolean> implements IBooleanValue {

	public BooleanValue(Boolean object, String name) {
		super(object, name);
	}
	
	public void Toggled() {
		this.setObject(!this.getObject());
	}

	public boolean getBooleanValue() {
		return this.getObject().booleanValue();
	}

	public boolean getValue() {
		return this.getObject().booleanValue();
	}

	public void setBoolean(boolean value) {
		super.setObject(value);
	}
}