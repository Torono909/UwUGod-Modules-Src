// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import java.util.Iterator;
import net.minecraft.potion.PotionType;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import java.util.Objects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.item.ItemTippedArrow;
import net.minecraft.init.Items;
import meow.candycat.uwu.util.PotionArrow;
import java.util.ArrayList;
import meow.candycat.uwu.module.Module;

@Info(name = "ArrowSwitcher", category = Category.MISC)
public class ArrowSwitcher extends Module
{
    @Override
    public void onUpdate() {
        final List<PotionArrow> arrowList = new ArrayList<PotionArrow>();
        if (ArrowSwitcher.mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_151031_f) {
            return;
        }
        for (int i = 9; i < 45; ++i) {
            if (ArrowSwitcher.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() instanceof ItemTippedArrow) {
                final PotionType potionEffect = PotionUtils.func_185191_c(ArrowSwitcher.mc.field_71439_g.field_71071_by.func_70301_a(i));
                arrowList.add(new PotionArrow(i, potionEffect));
            }
        }
        if (arrowList.size() == 0) {
            return;
        }
        PotionArrow shouldShoot = null;
        for (final PotionArrow arrow : arrowList) {
            if (shouldShoot == null) {
                shouldShoot = arrow;
            }
            else if (!ArrowSwitcher.mc.field_71439_g.func_70644_a(Objects.requireNonNull(arrow.potionEffects.func_185170_a().stream().findFirst().orElse(null)).func_188419_a()) && ArrowSwitcher.mc.field_71439_g.func_70644_a(Objects.requireNonNull(shouldShoot.potionEffects.func_185170_a().stream().findFirst().orElse(null)).func_188419_a())) {
                shouldShoot = arrow;
            }
            else {
                if (!ArrowSwitcher.mc.field_71439_g.func_70644_a(Objects.requireNonNull(arrow.potionEffects.func_185170_a().stream().findFirst().orElse(null)).func_188419_a()) || !ArrowSwitcher.mc.field_71439_g.func_70644_a(Objects.requireNonNull(shouldShoot.potionEffects.func_185170_a().stream().findFirst().orElse(null)).func_188419_a()) || ArrowSwitcher.mc.field_71439_g.func_70660_b(Objects.requireNonNull(arrow.potionEffects.func_185170_a().stream().findFirst().orElse(null)).func_188419_a()).field_76460_b >= ArrowSwitcher.mc.field_71439_g.func_70660_b(Objects.requireNonNull(shouldShoot.potionEffects.func_185170_a().stream().findFirst().orElse(null)).func_188419_a()).field_76460_b) {
                    continue;
                }
                shouldShoot = arrow;
            }
        }
        final int lastSlot = arrowList.get(0).itemSlot;
        if (lastSlot != shouldShoot.itemSlot) {
            ArrowSwitcher.mc.field_71442_b.func_187098_a(0, (shouldShoot.itemSlot < 9) ? (shouldShoot.itemSlot + 36) : shouldShoot.itemSlot, 0, ClickType.PICKUP, (EntityPlayer)ArrowSwitcher.mc.field_71439_g);
            ArrowSwitcher.mc.field_71442_b.func_187098_a(0, (lastSlot < 9) ? (lastSlot + 36) : lastSlot, 0, ClickType.PICKUP, (EntityPlayer)ArrowSwitcher.mc.field_71439_g);
            ArrowSwitcher.mc.field_71442_b.func_187098_a(0, (shouldShoot.itemSlot < 9) ? (shouldShoot.itemSlot + 36) : shouldShoot.itemSlot, 0, ClickType.PICKUP, (EntityPlayer)ArrowSwitcher.mc.field_71439_g);
        }
    }
}
