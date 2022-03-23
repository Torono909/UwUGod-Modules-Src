// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.player;

import net.minecraft.util.math.MathHelper;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumActionResult;
import meow.candycat.uwu.util.BlockInteractionHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import java.util.function.Predicate;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockContainer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import meow.candycat.uwu.util.Wrapper;
import meow.candycat.uwu.module.modules.combat.Aimbot;
import meow.candycat.uwu.module.modules.combat.KillAura;
import meow.candycat.uwu.module.ModuleManager;
import meow.candycat.uwu.module.modules.combat.AutoCrystal;
import java.util.Arrays;
import net.minecraft.init.Blocks;
import meow.candycat.uwu.event.events.EventPlayerPreMotionUpdateBeforeAimbot;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.EventPlayerPostMotionUpdate;
import meow.candycat.eventsystem.listener.Listener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.Block;
import java.util.List;
import meow.candycat.uwu.module.Module;

@Info(name = "Scaffold", category = Category.PLAYER)
public class Scaffold extends Module
{
    private List<Block> blackList;
    int resetTimer;
    private long time;
    public boolean rotated;
    int newSlot;
    BlockPos lastPos;
    @EventHandler
    private Listener<EventPlayerPostMotionUpdate> owo;
    @EventHandler
    private Listener<EventPlayerPreMotionUpdateBeforeAimbot> w;
    
    public Scaffold() {
        this.blackList = Arrays.asList(Blocks.field_150477_bB, (Block)Blocks.field_150486_ae, Blocks.field_150447_bR);
        this.rotated = false;
        this.newSlot = -1;
        this.lastPos = null;
        KillAura killAura;
        int i;
        ItemStack stack;
        Block block;
        int oldSlot;
        boolean y;
        KillAura killAura2;
        this.owo = new Listener<EventPlayerPostMotionUpdate>(event -> {
            if (this.lastPos == null) {
                ++this.resetTimer;
                if (this.resetTimer >= 10 && this.rotated && !((AutoCrystal)ModuleManager.getModuleByName("AutoCrystal")).rotated) {
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
                this.newSlot = -1;
                for (i = 0; i < 9; ++i) {
                    stack = Wrapper.getPlayer().field_71071_by.func_70301_a(i);
                    if (stack != ItemStack.field_190927_a && stack.func_77973_b() instanceof ItemBlock) {
                        block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
                        if (!this.blackList.contains(block) && !(block instanceof BlockContainer) && Block.func_149634_a(stack.func_77973_b()).func_176223_P().func_185913_b() && !(((ItemBlock)stack.func_77973_b()).func_179223_d() instanceof BlockFalling)) {
                            this.newSlot = i;
                            break;
                        }
                    }
                }
                if (this.newSlot == -1) {
                    return;
                }
                else {
                    oldSlot = Wrapper.getPlayer().field_71071_by.field_70461_c;
                    Wrapper.getPlayer().field_71071_by.field_70461_c = this.newSlot;
                    y = this.placeBlockScaffold(this.lastPos);
                    if (!y) {
                        ++this.resetTimer;
                        if (this.resetTimer >= 10 && this.rotated && !((AutoCrystal)ModuleManager.getModuleByName("AutoCrystal")).rotated) {
                            killAura2 = (KillAura)ModuleManager.getModuleByName("KillAura");
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
                    Wrapper.getPlayer().field_71071_by.field_70461_c = oldSlot;
                    return;
                }
            }
        }, (Predicate<EventPlayerPostMotionUpdate>[])new Predicate[0]);
        Vec3d vec3d;
        Vec3d vec3d2;
        BlockPos blockPos;
        BlockPos belowBlockPos;
        KillAura killAura3;
        int j;
        ItemStack stack2;
        Block block2;
        final EnumFacing[] array;
        int length;
        int k = 0;
        EnumFacing side;
        BlockPos neighbour;
        Vec3d eyesPos;
        final EnumFacing[] array2;
        int length2;
        int l = 0;
        EnumFacing side2;
        BlockPos neighbor;
        EnumFacing side3;
        Vec3d hitVec;
        final Vec3d vec3d3;
        Vec3d hitVec2;
        float[] w;
        this.w = new Listener<EventPlayerPreMotionUpdateBeforeAimbot>(event -> {
            if (Scaffold.mc.field_71439_g != null && !ModuleManager.isModuleEnabled("Freecam")) {
                this.lastPos = null;
                vec3d = getInterpolatedPos((Entity)Scaffold.mc.field_71439_g, 4.0f);
                vec3d2 = new Vec3d(vec3d.field_72450_a, Math.floor(Scaffold.mc.field_71439_g.field_70163_u), vec3d.field_72449_c);
                blockPos = new BlockPos(vec3d2).func_177977_b();
                belowBlockPos = blockPos.func_177977_b();
                if (!Wrapper.getWorld().func_180495_p(blockPos).func_185904_a().func_76222_j()) {
                    ++this.resetTimer;
                    if (this.resetTimer >= 10 && this.rotated && !((AutoCrystal)ModuleManager.getModuleByName("AutoCrystal")).rotated) {
                        killAura3 = (KillAura)ModuleManager.getModuleByName("KillAura");
                        if (!KillAura.isAiming) {
                            ((Aimbot)ModuleManager.getModuleByName("Aimbot")).resetRotation();
                            this.resetTimer = 0;
                            this.rotated = false;
                        }
                    }
                }
                else {
                    this.newSlot = -1;
                    for (j = 0; j < 9; ++j) {
                        stack2 = Wrapper.getPlayer().field_71071_by.func_70301_a(j);
                        if (stack2 != ItemStack.field_190927_a && stack2.func_77973_b() instanceof ItemBlock) {
                            block2 = ((ItemBlock)stack2.func_77973_b()).func_179223_d();
                            if (!this.blackList.contains(block2) && !(block2 instanceof BlockContainer) && Block.func_149634_a(stack2.func_77973_b()).func_176223_P().func_185913_b() && (!(((ItemBlock)stack2.func_77973_b()).func_179223_d() instanceof BlockFalling) || !Wrapper.getWorld().func_180495_p(belowBlockPos).func_185904_a().func_76222_j())) {
                                this.newSlot = j;
                                break;
                            }
                        }
                    }
                    if (this.newSlot != -1) {
                        this.lastPos = blockPos;
                        Label_0417_1: {
                            if (!this.hasNeighbour(blockPos)) {
                                EnumFacing.values();
                                length = array.length;
                                while (k < length) {
                                    side = array[k];
                                    neighbour = blockPos.func_177972_a(side);
                                    if (this.hasNeighbour(neighbour)) {
                                        blockPos = neighbour;
                                        this.lastPos = blockPos;
                                        break Label_0417_1;
                                    }
                                    else {
                                        ++k;
                                    }
                                }
                                return;
                            }
                        }
                        if (Scaffold.mc.field_71441_e.func_72872_a((Class)EntityPlayer.class, new AxisAlignedBB(blockPos)).isEmpty()) {
                            if (Scaffold.mc.field_71441_e.func_72872_a((Class)EntityEnderCrystal.class, new AxisAlignedBB(blockPos)).isEmpty()) {
                                eyesPos = new Vec3d(Wrapper.getPlayer().field_70165_t, Wrapper.getPlayer().field_70163_u + Wrapper.getPlayer().func_70047_e(), Wrapper.getPlayer().field_70161_v);
                                EnumFacing.values();
                                for (length2 = array2.length; l < length2; ++l) {
                                    side2 = array2[l];
                                    neighbor = blockPos.func_177972_a(side2);
                                    side3 = side2.func_176734_d();
                                    if (canBeClicked(neighbor)) {
                                        hitVec = new Vec3d((Vec3i)neighbor).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(side3.func_176730_m()).func_186678_a(0.5));
                                        if (eyesPos.func_72436_e(hitVec) <= 18.0625) {
                                            new Vec3d(hitVec.field_72450_a, side2.equals((Object)EnumFacing.UP) ? (hitVec.field_72448_b - 0.5) : (hitVec.field_72448_b + 0.5), hitVec.field_72449_c);
                                            hitVec2 = vec3d3;
                                            w = BlockInteractionHelper.getLegitRotations(hitVec2);
                                            ((Aimbot)ModuleManager.getModuleByName("Aimbot")).setRotation(w[0], w[1]);
                                            this.lastPos = blockPos;
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                        this.lastPos = null;
                    }
                }
            }
        }, (Predicate<EventPlayerPreMotionUpdateBeforeAimbot>[])new Predicate[0]);
    }
    
    private boolean hasNeighbour(final BlockPos blockPos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = blockPos.func_177972_a(side);
            if (!Wrapper.getWorld().func_180495_p(neighbour).func_185904_a().func_76222_j()) {
                return true;
            }
        }
        return false;
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double x, final double y, final double z) {
        return new Vec3d((entity.field_70165_t - entity.field_70142_S) * x, 0.0 * y, (entity.field_70161_v - entity.field_70136_U) * z);
    }
    
    public static Vec3d getInterpolatedPos(final Entity entity, final float ticks) {
        return new Vec3d(entity.field_70142_S, entity.field_70137_T, entity.field_70136_U).func_178787_e(getInterpolatedAmount(entity, ticks, ticks, ticks));
    }
    
    public boolean passedMs(final long ms) {
        return this.getMs(System.nanoTime() - this.time) >= ms;
    }
    
    public long getMs(final long time) {
        return time / 1000000L;
    }
    
    public void reset() {
        this.time = System.nanoTime();
    }
    
    public boolean placeBlockScaffold(final BlockPos pos) {
        if (Scaffold.mc.field_71441_e.func_72872_a((Class)EntityPlayer.class, new AxisAlignedBB(pos)).isEmpty() && Scaffold.mc.field_71441_e.func_72872_a((Class)EntityEnderCrystal.class, new AxisAlignedBB(pos)).isEmpty()) {
            final Vec3d eyesPos = new Vec3d(Wrapper.getPlayer().field_70165_t, Wrapper.getPlayer().field_70163_u + Wrapper.getPlayer().func_70047_e(), Wrapper.getPlayer().field_70161_v);
            for (final EnumFacing side : EnumFacing.values()) {
                final BlockPos neighbor = pos.func_177972_a(side);
                final EnumFacing side2 = side.func_176734_d();
                if (canBeClicked(neighbor)) {
                    final Vec3d hitVec = new Vec3d((Vec3i)neighbor).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(side2.func_176730_m()).func_186678_a(0.5));
                    if (eyesPos.func_72436_e(hitVec) <= 18.0625) {
                        final EnumActionResult x = processRightClickBlock(neighbor, side2, hitVec);
                        if (x.equals((Object)EnumActionResult.SUCCESS)) {
                            Scaffold.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                            Scaffold.mc.field_71467_ac = 4;
                            if (Scaffold.mc.field_71439_g.field_70702_br == 0.0f && Scaffold.mc.field_71439_g.field_191988_bg == 0.0f && Scaffold.mc.field_71474_y.field_74314_A.func_151470_d()) {
                                Scaffold.mc.field_71439_g.func_70664_aZ();
                                final EntityPlayerSP field_71439_g = Scaffold.mc.field_71439_g;
                                field_71439_g.field_70159_w *= 0.3;
                                final EntityPlayerSP field_71439_g2 = Scaffold.mc.field_71439_g;
                                field_71439_g2.field_70179_y *= 0.3;
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private static PlayerControllerMP getPlayerController() {
        return Minecraft.func_71410_x().field_71442_b;
    }
    
    public static EnumActionResult processRightClickBlock(final BlockPos pos, final EnumFacing side, final Vec3d hitVec) {
        return getPlayerController().func_187099_a(Wrapper.getPlayer(), Scaffold.mc.field_71441_e, pos, side, hitVec, EnumHand.MAIN_HAND);
    }
    
    public static IBlockState getState(final BlockPos pos) {
        return Wrapper.getWorld().func_180495_p(pos);
    }
    
    public static Block getBlock(final BlockPos pos) {
        return getState(pos).func_177230_c();
    }
    
    public static boolean canBeClicked(final BlockPos pos) {
        return getBlock(pos).func_176209_a(getState(pos), false);
    }
    
    public static void faceVectorPacketInstant(final Vec3d vec) {
        final float[] rotations = getNeededRotations2(vec);
        Wrapper.getPlayer().field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], Wrapper.getPlayer().field_70122_E));
    }
    
    private static float[] getNeededRotations2(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.field_72450_a - eyesPos.field_72450_a;
        final double diffY = vec.field_72448_b - eyesPos.field_72448_b;
        final double diffZ = vec.field_72449_c - eyesPos.field_72449_c;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { Wrapper.getPlayer().field_70177_z + MathHelper.func_76142_g(yaw - Wrapper.getPlayer().field_70177_z), Wrapper.getPlayer().field_70125_A + MathHelper.func_76142_g(pitch - Wrapper.getPlayer().field_70125_A) };
    }
    
    public static Vec3d getEyesPos() {
        return new Vec3d(Wrapper.getPlayer().field_70165_t, Wrapper.getPlayer().field_70163_u + Wrapper.getPlayer().func_70047_e(), Wrapper.getPlayer().field_70161_v);
    }
    
    public void onDisable() {
        if (this.rotated && !((AutoCrystal)ModuleManager.getModuleByName("AutoCrystal")).rotated) {
            final KillAura killAura = (KillAura)ModuleManager.getModuleByName("KillAura");
            if (!KillAura.isAiming) {
                ((Aimbot)ModuleManager.getModuleByName("Aimbot")).resetRotation();
            }
        }
        this.resetTimer = 0;
        this.rotated = false;
    }
}
