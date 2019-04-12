package net.tntchina.mod;

import java.io.IOException;
import net.minecraftforge.fml.common.asm.transformers.AccessTransformer;

public class TNTBaseAccessTransformer extends AccessTransformer {

	public TNTBaseAccessTransformer() throws IOException {
		super("tnt_at.cfg");
	}
}
