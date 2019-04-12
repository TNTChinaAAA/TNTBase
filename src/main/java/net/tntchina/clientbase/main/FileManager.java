package net.tntchina.clientbase.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;
import net.tntchina.clientbase.clickgui.CustomGUI;
import net.tntchina.clientbase.clickgui.module.ModuleButton;
import net.tntchina.clientbase.clickgui.module.ModulePanel;
import net.tntchina.clientbase.clickgui.other.Option;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleManager;
import net.tntchina.clientbase.value.Value;
import net.tntchina.clientbase.value.values.CustomUIValue;
import net.tntchina.util.LogManager;
import net.tntchina.util.Logger;

public class FileManager {
	
	public static final Logger logger = LogManager.getLogger("FileManager");
	public static FileManager fileManager;
	private final File dir = new File(Minecraft.getMinecraft().mcDataDir, "TNTBase");// Custom your client config name
	private final File fontDir = new File(this.dir, "fonts");
	private final File modules = new File(this.dir, "modules.json");
	private final File gui = new File(this.dir, "clickgui.json");
	private final File customUI = new File(this.dir, "customUI.json");
	private final Gson gson = new Gson();
	public String fontLoaderPath = "";
	
	public FileManager() {
		FileManager.fileManager = this;
		this.dir.mkdirs();
		this.fontDir.mkdirs();
	}
	
	public void loadCustomUI() {
		BufferedReader bufferedReader = null;
		
		if (!this.customUI.exists()) {
			try {
				this.customUI.createNewFile();
				this.saveCustomUI();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return;
		}
		
		try {
			bufferedReader = new BufferedReader(new FileReader(this.customUI));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			bufferedReader = new BufferedReader(new FileReader(this.customUI));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JsonElement jsonElement = this.gson.fromJson(bufferedReader, JsonElement.class);

		if (jsonElement instanceof JsonNull) {
			return;
		}
		
		JsonObject jsonObject = (JsonObject) jsonElement;
		
		for (CustomGUI.CustomCategory category : CustomGUI.CustomCategory.values()) {
			if (jsonObject.has(category.getName())) {
				JsonElement categoryElement = jsonObject.get(category.getName());
				
				if (categoryElement instanceof JsonNull) {
					continue;
				}
				
				JsonObject categoryObject = (JsonObject) categoryElement;
				
				for (CustomUIValue value : Wrapper.getCustomUI().getValues()) {
					if (value.getType().equals(category)) {
						if (categoryObject.has(value.getValueName())) {
							JsonElement valueValue = categoryObject.get(value.getValueName());
							
							if (value.isBoolean())
								value.setObject(valueValue.getAsBoolean());
							else if (value.isByte()) 
								value.setObject(valueValue.getAsByte());
							else if (value.isDouble())
								value.setObject(valueValue.getAsDouble());
							else if (value.isFloat()) 
								value.setObject(valueValue.getAsFloat());
							else if (value.isInteger())
								value.setObject(valueValue.getAsInt());
							else if (value.isLong()) 
								value.setObject(valueValue.getAsLong());
							else if (value.isShort())
								value.setObject(valueValue.getAsShort());
						}
					}
				}
			}
		}
		
		if (jsonObject.has("font")) {
			this.fontLoaderPath = jsonObject.get("font").getAsString();
		}
	}
	
	public void saveCustomUI() {
		if (!this.customUI.exists()) {
			try {
				this.customUI.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("font", this.fontLoaderPath);
		
		for (CustomGUI.CustomCategory category : CustomGUI.CustomCategory.values()) {
			JsonObject categoryJson = new JsonObject();
			
			for (CustomUIValue value : Wrapper.getCustomUI().getValues()) {
				if (value.getType().equals(category)) {
					if (value.isNumber()) {
						if (value.getObject() instanceof Number) {
							categoryJson.addProperty(value.getValueName(), (Number) value.getObject());
						} else if (value.getObject() instanceof Boolean) {
							categoryJson.addProperty(value.getValueName(), (Boolean) value.getObject());
						} else if (value.getObject() instanceof String) {
							categoryJson.addProperty(value.getValueName(), (String) value.getObject());
						}
					}
				}
			}
			
			jsonObject.add(category.getName(), categoryJson);
		}
		
		try {
			PrintWriter printWriter = new PrintWriter(this.customUI);
			printWriter.println(this.gson.toJson(jsonObject));
			printWriter.flush();
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadGUISetting() {
		BufferedReader bufferedReader = null;
		
		if (!this.gui.exists()) {
			try {
				this.gui.createNewFile();
				this.saveGUISetting();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return;
		}
		
		try {
			bufferedReader = new BufferedReader(new FileReader(this.gui));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JsonElement jsonElement = this.gson.fromJson(bufferedReader, JsonElement.class);

		if (jsonElement instanceof JsonNull) {
			return;
		}
		
		JsonObject jsonObject = (JsonObject) jsonElement;
		
		for (ModulePanel panel : Wrapper.getClickGUIModule().getClickGui().panels) {
			if (!jsonObject.has(panel.getPanelName())) {
				continue;
			}
			
			JsonElement PanelElement = jsonObject.get(panel.getPanelName());

			if (PanelElement instanceof JsonNull) {
				continue;
			}
				
			JsonObject PanelJson = (JsonObject) PanelElement;
			
			if (PanelJson.has("x")) {
				panel.setX(PanelJson.get("x").getAsInt());
				panel.setLabelX(panel.getX() + ((panel.getWidth() - this.getStringWidth(panel.getPanelName())) / 2));
			}
			
			if (PanelJson.has("y")) {
				panel.setY(PanelJson.get("y").getAsInt());
				panel.setLabelY(panel.getY() + ((panel.getHeight() - this.getFontHeight()) / 2));
			}
			
			if (PanelJson.has("state")) {
				panel.setState(PanelJson.get("state").getAsBoolean());
			}
			
			for (ModuleButton button : panel.getButtons()) {
				if (PanelJson.has(button.getName())) {
					JsonElement ButtonElement = PanelJson.get(button.getName());

					if (ButtonElement instanceof JsonNull) {
						continue;
					}
					
					JsonObject buttonJson = (JsonObject) ButtonElement;
					
					if (buttonJson.has("x")) {
						button.setX(buttonJson.get("x").getAsInt());
						button.setLabelX(button.getX() + ((button.getWidth() - this.getStringWidth(button.getName())) / 2));
					}
					
					if (buttonJson.has("y")) {
						button.setY(buttonJson.get("y").getAsInt());
						button.setLabelY(button.getY() + ((button.getHeight() - this.getFontHeight()) / 2));
					}
					
					if (button.hasOption() && buttonJson.has("option")) {
						Option option = button.getOption();
						JsonElement optionElement = buttonJson.get("option");
						
						if (optionElement instanceof JsonNull) {
							continue;
						}
						
						JsonObject optionJson = (JsonObject) optionElement;
						
						if (optionJson.has("x")) {
							option.setX(optionJson.get("x").getAsInt());
						}
						
						if (optionJson.has("y")) {
							option.setY(optionJson.get("y").getAsInt());
						}
						
						if (optionJson.has("state")) {
							option.setState(optionJson.getAsBoolean());
						}
					}
				}
			}
		}
		
		for (ModulePanel panel : Wrapper.getClickGUIModule().getClickGui().panels) {
			Wrapper.getClickGUIModule().getClickGui().onPanelDrag(panel);
		}
	}

	public void saveGUISetting() {
		if (!this.gui.exists()) {
			try {
				this.gui.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		JsonObject jsonObject = new JsonObject();
		
		for (ModulePanel panel : Wrapper.getClickGUIModule().getClickGui().panels) {
			JsonObject json = new JsonObject();
			json.addProperty("state", panel.getState());
			json.addProperty("x", panel.getX());
			json.addProperty("y", panel.getY());
			
			for (ModuleButton button : panel.getButtons()) {
				JsonObject buttonJson = new JsonObject();
				buttonJson.addProperty("x", button.getX());
				buttonJson.addProperty("y", button.getY());
				
				if (button.hasOption()) {
					Option o = button.getOption();
					JsonObject optionJson = new JsonObject();
					optionJson.addProperty("x", o.getX());
					optionJson.addProperty("y", o.getY());
					buttonJson.add("option", optionJson);
				}
				
				json.add(button.getName(), buttonJson);
			}
			
			jsonObject.add(panel.getPanelName(), json);
		}
		
		try {
			PrintWriter printWriter = new PrintWriter(this.gui);
			printWriter.println(this.gson.toJson(jsonObject));
			printWriter.flush();
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void saveModules() {
		if (!this.modules.exists()) {
			try {
				this.modules.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		JsonObject jsonObject = new JsonObject();
		
		for (Module module : ModuleManager.getModules()) {
			JsonObject moduleJson = new JsonObject();
			moduleJson.addProperty("state", module.getState());
			moduleJson.addProperty("key", module.getKeyBind());
			
			for (Value value : module.getValues()) {
				if (value.getObject() instanceof Number) {
					moduleJson.addProperty(value.getValueName(), (Number) value.getObject());
				} else if (value.getObject() instanceof Boolean) {
					moduleJson.addProperty(value.getValueName(), (Boolean) value.getObject());
				} else if (value.getObject() instanceof String) {
					moduleJson.addProperty(value.getValueName(), (String) value.getObject());
				}
			}

			jsonObject.add(module.getName(), moduleJson);
		}

		try {
			PrintWriter printWriter = new PrintWriter(this.modules);
			printWriter.println(this.gson.toJson(jsonObject));
			printWriter.flush();
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void loadModules() {
		BufferedReader bufferedReader = null;

		if (!this.modules.exists()) {
			try {
				this.modules.createNewFile();
				saveModules();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return;
		}

		try {
			bufferedReader = new BufferedReader(new FileReader(this.modules));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		JsonElement jsonElement = this.gson.fromJson(bufferedReader, JsonElement.class);

		if (jsonElement instanceof JsonNull) {
			return;
		}
		
		JsonObject jsonObject = (JsonObject) jsonElement;

		for (Module module : ModuleManager.getModules()) {
			if (!jsonObject.has(module.getName())) {
				continue;
			}
				
			JsonElement moduleElement = jsonObject.get(module.getName());

			if (moduleElement instanceof JsonNull) {
				continue;
			}
				
			JsonObject moduleJson = (JsonObject) moduleElement;

			if (moduleJson.has("state")) {
				module.state = moduleJson.get("state").getAsBoolean();
			} if (moduleJson.has("key")) {
				module.setKeyBind(moduleJson.get("key").getAsInt());
			}
					
			for (Value value : module.getValues()) {
				if (!moduleJson.has(value.getValueName())) {
					continue;
				}

				if (value.getObject() instanceof Float) {
					value.setObject(moduleJson.get(value.getValueName()).getAsFloat());
				} else if (value.getObject() instanceof Double) {
					value.setObject(moduleJson.get(value.getValueName()).getAsDouble());
				} else if (value.getObject() instanceof Integer) {
					value.setObject(moduleJson.get(value.getValueName()).getAsInt());
				} else if (value.getObject() instanceof Long) {
					value.setObject(moduleJson.get(value.getValueName()).getAsLong());
				} else if (value.getObject() instanceof Byte) {
					value.setObject(moduleJson.get(value.getValueName()).getAsByte());
				} else if (value.getObject() instanceof Boolean) {
					value.setObject(moduleJson.get(value.getValueName()).getAsBoolean());
				} else if (value.getObject() instanceof String) {
					value.setObject(moduleJson.get(value.getValueName()).getAsString());
				}
			}
		}
	}
	
	public int getFontHeight() {
		return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
	}

	public int drawString(String text, int x, int y, int color) {
		return Minecraft.getMinecraft().fontRendererObj.drawString(text, x, y, color);
	}

	public int getStringWidth(String text) {
		return Minecraft.getMinecraft().fontRendererObj.getStringWidth(text);
	}
}