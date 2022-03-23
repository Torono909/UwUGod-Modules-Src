// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.gui;

import net.minecraft.client.gui.GuiIngame;
import meow.candycat.uwu.module.ModuleManager;
import java.awt.Color;
import meow.candycat.uwu.util.ColourUtils;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import meow.candycat.uwu.gui.font.CFont;
import meow.candycat.uwu.setting.Settings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.gui.font.CFontRenderer;
import meow.candycat.uwu.module.Module;

@Info(name = "WaterMark", category = Category.GUI, description = "View your inventory on screen", showOnArray = ShowOnArray.OFF)
public class WaterMark extends Module
{
    CFontRenderer cFontRenderer;
    public Setting<Boolean> backToDefault;
    public Setting<Boolean> LOGO;
    private Setting<Integer> size;
    public Setting<Boolean> Watermark;
    public Setting<Boolean> RainbowWatermark;
    public Setting<Boolean> Hello;
    public Setting<Boolean> RainbowHello;
    private Setting<Integer> Hellowx;
    private Setting<Integer> Hellowy;
    public Setting<Integer> hred;
    public Setting<Integer> hgreen;
    public Setting<Integer> hblue;
    private Setting<Integer> Watermarkx;
    private Setting<Integer> Watermarky;
    public Setting<Integer> wred;
    public Setting<Integer> wgreen;
    public Setting<Integer> wblue;
    private Setting<Integer> optionX;
    private Setting<Integer> optionY;
    FontRenderer fontRenderer;
    Minecraft mc;
    private Setting<ViewMode> viewMode;
    
    @Override
    public void onUpdate() {
        if (this.backToDefault.getValue()) {
            this.Hellowx.setValue(440);
            this.Hellowy.setValue(3);
            this.Watermarkx.setValue(1);
            this.Watermarky.setValue(0);
            this.optionX.setValue(0);
            this.optionY.setValue(10);
            this.size.setValue(45);
            this.backToDefault.setValue(false);
        }
    }
    
    private ResourceLocation getBox() {
        return new ResourceLocation("textures/gui/container/aua-2.png");
    }
    
    public WaterMark() {
        this.backToDefault = this.register(Settings.b("Default", false));
        this.LOGO = this.register(Settings.booleanBuilder("LOGO").withValue(true).build());
        this.size = this.register((Setting<Integer>)Settings.integerBuilder("Size").withValue(45).build());
        this.Watermark = this.register(Settings.booleanBuilder("Watermark").withValue(true).build());
        this.RainbowWatermark = this.register(Settings.booleanBuilder("Rainbow Watermark").withValue(true).build());
        this.Hello = this.register(Settings.booleanBuilder("Hello").withValue(false).build());
        this.RainbowHello = this.register(Settings.booleanBuilder("Rainbow Hello").withValue(false).build());
        this.Hellowx = this.register((Setting<Integer>)Settings.integerBuilder("Hello X").withValue(440).build());
        this.Hellowy = this.register((Setting<Integer>)Settings.integerBuilder("Hello Y").withValue(3).build());
        this.hred = this.register((Setting<Integer>)Settings.integerBuilder("Hello Red").withRange(0, 255).withValue(0).build());
        this.hgreen = this.register((Setting<Integer>)Settings.integerBuilder("Hello Green").withRange(0, 255).withValue(0).build());
        this.hblue = this.register((Setting<Integer>)Settings.integerBuilder("Hello Blue").withRange(0, 255).withValue(255).build());
        this.Watermarkx = this.register((Setting<Integer>)Settings.integerBuilder("Watermark X").withValue(1).build());
        this.Watermarky = this.register((Setting<Integer>)Settings.integerBuilder("Watermark Y").withValue(0).build());
        this.wred = this.register((Setting<Integer>)Settings.integerBuilder("Watermark Red").withRange(0, 255).withValue(0).build());
        this.wgreen = this.register((Setting<Integer>)Settings.integerBuilder("Watermark Green").withRange(0, 255).withValue(0).build());
        this.wblue = this.register((Setting<Integer>)Settings.integerBuilder("Watermark Blue").withRange(0, 255).withValue(255).build());
        this.fontRenderer = Minecraft.func_71410_x().field_71466_p;
        this.mc = Minecraft.func_71410_x();
        this.viewMode = this.register(Settings.e("LogoType", ViewMode.ICON));
        this.optionX = this.register(Settings.i("LOGO X", 0));
        this.optionY = this.register(Settings.i("LOGO Y", 10));
        final CFontRenderer cFontRenderer = new CFontRenderer(new CFont.CustomFont("/assets/minecraft/font/Comfortaa-Bold.ttf", 18.0f, 0), true, false);
        this.cFontRenderer = cFontRenderer;
        this.cFontRenderer = cFontRenderer;
    }
    
    private static void preboxrender() {
        GL11.glPushMatrix();
        GlStateManager.func_179094_E();
        GlStateManager.func_179118_c();
        GlStateManager.func_179086_m(256);
        GlStateManager.func_179147_l();
    }
    
    private static void postboxrender() {
        GlStateManager.func_179084_k();
        GlStateManager.func_179097_i();
        GlStateManager.func_179140_f();
        GlStateManager.func_179126_j();
        GlStateManager.func_179141_d();
        GlStateManager.func_179121_F();
        GL11.glPopMatrix();
    }
    
    public void onEnable() {
        if (this.mc.field_71439_g == null && this.mc.field_71439_g == null) {
            return;
        }
    }
    
    @Override
    public void onRender() {
        final int hrgb = ColourUtils.toRGBA(this.hred.getValue(), this.hgreen.getValue(), this.hblue.getValue(), 255);
        final int wrgb = ColourUtils.toRGBA(this.wred.getValue(), this.wgreen.getValue(), this.wblue.getValue(), 255);
        this.boxrender(this.optionX.getValue(), this.optionY.getValue());
        if (this.Hello.getValue()) {
            final float[] hue = { System.currentTimeMillis() % 11520L / 11520.0f };
            final int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
            final String playername = this.mc.field_71439_g.func_70005_c_();
            if (this.RainbowHello.getValue()) {
                if (ModuleManager.getModuleByName("SmoothFont").isDisabled()) {
                    this.fontRenderer.func_175063_a("Meow " + playername + " owo", (float)this.Hellowx.getValue(), (float)this.Hellowy.getValue(), rgb);
                }
                else {
                    this.cFontRenderer.drawStringWithShadow("Meow " + playername + " owo", this.Hellowx.getValue(), this.Hellowy.getValue(), rgb);
                }
                final float[] arrf = hue;
                arrf[0] += 0.02f;
            }
            else if (ModuleManager.getModuleByName("SmoothFont").isDisabled()) {
                this.fontRenderer.func_175063_a("Meow " + playername + " owo", (float)this.Hellowx.getValue(), (float)this.Hellowy.getValue(), hrgb);
            }
            else {
                this.cFontRenderer.drawStringWithShadow("Meow " + playername + " owo", this.Hellowx.getValue(), this.Hellowy.getValue(), hrgb);
            }
        }
        if (this.Watermark.getValue()) {
            final float[] hue = { System.currentTimeMillis() % 11520L / 11520.0f };
            final int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
            if (this.RainbowWatermark.getValue()) {
                if (ModuleManager.getModuleByName("SmoothFont").isDisabled()) {
                    this.fontRenderer.func_175063_a("UwUGod v2.0", (float)this.Watermarkx.getValue(), (float)this.Watermarky.getValue(), rgb);
                }
                else {
                    this.cFontRenderer.drawStringWithShadow("UwUGod v2.0", this.Watermarkx.getValue(), this.Watermarky.getValue(), rgb);
                }
                final float[] arrf2 = hue;
                arrf2[0] += 0.02f;
            }
            else if (ModuleManager.getModuleByName("SmoothFont").isDisabled()) {
                this.fontRenderer.func_175063_a("UwUGod v2.0", (float)this.Watermarkx.getValue(), (float)this.Watermarky.getValue(), wrgb);
            }
            else {
                this.cFontRenderer.drawStringWithShadow("UwUGod v2.0", this.Watermarkx.getValue(), this.Watermarky.getValue(), wrgb);
            }
        }
    }
    
    private void boxrender(final int x, final int y) {
        if (this.LOGO.getValue()) {
            GlStateManager.func_179141_d();
            GlStateManager.func_179084_k();
            GlStateManager.func_179097_i();
            preboxrender();
            final ResourceLocation box = this.getBox();
            final float[] hue = { System.currentTimeMillis() % 11520L / 11520.0f };
            final int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
            final int red = rgb >> 16 & 0xFF;
            final int green = rgb >> 8 & 0xFF;
            final int blue = rgb & 0xFF;
            this.mc.field_71446_o.func_110577_a(box);
            GlStateManager.func_179131_c((float)red, (float)green, (float)blue, 1.0f);
            GuiIngame.func_152125_a(x, y, 0.0f, 0.0f, 208, 208, (int)this.size.getValue(), (int)this.size.getValue(), 208.0f, 208.0f);
            postboxrender();
        }
    }
    
    private enum ViewMode
    {
        LOGO, 
        ICON, 
        NOTEXT, 
        NOTEXT2, 
        BELLA;
    }
}
