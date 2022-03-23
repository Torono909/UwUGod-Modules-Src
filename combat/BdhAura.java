// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import net.minecraft.item.ItemSword;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.item.ItemStack;
import java.util.Iterator;
import net.minecraft.util.EnumHand;
import meow.candycat.uwu.util.Friends;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "32K'Kill", category = Category.COMBAT)
public class BdhAura extends Module
{
    private int hasWaited;
    private Setting<Float> range;
    private Setting<Boolean> only32k;
    private Setting<Boolean> playersOnly;
    private Setting<Boolean> switch32k;
    private Setting<Double> delay;
    
    public BdhAura() {
        this.range = this.register(Settings.f("Range", 5.0f));
        this.only32k = this.register(Settings.b("32k Only", false));
        this.playersOnly = this.register(Settings.b("Players only", false));
        this.switch32k = this.register(Settings.b("32k Switch", true));
        this.delay = this.register(Settings.d("Delay in ticks", 50.0));
    }
    
    @Override
    public void onUpdate() {
        if (!this.isEnabled() || BdhAura.mc.field_71439_g.field_70128_L || BdhAura.mc.field_71441_e == null) {
            return;
        }
        if (this.hasWaited < this.delay.getValue()) {
            ++this.hasWaited;
            return;
        }
        this.hasWaited = 0;
        for (final Entity entity : BdhAura.mc.field_71441_e.field_72996_f) {
            if (entity instanceof EntityLivingBase) {
                if (entity == BdhAura.mc.field_71439_g) {
                    continue;
                }
                if (BdhAura.mc.field_71439_g.func_70032_d(entity) > this.range.getValue() || ((EntityLivingBase)entity).func_110143_aJ() <= 0.0f || (!(entity instanceof EntityPlayer) && this.playersOnly.getValue())) {
                    continue;
                }
                if (!this.isSuperWeapon(BdhAura.mc.field_71439_g.func_184614_ca()) && this.only32k.getValue()) {
                    continue;
                }
                if (this.switch32k.getValue()) {
                    equipBestWeapon();
                }
                if (Friends.isFriend(entity.func_70005_c_())) {
                    continue;
                }
                BdhAura.mc.field_71442_b.func_78764_a((EntityPlayer)BdhAura.mc.field_71439_g, entity);
                BdhAura.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
            }
        }
    }
    
    private boolean isSuperWeapon(final ItemStack item) {
        if (item == null) {
            return false;
        }
        if (item.func_77978_p() == null) {
            return false;
        }
        if (item.func_77986_q().func_150303_d() == 0) {
            return false;
        }
        final NBTTagList enchants = (NBTTagList)item.func_77978_p().func_74781_a("ench");
        int i = 0;
        while (i < enchants.func_74745_c()) {
            final NBTTagCompound enchant = enchants.func_150305_b(i);
            if (enchant.func_74762_e("id") == 16) {
                final int lvl = enchant.func_74762_e("lvl");
                if (lvl >= 16) {
                    return true;
                }
                break;
            }
            else {
                ++i;
            }
        }
        return false;
    }
    
    public static void equipBestWeapon() {
        int bestSlot = -1;
        double maxDamage = 0.0;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = BdhAura.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (!stack.field_190928_g) {
                if (stack.func_77973_b() instanceof ItemTool) {
                    final double damage = ((ItemTool)stack.func_77973_b()).field_77865_bY + EnchantmentHelper.func_152377_a(stack, EnumCreatureAttribute.UNDEFINED);
                    if (damage > maxDamage) {
                        maxDamage = damage;
                        bestSlot = i;
                    }
                }
                else if (stack.func_77973_b() instanceof ItemSword) {
                    final double damage = ((ItemSword)stack.func_77973_b()).func_150931_i() + EnchantmentHelper.func_152377_a(stack, EnumCreatureAttribute.UNDEFINED);
                    if (damage > maxDamage) {
                        maxDamage = damage;
                        bestSlot = i;
                    }
                }
            }
        }
        if (bestSlot != -1) {
            equip(bestSlot);
        }
    }
    
    private static void equip(final int slot) {
        BdhAura.mc.field_71439_g.field_71071_by.field_70461_c = slot;
        BdhAura.mc.field_71442_b.func_78750_j();
    }
}
