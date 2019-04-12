package net.tntchina.clientbase.value;

public class Value<T> {

	private T object;
	private String name;
	
	public Value(T object, String name) {
		this.object = object;
		this.name = name;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public String getValueName() {
		return this.name;
	}
	
	public String getName() {
		return this.name;
	}
}
