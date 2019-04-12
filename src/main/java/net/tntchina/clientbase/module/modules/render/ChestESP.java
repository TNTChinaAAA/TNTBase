package net.tntchina.clientbase.module.modules.render;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.util.RenderUtil;

public class ChestESP extends Module {

	public ChestESP(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	public void onRender() {
		if (this.getState()) {
			for (TileEntity tileEntity : this.mc.theWorld.loadedTileEntityList) {
				if (tileEntity.getBlockType().equals(Blocks.chest)) {
					RenderUtil.blockESPBox(tileEntity.getPos());
				}
			}
		}
	}
}
