// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.gui;

import meow.candycat.uwu.util.Friends;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.math.Vec3d;
import meow.candycat.uwu.util.MathUtil;
import meow.candycat.uwu.util.EntityUtil;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import java.util.Iterator;
import net.minecraft.init.MobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import meow.candycat.uwu.module.modules.combat.TotemPopCounter;
import java.text.DecimalFormat;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager;
import meow.candycat.uwu.util.RenderUtils;
import java.awt.Color;
import meow.candycat.uwu.module.modules.combat.KillAura;
import net.minecraft.entity.player.EntityPlayer;
import meow.candycat.uwu.module.modules.combat.AutoCrystal;
import meow.candycat.uwu.module.ModuleManager;
import net.minecraft.client.Minecraft;
import java.awt.Font;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.gui.font.CFontRenderer;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "PhobosTargetHud", category = Category.GUI)
public class TargetHud extends Module
{
    private Setting<Integer> targetHudX;
    private Setting<Integer> targetHudY;
    private Setting<Boolean> targetHudBackground;
    CFontRenderer cFontRenderer;
    
    public TargetHud() {
        this.targetHudX = this.register(Settings.i("TargetHudX", 1));
        this.targetHudY = this.register(Settings.i("TargetHudY", 1));
        this.targetHudBackground = this.register(Settings.b("targetHudBackground"));
        this.cFontRenderer = new CFontRenderer(new Font("Verdana", 0, 18), true, true);
    }
    
    @Override
    public void onRender() {
        this.drawTargetHud(Minecraft.func_71410_x().func_184121_ak());
    }
    
    public void drawTargetHud(final float partialTicks) {
        EntityPlayer target = null;
        Label_0081: {
            if (ModuleManager.getModuleByName("AutoCrystal").isEnabled()) {
                final AutoCrystal autoCrystal = (AutoCrystal)ModuleManager.getModuleByName("AutoCrystal");
                if (AutoCrystal.renderEnt != null) {
                    final AutoCrystal autoCrystal2 = (AutoCrystal)ModuleManager.getModuleByName("AutoCrystal");
                    target = (EntityPlayer)AutoCrystal.renderEnt;
                    break Label_0081;
                }
            }
            if (((KillAura)ModuleManager.getModuleByName("KillAura")).lastentity != null) {
                target = (EntityPlayer)((KillAura)ModuleManager.getModuleByName("KillAura")).lastentity;
            }
            else {
                target = getClosestEnemy();
            }
        }
        if (target == null || target.field_70128_L) {
            return;
        }
        if (this.targetHudBackground.getValue()) {
            RenderUtils.drawRect(this.targetHudX.getValue(), this.targetHudY.getValue(), (float)(this.targetHudX.getValue() + 210), (float)(this.targetHudY.getValue() + 100), new Color(20, 20, 20, 160).getRGB());
        }
        GlStateManager.func_179101_C();
        GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
        GlStateManager.func_179090_x();
        GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        try {
            GuiInventory.func_147046_a(this.targetHudX.getValue() + 30, this.targetHudY.getValue() + 90, 45, 0.0f, 0.0f, (EntityLivingBase)target);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        GlStateManager.func_179091_B();
        GlStateManager.func_179098_w();
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        this.cFontRenderer.drawStringWithShadow(target.func_70005_c_(), (float)(this.targetHudX.getValue() + 60), (float)(this.targetHudY.getValue() + 10), new Color(255, 0, 0, 255).getRGB());
        final float health = target.func_110143_aJ() + target.func_110139_bj();
        int healthColor;
        if (health >= 16.0f) {
            healthColor = new Color(0, 255, 0, 255).getRGB();
        }
        else if (health >= 10.0f) {
            healthColor = new Color(255, 255, 0, 255).getRGB();
        }
        else {
            healthColor = new Color(255, 0, 0, 255).getRGB();
        }
        final DecimalFormat df = new DecimalFormat("##.#");
        this.cFontRenderer.drawStringWithShadow(df.format(target.func_110143_aJ() + target.func_110139_bj()), (float)(this.targetHudX.getValue() + 60 + this.cFontRenderer.getStringWidth(target.func_70005_c_() + "  ")), (float)(this.targetHudY.getValue() + 10), healthColor);
        final Integer ping = (TargetHud.mc.func_147114_u().func_175102_a(target.func_110124_au()) == null) ? 0 : TargetHud.mc.func_147114_u().func_175102_a(target.func_110124_au()).func_178853_c();
        int color;
        if (ping < 75) {
            color = new Color(0, 255, 0, 255).getRGB();
        }
        else if (ping < 200) {
            color = new Color(255, 255, 0, 255).getRGB();
        }
        else {
            color = new Color(255, 0, 0, 255).getRGB();
        }
        final TotemPopCounter totemPopCounter = (TotemPopCounter)ModuleManager.getModuleByName("TotemPopCounter");
        final int totemPop = (totemPopCounter.popList.get(target.func_70005_c_()) == null) ? 0 : totemPopCounter.popList.get(target.func_70005_c_());
        this.cFontRenderer.drawStringWithShadow("Ping: " + ((ping == null) ? 0 : ping), (float)(this.targetHudX.getValue() + 60), (float)(this.targetHudY.getValue() + this.cFontRenderer.getHeight() + 20), color);
        this.cFontRenderer.drawStringWithShadow("Pops: " + totemPop, (float)(this.targetHudX.getValue() + 60), (float)(this.targetHudY.getValue() + this.cFontRenderer.getHeight() * 2 + 30), new Color(255, 0, 0, 255).getRGB());
        GlStateManager.func_179098_w();
        int iteration = 0;
        final int i = this.targetHudX.getValue() + 50;
        final int y = this.targetHudY.getValue() + this.cFontRenderer.getHeight() * 3 + 44;
        for (final ItemStack is : target.field_71071_by.field_70460_b) {
            ++iteration;
            if (is.func_190926_b()) {
                continue;
            }
            final int x = i - 90 + (9 - iteration) * 20 + 2;
            GlStateManager.func_179126_j();
            RenderUtils.itemRender.field_77023_b = 200.0f;
            RenderUtils.itemRender.func_180450_b(is, x, y);
            RenderUtils.itemRender.func_180453_a(TargetHud.mc.field_71466_p, is, x, y, "");
            RenderUtils.itemRender.field_77023_b = 0.0f;
            GlStateManager.func_179098_w();
            GlStateManager.func_179140_f();
            GlStateManager.func_179097_i();
            final String s = (is.func_190916_E() > 1) ? (is.func_190916_E() + "") : "";
            this.cFontRenderer.drawStringWithShadow(s, (float)(x + 19 - 2 - this.cFontRenderer.getStringWidth(s)), (float)(y + 9), 16777215);
            int dmg = 0;
            final int itemDurability = is.func_77958_k() - is.func_77952_i();
            final float green = (is.func_77958_k() - (float)is.func_77952_i()) / is.func_77958_k();
            final float red = 1.0f - green;
            dmg = 100 - (int)(red * 100.0f);
            this.cFontRenderer.drawStringWithShadow(dmg + "", x + 8 - this.cFontRenderer.getStringWidth(dmg + "") / 2.0f, (float)(y - 5), new Color((int)(red * 255.0f), (int)(green * 255.0f), 0).getRGB());
        }
        this.drawOverlay(partialTicks, (Entity)target, this.targetHudX.getValue() + 155, this.targetHudY.getValue() + 6);
        this.cFontRenderer.drawStringWithShadow("Strength", (float)(this.targetHudX.getValue() + 150), (float)(this.targetHudY.getValue() + 60), target.func_70644_a(MobEffects.field_76420_g) ? new Color(0, 255, 0, 255).getRGB() : new Color(255, 0, 0, 255).getRGB());
        this.cFontRenderer.drawStringWithShadow("Weakness", (float)(this.targetHudX.getValue() + 150), (float)(this.targetHudY.getValue() + this.cFontRenderer.getHeight() + 70), target.func_70644_a(MobEffects.field_76437_t) ? new Color(0, 255, 0, 255).getRGB() : new Color(255, 0, 0, 255).getRGB());
    }
    
    public void drawOverlay(final float partialTicks, final Entity player, final int x, final int y) {
        float yaw = 0.0f;
        final int dir = MathHelper.func_76128_c(player.field_70177_z * 4.0f / 360.0f + 0.5) & 0x3;
        switch (dir) {
            case 1: {
                yaw = 90.0f;
                break;
            }
            case 2: {
                yaw = -180.0f;
                break;
            }
            case 3: {
                yaw = -90.0f;
                break;
            }
        }
        final BlockPos northPos = this.traceToBlock(partialTicks, yaw, player);
        final Block north = this.getBlock(northPos);
        if (north != null && north != Blocks.field_150350_a) {
            final int damage = this.getBlockDamage(northPos);
            if (damage != 0) {
                RenderUtils.drawRect((float)(x + 16), (float)y, (float)(x + 32), (float)(y + 16), 1627324416);
            }
            this.drawBlock(north, (float)(x + 16), (float)y);
        }
        final BlockPos southPos = this.traceToBlock(partialTicks, yaw - 180.0f, player);
        final Block south = this.getBlock(southPos);
        if (south != null && south != Blocks.field_150350_a) {
            final int damage2 = this.getBlockDamage(southPos);
            if (damage2 != 0) {
                RenderUtils.drawRect((float)(x + 16), (float)(y + 32), (float)(x + 32), (float)(y + 48), 1627324416);
            }
            this.drawBlock(south, (float)(x + 16), (float)(y + 32));
        }
        final BlockPos eastPos = this.traceToBlock(partialTicks, yaw + 90.0f, player);
        final Block east = this.getBlock(eastPos);
        if (east != null && east != Blocks.field_150350_a) {
            final int damage3 = this.getBlockDamage(eastPos);
            if (damage3 != 0) {
                RenderUtils.drawRect((float)(x + 32), (float)(y + 16), (float)(x + 48), (float)(y + 32), 1627324416);
            }
            this.drawBlock(east, (float)(x + 32), (float)(y + 16));
        }
        final BlockPos westPos = this.traceToBlock(partialTicks, yaw - 90.0f, player);
        final Block west = this.getBlock(westPos);
        if (west != null && west != Blocks.field_150350_a) {
            final int damage4 = this.getBlockDamage(westPos);
            if (damage4 != 0) {
                RenderUtils.drawRect((float)x, (float)(y + 16), (float)(x + 16), (float)(y + 32), 1627324416);
            }
            this.drawBlock(west, (float)x, (float)(y + 16));
        }
    }
    
    private BlockPos traceToBlock(final float partialTicks, final float yaw) {
        final Vec3d pos = EntityUtil.getInterpolatedPos((Entity)TargetHud.mc.field_71439_g, partialTicks);
        final Vec3d dir = MathUtil.direction(yaw);
        return new BlockPos(pos.field_72450_a + dir.field_72450_a, pos.field_72448_b, pos.field_72449_c + dir.field_72449_c);
    }
    
    private BlockPos traceToBlock(final float partialTicks, final float yaw, final Entity player) {
        final Vec3d pos = EntityUtil.getInterpolatedPos(player, partialTicks);
        final Vec3d dir = MathUtil.direction(yaw);
        return new BlockPos(pos.field_72450_a + dir.field_72450_a, pos.field_72448_b, pos.field_72449_c + dir.field_72449_c);
    }
    
    private Block getBlock(final BlockPos pos) {
        final Block block = TargetHud.mc.field_71441_e.func_180495_p(pos).func_177230_c();
        if (block == Blocks.field_150357_h || block == Blocks.field_150343_Z) {
            return block;
        }
        return Blocks.field_150350_a;
    }
    
    private void drawBlock(final Block block, final float x, final float y) {
        final ItemStack stack = new ItemStack(block);
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        RenderHelper.func_74520_c();
        GlStateManager.func_179109_b(x, y, 0.0f);
        TargetHud.mc.func_175599_af().field_77023_b = 501.0f;
        TargetHud.mc.func_175599_af().func_180450_b(stack, 0, 0);
        TargetHud.mc.func_175599_af().field_77023_b = 0.0f;
        RenderHelper.func_74518_a();
        GlStateManager.func_179084_k();
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.func_179121_F();
    }
    
    private int getBlockDamage(final BlockPos pos) {
        for (final DestroyBlockProgress destBlockProgress : TargetHud.mc.field_71438_f.field_72738_E.values()) {
            if (destBlockProgress.func_180246_b().func_177958_n() == pos.func_177958_n() && destBlockProgress.func_180246_b().func_177956_o() == pos.func_177956_o() && destBlockProgress.func_180246_b().func_177952_p() == pos.func_177952_p()) {
                return destBlockProgress.func_73106_e();
            }
        }
        return 0;
    }
    
    public static EntityPlayer getClosestEnemy() {
        EntityPlayer closestPlayer = null;
        for (final EntityPlayer player : TargetHud.mc.field_71441_e.field_73010_i) {
            if (player == TargetHud.mc.field_71439_g) {
                continue;
            }
            if (Friends.isFriend(player.func_70005_c_())) {
                continue;
            }
            if (closestPlayer == null) {
                closestPlayer = player;
            }
            else {
                if (TargetHud.mc.field_71439_g.func_70068_e((Entity)player) >= TargetHud.mc.field_71439_g.func_70068_e((Entity)closestPlayer)) {
                    continue;
                }
                closestPlayer = player;
            }
        }
        return closestPlayer;
    }
}
