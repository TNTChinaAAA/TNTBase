package net.tntchina.clientbase.clickgui.other;

import net.tntchina.clientbase.value.impl.IIntegerValue;
import net.tntchina.gui.*;

public class IntegerProcessBar extends Component {

	private IIntegerValue value;
	private long minValue, maxValue;
	
	public IntegerProcessBar(IIntegerValue value) {
		this.value = value;
		this.maxValue = value.getMaxValue();
		this.minValue = value.getMinValue();
	}
	
	public void setValue(long value) {
		if (value > this.getMaxValue() | value < this.getMinValue()) {
			return;
		}

		this.value.setInt(Long.valueOf(value).intValue());
	}
	
	public long getValue() {
		return this.value.getIntValue();
	}
	
	public long getMinValue() {
		return this.minValue;
	}
	
	public long getMaxValue() {
		return this.maxValue;
	}
}