// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.block.BlockAir;
import java.util.Iterator;
import java.util.List;
import meow.candycat.uwu.module.ModuleManager;
import meow.candycat.uwu.module.modules.combat.CrystalAura;
import meow.candycat.uwu.command.Command;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.item.ItemBlock;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import meow.candycat.uwu.util.BlockInteractionHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import meow.candycat.uwu.setting.Settings;
import net.minecraft.util.math.BlockPos;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "AutoSpawner", category = Category.MISC, description = "Automatically spawns Withers, Iron Golems and Snowmen")
public class AutoSpawner extends Module
{
    private Setting<UseMode> useMode;
    private Setting<Boolean> party;
    private Setting<Boolean> partyWithers;
    private Setting<EntityMode> entityMode;
    private Setting<Float> placeRange;
    private Setting<Integer> delay;
    private Setting<Boolean> rotate;
    private Setting<Boolean> notifications;
    private static boolean isSneaking;
    private BlockPos placeTarget;
    private boolean rotationPlaceableX;
    private boolean rotationPlaceableZ;
    private int bodySlot;
    private int headSlot;
    private int buildStage;
    private int delayStep;
    
    public AutoSpawner() {
        this.useMode = this.register(Settings.e("Use Mode", UseMode.SPAM));
        this.party = this.register(Settings.b("Party", false));
        this.partyWithers = this.register(Settings.booleanBuilder("Withers").withValue(false).withVisibility(v -> this.party.getValue()).build());
        this.entityMode = this.register((Setting<EntityMode>)Settings.enumBuilder(EntityMode.class).withName("Entity Mode").withValue(EntityMode.SNOW).withVisibility(v -> !this.party.getValue()).build());
        this.placeRange = this.register((Setting<Float>)Settings.floatBuilder("Place Range").withMinimum(2.0f).withValue(3.5f).withMaximum(10.0f).build());
        this.delay = this.register(Settings.integerBuilder("Delay").withMinimum(12).withValue(20).withMaximum(100).withVisibility(v -> this.useMode.getValue().equals(UseMode.SPAM)).build());
        this.rotate = this.register(Settings.b("Rotate", true));
        this.notifications = this.register(Settings.b("Notifications", false));
    }
    
    private static void placeBlock(final BlockPos pos, final boolean rotate) {
        final EnumFacing side = getPlaceableSide(pos);
        if (side == null) {
            return;
        }
        final BlockPos neighbour = pos.func_177972_a(side);
        final EnumFacing opposite = side.func_176734_d();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(opposite.func_176730_m()).func_186678_a(0.5));
        final Block neighbourBlock = AutoSpawner.mc.field_71441_e.func_180495_p(neighbour).func_177230_c();
        if (!AutoSpawner.isSneaking && (BlockInteractionHelper.blackList.contains(neighbourBlock) || BlockInteractionHelper.shulkerList.contains(neighbourBlock))) {
            AutoSpawner.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)AutoSpawner.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
            AutoSpawner.isSneaking = true;
        }
        if (rotate) {
            BlockInteractionHelper.faceVectorPacketInstant(hitVec);
        }
        AutoSpawner.mc.field_71442_b.func_187099_a(AutoSpawner.mc.field_71439_g, AutoSpawner.mc.field_71441_e, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        AutoSpawner.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
        AutoSpawner.mc.field_71467_ac = 4;
    }
    
    private static EnumFacing getPlaceableSide(final BlockPos pos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.func_177972_a(side);
            if (AutoSpawner.mc.field_71441_e.func_180495_p(neighbour).func_177230_c().func_176209_a(AutoSpawner.mc.field_71441_e.func_180495_p(neighbour), false)) {
                final IBlockState blockState = AutoSpawner.mc.field_71441_e.func_180495_p(neighbour);
                if (!blockState.func_185904_a().func_76222_j() && !(blockState.func_177230_c() instanceof BlockTallGrass) && !(blockState.func_177230_c() instanceof BlockDeadBush)) {
                    return side;
                }
            }
        }
        return null;
    }
    
    @Override
    protected void onEnable() {
        if (AutoSpawner.mc.field_71439_g == null) {
            this.disable();
            return;
        }
        this.buildStage = 1;
        this.delayStep = 1;
    }
    
    private boolean checkBlocksInHotbar() {
        this.headSlot = -1;
        this.bodySlot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AutoSpawner.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack != ItemStack.field_190927_a) {
                if (this.entityMode.getValue().equals(EntityMode.WITHER)) {
                    if (stack.func_77973_b() == Items.field_151144_bL && stack.func_77952_i() == 1) {
                        if (AutoSpawner.mc.field_71439_g.field_71071_by.func_70301_a(i).field_77994_a >= 3) {
                            this.headSlot = i;
                        }
                        continue;
                    }
                    else {
                        if (!(stack.func_77973_b() instanceof ItemBlock)) {
                            continue;
                        }
                        final Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
                        if (block instanceof BlockSoulSand && AutoSpawner.mc.field_71439_g.field_71071_by.func_70301_a(i).field_77994_a >= 4) {
                            this.bodySlot = i;
                        }
                    }
                }
                if (this.entityMode.getValue().equals(EntityMode.IRON)) {
                    if (!(stack.func_77973_b() instanceof ItemBlock)) {
                        continue;
                    }
                    final Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
                    if ((block == Blocks.field_150428_aP || block == Blocks.field_150423_aK) && AutoSpawner.mc.field_71439_g.field_71071_by.func_70301_a(i).field_77994_a >= 1) {
                        this.headSlot = i;
                    }
                    if (block == Blocks.field_150339_S && AutoSpawner.mc.field_71439_g.field_71071_by.func_70301_a(i).field_77994_a >= 4) {
                        this.bodySlot = i;
                    }
                }
                if (this.entityMode.getValue().equals(EntityMode.SNOW)) {
                    if (stack.func_77973_b() instanceof ItemBlock) {
                        final Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
                        if ((block == Blocks.field_150428_aP || block == Blocks.field_150423_aK) && AutoSpawner.mc.field_71439_g.field_71071_by.func_70301_a(i).field_77994_a >= 1) {
                            this.headSlot = i;
                        }
                        if (block == Blocks.field_150433_aE && AutoSpawner.mc.field_71439_g.field_71071_by.func_70301_a(i).field_77994_a >= 2) {
                            this.bodySlot = i;
                        }
                    }
                }
            }
        }
        return this.bodySlot != -1 && this.headSlot != -1;
    }
    
    private boolean testStructure() {
        if (this.entityMode.getValue().equals(EntityMode.WITHER)) {
            return this.testWitherStructure();
        }
        if (this.entityMode.getValue().equals(EntityMode.IRON)) {
            return this.testIronGolemStructure();
        }
        return this.entityMode.getValue().equals(EntityMode.SNOW) && this.testSnowGolemStructure();
    }
    
    private boolean testWitherStructure() {
        boolean noRotationPlaceable = true;
        this.rotationPlaceableX = true;
        this.rotationPlaceableZ = true;
        boolean isShitGrass = false;
        if (AutoSpawner.mc.field_71441_e.func_180495_p(this.placeTarget) == null) {
            return false;
        }
        final Block block = AutoSpawner.mc.field_71441_e.func_180495_p(this.placeTarget).func_177230_c();
        if (block instanceof BlockTallGrass || block instanceof BlockDeadBush) {
            isShitGrass = true;
        }
        if (getPlaceableSide(this.placeTarget.func_177984_a()) == null) {
            return false;
        }
        for (final BlockPos pos : BodyParts.bodyBase) {
            if (this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos))) {
                noRotationPlaceable = false;
            }
        }
        for (final BlockPos pos : BodyParts.ArmsX) {
            if (this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos)) || this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos.func_177977_b()))) {
                this.rotationPlaceableX = false;
            }
        }
        for (final BlockPos pos : BodyParts.ArmsZ) {
            if (this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos)) || this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos.func_177977_b()))) {
                this.rotationPlaceableZ = false;
            }
        }
        for (final BlockPos pos : BodyParts.headsX) {
            if (this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos))) {
                this.rotationPlaceableX = false;
            }
        }
        for (final BlockPos pos : BodyParts.headsZ) {
            if (this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos))) {
                this.rotationPlaceableZ = false;
            }
        }
        return !isShitGrass && noRotationPlaceable && (this.rotationPlaceableX || this.rotationPlaceableZ);
    }
    
    private boolean testIronGolemStructure() {
        boolean noRotationPlaceable = true;
        this.rotationPlaceableX = true;
        this.rotationPlaceableZ = true;
        boolean isShitGrass = false;
        if (AutoSpawner.mc.field_71441_e.func_180495_p(this.placeTarget) == null) {
            return false;
        }
        final Block block = AutoSpawner.mc.field_71441_e.func_180495_p(this.placeTarget).func_177230_c();
        if (block instanceof BlockTallGrass || block instanceof BlockDeadBush) {
            isShitGrass = true;
        }
        if (getPlaceableSide(this.placeTarget.func_177984_a()) == null) {
            return false;
        }
        for (final BlockPos pos : BodyParts.bodyBase) {
            if (this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos))) {
                noRotationPlaceable = false;
            }
        }
        for (final BlockPos pos : BodyParts.ArmsX) {
            if (this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos)) || this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos.func_177977_b()))) {
                this.rotationPlaceableX = false;
            }
        }
        for (final BlockPos pos : BodyParts.ArmsZ) {
            if (this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos)) || this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos.func_177977_b()))) {
                this.rotationPlaceableZ = false;
            }
        }
        for (final BlockPos pos : BodyParts.head) {
            if (this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos))) {
                noRotationPlaceable = false;
            }
        }
        return !isShitGrass && noRotationPlaceable && (this.rotationPlaceableX || this.rotationPlaceableZ);
    }
    
    private boolean testSnowGolemStructure() {
        boolean noRotationPlaceable = true;
        boolean isShitGrass = false;
        if (AutoSpawner.mc.field_71441_e.func_180495_p(this.placeTarget) == null) {
            return false;
        }
        final Block block = AutoSpawner.mc.field_71441_e.func_180495_p(this.placeTarget).func_177230_c();
        if (block instanceof BlockTallGrass || block instanceof BlockDeadBush) {
            isShitGrass = true;
        }
        if (getPlaceableSide(this.placeTarget.func_177984_a()) == null) {
            return false;
        }
        for (final BlockPos pos : BodyParts.bodyBase) {
            if (this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos))) {
                noRotationPlaceable = false;
            }
        }
        for (final BlockPos pos : BodyParts.head) {
            if (this.placingIsBlocked(this.placeTarget.func_177971_a((Vec3i)pos))) {
                noRotationPlaceable = false;
            }
        }
        return !isShitGrass && noRotationPlaceable;
    }
    
    @Override
    public void onUpdate() {
        if (AutoSpawner.mc.field_71439_g == null) {
            return;
        }
        if (this.buildStage == 1) {
            AutoSpawner.isSneaking = false;
            this.rotationPlaceableX = false;
            this.rotationPlaceableZ = false;
            if (this.party.getValue()) {
                final Random random = new Random();
                int partyMode;
                if (this.partyWithers.getValue()) {
                    partyMode = random.nextInt(3);
                }
                else {
                    partyMode = random.nextInt(2);
                }
                if (partyMode == 0) {
                    this.entityMode.setValue(EntityMode.SNOW);
                }
                else if (partyMode == 1) {
                    this.entityMode.setValue(EntityMode.IRON);
                }
                else if (partyMode == 2) {
                    this.entityMode.setValue(EntityMode.WITHER);
                }
            }
            if (!this.checkBlocksInHotbar()) {
                if (!this.party.getValue()) {
                    if (this.notifications.getValue()) {
                        Command.sendChatMessage("[AutoSpawner] " + ChatFormatting.RED.toString() + "Blocks missing for: " + ChatFormatting.RESET.toString() + this.entityMode.getValue().toString() + ChatFormatting.RED.toString() + ", disabling.");
                    }
                    this.disable();
                }
                return;
            }
            final CrystalAura crystalAura = (CrystalAura)ModuleManager.getModuleByName("CrystalAura");
            final List<BlockPos> blockPosList = crystalAura.getSphere(AutoSpawner.mc.field_71439_g.func_180425_c().func_177977_b(), this.placeRange.getValue(), this.placeRange.getValue().intValue(), false, true, 0);
            boolean noPositionInArea = true;
            for (final BlockPos pos : blockPosList) {
                this.placeTarget = pos.func_177977_b();
                if (this.testStructure()) {
                    noPositionInArea = false;
                    break;
                }
            }
            if (noPositionInArea) {
                if (this.useMode.getValue().equals(UseMode.SINGLE)) {
                    if (this.notifications.getValue()) {
                        Command.sendChatMessage("[AutoSpawner] " + ChatFormatting.RED.toString() + "Position not valid, disabling.");
                    }
                    this.disable();
                }
                return;
            }
            AutoSpawner.mc.field_71439_g.field_71071_by.field_70461_c = this.bodySlot;
            for (final BlockPos pos2 : BodyParts.bodyBase) {
                placeBlock(this.placeTarget.func_177971_a((Vec3i)pos2), this.rotate.getValue());
            }
            if (this.entityMode.getValue().equals(EntityMode.WITHER) || this.entityMode.getValue().equals(EntityMode.IRON)) {
                if (this.rotationPlaceableX) {
                    for (final BlockPos pos2 : BodyParts.ArmsX) {
                        placeBlock(this.placeTarget.func_177971_a((Vec3i)pos2), this.rotate.getValue());
                    }
                }
                else if (this.rotationPlaceableZ) {
                    for (final BlockPos pos2 : BodyParts.ArmsZ) {
                        placeBlock(this.placeTarget.func_177971_a((Vec3i)pos2), this.rotate.getValue());
                    }
                }
            }
            this.buildStage = 2;
        }
        else if (this.buildStage == 2) {
            AutoSpawner.mc.field_71439_g.field_71071_by.field_70461_c = this.headSlot;
            if (this.entityMode.getValue().equals(EntityMode.WITHER)) {
                if (this.rotationPlaceableX) {
                    for (final BlockPos pos3 : BodyParts.headsX) {
                        placeBlock(this.placeTarget.func_177971_a((Vec3i)pos3), this.rotate.getValue());
                    }
                }
                else if (this.rotationPlaceableZ) {
                    for (final BlockPos pos3 : BodyParts.headsZ) {
                        placeBlock(this.placeTarget.func_177971_a((Vec3i)pos3), this.rotate.getValue());
                    }
                }
            }
            if (this.entityMode.getValue().equals(EntityMode.IRON) || this.entityMode.getValue().equals(EntityMode.SNOW)) {
                for (final BlockPos pos3 : BodyParts.head) {
                    placeBlock(this.placeTarget.func_177971_a((Vec3i)pos3), this.rotate.getValue());
                }
            }
            if (AutoSpawner.isSneaking) {
                AutoSpawner.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)AutoSpawner.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
                AutoSpawner.isSneaking = false;
            }
            if (this.useMode.getValue().equals(UseMode.SINGLE)) {
                this.disable();
            }
            this.buildStage = 3;
        }
        else if (this.buildStage == 3) {
            if (this.delayStep < this.delay.getValue()) {
                ++this.delayStep;
            }
            else {
                this.delayStep = 1;
                this.buildStage = 1;
            }
        }
    }
    
    private boolean placingIsBlocked(final BlockPos pos) {
        final Block block = AutoSpawner.mc.field_71441_e.func_180495_p(pos).func_177230_c();
        if (!(block instanceof BlockAir)) {
            return true;
        }
        for (final Entity entity : AutoSpawner.mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(pos))) {
            if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getHudInfo() {
        if (!this.party.getValue()) {
            return this.entityMode.getValue().toString();
        }
        if (this.partyWithers.getValue()) {
            return "PARTY WITHER";
        }
        return "PARTY";
    }
    
    private enum UseMode
    {
        SINGLE, 
        SPAM;
    }
    
    private enum EntityMode
    {
        SNOW, 
        IRON, 
        WITHER;
    }
    
    private static class BodyParts
    {
        private static final BlockPos[] bodyBase;
        private static final BlockPos[] ArmsX;
        private static final BlockPos[] ArmsZ;
        private static final BlockPos[] headsX;
        private static final BlockPos[] headsZ;
        private static final BlockPos[] head;
        
        static {
            bodyBase = new BlockPos[] { new BlockPos(0, 1, 0), new BlockPos(0, 2, 0) };
            ArmsX = new BlockPos[] { new BlockPos(-1, 2, 0), new BlockPos(1, 2, 0) };
            ArmsZ = new BlockPos[] { new BlockPos(0, 2, -1), new BlockPos(0, 2, 1) };
            headsX = new BlockPos[] { new BlockPos(0, 3, 0), new BlockPos(-1, 3, 0), new BlockPos(1, 3, 0) };
            headsZ = new BlockPos[] { new BlockPos(0, 3, 0), new BlockPos(0, 3, -1), new BlockPos(0, 3, 1) };
            head = new BlockPos[] { new BlockPos(0, 3, 0) };
        }
    }
}
