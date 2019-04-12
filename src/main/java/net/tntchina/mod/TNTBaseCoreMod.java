package net.tntchina.mod;

import java.util.Map;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import net.tntchina.inputFix.InputFixSetup;

@TransformerExclusions("net.tntchina.mod.")
@MCVersion("1.8.9")
public class TNTBaseCoreMod implements IFMLLoadingPlugin {
	
	public TNTBaseCoreMod() {
		MixinBootstrap.init();
		Mixins.addConfiguration("mixins.tntbase.json");
		MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
	}
	
	public String[] getASMTransformerClass() {
		return new String[]{};
	}

	public String getModContainerClass() {
		return TNTBaseModContainer.class.getName();
	}

	public String getSetupClass() {
		return InputFixSetup.class.getName();
	}

	public void injectData(Map<String, Object> data) {}

	public String getAccessTransformerClass() {
		return TNTBaseAccessTransformer.class.getName();
	}
}