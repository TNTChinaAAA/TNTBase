package net.tntchina.clientbase.module.modules.render;

import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.tntchina.clientbase.event.EventTarget;
import net.tntchina.clientbase.event.events.ClientPostStartingEvent;
import net.tntchina.clientbase.event.events.Render3DEvent;
import net.tntchina.clientbase.font.UnicodeFontRenderer;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.clientbase.value.values.BooleanValue;

@SuppressWarnings("deprecation")
public class NameTags extends Module {

	public NameTags(String name, ModuleCategory categorys) {
		super(name, categorys);
	}

	private BooleanValue armor = new BooleanValue(true, "Armor");
	private UnicodeFontRenderer fontRendererObj;

	@EventTarget
	public void onStart(ClientPostStartingEvent e) {
		this.fontRendererObj = new UnicodeFontRenderer(new Font("Comic Sans MC", Font.BOLD, 22));
	}
	
	@EventTarget
	public void onEvent(Render3DEvent event) {
		for (EntityPlayer entity : this.mc.theWorld.playerEntities) {
			if (entity != this.mc.thePlayer) {
				GL11.glPushMatrix();
				GL11.glEnable(3042);
				GL11.glDisable(2929);
				GL11.glNormal3f(0.0F, 1.0F, 0.0F);
				GlStateManager.disableLighting();
				GlStateManager.enableBlend();
				GL11.glBlendFunc(770, 771);
				GL11.glDisable(3553);
				float partialTicks = this.mc.timer.renderPartialTicks;
				double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - this.mc.getRenderManager().renderPosX;
				double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - this.mc.getRenderManager().renderPosY;
				double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - this.mc.getRenderManager().renderPosZ;
				y += this.mc.thePlayer.getDistanceToEntity(entity) * 0.02F;
				BigDecimal bigDecimal = new BigDecimal(entity.getHealth());
				bigDecimal = bigDecimal.setScale(1, RoundingMode.HALF_UP);
				String USERNAME = entity.getName();
				double HEALTH = bigDecimal.doubleValue();
				int COLOR = -1;
				
				if (HEALTH >= 10.0D) {
					COLOR = -16711936;
				} else if (HEALTH >= 3.0D) {
					COLOR = -23296;
				} else {
					COLOR = -65536;
				}
				
				boolean isFriend = false;
				String alias = null;
				char cn = 167;
				USERNAME = (isFriend ? cn + "c" + alias : USERNAME) + " " + String.valueOf(HEALTH);
				float DISTANCE = this.mc.thePlayer.getDistanceToEntity(entity);
				float SCALE = Math.min(Math.max(1.2F * (DISTANCE * 0.15F), 1.5F), 6.0F) * 0.02F;
				GlStateManager.translate((float) x, (float) y + entity.height + 0.5F - (entity.isChild() ? entity.height / 2.0F : 0.0F), (float) z);
				GL11.glNormal3f(0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(-this.mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(this.mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
				GL11.glScalef(-SCALE, -SCALE, SCALE);
				Tessellator tesselator = Tessellator.getInstance();
				WorldRenderer worldRenderer = tesselator.getWorldRenderer();
				UnicodeFontRenderer fontRenderer = this.fontRendererObj;
				int STRING_WIDTH = fontRenderer.getStringWidth(USERNAME) / 2;
				worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
				worldRenderer.pos(-STRING_WIDTH - 1, -2.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.75F).endVertex();
				worldRenderer.pos(-STRING_WIDTH - 1, 9.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.75F).endVertex();
				worldRenderer.pos(STRING_WIDTH + 2, 9.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.75F).endVertex();
				worldRenderer.pos(STRING_WIDTH + 2, -2.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.75F).endVertex();
				tesselator.draw();
				GL11.glEnable(3553);
				fontRenderer.drawString(entity.getName(), -fontRenderer.getStringWidth(USERNAME) / 2 + 1.0F, -fontRenderer.FONT_HEIGHT / 4 + 1.0F, Color.BLACK.hashCode());
				fontRenderer.drawString(entity.getName(), -fontRenderer.getStringWidth(USERNAME) / 2, -fontRenderer.FONT_HEIGHT / 4, -1);
				fontRenderer.drawString(String.valueOf(HEALTH), fontRenderer.getStringWidth(USERNAME) / 2 - fontRenderer.getStringWidth(String.valueOf(HEALTH)) + 1.0F, -fontRenderer.FONT_HEIGHT / 4 + 1.5F, Color.BLACK.hashCode());
				fontRenderer.drawString(String.valueOf(HEALTH), fontRenderer.getStringWidth(USERNAME) / 2 - fontRenderer.getStringWidth(String.valueOf(HEALTH)), -fontRenderer.FONT_HEIGHT / 4 + 0.5F, COLOR);
				
				if (this.armor.getValue()) {
					if ((entity.getCurrentArmor(0) != null) && ((entity.getCurrentArmor(0).getItem() instanceof ItemArmor))) {
						this.renderItem(entity.getCurrentArmor(0), 26, 0, 0);
					}
					
					if ((entity.getCurrentArmor(1) != null) && ((entity.getCurrentArmor(1).getItem() instanceof ItemArmor))) {
						this.renderItem(entity.getCurrentArmor(1), 13, 0, 0);
					}
					
					if ((entity.getCurrentArmor(2) != null) && ((entity.getCurrentArmor(2).getItem() instanceof ItemArmor))) {
						this.renderItem(entity.getCurrentArmor(2), 0, 0, 0);
					}
					
					if ((entity.getCurrentArmor(3) != null) && ((entity.getCurrentArmor(3).getItem() instanceof ItemArmor))) {
						this.renderItem(entity.getCurrentArmor(3), -13, 0, 0);
					}
					
					if (entity.getHeldItem() != null) {
						this.renderItem(entity.getHeldItem(), -26, 0, 0);
					}
				}
				
				GL11.glEnable(2929);
				GlStateManager.disableBlend();
				GL11.glDisable(3042);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glNormal3f(1.0F, 1.0F, 1.0F);
				GL11.glPopMatrix();
			}
		}
	}

	public void renderItem(ItemStack item, int xPos, int yPos, int zPos) {
		GL11.glPushMatrix();
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableAlpha();
		GlStateManager.alphaFunc(516, 0.1F);
		GlStateManager.enableBlend();
		GL11.glBlendFunc(770, 771);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		IBakedModel ibakedmodel = this.mc.getRenderItem().getItemModelMesher().getItemModel(item);
		this.mc.getRenderItem().textureManager.bindTexture(TextureMap.locationBlocksTexture);
		GlStateManager.scale(16.0F, 16.0F, 0.0F);
		GL11.glTranslated((xPos - 7.85F) / 16.0F, (-5 + yPos) / 16.0F, zPos / 16.0F);
		GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.disableLighting();
		ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GUI);
		
		if (ibakedmodel.isBuiltInRenderer()) {
			GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(-0.5F, -0.5F, -0.5F);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableRescaleNormal();
			TileEntityItemStackRenderer.instance.renderByItem(item);
		} else {
			this.mc.getRenderItem().renderModel(ibakedmodel, -1, item);
		}
		
		GlStateManager.enableAlpha();
		GlStateManager.disableRescaleNormal();
		GlStateManager.disableLighting();
		GlStateManager.popMatrix();
	}
}