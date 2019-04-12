package net.tntchina.clientbase.module.modules.render.hud;

import java.awt.Font;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.lwjgl.opengl.Display;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.tntchina.clientbase.clickgui.CustomGUI;
import net.tntchina.clientbase.main.TNTBase;
import net.tntchina.clientbase.main.Wrapper;
import net.tntchina.clientbase.module.ModuleManager;
import net.tntchina.clientbase.module.modules.render.HUD;
import net.tntchina.clientbase.value.values.ClickValue;
import net.tntchina.clientbase.value.values.CustomUIValue;
import net.tntchina.clientbase.value.values.XValue;
import net.tntchina.clientbase.value.values.YValue;

public class HUDValueManager {
	
	public static CustomUIValue title_size = new CustomUIValue(22, 72, 12, "BodySize", CustomGUI.CustomCategory.TITLE);
	public static CustomUIValue array_size = new CustomUIValue(22, 72, 12, "ArraySize", CustomGUI.CustomCategory.ARRAYLIST);
	public static CustomUIValue array_right = new CustomUIValue(true, "Right", CustomGUI.CustomCategory.ARRAYLIST);
	public static CustomUIValue title_x = new XValue(0, new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth(), "TitleX", CustomGUI.CustomCategory.TITLE);
	public static CustomUIValue title_y = new YValue(0, new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight(), "TitleY", CustomGUI.CustomCategory.TITLE);
	public static CustomUIValue tabgui_x = new XValue(10, 0, "TabGuiX", CustomGUI.CustomCategory.TABGUI);
	public static CustomUIValue tabgui_y = new XValue(20, 0, "TabGuiY", CustomGUI.CustomCategory.TABGUI);
	public static CustomUIValue array_x = new XValue(0, 0, "ArrayX", CustomGUI.CustomCategory.ARRAYLIST);
	public static CustomUIValue array_y = new YValue(0, 0, "ArrayY", CustomGUI.CustomCategory.ARRAYLIST);
	public static CustomUIValue title_right = new CustomUIValue(false, "Right", CustomGUI.CustomCategory.TITLE);
	public static CustomUIValue tabgui_right = new CustomUIValue(false, "Right", CustomGUI.CustomCategory.TABGUI);
	
	public static void makeValues() {
		HUDValueManager.addValue(new ClickValue("Select Font", CustomGUI.CustomCategory.FONT, new Sgima()));
		HUDValueManager.addValue(title_size);
		HUDValueManager.addValue(array_size);
		HUDValueManager.addValue(title_x);
		HUDValueManager.addValue(title_y);
		HUDValueManager.addValue(tabgui_x);
		HUDValueManager.addValue(tabgui_y);
		HUDValueManager.addValue(array_x);
		HUDValueManager.addValue(array_y);
		HUDValueManager.addValue(title_right);
		HUDValueManager.addValue(tabgui_right);
		HUDValueManager.addValue(array_right);
	}
	
	public static void addValue(CustomUIValue value) {
		Wrapper.getCustomUI().addValue(value);
	}
	
	public static class Sgima implements ClickValue.ClickEvent {

		public void onClick() {
			Display.getParent();
			final HUD hud = ModuleManager.getModule(HUD.class);
	        JFileChooser chooser = new JFileChooser();
	        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	        chooser.setCurrentDirectory(TNTBase.getDir());
	        FileFilter filter = new FileNameExtensionFilter("Font files", "eot", "otf", "ttf", "ttc", "woff", "woff2");
	        chooser.addChoosableFileFilter(filter);
	        chooser.setFileFilter(filter);
	        chooser.setAcceptAllFileFilterUsed(false);
	        chooser.showOpenDialog(Display.getParent());
	        
	        if (chooser.getSelectedFile() == null) {
	        	return;
	        }
	        
	        Font font = null;
	        
	        try {
				font = Font.createFont(Font.PLAIN, chooser.getSelectedFile());
			} catch (Exception e) {
				;
			}
	        
	        if (font == null) {
	        	return;
	        }
	        
	        String path = chooser.getSelectedFile().getAbsolutePath();
	        String clientPath = TNTBase.getDir().getAbsolutePath();
	        path = path.substring(clientPath.length() - 1, path.length());
	        TNTBase.getFileManager().fontLoaderPath = path;
	        hud.fontRendererObj.setFont(font.deriveFont(0, HUDValueManager.array_size.getIntValue()));
	        hud.titleRendererObj.setFont(font.deriveFont(0, HUDValueManager.title_size.getIntValue()));
	        hud.tabGuiRendererObj.setFont(font.deriveFont(0, 15));
		}
	}
}
