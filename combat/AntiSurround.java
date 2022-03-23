// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import net.minecraft.entity.Entity;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.util.math.RayTraceResult;
import java.util.Iterator;
import java.util.function.Predicate;
import meow.candycat.uwu.util.EntityUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import meow.candycat.uwu.module.ModuleManager;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.player.EntityPlayer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Comparator;
import meow.candycat.uwu.util.Friends;
import java.util.List;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.EventPlayerPreMotionUpdateBeforeAimbot;
import meow.candycat.eventsystem.listener.Listener;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "AntiSurround", category = Category.COMBAT)
public class AntiSurround extends Module
{
    public Setting<Double> range;
    public Setting<Boolean> rotate;
    public Setting<Boolean> bed;
    private BlockPos breakPos;
    public boolean rotated;
    boolean startedMining;
    EnumFacing f;
    String name;
    @EventHandler
    private Listener<EventPlayerPreMotionUpdateBeforeAimbot> listener;
    
    public AntiSurround() {
        this.range = this.register(Settings.d("Range", 5.0));
        this.rotate = this.register(Settings.b("Rotate", true));
        this.bed = this.register(Settings.b("BedMode", false));
        this.rotated = false;
        this.startedMining = false;
        this.name = null;
        List playerList;
        final Iterator<EntityPlayer> iterator;
        EntityPlayer player;
        Vec3d[] holeOffset;
        Vec3d[] holeOffset2;
        double distance;
        int i;
        Vec3d vecOffset;
        Vec3d vecOffset2;
        BlockPos offset;
        BlockPos offset2;
        RayTraceResult result;
        double[] w;
        this.listener = new Listener<EventPlayerPreMotionUpdateBeforeAimbot>(g -> {
            if (this.breakPos == null) {
                playerList = (List)AntiSurround.mc.field_71441_e.field_73010_i.stream().filter(e -> !Friends.isFriend(((EntityPlayer)e).func_70005_c_()) && e != AntiSurround.mc.field_71439_g && ((EntityPlayer)e).func_110143_aJ() > 0.0f && !((EntityPlayer)e).field_70128_L).sorted(Comparator.comparing(e -> AntiSurround.mc.field_71439_g.func_70032_d(e))).collect(Collectors.toList());
                playerList.iterator();
                while (iterator.hasNext()) {
                    player = iterator.next();
                    holeOffset = new Vec3d[] { player.func_174791_d().func_72441_c(1.0, 0.0, 0.0), player.func_174791_d().func_72441_c(-1.0, 0.0, 0.0), player.func_174791_d().func_72441_c(0.0, 0.0, 1.0), player.func_174791_d().func_72441_c(0.0, 0.0, -1.0) };
                    holeOffset2 = new Vec3d[] { player.func_174791_d().func_72441_c(2.0, 0.0, 0.0), player.func_174791_d().func_72441_c(-2.0, 0.0, 0.0), player.func_174791_d().func_72441_c(0.0, 0.0, 2.0), player.func_174791_d().func_72441_c(0.0, 0.0, -2.0) };
                    distance = 10000.0;
                    i = 0;
                    while (i < 4) {
                        vecOffset = holeOffset[i];
                        vecOffset2 = holeOffset2[i];
                        offset = new BlockPos(vecOffset.field_72450_a, vecOffset.field_72448_b, vecOffset.field_72449_c);
                        offset2 = new BlockPos(vecOffset2.field_72450_a, vecOffset2.field_72448_b, vecOffset2.field_72449_c);
                        if ((AntiSurround.mc.field_71441_e.func_180495_p(offset).func_177230_c() == Blocks.field_150343_Z || AntiSurround.mc.field_71441_e.func_180495_p(offset).func_177230_c() == Blocks.field_150477_bB || AntiSurround.mc.field_71441_e.func_180495_p(offset).func_177230_c() == Blocks.field_150467_bQ) && AntiSurround.mc.field_71439_g.func_174818_b(offset) < distance && (this.bed.getValue() || (AntiSurround.mc.field_71441_e.func_180495_p(offset2).func_177230_c() == Blocks.field_150350_a && AntiSurround.mc.field_71441_e.func_180495_p(offset2.func_177984_a()).func_177230_c() == Blocks.field_150350_a && (AntiSurround.mc.field_71441_e.func_180495_p(offset2.func_177977_b()).func_177230_c() == Blocks.field_150343_Z || AntiSurround.mc.field_71441_e.func_180495_p(offset2.func_177977_b()).func_177230_c() == Blocks.field_150357_h)))) {
                            distance = AntiSurround.mc.field_71439_g.func_174818_b(offset);
                            this.breakPos = offset;
                        }
                        if (AntiSurround.mc.field_71441_e.func_180495_p(offset).func_177230_c() == Blocks.field_150350_a && AntiSurround.mc.field_71441_e.func_180495_p(offset2).func_177230_c() == Blocks.field_150350_a && AntiSurround.mc.field_71441_e.func_180495_p(offset2.func_177984_a()).func_177230_c() == Blocks.field_150350_a && (AntiSurround.mc.field_71441_e.func_180495_p(offset2.func_177977_b()).func_177230_c() == Blocks.field_150343_Z || AntiSurround.mc.field_71441_e.func_180495_p(offset2.func_177977_b()).func_177230_c() == Blocks.field_150357_h)) {
                            this.breakPos = null;
                            if (this.rotated) {
                                this.rotated = false;
                                ((Aimbot)ModuleManager.getModuleByName("Aimbot")).resetRotation();
                            }
                            return;
                        }
                        else {
                            ++i;
                        }
                    }
                    if (this.breakPos != null) {
                        this.name = player.func_70005_c_();
                        break;
                    }
                }
                if (this.breakPos == null) {
                    if (this.rotated) {
                        ((Aimbot)ModuleManager.getModuleByName("Aimbot")).resetRotation();
                        this.rotated = false;
                    }
                    return;
                }
            }
            if (this.f == null) {
                result = AntiSurround.mc.field_71441_e.func_72933_a(new Vec3d(AntiSurround.mc.field_71439_g.field_70165_t, AntiSurround.mc.field_71439_g.field_70163_u + AntiSurround.mc.field_71439_g.func_70047_e(), AntiSurround.mc.field_71439_g.field_70161_v), new Vec3d(this.breakPos.field_177962_a + 0.5, (double)this.breakPos.field_177960_b, this.breakPos.field_177961_c + 0.5));
                if (result == null || result.field_178784_b == null || AntiSurround.mc.field_71439_g.func_174818_b(this.breakPos) > Math.pow(this.range.getValue(), 2.0)) {
                    return;
                }
                else {
                    this.f = result.field_178784_b;
                }
            }
            AntiSurround.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
            if (!this.startedMining) {
                AntiSurround.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.breakPos, this.f));
                this.startedMining = true;
            }
            else {
                AntiSurround.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.breakPos, this.f));
            }
            if (this.rotate.getValue()) {
                w = EntityUtil.calculateLookAt(this.breakPos.field_177962_a + 0.5, this.breakPos.field_177960_b, this.breakPos.field_177961_c + 0.5, (EntityPlayer)AntiSurround.mc.field_71439_g);
                ((Aimbot)ModuleManager.getModuleByName("Aimbot")).setRotation((float)w[0], (float)w[1]);
                this.rotated = true;
            }
        }, (Predicate<EventPlayerPreMotionUpdateBeforeAimbot>[])new Predicate[0]);
    }
    
    public void onEnable() {
        this.breakPos = null;
        this.rotated = false;
        this.f = null;
        this.startedMining = false;
        this.name = null;
    }
    
    private boolean canBreak(final BlockPos pos) {
        final IBlockState blockState = AntiSurround.mc.field_71441_e.func_180495_p(pos);
        final Block block = blockState.func_177230_c();
        return block.func_176195_g(blockState, (World)AntiSurround.mc.field_71441_e, pos) != -1.0f;
    }
    
    @Override
    public String getHudInfo() {
        return (this.name == null) ? null : this.name;
    }
    
    public void onDisable() {
        ((Aimbot)ModuleManager.getModuleByName("Aimbot")).resetRotation();
        this.rotated = false;
    }
}
