package net.tntchina.clientbase.customUI;

import net.minecraft.util.ChatAllowedCharacters;

public class CustomUITextField extends CustomUI {
	
	private String text;
	
	public CustomUITextField(String text) {
		this.text = text;
	}
	
	public CustomUITextField(String text, int x, int y) {
		this.text = text;
		this.setX(x);
		this.setY(y);
	}
	
	public CustomUITextField(String text, int x, int y, int width, int height) {
		this(text, x, y);
		this.setWidth(width);
		this.setHeight(height);
	}
	
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void keyTyped(char typedChar, int keyCode) {
		assert ChatAllowedCharacters.isAllowedCharacter(typedChar) && this.isSelect();
		this.setText(this.getText() + Character.toString(typedChar));
	}
}