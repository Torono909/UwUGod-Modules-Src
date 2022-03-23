// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.player;

import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.item.ItemFood;
import meow.candycat.uwu.module.Module;

@Info(name = "PacketEat", category = Category.PLAYER)
public class PacketEat extends Module
{
    @Override
    public void onUpdate() {
        if (PacketEat.mc.field_71439_g.func_184586_b(EnumHand.MAIN_HAND).func_77973_b() instanceof ItemFood || PacketEat.mc.field_71439_g.func_184586_b(EnumHand.OFF_HAND).func_77973_b() instanceof ItemFood) {
            if (!PacketEat.mc.field_71439_g.func_184587_cr()) {
                return;
            }
            PacketEat.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, PacketEat.mc.field_71439_g.func_174811_aO()));
            PacketEat.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(PacketEat.mc.field_71439_g.func_184600_cs()));
            PacketEat.mc.field_71439_g.func_184597_cx();
        }
    }
}
