// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.render;

import meow.candycat.uwu.setting.Settings;
import java.util.Iterator;
import meow.candycat.uwu.util.Pair;
import java.util.HashMap;
import net.minecraft.world.BossInfo;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.BossInfoClient;
import java.util.UUID;
import java.util.Map;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraft.util.ResourceLocation;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "BossStack", description = "Modify the boss health GUI to take up less space", category = Category.MISC)
public class BossStack extends Module
{
    private static Setting<BossStackMode> mode;
    private static Setting<Double> scale;
    private static final ResourceLocation GUI_BARS_TEXTURES;
    
    public BossStack() {
        this.registerAll(BossStack.mode, BossStack.scale);
    }
    
    public static void render(final RenderGameOverlayEvent.Post event) {
        if (BossStack.mode.getValue() == BossStackMode.MINIMIZE) {
            final Map<UUID, BossInfoClient> map = (Map<UUID, BossInfoClient>)Minecraft.func_71410_x().field_71456_v.func_184046_j().field_184060_g;
            if (map == null) {
                return;
            }
            final ScaledResolution scaledresolution = new ScaledResolution(Minecraft.func_71410_x());
            final int i = scaledresolution.func_78326_a();
            int j = 12;
            for (final Map.Entry<UUID, BossInfoClient> entry : map.entrySet()) {
                final BossInfoClient info = entry.getValue();
                final String text = info.func_186744_e().func_150254_d();
                final int k = (int)(i / BossStack.scale.getValue() / 2.0 - 91.0);
                GL11.glScaled((double)BossStack.scale.getValue(), (double)BossStack.scale.getValue(), 1.0);
                if (!event.isCanceled()) {
                    GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                    Minecraft.func_71410_x().func_110434_K().func_110577_a(BossStack.GUI_BARS_TEXTURES);
                    Minecraft.func_71410_x().field_71456_v.func_184046_j().func_184052_a(k, j, (BossInfo)info);
                    Minecraft.func_71410_x().field_71466_p.func_175063_a(text, (float)(i / BossStack.scale.getValue() / 2.0 - Minecraft.func_71410_x().field_71466_p.func_78256_a(text) / 2), (float)(j - 9), 16777215);
                }
                GL11.glScaled(1.0 / BossStack.scale.getValue(), 1.0 / BossStack.scale.getValue(), 1.0);
                j += 10 + Minecraft.func_71410_x().field_71466_p.field_78288_b;
            }
        }
        else if (BossStack.mode.getValue() == BossStackMode.STACK) {
            final Map<UUID, BossInfoClient> map = (Map<UUID, BossInfoClient>)Minecraft.func_71410_x().field_71456_v.func_184046_j().field_184060_g;
            final HashMap<String, Pair<BossInfoClient, Integer>> to = new HashMap<String, Pair<BossInfoClient, Integer>>();
            for (final Map.Entry<UUID, BossInfoClient> entry2 : map.entrySet()) {
                final String s = entry2.getValue().func_186744_e().func_150254_d();
                if (to.containsKey(s)) {
                    Pair<BossInfoClient, Integer> p = to.get(s);
                    p = new Pair<BossInfoClient, Integer>(p.getKey(), p.getValue() + 1);
                    to.put(s, p);
                }
                else {
                    final Pair<BossInfoClient, Integer> p = new Pair<BossInfoClient, Integer>(entry2.getValue(), 1);
                    to.put(s, p);
                }
            }
            final ScaledResolution scaledresolution2 = new ScaledResolution(Minecraft.func_71410_x());
            final int l = scaledresolution2.func_78326_a();
            int m = 12;
            for (final Map.Entry<String, Pair<BossInfoClient, Integer>> entry3 : to.entrySet()) {
                String text = entry3.getKey();
                final BossInfoClient info2 = entry3.getValue().getKey();
                final int a = entry3.getValue().getValue();
                text = text + " x" + a;
                final int k2 = (int)(l / BossStack.scale.getValue() / 2.0 - 91.0);
                GL11.glScaled((double)BossStack.scale.getValue(), (double)BossStack.scale.getValue(), 1.0);
                if (!event.isCanceled()) {
                    GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                    Minecraft.func_71410_x().func_110434_K().func_110577_a(BossStack.GUI_BARS_TEXTURES);
                    Minecraft.func_71410_x().field_71456_v.func_184046_j().func_184052_a(k2, m, (BossInfo)info2);
                    Minecraft.func_71410_x().field_71466_p.func_175063_a(text, (float)(l / BossStack.scale.getValue() / 2.0 - Minecraft.func_71410_x().field_71466_p.func_78256_a(text) / 2), (float)(m - 9), 16777215);
                }
                GL11.glScaled(1.0 / BossStack.scale.getValue(), 1.0 / BossStack.scale.getValue(), 1.0);
                m += 10 + Minecraft.func_71410_x().field_71466_p.field_78288_b;
            }
        }
    }
    
    static {
        BossStack.mode = Settings.e("Mode", BossStackMode.STACK);
        BossStack.scale = Settings.d("Scale", 0.5);
        GUI_BARS_TEXTURES = new ResourceLocation("textures/gui/bars.png");
    }
    
    private enum BossStackMode
    {
        REMOVE, 
        STACK, 
        MINIMIZE;
    }
}
