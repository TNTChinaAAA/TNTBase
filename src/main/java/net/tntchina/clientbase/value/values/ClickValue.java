package net.tntchina.clientbase.value.values;

public class ClickValue extends CustomUIValue {
	
	private ClickEvent event;
	
	public ClickValue(String name, Enum<?> type, ClickEvent event) {
		super(name, name, type);
		this.event = event;
	}

	public ClickEvent getEvent() {
		return this.event;
	}

	public void setEvent(ClickEvent event) {
		this.event = event;
	}

	public static interface ClickEvent {
		
		void onClick();
	}
}