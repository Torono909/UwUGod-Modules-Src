// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.render;

import net.minecraft.util.math.Vec3d;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import meow.candycat.uwu.util.MathUtil;
import java.awt.Color;
import meow.candycat.uwu.util.UwUGodTessellator;
import meow.candycat.uwu.event.events.RenderEvent;
import net.minecraft.block.Block;
import java.util.Iterator;
import net.minecraft.util.math.Vec3i;
import net.minecraft.init.Blocks;
import java.util.ArrayList;
import java.util.List;
import meow.candycat.uwu.setting.Settings;
import java.util.concurrent.ConcurrentHashMap;
import meow.candycat.uwu.setting.Setting;
import net.minecraft.util.math.BlockPos;
import meow.candycat.uwu.module.Module;

@Info(name = "HoleESP", category = Category.RENDER, description = "Show safe holes")
public class HoleESP extends Module
{
    private final BlockPos[] surroundOffset;
    private Setting<HoleType> holeType;
    private Setting<Boolean> hideOwn;
    private Setting<Double> renderDistance;
    private Setting<RenderMode> renderMode;
    private Setting<Integer> obiRed;
    private Setting<Integer> obiGreen;
    private Setting<Integer> obiBlue;
    private Setting<Integer> brockRed;
    private Setting<Integer> brockGreen;
    private Setting<Integer> brockBlue;
    private Setting<Integer> alpha;
    private ConcurrentHashMap<BlockPos, Boolean> safeHoles;
    
    public HoleESP() {
        this.surroundOffset = new BlockPos[] { new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0) };
        this.holeType = this.register(Settings.e("HoleType", HoleType.BOTH));
        this.hideOwn = this.register(Settings.b("HideOwn", false));
        this.renderDistance = this.register((Setting<Double>)Settings.doubleBuilder("RenderDistance").withMinimum(1.0).withValue(8.0).withMaximum(32.0).build());
        this.renderMode = this.register(Settings.e("RenderMode", RenderMode.DOWN));
        this.obiRed = this.register((Setting<Integer>)Settings.integerBuilder("ObiRed").withMinimum(0).withValue(104).withMaximum(255).build());
        this.obiGreen = this.register((Setting<Integer>)Settings.integerBuilder("ObiGreen").withMinimum(0).withValue(12).withMaximum(255).build());
        this.obiBlue = this.register((Setting<Integer>)Settings.integerBuilder("ObiBlue").withMinimum(0).withValue(35).withMaximum(255).build());
        this.brockRed = this.register((Setting<Integer>)Settings.integerBuilder("BrockRed").withMinimum(0).withValue(81).withMaximum(255).build());
        this.brockGreen = this.register((Setting<Integer>)Settings.integerBuilder("BrockGreen").withMinimum(0).withValue(12).withMaximum(255).build());
        this.brockBlue = this.register((Setting<Integer>)Settings.integerBuilder("BrockBlue").withMinimum(0).withValue(104).withMaximum(255).build());
        this.alpha = this.register((Setting<Integer>)Settings.integerBuilder("Alpha").withMinimum(0).withValue(169).withMaximum(255).build());
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
    
    @Override
    public void onUpdate() {
        if (this.safeHoles == null) {
            this.safeHoles = new ConcurrentHashMap<BlockPos, Boolean>();
        }
        else {
            this.safeHoles.clear();
        }
        final int range = (int)Math.ceil(this.renderDistance.getValue());
        final List<BlockPos> blockPosList = this.getSphere(new BlockPos(HoleESP.mc.field_71439_g.field_70165_t, HoleESP.mc.field_71439_g.field_70163_u, HoleESP.mc.field_71439_g.field_70161_v), (float)range, range, false, true, 0);
        for (final BlockPos pos : blockPosList) {
            if (!HoleESP.mc.field_71441_e.func_180495_p(pos).func_177230_c().equals(Blocks.field_150350_a)) {
                continue;
            }
            if (!HoleESP.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a)) {
                continue;
            }
            if (!HoleESP.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a)) {
                continue;
            }
            if (this.hideOwn.getValue() && pos.equals((Object)new BlockPos(HoleESP.mc.field_71439_g.field_70165_t, HoleESP.mc.field_71439_g.field_70163_u, HoleESP.mc.field_71439_g.field_70161_v))) {
                continue;
            }
            boolean isSafe = true;
            boolean isBedrock = true;
            boolean canGetIn = false;
            for (final BlockPos offset : new BlockPos[] { new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0) }) {
                final Block block = HoleESP.mc.field_71441_e.func_180495_p(pos.func_177971_a((Vec3i)offset).func_177982_a(0, 1, 0)).func_177230_c();
                if (block == Blocks.field_150350_a) {
                    canGetIn = true;
                    break;
                }
            }
            if (!canGetIn && !HoleESP.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 3, 0)).func_177230_c().equals(Blocks.field_150350_a)) {
                continue;
            }
            for (final BlockPos offset : this.surroundOffset) {
                final Block block = HoleESP.mc.field_71441_e.func_180495_p(pos.func_177971_a((Vec3i)offset)).func_177230_c();
                if (block != Blocks.field_150357_h) {
                    isBedrock = false;
                }
                if (block != Blocks.field_150357_h && block != Blocks.field_150343_Z && block != Blocks.field_150477_bB && block != Blocks.field_150467_bQ) {
                    isSafe = false;
                    break;
                }
            }
            if (!isSafe) {
                continue;
            }
            this.safeHoles.put(pos, isBedrock);
        }
    }
    
    @Override
    public void onWorldRender(final RenderEvent event) {
        if (HoleESP.mc.field_71439_g == null || this.safeHoles == null) {
            return;
        }
        if (this.safeHoles.isEmpty()) {
            return;
        }
        UwUGodTessellator.prepare(7);
        this.safeHoles.forEach((blockPos, isBedrock) -> {
            if (isBedrock) {
                if (this.holeType.getValue().equals(HoleType.BOTH) || this.holeType.getValue().equals(HoleType.BROCK)) {
                    this.drawBlock(blockPos, this.brockRed.getValue(), this.brockGreen.getValue(), this.brockBlue.getValue());
                }
            }
            else if (this.holeType.getValue().equals(HoleType.BOTH) || this.holeType.getValue().equals(HoleType.OBI)) {
                this.drawBlock(blockPos, this.obiRed.getValue(), this.obiGreen.getValue(), this.obiBlue.getValue());
            }
            return;
        });
        UwUGodTessellator.release();
    }
    
    private void drawBlock(final BlockPos blockPos, final int r, final int g, final int b) {
        final Color color = new Color(r, g, b, this.alpha.getValue());
        int mask = 1;
        final IBlockState iblockstate = HoleESP.mc.field_71441_e.func_180495_p(blockPos);
        final Vec3d interp = MathUtil.interpolateEntity((Entity)HoleESP.mc.field_71439_g, HoleESP.mc.func_184121_ak());
        if (this.renderMode.getValue().equals(RenderMode.BLOCK)) {
            mask = 63;
        }
        UwUGodTessellator.drawBox2(iblockstate.func_185918_c((World)HoleESP.mc.field_71441_e, blockPos).func_72317_d(-interp.field_72450_a, -interp.field_72448_b, -interp.field_72449_c), r, g, b, this.alpha.getValue(), mask);
    }
    
    @Override
    public String getHudInfo() {
        return (this.holeType.getValue() == HoleType.BROCK) ? "Bedrock" : ((this.holeType.getValue() == HoleType.BOTH) ? "Both" : "Obsidian");
    }
    
    private enum RenderMode
    {
        DOWN, 
        BLOCK;
    }
    
    private enum HoleType
    {
        BROCK, 
        OBI, 
        BOTH;
    }
}
