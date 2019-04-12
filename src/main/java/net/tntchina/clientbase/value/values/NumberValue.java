package net.tntchina.clientbase.value.values;

import net.tntchina.clientbase.value.Value;
import net.tntchina.clientbase.value.impl.INumberValue;

public class NumberValue extends Value<Double> implements INumberValue {

	private double maxValue, minValue;
	
	public NumberValue(double object, String name, double maxValue, double minValue) {
		super(object, name);
		this.maxValue = maxValue;
		this.minValue = minValue;
	}
	
	public double getMinValue() {
		return this.minValue;
	}
	
	public double getMaxValue() {
		return this.maxValue;
	}

	public double getValue() {
		return this.getObject().doubleValue();
	}

	public void setMaxValue(double d) {
		this.maxValue = d;
	}

	public double getMaxDoubleValue() {
		return this.getMaxValue();
	}

	public double getMinDoubleValue() {
		return this.getMinValue();
	}

	public double getDoubleValue() {
		return this.getValue();
	}

	public void setDouble(double value) {
		super.setObject(value);
	}
}