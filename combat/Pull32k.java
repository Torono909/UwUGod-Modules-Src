// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemAir;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import meow.candycat.uwu.module.Module;

@Info(name = "Pull32k", category = Category.COMBAT)
public class Pull32k extends Module
{
    boolean foundsword;
    
    public Pull32k() {
        this.foundsword = false;
    }
    
    @Override
    public void onUpdate() {
        boolean foundair = false;
        int enchantedSwordIndex = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = (ItemStack)Pull32k.mc.field_71439_g.field_71071_by.field_70462_a.get(i);
            if (EnchantmentHelper.func_77506_a(Enchantments.field_185302_k, itemStack) >= 32767) {
                enchantedSwordIndex = i;
                this.foundsword = true;
            }
            if (!this.foundsword) {
                enchantedSwordIndex = -1;
                this.foundsword = false;
            }
        }
        if (enchantedSwordIndex != -1 && Pull32k.mc.field_71439_g.field_71071_by.field_70461_c != enchantedSwordIndex) {
            Pull32k.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(enchantedSwordIndex));
            Pull32k.mc.field_71439_g.field_71071_by.field_70461_c = enchantedSwordIndex;
            Pull32k.mc.field_71442_b.func_78765_e();
        }
        if (enchantedSwordIndex == -1 && Pull32k.mc.field_71439_g.field_71070_bA != null && Pull32k.mc.field_71439_g.field_71070_bA instanceof ContainerHopper && Pull32k.mc.field_71439_g.field_71070_bA.field_75151_b != null && !Pull32k.mc.field_71439_g.field_71070_bA.field_75151_b.isEmpty()) {
            for (int i = 0; i < 5; ++i) {
                if (EnchantmentHelper.func_77506_a(Enchantments.field_185302_k, Pull32k.mc.field_71439_g.field_71070_bA.field_75151_b.get(0).field_75224_c.func_70301_a(i)) >= 32767) {
                    enchantedSwordIndex = i;
                    break;
                }
            }
            if (enchantedSwordIndex == -1) {
                return;
            }
            if (enchantedSwordIndex != -1) {
                for (int i = 0; i < 9; ++i) {
                    final ItemStack itemStack = (ItemStack)Pull32k.mc.field_71439_g.field_71071_by.field_70462_a.get(i);
                    if (itemStack.func_77973_b() instanceof ItemAir) {
                        if (Pull32k.mc.field_71439_g.field_71071_by.field_70461_c != i) {
                            Pull32k.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(i));
                            Pull32k.mc.field_71439_g.field_71071_by.field_70461_c = i;
                            Pull32k.mc.field_71442_b.func_78765_e();
                        }
                        foundair = true;
                        break;
                    }
                }
            }
            if (foundair || this.checkStuff()) {
                Pull32k.mc.field_71442_b.func_187098_a(Pull32k.mc.field_71439_g.field_71070_bA.field_75152_c, enchantedSwordIndex, Pull32k.mc.field_71439_g.field_71071_by.field_70461_c, ClickType.SWAP, (EntityPlayer)Pull32k.mc.field_71439_g);
            }
        }
    }
    
    public boolean checkStuff() {
        return EnchantmentHelper.func_77506_a(Enchantments.field_185302_k, Pull32k.mc.field_71439_g.field_71071_by.func_70448_g()) == 5;
    }
}
