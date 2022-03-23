// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import net.minecraft.client.Minecraft;
import java.util.List;
import meow.candycat.uwu.command.Command;
import net.minecraft.item.ItemShulkerBox;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.Timer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import java.util.function.Predicate;
import meow.candycat.uwu.module.ModuleManager;
import net.minecraft.util.math.Vec3d;
import net.minecraft.init.Blocks;
import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Comparator;
import net.minecraft.entity.Entity;
import meow.candycat.uwu.util.Friends;
import java.util.Collection;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.event.events.EventPlayerPostMotionUpdate;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.EventPlayerPreMotionUpdateForPacketMine;
import meow.candycat.eventsystem.listener.Listener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "Elevator", category = Category.COMBAT)
public class Elevator extends Module
{
    public Setting<Integer> delay;
    int shulker;
    BlockPos placeBlockOffset;
    boolean shouldDisable;
    int ii;
    EntityPlayer target;
    @EventHandler
    private Listener<EventPlayerPreMotionUpdateForPacketMine> awa;
    @EventHandler
    private Listener<EventPlayerPostMotionUpdate> listener;
    
    public Elevator() {
        this.delay = this.register(Settings.i("OpenDelay", 100));
        this.shulker = -1;
        this.placeBlockOffset = null;
        this.shouldDisable = false;
        this.ii = 0;
        this.target = null;
        ArrayList<Object> entities;
        final Iterator<EntityPlayer> iterator;
        EntityPlayer entity;
        BlockPos originalPos;
        Vec3d[] offset;
        Vec3d[] offset2;
        BlockPos a;
        boolean b;
        int i;
        Vec3d vecOffset;
        final BlockPos blockPos;
        BlockPos offset3;
        int j;
        Vec3d vecOffset2;
        final BlockPos blockPos2;
        BlockPos offset4;
        int holeBlocks;
        Vec3d[] holeOffset;
        BlockPos a2;
        boolean b2;
        final Vec3d[] array;
        int length;
        int n = 0;
        Vec3d vecOffset3;
        final BlockPos blockPos3;
        BlockPos offset5;
        Vec3d[] offset6;
        Vec3d[] offset7;
        BlockPos a3;
        boolean b3;
        int k;
        Vec3d vecOffset4;
        final BlockPos blockPos4;
        BlockPos offset8;
        int l;
        Vec3d vecOffset5;
        final BlockPos blockPos5;
        BlockPos offset9;
        this.awa = new Listener<EventPlayerPreMotionUpdateForPacketMine>(event -> {
            if (this.shouldDisable) {
                return;
            }
            else {
                entities = new ArrayList<Object>((Collection<?>)Elevator.mc.field_71441_e.field_73010_i.stream().filter(e -> !Friends.isFriend(((EntityPlayer)e).func_70005_c_()) && e != Elevator.mc.field_71439_g && ((EntityPlayer)e).func_110143_aJ() > 0.0f && !((EntityPlayer)e).field_70128_L).sorted(Comparator.comparing(e -> e.func_70032_d((Entity)Elevator.mc.field_71439_g))).collect(Collectors.toList()));
                entities.iterator();
                while (iterator.hasNext()) {
                    entity = iterator.next();
                    originalPos = new BlockPos(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
                    if (!Elevator.mc.field_71441_e.func_180495_p(originalPos).func_177230_c().equals(Blocks.field_150343_Z) && !Elevator.mc.field_71441_e.func_180495_p(originalPos).func_177230_c().equals(Blocks.field_150357_h)) {
                        continue;
                    }
                    else {
                        offset = new Vec3d[] { entity.func_174791_d().func_72441_c(1.0, 0.0, 0.0), entity.func_174791_d().func_72441_c(-1.0, 0.0, 0.0) };
                        offset2 = new Vec3d[] { entity.func_174791_d().func_72441_c(0.0, 0.0, 1.0), entity.func_174791_d().func_72441_c(0.0, 0.0, -1.0) };
                        a = new BlockPos(entity.func_174791_d().field_72450_a, entity.func_174791_d().field_72448_b, entity.func_174791_d().field_72449_c);
                        b = false;
                        if (Elevator.mc.field_71441_e.func_180495_p(a).func_177230_c() == Blocks.field_150477_bB) {
                            b = true;
                        }
                        i = 0;
                        while (i < 1) {
                            vecOffset = offset[i];
                            new BlockPos(vecOffset.field_72450_a, b ? (vecOffset.field_72448_b + 1.0) : vecOffset.field_72448_b, vecOffset.field_72449_c);
                            offset3 = blockPos;
                            if (Elevator.mc.field_71441_e.func_180495_p(offset3).func_185904_a().func_76222_j()) {
                                break;
                            }
                            else if (!Elevator.mc.field_71441_e.func_180495_p(offset3.func_177984_a()).func_185904_a().func_76222_j()) {
                                break;
                            }
                            else {
                                this.target = entity;
                                this.placeBlockOffset = offset3;
                                this.ii = 1;
                                ++i;
                            }
                        }
                        j = 0;
                        while (j < 1) {
                            vecOffset2 = offset2[j];
                            new BlockPos(vecOffset2.field_72450_a, b ? (vecOffset2.field_72448_b + 1.0) : vecOffset2.field_72448_b, vecOffset2.field_72449_c);
                            offset4 = blockPos2;
                            if (Elevator.mc.field_71441_e.func_180495_p(offset4).func_185904_a().func_76222_j()) {
                                break;
                            }
                            else if (!Elevator.mc.field_71441_e.func_180495_p(offset4.func_177984_a()).func_185904_a().func_76222_j()) {
                                break;
                            }
                            else {
                                this.target = entity;
                                this.placeBlockOffset = offset4;
                                this.ii = 2;
                                ++j;
                            }
                        }
                        if (this.target != null) {
                            break;
                        }
                        else {
                            holeBlocks = 0;
                            holeOffset = new Vec3d[] { Elevator.mc.field_71439_g.func_174791_d().func_72441_c(1.0, 0.0, 0.0), Elevator.mc.field_71439_g.func_174791_d().func_72441_c(-1.0, 0.0, 0.0), Elevator.mc.field_71439_g.func_174791_d().func_72441_c(0.0, 0.0, 1.0), Elevator.mc.field_71439_g.func_174791_d().func_72441_c(0.0, 0.0, -1.0) };
                            a2 = new BlockPos(entity.func_174791_d().field_72450_a, entity.func_174791_d().field_72448_b, entity.func_174791_d().field_72449_c);
                            b2 = false;
                            if (Elevator.mc.field_71441_e.func_180495_p(a2).func_177230_c() == Blocks.field_150477_bB) {
                                b2 = true;
                            }
                            for (length = array.length; n < length; ++n) {
                                vecOffset3 = array[n];
                                new BlockPos(vecOffset3.field_72450_a, b2 ? (vecOffset3.field_72448_b + 1.0) : vecOffset3.field_72448_b, vecOffset3.field_72449_c);
                                offset5 = blockPos3;
                                if (Elevator.mc.field_71441_e.func_180495_p(offset5).func_177230_c() == Blocks.field_150343_Z || Elevator.mc.field_71441_e.func_180495_p(offset5).func_177230_c() == Blocks.field_150477_bB || Elevator.mc.field_71441_e.func_180495_p(offset5).func_177230_c() == Blocks.field_150357_h) {
                                    ++holeBlocks;
                                }
                            }
                            if (holeBlocks < 5) {
                                continue;
                            }
                            else {
                                offset6 = new Vec3d[] { entity.func_174791_d().func_72441_c(1.0, 0.0, 0.0), entity.func_174791_d().func_72441_c(-1.0, 0.0, 0.0) };
                                offset7 = new Vec3d[] { entity.func_174791_d().func_72441_c(0.0, 0.0, 1.0), entity.func_174791_d().func_72441_c(0.0, 0.0, -1.0) };
                                a3 = new BlockPos(entity.func_174791_d().field_72450_a, entity.func_174791_d().field_72448_b, entity.func_174791_d().field_72449_c);
                                b3 = false;
                                if (Elevator.mc.field_71441_e.func_180495_p(a3).func_177230_c() == Blocks.field_150477_bB) {
                                    b3 = true;
                                }
                                k = 0;
                                while (k < 1) {
                                    vecOffset4 = offset6[k];
                                    new BlockPos(vecOffset4.field_72450_a, b3 ? (vecOffset4.field_72448_b + 1.0) : vecOffset4.field_72448_b, vecOffset4.field_72449_c);
                                    offset8 = blockPos4;
                                    if (Elevator.mc.field_71441_e.func_180495_p(offset8).func_185904_a().func_76222_j()) {
                                        break;
                                    }
                                    else if (!Elevator.mc.field_71441_e.func_180495_p(offset8.func_177984_a()).func_185904_a().func_76222_j()) {
                                        break;
                                    }
                                    else {
                                        this.target = entity;
                                        this.placeBlockOffset = offset8;
                                        this.ii = 1;
                                        ++k;
                                    }
                                }
                                l = 0;
                                while (l < 1) {
                                    vecOffset5 = offset7[l];
                                    new BlockPos(vecOffset5.field_72450_a, b3 ? (vecOffset5.field_72448_b + 1.0) : vecOffset5.field_72448_b, vecOffset5.field_72449_c);
                                    offset9 = blockPos5;
                                    if (Elevator.mc.field_71441_e.func_180495_p(offset9).func_185904_a().func_76222_j()) {
                                        break;
                                    }
                                    else if (!Elevator.mc.field_71441_e.func_180495_p(offset9.func_177984_a()).func_185904_a().func_76222_j()) {
                                        break;
                                    }
                                    else {
                                        this.target = entity;
                                        this.placeBlockOffset = offset9;
                                        this.ii = 2;
                                        ++l;
                                    }
                                }
                                if (this.target != null) {
                                    break;
                                }
                                else {
                                    continue;
                                }
                            }
                        }
                    }
                }
                if (this.target != null) {
                    if (this.ii == 1) {
                        ((Aimbot)ModuleManager.getModuleByName("Aimbot")).setRotation(89.0f, Elevator.mc.field_71439_g.field_70125_A);
                    }
                    else {
                        ((Aimbot)ModuleManager.getModuleByName("Aimbot")).setRotation(-1.0f, Elevator.mc.field_71439_g.field_70125_A);
                    }
                }
                return;
            }
        }, (Predicate<EventPlayerPreMotionUpdateForPacketMine>[])new Predicate[0]);
        int oldSlot;
        boolean switched;
        Vec3d vec;
        float f;
        float f2;
        float f3;
        BlockPos finalPlaceBlockOffset;
        this.listener = new Listener<EventPlayerPostMotionUpdate>(event -> {
            if (this.target != null) {
                oldSlot = -1;
                switched = false;
                if (Elevator.mc.field_71439_g.field_71071_by.field_70461_c != this.shulker) {
                    oldSlot = Elevator.mc.field_71439_g.field_71071_by.field_70461_c;
                    Elevator.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.shulker));
                    switched = true;
                }
                vec = new Vec3d((Vec3i)this.placeBlockOffset).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(EnumFacing.DOWN.func_176730_m()).func_186678_a(0.5));
                f = (float)(vec.field_72450_a - this.placeBlockOffset.func_177958_n());
                f2 = (float)(vec.field_72448_b - this.placeBlockOffset.func_177956_o());
                f3 = (float)(vec.field_72449_c - this.placeBlockOffset.func_177952_p());
                Elevator.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                Elevator.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(this.placeBlockOffset, EnumFacing.UP, EnumHand.MAIN_HAND, f, f2, f3));
                finalPlaceBlockOffset = this.placeBlockOffset;
                new Timer().schedule(new TimerTask() {
                    final /* synthetic */ BlockPos val$finalPlaceBlockOffset;
                    final /* synthetic */ float val$f;
                    final /* synthetic */ float val$f1;
                    final /* synthetic */ float val$f2;
                    
                    @Override
                    public void run() {
                        Elevator.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(this.val$finalPlaceBlockOffset.func_177984_a(), EnumFacing.UP, EnumHand.MAIN_HAND, this.val$f, this.val$f1 - 1.0f, this.val$f2));
                    }
                }, this.delay.getValue());
                if (switched) {
                    Elevator.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(oldSlot));
                }
                this.shouldDisable = true;
                ((Aimbot)ModuleManager.getModuleByName("Aimbot")).resetRotation();
            }
        }, (Predicate<EventPlayerPostMotionUpdate>[])new Predicate[0]);
    }
    
    @Override
    public void onUpdate() {
        if (this.shouldDisable) {
            this.shouldDisable = false;
            this.disable();
        }
    }
    
    public void onEnable() {
        this.shulker = -1;
        this.shouldDisable = false;
        this.target = null;
        if (Elevator.mc.field_71439_g == null || Elevator.mc.field_71441_e == null) {
            this.disable();
            return;
        }
        for (int i = 0; i < 9; ++i) {
            if (Elevator.mc.field_71439_g.field_71071_by.func_70301_a(i) != Elevator.mc.field_71439_g.func_184592_cb()) {
                if (Elevator.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() instanceof ItemShulkerBox) {
                    this.shulker = i;
                }
            }
        }
        if (this.shulker == -1) {
            this.disable();
            Command.sendChatMessage("[Elevator] No Shulker, disabling");
        }
    }
}
