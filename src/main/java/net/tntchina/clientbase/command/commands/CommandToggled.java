package net.tntchina.clientbase.command.commands;

import net.tntchina.clientbase.command.Command;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleManager;

/**
 * the command on process changed module state.(����������ʱ���л����ܵĿ���״̬)
 * @author TNTChina
 */
public class CommandToggled extends Command {
	
	public CommandToggled() {
		super("Toggled");
	}

	public void execute(String[] strings) {
		if (strings.length > 1) {
			Module m = ModuleManager.getModule(strings[1]);
			
			if (m != null) {
				m.Toggled();
				this.printMessageToGuiScreen(Color + "c<Toggled>: Toggled Module " + m.getName() + ".");
			} else {
				this.printMessageToGuiScreen(this.getBigString("<Toggled>: ") + "Module not exists!");
			}
		} else {
			this.printMessageToGuiScreen(Color + "c<Toggled>: Format Error!");
		}
	}
}
