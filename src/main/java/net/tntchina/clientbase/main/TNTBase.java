package net.tntchina.clientbase.main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.IChatComponent;
import net.tntchina.clientbase.command.CommandManager;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleManager;
import net.tntchina.clientbase.module.modules.render.hud.HUDValueManager;
import net.tntchina.clientbase.tabgui.TabManager;
import net.tntchina.util.LogManager;
import net.tntchina.util.Logger;
import net.tntchina.util.XRayUtil;

public class TNTBase {
	
	public static final String SPACE = " ";
	public static final String CLIENT_NAME = "TNTBase";
	public static final String CLIENT_AUTHOR = "TNTChina";
	public static final String CLIENT_VERSION = "1.0.0";
	public static final String CLIENT_FULL_NAME = TNTBase.getFullName();
	public static final String CLIENT_AUTHOR_QQ = "3274578216";
	public static final Logger logger = LogManager.getLogger("TNTBase");
	public static Minecraft mc;
	public static File ResourceFolden;
	public static TNTBase theClient;
	public static ModuleManager moduleManager;
	public static CommandManager commandManager;
	private static FileManager fileManager;
	private static volatile boolean isLoading = true;
	
	private TNTBase() {
		TNTBase.theClient = this;
	}
	
	public static InputStream getResourceAsStream(String classLoaderPath) {
		return TNTBase.class.getClassLoader().getResourceAsStream(classLoaderPath);
	}
	
	public static String getFullName() {
		List<String> strings = new ArrayList<String>();
		LineNumberReader line = new LineNumberReader(new InputStreamReader(TNTBase.getResourceAsStream("assets/minecraft/txt/sir.txt")));
		String txt = "";
		
		try {
			while ((txt = line.readLine()) != null) {
				strings.add(txt);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Random random = new Random();
		int index = random.nextInt(strings.size());
		return CLIENT_NAME + SPACE + "V" + CLIENT_VERSION + " | " + strings.get(index) + " ---by " + TNTBase.CLIENT_AUTHOR + " | QQ " + TNTBase.CLIENT_AUTHOR_QQ;
	}
	
	public static TNTBase getTNTChina() {
		return TNTBase.theClient;
	}
	
	public static String getClassName() {
		String name = TNTBase.class.getName().replace(".", "/");
		return name;
	}

	public static void InstanceClient() {
		new TNTBase();
		TNTBase.loaderEvent();
		XRayUtil.initXRay();
		TNTBase.registerValue();
		TNTBase.mkdirs();
		TNTBase.registerAll();
		TNTBase.startThread();
	}
	
	private static void loaderEvent() {
		new EventLoader();
	}
	
	private static void registerValue() {
		TNTBase.mc = Minecraft.getMinecraft();
		TNTBase.ResourceFolden = new File(Minecraft.getMinecraft().mcDataDir, "TNTChina Client");
		logger.info("Register value end.");
	}
	
	private static void registerAll() {
		TNTBase.registerModules();
		TNTBase.registerCommand();
		TNTBase.registerTabGui();
		TNTBase.registerJson();
		logger.info("Register Class end.");
	}
	
	private static void registerCommand() {
		TNTBase.commandManager = new CommandManager();
	}
	
	private static void registerModules() {
		TNTBase.moduleManager = new ModuleManager();
	}
	
	private static void registerTabGui() {
		TabManager.initTabGui();
	}
	
	private static void registerJson() {
		TNTBase.fileManager = new FileManager();
	}
	
	private static void startThread() {
		TNTBase.fileManager.loadModules();
	}
	
	private static void mkdirs() {
		TNTBase.ResourceFolden.mkdirs();
		logger.info("Md dir end.");
	}
	
	public static void loadModule() {
		if (Minecraft.getMinecraft().thePlayer == null) {
			return;
		}
		
		if (TNTBase.isLoading) {
			for (Module m : ModuleManager.getModules()) {
				m.onToggled();
			
				if (m.getState()) {
					m.onEnable();
				} else {
					m.onDisable();
				}
			}
			
			TNTBase.logger.info("Loading Module...");
		} else {
			return;
		}
	
		TNTBase.isLoading = false;
	}
	
	public static void startClient() throws Exception {
		HUDValueManager.makeValues();
		Wrapper.getClickGUIModule().setup();
		Wrapper.getCustomUI().initUI();
		TNTBase.logger.info("TNTChina Client by " + TNTBase.CLIENT_AUTHOR);
		TNTBase.logger.info("The Client has " + TNTBase.getModules().size() + " modules.");
		TNTBase.fileManager.loadGUISetting();
		TNTBase.fileManager.loadCustomUI();
		Display.getParent();
		Display.getParent();
		Display.getParent();
	}
	
	public static void stopClient() throws Exception {
		TNTBase.fileManager.saveModules();
		TNTBase.fileManager.saveGUISetting();
		TNTBase.fileManager.saveCustomUI();
		
		/*try {
			FileWriter writer = new FileWriter(new File(TNTChina.TNTCHINA_FOLDEN, "read.data"));
			writer.write(MainCheck.lastToken);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	public static void isPresses(int key) {
		if (Wrapper.getClickGUIModule().getKeyBind() == key) {
			Wrapper.getClickGUIModule().Toggled();
		}
		
		for (Module m : ModuleManager.getModules()) {
			if (m.getKeyBind() == key) {
				m.Toggled();
			}
		}
	}
	
	public static void onRender() {
		for (Module m : ModuleManager.getModules()) {
			m.onRender();
		}
	}
	
	public static List<Module> getModules() {
		return ModuleManager.getModules();
	}
	
    public static void displayMessage(String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(IChatComponent.Serializer.jsonToComponent("{text:\"" + message + "\"}"));
    }
    
    public static String getTNTChinaDir() {
    	return TNTBase.ResourceFolden.getAbsolutePath();
    }
    
    public static void callCommand(String args) {
    	TNTBase.commandManager.callCommand(args);
    }
    
    public static CommandManager getCommandManager() {
    	return TNTBase.commandManager;
    }
    
    public static Logger getLogger() {
    	return TNTBase.logger;
    }
    
    public static void GameKeyPresses() {
        if (Minecraft.getMinecraft().thePlayer == null) {
        	return;
        }
    	
    	int i = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() : Keyboard.getEventKey();

		if (i == Wrapper.getClickGUIModule().getKeyBind()) {
        	Wrapper.getClickGUIModule().Toggled();
        	
        	if (!Wrapper.getClickGUIModule().getState()) {	
            	Minecraft.getMinecraft().displayGuiScreen((GuiScreen) null);
            	
            	if (Minecraft.getMinecraft().currentScreen == null) {
            		Minecraft.getMinecraft().setIngameFocus();
            	}
            }
		}
	}
    
    public static File getDir() {
    	return new File(Minecraft.getMinecraft().mcDataDir, "TNTBase");
    }

	public static FileManager getFileManager() {
		return TNTBase.fileManager;
	}
}