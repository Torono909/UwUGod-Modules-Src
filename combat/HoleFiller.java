// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.minecraft.util.NonNullList;
import java.util.Iterator;
import meow.candycat.uwu.util.Friends;
import meow.candycat.uwu.util.EntityUtil;
import meow.candycat.uwu.util.UwUGodTessellator;
import meow.candycat.uwu.event.events.RenderEvent;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import meow.candycat.uwu.util.Wrapper;
import net.minecraft.entity.item.EntityEnderCrystal;
import meow.candycat.uwu.command.Command;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.network.Packet;
import java.util.List;
import net.minecraft.network.play.client.CPacketPlayer;
import java.util.function.Predicate;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.Comparator;
import net.minecraft.entity.Entity;
import meow.candycat.uwu.util.BlockInteractionHelper;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import meow.candycat.uwu.module.ModuleManager;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.EventPlayerPreMotionUpdateBeforeAimbot;
import meow.candycat.eventsystem.listener.Listener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "HoleFiller", category = Category.COMBAT)
public class HoleFiller extends Module
{
    private Setting<Double> range;
    private Setting<Boolean> smart;
    private Setting<Integer> smartRange;
    private Setting<Boolean> announceUsage;
    private BlockPos render;
    private EntityPlayer closestTarget;
    int resetTimer;
    boolean rotated;
    @EventHandler
    private Listener<EventPlayerPreMotionUpdateBeforeAimbot> w;
    private static boolean isSpoofingAngles;
    private static double yaw;
    private static double pitch;
    @EventHandler
    private Listener<PacketEvent.Send> packetListener;
    
    public HoleFiller() {
        this.range = this.register(Settings.d("Range", 4.5));
        this.smart = this.register(Settings.b("Smart", false));
        this.smartRange = this.register(Settings.i("Smart Range", 4));
        this.announceUsage = this.register(Settings.b("Announce Usage", false));
        this.rotated = false;
        BlockPos q;
        double dist;
        double prevDist;
        int obsidianSlot;
        int l;
        KillAura killAura;
        EntityPlayer w;
        KillAura killAura2;
        List<BlockPos> blocks;
        final void void1;
        BlockPos q2;
        KillAura killAura3;
        int oldSlot;
        boolean y;
        KillAura killAura4;
        this.w = new Listener<EventPlayerPreMotionUpdateBeforeAimbot>(event -> {
            if (HoleFiller.mc.field_71439_g == null || ModuleManager.isModuleEnabled("Freecam")) {
                return;
            }
            else if (HoleFiller.mc.field_71441_e == null) {
                return;
            }
            else {
                if (this.smart.getValue()) {
                    this.findClosestTarget();
                }
                q = null;
                dist = 0.0;
                prevDist = 0.0;
                obsidianSlot = ((HoleFiller.mc.field_71439_g.func_184614_ca().func_77973_b() == Item.func_150898_a(Blocks.field_150343_Z)) ? HoleFiller.mc.field_71439_g.field_71071_by.field_70461_c : -1);
                if (obsidianSlot == -1) {
                    l = 0;
                    while (l < 9) {
                        if (HoleFiller.mc.field_71439_g.field_71071_by.func_70301_a(l).func_77973_b() == Item.func_150898_a(Blocks.field_150343_Z)) {
                            obsidianSlot = l;
                            break;
                        }
                        else {
                            ++l;
                        }
                    }
                }
                if (obsidianSlot == -1) {
                    if (this.rotated && !((AutoCrystal)ModuleManager.getModuleByName("AutoCrystal")).rotated) {
                        killAura = (KillAura)ModuleManager.getModuleByName("KillAura");
                        if (!KillAura.isAiming) {
                            ((Aimbot)ModuleManager.getModuleByName("Aimbot")).resetRotation();
                            this.resetTimer = 0;
                            this.rotated = false;
                        }
                    }
                    return;
                }
                else {
                    w = this.findClosestTarget();
                    if (w == null) {
                        ++this.resetTimer;
                        if (this.resetTimer >= 5 && this.rotated && !((AutoCrystal)ModuleManager.getModuleByName("AutoCrystal")).rotated) {
                            killAura2 = (KillAura)ModuleManager.getModuleByName("KillAura");
                            if (!KillAura.isAiming) {
                                ((Aimbot)ModuleManager.getModuleByName("Aimbot")).resetRotation();
                                this.resetTimer = 0;
                                this.rotated = false;
                            }
                        }
                        return;
                    }
                    else {
                        blocks = this.findCrystalBlocks((EntityPlayer)HoleFiller.mc.field_71439_g);
                        blocks.sort(Comparator.comparing(e -> BlockInteractionHelper.blockDistance2d(e.field_177962_a + 0.5, e.field_177961_c + 0.5, (Entity)w)));
                        blocks.removeIf(e -> {
                            if (Math.sqrt(w.func_70092_e(e.field_177962_a + 0.5, (double)e.field_177960_b, e.field_177961_c + 0.5)) <= 2.5) {
                                if (!HoleFiller.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(e)).isEmpty()) {
                                    if (!(!HoleFiller.mc.field_71441_e.func_72872_a((Class)EntityItem.class, new AxisAlignedBB(e)).isEmpty())) {
                                        return 1 != 0;
                                    }
                                }
                                if (Math.sqrt(HoleFiller.mc.field_71439_g.func_70092_e((double)e.field_177962_a, (double)(e.field_177960_b - HoleFiller.mc.field_71439_g.func_70047_e()), (double)e.field_177961_c)) <= this.range.getValue()) {
                                    return (boolean)void1;
                                }
                            }
                            return (boolean)void1;
                        });
                        q2 = blocks.stream().findFirst().orElse(null);
                        if ((this.render = q2) == null) {
                            ++this.resetTimer;
                            if (this.resetTimer >= 5 && this.rotated && !((AutoCrystal)ModuleManager.getModuleByName("AutoCrystal")).rotated) {
                                killAura3 = (KillAura)ModuleManager.getModuleByName("KillAura");
                                if (!KillAura.isAiming) {
                                    ((Aimbot)ModuleManager.getModuleByName("Aimbot")).resetRotation();
                                    this.resetTimer = 0;
                                    this.rotated = false;
                                }
                            }
                        }
                        else if (HoleFiller.mc.field_71439_g.field_70122_E) {
                            oldSlot = HoleFiller.mc.field_71439_g.field_71071_by.field_70461_c;
                            if (HoleFiller.mc.field_71439_g.field_71071_by.field_70461_c != obsidianSlot) {
                                HoleFiller.mc.field_71439_g.field_71071_by.field_70461_c = obsidianSlot;
                            }
                            y = this.placeBlockScaffold(this.render);
                            if (!y) {
                                ++this.resetTimer;
                                if (this.resetTimer >= 5 && this.rotated && !((AutoCrystal)ModuleManager.getModuleByName("AutoCrystal")).rotated) {
                                    killAura4 = (KillAura)ModuleManager.getModuleByName("KillAura");
                                    if (!KillAura.isAiming) {
                                        ((Aimbot)ModuleManager.getModuleByName("Aimbot")).resetRotation();
                                        this.resetTimer = 0;
                                        this.rotated = false;
                                    }
                                }
                            }
                            else {
                                this.rotated = true;
                                this.resetTimer = 0;
                            }
                            HoleFiller.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                            HoleFiller.mc.field_71439_g.field_71071_by.field_70461_c = oldSlot;
                        }
                        return;
                    }
                }
            }
        }, (Predicate<EventPlayerPreMotionUpdateBeforeAimbot>[])new Predicate[0]);
        final Packet packet;
        this.packetListener = new Listener<PacketEvent.Send>(event -> {
            packet = event.getPacket();
            if (packet instanceof CPacketPlayer && HoleFiller.isSpoofingAngles) {
                ((CPacketPlayer)packet).field_149476_e = (float)HoleFiller.yaw;
                ((CPacketPlayer)packet).field_149473_f = (float)HoleFiller.pitch;
            }
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
    }
    
    public void onEnable() {
        if (this.announceUsage.getValue()) {
            Command.sendChatMessage("[HoleFiller] " + ChatFormatting.GREEN.toString() + "Enabled" + ChatFormatting.RESET.toString() + "!");
        }
    }
    
    public boolean placeBlockScaffold(final BlockPos pos) {
        if (HoleFiller.mc.field_71441_e.func_72872_a((Class)EntityPlayer.class, new AxisAlignedBB(pos)).isEmpty() && HoleFiller.mc.field_71441_e.func_72872_a((Class)EntityEnderCrystal.class, new AxisAlignedBB(pos)).isEmpty()) {
            final Vec3d eyesPos = new Vec3d(Wrapper.getPlayer().field_70165_t, Wrapper.getPlayer().field_70163_u + Wrapper.getPlayer().func_70047_e(), Wrapper.getPlayer().field_70161_v);
            for (final EnumFacing side : EnumFacing.values()) {
                final BlockPos neighbor = pos.func_177972_a(side);
                final EnumFacing side2 = side.func_176734_d();
                if (BlockInteractionHelper.canBeClicked(neighbor)) {
                    final Vec3d hitVec = new Vec3d((Vec3i)neighbor).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(side2.func_176730_m()).func_186678_a(0.5));
                    final float[] w = BlockInteractionHelper.getLegitRotations(hitVec);
                    ((Aimbot)ModuleManager.getModuleByName("Aimbot")).setRotation(w[0], w[1]);
                    HoleFiller.mc.field_71442_b.func_187099_a(HoleFiller.mc.field_71439_g, HoleFiller.mc.field_71441_e, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                    Wrapper.getPlayer().func_184609_a(EnumHand.MAIN_HAND);
                    HoleFiller.mc.field_71467_ac = 4;
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void onWorldRender(final RenderEvent event) {
        if (this.render != null) {
            UwUGodTessellator.prepare(7);
            UwUGodTessellator.drawBox(this.render, 822018048, 63);
            UwUGodTessellator.release();
            UwUGodTessellator.prepare(7);
            UwUGodTessellator.drawBoundingBoxBlockPos(this.render, 1.0f, 0, 255, 0, 170);
            UwUGodTessellator.release();
        }
    }
    
    private double getDistanceToBlockPos(final BlockPos pos1, final BlockPos pos2) {
        final double x = pos1.func_177958_n() - pos2.func_177958_n();
        final double y = pos1.func_177956_o() - pos2.func_177956_o();
        final double z = pos1.func_177952_p() - pos2.func_177952_p();
        return Math.sqrt(x * x + y * y + z * z);
    }
    
    private void lookAtPacket(final double px, final double py, final double pz, final EntityPlayer me) {
        final double[] v = EntityUtil.calculateLookAt(px, py, pz, me);
        setYawAndPitch((float)v[0], (float)v[1]);
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
        return HoleFiller.mc.field_71441_e.func_180495_p(boost).func_177230_c() == Blocks.field_150350_a && HoleFiller.mc.field_71441_e.func_180495_p(boost2).func_177230_c() == Blocks.field_150350_a && HoleFiller.mc.field_71441_e.func_180495_p(boost7).func_177230_c() == Blocks.field_150350_a && (HoleFiller.mc.field_71441_e.func_180495_p(boost3).func_177230_c() == Blocks.field_150343_Z || HoleFiller.mc.field_71441_e.func_180495_p(boost3).func_177230_c() == Blocks.field_150357_h || HoleFiller.mc.field_71441_e.func_180495_p(boost3).func_177230_c() == Blocks.field_150477_bB) && (HoleFiller.mc.field_71441_e.func_180495_p(boost4).func_177230_c() == Blocks.field_150343_Z || HoleFiller.mc.field_71441_e.func_180495_p(boost4).func_177230_c() == Blocks.field_150357_h || HoleFiller.mc.field_71441_e.func_180495_p(boost4).func_177230_c() == Blocks.field_150477_bB) && (HoleFiller.mc.field_71441_e.func_180495_p(boost5).func_177230_c() == Blocks.field_150343_Z || HoleFiller.mc.field_71441_e.func_180495_p(boost5).func_177230_c() == Blocks.field_150357_h || HoleFiller.mc.field_71441_e.func_180495_p(boost5).func_177230_c() == Blocks.field_150477_bB) && (HoleFiller.mc.field_71441_e.func_180495_p(boost6).func_177230_c() == Blocks.field_150343_Z || HoleFiller.mc.field_71441_e.func_180495_p(boost6).func_177230_c() == Blocks.field_150357_h || HoleFiller.mc.field_71441_e.func_180495_p(boost6).func_177230_c() == Blocks.field_150477_bB) && HoleFiller.mc.field_71441_e.func_180495_p(boost8).func_177230_c() == Blocks.field_150350_a && (HoleFiller.mc.field_71441_e.func_180495_p(boost9).func_177230_c() == Blocks.field_150343_Z || HoleFiller.mc.field_71441_e.func_180495_p(boost9).func_177230_c() == Blocks.field_150357_h || HoleFiller.mc.field_71441_e.func_180495_p(boost9).func_177230_c() == Blocks.field_150477_bB);
    }
    
    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(HoleFiller.mc.field_71439_g.field_70165_t), Math.floor(HoleFiller.mc.field_71439_g.field_70163_u), Math.floor(HoleFiller.mc.field_71439_g.field_70161_v));
    }
    
    public BlockPos getClosestTargetPos() {
        if (this.closestTarget != null) {
            return new BlockPos(Math.floor(this.closestTarget.field_70165_t), Math.floor(this.closestTarget.field_70163_u), Math.floor(this.closestTarget.field_70161_v));
        }
        return null;
    }
    
    private EntityPlayer findClosestTarget() {
        final List<EntityPlayer> playerList = (List<EntityPlayer>)HoleFiller.mc.field_71441_e.field_73010_i;
        EntityPlayer closesttarget = null;
        for (final EntityPlayer target : playerList) {
            if (target == HoleFiller.mc.field_71439_g) {
                continue;
            }
            if (Friends.isFriend(target.func_70005_c_())) {
                continue;
            }
            if (!EntityUtil.isLiving((Entity)target)) {
                continue;
            }
            if (target.func_110143_aJ() <= 0.0f) {
                continue;
            }
            if (closesttarget == null) {
                closesttarget = target;
            }
            else {
                if (HoleFiller.mc.field_71439_g.func_70032_d((Entity)target) >= HoleFiller.mc.field_71439_g.func_70032_d((Entity)closesttarget)) {
                    continue;
                }
                closesttarget = target;
            }
        }
        return closesttarget;
    }
    
    private boolean isInRange(final BlockPos blockPos) {
        final NonNullList<BlockPos> positions = (NonNullList<BlockPos>)NonNullList.func_191196_a();
        positions.addAll((Collection)this.getSphere(getPlayerPos(), this.range.getValue().floatValue(), this.range.getValue().intValue(), false, true, 0).stream().filter((Predicate<? super Object>)this::IsHole).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        return positions.contains((Object)blockPos);
    }
    
    private List<BlockPos> findCrystalBlocks(final EntityPlayer player) {
        final NonNullList<BlockPos> positions = (NonNullList<BlockPos>)NonNullList.func_191196_a();
        if (this.smart.getValue() && this.closestTarget != null) {
            positions.addAll((Collection)this.getSphere(this.getClosestTargetPos(), this.smartRange.getValue(), this.range.getValue().intValue(), false, true, 0).stream().filter((Predicate<? super Object>)this::IsHole).filter((Predicate<? super Object>)this::isInRange).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        }
        else if (!this.smart.getValue()) {
            positions.addAll((Collection)this.getSphere(player.func_180425_c(), this.range.getValue().floatValue(), this.range.getValue().intValue(), false, true, 0).stream().filter((Predicate<? super Object>)this::IsHole).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        }
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
    
    private static void setYawAndPitch(final float yaw1, final float pitch1) {
        HoleFiller.yaw = yaw1;
        HoleFiller.pitch = pitch1;
        HoleFiller.isSpoofingAngles = true;
    }
    
    private static void resetRotation() {
        if (HoleFiller.isSpoofingAngles) {
            HoleFiller.yaw = HoleFiller.mc.field_71439_g.field_70177_z;
            HoleFiller.pitch = HoleFiller.mc.field_71439_g.field_70125_A;
            HoleFiller.isSpoofingAngles = false;
        }
    }
    
    public void onDisable() {
        this.closestTarget = null;
        this.render = null;
        resetRotation();
        if (this.announceUsage.getValue()) {
            Command.sendChatMessage("[HoleFiller] " + ChatFormatting.RED.toString() + "Disabled" + ChatFormatting.RESET.toString() + "!");
        }
    }
}
