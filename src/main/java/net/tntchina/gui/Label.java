package net.tntchina.gui;

public class Label extends Component {
	
	private String name = "";
	
	public Label(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean hasName() {
		return !this.name.equals("");
	}
}