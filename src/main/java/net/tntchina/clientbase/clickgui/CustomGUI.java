package net.tntchina.clientbase.clickgui;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.launchwrapper.LogWrapper;
import net.tntchina.clientbase.clickgui.other.AddUIButton;
import net.tntchina.clientbase.clickgui.other.BooleanValueButton;
import net.tntchina.clientbase.clickgui.other.ButtonAndOption;
import net.tntchina.clientbase.clickgui.other.ClickValueButton;
import net.tntchina.clientbase.clickgui.other.IntegerProcessBar;
import net.tntchina.clientbase.clickgui.other.IntegerValueButton;
import net.tntchina.clientbase.clickgui.other.NumberValueButton;
import net.tntchina.clientbase.clickgui.other.Option;
import net.tntchina.clientbase.clickgui.other.ProcessBar;
import net.tntchina.clientbase.customUI.CustomUI;
import net.tntchina.clientbase.customUI.CustomUITextField;
import net.tntchina.clientbase.font.UnicodeFontRenderer;
import net.tntchina.clientbase.main.TNTBase;
import net.tntchina.clientbase.value.values.ClickValue;
import net.tntchina.clientbase.value.values.CustomUIValue;
import net.tntchina.gui.Button;
import net.tntchina.gui.Label;
import net.tntchina.gui.Panel;
import net.tntchina.util.GuiUtil;
import net.tntchina.util.MathHelper;
import net.tntchina.util.TimeHelper;

public class CustomGUI extends GuiScreen {
	
	public static enum CustomCategory {
		
		ARRAYLIST("ArrayList"), CUSTOMUI("CustomUI"), TITLE("Title"), FONT("Font"), TABGUI("TabGui");
		
		private String name;
		
		CustomCategory(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
	}
	
	public List<CustomUI> customUIs = new ArrayList<CustomUI>();
	public List<CustomUIValue> values = new ArrayList<CustomUIValue>();
	public List<Panel<Button>> panels = new ArrayList<Panel<Button>>();
	public Minecraft mc = Minecraft.getMinecraft();
	public FontRenderer fontRendererObj;
	public TimeHelper timer = new TimeHelper();
	
	public CustomGUI() {
		this.mc = Minecraft.getMinecraft();
	}
	
	public void addValue(CustomUIValue value) {
		this.values.add(value);
	}
	
	public void keyTyped(char typedChar, int keyCode) throws IOException {
		for (CustomUI customUI : this.customUIs) {
			if (customUI instanceof CustomUITextField) {
				CustomUITextField textField = (CustomUITextField) customUI;
				textField.keyTyped(typedChar, keyCode);
			}
		}
		
		super.keyTyped(typedChar, keyCode);
	}
	
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	public void initUI() {
		this.fontRendererObj = new UnicodeFontRenderer(new Font("Comic Sans MC", Font.BOLD, 14));
		
		for (CustomCategory category : CustomCategory.values()) {
			Panel<Button> panel = new Panel<Button>(category.getName());
			
			if (category == CustomCategory.CUSTOMUI) {
				String name = "TNT V" + TNTBase.CLIENT_VERSION;
				CustomUITextField textField = new CustomUITextField(name, 0, 0, this.getStringWidth(name), this.getFontHeight());
				textField.setDefaultLocation();
				AddUIButton<CustomUITextField> add = new AddUIButton<CustomUITextField>("Add Text", textField);
				panel.addButton(add);
			}
			
			for (CustomUIValue value : this.values) {
				if (!value.getType().equals(category)) {
					continue;
				} else {
					if (value.isByte() | value.isLong() | value.isShort() | value.isInteger()) {
						panel.addButton(new IntegerValueButton(value, new IntegerProcessBar(value)));
					}
					
					if (value.isDouble()) {
						panel.addButton(new NumberValueButton(value, new ProcessBar(value.getDoubleValue(), value.getMaxDoubleValue(), value.getMinDoubleValue())));
					}
					
					if (value.isBoolean()) {
						panel.addButton(new BooleanValueButton(value));
					}
					
					if (value instanceof ClickValue) {
						final ClickValue click = (ClickValue) value;
						panel.addButton(new ClickValueButton(click));
					}
				}
			}
			
			this.panels.add(panel);
		}
		
		for (int i = 0; i < this.panels.size(); i++) {
			Panel<Button> panel = this.panels.get(i);
			panel.setX(i * 100);
			panel.setY(20);
			panel.setWidth(75);
			panel.setHeight(20);
			final Label pa_ = panel.getLabel();
			pa_.setX(panel.getX() + ((panel.getWidth() - this.getStringWidth(pa_.getName())) / 2));
			pa_.setY(panel.getY() + ((panel.getHeight() - this.getFontHeight()) / 2));
			
			for (int j = 0; j < panel.getButtons().size(); j++) {
				Button button = panel.getButtons().get(j);
				button.setID(j);
				button.setX(panel.getX());
				button.setY(panel.getY() + panel.getHeight() + panel.getHeight() * j);
				button.setWidth(panel.getWidth());
				button.setHeight(panel.getHeight());
				final Label label = button.getLabel();
				label.setX(button.getX() + ((button.getWidth() - this.getStringWidth(label.getName())) / 2));
				label.setY(button.getY() + ((button.getHeight() - this.getFontHeight()) / 2));
				label.setWidth(this.getStringWidth(label.getName()));
				label.setHeight(this.getFontHeight());
				
				if (button instanceof IntegerValueButton) {
					IntegerValueButton numberValueButton = (IntegerValueButton) button;
					IntegerProcessBar bar = numberValueButton.getProcessBar();
					bar.setX(numberValueButton.getX());
					bar.setY(numberValueButton.getY() + this.getFontHeight() + 2);
					bar.setWidth(numberValueButton.getWidth());
					bar.setHeight(this.getFontHeight());
				}
				
				if (button instanceof BooleanValueButton) {
					;
				}
				
				if (button instanceof NumberValueButton) {
					NumberValueButton numberValueButton = (NumberValueButton) button;
					ProcessBar bar = numberValueButton.getProcessBar();
					bar.setX(numberValueButton.getX());
					bar.setY(numberValueButton.getY() + this.getFontHeight() + 2);
					bar.setWidth(numberValueButton.getWidth());
					bar.setHeight(this.getFontHeight());
				}
				
				if (button instanceof ButtonAndOption) {
					if (((ButtonAndOption) button).hasOption()) {
						Option option = ((ButtonAndOption) button).getOption();
						option.setX(button.getX() + button.getWidth() - this.getStringWidth("+"));
						option.setY(button.getY() + ((button.getHeight() - this.getFontHeight()) / 2));
						option.setWidth(this.getStringWidth("+"));
						option.setHeight(this.getFontHeight());	
						
						for (int k = 0; k < option.getButtons().size(); k++) {
							Button optionButton = option.getButtons().get(k);
							optionButton.setID(k);
							optionButton.setHeight(20);
							optionButton.setWidth(75);
							optionButton.setX(button.getX() + button.getWidth());
							optionButton.setY(button.getY() + k * 20);
							optionButton.getLabel().setWidth(this.getStringWidth(optionButton.getLabel().getName()));
							optionButton.getLabel().setHeight(this.getFontHeight());
							optionButton.getLabel().setX(optionButton.getX() + optionButton.getWidth() - this.getStringWidth(optionButton.getName()));
							optionButton.getLabel().setY(optionButton.getY() + ((optionButton.getHeight() - this.getFontHeight()) / 2));
							
							if (optionButton instanceof IntegerValueButton) {
								IntegerValueButton numberValueButton = (IntegerValueButton) optionButton;
								IntegerProcessBar bar = numberValueButton.getProcessBar();
								bar.setX(numberValueButton.getX());
								bar.setY(numberValueButton.getY() + this.getFontHeight() + 2);
								bar.setWidth(numberValueButton.getWidth());
								bar.setHeight(this.getFontHeight());
							}
							
							if (optionButton instanceof BooleanValueButton) {
								
							}
						}
					}
				}
			}
		}
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		for (CustomUI customUI : this.customUIs) {
			if (customUI.isDrag()) {
				customUI.setX(mouseX + customUI.getDragX());
				customUI.setY(mouseY + customUI.getDragY());
			}
			
			if (customUI.isSelect()) {
				GuiUtil.drawRect(customUI.getX() - 2, customUI.getY() - 2, customUI.getWidth() + 4, customUI.getHeight() + 4, 0x80000000);
			}
			
			if (customUI instanceof CustomUITextField) {
				CustomUITextField textField = (CustomUITextField) customUI;
				this.drawString(textField.getText(), textField.getX(), textField.getY(), -1);
			}
		}
		
		for (Panel<Button> panel : this.panels) {
			if (panel.isDrag()) {
				panel.setX(mouseX + panel.getDragX());
				panel.setY(mouseY + panel.getDragY());
				panel.setLabelX(panel.getX() + ((panel.getWidth() - this.getStringWidth(panel.getPanelName())) / 2));
				panel.setLabelY(panel.getY() + ((panel.getHeight() - this.getFontHeight()) / 2));
			
				for (Button button : panel.getButtons()) {
					button.setX(panel.getX());
					button.setY(panel.getY() + panel.getHeight() + panel.getHeight() * button.getID());
					final Label a = button.getLabel();
					a.setX(button.getX() + ((button.getWidth() - this.getStringWidth(a.getName())) / 2));
					a.setY(button.getY() + ((button.getHeight() - this.getFontHeight()) / 2));
				
					if (button instanceof IntegerValueButton) {
						IntegerValueButton numberValueButton = (IntegerValueButton) button;
						IntegerProcessBar bar = numberValueButton.getProcessBar();
						bar.setY(numberValueButton.getY() + this.getFontHeight() + 2);
						bar.setX(numberValueButton.getX());
					}
					
					if (button instanceof ButtonAndOption) {
						ButtonAndOption bn2 = (ButtonAndOption) button;
						
						if (bn2.hasOption()) {
							bn2.getOption().setX(button.getX() + button.getWidth() - this.getStringWidth("+"));
							bn2.getOption().setY(button.getY() + ((button.getHeight() - this.getFontHeight()) / 2));
						
							for (int k = 0; k <  bn2.getOption().getButtons().size(); k++) {
								Button optionButton =  bn2.getOption().getButtons().get(k);
								optionButton.setX(button.getX() + button.getWidth());
								optionButton.setY(button.getY() + k * 20);
								
								if (optionButton instanceof NumberValueButton) {
									NumberValueButton numberValueButton = (NumberValueButton) optionButton;
									ProcessBar bar = numberValueButton.getProcessBar();
									bar.setY(numberValueButton.getY() + this.getFontHeight() + 2);
									bar.setX(numberValueButton.getX());
								}
								
								if (optionButton instanceof IntegerValueButton) {
									IntegerValueButton numberValueButton = (IntegerValueButton) optionButton;
									IntegerProcessBar bar = numberValueButton.getProcessBar();
									bar.setY(numberValueButton.getY() + this.getFontHeight() + 2);
									bar.setX(numberValueButton.getX());
								}
							}
						}
					}
				}
			}
			
			GuiUtil.drawRect(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight(), new Color(239, 111, 16).hashCode());
			this.drawString(panel.getPanelName(), panel.getLabel().getX(), panel.getLabel().getY(), 16777215);

			for (Button button : panel.getButtons()) {
				GuiUtil.drawRect(button.getX(), button.getY(), button.getWidth(), button.getHeight(), 0x80000000);
				boolean canDrawString = true;
				
				if (button instanceof BooleanValueButton) {
					BooleanValueButton bn = (BooleanValueButton) button;
					this.drawString(button.getName(), button.getLabel().getX(), button.getLabel().getY(), bn.getState() ? new Color(252, 44, 205).hashCode() : 0xD3D3D3);
					canDrawString = false;
				}
				
				if (button instanceof NumberValueButton) {
					canDrawString = false;
					NumberValueButton numberValueButton = (NumberValueButton) button;
					ProcessBar bar = numberValueButton.getProcessBar();
					GuiUtil.drawRect(numberValueButton.getX(), numberValueButton.getY(), numberValueButton.getWidth(), numberValueButton.getHeight() - bar.getHeight(), 0x80000000);
					this.drawString(numberValueButton.getName() + ": " + String.format("%.2f", numberValueButton.getValue()), numberValueButton.getX(), numberValueButton.getY(), 0xD3D3D3);
					GuiUtil.drawRect(bar.getX(), bar.getY(), bar.getWidth(), bar.getHeight(), Color.BLACK.hashCode());
					double valueX = MathHelper.div(bar.getValue() - bar.getMinValue(), bar.getMaxValue() - bar.getMinValue());
					GuiUtil.drawRect(bar.getX(), bar.getY(), (int) (valueX * bar.getWidth()), bar.getHeight(), -12417292);
				} else if (button instanceof IntegerValueButton) {
					canDrawString = false;
					IntegerValueButton numberValueButton = (IntegerValueButton) button;
					IntegerProcessBar bar = numberValueButton.getProcessBar();
					GuiUtil.drawRect(numberValueButton.getX(), numberValueButton.getY(), numberValueButton.getWidth(), numberValueButton.getHeight() - bar.getHeight(), 0x80000000);
					this.drawString(numberValueButton.getName() + ": " + numberValueButton.getValue(), numberValueButton.getX(), numberValueButton.getY(), 0xD3D3D3);
					GuiUtil.drawRect(bar.getX(), bar.getY(), bar.getWidth(), bar.getHeight(), Color.BLACK.hashCode());
					double valueX = MathHelper.div(bar.getValue() - bar.getMinValue(), bar.getMaxValue() - bar.getMinValue());
					GuiUtil.drawRect(bar.getX(), bar.getY(), (int) (valueX * bar.getWidth()), bar.getHeight(), -12417292);
				} else if (button instanceof ButtonAndOption) {
					ButtonAndOption bn = (ButtonAndOption) button;
					
					if (bn.hasOption()) {
						final Option o = bn.getOption();
						this.drawString("+", o.getX(), o.getY(), 0xD3D3D3);
						
						if (o.getState()) {
							for (Button optionButton : o.getButtons()) {
								if (optionButton instanceof NumberValueButton) {
									NumberValueButton numberValueButton = (NumberValueButton) optionButton;
									ProcessBar bar = numberValueButton.getProcessBar();
									GuiUtil.drawRect(numberValueButton.getX(), numberValueButton.getY(), numberValueButton.getWidth(), numberValueButton.getHeight() - bar.getHeight(), 0x80000000);
									this.drawString(numberValueButton.getName() + ": " + String.format("%.2f", numberValueButton.getValue()), numberValueButton.getX(), numberValueButton.getY(), 0xD3D3D3);
									GuiUtil.drawRect(bar.getX(), bar.getY(), bar.getWidth(), bar.getHeight(), Color.BLACK.hashCode());
									double valueX = MathHelper.div(bar.getValue() - bar.getMinValue(), bar.getMaxValue() - bar.getMinValue());
									GuiUtil.drawRect(bar.getX(), bar.getY(), (int) (valueX * bar.getWidth()), bar.getHeight(), -12417292);
								}
								
								if (optionButton instanceof IntegerValueButton) {
									IntegerValueButton numberValueButton = (IntegerValueButton) optionButton;
									IntegerProcessBar bar = numberValueButton.getProcessBar();
									GuiUtil.drawRect(numberValueButton.getX(), numberValueButton.getY(), numberValueButton.getWidth(), numberValueButton.getHeight() - bar.getHeight(), 0x80000000);
									this.drawString(numberValueButton.getName() + ": " + numberValueButton.getValue(), numberValueButton.getX(), numberValueButton.getY(), 0xD3D3D3);
									GuiUtil.drawRect(bar.getX(), bar.getY(), bar.getWidth(), bar.getHeight(), Color.BLACK.hashCode());
									double valueX = MathHelper.div(bar.getValue() - bar.getMinValue(), bar.getMaxValue() - bar.getMinValue());
									GuiUtil.drawRect(bar.getX(), bar.getY(), (int) (valueX * bar.getWidth()), bar.getHeight(), -12417292);
								}
							}
						}
					}
				}
				
				if (canDrawString) {
					this.drawString(button.getName(), button.getLabel().getX(), button.getLabel().getY(), 0xD3D3D3);
				}
			}
		}
	}
	
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (mouseButton == 0) {
			for (CustomUI customUI : this.customUIs) {
				if (customUI.isHover(mouseX, mouseY)) {
					customUI.setDrag(true);
					customUI.setDragX(customUI.getX() - mouseX);
					customUI.setDragY(customUI.getY() - mouseY);
					customUI.addClickTicks();
					
					if (customUI.hasTimeReached(100) && customUI.getClickTicks() % 2 == 0) {
						customUI.setSelect(true);
						
						{
							LogWrapper.info("Successly");
						}
						customUI.setLastMS();
					} 
				} else {
					customUI.setSelect(false);
				}
			}
			
			for (Panel<Button> panel : this.panels) { 
				if (panel.isHover(mouseX, mouseY)) {
					if (panel.isHover(mouseX, mouseY)) {
						panel.setDrag(true);
						panel.setDragX(panel.getX() - mouseX);
						panel.setDragY(panel.getY() - mouseY);
					}
				}
				
				for (Button button : panel.getButtons()) {
					if (button.isHover(mouseX, mouseY)) {
						button.onTickButton();
					}
					
					if (button instanceof ButtonAndOption) {
						ButtonAndOption bn2 = (ButtonAndOption) button;
						
						if (bn2.hasOption()) {
							final Option o = bn2.getOption();
							
							if (o.isHover(mouseX, mouseY)) {
								o.Toggled();
							}
							
							for (Button optionButton : o.getButtons()) {
								if (optionButton instanceof NumberValueButton) {
									NumberValueButton numberValueButton = (NumberValueButton) optionButton;
									
									if (numberValueButton.getProcessBar().isHover(mouseX, mouseY)) {
										double value = MathHelper.div(mouseX - numberValueButton.getProcessBar().getX(), numberValueButton.getProcessBar().getWidth());
										numberValueButton.setValue(Double.parseDouble(String.format("%.2f", numberValueButton.getProcessBar().getMinValue() + value * (numberValueButton.getProcessBar().getMaxValue() - numberValueButton.getProcessBar().getMinValue()))));
									}
								}
								
								if (optionButton instanceof IntegerValueButton) {
									IntegerValueButton integerValueButton = (IntegerValueButton) optionButton;
									
									if (integerValueButton.getProcessBar().isHover(mouseX, mouseY)) {
										double value = MathHelper.div(mouseX - integerValueButton.getProcessBar().getX(), integerValueButton.getProcessBar().getWidth());
										long a = integerValueButton.getProcessBar().getMaxValue() - integerValueButton.getProcessBar().getMinValue();
										long b = MathHelper.round(MathHelper.mul(value, a));//value * (integerValueButton.getProcessBar().getMaxValue() - integerValueButton.getProcessBar().getMinValue()));
										long finalValue = Math.round(integerValueButton.getProcessBar().getMinValue() + b);
										//integerValueButton.getProcessBar().setValue(finalValue);
										integerValueButton.setValue(finalValue);
									}
								} else if (optionButton instanceof NumberValueButton) {
									NumberValueButton numberValueButton = (NumberValueButton) optionButton;
									
									if (numberValueButton.getProcessBar().isHover(mouseX, mouseY)) {
										double value = MathHelper.div(mouseX - numberValueButton.getProcessBar().getX(), numberValueButton.getProcessBar().getWidth());
										numberValueButton.setValue(Double.parseDouble(String.format("%.2f", numberValueButton.getProcessBar().getMinValue() + value * (numberValueButton.getProcessBar().getMaxValue() - numberValueButton.getProcessBar().getMinValue()))));
									}
								}
							}
						}
					} else {
						if (button instanceof IntegerValueButton) {
							IntegerValueButton integerValueButton = (IntegerValueButton) button;
							
							if (integerValueButton.getProcessBar().isHover(mouseX, mouseY)) {
								double value = MathHelper.div(mouseX - integerValueButton.getProcessBar().getX(), integerValueButton.getProcessBar().getWidth());
								long a = integerValueButton.getProcessBar().getMaxValue() - integerValueButton.getProcessBar().getMinValue();
								long b = MathHelper.round(MathHelper.mul(value, a));//value * (integerValueButton.getProcessBar().getMaxValue() - integerValueButton.getProcessBar().getMinValue()));
								long finalValue = Math.round(integerValueButton.getProcessBar().getMinValue() + b);
								integerValueButton.setValue(finalValue);
							}
						}
						
						if (button instanceof NumberValueButton) {
							NumberValueButton numberValueButton = (NumberValueButton) button;
							
							if (numberValueButton.getProcessBar().isHover(mouseX, mouseY)) {
								double value = MathHelper.div(mouseX - numberValueButton.getProcessBar().getX(), numberValueButton.getProcessBar().getWidth());
								numberValueButton.setValue(Double.parseDouble(String.format("%.2f", numberValueButton.getProcessBar().getMinValue() + value * (numberValueButton.getProcessBar().getMaxValue() - numberValueButton.getProcessBar().getMinValue()))));
							}
						}
						
						if (button instanceof BooleanValueButton) {
							BooleanValueButton bn = (BooleanValueButton) button;
							
							if (bn.getLabel().isHover(mouseX, mouseY)) {
								bn.Toggled();
							}
						}
					}
				}
			}
		}
	}
	
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		for (Panel<Button> panel : this.panels) { 
			panel.setDrag(false);
		}
		
		for (CustomUI customUI : this.customUIs) {
			customUI.setDrag(false);
		}
	}
	
	public int getStringWidth(String message) {
		return this.fontRendererObj.getStringWidth(message);
	}
	
	public int drawString(String message, int x, int y, int color) {
		return this.fontRendererObj.drawString(message, x, y, color);
	}
	
	public int getFontHeight() {
		return this.fontRendererObj.FONT_HEIGHT;
	}

	public List<CustomUIValue> getValues() {
		return this.values;
	}

	public List<Panel<Button>> getPanels() {
		return this.panels;
	}

	public void addCustomUI(CustomUI ui) {
		this.customUIs.add(ui);
	}
	
	public List<CustomUI> getCustomUIs() {
		return this.customUIs;
	}
}