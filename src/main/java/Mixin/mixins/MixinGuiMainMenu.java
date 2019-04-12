package Mixin.mixins;

import java.awt.Color;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLanguage;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.tntchina.clientbase.main.TNTBase;
import net.tntchina.util.GuiUtil;

@Mixin(GuiMainMenu.class)
public abstract class MixinGuiMainMenu extends GuiScreen {

	private GuiMainMenu INSTANCE;
	
	@Shadow
	private GuiScreen field_183503_M;

	@Shadow
	private float updateCounter;

	@Shadow
	private String openGLWarning1, openGLWarning2;
	
	@Shadow
    private int field_92024_r;
	
	@Shadow
    private int field_92023_s;
	
	@Shadow
    private int field_92022_t;
	
	@Shadow
    private int field_92021_u;
	
	@Shadow
    private int field_92020_v;
	
	@Shadow
    private int field_92019_w;
	
	@Shadow
	private static ResourceLocation minecraftTitleTextures;
	
	@Inject(method = "<init>", at = @At("RETURN"))
	public void setup(CallbackInfo callbackInfo) {
		this.INSTANCE = (GuiMainMenu) ((Object) this);
	}

	public void renderPumpkinOverlay(ScaledResolution scaledRes) {
		GuiUtil.drawImage(new ResourceLocation("background/bg.jpg"), 0, 0, scaledRes.getScaledWidth(), scaledRes.getScaledHeight());
		GuiUtil.drawImage(new ResourceLocation("background/startbg.jpg"), (scaledRes.getScaledWidth() - 200) / 2, (scaledRes.getScaledHeight() - 200) / 2, 200, 200);
	}
	
	@Overwrite
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.disableAlpha();
        this.renderSkybox(mouseX, mouseY, partialTicks);
        GlStateManager.enableAlpha();
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.toString();
        int i = 274;
        int j = this.width / 2 - i / 2;
        int k = 30;
        this.drawGradientRect(0, 0, this.width, this.height, -2130706433, 16777215);
        this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
        this.renderPumpkinOverlay(new ScaledResolution(Minecraft.getMinecraft()));
        this.mc.getTextureManager().bindTexture(minecraftTitleTextures);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        if (this.updateCounter < 1.0E-4D) {
            this.drawTexturedModalRect(j + 0, k + 0, 0, 0, 99, 44);
            this.drawTexturedModalRect(j + 99, k + 0, 129, 0, 27, 44);
            this.drawTexturedModalRect(j + 99 + 26, k + 0, 126, 0, 3, 44);
            this.drawTexturedModalRect(j + 99 + 26 + 3, k + 0, 99, 0, 26, 44);
            this.drawTexturedModalRect(j + 155, k + 0, 0, 45, 155, 44);
        } else {
            this.drawTexturedModalRect(j + 0, k + 0, 0, 0, 155, 44);
            this.drawTexturedModalRect(j + 155, k + 0, 0, 45, 155, 44);
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate((float)(this.width / 2 + 90), 70.0F, 0.0F);
        GlStateManager.rotate(-20.0F, 0.0F, 0.0F, 1.0F);
        float f = 1.8F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0F * (float)Math.PI * 2.0F) * 0.1F);
        f = f * 100.0F / 0 + 32;
        GlStateManager.scale(f, f, f);
        this.drawCenteredString(this.fontRendererObj, "", 0, -8, -256);
        GlStateManager.popMatrix();

        List<String> brandings = Lists.reverse(FMLCommonHandler.instance().getBrandings(true));
        
        for (int brdline = 0; brdline < brandings.size(); brdline++) {
            String brd = brandings.get(brdline);
            
            if (!Strings.isNullOrEmpty(brd)) {
                if (brd.equals("Minecraft 1.8.9")) {
                	brd = TNTBase.CLIENT_NAME + TNTBase.SPACE + TNTBase.CLIENT_VERSION + " | Minecraft 1.8.9";
                }
            	
            	this.drawString(this.fontRendererObj, brd, 2, this.height - ( 10 + brdline * (this.fontRendererObj.FONT_HEIGHT + 1)), 16777215);
            }
        }
       
        ForgeHooksClient.renderMainMenu(this.INSTANCE, this.fontRendererObj, this.width, this.height);
        String s1 = "Copyright TNTChina. Do not distribute!";
        this.drawString(this.fontRendererObj, s1, this.width - this.fontRendererObj.getStringWidth(s1) - 2, this.height - 10, -1);

        if (this.openGLWarning1 != null && this.openGLWarning1.length() > 0) {
            Gui.drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
            this.drawString(this.fontRendererObj, this.openGLWarning1, this.field_92022_t, this.field_92021_u, -1);
            this.drawString(this.fontRendererObj, this.openGLWarning2, (this.width - this.field_92024_r) / 2, ((GuiButton)this.buttonList.get(0)).yPosition - 12, -1);
        }
        
        int Idontknow = 0;
        int abstract_ = 0;
        
        for (GuiButton button : this.buttonList) {
        	if (button.id == 1) {
        		Idontknow = button.xPosition - 25;
        		abstract_ = button.height;
        		break;
        	}
        }
        
        GuiUtil.drawRect(Idontknow, this.buttonList.get(0).yPosition - 20, this.buttonList.get(0).getButtonWidth() + 50, (this.buttonList.get(0).height * 8) - abstract_, 0x80000000);
        
        for (int ax = 0; ax < this.buttonList.size(); ++ax) {
            final GuiButton button = this.buttonList.get(ax);
            GuiUtil.drawRect(button.xPosition, button.yPosition, button.width, button.height, new Color(239, 111, 16).hashCode());
            
            if (button.id == 5) {            
            	final GuiButtonLanguage language = (GuiButtonLanguage) button;
            	language.drawButton(this.mc, mouseX, mouseY);
            	continue;
            }
            
            int color = 14737632;

            if (button.packedFGColour != 0) {
            	color = button.packedFGColour;
            } else if (!button.enabled) {
            	color = 10526880;
            } else if (button.isMouseOver()) {
            	color = 16777120;
            }
            
            button.drawCenteredString(this.mc.fontRendererObj, button.displayString, button.xPosition + button.width / 2, button.yPosition + (button.height - 8) / 2, color);
            //button.drawButton(this.mc, mouseX, mouseY);
        }
        
        for (int ah = 0; ah < this.labelList.size(); ++ah) {
            this.labelList.get(ah).drawLabel(this.mc, mouseX, mouseY);
        }

        if (this.func_183501_a()) {
            this.field_183503_M.drawScreen(mouseX, mouseY, partialTicks);
        }
	}

	@Shadow
	public abstract void renderSkybox(int mouseX, int mouseY, float partialTicks);

	@Shadow
	public abstract boolean func_183501_a();
}