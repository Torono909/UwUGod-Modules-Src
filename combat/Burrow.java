// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.math.MathHelper;
import net.minecraft.block.state.IBlockState;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import java.util.Iterator;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import meow.candycat.uwu.command.Command;
import net.minecraft.block.BlockObsidian;
import net.minecraft.init.Blocks;
import meow.candycat.uwu.setting.Settings;
import net.minecraft.util.math.BlockPos;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "Burrow", category = Category.COMBAT)
public class Burrow extends Module
{
    private Setting<mode> Mode;
    private Setting<Boolean> rotate;
    private Setting<Float> offset;
    private Setting<Integer> timeoutTick;
    private Setting<Boolean> bypass;
    private BlockPos originalPos;
    private int oldSlot;
    public double originalY;
    boolean jumped;
    int tickPassed;
    int stage;
    int ticks;
    
    public Burrow() {
        this.Mode = this.register(Settings.e("Mode", mode.INSTANT));
        this.rotate = this.register(Settings.b("Rotate", true));
        this.offset = this.register(Settings.f("Offset", 7.0f));
        this.timeoutTick = this.register(Settings.i("TimeoutTicks", 12));
        this.bypass = this.register(Settings.b("Bypass"));
        this.oldSlot = -1;
        this.jumped = false;
        this.tickPassed = 0;
        this.stage = 0;
        this.ticks = 0;
    }
    
    public void onEnable() {
        super.onEnable();
        this.originalPos = new BlockPos(Burrow.mc.field_71439_g.field_70165_t, Burrow.mc.field_71439_g.field_70163_u, Burrow.mc.field_71439_g.field_70161_v);
        if (Burrow.mc.field_71441_e.func_180495_p(new BlockPos(Burrow.mc.field_71439_g.field_70165_t, Burrow.mc.field_71439_g.field_70163_u, Burrow.mc.field_71439_g.field_70161_v)).func_177230_c().equals(Blocks.field_150343_Z) || this.intersectsWithEntity(this.originalPos)) {
            this.toggle();
            return;
        }
        this.stage = 0;
        this.jumped = false;
        this.tickPassed = 0;
        this.ticks = 0;
    }
    
    @Override
    public void onUpdate() {
        if (findHotbarBlock(BlockObsidian.class) == -1) {
            Command.sendChatMessage("Can't find obsidian in hotbar!");
            this.toggle();
            return;
        }
        if (this.tickPassed == this.timeoutTick.getValue() * 2) {
            this.disable();
            return;
        }
        ++this.tickPassed;
        if (this.ticks++ % 2 > 0) {
            return;
        }
        if (this.Mode.getValue() == mode.INSTANT) {
            switch (this.stage) {
                case 0: {
                    this.oldSlot = Burrow.mc.field_71439_g.field_71071_by.field_70461_c;
                    switchToSlot(findHotbarBlock(BlockObsidian.class));
                    Burrow.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Burrow.mc.field_71439_g.field_70165_t, Burrow.mc.field_71439_g.field_70163_u + 0.41999998688698, Burrow.mc.field_71439_g.field_70161_v, Burrow.mc.field_71439_g.field_70122_E));
                    Burrow.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Burrow.mc.field_71439_g.field_70165_t, Burrow.mc.field_71439_g.field_70163_u + 0.7531999805211997, Burrow.mc.field_71439_g.field_70161_v, Burrow.mc.field_71439_g.field_70122_E));
                    Burrow.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Burrow.mc.field_71439_g.field_70165_t, Burrow.mc.field_71439_g.field_70163_u + 1.00133597911214, Burrow.mc.field_71439_g.field_70161_v, Burrow.mc.field_71439_g.field_70122_E));
                    Burrow.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Burrow.mc.field_71439_g.field_70165_t, Burrow.mc.field_71439_g.field_70163_u + 1.16610926093821, Burrow.mc.field_71439_g.field_70161_v, Burrow.mc.field_71439_g.field_70122_E));
                    ++this.stage;
                    break;
                }
                case 1: {
                    placeBlock(this.originalPos, EnumHand.MAIN_HAND, this.rotate.getValue(), true, false);
                    placeBlock(this.originalPos, EnumHand.MAIN_HAND, false, true, false);
                    Burrow.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Burrow.mc.field_71439_g.field_70165_t, Burrow.mc.field_71439_g.field_70163_u + this.offset.getValue(), Burrow.mc.field_71439_g.field_70161_v, Burrow.mc.field_71439_g.field_70122_E));
                    Burrow.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Burrow.mc.field_71439_g.field_70165_t, Burrow.mc.field_71439_g.field_70163_u, Burrow.mc.field_71439_g.field_70161_v, Burrow.mc.field_71439_g.field_70122_E));
                    switchToSlot(this.oldSlot);
                    if (this.bypass.getValue() && !Burrow.mc.field_71439_g.func_70093_af()) {
                        Burrow.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Burrow.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
                        Burrow.mc.field_71439_g.func_70095_a(true);
                        Burrow.mc.field_71442_b.func_78765_e();
                        Burrow.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Burrow.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
                        Burrow.mc.field_71439_g.func_70095_a(false);
                        Burrow.mc.field_71442_b.func_78765_e();
                    }
                    this.toggle();
                    break;
                }
            }
        }
        else {
            if (!this.jumped) {
                this.originalY = Burrow.mc.field_71439_g.field_70163_u;
                Burrow.mc.field_71439_g.func_70664_aZ();
                this.jumped = true;
            }
            if (this.tickPassed == 8) {
                this.oldSlot = Burrow.mc.field_71439_g.field_71071_by.field_70461_c;
                switchToSlot(findHotbarBlock(BlockObsidian.class));
                placeBlock(this.originalPos, EnumHand.MAIN_HAND, this.rotate.getValue(), true, false);
                Burrow.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Burrow.mc.field_71439_g.field_70165_t, Burrow.mc.field_71439_g.field_70163_u + this.offset.getValue(), Burrow.mc.field_71439_g.field_70161_v, false));
                switchToSlot(this.oldSlot);
                if (this.bypass.getValue() && !Burrow.mc.field_71439_g.func_70093_af()) {
                    Burrow.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Burrow.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
                    Burrow.mc.field_71439_g.func_70095_a(true);
                    Burrow.mc.field_71442_b.func_78765_e();
                    Burrow.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Burrow.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
                    Burrow.mc.field_71439_g.func_70095_a(false);
                    Burrow.mc.field_71442_b.func_78765_e();
                }
                this.toggle();
            }
        }
    }
    
    private boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : Burrow.mc.field_71441_e.field_72996_f) {
            if (entity.equals((Object)Burrow.mc.field_71439_g)) {
                continue;
            }
            if (entity instanceof EntityEnderCrystal) {
                continue;
            }
            if (entity instanceof EntityItem) {
                continue;
            }
            if (new AxisAlignedBB(pos).func_72326_a(entity.func_174813_aQ())) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean placeBlock(final BlockPos pos, final EnumHand hand, final boolean rotate, final boolean packet, final boolean isSneaking) {
        boolean sneaking = false;
        final EnumFacing side = getFirstFacing(pos);
        if (side == null) {
            return isSneaking;
        }
        final BlockPos neighbour = pos.func_177972_a(side);
        final EnumFacing opposite = side.func_176734_d();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(opposite.func_176730_m()).func_186678_a(0.5));
        final Block neighbourBlock = Burrow.mc.field_71441_e.func_180495_p(neighbour).func_177230_c();
        if (!Burrow.mc.field_71439_g.func_70093_af()) {
            Burrow.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Burrow.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
            Burrow.mc.field_71439_g.func_70095_a(true);
            sneaking = true;
        }
        if (rotate) {
            faceVector(hitVec, true);
        }
        rightClickBlock(neighbour, hitVec, hand, opposite, packet);
        Burrow.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
        Burrow.mc.field_71467_ac = 4;
        return sneaking || isSneaking;
    }
    
    public static List<EnumFacing> getPossibleSides(final BlockPos pos) {
        final List<EnumFacing> facings = new ArrayList<EnumFacing>();
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.func_177972_a(side);
            if (Burrow.mc.field_71441_e.func_180495_p(neighbour).func_177230_c().func_176209_a(Burrow.mc.field_71441_e.func_180495_p(neighbour), false)) {
                final IBlockState blockState = Burrow.mc.field_71441_e.func_180495_p(neighbour);
                if (!blockState.func_185904_a().func_76222_j()) {
                    facings.add(side);
                }
            }
        }
        return facings;
    }
    
    @Override
    public String getHudInfo() {
        return (this.Mode.getValue() == mode.INSTANT) ? "Instant" : "Jump";
    }
    
    public static EnumFacing getFirstFacing(final BlockPos pos) {
        final Iterator<EnumFacing> iterator = getPossibleSides(pos).iterator();
        if (iterator.hasNext()) {
            final EnumFacing facing = iterator.next();
            return facing;
        }
        return null;
    }
    
    public static Vec3d getEyesPos() {
        return new Vec3d(Burrow.mc.field_71439_g.field_70165_t, Burrow.mc.field_71439_g.field_70163_u + Burrow.mc.field_71439_g.func_70047_e(), Burrow.mc.field_71439_g.field_70161_v);
    }
    
    public static float[] getLegitRotations(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.field_72450_a - eyesPos.field_72450_a;
        final double diffY = vec.field_72448_b - eyesPos.field_72448_b;
        final double diffZ = vec.field_72449_c - eyesPos.field_72449_c;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { Burrow.mc.field_71439_g.field_70177_z + MathHelper.func_76142_g(yaw - Burrow.mc.field_71439_g.field_70177_z), Burrow.mc.field_71439_g.field_70125_A + MathHelper.func_76142_g(pitch - Burrow.mc.field_71439_g.field_70125_A) };
    }
    
    public static void faceVector(final Vec3d vec, final boolean normalizeAngle) {
        final float[] rotations = getLegitRotations(vec);
        Burrow.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(rotations[0], normalizeAngle ? ((float)MathHelper.func_180184_b((int)rotations[1], 360)) : rotations[1], Burrow.mc.field_71439_g.field_70122_E));
    }
    
    public static void rightClickBlock(final BlockPos pos, final Vec3d vec, final EnumHand hand, final EnumFacing direction, final boolean packet) {
        if (packet) {
            final float f = (float)(vec.field_72450_a - pos.func_177958_n());
            final float f2 = (float)(vec.field_72448_b - pos.func_177956_o());
            final float f3 = (float)(vec.field_72449_c - pos.func_177952_p());
            Burrow.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f2, f3));
        }
        else {
            Burrow.mc.field_71442_b.func_187099_a(Burrow.mc.field_71439_g, Burrow.mc.field_71441_e, pos, direction, vec, hand);
        }
        Burrow.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
        Burrow.mc.field_71467_ac = 4;
    }
    
    public static int findHotbarBlock(final Class clazz) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Burrow.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack != ItemStack.field_190927_a) {
                if (clazz.isInstance(stack.func_77973_b())) {
                    return i;
                }
                if (stack.func_77973_b() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
                    if (clazz.isInstance(block)) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    
    public static void switchToSlot(final int slot) {
        Burrow.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot));
        Burrow.mc.field_71439_g.field_71071_by.field_70461_c = slot;
        Burrow.mc.field_71442_b.func_78765_e();
    }
    
    public enum mode
    {
        JUMP, 
        INSTANT;
    }
}
