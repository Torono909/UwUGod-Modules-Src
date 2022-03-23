// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.inventory.ClickType;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3i;
import java.util.Comparator;
import meow.candycat.uwu.util.Friends;
import net.minecraft.block.Block;
import meow.candycat.uwu.command.Command;
import meow.candycat.uwu.util.BlockInteractionHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.EntityLivingBase;
import java.util.Collection;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import meow.candycat.uwu.util.EntityUtil;
import java.util.ArrayList;
import java.util.List;
import meow.candycat.uwu.setting.Settings;
import net.minecraft.util.EnumFacing;
import meow.candycat.uwu.setting.Setting;
import net.minecraft.util.math.BlockPos;
import meow.candycat.uwu.module.Module;

@Info(name = "Dispenser32k", category = Category.COMBAT)
public class Dispenser32k extends Module
{
    private int hopperSlot;
    private int redstoneSlot;
    private int shulkerSlot;
    private int dispenserSlot;
    private int obiSlot;
    private int stage;
    private int time;
    private BlockPos placeTarget;
    private Setting<Double> placerange;
    private Setting<Boolean> placeclosetoenemy;
    private Setting<Boolean> hopperWait;
    private EnumFacing q;
    private EnumFacing f;
    private int delay;
    private int delaycount;
    
    public Dispenser32k() {
        this.placerange = this.register(Settings.d("PlaceRange", 4.5));
        this.placeclosetoenemy = this.register(Settings.b("Place Close To Enemy"));
        this.hopperWait = this.register(Settings.b("HopperWait"));
    }
    
    public List<BlockPos> getPlaceableBlocks() {
        final List<BlockPos> toreturn = new ArrayList<BlockPos>();
        for (final BlockPos w : this.getSphere(new BlockPos(Dispenser32k.mc.field_71439_g.field_70165_t, Dispenser32k.mc.field_71439_g.field_70163_u + 1.0, Dispenser32k.mc.field_71439_g.field_70161_v), this.placerange.getValue().floatValue(), 4, false, true, 0)) {
            final double[] lookat = EntityUtil.calculateLookAt(w.field_177962_a, w.field_177960_b + 1, w.field_177961_c, (EntityPlayer)Dispenser32k.mc.field_71439_g);
            final double lookatyaw = (lookat[0] + 45.0 > 360.0) ? (lookat[0] - 360.0) : lookat[0];
            final float yaw = (float)lookatyaw;
            boolean isNegative = false;
            if (yaw < 0.0f) {
                isNegative = true;
            }
            final int dir = Math.round(Math.abs(yaw)) % 360;
            EnumFacing f;
            if (135 < dir && dir < 225) {
                f = EnumFacing.SOUTH;
            }
            else if (225 < dir && dir < 315) {
                if (isNegative) {
                    f = EnumFacing.EAST;
                }
                else {
                    f = EnumFacing.WEST;
                }
            }
            else if (45 < dir && dir < 135) {
                if (isNegative) {
                    f = EnumFacing.WEST;
                }
                else {
                    f = EnumFacing.EAST;
                }
            }
            else {
                f = EnumFacing.NORTH;
            }
            if (this.isAreaPlaceable(w, f)) {
                toreturn.add(w);
            }
        }
        return toreturn;
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
    
    private boolean isAreaPlaceable(final BlockPos blockPos, final EnumFacing f) {
        for (final EntityPlayer w : Dispenser32k.mc.field_71441_e.field_73010_i) {
            if (Math.sqrt(w.func_70092_e((double)blockPos.field_177962_a, Dispenser32k.mc.field_71439_g.field_70163_u, (double)blockPos.field_177961_c)) <= 2.0) {
                return false;
            }
        }
        final List<Entity> entityList = new ArrayList<Entity>();
        entityList.addAll(Dispenser32k.mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(blockPos)));
        entityList.addAll(Dispenser32k.mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(blockPos.func_177982_a(0, 1, 0))));
        entityList.addAll(Dispenser32k.mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(blockPos.func_177982_a(0, 2, 0))));
        entityList.addAll(Dispenser32k.mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(blockPos.func_177982_a(0, 2, 0))));
        entityList.addAll(Dispenser32k.mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(blockPos.func_177972_a(f))));
        entityList.addAll(Dispenser32k.mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(blockPos.func_177982_a(0, 1, 0).func_177972_a(f))));
        for (final Entity entity : entityList) {
            if (entity instanceof EntityLivingBase) {
                return false;
            }
        }
        return Dispenser32k.mc.field_71441_e.func_180495_p(blockPos).func_185904_a().func_76222_j() && Dispenser32k.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 1, 0)).func_185904_a().func_76222_j() && (Math.abs(blockPos.field_177960_b + 1 - Dispenser32k.mc.field_71439_g.field_70163_u) < 2.0 || Math.sqrt(Dispenser32k.mc.field_71439_g.func_70092_e((double)blockPos.field_177962_a, Dispenser32k.mc.field_71439_g.field_70163_u, (double)blockPos.field_177961_c)) > 2.0) && Dispenser32k.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 2, 0)).func_185904_a().func_76222_j() && Dispenser32k.mc.field_71441_e.func_180495_p(blockPos.func_177972_a(f)).func_185904_a().func_76222_j() && !(Dispenser32k.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, -1, 0)).func_177230_c() instanceof BlockAir) && !(Dispenser32k.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, -1, 0)).func_177230_c() instanceof BlockLiquid) && Dispenser32k.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 1, 0).func_177972_a(f)).func_185904_a().func_76222_j();
    }
    
    public void onEnable() {
        final int hopperSlot = -1;
        this.obiSlot = hopperSlot;
        this.dispenserSlot = hopperSlot;
        this.shulkerSlot = hopperSlot;
        this.redstoneSlot = hopperSlot;
        this.hopperSlot = hopperSlot;
        for (int i = 0; i < 9 && (this.obiSlot == -1 || this.dispenserSlot == -1 || this.shulkerSlot == -1 || this.redstoneSlot == -1 || this.hopperSlot == -1); ++i) {
            final ItemStack stack = Dispenser32k.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack != ItemStack.field_190927_a && stack.func_77973_b() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
                if (block == Blocks.field_150438_bZ) {
                    this.hopperSlot = i;
                }
                else if (BlockInteractionHelper.shulkerList.contains(block)) {
                    this.shulkerSlot = i;
                }
                else if (block == Blocks.field_150343_Z) {
                    this.obiSlot = i;
                }
                else if (block == Blocks.field_150367_z) {
                    this.dispenserSlot = i;
                }
                else if (block == Blocks.field_150451_bX) {
                    this.redstoneSlot = i;
                }
            }
        }
        if (this.obiSlot == -1 || this.dispenserSlot == -1 || this.shulkerSlot == -1 || this.redstoneSlot == -1 || this.hopperSlot == -1) {
            Command.sendChatMessage("[Dispenser32k] Item missing, disabling.");
            this.disable();
        }
        this.stage = 0;
    }
    
    @Override
    public void onUpdate() {
        switch (this.stage) {
            case 0: {
                this.delay = 10;
                this.delaycount = 0;
                final List<BlockPos> canPlaceLocation = this.getPlaceableBlocks();
                if (this.placeclosetoenemy.getValue()) {
                    final EntityPlayer targetPlayer = (EntityPlayer)Dispenser32k.mc.field_71441_e.field_73010_i.stream().filter(e -> e != Dispenser32k.mc.field_71439_g && !Friends.isFriend(e.func_70005_c_())).min(Comparator.comparing(e -> Dispenser32k.mc.field_71439_g.func_70032_d(e))).orElse(null);
                    this.placeTarget = ((targetPlayer != null) ? canPlaceLocation.stream().min(Comparator.comparing(e -> BlockInteractionHelper.blockDistance(e.field_177962_a, e.field_177960_b, e.field_177961_c, (Entity)targetPlayer))).orElse(null) : canPlaceLocation.stream().max(Comparator.comparing(e -> BlockInteractionHelper.blockDistance(e.field_177962_a, e.field_177960_b, e.field_177961_c, (Entity)Dispenser32k.mc.field_71439_g))).orElse(null));
                }
                else {
                    this.placeTarget = canPlaceLocation.stream().max(Comparator.comparing(e -> BlockInteractionHelper.blockDistance(e.field_177962_a, e.field_177960_b, e.field_177961_c, (Entity)Dispenser32k.mc.field_71439_g))).orElse(null);
                }
                if (this.placeTarget == null) {
                    Command.sendChatMessage("[Dispenser32k] No suitable place to place, disabling");
                    this.disable();
                    break;
                }
                Dispenser32k.mc.field_71439_g.field_71071_by.field_70461_c = this.obiSlot;
                BlockInteractionHelper.placeBlockScaffold(new BlockPos((Vec3i)this.placeTarget));
                final double[] lookat = EntityUtil.calculateLookAt(this.placeTarget.field_177962_a, this.placeTarget.field_177960_b + 1, this.placeTarget.field_177961_c, (EntityPlayer)Dispenser32k.mc.field_71439_g);
                final double lookatyaw = (lookat[0] + 45.0 > 360.0) ? (lookat[0] - 360.0) : lookat[0];
                final float yaw = (float)lookatyaw;
                boolean isNegative = false;
                if (yaw < 0.0f) {
                    isNegative = true;
                }
                final int dir = Math.round(Math.abs(yaw)) % 360;
                if (135 < dir && dir < 225) {
                    this.f = EnumFacing.SOUTH;
                }
                else if (225 < dir && dir < 315) {
                    if (isNegative) {
                        this.f = EnumFacing.EAST;
                    }
                    else {
                        this.f = EnumFacing.WEST;
                    }
                }
                else if (45 < dir && dir < 135) {
                    if (isNegative) {
                        this.f = EnumFacing.WEST;
                    }
                    else {
                        this.f = EnumFacing.EAST;
                    }
                }
                else {
                    this.f = EnumFacing.NORTH;
                }
                Dispenser32k.mc.field_71439_g.field_71071_by.field_70461_c = this.dispenserSlot;
                BlockInteractionHelper.placeBlockScaffold(new BlockPos((Vec3i)this.placeTarget.func_177982_a(0, 1, 0)));
                Dispenser32k.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(this.placeTarget.func_177982_a(0, 1, 0), EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                ++this.stage;
                break;
            }
            case 1: {
                if (Dispenser32k.mc.field_71462_r instanceof GuiContainer) {
                    Dispenser32k.mc.field_71442_b.func_187098_a(Dispenser32k.mc.field_71439_g.field_71070_bA.field_75152_c, 1, this.shulkerSlot, ClickType.SWAP, (EntityPlayer)Dispenser32k.mc.field_71439_g);
                    Dispenser32k.mc.field_71439_g.func_71053_j();
                    ++this.stage;
                    break;
                }
                break;
            }
            case 2: {
                Dispenser32k.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Dispenser32k.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
                Dispenser32k.mc.field_71439_g.field_71071_by.field_70461_c = this.redstoneSlot;
                BlockInteractionHelper.placeBlockScaffold(new BlockPos((Vec3i)this.placeTarget.func_177982_a(0, 2, 0)));
                Dispenser32k.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Dispenser32k.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
                ++this.stage;
                break;
            }
            case 3: {
                if (!this.hopperWait.getValue()) {
                    Dispenser32k.mc.field_71439_g.field_71071_by.field_70461_c = this.hopperSlot;
                    BlockInteractionHelper.placeBlockScaffold(new BlockPos((Vec3i)this.placeTarget.func_177972_a(this.f)));
                    Dispenser32k.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(this.placeTarget.func_177972_a(this.f), EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                    Dispenser32k.mc.field_71439_g.field_71071_by.field_70461_c = this.shulkerSlot;
                    this.stage = 0;
                    this.disable();
                    break;
                }
                if (this.delaycount >= this.delay) {
                    Dispenser32k.mc.field_71439_g.field_71071_by.field_70461_c = this.hopperSlot;
                    BlockInteractionHelper.placeBlockScaffold(new BlockPos((Vec3i)this.placeTarget.func_177972_a(this.f)));
                    Dispenser32k.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(this.placeTarget.func_177972_a(this.f), EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                    Dispenser32k.mc.field_71439_g.field_71071_by.field_70461_c = this.shulkerSlot;
                    this.stage = 0;
                    this.disable();
                    break;
                }
                ++this.delaycount;
                break;
            }
        }
    }
}
