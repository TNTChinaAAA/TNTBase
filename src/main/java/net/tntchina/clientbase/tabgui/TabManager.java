package net.tntchina.clientbase.tabgui;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.input.Keyboard;

import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.clientbase.module.ModuleManager;

/**
 * The tab manager(Tab的处理者)
 * @author TNTChina
 */
public class TabManager {

	private static final List<Tab> tabList = new ArrayList<Tab>();
	private static int currentID = 0;
	private static Tab currentTab;
	private static int tabSize = ModuleCategory.values().length;
	private static ModuleCategory[] categorys = ModuleCategory.values();
	
	public static final List<? extends Tab> getTabs() {
		return TabManager.tabList; 
	}
	
	public static void initTabGui() {
		for (int i = 0; i < categorys.length; i++) {
			ModuleCategory category = categorys[i];
			Tab tab = new Tab(category.getName());
			tab.setID(i);
			tab.setWidth(75);
			tab.setHeight(20);
			tab.setX(10);
			tab.setY((i + 2) * tab.getHeight() + tab.getHeight());

			
			for (Module m : ModuleManager.getModules()) {
				if (m.getCategory().equals(category)) {
					tab.addButton(new TabButton(m));
				}
			}
			
			tabList.add(tab);
		}
		
		for (Tab tab : tabList) {
			for (int i = 0; i < tab.getButtons().size(); i++) {
				TabButton tabButton = tab.getButtons().get(i);
				tabButton.setID(i);
				tabButton.setWidth(50);
				tabButton.setHeight(20);
				tabButton.setX(tab.getX() + tab.getWidth());
				tabButton.setY(tab.getY() + i * tabButton.getHeight());
			}
			
			if (tab.getButtons().size() == 0) {
				continue;
			}
			
			tab.setCurrentButton(tab.getButtons().get(0));
		}
		
		currentTab = tabList.get(0);
	}
	
	public static void onUpPresses() {
		currentID--;
	
		if (currentID < 0) {
			currentID += tabList.size();
		}
		
		currentTab = tabList.get(currentID);
	}
	
	public static void onDownPresses() {
		currentID++;
		
		if (currentID > (tabSize - 1)) {
			currentID = (currentID - (tabSize));
		}
		
		currentTab = tabList.get(currentID);
	}
	
	public static void onKeyPresses(int key) {
		if (!Keyboard.getEventKeyState()) {
			return;
		}
		
		switch (key) {
			case Keyboard.KEY_DOWN: {
				if (!currentTab.isSelect()) {
					onDownPresses();
				} else {
					currentTab.onDownPresses();
				}
				
				break;
			} case Keyboard.KEY_UP: {
				if (!currentTab.isSelect()) {
					onUpPresses();
				} else {
					currentTab.onUpPresses();
				}
				
				break;
			} case Keyboard.KEY_RETURN: {
				if (currentTab.isSelect()) {
					if (currentTab.getCurrentButton() == null) {
						if (currentTab.getButtons().size() == 0) {
							break;
						}
						
						currentTab.setCurrentButton(currentTab.getButtons().get(0));
					}
					
					currentTab.getCurrentButton().getModule().Toggled();
				}
				
				break;
			} case Keyboard.KEY_LEFT: {
				currentTab.setSelect(false);
				break;
			} case Keyboard.KEY_RIGHT: {
				currentTab.setSelect(true);
				break;
			}
		}
	}

	public static Tab getCurrentTab() {
		return currentTab;
	}
}
