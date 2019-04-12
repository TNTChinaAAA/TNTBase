package net.tntchina.clientbase.module.modules.misc;

import net.tntchina.clientbase.event.EventTarget;
import net.tntchina.clientbase.event.events.TextEvent;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.clientbase.value.Value;

public class NameProtect extends Module {

	public Value<String> name = new Value<String>("byTNTChina", "Name");
	
	public NameProtect(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	@EventTarget
	public void onText(TextEvent event) {
		if (this.getState() && event.getStr() != null && this.mc.thePlayer != null) {
			final String str = event.getStr();
			
			if (str.contains(this.mc.thePlayer.getName())) {
				event.setStr(str.replace(this.mc.thePlayer.getName(), this.name.getObject()));
			}
		}
	}
}