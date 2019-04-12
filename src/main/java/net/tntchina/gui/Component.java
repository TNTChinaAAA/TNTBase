/**
 * @author TNTChina
 * the package is Minecraft gui api
 * (这个包是我的世界GUI的API)
 */
package net.tntchina.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

/**
 * all component super
 * (所有容器的对象)
 * @author TNTChina
 */
public class Component {
	
	protected volatile Minecraft mc = Minecraft.getMinecraft();
	protected volatile int x = 0;
	protected volatile int y = 0;
	protected volatile int width = 0;
	protected volatile int height = 0;
	protected volatile int dragX = 0, dragY = 0;
	protected volatile int showHeight = 0;
	protected volatile boolean drag = false;
	protected volatile boolean show = false;
	
	public boolean canShow() {
		return this.show;
	}
	
	public void setShow(boolean show) {
		this.show = show;
	}
	
	public int getShowHeight() {
		return this.showHeight;
	}
	
	public void setShowHeight(int showHeight) {
		this.showHeight = showHeight;
	}
	
	public int getDragX() {
		return this.dragX;
	}

	/**
	 * set the component's dragX 
	 */
	public void setDragX(int dragX) {
		this.dragX = dragX;
	}

	public int getDragY() {
		return this.dragY;
	}

	
	/**
	 * set the component's dragY
	 */
	public void setDragY(int dragY) {
		this.dragY = dragY;
	}

	public boolean isDrag() {
		return drag;
	}

	public void setDrag(boolean drag) {
		this.drag = drag;
	}

	/**
	 * @param mouseX mouseClick X
	 * @param mouseY mouseClick Y
	 * @param fontHeight font's height
	 * @param fontWidth font's width
	 * @return Gui is Click this;
	 */
	
	public boolean isHover(int mouseX, int mouseY) {
		 return mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() && mouseY <= this.getY() + this.getHeight();
	}
	
	public FontRenderer getFontRenderer() {
		return Minecraft.getMinecraft().fontRendererObj;
	}
	
	/**
	 * set the component's x 
	 */
	
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * set the component's y 
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * @return Component X
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * @return Component Y
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 *  @return Component Width
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * @return Component Height
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * set Component Width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * set Component Height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
}