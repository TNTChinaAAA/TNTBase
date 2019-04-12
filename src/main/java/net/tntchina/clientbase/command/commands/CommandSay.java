package net.tntchina.clientbase.command.commands;

import net.tntchina.clientbase.command.Command;

public class CommandSay extends Command {

	public CommandSay() {
		super("say");
	}

	public void execute(String[] strings) {
		this.mc.ingameGUI.getChatGUI().addToSentMessages(strings[1]);
		this.mc.thePlayer.sendChatMessage(strings[1]);
	}
}