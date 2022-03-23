// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import net.minecraft.util.math.MathHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketAnimation;
import meow.candycat.uwu.util.BlockInteractionHelper;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.Vec3i;
import meow.candycat.uwu.command.Command;
import meow.candycat.uwu.util.Wrapper;
import meow.candycat.uwu.module.ModuleManager;
import meow.candycat.uwu.setting.Settings;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "Surround", category = Category.COMBAT, description = "Surrounds you with obsidian")
public class Surround extends Module
{
    private Setting<Boolean> autoDisable;
    private Setting<Boolean> spoofRotations;
    private Setting<Boolean> spoofHotbar;
    private Setting<Double> blockPerTick;
    private Setting<DebugMsgs> debugMsgs;
    private Setting<AutoCenter> autoCenter;
    private final Vec3d[] surroundTargets;
    private Vec3d playerPos;
    private BlockPos basePos;
    private int offsetStep;
    private int playerHotbarSlot;
    private int lastHotbarSlot;
    
    public Surround() {
        this.autoDisable = this.register(Settings.b("Disable on place", true));
        this.spoofRotations = this.register(Settings.b("Spoof Rotations", true));
        this.spoofHotbar = this.register(Settings.b("Spoof Hotbar", true));
        this.blockPerTick = this.register((Setting<Double>)Settings.doubleBuilder("Blocks per Tick").withMinimum(1.0).withValue(4.0).withMaximum(10.0).build());
        this.debugMsgs = this.register(Settings.e("Debug Messages", DebugMsgs.IMPORTANT));
        this.autoCenter = this.register(Settings.e("Auto Center", AutoCenter.TP));
        this.surroundTargets = new Vec3d[] { new Vec3d(0.0, 0.0, 0.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, -1.0) };
        this.offsetStep = 0;
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
    }
    
    @Override
    public void onUpdate() {
        if (!this.isDisabled() && Surround.mc.field_71439_g != null && !ModuleManager.isModuleEnabled("Freecam")) {
            if (this.offsetStep == 0) {
                this.basePos = new BlockPos(Surround.mc.field_71439_g.func_174791_d()).func_177977_b();
                this.playerHotbarSlot = Wrapper.getPlayer().field_71071_by.field_70461_c;
                if (this.debugMsgs.getValue().equals(DebugMsgs.ALL)) {
                    Command.sendChatMessage("[Surround] Starting Loop, current Player Slot: " + this.playerHotbarSlot);
                }
                if (!this.spoofHotbar.getValue()) {
                    this.lastHotbarSlot = Surround.mc.field_71439_g.field_71071_by.field_70461_c;
                }
            }
            for (int i = 0; i < (int)Math.floor(this.blockPerTick.getValue()); ++i) {
                if (this.debugMsgs.getValue().equals(DebugMsgs.ALL)) {
                    Command.sendChatMessage("[Surround] Loop iteration: " + this.offsetStep);
                }
                if (this.offsetStep >= this.surroundTargets.length) {
                    this.endLoop();
                    return;
                }
                final Vec3d offset = this.surroundTargets[this.offsetStep];
                this.placeBlock(new BlockPos((Vec3i)this.basePos.func_177963_a(offset.field_72450_a, offset.field_72448_b, offset.field_72449_c)));
                ++this.offsetStep;
            }
        }
    }
    
    private void centerPlayer(final double x, final double y, final double z) {
        if (this.debugMsgs.getValue().equals(DebugMsgs.ALL) && this.playerPos != null) {
            Command.sendChatMessage("[Surround: AutoCenter] Player position is " + this.playerPos.toString());
        }
        else if (this.debugMsgs.getValue().equals(DebugMsgs.ALL)) {
            Command.sendChatMessage("[Surround: AutoCenter] Player position is null");
        }
        Surround.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(x, y, z, true));
        Surround.mc.field_71439_g.func_70107_b(x, y, z);
    }
    
    double getDst(final Vec3d vec) {
        return this.playerPos.func_72438_d(vec);
    }
    
    public void onEnable() {
        if (Surround.mc.field_71439_g == null) {
            return;
        }
        final BlockPos centerPos = Surround.mc.field_71439_g.func_180425_c();
        this.playerPos = Surround.mc.field_71439_g.func_174791_d();
        final double y = centerPos.func_177956_o();
        double x = centerPos.func_177958_n();
        double z = centerPos.func_177952_p();
        final Vec3d plusPlus = new Vec3d(x + 0.5, y, z + 0.5);
        final Vec3d plusMinus = new Vec3d(x + 0.5, y, z - 0.5);
        final Vec3d minusMinus = new Vec3d(x - 0.5, y, z - 0.5);
        final Vec3d minusPlus = new Vec3d(x - 0.5, y, z + 0.5);
        if (this.autoCenter.getValue().equals(AutoCenter.TP)) {
            if (this.getDst(plusPlus) < this.getDst(plusMinus) && this.getDst(plusPlus) < this.getDst(minusMinus) && this.getDst(plusPlus) < this.getDst(minusPlus)) {
                x = centerPos.func_177958_n() + 0.5;
                z = centerPos.func_177952_p() + 0.5;
                this.centerPlayer(x, y, z);
            }
            if (this.getDst(plusMinus) < this.getDst(plusPlus) && this.getDst(plusMinus) < this.getDst(minusMinus) && this.getDst(plusMinus) < this.getDst(minusPlus)) {
                x = centerPos.func_177958_n() + 0.5;
                z = centerPos.func_177952_p() - 0.5;
                this.centerPlayer(x, y, z);
            }
            if (this.getDst(minusMinus) < this.getDst(plusPlus) && this.getDst(minusMinus) < this.getDst(plusMinus) && this.getDst(minusMinus) < this.getDst(minusPlus)) {
                x = centerPos.func_177958_n() - 0.5;
                z = centerPos.func_177952_p() - 0.5;
                this.centerPlayer(x, y, z);
            }
            if (this.getDst(minusPlus) < this.getDst(plusPlus) && this.getDst(minusPlus) < this.getDst(plusMinus) && this.getDst(minusPlus) < this.getDst(minusMinus)) {
                x = centerPos.func_177958_n() - 0.5;
                z = centerPos.func_177952_p() + 0.5;
                this.centerPlayer(x, y, z);
            }
        }
        this.playerHotbarSlot = Wrapper.getPlayer().field_71071_by.field_70461_c;
        this.lastHotbarSlot = -1;
        if (this.debugMsgs.getValue().equals(DebugMsgs.ALL)) {
            Command.sendChatMessage("[Surround] Saving initial Slot  = " + this.playerHotbarSlot);
        }
    }
    
    public void onDisable() {
        if (Surround.mc.field_71439_g != null) {
            if (this.debugMsgs.getValue().equals(DebugMsgs.ALL)) {
                Command.sendChatMessage("[Surround] Disabling");
            }
            if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
                if (this.spoofHotbar.getValue()) {
                    Surround.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.playerHotbarSlot));
                }
                else {
                    Wrapper.getPlayer().field_71071_by.field_70461_c = this.playerHotbarSlot;
                }
            }
            this.playerHotbarSlot = -1;
            this.lastHotbarSlot = -1;
        }
    }
    
    private void endLoop() {
        this.offsetStep = 0;
        if (this.debugMsgs.getValue().equals(DebugMsgs.ALL)) {
            Command.sendChatMessage("[Surround] Ending Loop");
        }
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            if (this.debugMsgs.getValue().equals(DebugMsgs.ALL)) {
                Command.sendChatMessage("[Surround] Setting Slot back to  = " + this.playerHotbarSlot);
            }
            if (this.spoofHotbar.getValue()) {
                Surround.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.playerHotbarSlot));
            }
            else {
                Wrapper.getPlayer().field_71071_by.field_70461_c = this.playerHotbarSlot;
            }
            this.lastHotbarSlot = this.playerHotbarSlot;
        }
        if (this.autoDisable.getValue()) {
            this.disable();
        }
    }
    
    private void placeBlock(final BlockPos blockPos) {
        if (!Wrapper.getWorld().func_180495_p(blockPos).func_185904_a().func_76222_j()) {
            if (this.debugMsgs.getValue().equals(DebugMsgs.ALL)) {
                Command.sendChatMessage("[Surround] Block is already placed, skipping");
            }
        }
        else if (!BlockInteractionHelper.checkForNeighbours(blockPos) && this.debugMsgs.getValue().equals(DebugMsgs.ALL)) {
            Command.sendChatMessage("[Surround] !checkForNeighbours(blockPos), disabling! ");
        }
        else {
            Surround.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(Surround.mc.field_71439_g.func_184600_cs()));
            this.placeBlockExecute(blockPos);
        }
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
    
    public void placeBlockExecute(final BlockPos pos) {
        final Vec3d eyesPos = new Vec3d(Wrapper.getPlayer().field_70165_t, Wrapper.getPlayer().field_70163_u + Wrapper.getPlayer().func_70047_e(), Wrapper.getPlayer().field_70161_v);
        final EnumFacing[] values;
        final EnumFacing[] var3 = values = EnumFacing.values();
        for (final EnumFacing side : values) {
            final BlockPos neighbor = pos.func_177972_a(side);
            final EnumFacing side2 = side.func_176734_d();
            if (!canBeClicked(neighbor)) {
                if (this.debugMsgs.getValue().equals(DebugMsgs.ALL)) {
                    Command.sendChatMessage("[Surround] No neighbor to click at!");
                }
            }
            else {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(side2.func_176730_m()).func_186678_a(0.5));
                if (eyesPos.func_72436_e(hitVec) <= 18.0625) {
                    if (this.spoofRotations.getValue()) {
                        faceVectorPacketInstant(hitVec);
                    }
                    boolean needSneak = false;
                    final Block blockBelow = Surround.mc.field_71441_e.func_180495_p(neighbor).func_177230_c();
                    if (BlockInteractionHelper.blackList.contains(blockBelow) || BlockInteractionHelper.shulkerList.contains(blockBelow)) {
                        if (this.debugMsgs.getValue().equals(DebugMsgs.IMPORTANT)) {
                            Command.sendChatMessage("[Surround] Sneak enabled!");
                        }
                        needSneak = true;
                    }
                    if (needSneak) {
                        Surround.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Surround.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
                    }
                    final int obiSlot = this.findObiInHotbar();
                    if (obiSlot == -1) {
                        if (this.debugMsgs.getValue().equals(DebugMsgs.IMPORTANT)) {
                            Command.sendChatMessage("[Surround] No obsidian in hotbar, disabling!");
                        }
                        this.disable();
                        return;
                    }
                    if (this.lastHotbarSlot != obiSlot) {
                        if (this.debugMsgs.getValue().equals(DebugMsgs.ALL)) {
                            Command.sendChatMessage("[Surround] Setting Slot to obsidian at  = " + obiSlot);
                        }
                        if (this.spoofHotbar.getValue()) {
                            Surround.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(obiSlot));
                        }
                        else {
                            Wrapper.getPlayer().field_71071_by.field_70461_c = obiSlot;
                        }
                        this.lastHotbarSlot = obiSlot;
                    }
                    Surround.mc.field_71442_b.func_187099_a(Wrapper.getPlayer(), Surround.mc.field_71441_e, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                    Surround.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                    if (needSneak) {
                        if (this.debugMsgs.getValue().equals(DebugMsgs.IMPORTANT)) {
                            Command.sendChatMessage("[Surround] Sneak disabled!");
                        }
                        Surround.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Surround.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
                    }
                    return;
                }
                else if (this.debugMsgs.getValue().equals(DebugMsgs.ALL)) {
                    Command.sendChatMessage("[Surround] Distance > 4.25 blocks!");
                }
            }
        }
    }
    
    private static boolean canBeClicked(final BlockPos pos) {
        return getBlock(pos).func_176209_a(getState(pos), false);
    }
    
    public static Block getBlock(final BlockPos pos) {
        return getState(pos).func_177230_c();
    }
    
    private static IBlockState getState(final BlockPos pos) {
        return Wrapper.getWorld().func_180495_p(pos);
    }
    
    private static void faceVectorPacketInstant(final Vec3d vec) {
        final float[] rotations = getLegitRotations(vec);
        Wrapper.getPlayer().field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], Wrapper.getPlayer().field_70122_E));
    }
    
    private static float[] getLegitRotations(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.field_72450_a - eyesPos.field_72450_a;
        final double diffY = vec.field_72448_b - eyesPos.field_72448_b;
        final double diffZ = vec.field_72449_c - eyesPos.field_72449_c;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { Wrapper.getPlayer().field_70177_z + MathHelper.func_76142_g(yaw - Wrapper.getPlayer().field_70177_z), Wrapper.getPlayer().field_70125_A + MathHelper.func_76142_g(pitch - Wrapper.getPlayer().field_70125_A) };
    }
    
    private static Vec3d getEyesPos() {
        return new Vec3d(Wrapper.getPlayer().field_70165_t, Wrapper.getPlayer().field_70163_u + Wrapper.getPlayer().func_70047_e(), Wrapper.getPlayer().field_70161_v);
    }
    
    private enum DebugMsgs
    {
        NONE, 
        IMPORTANT, 
        ALL;
    }
    
    private enum AutoCenter
    {
        OFF, 
        TP;
    }
}
