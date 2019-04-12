package net.tntchina.clientbase.command;

import java.util.*;

import net.tntchina.clientbase.command.commands.*;

/**
 * all command manager(所有命令的处理者)
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
	 * register the your command to the list.(注册命令到命令集合)
	 */
	public void register(Command command) {
		CommandManager.commands.add(command);
	}
	
	/**
	 * Check and invoke command(检查并调用命令)
	 * @param msg (The player enter)(玩家输入的文字字段)
	 */
	public void callCommand(String msg) {
		CommandManager.commands.stream().filter(command -> this.isProcessCommand(msg.split(" "), command)).forEach(command -> command.execute(msg.split(" ")));
	}
	
	/**
	 * @param str
	 * @param command
	 * @return the player has process the command.(玩家有没有激活、调用这个命令)
	 */
	public boolean isProcessCommand(String[] str, Command command) {
		return str[0].equalsIgnoreCase("." + command.getName());
	}
	
	/**
	 * @return the command list.(获得命令集合)
	 */
	public static List<Command> getCommands() {
		return CommandManager.commands;
	}
}