package net.tntchina.clientbase.value.values;

import net.tntchina.clientbase.value.*;
import net.tntchina.clientbase.value.impl.*;

public class CustomUIValue extends Value<Object> implements IIntegerValue, INumberValue, IBooleanValue {

	private Enum<?> type;
	private Number maxValue, minValue;

	public CustomUIValue(Object object, String name, Enum<?> type) {
		super(object, name);
		this.type = type;
	}

	public CustomUIValue(double value, double maxValue, double minValue, String name, Enum<?> type) {
		this(value, name, type);
		this.maxValue = maxValue;
		this.minValue = minValue;
	}

	public CustomUIValue(float value, float maxValue, float minValue, String name, Enum<?> type) {
		this(value, name, type);
		this.maxValue = maxValue;
		this.minValue = minValue;
	}

	public CustomUIValue(short value, short maxValue, short minValue, String name, Enum<?> type) {
		this(value, name, type);
		this.maxValue = maxValue;
		this.minValue = minValue;
	}

	public CustomUIValue(int value, int maxValue, int minValue, String name, Enum<?> type) {
		this(value, name, type);
		this.maxValue = maxValue;
		this.minValue = minValue;
	}

	public CustomUIValue(byte value, byte maxValue, byte minValue, String name, Enum<?> type) {
		this(value, name, type);
		this.maxValue = maxValue;
		this.minValue = minValue;
	}

	public CustomUIValue(long value, long maxValue, long minValue, String name, Enum<?> type) {
		this(value, name, type);
		this.maxValue = maxValue;
		this.minValue = minValue;
	}

	public boolean isInteger() {
		return this.getObject() instanceof Integer;
	}

	public boolean isDouble() {
		return this.getObject() instanceof Double;
	}

	public boolean isFloat() {
		return this.getObject() instanceof Float;
	}

	public boolean isShort() {
		return this.getObject() instanceof Short;
	}

	public boolean isString() {
		return this.getObject() instanceof String;
	}

	public boolean isByte() {
		return this.getObject() instanceof Byte;
	}

	public boolean isLong() {
		return this.getObject() instanceof Long;
	}

	public boolean isBoolean() {
		return this.getObject() instanceof Boolean;
	}

	public boolean isNumber() {
		return this.getObject() instanceof Number;
	}

	public Enum<?> getType() {
		return this.type;
	}

	public void setType(Enum<?> type) {
		this.type = type;
	}

	public Object getValue() {
		return this.getObject();
	}

	public boolean getBooleanValue() {
		if (this.isBoolean()) {
			return (boolean) this.getObject();
		} else {
			return false;
		}
	}

	public int getMaxValue() {
		return (this.isInteger() | this.isByte() | this.isShort()) ? (int) this.maxValue : 0;
	}

	public int getMinValue() {
		return (this.isInteger() | this.isByte() | this.isShort()) ? (int) this.minValue : 0;
	}

	public int getIntValue() {
		return (this.isInteger() | this.isByte() | this.isShort()) ? (int) this.getObject() : 0;
	}

	public void setInt(int value) {
		if (!this.isInteger() && !this.isByte() && !this.isShort()) {
			return;
		}

		super.setObject(value);
	}

	public double getMaxDoubleValue() {
		return this.isDouble() ? (double) this.maxValue : 0.0;
	}

	public double getMinDoubleValue() {
		return this.isDouble() ? (double) this.minValue : 0.0;
	}

	public double getDoubleValue() {
		return this.isDouble() ? (double) this.getObject() : 0.0;
	}

	public void setDouble(double value) {
		if (this.isDouble()) {
			this.setObject(value);
		}
	}

	public void setBoolean(boolean value) {
		if (this.isBoolean()) {
			this.setObject(value);
		}
	}
	
	public void Toggled() {
		if (this.isBoolean()) {
			this.setBoolean(!this.getBooleanValue());
		}
	}
}