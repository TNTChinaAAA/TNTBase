package net.tntchina.mod;

import java.util.Arrays;
import com.google.common.eventbus.EventBus;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.tntchina.clientbase.main.TNTBase;

public class TNTBaseModContainer extends DummyModContainer {
	
	public TNTBaseModContainer() {
		super(new ModMetadata());
		ModMetadata data = this.getMetadata();
		data.modId = "TNT";
		data.name = "TNT";
		data.version = "V" + TNTBase.CLIENT_VERSION;
		data.url = "https://github.com/TNTChinaAAA/TNTBase";
		data.authorList = Arrays.asList("[TNTChina]");
		data.description = "The hack mod by TNTChina.His QQ number is 3274578216";
	}
	
	public Object getMod() {
		return this;
	}
	
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}
}
