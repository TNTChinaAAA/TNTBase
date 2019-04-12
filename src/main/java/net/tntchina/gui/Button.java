package net.tntchina.gui;

/**
 * the component button(ÈÝÆ÷µÄ°´Å¥)
 * @author TNTChina
 */
public class Button extends Component {
	
	protected Label label;
	private int ID = -1;
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public Button(Label label) {
		this.label = label;
		this.onInitButton();
	}

	public Button(String name) {
		this(new Label(name));
	}

	public void setLabel(Label label) {
		this.label = label;
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
	
	public void setLabelWidth(int width) {
		this.label.setWidth(width);
	}
	
	public void setLabelHeight(int height) {
		this.label.setHeight(height);
	}
	
	public String getName() {
		return this.getLabel().getName();
	}
	
	public void onTickButton() {
		
	}
	
	public void onInitButton() {
		
	}
}