// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.movement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import net.minecraft.util.NonNullList;
import java.util.List;
import java.util.Comparator;
import net.minecraft.util.math.Vec3d;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import meow.candycat.uwu.module.modules.combat.HoleDetect;
import meow.candycat.uwu.module.ModuleManager;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "HoleTP", category = Category.MOVEMENT)
public class HoleTP extends Module
{
    private double ydrop;
    private double yrise;
    private boolean shouldReset;
    private Setting<mode> modeSetting;
    private Setting<Double> range;
    
    public HoleTP() {
        this.shouldReset = false;
        this.modeSetting = this.register(Settings.e("Mode", mode.PULL));
        this.range = this.register(Settings.doubleBuilder("Range").withValue(6.0).withVisibility(v -> this.modeSetting.getValue() == mode.TP).build());
    }
    
    @Override
    public String getHudInfo() {
        return (this.modeSetting.getValue() == mode.PULL) ? "Pull" : "Teleport";
    }
    
    @Override
    public void onUpdate() {
        if (ModuleManager.getModuleByName("PacketFly").isEnabled() || (ModuleManager.getModuleByName("Speed").isEnabled() && ((Speed)ModuleManager.getModuleByName("Speed")).mode.getValue() == Speed.Mode.MINIHOP)) {
            return;
        }
        if (this.shouldReset && HoleTP.mc.field_71439_g.field_70122_E) {
            HoleTP.mc.field_71439_g.field_70138_W = 0.5f;
            this.shouldReset = false;
        }
        if (this.modeSetting.getValue() == mode.PULL) {
            if (HoleTP.mc.field_71439_g.field_70137_T > HoleTP.mc.field_71439_g.field_70163_u) {
                this.ydrop += HoleTP.mc.field_71439_g.field_70137_T - HoleTP.mc.field_71439_g.field_70163_u;
                this.yrise = 0.0;
            }
            else {
                this.ydrop = 0.0;
                this.yrise += HoleTP.mc.field_71439_g.field_70163_u - HoleTP.mc.field_71439_g.field_70137_T;
            }
            if (HoleDetect.inhole && !HoleDetect.lastinhole && this.ydrop < 1.0 && this.yrise == 0.0) {
                final Vec3d vecOffset = HoleTP.mc.field_71439_g.func_174791_d();
                final BlockPos offset = new BlockPos(vecOffset.field_72450_a, vecOffset.field_72448_b, vecOffset.field_72449_c);
                if (HoleTP.mc.field_71441_e.func_180495_p(offset).func_177230_c() == Blocks.field_150477_bB) {
                    if (HoleTP.mc.field_71439_g.field_70122_E) {
                        return;
                    }
                    HoleTP.mc.field_71439_g.func_70107_b(HoleTP.mc.field_71439_g.field_70165_t, Math.floor(HoleTP.mc.field_71439_g.field_70163_u) + 0.875, HoleTP.mc.field_71439_g.field_70161_v);
                }
                else {
                    if (HoleTP.mc.field_71441_e.func_180495_p(offset.func_177982_a(0, -1, 0)).func_177230_c() == Blocks.field_150477_bB) {
                        HoleTP.mc.field_71439_g.func_70107_b(HoleTP.mc.field_71439_g.field_70165_t, Math.floor(HoleTP.mc.field_71439_g.field_70163_u) - 0.125, HoleTP.mc.field_71439_g.field_70161_v);
                        return;
                    }
                    HoleTP.mc.field_71439_g.field_70138_W = 0.0f;
                    this.shouldReset = true;
                    HoleTP.mc.field_71439_g.field_70181_x = -10.0;
                }
            }
        }
    }
    
    public void onEnable() {
        if (this.modeSetting.getValue() != mode.TP) {
            return;
        }
        if (HoleTP.mc.field_71441_e == null || HoleTP.mc.field_71439_g == null) {
            this.disable();
            return;
        }
        if (!HoleDetect.inhole) {
            final BlockPos block = this.findCrystalBlocks().stream().filter(e -> this.IsHole(e)).sorted(Comparator.comparing(e -> HoleTP.mc.field_71439_g.func_70092_e((double)e.field_177962_a, (double)e.field_177960_b, (double)e.field_177961_c))).findFirst().orElse(null);
            if (block != null) {
                HoleTP.mc.field_71439_g.func_70107_b(block.field_177962_a + 0.5, (double)block.field_177960_b, block.field_177961_c + 0.5);
            }
            this.disable();
            return;
        }
        final Vec3d vecOffset = HoleTP.mc.field_71439_g.func_174791_d().func_72441_c(0.0, 0.0, 0.0);
        final BlockPos offset = new BlockPos(vecOffset.field_72450_a, vecOffset.field_72448_b, vecOffset.field_72449_c);
        if (HoleTP.mc.field_71441_e.func_180495_p(offset).func_177230_c() == Blocks.field_150477_bB) {
            this.disable();
            return;
        }
        if (HoleTP.mc.field_71439_g.field_70163_u != Math.floor(HoleTP.mc.field_71439_g.field_70163_u)) {
            HoleTP.mc.field_71439_g.func_70107_b(HoleTP.mc.field_71439_g.field_70165_t, Math.floor(HoleTP.mc.field_71439_g.field_70163_u), HoleTP.mc.field_71439_g.field_70161_v);
        }
        this.disable();
    }
    
    private boolean IsHole(final BlockPos blockPos) {
        final BlockPos boost = blockPos.func_177982_a(0, 1, 0);
        final BlockPos boost2 = blockPos.func_177982_a(0, 0, 0);
        final BlockPos boost3 = blockPos.func_177982_a(0, 0, -1);
        final BlockPos boost4 = blockPos.func_177982_a(1, 0, 0);
        final BlockPos boost5 = blockPos.func_177982_a(-1, 0, 0);
        final BlockPos boost6 = blockPos.func_177982_a(0, 0, 1);
        final BlockPos boost7 = blockPos.func_177982_a(0, 2, 0);
        final BlockPos boost8 = blockPos.func_177963_a(0.5, 0.5, 0.5);
        final BlockPos boost9 = blockPos.func_177982_a(0, -1, 0);
        return HoleTP.mc.field_71441_e.func_180495_p(boost).func_177230_c() == Blocks.field_150350_a && HoleTP.mc.field_71441_e.func_180495_p(boost2).func_177230_c() == Blocks.field_150350_a && HoleTP.mc.field_71441_e.func_180495_p(boost7).func_177230_c() == Blocks.field_150350_a && (HoleTP.mc.field_71441_e.func_180495_p(boost3).func_177230_c() == Blocks.field_150343_Z || HoleTP.mc.field_71441_e.func_180495_p(boost3).func_177230_c() == Blocks.field_150357_h || HoleTP.mc.field_71441_e.func_180495_p(boost3).func_177230_c() == Blocks.field_150477_bB) && (HoleTP.mc.field_71441_e.func_180495_p(boost4).func_177230_c() == Blocks.field_150343_Z || HoleTP.mc.field_71441_e.func_180495_p(boost4).func_177230_c() == Blocks.field_150357_h || HoleTP.mc.field_71441_e.func_180495_p(boost4).func_177230_c() == Blocks.field_150477_bB) && (HoleTP.mc.field_71441_e.func_180495_p(boost5).func_177230_c() == Blocks.field_150343_Z || HoleTP.mc.field_71441_e.func_180495_p(boost5).func_177230_c() == Blocks.field_150357_h || HoleTP.mc.field_71441_e.func_180495_p(boost5).func_177230_c() == Blocks.field_150477_bB) && (HoleTP.mc.field_71441_e.func_180495_p(boost6).func_177230_c() == Blocks.field_150343_Z || HoleTP.mc.field_71441_e.func_180495_p(boost6).func_177230_c() == Blocks.field_150357_h || HoleTP.mc.field_71441_e.func_180495_p(boost6).func_177230_c() == Blocks.field_150477_bB) && HoleTP.mc.field_71441_e.func_180495_p(boost8).func_177230_c() == Blocks.field_150350_a && (HoleTP.mc.field_71441_e.func_180495_p(boost9).func_177230_c() == Blocks.field_150343_Z || HoleTP.mc.field_71441_e.func_180495_p(boost9).func_177230_c() == Blocks.field_150357_h || HoleTP.mc.field_71441_e.func_180495_p(boost9).func_177230_c() == Blocks.field_150477_bB);
    }
    
    private List<BlockPos> findCrystalBlocks() {
        final NonNullList<BlockPos> positions = (NonNullList<BlockPos>)NonNullList.func_191196_a();
        positions.addAll((Collection)this.getSphere(HoleTP.mc.field_71439_g.func_180425_c(), this.range.getValue().floatValue(), this.range.getValue().intValue(), false, true, 0).stream().filter((Predicate<? super Object>)this::IsHole).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        return (List<BlockPos>)positions;
    }
    
    public List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final List<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.func_177958_n();
        final int cy = loc.func_177956_o();
        final int cz = loc.func_177952_p();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }
    
    private enum mode
    {
        PULL, 
        TP;
    }
}
