// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import java.util.Arrays;
import meow.candycat.uwu.util.BlockInteractionHelper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.inventory.ClickType;
import net.minecraft.client.gui.inventory.GuiContainer;
import java.util.Iterator;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import java.util.Comparator;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import meow.candycat.uwu.util.Friends;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import java.util.HashMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import java.math.RoundingMode;
import meow.candycat.uwu.module.ModuleManager;
import meow.candycat.uwu.command.Command;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import java.text.DecimalFormat;
import net.minecraft.block.Block;
import java.util.List;
import meow.candycat.uwu.module.Module;

@Info(name = "Auto32k", category = Category.COMBAT, description = "")
public class Auto32k2 extends Module
{
    private static final List<Block> blackList;
    private static final List<Block> shulkerList;
    private static final DecimalFormat df;
    private Setting<Boolean> moveToHotbar;
    private Setting<Boolean> autoEnableHitAura;
    private Setting<Double> placeRange;
    private Setting<Integer> yOffset;
    private Setting<Boolean> placeCloseToEnemy;
    private Setting<Boolean> placeObiOnTop;
    private Setting<Boolean> debugMessages;
    private int swordSlot;
    private static boolean isSneaking;
    
    public Auto32k2() {
        this.moveToHotbar = this.register(Settings.b("Move 32k to Hotbar", true));
        this.autoEnableHitAura = this.register(Settings.b("Auto enable Hit Aura", true));
        this.placeRange = this.register((Setting<Double>)Settings.doubleBuilder("Place range").withMinimum(1.0).withValue(4.0).withMaximum(10.0).build());
        this.yOffset = this.register(Settings.i("Y Offset (Hopper)", 2));
        this.placeCloseToEnemy = this.register(Settings.b("Place close to enemy", false));
        this.placeObiOnTop = this.register(Settings.b("Place Obi on Top", true));
        this.debugMessages = this.register(Settings.b("Debug Messages", true));
    }
    
    @Override
    protected void onEnable() {
        Command.sendChatMessage("Do Not Use Any AntiGhostBlock Modules!");
        if (this.isDisabled() || Auto32k2.mc.field_71439_g == null || ModuleManager.isModuleEnabled("Freecam")) {
            this.disable();
            return;
        }
        Auto32k2.df.setRoundingMode(RoundingMode.CEILING);
        int hopperSlot = -1;
        int shulkerSlot = -1;
        int obiSlot = -1;
        this.swordSlot = -1;
        for (int i = 0; i < 9 && (hopperSlot == -1 || shulkerSlot == -1 || obiSlot == -1); ++i) {
            final ItemStack stack = Auto32k2.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack != ItemStack.field_190927_a) {
                if (stack.func_77973_b() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
                    if (block == Blocks.field_150438_bZ) {
                        hopperSlot = i;
                    }
                    else if (Auto32k2.shulkerList.contains(block)) {
                        shulkerSlot = i;
                    }
                    else if (block == Blocks.field_150343_Z) {
                        obiSlot = i;
                    }
                }
            }
        }
        if (hopperSlot == -1) {
            if (this.debugMessages.getValue()) {
                Command.sendChatMessage("[Auto32k] Hopper missing, disabling.");
            }
            this.disable();
            return;
        }
        if (shulkerSlot == -1) {
            if (this.debugMessages.getValue()) {
                Command.sendChatMessage("[Auto32k] Shulker missing, disabling.");
            }
            this.disable();
            return;
        }
        final int range = (int)Math.ceil(this.placeRange.getValue());
        final AutoCrystal crystalAura = (AutoCrystal)ModuleManager.getModuleByName("AutoCrystal");
        final List<BlockPos> placeTargetList = crystalAura.getSphere(AutoCrystal.getPlayerPos(), (float)range, range, false, true, 0);
        final Map<BlockPos, Double> placeTargetMap = new HashMap<BlockPos, Double>();
        BlockPos placeTarget = null;
        boolean useRangeSorting = false;
        for (final BlockPos placeTargetTest : placeTargetList) {
            for (final Entity entity : Auto32k2.mc.field_71441_e.field_72996_f) {
                if (!(entity instanceof EntityPlayer)) {
                    continue;
                }
                if (entity == Auto32k2.mc.field_71439_g) {
                    continue;
                }
                if (Friends.isFriend(entity.func_70005_c_())) {
                    continue;
                }
                if (this.yOffset.getValue() != 0 && Math.abs(Auto32k2.mc.field_71439_g.func_180425_c().field_177960_b - placeTargetTest.field_177960_b) > Math.abs(this.yOffset.getValue())) {
                    continue;
                }
                if (!this.isAreaPlaceable(placeTargetTest)) {
                    continue;
                }
                useRangeSorting = true;
                final double distanceToEntity = entity.func_70011_f((double)placeTargetTest.field_177962_a, (double)placeTargetTest.field_177960_b, (double)placeTargetTest.field_177961_c);
                placeTargetMap.put(placeTargetTest, placeTargetMap.containsKey(placeTargetTest) ? (placeTargetMap.get(placeTargetTest) + distanceToEntity) : distanceToEntity);
                useRangeSorting = true;
            }
        }
        if (placeTargetMap.size() > 0) {
            final Map map;
            placeTargetMap.forEach((k, v) -> {
                if (!this.isAreaPlaceable(k)) {
                    map.remove(k);
                }
                return;
            });
            if (placeTargetMap.size() == 0) {
                useRangeSorting = false;
            }
        }
        if (useRangeSorting) {
            if (this.placeCloseToEnemy.getValue()) {
                if (this.debugMessages.getValue()) {
                    Command.sendChatMessage("[Auto32k] Placing close to Enemy");
                }
                placeTarget = Collections.min((Collection<? extends Map.Entry<BlockPos, V>>)placeTargetMap.entrySet(), (Comparator<? super Map.Entry<BlockPos, V>>)Map.Entry.comparingByValue()).getKey();
            }
            else {
                if (this.debugMessages.getValue()) {
                    Command.sendChatMessage("[Auto32k] Placing far from Enemy");
                }
                placeTarget = Collections.max((Collection<? extends Map.Entry<BlockPos, V>>)placeTargetMap.entrySet(), (Comparator<? super Map.Entry<BlockPos, V>>)Map.Entry.comparingByValue()).getKey();
            }
        }
        else {
            if (this.debugMessages.getValue()) {
                Command.sendChatMessage("[Auto32k] No enemy nearby, placing at first valid position.");
            }
            for (final BlockPos pos : placeTargetList) {
                if (this.isAreaPlaceable(pos)) {
                    placeTarget = pos;
                    break;
                }
            }
        }
        if (placeTarget == null) {
            if (this.debugMessages.getValue()) {
                Command.sendChatMessage("[Auto32k] No valid position in range to place!");
            }
            this.disable();
            return;
        }
        if (this.debugMessages.getValue()) {
            Command.sendChatMessage("[Auto32k] Place Target: " + placeTarget.field_177962_a + " " + placeTarget.field_177960_b + " " + placeTarget.field_177961_c + " Distance: " + Auto32k2.df.format(Auto32k2.mc.field_71439_g.func_174791_d().func_72438_d(new Vec3d((Vec3i)placeTarget))));
        }
        Auto32k2.mc.field_71439_g.field_71071_by.field_70461_c = hopperSlot;
        placeBlock(new BlockPos((Vec3i)placeTarget));
        Auto32k2.mc.field_71439_g.field_71071_by.field_70461_c = shulkerSlot;
        placeBlock(new BlockPos((Vec3i)placeTarget.func_177982_a(0, 1, 0)));
        if (this.placeObiOnTop.getValue() && obiSlot != -1) {
            Auto32k2.mc.field_71439_g.field_71071_by.field_70461_c = obiSlot;
            placeBlock(new BlockPos((Vec3i)placeTarget.func_177982_a(0, 2, 0)));
        }
        if (Auto32k2.isSneaking) {
            Auto32k2.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Auto32k2.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
            Auto32k2.isSneaking = false;
        }
        Auto32k2.mc.field_71439_g.field_71071_by.field_70461_c = shulkerSlot;
        final BlockPos hopperPos = new BlockPos((Vec3i)placeTarget);
        Auto32k2.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(hopperPos, EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
        this.swordSlot = shulkerSlot + 32;
    }
    
    @Override
    public void onUpdate() {
        if (this.isDisabled() || Auto32k2.mc.field_71439_g == null || ModuleManager.isModuleEnabled("Freecam")) {
            return;
        }
        if (!(Auto32k2.mc.field_71462_r instanceof GuiContainer)) {
            return;
        }
        if (!this.moveToHotbar.getValue()) {
            this.disable();
            return;
        }
        if (this.swordSlot == -1) {
            return;
        }
        boolean swapReady = true;
        if (((GuiContainer)Auto32k2.mc.field_71462_r).field_147002_h.func_75139_a(0).func_75211_c().field_190928_g) {
            swapReady = false;
        }
        if (!((GuiContainer)Auto32k2.mc.field_71462_r).field_147002_h.func_75139_a(this.swordSlot).func_75211_c().field_190928_g) {
            swapReady = false;
        }
        if (swapReady) {
            Auto32k2.mc.field_71442_b.func_187098_a(((GuiContainer)Auto32k2.mc.field_71462_r).field_147002_h.field_75152_c, 0, this.swordSlot - 32, ClickType.SWAP, (EntityPlayer)Auto32k2.mc.field_71439_g);
            if (this.autoEnableHitAura.getValue()) {
                ModuleManager.getModuleByName("Aura").enable();
            }
            this.disable();
        }
    }
    
    private boolean isAreaPlaceable(final BlockPos blockPos) {
        for (final Entity entity : Auto32k2.mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(blockPos))) {
            if (entity instanceof EntityLivingBase) {
                return false;
            }
        }
        if (!Auto32k2.mc.field_71441_e.func_180495_p(blockPos).func_185904_a().func_76222_j()) {
            return false;
        }
        if (!Auto32k2.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 1, 0)).func_185904_a().func_76222_j()) {
            return false;
        }
        if (Auto32k2.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, -1, 0)).func_177230_c() instanceof BlockAir) {
            return false;
        }
        if (Auto32k2.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, -1, 0)).func_177230_c() instanceof BlockLiquid) {
            return false;
        }
        if (Auto32k2.mc.field_71439_g.func_174791_d().func_72438_d(new Vec3d((Vec3i)blockPos)) > this.placeRange.getValue()) {
            return false;
        }
        final Block block = Auto32k2.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, -1, 0)).func_177230_c();
        return !Auto32k2.blackList.contains(block) && !Auto32k2.shulkerList.contains(block) && Auto32k2.mc.field_71439_g.func_174791_d().func_72438_d(new Vec3d((Vec3i)blockPos).func_72441_c(0.0, 1.0, 0.0)) <= this.placeRange.getValue();
    }
    
    private static void placeBlock(final BlockPos pos) {
        if (!Auto32k2.mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76222_j()) {
            return;
        }
        if (!checkForNeighbours(pos)) {
            return;
        }
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.func_177972_a(side);
            final EnumFacing side2 = side.func_176734_d();
            if (Auto32k2.mc.field_71441_e.func_180495_p(neighbor).func_177230_c().func_176209_a(Auto32k2.mc.field_71441_e.func_180495_p(neighbor), false)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(side2.func_176730_m()).func_186678_a(0.5));
                final Block neighborPos = Auto32k2.mc.field_71441_e.func_180495_p(neighbor).func_177230_c();
                if (Auto32k2.blackList.contains(neighborPos) || Auto32k2.shulkerList.contains(neighborPos)) {
                    Auto32k2.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Auto32k2.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
                    Auto32k2.isSneaking = true;
                }
                BlockInteractionHelper.faceVectorPacketInstant(hitVec);
                Auto32k2.mc.field_71442_b.func_187099_a(Auto32k2.mc.field_71439_g, Auto32k2.mc.field_71441_e, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                Auto32k2.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                Auto32k2.mc.field_71467_ac = 4;
                return;
            }
        }
    }
    
    private static boolean checkForNeighbours(final BlockPos blockPos) {
        if (!hasNeighbour(blockPos)) {
            for (final EnumFacing side : EnumFacing.values()) {
                final BlockPos neighbour = blockPos.func_177972_a(side);
                if (hasNeighbour(neighbour)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
    
    private static boolean hasNeighbour(final BlockPos blockPos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = blockPos.func_177972_a(side);
            if (!Auto32k2.mc.field_71441_e.func_180495_p(neighbour).func_185904_a().func_76222_j()) {
                return true;
            }
        }
        return false;
    }
    
    static {
        blackList = Arrays.asList(Blocks.field_150477_bB, (Block)Blocks.field_150486_ae, Blocks.field_150447_bR, Blocks.field_150462_ai, Blocks.field_150467_bQ, Blocks.field_150382_bo, (Block)Blocks.field_150438_bZ, Blocks.field_150409_cd, Blocks.field_150367_z, Blocks.field_150415_aT);
        shulkerList = Arrays.asList(Blocks.field_190977_dl, Blocks.field_190978_dm, Blocks.field_190979_dn, Blocks.field_190980_do, Blocks.field_190981_dp, Blocks.field_190982_dq, Blocks.field_190983_dr, Blocks.field_190984_ds, Blocks.field_190985_dt, Blocks.field_190986_du, Blocks.field_190987_dv, Blocks.field_190988_dw, Blocks.field_190989_dx, Blocks.field_190990_dy, Blocks.field_190991_dz, Blocks.field_190975_dA);
        df = new DecimalFormat("#.#");
    }
}
