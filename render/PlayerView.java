// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "PlayerView", category = Category.RENDER, description = "awa")
public class PlayerView extends Module
{
    private Setting<Float> x;
    private Setting<Float> Y;
    private Setting<Float> size;
    
    public PlayerView() {
        this.x = this.register(Settings.f("InfoX", 100.0f));
        this.Y = this.register(Settings.f("InfoY", 100.0f));
        this.size = this.register(Settings.f("size", 45.0f));
    }
    
    @Override
    public void onRender() {
        this.boxRender(this.x.getValue(), this.Y.getValue(), this.size.getValue());
    }
    
    private void boxRender(final Float x, final Float Y, final Float size) {
        drawEntityOnScreen(x, Y, size, (EntityLivingBase)PlayerView.mc.field_71439_g);
    }
    
    public static void drawEntityOnScreen(final Float x, final Float Y, final Float size, final EntityLivingBase ent) {
        GlStateManager.func_179142_g();
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b((float)x, (float)Y, 50.0f);
        GlStateManager.func_179152_a(-size, (float)size, (float)size);
        GlStateManager.func_179114_b(180.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.func_179114_b(135.0f, 0.0f, 1.0f, 0.0f);
        RenderHelper.func_74519_b();
        GlStateManager.func_179114_b(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(-(float)Math.atan(0.0) * 20.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179109_b(0.0f, 0.0f, 0.0f);
        final RenderManager rendermanager = Minecraft.func_71410_x().func_175598_ae();
        rendermanager.func_178631_a(180.0f);
        rendermanager.func_178633_a(false);
        rendermanager.func_188391_a((Entity)ent, 0.0, 0.0, 0.0, 0.0f, 1.0f, false);
        rendermanager.func_178633_a(true);
        GlStateManager.func_179121_F();
        RenderHelper.func_74518_a();
        GlStateManager.func_179101_C();
        GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
        GlStateManager.func_179090_x();
        GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
    }
}
