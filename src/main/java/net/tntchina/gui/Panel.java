package net.tntchina.gui;

import java.util.*;

public class Panel<T extends Button> extends Component {
	
	private List<T> buttonList = new ArrayList<T>();
	private String panelName = "";
	private Label label;
	
	public Panel(String name) {
		this.label = new Label(name);
		this.panelName = name;
	}
	
	public boolean hasPanelName() {
		return !this.panelName.equals("");
	}
	
	public String getPanelName() {
		return this.panelName;
	}
	
	public void setPanelName(String panelName) {
		this.panelName = panelName;
	}
	
	public void addButton(T button) {
		this.buttonList.add(button);
	}
	
	public List<T> getButtons() {
		return this.buttonList;
	}
	
	public Label getLabel() {
		return this.label;
	}
	
	public void setLabelX(int x) {
		this.label.setX(x);
	}
	
	public void setLabelY(int y) {
		this.label.setY(y);
	}
}
