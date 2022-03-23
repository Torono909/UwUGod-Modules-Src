// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.render;

import net.minecraft.item.Item;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemSword;
import meow.candycat.uwu.util.Enemies;
import meow.candycat.uwu.util.Friends;
import meow.candycat.uwu.module.ModuleManager;
import meow.candycat.uwu.module.modules.combat.TotemPopCounter;
import java.util.Objects;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemTool;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import java.awt.Color;
import meow.candycat.uwu.util.ColourConverter;
import net.minecraft.util.math.Vec3d;
import meow.candycat.uwu.util.BlockInteractionHelper;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import meow.candycat.uwu.event.events.RenderEvent;
import java.awt.Font;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.gui.font.CFontRenderer;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "Nametags", description = "Draws descriptive nametags above entities", category = Category.RENDER)
public class Nametags extends Module
{
    public Setting<Double> range;
    public Setting<Boolean> rainbowMode;
    public Setting<Integer> sat;
    public Setting<Integer> brightness;
    public Setting<Integer> red;
    public Setting<Integer> green;
    public Setting<Integer> blue;
    public Setting<Integer> alpha;
    public Setting<Integer> scale;
    public Setting<Boolean> showArmor;
    public Setting<Boolean> reverseArmor;
    public Setting<Boolean> simplify;
    public Setting<Boolean> showHealth;
    public Setting<Boolean> showPing;
    public Setting<Boolean> showTot;
    CFontRenderer cFontRenderer;
    
    public Nametags() {
        this.range = this.register(Settings.d("Range", 250.0));
        this.rainbowMode = this.register(Settings.b("RainbowMode"));
        this.sat = this.register((Setting<Integer>)Settings.integerBuilder().withName("SaturationR").withValue(117).withMinimum(0).withMaximum(255).build());
        this.brightness = this.register((Setting<Integer>)Settings.integerBuilder().withName("BrightnessR").withValue(255).withMinimum(0).withMaximum(255).build());
        this.red = this.register((Setting<Integer>)Settings.integerBuilder("Red").withMinimum(0).withValue(255).withMaximum(255).build());
        this.green = this.register((Setting<Integer>)Settings.integerBuilder("Green").withMinimum(0).withValue(255).withMaximum(255).build());
        this.blue = this.register((Setting<Integer>)Settings.integerBuilder("Blue").withMinimum(0).withValue(255).withMaximum(255).build());
        this.alpha = this.register((Setting<Integer>)Settings.integerBuilder("Alpha").withMinimum(0).withValue(75).withMaximum(255).build());
        this.scale = this.register(Settings.i("Scale", 4));
        this.showArmor = this.register(Settings.b("ShowArmor"));
        this.reverseArmor = this.register(Settings.b("ReverseArmor"));
        this.simplify = this.register(Settings.b("Simplify"));
        this.showHealth = this.register(Settings.b("ShowHealth"));
        this.showPing = this.register(Settings.b("ShowPing"));
        this.showTot = this.register(Settings.b("ShowTot"));
        this.cFontRenderer = new CFontRenderer(new Font("Arial", 0, 36), true, true);
    }
    
    @Override
    public void onWorldRender(final RenderEvent event) {
        for (final EntityPlayer player : Nametags.mc.field_71441_e.field_73010_i) {
            if (player != null && !player.equals((Object)Nametags.mc.field_71439_g) && player.func_70089_S() && Nametags.mc.field_71439_g.func_70032_d((Entity)player) < this.range.getValue() && isInFov((Entity)player)) {
                final double x = this.interpolate(player.field_70142_S, player.field_70165_t, event.getPartialTicks()) - Nametags.mc.func_175598_ae().field_78725_b;
                final double y = this.interpolate(player.field_70137_T, player.field_70163_u, event.getPartialTicks()) - Nametags.mc.func_175598_ae().field_78726_c;
                final double z = this.interpolate(player.field_70136_U, player.field_70161_v, event.getPartialTicks()) - Nametags.mc.func_175598_ae().field_78723_d;
                this.renderNameTag(player, x, y, z, event.getPartialTicks());
            }
        }
    }
    
    public static boolean isInFov(final Entity entity) {
        return entity != null && (BlockInteractionHelper.blockDistance2d(entity.field_70165_t, entity.field_70161_v, (Entity)Nametags.mc.field_71439_g) < 3.0 || (yawDist(entity) < getHalvedFov() * 1.4f && pitchDist(entity) < getHalvedFov() * 1.1f));
    }
    
    public static double yawDist(final Entity e) {
        if (e != null) {
            final Vec3d difference = e.func_174791_d().func_72441_c(0.0, (double)(e.func_70047_e() / 2.0f), 0.0).func_178788_d(Nametags.mc.field_71439_g.func_174824_e(Nametags.mc.func_184121_ak()));
            final double d = Math.abs(Nametags.mc.field_71439_g.field_70177_z - (Math.toDegrees(Math.atan2(difference.field_72449_c, difference.field_72450_a)) - 90.0)) % 360.0;
            return (d > 180.0) ? (360.0 - d) : d;
        }
        return 0.0;
    }
    
    public static double pitchDist(final Entity e) {
        if (e != null) {
            final Vec3d difference = e.func_174791_d().func_72441_c(0.0, (double)(e.func_70047_e() / 2.0f), 0.0).func_178788_d(Nametags.mc.field_71439_g.func_174824_e(Nametags.mc.func_184121_ak()));
            final float pitch = (float)(-Math.toDegrees(Math.atan2(difference.field_72448_b, Math.sqrt(difference.field_72449_c * difference.field_72449_c + difference.field_72450_a * difference.field_72450_a))));
            return Math.abs(Nametags.mc.field_71439_g.field_70125_A - (double)pitch);
        }
        return 0.0;
    }
    
    public static float getHalvedFov() {
        return Nametags.mc.field_71474_y.field_74334_X / 2.0f;
    }
    
    @Override
    public void onUpdate() {
        if (this.rainbowMode.getValue()) {
            this.cycle_rainbow();
        }
    }
    
    public void cycle_rainbow() {
        final float[] tick_color = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int color_rgb_o = Color.HSBtoRGB(tick_color[0], ColourConverter.toF(this.sat.getValue()), ColourConverter.toF(this.brightness.getValue()));
        this.red.setValue(color_rgb_o >> 16 & 0xFF);
        this.green.setValue(color_rgb_o >> 8 & 0xFF);
        this.blue.setValue(color_rgb_o & 0xFF);
    }
    
    private void renderNameTag(final EntityPlayer player, final double x, final double y, final double z, final float delta) {
        double tempY = y;
        tempY += (player.func_70093_af() ? 0.5 : 0.7);
        final Entity camera = Nametags.mc.func_175606_aa();
        assert camera != null;
        final double originalPositionX = camera.field_70165_t;
        final double originalPositionY = camera.field_70163_u;
        final double originalPositionZ = camera.field_70161_v;
        camera.field_70165_t = this.interpolate(camera.field_70169_q, camera.field_70165_t, delta);
        camera.field_70163_u = this.interpolate(camera.field_70167_r, camera.field_70163_u, delta);
        camera.field_70161_v = this.interpolate(camera.field_70166_s, camera.field_70161_v, delta);
        final String displayTag = this.getDisplayTag(player);
        final double distance = camera.func_70011_f(x + Nametags.mc.func_175598_ae().field_78730_l, y + Nametags.mc.func_175598_ae().field_78731_m, z + Nametags.mc.func_175598_ae().field_78728_n);
        final int width = this.cFontRenderer.getStringWidth(displayTag) / 2;
        double scale = (0.0018 + this.scale.getValue() * (distance * 0.3)) / 1000.0;
        if (distance <= 8.0) {
            scale = 0.0245;
        }
        if (distance <= 2.0) {
            scale = 0.0125;
        }
        GlStateManager.func_179094_E();
        RenderHelper.func_74519_b();
        GlStateManager.func_179088_q();
        GlStateManager.func_179136_a(1.0f, -1500000.0f);
        GlStateManager.func_179140_f();
        GlStateManager.func_179109_b((float)x, (float)tempY + 1.4f, (float)z);
        GlStateManager.func_179114_b(-Nametags.mc.func_175598_ae().field_78735_i, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(Nametags.mc.func_175598_ae().field_78732_j, (Nametags.mc.field_71474_y.field_74320_O == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179139_a(-scale, -scale, scale);
        GlStateManager.func_179097_i();
        GlStateManager.func_179147_l();
        GlStateManager.func_179147_l();
        drawRect((float)(-width / 2 - 2), (float)(-(this.cFontRenderer.getHeight() / 2 + 1)), width / 2 + 2.0f, 1.5f, 1426063360);
        GlStateManager.func_179084_k();
        final ItemStack renderMainHand = player.func_184614_ca().func_77946_l();
        if (renderMainHand.func_77962_s() && (renderMainHand.func_77973_b() instanceof ItemTool || renderMainHand.func_77973_b() instanceof ItemArmor)) {
            renderMainHand.field_77994_a = 1;
        }
        if (!renderMainHand.field_190928_g && renderMainHand.func_77973_b() != Items.field_190931_a) {
            final String stackName = renderMainHand.func_82833_r();
            final int stackNameWidth = this.cFontRenderer.getStringWidth(stackName) / 2;
            GL11.glPushMatrix();
            GL11.glScalef(0.75f, 0.75f, 0.0f);
            GlStateManager.func_179152_a(0.5f, 0.5f, 0.5f);
            this.cFontRenderer.drawString(stackName, (float)(-stackNameWidth), -(this.getBiggestArmorTag(player) + 36.0f), -1);
            GlStateManager.func_179152_a(2.0f, 2.0f, 2.0f);
            GL11.glScalef(1.5f, 1.5f, 1.0f);
            GL11.glPopMatrix();
        }
        if (this.showArmor.getValue()) {
            GlStateManager.func_179094_E();
            int xOffset = -8;
            for (final ItemStack stack : player.field_71071_by.field_70460_b) {
                if (stack != null) {
                    xOffset -= 8;
                }
            }
            xOffset -= 8;
            final ItemStack renderOffhand = player.func_184592_cb().func_77946_l();
            if (renderOffhand.func_77962_s() && (renderOffhand.func_77973_b() instanceof ItemTool || renderOffhand.func_77973_b() instanceof ItemArmor)) {
                renderOffhand.field_77994_a = 1;
            }
            this.renderItemStack(renderOffhand, xOffset);
            xOffset += 16;
            if (this.reverseArmor.getValue()) {
                for (final ItemStack stack2 : player.field_71071_by.field_70460_b) {
                    if (stack2 != null) {
                        final ItemStack armourStack = stack2.func_77946_l();
                        if (armourStack.func_77962_s() && (armourStack.func_77973_b() instanceof ItemTool || armourStack.func_77973_b() instanceof ItemArmor)) {
                            armourStack.field_77994_a = 1;
                        }
                        this.renderItemStack(armourStack, xOffset);
                        xOffset += 16;
                    }
                }
            }
            else {
                for (int i = player.field_71071_by.field_70460_b.size(); i > 0; --i) {
                    final ItemStack stack2 = (ItemStack)player.field_71071_by.field_70460_b.get(i - 1);
                    final ItemStack armourStack = stack2.func_77946_l();
                    if (armourStack.func_77962_s() && (armourStack.func_77973_b() instanceof ItemTool || armourStack.func_77973_b() instanceof ItemArmor)) {
                        armourStack.field_77994_a = 1;
                    }
                    this.renderItemStack(armourStack, xOffset);
                    xOffset += 16;
                }
            }
            this.renderItemStack(renderMainHand, xOffset);
            GlStateManager.func_179121_F();
        }
        GlStateManager.func_179152_a(0.5f, 0.5f, 0.5f);
        this.cFontRenderer.drawString(displayTag, (float)(-width), (float)(-this.cFontRenderer.getHeight()), this.getDisplayColour(player));
        GlStateManager.func_179152_a(2.0f, 2.0f, 2.0f);
        camera.field_70165_t = originalPositionX;
        camera.field_70163_u = originalPositionY;
        camera.field_70161_v = originalPositionZ;
        GlStateManager.func_179126_j();
        GlStateManager.func_179084_k();
        GlStateManager.func_179113_r();
        GlStateManager.func_179136_a(1.0f, 1500000.0f);
        GlStateManager.func_179121_F();
    }
    
    private void renderItemStack(final ItemStack stack, final int x) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179132_a(true);
        GlStateManager.func_179086_m(256);
        RenderHelper.func_74519_b();
        Nametags.mc.func_175599_af().field_77023_b = -150.0f;
        GlStateManager.func_179118_c();
        GlStateManager.func_179126_j();
        GlStateManager.func_179129_p();
        Nametags.mc.func_175599_af().func_180450_b(stack, x, -29);
        Nametags.mc.func_175599_af().func_175030_a(Nametags.mc.field_71466_p, stack, x, -29);
        Nametags.mc.func_175599_af().field_77023_b = 0.0f;
        RenderHelper.func_74518_a();
        GlStateManager.func_179089_o();
        GlStateManager.func_179141_d();
        GlStateManager.func_179152_a(0.5f, 0.5f, 0.5f);
        GlStateManager.func_179097_i();
        this.renderEnchantmentText(stack, x);
        GlStateManager.func_179126_j();
        GlStateManager.func_179152_a(2.0f, 2.0f, 2.0f);
        GlStateManager.func_179121_F();
    }
    
    private void renderEnchantmentText(final ItemStack stack, final int x) {
        int enchantmentY = -74;
        final NBTTagList enchants = stack.func_77986_q();
        if (enchants.func_74745_c() > 2 && this.simplify.getValue()) {
            GlStateManager.func_179152_a(0.5f, 0.5f, 0.5f);
            this.cFontRenderer.drawString("god", (float)(x * 4), (float)enchantmentY, -3977919);
            GlStateManager.func_179152_a(2.0f, 2.0f, 2.0f);
            enchantmentY -= 8;
        }
        else {
            for (int index = 0; index < enchants.func_74745_c(); ++index) {
                final short id = enchants.func_150305_b(index).func_74765_d("id");
                final short level = enchants.func_150305_b(index).func_74765_d("lvl");
                final Enchantment enc = Enchantment.func_185262_c((int)id);
                if (enc != null) {
                    String encName = enc.func_190936_d() ? (TextFormatting.RED + enc.func_77316_c((int)level).substring(11).substring(0, 1).toLowerCase()) : enc.func_77316_c((int)level).substring(0, 1).toLowerCase();
                    encName += level;
                    GlStateManager.func_179152_a(0.5f, 0.5f, 0.5f);
                    this.cFontRenderer.drawString(encName, (float)(x * 4), (float)enchantmentY, -1);
                    GlStateManager.func_179139_a(2.0, 2.0, 2.0);
                    enchantmentY -= 16;
                }
            }
        }
        if (hasDurability(stack)) {
            final int percent = getRoundedDamage(stack);
            String color;
            if (percent >= 60) {
                color = this.section_sign() + "a";
            }
            else if (percent >= 25) {
                color = this.section_sign() + "e";
            }
            else {
                color = this.section_sign() + "c";
            }
            GlStateManager.func_179152_a(0.5f, 0.5f, 0.5f);
            this.cFontRenderer.drawString(color + percent + "%", (float)(x * 4), (enchantmentY < -124) ? ((float)enchantmentY) : -124.0f, -1);
            GlStateManager.func_179139_a(2.0, 2.0, 2.0);
        }
    }
    
    private float getBiggestArmorTag(final EntityPlayer player) {
        float enchantmentY = 0.0f;
        boolean arm = false;
        for (final ItemStack stack : player.field_71071_by.field_70460_b) {
            float encY = 0.0f;
            if (stack != null) {
                final NBTTagList enchants = stack.func_77986_q();
                for (int index = 0; index < enchants.func_74745_c(); ++index) {
                    final short id = enchants.func_150305_b(index).func_74765_d("id");
                    final Enchantment enc = Enchantment.func_185262_c((int)id);
                    if (enc != null) {
                        encY += 16.0f;
                        arm = true;
                    }
                }
            }
            if (encY > enchantmentY) {
                enchantmentY = encY;
            }
        }
        final ItemStack renderMainHand = player.func_184614_ca().func_77946_l();
        if (renderMainHand.func_77962_s()) {
            float encY2 = 0.0f;
            final NBTTagList enchants2 = renderMainHand.func_77986_q();
            for (int index2 = 0; index2 < enchants2.func_74745_c(); ++index2) {
                final short id2 = enchants2.func_150305_b(index2).func_74765_d("id");
                final Enchantment enc2 = Enchantment.func_185262_c((int)id2);
                if (enc2 != null) {
                    encY2 += 16.0f;
                    arm = true;
                }
            }
            if (encY2 > enchantmentY) {
                enchantmentY = encY2;
            }
        }
        final ItemStack renderOffHand = player.func_184592_cb().func_77946_l();
        if (renderOffHand.func_77962_s()) {
            float encY = 0.0f;
            final NBTTagList enchants = renderOffHand.func_77986_q();
            for (int index = 0; index < enchants.func_74745_c(); ++index) {
                final short id = enchants.func_150305_b(index).func_74765_d("id");
                final Enchantment enc = Enchantment.func_185262_c((int)id);
                if (enc != null) {
                    encY += 16.0f;
                    arm = true;
                }
            }
            if (encY > enchantmentY) {
                enchantmentY = encY;
            }
        }
        return (arm ? 0 : 80) + enchantmentY;
    }
    
    public static void drawRect(final float x, final float y, final float w, final float h, final int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder bufferbuilder = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b((double)x, (double)h, 0.0).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b((double)w, (double)h, 0.0).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b((double)w, (double)y, 0.0).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b((double)x, (double)y, 0.0).func_181666_a(red, green, blue, alpha).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }
    
    public static void drawRect(final float x, final float y, final float w, final float h, final float r, final float g, final float b, final float a) {
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder bufferbuilder = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b((double)x, (double)h, 0.0).func_181666_a(r / 255.0f, g / 255.0f, b / 255.0f, a).func_181675_d();
        bufferbuilder.func_181662_b((double)w, (double)h, 0.0).func_181666_a(r / 255.0f, g / 255.0f, b / 255.0f, a).func_181675_d();
        bufferbuilder.func_181662_b((double)w, (double)y, 0.0).func_181666_a(r / 255.0f, g / 255.0f, b / 255.0f, a).func_181675_d();
        bufferbuilder.func_181662_b((double)x, (double)y, 0.0).func_181666_a(r / 255.0f, g / 255.0f, b / 255.0f, a).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }
    
    private String getDisplayTag(final EntityPlayer player) {
        String name = player.getDisplayNameString();
        if (!this.showHealth.getValue()) {
            return name;
        }
        final float health = player.func_110143_aJ() + player.func_110139_bj();
        if (health <= 0.0f) {
            return name + " - DEAD";
        }
        String color;
        if (health > 25.0f) {
            color = this.section_sign() + "5";
        }
        else if (health > 20.0f) {
            color = this.section_sign() + "a";
        }
        else if (health > 15.0f) {
            color = this.section_sign() + "2";
        }
        else if (health > 10.0f) {
            color = this.section_sign() + "6";
        }
        else if (health > 5.0f) {
            color = this.section_sign() + "c";
        }
        else {
            color = this.section_sign() + "4";
        }
        String pingStr = "";
        if (this.showPing.getValue()) {
            try {
                final int responseTime = Objects.requireNonNull(Nametags.mc.func_147114_u()).func_175102_a(player.func_110124_au()).func_178853_c();
                if (responseTime > 200) {
                    pingStr = pingStr + this.section_sign() + "4";
                }
                else if (responseTime > 100) {
                    pingStr = pingStr + this.section_sign() + "6";
                }
                else {
                    pingStr = pingStr + this.section_sign() + "2";
                }
                pingStr = pingStr + responseTime + "ms ";
            }
            catch (Exception ex) {}
        }
        String popStr = " ";
        if (this.showTot.getValue()) {
            final String target = player.func_70005_c_();
            final TotemPopCounter totemPopCounter = (TotemPopCounter)ModuleManager.getModuleByName("TotemPopCounter");
            if (totemPopCounter.popList.get(target) != null) {
                popStr += totemPopCounter.popList.get(target);
            }
        }
        if (Math.floor(health) == health) {
            name = name + color + " " + ((health > 0.0f) ? Integer.valueOf((int)Math.floor(health)) : "dead");
        }
        else {
            name = name + color + " " + ((health > 0.0f) ? Integer.valueOf((int)health) : "dead");
        }
        return pingStr + this.section_sign() + "r" + name + this.section_sign() + "r" + popStr;
    }
    
    private int getDisplayColour(final EntityPlayer player) {
        final int colour = -5592406;
        if (Friends.isFriend(player.func_70005_c_())) {
            return -11157267;
        }
        if (Enemies.isEnemy(player.func_70005_c_())) {
            return new Color(255, 90, 88, 255).getRGB();
        }
        return colour;
    }
    
    private double interpolate(final double previous, final double current, final float delta) {
        return previous + (current - previous) * delta;
    }
    
    public String section_sign() {
        return "§";
    }
    
    public static int getItemDamage(final ItemStack stack) {
        return stack.func_77958_k() - stack.func_77952_i();
    }
    
    public static float getDamageInPercent(final ItemStack stack) {
        return getItemDamage(stack) / (float)stack.func_77958_k() * 100.0f;
    }
    
    public static int getRoundedDamage(final ItemStack stack) {
        return (int)getDamageInPercent(stack);
    }
    
    public static boolean hasDurability(final ItemStack stack) {
        final Item item = stack.func_77973_b();
        return item instanceof ItemArmor || item instanceof ItemSword || item instanceof ItemTool || item instanceof ItemShield;
    }
}
