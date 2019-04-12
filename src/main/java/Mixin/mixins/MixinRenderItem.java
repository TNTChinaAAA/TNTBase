package Mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import Mixin.impl.IRenderItem;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mixin(RenderItem.class)
public abstract class MixinRenderItem implements IRenderItem {

	@Shadow
	private TextureManager textureManager;

	public TextureManager getTextureManager() {
		return this.textureManager;
	}
}