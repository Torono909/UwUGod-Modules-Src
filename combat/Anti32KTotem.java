// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.init.Items;
import net.minecraft.client.gui.inventory.GuiContainer;
import meow.candycat.uwu.module.Module;

@Info(name = "Anti32K-Totem", category = Category.COMBAT)
public class Anti32KTotem extends Module
{
    @Override
    public void onUpdate() {
        if (Anti32KTotem.mc.field_71462_r instanceof GuiContainer) {
            return;
        }
        if (Anti32KTotem.mc.field_71439_g.field_71071_by.func_70301_a(0).func_77973_b() == Items.field_190929_cY) {
            return;
        }
        for (int i = 9; i < 35; ++i) {
            if (Anti32KTotem.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() == Items.field_190929_cY) {
                Anti32KTotem.mc.field_71442_b.func_187098_a(Anti32KTotem.mc.field_71439_g.field_71069_bz.field_75152_c, i, 0, ClickType.SWAP, (EntityPlayer)Anti32KTotem.mc.field_71439_g);
                break;
            }
        }
    }
}
