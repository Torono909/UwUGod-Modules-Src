// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3i;
import meow.candycat.uwu.util.BlockInteractionHelper;
import meow.candycat.uwu.util.Wrapper;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import meow.candycat.uwu.command.Command;
import java.util.function.Predicate;
import meow.candycat.uwu.module.ModuleManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.EventPlayerPreMotionUpdateBeforeAimbot;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.module.Module;

@Info(name = "SelfTrap", category = Category.COMBAT)
public class SelfTrap extends Module
{
    int currentItem;
    int tickPassed;
    boolean rotated;
    boolean placed;
    boolean switched;
    boolean shouldStart;
    @EventHandler
    private Listener<EventPlayerPreMotionUpdateBeforeAimbot> x;
    
    public SelfTrap() {
        this.rotated = false;
        this.placed = false;
        this.switched = false;
        this.shouldStart = false;
        BlockPos mustBePlaced;
        final EnumFacing[] array;
        int length;
        int i = 0;
        EnumFacing side;
        BlockPos neighbor;
        final EnumFacing[] array2;
        int length2;
        int j = 0;
        EnumFacing side2;
        BlockPos neighbor2;
        BlockPos neighborUp;
        KillAura killAura;
        this.x = new Listener<EventPlayerPreMotionUpdateBeforeAimbot>(event -> {
            if (!this.isTrapped()) {
                this.placed = false;
                mustBePlaced = new BlockPos(SelfTrap.mc.field_71439_g.field_70165_t, SelfTrap.mc.field_71439_g.field_70163_u + 2.0, SelfTrap.mc.field_71439_g.field_70161_v);
                if (HoleDetect.inhole && HoleDetect.lastinhole && SelfTrap.mc.field_71439_g.field_70122_E && SelfTrap.mc.field_71439_g.field_184841_cd) {
                    if (this.canPlaceBlock(mustBePlaced)) {
                        this.placed = this.placeBlock(mustBePlaced);
                        this.rotated = (this.rotated || this.placed);
                        if (this.switched) {
                            SelfTrap.mc.field_71439_g.field_71071_by.field_70461_c = this.currentItem;
                            this.switched = false;
                        }
                        return;
                    }
                    else {
                        EnumFacing.values();
                        length = array.length;
                        while (i < length) {
                            side = array[i];
                            neighbor = mustBePlaced.func_177972_a(side);
                            if (this.canPlaceBlock(neighbor)) {
                                this.placed = this.placeBlock(neighbor);
                                this.rotated = (this.rotated || this.placed);
                                if (this.switched) {
                                    SelfTrap.mc.field_71439_g.field_71071_by.field_70461_c = this.currentItem;
                                    this.switched = false;
                                }
                                return;
                            }
                            else {
                                ++i;
                            }
                        }
                        EnumFacing.values();
                        length2 = array2.length;
                        while (j < length2) {
                            side2 = array2[j];
                            neighbor2 = mustBePlaced.func_177982_a(0, -1, 0).func_177972_a(side2);
                            neighborUp = mustBePlaced.func_177972_a(side2);
                            if (this.canPlaceBlock(neighbor2) && this.canHaveBlock(neighborUp)) {
                                this.placed = this.placeBlock(neighbor2);
                                this.rotated = (this.rotated || this.placed);
                                if (this.switched) {
                                    SelfTrap.mc.field_71439_g.field_71071_by.field_70461_c = this.currentItem;
                                    this.switched = false;
                                }
                                return;
                            }
                            else {
                                ++j;
                            }
                        }
                    }
                }
            }
            if (this.rotated && !this.placed) {
                ++this.tickPassed;
                if (this.tickPassed >= 5 && !((AutoCrystal)ModuleManager.getModuleByName("AutoCrystal")).rotated) {
                    killAura = (KillAura)ModuleManager.getModuleByName("KillAura");
                    if (!KillAura.isAiming) {
                        ((Aimbot)ModuleManager.getModuleByName("Aimbot")).resetRotation();
                        this.tickPassed = 0;
                        this.rotated = false;
                    }
                }
            }
        }, (Predicate<EventPlayerPreMotionUpdateBeforeAimbot>[])new Predicate[0]);
    }
    
    public void onEnable() {
        this.shouldStart = false;
    }
    
    @Override
    public void onUpdate() {
        if (this.isTrapped()) {
            Command.sendChatMessage("[SelfTrap] Trapped already, disabling");
            if (this.rotated && !((AutoCrystal)ModuleManager.getModuleByName("AutoCrystal")).rotated) {
                final KillAura killAura = (KillAura)ModuleManager.getModuleByName("KillAura");
                if (!KillAura.isAiming) {
                    ((Aimbot)ModuleManager.getModuleByName("Aimbot")).resetRotation();
                    this.tickPassed = 0;
                    this.rotated = false;
                }
            }
            this.disable();
            return;
        }
        if (this.findObiInHotbar() == -1) {
            this.disable();
            Command.sendChatMessage("[SelfTrap] No obby, disabling");
        }
    }
    
    private int findObiInHotbar() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = SelfTrap.mc.field_71439_g.field_71071_by.func_70301_a(i);
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
    
    public boolean isTrapped() {
        final Vec3d w = SelfTrap.mc.field_71439_g.func_174791_d().func_72441_c(0.0, 2.0, 0.0);
        final BlockPos blockPos = new BlockPos(w.field_72450_a, w.field_72448_b, w.field_72449_c);
        return HoleDetect.inhole && SelfTrap.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() != Blocks.field_150350_a;
    }
    
    public boolean placeBlock(final BlockPos blockPos) {
        final int obiSlot = this.findObiInHotbar();
        if (obiSlot == -1) {
            return false;
        }
        if (SelfTrap.mc.field_71439_g.field_71071_by.field_70461_c != obiSlot) {
            this.switched = true;
            this.currentItem = SelfTrap.mc.field_71439_g.field_71071_by.field_70461_c;
            SelfTrap.mc.field_71439_g.field_71071_by.field_70461_c = obiSlot;
        }
        final boolean y = this.placeBlockScaffold(blockPos);
        if (y) {
            this.tickPassed = 0;
        }
        return y;
    }
    
    public boolean placeBlockScaffold(final BlockPos pos) {
        if (SelfTrap.mc.field_71441_e.func_72872_a((Class)EntityPlayer.class, new AxisAlignedBB(pos)).isEmpty() && SelfTrap.mc.field_71441_e.func_72872_a((Class)EntityEnderCrystal.class, new AxisAlignedBB(pos)).isEmpty()) {
            final Vec3d eyesPos = new Vec3d(Wrapper.getPlayer().field_70165_t, Wrapper.getPlayer().field_70163_u + Wrapper.getPlayer().func_70047_e(), Wrapper.getPlayer().field_70161_v);
            for (final EnumFacing side : EnumFacing.values()) {
                final BlockPos neighbor = pos.func_177972_a(side);
                final EnumFacing side2 = side.func_176734_d();
                if (BlockInteractionHelper.canBeClicked(neighbor)) {
                    final Vec3d hitVec = new Vec3d((Vec3i)neighbor).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(side2.func_176730_m()).func_186678_a(0.5));
                    if (eyesPos.func_72436_e(hitVec) <= 36.0) {
                        BlockInteractionHelper.processRightClickBlock(neighbor, side2, hitVec);
                        final float[] w = BlockInteractionHelper.getLegitRotations(hitVec);
                        ((Aimbot)ModuleManager.getModuleByName("Aimbot")).setRotation(w[0], w[1]);
                        Wrapper.getPlayer().func_184609_a(EnumHand.MAIN_HAND);
                        SelfTrap.mc.field_71467_ac = 4;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean canHaveBlock(final BlockPos blockPos) {
        return SelfTrap.mc.field_71441_e.func_180495_p(blockPos).func_185904_a().func_76222_j() && SelfTrap.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(blockPos)).isEmpty();
    }
    
    public boolean canPlaceBlock(final BlockPos blockPos) {
        return this.canHaveBlock(blockPos) && this.haveCanBeClickedNeighbour(blockPos);
    }
    
    public boolean haveCanBeClickedNeighbour(final BlockPos blockPos) {
        final Vec3d eyesPos = new Vec3d(SelfTrap.mc.field_71439_g.field_70165_t, SelfTrap.mc.field_71439_g.field_70163_u + SelfTrap.mc.field_71439_g.func_70047_e(), SelfTrap.mc.field_71439_g.field_70161_v);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = blockPos.func_177972_a(side);
            final EnumFacing side2 = side.func_176734_d();
            if (BlockInteractionHelper.canBeClicked(neighbor)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(side2.func_176730_m()).func_186678_a(0.5));
                if (eyesPos.func_72436_e(hitVec) <= 36.0) {
                    return true;
                }
            }
        }
        return false;
    }
}
