package net.tntchina.clientbase.customUI;

import net.minecraft.client.gui.ScaledResolution;
import net.tntchina.gui.Component;
import net.tntchina.util.TimeHelper;

public abstract class CustomUI extends Component {
	
	private boolean select = false;
	private int clickTicks = 0;
	private TimeHelper timer = new TimeHelper();
	
	public boolean isSelect() {
		return this.select;
	}
	
	public void setSelect(boolean state) {
		this.select = state;
	}
	
	public void toggleSelect() {
		this.setSelect(!this.isSelect());
	}
	
	public int getClickTicks() {
		return this.clickTicks;
	}
	
	public void addClickTicks() {
		this.clickTicks++;
	}

	public void setClickTicks(int clickTicks) {
		this.clickTicks = clickTicks;
	}
	
	public boolean hasTimeReached(double delay) {
		return this.timer.hasTimeReached(delay);
	}
	
	public void setLastMS() {
		this.timer.setLastMS();
	}

	public CustomUI setDefaultLocation() {
		ScaledResolution scaled = new ScaledResolution(this.mc);
		this.setX((scaled.getScaledWidth() - this.getWidth()) / 2);
		this.setY((scaled.getScaledHeight() - this.getHeight()) / 2);
		return this;
	}
}