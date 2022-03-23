// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.EnumHand;
import meow.candycat.uwu.util.BlockInteractionHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import meow.candycat.uwu.util.WorldUtils;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "SelfWeb", category = Category.COMBAT)
public class SelfWeb extends Module
{
    private Setting<Boolean> doubleweb;
    
    public SelfWeb() {
        this.doubleweb = this.register(Settings.b("TwoWebs"));
    }
    
    public void onEnable() {
        if (HoleDetect.inhole) {
            final Vec3d holeOffset = SelfWeb.mc.field_71439_g.func_174791_d().func_72441_c(0.0, 0.0, 0.0);
            final BlockPos offset = new BlockPos(holeOffset.field_72450_a, holeOffset.field_72448_b, holeOffset.field_72449_c);
            if (SelfWeb.mc.field_71441_e.func_180495_p(offset).func_177230_c() != Blocks.field_150321_G) {
                final int oldSlot = SelfWeb.mc.field_71439_g.field_71071_by.field_70461_c;
                final int slot = WorldUtils.findBlock(Blocks.field_150321_G);
                if (slot == -1) {
                    this.disable();
                }
                SelfWeb.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot));
                BlockInteractionHelper.placeBlockScaffold(offset);
                SelfWeb.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                SelfWeb.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(oldSlot));
            }
            if (this.doubleweb.getValue()) {
                final Vec3d holeOffset2 = SelfWeb.mc.field_71439_g.func_174791_d().func_72441_c(0.0, 1.0, 0.0);
                final BlockPos offset2 = new BlockPos(holeOffset2.field_72450_a, holeOffset2.field_72448_b, holeOffset2.field_72449_c);
                if (SelfWeb.mc.field_71441_e.func_180495_p(offset2).func_177230_c() != Blocks.field_150321_G) {
                    final int oldSlot2 = SelfWeb.mc.field_71439_g.field_71071_by.field_70461_c;
                    final int slot2 = WorldUtils.findBlock(Blocks.field_150321_G);
                    if (slot2 == -1) {
                        this.disable();
                    }
                    SelfWeb.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot2));
                    BlockInteractionHelper.placeBlockScaffold(offset2);
                    SelfWeb.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                    SelfWeb.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(oldSlot2));
                }
            }
        }
        this.disable();
    }
}
