// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import java.util.List;
import net.minecraft.init.Blocks;
import meow.candycat.uwu.util.EntityUtil;
import meow.candycat.uwu.util.Friends;
import net.minecraft.block.BlockObsidian;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3i;
import net.minecraft.block.Block;
import java.util.Iterator;
import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import meow.candycat.uwu.util.BlockInteractionHelper;
import net.minecraft.client.gui.GuiGameOver;
import java.util.function.Predicate;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import meow.candycat.uwu.util.Wrapper;
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import meow.candycat.uwu.command.Command;
import meow.candycat.uwu.module.ModuleManager;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.event.events.EventPlayerPostMotionUpdate;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import meow.candycat.uwu.event.events.GuiScreenEvent;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.EventPlayerPreMotionUpdateBeforeAimbot;
import meow.candycat.eventsystem.listener.Listener;
import net.minecraft.entity.player.EntityPlayer;
import meow.candycat.uwu.setting.Setting;
import net.minecraft.util.math.Vec3d;
import meow.candycat.uwu.module.Module;

@Info(name = "AutoTrap", category = Category.COMBAT, description = "Traps players near you with obby")
public class AutoTrap extends Module
{
    private final Vec3d[] offsetsDefault;
    private Setting<Double> range;
    private Setting<Boolean> rotate;
    private Setting<Boolean> announceUsage;
    private Setting<Boolean> enemyinhole;
    private EntityPlayer closestTarget;
    private String lastTickTargetName;
    private int playerHotbarSlot;
    private boolean rotated;
    private int lastHotbarSlot;
    private boolean isSneaking;
    private int offsetStep;
    private boolean firstRun;
    boolean disable;
    int blocksPlaced;
    @EventHandler
    private Listener<EventPlayerPreMotionUpdateBeforeAimbot> w;
    @EventHandler
    public Listener<GuiScreenEvent.Displayed> listener;
    BlockPos neighbor;
    EnumFacing side2;
    Vec3d hitVec;
    @EventHandler
    private Listener<EventPlayerPostMotionUpdate> xw;
    
    public AutoTrap() {
        this.offsetsDefault = new Vec3d[] { new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(1.0, 3.0, 0.0), new Vec3d(0.0, 3.0, 1.0), new Vec3d(-1.0, 3.0, 0.0), new Vec3d(0.0, 3.0, 0.0) };
        this.range = this.register(Settings.d("Range", 5.5));
        this.rotate = this.register(Settings.b("Rotate", true));
        this.announceUsage = this.register(Settings.b("Announce Usage", true));
        this.enemyinhole = this.register(Settings.b("EnemyInHole"));
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
        this.isSneaking = false;
        this.offsetStep = 0;
        this.disable = false;
        this.blocksPlaced = 0;
        ArrayList<Object> placeTargets;
        BlockPos offsetPos;
        BlockPos targetPos;
        boolean shouldTryToPlace;
        final Iterator<Entity> iterator;
        Entity entity;
        this.w = new Listener<EventPlayerPreMotionUpdateBeforeAimbot>(event -> {
            if (AutoTrap.mc.field_71439_g == null || ModuleManager.isModuleEnabled("Freecam")) {
                return;
            }
            else {
                this.findClosestTarget();
                if (this.closestTarget == null) {
                    if (this.firstRun) {
                        this.firstRun = false;
                        if (this.announceUsage.getValue()) {
                            Command.sendChatMessage("[AutoTrap] Enabled, waiting for target.");
                        }
                    }
                    return;
                }
                else {
                    if (this.firstRun) {
                        this.firstRun = false;
                        this.lastTickTargetName = this.closestTarget.func_70005_c_();
                        if (this.announceUsage.getValue()) {
                            Command.sendChatMessage("[AutoTrap] Enabled, target: " + this.lastTickTargetName);
                        }
                    }
                    else if (this.closestTarget != null && !this.lastTickTargetName.equals(this.closestTarget.func_70005_c_())) {
                        this.lastTickTargetName = this.closestTarget.func_70005_c_();
                        this.offsetStep = 0;
                        if (this.announceUsage.getValue()) {
                            Command.sendChatMessage("[AutoTrap] New target: " + this.lastTickTargetName);
                        }
                    }
                    placeTargets = new ArrayList<Object>();
                    Collections.addAll(placeTargets, this.offsetsDefault);
                    this.blocksPlaced = 0;
                    while (this.blocksPlaced < 1) {
                        if (this.offsetStep >= placeTargets.size()) {
                            this.offsetStep = 0;
                            break;
                        }
                        else {
                            offsetPos = new BlockPos((Vec3d)placeTargets.get(this.offsetStep));
                            targetPos = new BlockPos(this.closestTarget.func_174791_d()).func_177977_b().func_177982_a(offsetPos.field_177962_a, offsetPos.field_177960_b, offsetPos.field_177961_c);
                            shouldTryToPlace = true;
                            if (!Wrapper.getWorld().func_180495_p(targetPos).func_185904_a().func_76222_j()) {
                                shouldTryToPlace = false;
                            }
                            AutoTrap.mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(targetPos)).iterator();
                            while (iterator.hasNext()) {
                                entity = iterator.next();
                                if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                                    shouldTryToPlace = false;
                                    break;
                                }
                            }
                            if (shouldTryToPlace && this.placeBlock(targetPos)) {
                                ++this.blocksPlaced;
                            }
                            ++this.offsetStep;
                        }
                    }
                    return;
                }
            }
        }, (Predicate<EventPlayerPreMotionUpdateBeforeAimbot>[])new Predicate[0]);
        this.listener = new Listener<GuiScreenEvent.Displayed>(event -> {
            if (!(event.getScreen() instanceof GuiGameOver)) {
                return;
            }
            else {
                this.disable = true;
                return;
            }
        }, (Predicate<GuiScreenEvent.Displayed>[])new Predicate[0]);
        Block neighborPos;
        KillAura killAura;
        this.xw = new Listener<EventPlayerPostMotionUpdate>(e -> {
            if (this.blocksPlaced > 0) {
                neighborPos = AutoTrap.mc.field_71441_e.func_180495_p(this.neighbor).func_177230_c();
                if (BlockInteractionHelper.blackList.contains(neighborPos) || BlockInteractionHelper.shulkerList.contains(neighborPos)) {
                    AutoTrap.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)AutoTrap.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
                    this.isSneaking = true;
                }
                AutoTrap.mc.field_71442_b.func_187099_a(AutoTrap.mc.field_71439_g, AutoTrap.mc.field_71441_e, this.neighbor, this.side2, this.hitVec, EnumHand.MAIN_HAND);
                AutoTrap.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
                    Wrapper.getPlayer().field_71071_by.field_70461_c = this.playerHotbarSlot;
                    this.lastHotbarSlot = this.playerHotbarSlot;
                }
                if (this.isSneaking) {
                    AutoTrap.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)AutoTrap.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
                    this.isSneaking = false;
                }
            }
            else if (this.rotated && !((AutoCrystal)ModuleManager.getModuleByName("AutoCrystal")).rotated) {
                killAura = (KillAura)ModuleManager.getModuleByName("KillAura");
                if (!KillAura.isAiming) {
                    ((Aimbot)ModuleManager.getModuleByName("Aimbot")).resetRotation();
                    this.rotated = false;
                }
            }
        }, (Predicate<EventPlayerPostMotionUpdate>[])new Predicate[0]);
    }
    
    @Override
    protected void onEnable() {
        if (AutoTrap.mc.field_71439_g == null || this.findObiInHotbar() == -1) {
            this.disable();
            return;
        }
        this.firstRun = true;
        this.playerHotbarSlot = Wrapper.getPlayer().field_71071_by.field_70461_c;
        this.lastHotbarSlot = -1;
    }
    
    @Override
    protected void onDisable() {
        if (AutoTrap.mc.field_71439_g == null) {
            return;
        }
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            Wrapper.getPlayer().field_71071_by.field_70461_c = this.playerHotbarSlot;
        }
        if (this.isSneaking) {
            AutoTrap.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)AutoTrap.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
        }
        ((Aimbot)ModuleManager.getModuleByName("Aimbot")).resetRotation();
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
        if (this.announceUsage.getValue()) {
            Command.sendChatMessage("[AutoTrap] Disabled!");
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.disable) {
            this.disable();
            this.disable = false;
        }
    }
    
    private boolean placeBlock(final BlockPos pos) {
        if (!AutoTrap.mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76222_j()) {
            return false;
        }
        if (!BlockInteractionHelper.checkForNeighbours(pos)) {
            return false;
        }
        final Vec3d eyesPos = new Vec3d(Wrapper.getPlayer().field_70165_t, Wrapper.getPlayer().field_70163_u + Wrapper.getPlayer().func_70047_e(), Wrapper.getPlayer().field_70161_v);
        for (final EnumFacing side : EnumFacing.values()) {
            this.neighbor = pos.func_177972_a(side);
            this.side2 = side.func_176734_d();
            if (AutoTrap.mc.field_71441_e.func_180495_p(this.neighbor).func_177230_c().func_176209_a(AutoTrap.mc.field_71441_e.func_180495_p(this.neighbor), false)) {
                this.hitVec = new Vec3d((Vec3i)this.neighbor).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(this.side2.func_176730_m()).func_186678_a(0.5));
                if (eyesPos.func_72438_d(this.hitVec) <= this.range.getValue()) {
                    final int obiSlot = this.findObiInHotbar();
                    if (obiSlot == -1) {
                        this.disable = true;
                        return false;
                    }
                    if (AutoTrap.mc.field_71439_g.field_71071_by.field_70461_c != obiSlot) {
                        this.playerHotbarSlot = AutoTrap.mc.field_71439_g.field_71071_by.field_70461_c;
                        Wrapper.getPlayer().field_71071_by.field_70461_c = obiSlot;
                        this.lastHotbarSlot = obiSlot;
                    }
                    if (this.rotate.getValue()) {
                        final float[] w = BlockInteractionHelper.getLegitRotations(this.hitVec);
                        ((Aimbot)ModuleManager.getModuleByName("Aimbot")).setRotation(w[0], w[1]);
                        this.rotated = true;
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
    
    private void findClosestTarget() {
        final List<EntityPlayer> playerList = (List<EntityPlayer>)Wrapper.getWorld().field_73010_i;
        this.closestTarget = null;
        for (final EntityPlayer target : playerList) {
            if (target == AutoTrap.mc.field_71439_g) {
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
            if (this.closestTarget != null && Wrapper.getPlayer().func_70032_d((Entity)target) >= Wrapper.getPlayer().func_70032_d((Entity)this.closestTarget)) {
                continue;
            }
            if (this.enemyinhole.getValue() && Math.floor(target.field_70163_u) == target.field_70163_u) {
                int holeBlocks = 0;
                final Vec3d[] array;
                final Vec3d[] holeOffset = array = new Vec3d[] { target.func_174791_d().func_72441_c(1.0, 0.0, 0.0), target.func_174791_d().func_72441_c(-1.0, 0.0, 0.0), target.func_174791_d().func_72441_c(0.0, 0.0, 1.0), target.func_174791_d().func_72441_c(0.0, 0.0, -1.0), target.func_174791_d().func_72441_c(0.0, -1.0, 0.0) };
                for (final Vec3d vecOffset : array) {
                    final BlockPos offset = new BlockPos(vecOffset.field_72450_a, vecOffset.field_72448_b, vecOffset.field_72449_c);
                    if (AutoTrap.mc.field_71441_e.func_180495_p(offset).func_177230_c() == Blocks.field_150343_Z || AutoTrap.mc.field_71441_e.func_180495_p(offset).func_177230_c() == Blocks.field_150357_h) {
                        ++holeBlocks;
                    }
                }
                if (holeBlocks != 5) {
                    continue;
                }
            }
            this.closestTarget = target;
        }
    }
    
    @Override
    public String getHudInfo() {
        return (this.closestTarget == null) ? null : this.closestTarget.func_70005_c_();
    }
}
