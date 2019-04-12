package net.tntchina.clientbase.command;

import java.util.*;

import net.tntchina.clientbase.command.commands.*;

/**
 * all command manager(��������Ĵ�����)
 * @author TNTChina
 */
public class CommandManager {

	public static final ArrayList<Command> commands = new ArrayList<Command>();
	
	public CommandManager() {
		this.register(new CommandKeyBind());
		this.register(new CommandToggled());
		this.register(new CommandValue());
		this.register(new CommandSay());
	}
	
	/**
	 * register the your command to the list.(ע����������)
	 */
	public void register(Command command) {
		CommandManager.commands.add(command);
	}
	
	/**
	 * Check and invoke command(��鲢��������)
	 * @param msg (The player enter)(�������������ֶ�)
	 */
	public void callCommand(String msg) {
		CommandManager.commands.stream().filter(command -> this.isProcessCommand(msg.split(" "), command)).forEach(command -> command.execute(msg.split(" ")));
	}
	
	/**
	 * @param str
	 * @param command
	 * @return the player has process the command.(�����û�м�������������)
	 */
	public boolean isProcessCommand(String[] str, Command command) {
		return str[0].equalsIgnoreCase("." + command.getName());
	}
	
	/**
	 * @return the command list.(��������)
	 */
	public static List<Command> getCommands() {
		return CommandManager.commands;
	}
}