package net.tntchina.clientbase.value.values;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class YValue extends CustomUIValue {

	public YValue(int value, int maxValue, String name, Enum<?> type) {
		super(value, maxValue, 0, name, type);
	}

	public int getMaxValue() {
		return new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight();
	}
}