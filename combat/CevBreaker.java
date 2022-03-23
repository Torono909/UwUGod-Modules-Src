// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import net.minecraft.block.BlockObsidian;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.Vec3i;
import meow.candycat.uwu.util.Wrapper;
import meow.candycat.uwu.util.BlockInteractionHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;
import net.minecraft.util.EnumHand;
import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import meow.candycat.uwu.command.Command;
import java.util.Comparator;
import meow.candycat.uwu.util.Friends;
import java.util.Iterator;
import java.util.Collection;
import net.minecraft.entity.Entity;
import java.util.ArrayList;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.world.World;
import net.minecraft.network.play.client.CPacketUseEntity;
import java.util.function.Predicate;
import net.minecraft.network.play.server.SPacketSpawnObject;
import meow.candycat.uwu.setting.Settings;
import net.minecraft.util.math.Vec3d;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import meow.candycat.uwu.setting.Setting;
import net.minecraft.entity.player.EntityPlayer;
import meow.candycat.uwu.module.Module;

@Info(name = "CevBreaker", category = Category.COMBAT)
public class CevBreaker extends Module
{
    int stage;
    EntityPlayer target;
    public Setting<Boolean> rotate;
    public Setting<Double> range;
    public Setting<Integer> waitDelay;
    boolean firstRun;
    int tick;
    boolean crystalSpawned;
    BlockPos targetPos;
    EnumFacing f;
    boolean shouldWait;
    int delay;
    @EventHandler
    private Listener<PacketEvent.Receive> receiveListener;
    @EventHandler
    private Listener<PacketEvent.Send> sendListener;
    BlockPos neighbor;
    EnumFacing side2;
    Vec3d hitVec;
    int playerHotbarSlot;
    
    public CevBreaker() {
        this.stage = 0;
        this.target = null;
        this.rotate = this.register(Settings.b("Rotate", false));
        this.range = this.register(Settings.d("Range", 5.0));
        this.waitDelay = this.register(Settings.i("WaitDelay", 5));
        this.firstRun = true;
        this.tick = 0;
        this.crystalSpawned = false;
        this.targetPos = null;
        this.shouldWait = true;
        this.delay = this.waitDelay.getValue();
        this.receiveListener = new Listener<PacketEvent.Receive>(e -> {
            if (e.getPacket() instanceof SPacketSpawnObject && ((SPacketSpawnObject)e.getPacket()).func_148993_l() == 51 && (int)((SPacketSpawnObject)e.getPacket()).func_186880_c() == (int)this.target.field_70165_t && (int)((SPacketSpawnObject)e.getPacket()).func_186881_e() == (int)this.target.field_70161_v && this.stage == 1) {
                this.stage = 2;
            }
            return;
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
        Entity entity;
        final Iterator<Entity> iterator;
        Entity e2;
        this.sendListener = new Listener<PacketEvent.Send>(event -> {
            if (event.getPacket() instanceof CPacketUseEntity) {
                entity = ((CPacketUseEntity)event.getPacket()).func_149564_a((World)CevBreaker.mc.field_71441_e);
                if (entity instanceof EntityEnderCrystal) {
                    new ArrayList<Entity>(CevBreaker.mc.field_71441_e.field_72996_f).iterator();
                    while (iterator.hasNext()) {
                        e2 = iterator.next();
                        if (e2 instanceof EntityEnderCrystal && e2.func_70011_f(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v) <= 6.0) {
                            e2.func_70106_y();
                        }
                    }
                }
            }
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
    }
    
    public void onEnable() {
        if (CevBreaker.mc.field_71439_g == null || CevBreaker.mc.field_71441_e == null) {
            this.disable();
            return;
        }
        this.stage = 0;
        this.target = (EntityPlayer)CevBreaker.mc.field_71441_e.field_73010_i.stream().filter(e -> !Friends.isFriend(e.func_70005_c_()) && e != CevBreaker.mc.field_71439_g && e.func_110143_aJ() > 0.0f && !e.field_70128_L).sorted(Comparator.comparing(e -> CevBreaker.mc.field_71439_g.func_70032_d(e))).findFirst().orElse(null);
        if (this.target == null) {
            Command.sendChatMessage("[CevBreaker] No target found, toggling");
            this.disable();
        }
        this.targetPos = null;
        this.firstRun = true;
        this.f = null;
        this.shouldWait = true;
        this.crystalSpawned = false;
    }
    
    @Override
    public void onUpdate() {
        ++this.tick;
        if (this.tick % 2 != 0) {
            return;
        }
        switch (this.stage) {
            case 0: {
                if (this.targetPos == null) {
                    final BlockPos offsetPos = new BlockPos(new Vec3d(0.0, 3.0, 0.0));
                    this.targetPos = new BlockPos(this.target.func_174791_d()).func_177977_b().func_177982_a(offsetPos.field_177962_a, offsetPos.field_177960_b, offsetPos.field_177961_c);
                }
                boolean shouldTryToPlace = true;
                for (final Entity entity2 : CevBreaker.mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(this.targetPos))) {
                    if (!(entity2 instanceof EntityItem) && !(entity2 instanceof EntityXPOrb)) {
                        shouldTryToPlace = false;
                        break;
                    }
                }
                if ((shouldTryToPlace && this.placeBlock(this.targetPos)) || CevBreaker.mc.field_71441_e.func_180495_p(this.targetPos).func_177230_c() == Blocks.field_150343_Z) {
                    ++this.stage;
                    break;
                }
                break;
            }
            case 1: {
                if (CevBreaker.mc.field_71441_e.func_180495_p(this.targetPos).func_177230_c() != Blocks.field_150343_Z) {
                    this.stage = 0;
                    return;
                }
                int crystalSlot = (CevBreaker.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_185158_cP) ? CevBreaker.mc.field_71439_g.field_71071_by.field_70461_c : -1;
                if (crystalSlot == -1) {
                    for (int l = 0; l < 9; ++l) {
                        if (CevBreaker.mc.field_71439_g.field_71071_by.func_70301_a(l).func_77973_b() == Items.field_185158_cP) {
                            crystalSlot = l;
                            break;
                        }
                    }
                }
                boolean offhand = false;
                if (CevBreaker.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_185158_cP) {
                    offhand = true;
                }
                else if (crystalSlot == -1) {
                    this.disable();
                    Command.sendChatMessage("[CevBreaker] No crystals, toggling");
                    return;
                }
                if (!offhand && crystalSlot != CevBreaker.mc.field_71439_g.field_71071_by.field_70461_c) {
                    CevBreaker.mc.field_71439_g.field_71071_by.field_70461_c = crystalSlot;
                }
                final RayTraceResult result = CevBreaker.mc.field_71441_e.func_72933_a(new Vec3d(CevBreaker.mc.field_71439_g.field_70165_t, CevBreaker.mc.field_71439_g.field_70163_u + CevBreaker.mc.field_71439_g.func_70047_e(), CevBreaker.mc.field_71439_g.field_70161_v), new Vec3d(this.targetPos.field_177962_a + 0.5, (double)this.targetPos.field_177960_b, this.targetPos.field_177961_c + 0.5));
                EnumFacing enumFacing;
                if (result == null || result.field_178784_b == null) {
                    enumFacing = EnumFacing.UP;
                }
                else {
                    enumFacing = result.field_178784_b;
                }
                CevBreaker.mc.field_71442_b.func_187099_a(CevBreaker.mc.field_71439_g, CevBreaker.mc.field_71441_e, this.targetPos, enumFacing, new Vec3d(this.targetPos.field_177962_a + 0.5, (double)(this.targetPos.field_177960_b + 1), this.targetPos.field_177961_c + 0.5), offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                for (final EntityEnderCrystal crystal : (List)CevBreaker.mc.field_71441_e.field_72996_f.stream().filter(entity -> entity instanceof EntityEnderCrystal).map(entity -> entity).sorted(Comparator.comparing(e -> CevBreaker.mc.field_71439_g.func_70032_d(e))).collect(Collectors.toList())) {
                    if ((int)crystal.field_70165_t == (int)this.target.field_70165_t && (int)crystal.field_70161_v == (int)this.target.field_70161_v) {
                        ++this.stage;
                        break;
                    }
                }
                break;
            }
            case 2: {
                int crystalSlot = (CevBreaker.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151046_w) ? CevBreaker.mc.field_71439_g.field_71071_by.field_70461_c : -1;
                if (crystalSlot == -1) {
                    for (int l = 0; l < 9; ++l) {
                        if (CevBreaker.mc.field_71439_g.field_71071_by.func_70301_a(l).func_77973_b() == Items.field_151046_w) {
                            crystalSlot = l;
                            break;
                        }
                    }
                }
                if (crystalSlot == -1) {
                    this.disable();
                    Command.sendChatMessage("[CevBreaker] No Pick, toggling");
                    return;
                }
                if (crystalSlot != CevBreaker.mc.field_71439_g.field_71071_by.field_70461_c) {
                    CevBreaker.mc.field_71439_g.field_71071_by.field_70461_c = crystalSlot;
                }
                if (this.f == null) {
                    final RayTraceResult result2 = CevBreaker.mc.field_71441_e.func_72933_a(new Vec3d(CevBreaker.mc.field_71439_g.field_70165_t, CevBreaker.mc.field_71439_g.field_70163_u + CevBreaker.mc.field_71439_g.func_70047_e(), CevBreaker.mc.field_71439_g.field_70161_v), new Vec3d(this.targetPos.field_177962_a + 0.5, (double)this.targetPos.field_177960_b, this.targetPos.field_177961_c + 0.5));
                    if (result2 == null || result2.field_178784_b == null || CevBreaker.mc.field_71439_g.func_174818_b(this.targetPos) > Math.pow(this.range.getValue(), 2.0)) {
                        return;
                    }
                    this.f = result2.field_178784_b;
                }
                if (this.firstRun) {
                    CevBreaker.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.targetPos, this.f));
                    this.firstRun = false;
                    this.shouldWait = true;
                }
                CevBreaker.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.targetPos, this.f));
                ++this.stage;
                this.delay = this.waitDelay.getValue();
                break;
            }
            case 3: {
                if (this.delay > 0) {
                    --this.delay;
                    return;
                }
                if (!this.shouldWait) {
                    CevBreaker.mc.field_71442_b.func_187103_a(this.targetPos);
                    CevBreaker.mc.field_71441_e.func_175698_g(this.targetPos);
                    ++this.stage;
                    break;
                }
                if (CevBreaker.mc.field_71441_e.func_180495_p(this.targetPos).func_177230_c() == Blocks.field_150350_a) {
                    this.shouldWait = false;
                    ++this.stage;
                    break;
                }
                break;
            }
            case 4: {
                this.stage = 0;
                break;
            }
        }
    }
    
    private boolean placeBlock(final BlockPos pos) {
        if (!CevBreaker.mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76222_j()) {
            return false;
        }
        if (!BlockInteractionHelper.checkForNeighbours(pos)) {
            return false;
        }
        final Vec3d eyesPos = new Vec3d(Wrapper.getPlayer().field_70165_t, Wrapper.getPlayer().field_70163_u + Wrapper.getPlayer().func_70047_e(), Wrapper.getPlayer().field_70161_v);
        for (final EnumFacing side : EnumFacing.values()) {
            this.neighbor = pos.func_177972_a(side);
            this.side2 = side.func_176734_d();
            if (CevBreaker.mc.field_71441_e.func_180495_p(this.neighbor).func_177230_c().func_176209_a(CevBreaker.mc.field_71441_e.func_180495_p(this.neighbor), false)) {
                this.hitVec = new Vec3d((Vec3i)this.neighbor).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(this.side2.func_176730_m()).func_186678_a(0.5));
                if (eyesPos.func_72438_d(this.hitVec) <= this.range.getValue()) {
                    final int obiSlot = this.findObiInHotbar();
                    if (obiSlot == -1) {
                        this.disable();
                        return false;
                    }
                    boolean switched = false;
                    if (CevBreaker.mc.field_71439_g.field_71071_by.field_70461_c != obiSlot) {
                        this.playerHotbarSlot = CevBreaker.mc.field_71439_g.field_71071_by.field_70461_c;
                        Wrapper.getPlayer().field_71071_by.field_70461_c = obiSlot;
                        switched = true;
                    }
                    if (this.rotate.getValue()) {
                        final float[] w = BlockInteractionHelper.getLegitRotations(this.hitVec);
                        CevBreaker.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(w[0], w[1], CevBreaker.mc.field_71439_g.field_70122_E));
                    }
                    boolean isSneaking = false;
                    final Block neighborPos = CevBreaker.mc.field_71441_e.func_180495_p(this.neighbor).func_177230_c();
                    if (BlockInteractionHelper.blackList.contains(neighborPos) || BlockInteractionHelper.shulkerList.contains(neighborPos)) {
                        CevBreaker.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)CevBreaker.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
                        isSneaking = true;
                    }
                    CevBreaker.mc.field_71442_b.func_187099_a(CevBreaker.mc.field_71439_g, CevBreaker.mc.field_71441_e, this.neighbor, this.side2, this.hitVec, EnumHand.MAIN_HAND);
                    CevBreaker.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                    if (switched) {
                        CevBreaker.mc.field_71439_g.field_71071_by.field_70461_c = this.playerHotbarSlot;
                    }
                    if (isSneaking) {
                        CevBreaker.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)CevBreaker.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    private int findObiInHotbar() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Wrapper.getPlayer().field_71071_by.func_70301_a(i);
            if (stack != ItemStack.field_190927_a && stack.func_77973_b() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
                if (block instanceof BlockObsidian) {
                    slot = i;
                    break;
                }
            }
        }
        return slot;
    }
}
