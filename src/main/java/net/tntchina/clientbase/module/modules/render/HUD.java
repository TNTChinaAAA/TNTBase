package net.tntchina.clientbase.module.modules.render;

import static net.tntchina.clientbase.module.modules.render.hud.HUDValueManager.*;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.core.helpers.Strings;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.tntchina.clientbase.clickgui.ClickGui;
import net.tntchina.clientbase.clickgui.CustomGUI;
import net.tntchina.clientbase.event.EventTarget;
import net.tntchina.clientbase.event.events.ClientPostStartingEvent;
import net.tntchina.clientbase.event.events.Render2DEvent;
import net.tntchina.clientbase.font.CFontRenderer;
import net.tntchina.clientbase.font.UnicodeFontRenderer;
import net.tntchina.clientbase.main.TNTBase;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.clientbase.module.ModuleManager;
import net.tntchina.clientbase.tabgui.Tab;
import net.tntchina.clientbase.tabgui.TabButton;
import net.tntchina.clientbase.tabgui.TabManager;
import net.tntchina.gui.Component;
import net.tntchina.util.GuiUtil;

public class HUD extends Module {
	
	public static Random random = new Random();
	public static List<Information> informations = new ArrayList<Information>();
	public UnicodeFontRenderer titleRendererObj;
	public CFontRenderer tabGuiRendererObj;
	public UnicodeFontRenderer fontRendererObj;
	public UnicodeFontRenderer defaultRendererObj;
	public Font defaultFont;
	
	public HUD(String name, int key, ModuleCategory Categorys) {
		super(name, key, Categorys);
		this.state = true;
		this.defaultFont = new Font("Comic Sans MC", Font.BOLD, 22);
	}
	
    private static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f).getRGB();
    }
	
	@EventTarget
	public void onStart(ClientPostStartingEvent event) {
		this.fontRendererObj = new UnicodeFontRenderer(this.defaultFont);
		this.titleRendererObj = new UnicodeFontRenderer(new Font("Comic Sans MC", Font.BOLD, title_size.getIntValue()));
		this.tabGuiRendererObj = new CFontRenderer(new Font("Comic Sans MC", Font.BOLD, 15), true, true);
		this.defaultRendererObj = new UnicodeFontRenderer(new Font("Comic Sans MC", Font.BOLD, 14));
		
		if (!Strings.isEmpty(TNTBase.getFileManager().fontLoaderPath)) {
			Font f = null;
			
			try {
				f = Font.createFont(0, new File(TNTBase.getDir(), TNTBase.getFileManager().fontLoaderPath)).deriveFont(0, array_size.getIntValue());
			} catch (Exception e) {
				return;
			}
			
			this.fontRendererObj.setFont(f);
			this.titleRendererObj.setFont(f.deriveFont(0, title_size.getIntValue()));
			this.tabGuiRendererObj.setFont(f.deriveFont(0, 15));
			return;
		}
	}
	
    public void renderInformation() {
    	ScaledResolution scaled = new ScaledResolution(this.mc);
    	
    	if (HUD.informations.isEmpty()) {
    		return;
    	}
    	
    	Information information = HUD.informations.get(0);
    	
    	if (information.getState() && information.getX() == -20) {
    		HUD.informations.remove(information);
    		return;
    	}
    	
    	information.show();
    	int x = scaled.getScaledWidth() - information.getX() - 20;
    	int y = scaled.getScaledHeight() - 80;
    	GuiUtil.drawRect(x, y, information.getWidth(), information.getHeight(), Color.BLACK.hashCode());
    	GuiUtil.drawRect(x + 1, y + 1, information.getWidth() - 2, information.getHeight() - 2, -12417292);
    	GuiUtil.drawRect(x + 1 + 5, y + 1 + 4, information.getWidth() - 12, information.getHeight() - 10, Color.BLACK.hashCode());
    	String showsMessage = (information.getFlag() ? "Enable" : "Disable") + " " + information.getName();
    	this.drawString(showsMessage, x + (((information.getWidth() - this.getStringWidth(showsMessage))) / 2), y + ((information.getHeight() - this.getFontHeight()) /2) , Color.WHITE.hashCode());
    }

	private void renderHotBar() {
 		ScaledResolution scaled = new ScaledResolution(this.mc);
		AtomicInteger index = new AtomicInteger();
		int x = 0;
		int y = scaled.getScaledHeight() - 22;
		GuiUtil.drawRect(x, y, scaled.getScaledWidth(), 22, 0x40000000);
		int len = 2;
		this.defaultRendererObj.drawString("FPS: " + Minecraft.getDebugFPS(), len, y + 2, HUD.rainbow(index.get() * 400));
		index.getAndIncrement();
		len += this.defaultRendererObj.getStringWidth("FPS: " + Minecraft.getDebugFPS() + "   ");
		index.getAndIncrement();
		this.defaultRendererObj.drawString("Username: " + this.mc.thePlayer.getName(), len, y + 2, HUD.rainbow(index.get() * 400));
		index.getAndIncrement();
		y += this.defaultRendererObj.getStringWidth("Username: " + this.mc.thePlayer.getName());
    	
		if (this.mc.getNetHandler() == null) {
    		return;
    	}
    	
    	this.renderPing(index);
    	this.renderPostion(index);
	}
	
	public void renderPostion(AtomicInteger index) {
		ScaledResolution scaled = new ScaledResolution(this.mc);
		int x = scaled.getScaledWidth() - 125;
		int y = scaled.getScaledHeight() - 22;
		this.defaultRendererObj.drawString("X: " + this.getFormat(2, this.mc.thePlayer.posX) + "  Y: " + this.getFormat(2, this.mc.thePlayer.posY) + "  Z: " + this.getFormat(2, this.mc.thePlayer.posZ), x, y, HUD.rainbow(index.get() * 400));
		index.getAndIncrement();
		y += this.defaultRendererObj.getFontHeight() + 2;
		this.defaultRendererObj.drawString(TNTBase.CLIENT_NAME + " " + TNTBase.CLIENT_VERSION + " - by " + TNTBase.CLIENT_AUTHOR, x, y, HUD.rainbow(index.get() * 400));
		index.getAndIncrement();
	}
	
	public String getColorFormat() {
		char a = 167; 
		return new String(new char[] { a });
	}
	
	public int drawStringWithShadow(String text, int x, int y, int color) {
		return this.mc.fontRendererObj.drawStringWithShadow(text, x, y, color);
	}

	public String getFormat(int scale, Object message) {
		return String.format("%." + scale + "f", message);
	}
	
	public void renderPing(AtomicInteger index) {
		if (this.mc == null || this.mc.getNetHandler() == null || this.mc.thePlayer == null || this.mc.getNetHandler().getPlayerInfo(this.mc.thePlayer.getName()) == null || this.mc.theWorld == null) {
			return;
		}
		
		ScaledResolution scaled = new ScaledResolution(this.mc);
		int y = scaled.getScaledHeight() - 9;
    	final TabOverlay tab = new TabOverlay();
    	String message = "Ping: " + this.mc.getNetHandler().getPlayerInfo(this.mc.thePlayer.getName()).getResponseTime() + "ms";
    	this.defaultRendererObj.drawString(message, 2, y, HUD.rainbow(index.get() * 800));
    	index.getAndIncrement();
    	tab.drawPing(5, this.getStringWidth(message) + 11, y, this.mc.getNetHandler().getPlayerInfo(this.mc.thePlayer.getName()));
	}
    
	private void renderTabGui() {
		ScaledResolution scaled = new ScaledResolution(this.mc);
		int tabX = tabgui_right.getBooleanValue() ? scaled.getScaledWidth() - tabgui_x.getIntValue() - 75 /** tab width**/ : tabgui_x.getIntValue();
		
		if (TabManager.getTabs().get(0).getX() != tabX) {
			for (Tab tab : TabManager.getTabs()) {
				if (tabgui_right.getBooleanValue()) {
					tab.setX(tabX);
				} else {
					tab.setX(tabgui_x.getIntValue());
				}
				
				for (int i = 0; i < tab.getButtons().size(); i++) {
					TabButton tabButton = tab.getButtons().get(i);
					tabButton.setX(tab.getX() + tab.getWidth());
				}
			}
		}
		
		if (TabManager.getTabs().get(0).getY() != tabgui_y.getIntValue()) {
			for (Tab tab : TabManager.getTabs()) {
				tab.setY(tabgui_y.getIntValue() + tab.getID() * tab.getHeight());
				
				for (int i = 0; i < tab.getButtons().size(); i++) {
					TabButton tabButton = tab.getButtons().get(i);
					tabButton.setY(tab.getY() + i * tabButton.getHeight());
				}
			}
		}
		
		for (Tab tab : TabManager.getTabs()) {
			int color = tab.equals(TabManager.getCurrentTab()) ? Color.BLACK.hashCode() : 0x80000000;
			GuiUtil.drawRect(tab.getX(), tab.getY(), tab.getWidth(), tab.getHeight(), color);
			
			if (color == Color.BLACK.hashCode()) {
				GuiUtil.drawRect(tab.getX(), tab.getY(), 5, tab.getHeight(), -12417292);
			}
			
			int x = tab.getX() + ((tab.getWidth() - this.tabGuiRendererObj.getStringWidth(tab.getName())) / 2);
			int y = tab.getY() + ((tab.getHeight() - this.tabGuiRendererObj.getHeight()) / 2);
			int fontColor = (color == -12417292) ? 0x80000000 : Color.WHITE.hashCode();
			this.tabGuiRendererObj.drawString(tab.getName(), x, y, fontColor);
			this.tabGuiRendererObj.drawString(">", tab.getX() + tab.getWidth() - this.getStringWidth(">") - 4, y, fontColor);
			
			if (tab.isSelect()) {
				if (tab.getButtons().size() == 0) {
					continue;
				}
				
				for (TabButton button : tab.getButtons()) {
					int color_ = (button.equals(tab.getCurrentButton())) ? Color.BLACK.hashCode() : 0x80000000;
					GuiUtil.drawRect(button.getX(), button.getY(), button.getWidth(), button.getHeight(), color_);
					
					if (color_ == Color.BLACK.hashCode()) {
						GuiUtil.drawRect(button.getX(), button.getY(), 5, button.getHeight(), -12417292);
					}
					
					int x_ = button.getX() + ((button.getWidth() - this.tabGuiRendererObj.getStringWidth(button.getLabel().getName())) / 2);
					int y_ = button.getY() + ((button.getHeight() - this.tabGuiRendererObj.getHeight()) / 2);
					int fontColor_ = (color_ == -12417292) ? 0x80000000 : Color.WHITE.hashCode();
					this.tabGuiRendererObj.drawString(button.getLabel().getName(), x_, y_, fontColor_);
				}
			}
		}
	}
	
	public void makeRender() {
		if (this.mc.currentScreen == null | !(this.mc.currentScreen instanceof GuiChat)) {
			this.renderHotBar();
		}
	}
	
	public void renderArrayList() {
		GlStateManager.pushMatrix();
		ScaledResolution scaled = new ScaledResolution(this.mc);
		int i = array_y.getIntValue();
		AtomicInteger index = new AtomicInteger();
		List<Module> modules = new ArrayList<Module>(ModuleManager.getToggled());
		
		modules.sort((m1, m2) -> {
			String modText1 = m1.getName() + (m1.getMode() == null ? "" : " " + m1.getMode());
			String modText2 = m2.getName() + (m2.getMode() == null ? "" : " " + m2.getMode());
			int width1 = this.fontRendererObj.getStringWidth(modText1);
			int width2 = this.fontRendererObj.getStringWidth(modText2);
			return -Float.compare(width1, width2);
		});
		
		for (Module m : modules) {
			int color = HUD.rainbow(index.get() * 320);
			
			if (array_right.getBooleanValue()) {
				int x = scaled.getScaledWidth() - this.fontRendererObj.getStringWidth(m.getName() + (m.getMode() == null ? "" : " " + m.getMode())) - array_x.getIntValue() - 1;
				GuiUtil.drawRect(x - 1, i, m.hasMode() ? this.fontRendererObj.getStringWidth(m.getName() + " " + m.getMode()) + 2 : this.fontRendererObj.getStringWidth(m.getName()) + 2, this.fontRendererObj.getHeight(), 0x40000000);
				GuiUtil.drawRect(x - 6, i, 5, this.fontRendererObj.getHeight(), color);
				this.fontRendererObj.drawStringWithShadow(m.getName(), x, i, color);
				
				if (m.getMode() != null) {
					this.fontRendererObj.drawStringWithShadow(" " + m.getMode(), scaled.getScaledWidth() - this.fontRendererObj.getStringWidth(" " + m.getMode()) - 1 - array_x.getIntValue(), i, -1);
				}
			} else {
				int x = array_x.getIntValue();
				String str = m.getName() + (m.hasMode() ? " " + m.getMode() : "");
				GuiUtil.drawRect(x, i, this.fontRendererObj.getStringWidth(str) + 2, this.fontRendererObj.getHeight(), 0x40000000);
				GuiUtil.drawRect(x + this.fontRendererObj.getStringWidth(str) + 2, i, 5, this.fontRendererObj.getHeight(), color);
				this.fontRendererObj.drawStringWithShadow(m.getName(), x, i, color);
				
				if (m.hasMode()) {
					this.fontRendererObj.drawStringWithShadow(" " + m.getMode(), x + this.fontRendererObj.getStringWidth(m.getName()), i, -1);
				}
			}
			
			i += this.fontRendererObj.getHeight();
			index.getAndIncrement();
		}
		
		GlStateManager.popMatrix();
	}
	
	@EventTarget
	private void onRender(Render2DEvent event) {
		ScaledResolution scaled = new ScaledResolution(this.mc);
		this.renderTabGui();
		this.renderInformation();
		
		if (this.mc.currentScreen != null) {
			if (!(this.mc.currentScreen instanceof ClickGui | this.mc.currentScreen instanceof CustomGUI)) {
				this.makeRender();
			}
		} else {
			this.makeRender();
		}
		
		if (!this.getState()) {
			return;
		}
		
		if (this.fontRendererObj.getFont().getSize() != array_size.getIntValue()) {
			this.fontRendererObj.setFont(this.fontRendererObj.getFont().deriveFont(0, array_size.getIntValue()));
		}
		
		if (this.titleRendererObj.getFont().getSize() != title_size.getIntValue()) {
			this.titleRendererObj.setFont(this.titleRendererObj.getFont().deriveFont(0, title_size.getIntValue()));
		}
		
		this.renderArrayList();
		GL11.glPushMatrix();
		String name = TNTBase.CLIENT_NAME + TNTBase.SPACE + "V" + TNTBase.CLIENT_VERSION;
		
		if (title_right.getBooleanValue()) {
			this.titleRendererObj.drawString(name, scaled.getScaledWidth() - title_x.getIntValue() - this.titleRendererObj.getStringWidth(name), title_y.getIntValue(), HUD.rainbow(200));
		} else {
			this.titleRendererObj.drawString(name, title_x.getIntValue(), title_y.getIntValue(), HUD.rainbow(200));
		}
		
		GL11.glPopMatrix();
	}
	
	private final int drawString(String text, double x, double y, int color) {
		return this.getFontRenderer().drawString(text, (float) x, (float) y, color, false);
	}
	
	private final int getStringWidth(String text) {
		return this.getFontRenderer().getStringWidth(text);
	}
	
	private final int getFontHeight() {
		return this.getFontRenderer().FONT_HEIGHT;
	}
	
	private final FontRenderer getFontRenderer() {
		return this.mc.fontRendererObj;
	}
	
    public final class TabOverlay extends GuiPlayerTabOverlay {

		public TabOverlay() {
			super(Minecraft.getMinecraft(), Minecraft.getMinecraft().ingameGUI);
		}
    	
		public void drawPing(int x, int width, int y, NetworkPlayerInfo networkPlayerInfoIn) {
			super.drawPing(x, width, y, networkPlayerInfoIn);
		}
    }
	
    public static final class Information extends Component {
    	
    	private boolean flag = false;
    	private String name;
    	private boolean state = false;
    	private boolean show = true;
    	private boolean back = false;
    	
    	public Information(boolean flag, String name) {
    		this.name = name;
    		this.flag = flag;
    	}
    	
    	public void show() {
    		if (!this.getState()) {
    			this.setState(true);
    		}
    		
    		if (this.canShow()) {
    			if (!this.canBack()) {
    				this.setX(this.getX() + 4);
    			} else {
    				this.setX(this.getX() - 4);
    			}
    			
    			if (this.getX() >= this.getWidth()) {
        			this.setBack(true);
        		}
    			
    			if (this.x == -20) {
    				this.setShow(false);
    			}
    		}
    	}
    	
    	public boolean canShow() {
    		return this.show;
    	}
    	
    	public boolean canBack() {
    		return this.back;
    	}
    	
    	public void setBack(boolean back) {
    		this.back = back;
    	}
    	
    	public void setShow(boolean show) {
    		this.show = show;
    	}
    
    	public void setState(boolean state) {
    		this.state = state;
    	}
    	
    	public int getWidth() {
    		return 95;
    	}
    	
    	public int getHeight() {
    		return 30;
    	}
    	
    	public boolean getState() {
    		return this.state;
    	}
    	
    	public boolean getFlag() {
    		return this.flag;
    	}
    	
    	public String getName() {
    		return this.name;
    	}
    }
}