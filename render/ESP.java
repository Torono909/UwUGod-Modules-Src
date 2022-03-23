// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.render;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.math.Vec3d;
import java.util.Iterator;
import java.awt.Color;
import meow.candycat.uwu.util.Friends;
import org.lwjgl.opengl.GL11;
import meow.candycat.uwu.util.EntityUtil;
import net.minecraft.client.renderer.GlStateManager;
import meow.candycat.uwu.util.UwUGodTessellator;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;
import meow.candycat.uwu.event.events.RenderEvent;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "ESP", category = Category.RENDER)
public class ESP extends Module
{
    private Setting<espmods> espmod;
    private Setting<Boolean> players;
    private Setting<Boolean> animals;
    private Setting<Boolean> monsters;
    private Setting<Boolean> items;
    private Setting<Boolean> xpBottles;
    private Setting<Boolean> crystals;
    private Setting<Boolean> orbs;
    private Setting<Integer> LineWidth;
    public Setting<Boolean> RGB;
    private Setting<Float> Red;
    private Setting<Float> Green;
    private Setting<Float> Blue;
    private Setting<Float> Alpha;
    public Setting<Boolean> RGB2;
    private Setting<Integer> red;
    private Setting<Integer> green;
    private Setting<Integer> blue;
    private Setting<Integer> alpha;
    public Setting<Boolean> Rainbow;
    private Setting<Float> RGBSpeed;
    public Setting<Float> Saturation;
    public Setting<Float> Brightness;
    public Setting<Boolean> Rainbow2;
    private Setting<Float> RGBSpeed2;
    public Setting<Float> Saturation2;
    public Setting<Float> Brightness2;
    
    public ESP() {
        this.espmod = this.register((Setting<espmods>)Settings.enumBuilder(espmods.class).withName("ESP Modes").withValue(espmods.CsGo).build());
        this.players = this.register(Settings.b("Players", true));
        this.animals = this.register(Settings.b("Animals", true));
        this.monsters = this.register(Settings.b("Monsters", false));
        this.items = this.register(Settings.b("Items", false));
        this.xpBottles = this.register(Settings.b("XpBottles", false));
        this.crystals = this.register(Settings.b("Crystals", false));
        this.orbs = this.register(Settings.b("XpOrbs", false));
        this.LineWidth = this.register((Setting<Integer>)Settings.integerBuilder("LineWidth").withMaximum(8).withMinimum(1).withValue(1).build());
        this.RGB = this.register(Settings.booleanBuilder("Player RGB").withValue(false).build());
        this.Red = this.register(Settings.floatBuilder("Red").withRange(0.0f, 1.0f).withValue(1.0f).withVisibility(v -> this.RGB.getValue()).build());
        this.Green = this.register(Settings.floatBuilder("Green").withRange(0.0f, 1.0f).withValue(0.0f).withVisibility(v -> this.RGB.getValue()).build());
        this.Blue = this.register(Settings.floatBuilder("Blue").withRange(0.0f, 1.0f).withValue(0.0f).withVisibility(v -> this.RGB.getValue()).build());
        this.Alpha = this.register(Settings.floatBuilder("Alpha").withRange(0.0f, 1.0f).withValue(1.0f).withVisibility(v -> this.RGB.getValue()).build());
        this.RGB2 = this.register(Settings.booleanBuilder("Other RGB").withValue(false).build());
        this.red = this.register(Settings.integerBuilder("Red").withMinimum(0).withMaximum(255).withValue(255).withVisibility(v -> this.RGB2.getValue()).build());
        this.green = this.register(Settings.integerBuilder("Green").withMinimum(0).withMaximum(255).withValue(0).withVisibility(v -> this.RGB2.getValue()).build());
        this.blue = this.register(Settings.integerBuilder("Blue").withMinimum(0).withMaximum(255).withValue(255).withVisibility(v -> this.RGB2.getValue()).build());
        this.alpha = this.register(Settings.integerBuilder("Alpha").withMinimum(0).withMaximum(255).withValue(255).withVisibility(v -> this.RGB2.getValue()).build());
        this.Rainbow = this.register(Settings.booleanBuilder("Player Rainbow").withValue(false).build());
        this.RGBSpeed = this.register(Settings.floatBuilder("RGB speed").withMinimum(0.0f).withValue(1.0f).withVisibility(b -> this.Rainbow.getValue()).build());
        this.Saturation = this.register(Settings.floatBuilder("Saturation").withRange(0.0f, 1.0f).withValue(1.0f).withVisibility(b -> this.Rainbow.getValue()).build());
        this.Brightness = this.register(Settings.floatBuilder("Brightness").withRange(0.0f, 1.0f).withValue(1.0f).withVisibility(b -> this.Rainbow.getValue()).build());
        this.Rainbow2 = this.register(Settings.booleanBuilder("Other Rainbow").withValue(false).build());
        this.RGBSpeed2 = this.register(Settings.floatBuilder("RGB speed").withMinimum(0.0f).withValue(1.0f).withVisibility(b -> this.Rainbow2.getValue()).build());
        this.Saturation2 = this.register(Settings.floatBuilder("Saturation").withRange(0.0f, 1.0f).withValue(1.0f).withVisibility(b -> this.Rainbow2.getValue()).build());
        this.Brightness2 = this.register(Settings.floatBuilder("Brightness").withRange(0.0f, 1.0f).withValue(1.0f).withVisibility(b -> this.Rainbow2.getValue()).build());
    }
    
    @Override
    public void onWorldRender(final RenderEvent event) {
        if (ESP.mc.func_175598_ae().field_78733_k == null) {
            return;
        }
        final boolean isThirdPersonFrontal = ESP.mc.func_175598_ae().field_78733_k.field_74320_O == 2;
        final float viewerYaw = ESP.mc.func_175598_ae().field_78735_i;
        for (final Entity e : (List)ESP.mc.field_71441_e.field_72996_f.stream().filter(entity -> ESP.mc.field_71439_g != entity).collect(Collectors.toList())) {
            if (this.espmod.getValue() == espmods.Glow) {
                if (e instanceof EntityPlayer && this.players.getValue()) {
                    e.func_184195_f(true);
                }
                if (isanimals(e) && this.animals.getValue()) {
                    e.func_184195_f(true);
                }
                if (e instanceof EntityExpBottle && this.xpBottles.getValue()) {
                    e.func_184195_f(true);
                }
                if (isMonster(e) && this.monsters.getValue()) {
                    e.func_184195_f(true);
                }
                if (e instanceof EntityItem && this.items.getValue()) {
                    e.func_184195_f(true);
                }
                if (e instanceof EntityEnderCrystal && this.crystals.getValue()) {
                    e.func_184195_f(true);
                }
                if (!(e instanceof EntityXPOrb) || !this.orbs.getValue()) {
                    continue;
                }
                e.func_184195_f(true);
            }
            else {
                if (this.espmod.getValue() == espmods.CsGo) {
                    UwUGodTessellator.prepareGL();
                    GlStateManager.func_179094_E();
                    final Vec3d pos = EntityUtil.getInterpolatedPos(e, event.getPartialTicks());
                    GlStateManager.func_179137_b(pos.field_72450_a - ESP.mc.func_175598_ae().field_78725_b, pos.field_72448_b - ESP.mc.func_175598_ae().field_78726_c, pos.field_72449_c - ESP.mc.func_175598_ae().field_78723_d);
                    GlStateManager.func_187432_a(0.0f, 1.0f, 0.0f);
                    GlStateManager.func_179114_b(-viewerYaw, 0.0f, 1.0f, 0.0f);
                    GlStateManager.func_179114_b((float)(isThirdPersonFrontal ? -1 : 1), 1.0f, 0.0f, 0.0f);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
                    GL11.glLineWidth(this.LineWidth.getValue() * 6.0f / ESP.mc.field_71439_g.func_70032_d(e));
                    GL11.glEnable(2848);
                }
                if (e instanceof EntityPlayer && this.players.getValue()) {
                    if (Friends.isFriend(e.func_70005_c_())) {
                        GL11.glColor4f(0.0f, 1.0f, 1.0f, 0.7f);
                    }
                    else if (this.Rainbow.getValue()) {
                        final float[] tick_color = { System.currentTimeMillis() % 11520L / 11520.0f * this.RGBSpeed.getValue() };
                        final int color_rgb = Color.HSBtoRGB(tick_color[0], this.Saturation.getValue(), this.Brightness.getValue());
                        final float r = (color_rgb >> 16 & 0xFF) / 255.0f;
                        final float g = (color_rgb >> 8 & 0xFF) / 255.0f;
                        final float b = (color_rgb & 0xFF) / 255.0f;
                        GL11.glColor4f(r, g, b, 0.7f);
                    }
                    else {
                        GL11.glColor4f((float)this.Red.getValue(), (float)this.Green.getValue(), (float)this.Blue.getValue(), (float)this.Alpha.getValue());
                    }
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)(-e.field_70130_N), 0.0);
                    GL11.glVertex2d((double)(-e.field_70130_N), (double)(e.field_70131_O / 3.0f));
                    GL11.glVertex2d((double)(-e.field_70130_N), 0.0);
                    GL11.glVertex2d((double)(-e.field_70130_N / 3.0f * 2.0f), 0.0);
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)(-e.field_70130_N), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(-e.field_70130_N / 3.0f * 2.0f), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(-e.field_70130_N), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(-e.field_70130_N), (double)(e.field_70131_O / 3.0f * 2.0f));
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)e.field_70130_N, (double)e.field_70131_O);
                    GL11.glVertex2d((double)(e.field_70130_N / 3.0f * 2.0f), (double)e.field_70131_O);
                    GL11.glVertex2d((double)e.field_70130_N, (double)e.field_70131_O);
                    GL11.glVertex2d((double)e.field_70130_N, (double)(e.field_70131_O / 3.0f * 2.0f));
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)e.field_70130_N, 0.0);
                    GL11.glVertex2d((double)(e.field_70130_N / 3.0f * 2.0f), 0.0);
                    GL11.glVertex2d((double)e.field_70130_N, 0.0);
                    GL11.glVertex2d((double)e.field_70130_N, (double)(e.field_70131_O / 3.0f));
                    GL11.glEnd();
                }
                if (this.Rainbow2.getValue().equals(true)) {
                    final float[] tick_color = { System.currentTimeMillis() % 11520L / 11520.0f * this.RGBSpeed2.getValue() };
                    final int color_rgb = Color.HSBtoRGB(tick_color[0], this.Saturation2.getValue(), this.Brightness2.getValue());
                    final float r = (color_rgb >> 16 & 0xFF) / 255.0f;
                    final float g = (color_rgb >> 8 & 0xFF) / 255.0f;
                    final float b = (color_rgb & 0xFF) / 255.0f;
                    GL11.glColor4f(r, g, b, 0.5f);
                }
                else {
                    GL11.glColor4f((float)this.red.getValue(), (float)this.green.getValue(), (float)this.blue.getValue(), (float)this.alpha.getValue());
                }
                if (isanimals(e) && this.animals.getValue()) {
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)(-e.field_70130_N), 0.0);
                    GL11.glVertex2d((double)(-e.field_70130_N), (double)(e.field_70131_O / 3.0f));
                    GL11.glVertex2d((double)(-e.field_70130_N), 0.0);
                    GL11.glVertex2d((double)(-e.field_70130_N / 3.0f * 2.0f), 0.0);
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)(-e.field_70130_N), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(-e.field_70130_N / 3.0f * 2.0f), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(-e.field_70130_N), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(-e.field_70130_N), (double)(e.field_70131_O / 3.0f * 2.0f));
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)e.field_70130_N, (double)e.field_70131_O);
                    GL11.glVertex2d((double)(e.field_70130_N / 3.0f * 2.0f), (double)e.field_70131_O);
                    GL11.glVertex2d((double)e.field_70130_N, (double)e.field_70131_O);
                    GL11.glVertex2d((double)e.field_70130_N, (double)(e.field_70131_O / 3.0f * 2.0f));
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)e.field_70130_N, 0.0);
                    GL11.glVertex2d((double)(e.field_70130_N / 3.0f * 2.0f), 0.0);
                    GL11.glVertex2d((double)e.field_70130_N, 0.0);
                    GL11.glVertex2d((double)e.field_70130_N, (double)(e.field_70131_O / 3.0f));
                    GL11.glEnd();
                }
                if (isMonster(e) && this.monsters.getValue()) {
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)(-e.field_70130_N), 0.0);
                    GL11.glVertex2d((double)(-e.field_70130_N), (double)(e.field_70131_O / 3.0f));
                    GL11.glVertex2d((double)(-e.field_70130_N), 0.0);
                    GL11.glVertex2d((double)(-e.field_70130_N / 3.0f * 2.0f), 0.0);
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)(-e.field_70130_N), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(-e.field_70130_N / 3.0f * 2.0f), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(-e.field_70130_N), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(-e.field_70130_N), (double)(e.field_70131_O / 3.0f * 2.0f));
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)e.field_70130_N, (double)e.field_70131_O);
                    GL11.glVertex2d((double)(e.field_70130_N / 3.0f * 2.0f), (double)e.field_70131_O);
                    GL11.glVertex2d((double)e.field_70130_N, (double)e.field_70131_O);
                    GL11.glVertex2d((double)e.field_70130_N, (double)(e.field_70131_O / 3.0f * 2.0f));
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)e.field_70130_N, 0.0);
                    GL11.glVertex2d((double)(e.field_70130_N / 3.0f * 2.0f), 0.0);
                    GL11.glVertex2d((double)e.field_70130_N, 0.0);
                    GL11.glVertex2d((double)e.field_70130_N, (double)(e.field_70131_O / 3.0f));
                    GL11.glEnd();
                }
                if (e instanceof EntityItem && this.items.getValue()) {
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)(-e.field_70130_N), 0.0);
                    GL11.glVertex2d((double)(-e.field_70130_N), (double)(e.field_70131_O / 3.0f));
                    GL11.glVertex2d((double)(-e.field_70130_N), 0.0);
                    GL11.glVertex2d((double)(-e.field_70130_N / 3.0f * 2.0f), 0.0);
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)(-e.field_70130_N), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(-e.field_70130_N / 3.0f * 2.0f), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(-e.field_70130_N), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(-e.field_70130_N), (double)(e.field_70131_O / 3.0f * 2.0f));
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)e.field_70130_N, (double)e.field_70131_O);
                    GL11.glVertex2d((double)(e.field_70130_N / 3.0f * 2.0f), (double)e.field_70131_O);
                    GL11.glVertex2d((double)e.field_70130_N, (double)e.field_70131_O);
                    GL11.glVertex2d((double)e.field_70130_N, (double)(e.field_70131_O / 3.0f * 2.0f));
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)e.field_70130_N, 0.0);
                    GL11.glVertex2d((double)(e.field_70130_N / 3.0f * 2.0f), 0.0);
                    GL11.glVertex2d((double)e.field_70130_N, 0.0);
                    GL11.glVertex2d((double)e.field_70130_N, (double)(e.field_70131_O / 3.0f));
                    GL11.glEnd();
                }
                if (e instanceof EntityExpBottle && this.xpBottles.getValue()) {
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)(-e.field_70130_N), 0.0);
                    GL11.glVertex2d((double)(-e.field_70130_N), (double)(e.field_70131_O / 3.0f));
                    GL11.glVertex2d((double)(-e.field_70130_N), 0.0);
                    GL11.glVertex2d((double)(-e.field_70130_N / 3.0f * 2.0f), 0.0);
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)(-e.field_70130_N), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(-e.field_70130_N / 3.0f * 2.0f), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(-e.field_70130_N), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(-e.field_70130_N), (double)(e.field_70131_O / 3.0f * 2.0f));
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)e.field_70130_N, (double)e.field_70131_O);
                    GL11.glVertex2d((double)(e.field_70130_N / 3.0f * 2.0f), (double)e.field_70131_O);
                    GL11.glVertex2d((double)e.field_70130_N, (double)e.field_70131_O);
                    GL11.glVertex2d((double)e.field_70130_N, (double)(e.field_70131_O / 3.0f * 2.0f));
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)e.field_70130_N, 0.0);
                    GL11.glVertex2d((double)(e.field_70130_N / 3.0f * 2.0f), 0.0);
                    GL11.glVertex2d((double)e.field_70130_N, 0.0);
                    GL11.glVertex2d((double)e.field_70130_N, (double)(e.field_70131_O / 3.0f));
                    GL11.glEnd();
                }
                if (e instanceof EntityEnderCrystal && this.crystals.getValue()) {
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)(-e.field_70130_N / 2.0f), 0.0);
                    GL11.glVertex2d((double)(-e.field_70130_N / 2.0f), (double)(e.field_70131_O / 3.0f));
                    GL11.glVertex2d((double)(-e.field_70130_N / 2.0f), 0.0);
                    GL11.glVertex2d((double)(-e.field_70130_N / 3.0f * 2.0f / 2.0f), 0.0);
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)(-e.field_70130_N / 2.0f), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(-e.field_70130_N / 3.0f * 2.0f / 2.0f), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(-e.field_70130_N / 2.0f), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(-e.field_70130_N / 2.0f), (double)(e.field_70131_O / 3.0f * 2.0f));
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)(e.field_70130_N / 2.0f), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(e.field_70130_N / 3.0f * 2.0f / 2.0f), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(e.field_70130_N / 2.0f), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(e.field_70130_N / 2.0f), (double)(e.field_70131_O / 3.0f * 2.0f));
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)(e.field_70130_N / 2.0f), 0.0);
                    GL11.glVertex2d((double)(e.field_70130_N / 3.0f * 2.0f / 2.0f), 0.0);
                    GL11.glVertex2d((double)(e.field_70130_N / 2.0f), 0.0);
                    GL11.glVertex2d((double)(e.field_70130_N / 2.0f), (double)(e.field_70131_O / 3.0f));
                    GL11.glEnd();
                }
                if (e instanceof EntityXPOrb && this.orbs.getValue()) {
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)(-e.field_70130_N / 2.0f), 0.0);
                    GL11.glVertex2d((double)(-e.field_70130_N / 2.0f), (double)(e.field_70131_O / 3.0f));
                    GL11.glVertex2d((double)(-e.field_70130_N / 2.0f), 0.0);
                    GL11.glVertex2d((double)(-e.field_70130_N / 3.0f * 2.0f / 2.0f), 0.0);
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)(-e.field_70130_N / 2.0f), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(-e.field_70130_N / 3.0f * 2.0f / 2.0f), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(-e.field_70130_N / 2.0f), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(-e.field_70130_N / 2.0f), (double)(e.field_70131_O / 3.0f * 2.0f));
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)(e.field_70130_N / 2.0f), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(e.field_70130_N / 3.0f * 2.0f / 2.0f), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(e.field_70130_N / 2.0f), (double)e.field_70131_O);
                    GL11.glVertex2d((double)(e.field_70130_N / 2.0f), (double)(e.field_70131_O / 3.0f * 2.0f));
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)(e.field_70130_N / 2.0f), 0.0);
                    GL11.glVertex2d((double)(e.field_70130_N / 3.0f * 2.0f / 2.0f), 0.0);
                    GL11.glVertex2d((double)(e.field_70130_N / 2.0f), 0.0);
                    GL11.glVertex2d((double)(e.field_70130_N / 2.0f), (double)(e.field_70131_O / 3.0f));
                    GL11.glEnd();
                }
                UwUGodTessellator.releaseGL();
                GlStateManager.func_179121_F();
            }
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static boolean isanimals(final Entity e) {
        return (!(e instanceof EntityWolf) || !((EntityWolf)e).func_70919_bu()) && (e instanceof EntityAgeable || e instanceof EntityAmbientCreature || e instanceof EntitySquid || (e instanceof EntityIronGolem && ((EntityIronGolem)e).func_70643_av() == null));
    }
    
    public static boolean isMonster(final Entity entity) {
        return entity.isCreatureType(EnumCreatureType.MONSTER, false);
    }
    
    @Override
    public String getHudInfo() {
        return this.espmod.getValue().toString();
    }
    
    private enum espmods
    {
        CsGo, 
        Glow;
    }
}
