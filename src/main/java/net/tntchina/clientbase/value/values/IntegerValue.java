package net.tntchina.clientbase.value.values;

import net.tntchina.clientbase.value.Value;
import net.tntchina.clientbase.value.impl.IIntegerValue;

public class IntegerValue extends Value<Long> implements IIntegerValue {

	private long maxValue, minValue;
	
	public IntegerValue(long object, String name, long maxValue, long minValue) {
		super(object, name);
		this.maxValue = maxValue;
		this.minValue = minValue;
	}
	
	public int getMinValue() {
		return Long.valueOf(this.minValue).intValue();
	}
	
	public int getMaxValue() {
		return Long.valueOf(this.maxValue).intValue();
	}

	public int getValue() {
		return this.getObject().intValue();
	}
	
	public void setMaxValue(long value) { 
		this.maxValue = value;
	}
	
	public void setMinValue(long value) {
		this.minValue = value;
	}

	public int getIntValue() {
		return this.getValue();
	}

	public void setInt(int value) {
		super.setObject(Long.valueOf(value));
	}
}