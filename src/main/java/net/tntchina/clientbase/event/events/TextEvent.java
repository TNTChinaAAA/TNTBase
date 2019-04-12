package net.tntchina.clientbase.event.events;

import net.tntchina.clientbase.event.Event;

public class TextEvent extends Event {
	
	private String str;
	
	public TextEvent(String str) {
		this.str = str;
	}

	public String getStr() {
		return this.str;
	}

	public void setStr(String str) {
		this.str = str;
	}
}