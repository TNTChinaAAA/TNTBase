package net.tntchina.clientbase.command;

import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;

/**
 * all command super.(所有命令的父类)
 * @author TNTChina
 */
public abstract class Command {
    
    public static final String Color = "" + new Character((char) 167);
    public static final String WHITE = Color + "a" + Color + "l";
    public Minecraft mc = Minecraft.getMinecraft();
    public String name;
    
    public Command(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    
	public void printMessageToGuiScreen(String message) {
		this.mc.thePlayer.addChatMessage(IChatComponent.Serializer.jsonToComponent("{text:\"" + message + "\"}"));
	}
	
	public String getBigString(String text) {
		return Color + "c" + Color + "l"+ text + Color + "r" + Color + "a";
	}

	/**
	 * on the player enter command invoke(The player has using the command name)(在玩家调用这个命令时，并且有用到这个命令的名字) 
	 * @param strings(玩家输送的文字字段)
	 */
    public abstract void execute(String[] strings);
}
