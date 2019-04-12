package net.tntchina.clientbase.clickgui.other;

import net.tntchina.gui.Component;

public class ProcessBar extends Component {
	
	private double value, minValue, maxValue;
	
	public ProcessBar(double value, double maxValue, double minValue) {
		this.value = value;
		this.maxValue = maxValue;
		this.minValue = minValue;
	}
	
	public void setValue(double value) {
		if (value > this.maxValue | value < this.minValue) {
			return;
		}

		this.value = value;
	}
	
	public double getValue() {
		return this.value;
	}
	
	public double getMinValue() {
		return this.minValue;
	}
	
	public double getMaxValue() {
		return this.maxValue;
	}
}
