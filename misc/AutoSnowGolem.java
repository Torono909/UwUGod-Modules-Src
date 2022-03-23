// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import java.util.Arrays;
import meow.candycat.uwu.util.BlockInteractionHelper;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.Iterator;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import java.util.Comparator;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import meow.candycat.uwu.util.Friends;
import net.minecraft.util.math.BlockPos;
import java.util.HashMap;
import meow.candycat.uwu.module.modules.combat.CrystalAura;
import net.minecraft.init.Blocks;
import java.math.RoundingMode;
import meow.candycat.uwu.module.ModuleManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.Entity;
import meow.candycat.uwu.command.Command;
import net.minecraft.item.ItemNameTag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import java.text.DecimalFormat;
import net.minecraft.block.Block;
import java.util.List;
import meow.candycat.uwu.module.Module;

@Info(name = "AutoSnowGolem", category = Category.MISC, description = "Automatically creates snowgolems")
public class AutoSnowGolem extends Module
{
    private static final List<Block> blackList;
    private static final List<Block> shulkerList;
    private static final DecimalFormat df;
    private Setting<Double> placeRange;
    private Setting<Boolean> placeCloseToEnemy;
    private Setting<Boolean> fastMode;
    private Setting<PlaceMode> placeMode;
    private Setting<Boolean> nametag;
    private Setting<DebugMsgs> debugMsgs;
    private int swordSlot;
    private static boolean isSneaking;
    
    public AutoSnowGolem() {
        this.placeRange = this.register((Setting<Double>)Settings.doubleBuilder("Place range").withMinimum(1.0).withValue(4.0).withMaximum(10.0).build());
        this.placeCloseToEnemy = this.register(Settings.b("Place close to enemy", false));
        this.fastMode = this.register(Settings.b("Disable after placing", false));
        this.placeMode = this.register(Settings.e("Place Mode", PlaceMode.AUTO));
        this.nametag = this.register(Settings.b("Name Golem", false));
        this.debugMsgs = this.register(Settings.e("Debug Messages", DebugMsgs.IMPORTANT));
    }
    
    @Override
    public void onUpdate() {
        if (this.nametag.getValue()) {
            int tagslot = -1;
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = AutoSnowGolem.mc.field_71439_g.field_71071_by.func_70301_a(i);
                if (stack != ItemStack.field_190927_a) {
                    if (!(stack.func_77973_b() instanceof ItemBlock)) {
                        final Item tag = stack.func_77973_b();
                        if (tag instanceof ItemNameTag) {
                            tagslot = i;
                        }
                    }
                }
            }
            if (tagslot == -1 && this.fastMode.getValue()) {
                Command.sendChatMessage("[AutoSnowGolem] Error: No nametags in inventory, disabling module");
                this.disable();
                return;
            }
            for (final Entity w : AutoSnowGolem.mc.field_71441_e.func_72910_y()) {
                if (w instanceof EntityWither) {
                    final EntityWither wither = (EntityWither)w;
                    if (AutoSnowGolem.mc.field_71439_g.func_70032_d((Entity)wither) > this.placeRange.getValue()) {
                        continue;
                    }
                    if (this.debugMsgs.getValue().equals(DebugMsgs.ALL)) {
                        Command.sendChatMessage("Registered Golem");
                    }
                    if (tagslot == -1) {
                        continue;
                    }
                    AutoSnowGolem.mc.field_71439_g.field_71071_by.field_70461_c = tagslot;
                    AutoSnowGolem.mc.field_71442_b.func_187097_a((EntityPlayer)AutoSnowGolem.mc.field_71439_g, (Entity)wither, EnumHand.MAIN_HAND);
                    if (this.nametag.getValue()) {
                        return;
                    }
                    if (this.fastMode.getValue()) {
                        this.disable();
                        return;
                    }
                    continue;
                }
            }
            AutoSnowGolem.mc.field_71439_g.field_71071_by.field_70461_c = this.swordSlot;
        }
        if (this.isDisabled() || AutoSnowGolem.mc.field_71439_g == null || ModuleManager.isModuleEnabled("Freecam")) {
            this.disable();
            Command.sendChatMessage("[AutoSnowGolem] Freecam enabled, disabling");
            return;
        }
        AutoSnowGolem.df.setRoundingMode(RoundingMode.CEILING);
        int snowSlot = -1;
        int pumpkinSlot = -1;
        this.swordSlot = AutoSnowGolem.mc.field_71439_g.field_71071_by.field_70461_c;
        for (int j = 0; j < 9; ++j) {
            final ItemStack stack2 = AutoSnowGolem.mc.field_71439_g.field_71071_by.func_70301_a(j);
            if (stack2 != ItemStack.field_190927_a) {
                if (stack2.func_77973_b() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack2.func_77973_b()).func_179223_d();
                    if (block == Blocks.field_150433_aE) {
                        snowSlot = j;
                    }
                    else if (block == Blocks.field_150423_aK) {
                        pumpkinSlot = j;
                    }
                }
            }
        }
        if (snowSlot == -1) {
            if (this.debugMsgs.getValue().equals(DebugMsgs.IMPORTANT)) {
                Command.sendChatMessage("[AutoSnowGolem] Snow missing, disabling.");
            }
            this.disable();
            return;
        }
        if (pumpkinSlot == -1) {
            if (this.debugMsgs.getValue().equals(DebugMsgs.IMPORTANT)) {
                Command.sendChatMessage("[AutoSnowGolem] Pumpkin missing, disabling.");
            }
            this.disable();
            return;
        }
        final int range = (int)Math.ceil(this.placeRange.getValue());
        final CrystalAura crystalAura = (CrystalAura)ModuleManager.getModuleByName("CrystalAura");
        final BlockPos lookPos = AutoSnowGolem.mc.field_71476_x.func_178782_a();
        final List<BlockPos> placeTargetList = crystalAura.getSphere(CrystalAura.getPlayerPos(), (float)range, range, false, true, 0);
        final Map<BlockPos, Double> placeTargetMap = new HashMap<BlockPos, Double>();
        BlockPos placeTarget = null;
        boolean useRangeSorting = false;
        for (final BlockPos placeTargetTest : placeTargetList) {
            for (final Entity entity : AutoSnowGolem.mc.field_71441_e.field_72996_f) {
                if (!(entity instanceof EntityPlayer)) {
                    continue;
                }
                if (entity == AutoSnowGolem.mc.field_71439_g) {
                    continue;
                }
                if (Friends.isFriend(entity.func_70005_c_())) {
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
                if (this.debugMsgs.getValue().equals(DebugMsgs.ALL)) {
                    Command.sendChatMessage("[AutoSnowGolem] Placing close to Enemy");
                }
                placeTarget = Collections.min((Collection<? extends Map.Entry<BlockPos, V>>)placeTargetMap.entrySet(), (Comparator<? super Map.Entry<BlockPos, V>>)Map.Entry.comparingByValue()).getKey();
            }
            else {
                if (this.debugMsgs.getValue().equals(DebugMsgs.ALL)) {
                    Command.sendChatMessage("[AutoSnowGolem] Placing far from Enemy");
                }
                placeTarget = Collections.max((Collection<? extends Map.Entry<BlockPos, V>>)placeTargetMap.entrySet(), (Comparator<? super Map.Entry<BlockPos, V>>)Map.Entry.comparingByValue()).getKey();
            }
        }
        else {
            if (this.debugMsgs.getValue().equals(DebugMsgs.ALL)) {
                Command.sendChatMessage("[AutoSnowGolem] No enemy nearby, placing at first valid position.");
            }
            for (final BlockPos pos : placeTargetList) {
                if (this.isAreaPlaceable(pos)) {
                    placeTarget = pos;
                    break;
                }
            }
        }
        if (placeTarget == null) {
            if (this.debugMsgs.getValue().equals(DebugMsgs.ALL)) {
                Command.sendChatMessage("[AutoSnowGolem] No valid position in range to place!");
            }
            this.disable();
            return;
        }
        if (this.debugMsgs.getValue().equals(DebugMsgs.ALL)) {
            Command.sendChatMessage("[AutoSnowGolem] Place Target: " + placeTarget.field_177962_a + " " + placeTarget.field_177960_b + " " + placeTarget.field_177961_c + " Distance: " + AutoSnowGolem.df.format(AutoSnowGolem.mc.field_71439_g.func_174791_d().func_72438_d(new Vec3d((Vec3i)placeTarget))));
        }
        if (this.placeMode.getValue().equals(PlaceMode.AUTO)) {
            AutoSnowGolem.mc.field_71439_g.field_71071_by.field_70461_c = snowSlot;
            placeBlock(new BlockPos((Vec3i)placeTarget));
            AutoSnowGolem.mc.field_71439_g.field_71071_by.field_70461_c = snowSlot;
            placeBlock(new BlockPos((Vec3i)placeTarget.func_177982_a(0, 1, 0)));
            AutoSnowGolem.mc.field_71439_g.field_71071_by.field_70461_c = pumpkinSlot;
            placeBlock(new BlockPos((Vec3i)placeTarget.func_177982_a(0, 2, 0)));
        }
        else if (this.placeMode.getValue().equals(PlaceMode.LOOK) && this.isAreaPlacableLook(AutoSnowGolem.mc.field_71476_x.func_178782_a())) {
            AutoSnowGolem.mc.field_71439_g.field_71071_by.field_70461_c = snowSlot;
            Command.sendWarningMessage("Trying to place snow");
            placeBlock(new BlockPos((Vec3i)AutoSnowGolem.mc.field_71476_x.func_178782_a().func_177982_a(0, 0, 0)));
            placeBlock(new BlockPos((Vec3i)AutoSnowGolem.mc.field_71476_x.func_178782_a().func_177982_a(0, 1, 0)));
            Command.sendChatMessage("Placed snow");
            AutoSnowGolem.mc.field_71439_g.field_71071_by.field_70461_c = pumpkinSlot;
            Command.sendWarningMessage("Trying to place pumpkin");
            placeBlock(new BlockPos((Vec3i)AutoSnowGolem.mc.field_71476_x.func_178782_a().func_177982_a(0, 2, 0)));
            Command.sendChatMessage("Placed pumpkin");
        }
        if (AutoSnowGolem.isSneaking) {
            AutoSnowGolem.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)AutoSnowGolem.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
            AutoSnowGolem.isSneaking = false;
        }
        AutoSnowGolem.mc.field_71439_g.field_71071_by.field_70461_c = this.swordSlot;
        if (this.fastMode.getValue()) {
            this.disable();
        }
    }
    
    private boolean isAreaPlaceable(final BlockPos blockPos) {
        for (final Entity entity : AutoSnowGolem.mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(blockPos))) {
            if (entity instanceof EntityLivingBase) {
                return false;
            }
        }
        if (!AutoSnowGolem.mc.field_71441_e.func_180495_p(blockPos).func_185904_a().func_76222_j()) {
            return false;
        }
        if (!AutoSnowGolem.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 1, 0)).func_185904_a().func_76222_j()) {
            return false;
        }
        if (AutoSnowGolem.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, -1, 0)).func_177230_c() instanceof BlockAir) {
            return false;
        }
        if (AutoSnowGolem.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, -1, 0)).func_177230_c() instanceof BlockLiquid) {
            return false;
        }
        if (AutoSnowGolem.mc.field_71439_g.func_174791_d().func_72438_d(new Vec3d((Vec3i)blockPos)) > this.placeRange.getValue()) {
            return false;
        }
        final Block block = AutoSnowGolem.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, -1, 0)).func_177230_c();
        return !AutoSnowGolem.blackList.contains(block) && !AutoSnowGolem.shulkerList.contains(block) && AutoSnowGolem.mc.field_71439_g.func_174791_d().func_72438_d(new Vec3d((Vec3i)blockPos).func_72441_c(0.0, 1.0, 0.0)) <= this.placeRange.getValue();
    }
    
    private boolean isAreaPlacableLook(BlockPos lookPos) {
        lookPos = AutoSnowGolem.mc.field_71476_x.func_178782_a();
        Command.sendWarningMessage("Trying to place");
        if (lookPos == null) {
            return false;
        }
        Command.sendChatMessage("Placed");
        if (!AutoSnowGolem.mc.field_71441_e.func_180495_p(lookPos).func_185904_a().func_76222_j()) {
            return false;
        }
        if (!AutoSnowGolem.mc.field_71441_e.func_180495_p(lookPos.func_177982_a(0, 1, 0)).func_185904_a().func_76222_j()) {
            return false;
        }
        if (!AutoSnowGolem.mc.field_71441_e.func_180495_p(lookPos.func_177982_a(0, 2, 0)).func_185904_a().func_76222_j()) {
            return false;
        }
        if (AutoSnowGolem.mc.field_71441_e.func_180495_p(lookPos.func_177982_a(0, -1, 0)).func_177230_c() instanceof BlockAir) {
            return false;
        }
        if (AutoSnowGolem.mc.field_71441_e.func_180495_p(lookPos.func_177982_a(0, -1, 0)).func_177230_c() instanceof BlockLiquid) {
            return false;
        }
        if (AutoSnowGolem.mc.field_71439_g.func_174791_d().func_72438_d(new Vec3d((Vec3i)lookPos)) > this.placeRange.getValue()) {
            return false;
        }
        final Block block = AutoSnowGolem.mc.field_71441_e.func_180495_p(lookPos.func_177982_a(0, -1, 0)).func_177230_c();
        return !AutoSnowGolem.blackList.contains(block) && !AutoSnowGolem.shulkerList.contains(block) && AutoSnowGolem.mc.field_71439_g.func_174791_d().func_72438_d(new Vec3d((Vec3i)lookPos).func_72441_c(0.0, 1.0, 0.0)) <= this.placeRange.getValue();
    }
    
    private static void placeBlock(final BlockPos pos) {
        if (!AutoSnowGolem.mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76222_j()) {
            return;
        }
        if (!checkForNeighbours(pos)) {
            return;
        }
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.func_177972_a(side);
            final EnumFacing side2 = side.func_176734_d();
            if (AutoSnowGolem.mc.field_71441_e.func_180495_p(neighbor).func_177230_c().func_176209_a(AutoSnowGolem.mc.field_71441_e.func_180495_p(neighbor), false)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(side2.func_176730_m()).func_186678_a(0.5));
                final Block neighborPos = AutoSnowGolem.mc.field_71441_e.func_180495_p(neighbor).func_177230_c();
                if (AutoSnowGolem.blackList.contains(neighborPos) || AutoSnowGolem.shulkerList.contains(neighborPos)) {
                    AutoSnowGolem.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)AutoSnowGolem.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
                    AutoSnowGolem.isSneaking = true;
                }
                BlockInteractionHelper.faceVectorPacketInstant(hitVec);
                AutoSnowGolem.mc.field_71442_b.func_187099_a(AutoSnowGolem.mc.field_71439_g, AutoSnowGolem.mc.field_71441_e, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                AutoSnowGolem.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                AutoSnowGolem.mc.field_71467_ac = 4;
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
            if (!AutoSnowGolem.mc.field_71441_e.func_180495_p(neighbour).func_185904_a().func_76222_j()) {
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
    
    private enum PlaceMode
    {
        AUTO, 
        LOOK;
    }
    
    private enum DebugMsgs
    {
        NONE, 
        IMPORTANT, 
        ALL;
    }
}
