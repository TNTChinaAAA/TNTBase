package net.tntchina.clientbase.clickgui;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.tntchina.clientbase.clickgui.module.ModuleButton;
import net.tntchina.clientbase.clickgui.module.ModulePanel;
import net.tntchina.clientbase.clickgui.other.BooleanValueButton;
import net.tntchina.clientbase.clickgui.other.IntegerProcessBar;
import net.tntchina.clientbase.clickgui.other.IntegerValueButton;
import net.tntchina.clientbase.clickgui.other.ModeButton;
import net.tntchina.clientbase.clickgui.other.ModeValueButton;
import net.tntchina.clientbase.clickgui.other.NumberValueButton;
import net.tntchina.clientbase.clickgui.other.Option;
import net.tntchina.clientbase.clickgui.other.ProcessBar;
import net.tntchina.clientbase.font.UnicodeFontRenderer;
import net.tntchina.clientbase.main.TNTBase;
import net.tntchina.clientbase.main.Wrapper;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.clientbase.module.ModuleManager;
import net.tntchina.clientbase.value.Value;
import net.tntchina.clientbase.value.values.BooleanValue;
import net.tntchina.clientbase.value.values.IntegerValue;
import net.tntchina.clientbase.value.values.ModeValue;
import net.tntchina.clientbase.value.values.NumberValue;
import net.tntchina.gui.Button;
import net.tntchina.gui.Label;
import net.tntchina.util.GuiUtil;
import net.tntchina.util.MathHelper;

/**
 * 这个类是ClickGUI, 他继承了GuiScreen(屏幕)，可以通过Minecraft的非静态方法displayGuiScreen映射到屏幕上 
 */
public class ClickGui extends GuiScreen {

	public String enableFont = new Character((char) 167).charValue() + "a";// 并没有用 请忽视
	public String disableFont = new Character((char) 167).charValue() + "c";// 同样没用
	public FontRenderer fontRendererObj;// 这样子FontRendererObj不会空值针异常
	public List<ModulePanel> panels = new ArrayList<ModulePanel>();// 所有的面板
	public final Button button = new Button(new Label("Custom UI"));
	
	public void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == 1) {
			Wrapper.getClickGUIModule().Toggled();
		}
		
		super.keyTyped(typedChar, keyCode);
	}
	
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	public void initButton() {
		ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
		this.button.setWidth(75);
		this.button.setHeight(20);
		this.button.setX(scaled.getScaledWidth() - 75);
		this.button.setY(scaled.getScaledHeight() - 20);
		this.button.getLabel().setWidth(this.getStringWidth(this.button.getName()));
		this.button.getLabel().setHeight(this.getFontHeight());
		this.button.getLabel().setX(this.button.getX() + ((this.button.getWidth() - this.button.getLabel().getWidth()) / 2));
		this.button.getLabel().setY(this.button.getY() + ((this.button.getHeight() - this.button.getLabel().getHeight()) / 2));
	}
	
	public void init() {// 将GUI实例化
		this.fontRendererObj = new UnicodeFontRenderer(new Font("Comic Sans MC", Font.BOLD, 14));//Minecraft.getMinecraft().fontRendererObj;// 赋值
		this.initButton();
		
		for (int i = 0; i < ModuleCategory.values().length; i++) {// 获得所有的Category
			ModuleCategory category = ModuleCategory.values()[i];
			ModulePanel panel = new ModulePanel(category);
			panel.setX(i * 100);
			panel.setY(20);
			panel.setWidth(75);
			panel.setHeight(20);
			panel.setLabelX(panel.getX() + ((panel.getWidth() - this.getStringWidth(panel.getPanelName())) / 2));
			panel.setLabelY(panel.getY() + ((panel.getHeight() - this.getFontHeight()) / 2));
			panel.getLabel().setWidth(this.getStringWidth(panel.getPanelName()));
			panel.getLabel().setHeight(this.getFontHeight());
			
			for (Module m : ModuleManager.getModules()) {
				if (m.getCategory().equals(category)) {// 功能和Category是否同一类型
					ModuleButton button = new ModuleButton(m);
					Option option = new Option();// 设置

					for (Value<?> value : button.getModule().getValues()) {// 变量集 CCBLUEX写的
						if (value instanceof NumberValue) {
							NumberValue numberValue = (NumberValue) value;
							option.addButton(new NumberValueButton(numberValue, new ProcessBar(numberValue.getObject(), numberValue.getMaxValue(), numberValue.getMinValue())));
						} else if (value instanceof BooleanValue) {
							option.addButton(new BooleanValueButton((BooleanValue) value));
						} else if (value instanceof ModeValue) {
							option.addButton(new ModeValueButton((ModeValue) value));
						} else if (value instanceof IntegerValue) {
							IntegerValue integerValue = (IntegerValue) value;
							option.addButton(new IntegerValueButton(integerValue, new IntegerProcessBar(integerValue)));
						}
					}

					if (option.getButtons().size() > 0) {
						option.setX(button.getX() + button.getWidth() - this.getStringWidth("+"));
						option.setY(button.getY() + ((button.getHeight() - this.getFontHeight()) / 2));
						button.setOption(option);
						TNTBase.getLogger().info(m.getName() + " has options.");
					}

					panel.addButton(button);
				}
			}

			for (int j = 0; j < panel.getButtons().size(); j++) {
				ModuleButton button = panel.getButtons().get(j);
				button.setX(panel.getX());
				button.setY(panel.getY() + panel.getHeight() + panel.getHeight() * j);
				button.setWidth(panel.getWidth());
				button.setHeight(panel.getHeight());
				button.setLabelX(button.getX() + ((button.getWidth() - this.getStringWidth(button.getName())) / 2));// 设置标签的X
				button.setLabelY(button.getY() + ((button.getHeight() - this.getFontHeight()) / 2));// 设置标签的Y
				button.setLabelWidth(this.getStringWidth(button.getName()));// 标签的长度
				button.setLabelHeight(this.getFontHeight());// 设置什么标签的高度

				if (button.hasOption()) {
					Option option = button.getOption();
					button.getOption().setX(button.getX() + button.getWidth() - this.getStringWidth("+"));
					button.getOption().setY(button.getY() + ((button.getHeight() - this.getFontHeight()) / 2));
					button.getOption().setWidth(this.getStringWidth("+"));
					button.getOption().setHeight(this.getFontHeight());
					
					for (int k = 0; k < option.getButtons().size(); k++) {
						Button optionButton = option.getButtons().get(k);
						optionButton.setID(k);
						optionButton.setHeight(20);
						optionButton.setWidth(75);
						optionButton.setX(button.getX() + button.getWidth());
						optionButton.setY(button.getY() + k * 20);
						optionButton.getLabel().setWidth(this.getStringWidth(optionButton.getLabel().getName()));
						optionButton.getLabel().setHeight(this.getFontHeight());
						
						if (optionButton instanceof NumberValueButton) {
							NumberValueButton numberValueButton = (NumberValueButton) optionButton;
							ProcessBar bar = numberValueButton.getProcessBar();
							bar.setX(numberValueButton.getX());
							bar.setY(numberValueButton.getY() + this.getFontHeight() + 2);
							bar.setWidth(numberValueButton.getWidth());
							bar.setHeight(this.getFontHeight());
						}
						
						if (optionButton instanceof IntegerValueButton) {
							IntegerValueButton numberValueButton = (IntegerValueButton) optionButton;
							IntegerProcessBar bar = numberValueButton.getProcessBar();
							bar.setX(numberValueButton.getX());
							bar.setY(numberValueButton.getY() + this.getFontHeight() + 2);
							bar.setWidth(numberValueButton.getWidth());
							bar.setHeight(this.getFontHeight());
						}
						
						if (optionButton instanceof BooleanValueButton) {
							BooleanValueButton booleanValueButton = (BooleanValueButton) optionButton;
							booleanValueButton.getLabel().setX(booleanValueButton.getX() + (((booleanValueButton.getWidth() - this.getStringWidth(booleanValueButton.getName())) / 2)));
							booleanValueButton.getLabel().setY(booleanValueButton.getY() + (((booleanValueButton.getHeight() - this.getFontHeight())) / 2));
						}
						
						if (optionButton instanceof ModeValueButton) {
							ModeValueButton modeValueButton = (ModeValueButton) optionButton;
							
							for (String str : modeValueButton.getValue().getStrings()) {
								ModeButton modeButton = new ModeButton(str);
								modeValueButton.addButtons(modeButton);
							}
							
							modeValueButton.getLabel().setX(modeValueButton.getX() + modeValueButton.getWidth() - this.getStringWidth("+"));
							modeValueButton.getLabel().setY(modeValueButton.getY());
							modeValueButton.getLabel().setWidth(this.getStringWidth(modeValueButton.getName()));
							
							for (int p = 0; p < modeValueButton.getButtons().size(); p++) {
								ModeButton modeButton = modeValueButton.getButtons().get(p);
								modeButton.setX(modeValueButton.getX() + modeValueButton.getWidth());
								modeButton.setY(modeValueButton.getY() + p * 20);
								modeButton.setWidth(45);
								modeButton.setHeight(20);
								modeButton.getLabel().setX(modeButton.getX() + ((modeButton.getWidth() - this.getStringWidth(modeButton.getLabel().getName())) / 2));
								modeButton.getLabel().setY(modeButton.getY() + ((modeButton.getHeight() - this.getFontHeight()) / 2));
								modeButton.getLabel().setWidth(this.getStringWidth(modeButton.getLabel().getName()));
								modeButton.getLabel().setHeight(this.getFontHeight());
							}
						}
					}
				}
			}

			this.panels.add(panel);
		}
	}
	
	public void onPanelDrag(ModulePanel panel) {
		for (int j = 0; j < panel.getButtons().size(); j++) {
			ModuleButton button = panel.getButtons().get(j);
			button.setX(panel.getX());
			button.setY(panel.getY() + panel.getHeight() + panel.getHeight() * j);
			button.setLabelX(button.getX() + ((button.getWidth() - this.getStringWidth(button.getName())) / 2));
			button.setLabelY(button.getY() + ((button.getHeight() - this.getFontHeight()) / 2));

			if (button.hasOption()) {
				button.getOption().setX(button.getX() + button.getWidth() - this.getStringWidth("+"));
				button.getOption().setY(button.getY() + ((button.getHeight() - this.getFontHeight()) / 2));
				
				for (int k = 0; k <  button.getOption().getButtons().size(); k++) {
					Button optionButton =  button.getOption().getButtons().get(k);
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
					
					if (optionButton instanceof BooleanValueButton) {
						BooleanValueButton booleanValueButton = (BooleanValueButton) optionButton;
						booleanValueButton.getLabel().setX(booleanValueButton.getX() + (((booleanValueButton.getWidth() - booleanValueButton.getLabel().getWidth()))) / 2);
						booleanValueButton.getLabel().setY(booleanValueButton.getY() + (((booleanValueButton.getHeight() - this.getFontHeight())) / 2));
					}
					
					if (optionButton instanceof ModeValueButton) {
						ModeValueButton modeValueButton = (ModeValueButton) optionButton;
						modeValueButton.getLabel().setX(button.getX() + button.getWidth() + modeValueButton.getWidth() - this.getStringWidth("+"));
						modeValueButton.getLabel().setY(modeValueButton.getY());
						
						for (int p = 0; p < modeValueButton.getButtons().size(); p++) {
							ModeButton modeButton = modeValueButton.getButtons().get(p);
							modeButton.setX(modeValueButton.getX() + modeValueButton.getWidth());
							modeButton.setY(modeValueButton.getY() + p * 20);
							modeButton.getLabel().setX(modeButton.getX() + ((modeButton.getWidth() - this.getStringWidth(modeButton.getLabel().getName())) / 2));
							modeButton.getLabel().setY(modeButton.getY() + ((modeButton.getHeight() - this.getFontHeight()) / 2));
						}
					}
				}
			}
		}
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.initButton();
		/*for (Tab tab : TabManager.getTabs()) {
			if (tab.isDrag()) {
				tab.setX(tab.getDragX() + mouseX);
				tab.setY(tab.getDragY() + mouseY);
				tab.setDrag(false);
				
				List<Tab> tabs = new ArrayList<Tab>(TabManager.getTabs());
				
				for (Tab tab1 : tabs) {
					if (tab1.equals(tab)) {
						continue;
					}
					
					tab1.setX(tab.getX());
					tab1.setY(tab.get);
				}
				
				for (TabButton tabButton : tab.getButtons()) {
					tabButton.setX(tab.getX());
					tabButton.setY(tab.getY() + tabButton.getID() * tabButton.getHeight());
				}
			}
		}*/
		
		for (ModulePanel panel : this.panels) {
			if (panel.isDrag()) {
				panel.setX(mouseX + panel.getDragX());
				panel.setY(mouseY + panel.getDragY());
				panel.setLabelX(panel.getX() + ((panel.getWidth() - this.getStringWidth(panel.getPanelName())) / 2));
				panel.setLabelY(panel.getY() + ((panel.getHeight() - this.getFontHeight()) / 2));
				this.onPanelDrag(panel);
			}

			GuiUtil.drawRect(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight(), new Color(239, 111, 16).hashCode());
			this.drawString(panel.getPanelName(), panel.getLabel().getX(), panel.getLabel().getY(), 16777215);

			for (ModuleButton button : panel.getButtons()) {
				GuiUtil.drawRect(button.getX(), button.getY(), button.getWidth(), button.getHeight(), 0x80000000);
				this.drawString(button.getName(), button.getLabel().getX(), button.getLabel().getY(), button.getModule().getState() ? new Color(252, 44, 205).hashCode() : 0xD3D3D3);

				if (button.hasOption()) {
					Option option = button.getOption();
					this.drawString("+", option.getX(), option.getY(), 0xD3D3D3);

					if (option.getState()) {
						for (Button optionButton : option.getButtons()) {
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
							
							if (optionButton instanceof BooleanValueButton) {
								GuiUtil.drawRect(optionButton.getX(), optionButton.getY(), optionButton.getWidth(), optionButton.getHeight(), 0x80000000);
								BooleanValueButton booleanValueButton = (BooleanValueButton) optionButton;
								this.drawString(booleanValueButton.getName(), booleanValueButton.getLabel().getX(), booleanValueButton.getLabel().getY(), booleanValueButton.getState() ? new Color(252, 44, 205).hashCode() : 0xD3D3D3);
							}
							
							if (optionButton instanceof ModeValueButton) {
								ModeValueButton modeValueButton = (ModeValueButton) optionButton;
								GuiUtil.drawRect(modeValueButton.getX(), modeValueButton.getY(), modeValueButton.getWidth(), modeValueButton.getHeight(), 0x80000000);
								this.drawString(modeValueButton.getValue().getValueName(), modeValueButton.getX(), modeValueButton.getY(), Color.RED.hashCode());
								this.drawString("+", modeValueButton.getLabel().getX(), modeValueButton.getLabel().getY(), 0xD3D3D3);
							
								if (modeValueButton.getState()) {
									for (ModeButton modeButton : modeValueButton.getButtons()) {
										GuiUtil.drawRect(modeButton.getX(), modeButton.getY(), modeButton.getWidth(), modeButton.getHeight(), 0x80000000);
										this.drawString(modeButton.getName(), modeButton.getLabel().getX(), modeButton.getLabel().getY(), modeButton.getName().equals(modeValueButton.getValue().getObject()) ? new Color(252, 44, 205).hashCode() : 0xD3D3D3);
									}
								}
							}
						}
					}
				}
			}
		}
		
		GuiUtil.drawRect(this.button.getX(), this.button.getY(), this.button.getWidth(), this.button.getHeight(), new Color(239, 111, 16).hashCode());
		this.drawString(this.button.getName(), this.button.getLabel().getX(), this.button.getLabel().getY(), 0xD3D3D3);//new Color(252, 44, 205).hashCode());
	}

	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (mouseButton != 0 | mouseX < 0 | mouseY < 0) {
			return;
		}
		
		if (this.button.isHover(mouseX, mouseY)) {
			this.mc.displayGuiScreen(Wrapper.getCustomUI());
		}
		
		/*for (Tab tab : TabManager.getTabs()) {
			if (tab.isHover(mouseX, mouseY)) {
				tab.setDrag(true);
				tab.setDragX(tab.getX() - mouseX);
				tab.setDragY(tab.getY() - mouseY);
			}
		}*/

		for (int i = (this.panels.size() - 1); i > -1; i--) {
			ModulePanel panel = this.panels.get(i);

			if (panel.isHover(mouseX, mouseY)) {
				panel.setDrag(true);
				panel.setDragX(panel.getX() - mouseX);
				panel.setDragY(panel.getY() - mouseY);
			}

			for (ModuleButton button : panel.getButtons()) {
				if (button.getLabel().isHover(mouseX, mouseY)) {
					button.getModule().Toggled();
				}
				
				if (button.hasOption()) {
					if (button.getOption().isHover(mouseX, mouseY)) {
						button.getOption().Toggled();
					}
					
					if (button.getOption().getState()) {
						for (Button optionButton : button.getOption().getButtons()) {
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
							}
							
							if (optionButton instanceof BooleanValueButton) {
								BooleanValueButton booleanValueButton = (BooleanValueButton) optionButton;
								
								if (booleanValueButton.getLabel().isHover(mouseX, mouseY)) {
									booleanValueButton.Toggled();
								}
							}
							
							if (optionButton instanceof ModeValueButton) {
								ModeValueButton modeValueButton = (ModeValueButton) optionButton;
								
								if (modeValueButton.getLabel().isHover(mouseX, mouseY)) {
									modeValueButton.Toggled(); 
								}
								
								for (ModeButton modeButton : modeValueButton.getButtons()) {
									if (modeButton.getLabel().isHover(mouseX, mouseY)) {
										modeValueButton.setValue(modeButton.getName());
									}
								}
							}
						}
					}
				}
			}
		}
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	public void mouseReleased(int mouseX, int mouseY, int state) {
		for (ModulePanel panel : this.panels) {
			panel.setDrag(false);
		}

		super.mouseReleased(mouseX, mouseY, state);
	}

	public int getFontHeight() {
		return this.fontRendererObj.FONT_HEIGHT;
	}

	public int drawString(String text, int x, int y, int color) {
		return this.fontRendererObj.drawString(text, x, y, color);
	}

	public int getStringWidth(String text) {
		return this.fontRendererObj.getStringWidth(text);
	}
}