// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import net.minecraft.util.math.Vec3d;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.item.ItemStack;
import java.util.Iterator;
import meow.candycat.uwu.module.modules.misc.AutoTool2;
import meow.candycat.uwu.module.ModuleManager;
import meow.candycat.uwu.util.Friends;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import meow.candycat.uwu.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumHand;
import net.minecraft.init.Items;
import meow.candycat.uwu.util.LagCompensator;
import meow.candycat.uwu.command.Command;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "Aura2", category = Category.COMBAT, description = "Hits entities around you")
public class Aura2 extends Module
{
    private Setting<Boolean> attackPlayers;
    private Setting<Boolean> attackMobs;
    private Setting<Boolean> attackAnimals;
    private Setting<Double> hitRange;
    private Setting<Boolean> ignoreWalls;
    private Setting<WaitMode> waitMode;
    private Setting<Double> waitTick;
    private Setting<Boolean> autoWait;
    private Setting<SwitchMode> switchMode;
    private Setting<HitMode> hitMode;
    private Setting<Boolean> infoMsg;
    private int waitCounter;
    
    public Aura2() {
        this.attackPlayers = this.register(Settings.b("Players", true));
        this.attackMobs = this.register(Settings.b("Mobs", false));
        this.attackAnimals = this.register(Settings.b("Animals", false));
        this.hitRange = this.register(Settings.d("Hit Range", 5.5));
        this.ignoreWalls = this.register(Settings.b("Ignores Walls", true));
        this.waitMode = this.register(Settings.e("Mode", WaitMode.TICK));
        this.waitTick = this.register((Setting<Double>)Settings.doubleBuilder("Tick Delay").withMinimum(0.0).withValue(2.0).withMaximum(20.0).build());
        this.autoWait = this.register(Settings.b("Auto Tick Delay", true));
        this.switchMode = this.register(Settings.e("Autoswitch", SwitchMode.ALL));
        this.hitMode = this.register(Settings.e("Tool", HitMode.SWORD));
        this.infoMsg = this.register(Settings.b("Info Message", true));
    }
    
    public void onEnable() {
        if (Aura2.mc.field_71439_g == null) {
            return;
        }
        if (this.autoWait.getValue() && this.infoMsg.getValue()) {
            Command.sendWarningMessage("[Aura2] When Auto Tick Delay is turned on whatever you give Tick Delay doesn't matter, it uses the current TPS instead");
        }
    }
    
    @Override
    public void onUpdate() {
        double autoWaitTick = 0.0;
        if (Aura2.mc.field_71439_g.field_70128_L || Aura2.mc.field_71439_g == null) {
            return;
        }
        if (this.autoWait.getValue()) {
            autoWaitTick = 20.0 - Math.round(LagCompensator.INSTANCE.getTickRate() * 10.0f) / 10.0;
        }
        final boolean shield = Aura2.mc.field_71439_g.func_184592_cb().func_77973_b().equals(Items.field_185159_cQ) && Aura2.mc.field_71439_g.func_184600_cs() == EnumHand.OFF_HAND;
        if (Aura2.mc.field_71439_g.func_184587_cr() && !shield) {
            return;
        }
        if (this.waitMode.getValue().equals(WaitMode.CPS)) {
            if (Aura2.mc.field_71439_g.func_184825_o(this.getLagComp()) < 1.0f) {
                return;
            }
            if (Aura2.mc.field_71439_g.field_70173_aa % 2 != 0) {
                return;
            }
        }
        if (this.autoWait.getValue()) {
            if (this.waitMode.getValue().equals(WaitMode.TICK) && autoWaitTick > 0.0) {
                if (this.waitCounter < autoWaitTick) {
                    ++this.waitCounter;
                    return;
                }
                this.waitCounter = 0;
            }
        }
        else if (this.waitMode.getValue().equals(WaitMode.TICK) && this.waitTick.getValue() > 0.0) {
            if (this.waitCounter < this.waitTick.getValue()) {
                ++this.waitCounter;
                return;
            }
            this.waitCounter = 0;
        }
        for (final Entity target : Minecraft.func_71410_x().field_71441_e.field_72996_f) {
            if (!EntityUtil.isLiving(target)) {
                continue;
            }
            if (target == Aura2.mc.field_71439_g) {
                continue;
            }
            if (Aura2.mc.field_71439_g.func_70032_d(target) > this.hitRange.getValue()) {
                continue;
            }
            if (((EntityLivingBase)target).func_110143_aJ() <= 0.0f) {
                continue;
            }
            if (this.waitMode.getValue().equals(WaitMode.CPS) && ((EntityLivingBase)target).field_70737_aN != 0) {
                continue;
            }
            if (!this.ignoreWalls.getValue() && !Aura2.mc.field_71439_g.func_70685_l(target) && !this.canEntityFeetBeSeen(target)) {
                continue;
            }
            if (this.attackPlayers.getValue() && target instanceof EntityPlayer && !Friends.isFriend(target.func_70005_c_())) {
                this.attack(target);
                return;
            }
            Label_0598: {
                if (EntityUtil.isPassive(target)) {
                    if (this.attackAnimals.getValue()) {
                        break Label_0598;
                    }
                    continue;
                }
                else {
                    if (EntityUtil.isMobAggressive(target) && this.attackMobs.getValue()) {
                        break Label_0598;
                    }
                    continue;
                }
                continue;
            }
            if ((!this.switchMode.getValue().equals(SwitchMode.Only32k) || this.switchMode.getValue().equals(SwitchMode.ALL)) && ModuleManager.isModuleEnabled("AutoTool2")) {
                AutoTool2.equipBestWeapon();
            }
            this.attack(target);
        }
    }
    
    private boolean checkSharpness(final ItemStack stack) {
        if (stack.func_77978_p() == null) {
            return false;
        }
        if (stack.func_77973_b().equals(Items.field_151056_x) && this.hitMode.getValue().equals(HitMode.SWORD)) {
            return false;
        }
        if (stack.func_77973_b().equals(Items.field_151048_u) && this.hitMode.getValue().equals(HitMode.AXE)) {
            return false;
        }
        final NBTTagList enchants = (NBTTagList)stack.func_77978_p().func_74781_a("ench");
        if (enchants == null) {
            return false;
        }
        int i = 0;
        while (i < enchants.func_74745_c()) {
            final NBTTagCompound enchant = enchants.func_150305_b(i);
            if (enchant.func_74762_e("id") == 16) {
                final int lvl = enchant.func_74762_e("lvl");
                if (this.switchMode.getValue().equals(SwitchMode.Only32k)) {
                    if (lvl >= 42) {
                        return true;
                    }
                    break;
                }
                else if (this.switchMode.getValue().equals(SwitchMode.ALL)) {
                    if (lvl >= 4) {
                        return true;
                    }
                    break;
                }
                else {
                    if (this.switchMode.getValue().equals(SwitchMode.NONE)) {
                        return true;
                    }
                    break;
                }
            }
            else {
                ++i;
            }
        }
        return false;
    }
    
    private void attack(final Entity e) {
        boolean holding32k = false;
        if (this.checkSharpness(Aura2.mc.field_71439_g.func_184614_ca())) {
            holding32k = true;
        }
        if ((this.switchMode.getValue().equals(SwitchMode.Only32k) || this.switchMode.getValue().equals(SwitchMode.ALL)) && !holding32k) {
            int newSlot = -1;
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = Aura2.mc.field_71439_g.field_71071_by.func_70301_a(i);
                if (stack != ItemStack.field_190927_a) {
                    if (this.checkSharpness(stack)) {
                        newSlot = i;
                        break;
                    }
                }
            }
            if (newSlot != -1) {
                Aura2.mc.field_71439_g.field_71071_by.field_70461_c = newSlot;
                holding32k = true;
            }
        }
        if (this.switchMode.getValue().equals(SwitchMode.Only32k) && !holding32k) {
            return;
        }
        Aura2.mc.field_71442_b.func_78764_a((EntityPlayer)Aura2.mc.field_71439_g, e);
        Aura2.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
    }
    
    private float getLagComp() {
        if (this.waitMode.getValue().equals(WaitMode.CPS)) {
            return -(20.0f - LagCompensator.INSTANCE.getTickRate());
        }
        return 0.0f;
    }
    
    private boolean canEntityFeetBeSeen(final Entity entityIn) {
        return Aura2.mc.field_71441_e.func_147447_a(new Vec3d(Aura2.mc.field_71439_g.field_70165_t, Aura2.mc.field_71439_g.field_70163_u + Aura2.mc.field_71439_g.func_70047_e(), Aura2.mc.field_71439_g.field_70161_v), new Vec3d(entityIn.field_70165_t, entityIn.field_70163_u, entityIn.field_70161_v), false, true, false) == null;
    }
    
    private enum SwitchMode
    {
        NONE, 
        ALL, 
        Only32k;
    }
    
    private enum HitMode
    {
        SWORD, 
        AXE;
    }
    
    private enum WaitMode
    {
        CPS, 
        TICK;
    }
}
