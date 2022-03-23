// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import net.minecraft.nbt.NBTTagList;
import net.minecraft.enchantment.Enchantment;
import java.util.Arrays;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.IBlockAccess;
import net.minecraft.client.renderer.GlStateManager;
import meow.candycat.uwu.util.MathUtil;
import meow.candycat.uwu.util.UwUGodTessellator;
import meow.candycat.uwu.event.events.RenderEvent;
import java.util.Objects;
import net.minecraft.potion.Potion;
import meow.candycat.uwu.module.modules.util.ResistanceDetector;
import net.minecraft.util.math.MathHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.CombatRules;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.Explosion;
import net.minecraft.entity.EntityLivingBase;
import meow.candycat.uwu.util.BlockInteractionHelper;
import net.minecraft.block.Block;
import java.util.Iterator;
import meow.candycat.uwu.util.MultiThreading;
import meow.candycat.uwu.module.ModuleManager;
import meow.candycat.uwu.util.BedSaver;
import java.util.List;
import meow.candycat.uwu.util.EntityUtil;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import meow.candycat.uwu.util.Friends;
import java.util.Collection;
import net.minecraft.client.network.NetHandlerPlayClient;
import java.awt.Font;
import java.util.function.Predicate;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.init.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.inventory.ClickType;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.gui.font.CFontRenderer;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.EventPlayerPostMotionUpdate;
import meow.candycat.eventsystem.listener.Listener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "BedAura", category = Category.COMBAT)
public class BedAura extends Module
{
    public Setting<Boolean> autoSwitch;
    public Setting<Double> range;
    public Setting<Double> distance;
    public Setting<Boolean> prediction;
    public Setting<Integer> predictedTicks;
    public Setting<Boolean> refill;
    public Setting<Integer> minDmg;
    public Setting<Integer> minDifference;
    public Setting<Integer> maxSelfDmg;
    public boolean shouldPlace;
    public BlockPos blockPos;
    public EnumFacing direction;
    EntityPlayer renderEnt;
    @EventHandler
    private Listener<EventPlayerPostMotionUpdate> awa;
    CFontRenderer fontRenderer;
    
    public BedAura() {
        this.autoSwitch = this.register(Settings.b("AutoSwitch"));
        this.range = this.register(Settings.d("Range", 5.0));
        this.distance = this.register((Setting<Double>)Settings.doubleBuilder("EnemyBedDist").withMinimum(1.0).withValue(8.0).withMaximum(13.0).build());
        this.prediction = this.register(Settings.b("Prediction", true));
        this.predictedTicks = this.register(Settings.integerBuilder("PredictedTicks").withMinimum(0).withValue(0).withMaximum(20).withVisibility(v -> this.prediction.getValue()).build());
        this.refill = this.register(Settings.b("Refill", true));
        this.minDmg = this.register((Setting<Integer>)Settings.integerBuilder("MinDmg").withMinimum(0).withValue(4).withMaximum(20).build());
        this.minDifference = this.register((Setting<Integer>)Settings.integerBuilder("MinAdvantage").withMinimum(0).withValue(4).withMaximum(20).build());
        this.maxSelfDmg = this.register((Setting<Integer>)Settings.integerBuilder("MaxSelfDmg").withMinimum(0).withValue(4).withMaximum(20).build());
        this.shouldPlace = false;
        this.blockPos = null;
        this.direction = null;
        this.renderEnt = null;
        int bedSlot;
        int l;
        boolean offhand;
        boolean switched;
        int i;
        int j;
        Vec3d vec;
        float f;
        float f2;
        float f3;
        boolean sneak;
        NetHandlerPlayClient field_71174_a;
        final CPacketAnimation cPacketAnimation;
        NetHandlerPlayClient field_71174_a2;
        final CPacketPlayerTryUseItemOnBlock cPacketPlayerTryUseItemOnBlock;
        NetHandlerPlayClient field_71174_a3;
        final CPacketPlayerTryUseItemOnBlock cPacketPlayerTryUseItemOnBlock2;
        NetHandlerPlayClient field_71174_a4;
        final CPacketAnimation cPacketAnimation2;
        NetHandlerPlayClient field_71174_a5;
        final CPacketPlayerTryUseItemOnBlock cPacketPlayerTryUseItemOnBlock3;
        this.awa = new Listener<EventPlayerPostMotionUpdate>(e -> {
            bedSlot = ((BedAura.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151104_aV) ? BedAura.mc.field_71439_g.field_71071_by.field_70461_c : -1);
            if (bedSlot == -1) {
                l = 0;
                while (l < 9) {
                    if (BedAura.mc.field_71439_g.field_71071_by.func_70301_a(l).func_77973_b() == Items.field_151104_aV) {
                        bedSlot = l;
                        break;
                    }
                    else {
                        ++l;
                    }
                }
            }
            offhand = false;
            if (BedAura.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_151104_aV) {
                offhand = true;
            }
            if (this.shouldPlace) {
                this.shouldPlace = false;
                switched = false;
                if (bedSlot == -1 && !offhand) {
                    if (!(BedAura.mc.field_71462_r instanceof GuiContainer)) {
                        i = 9;
                        while (i < 35) {
                            if (BedAura.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() == Items.field_151104_aV) {
                                BedAura.mc.field_71442_b.func_187098_a(BedAura.mc.field_71439_g.field_71069_bz.field_75152_c, i, 0, ClickType.QUICK_MOVE, (EntityPlayer)BedAura.mc.field_71439_g);
                                break;
                            }
                            else {
                                ++i;
                            }
                        }
                    }
                    bedSlot = ((BedAura.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151104_aV) ? BedAura.mc.field_71439_g.field_71071_by.field_70461_c : -1);
                    if (bedSlot == -1) {
                        j = 0;
                        while (j < 9) {
                            if (BedAura.mc.field_71439_g.field_71071_by.func_70301_a(j).func_77973_b() == Items.field_151104_aV) {
                                bedSlot = j;
                                break;
                            }
                            else {
                                ++j;
                            }
                        }
                    }
                    offhand = false;
                    if (BedAura.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_151104_aV) {
                        offhand = true;
                    }
                    if (bedSlot == -1 && !offhand) {
                        return;
                    }
                }
                if (!offhand && BedAura.mc.field_71439_g.field_71071_by.field_70461_c != bedSlot && this.autoSwitch.getValue()) {
                    BedAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(bedSlot));
                    switched = true;
                }
                vec = new Vec3d((Vec3i)this.blockPos).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(EnumFacing.DOWN.func_176730_m()).func_186678_a(0.5));
                f = (float)(vec.field_72450_a - this.blockPos.func_177958_n());
                f2 = (float)(vec.field_72448_b - this.blockPos.func_177956_o());
                f3 = (float)(vec.field_72449_c - this.blockPos.func_177952_p());
                sneak = false;
                if (BedAura.mc.field_71439_g.func_70093_af()) {
                    sneak = true;
                    BedAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)BedAura.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
                }
                if (BedAura.mc.field_71441_e.func_180495_p(this.blockPos.func_177984_a().func_177972_a(this.direction)).func_177230_c() == Blocks.field_150324_C) {
                    field_71174_a = BedAura.mc.field_71439_g.field_71174_a;
                    new CPacketAnimation(offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                    field_71174_a.func_147297_a((Packet)cPacketAnimation);
                    field_71174_a2 = BedAura.mc.field_71439_g.field_71174_a;
                    new CPacketPlayerTryUseItemOnBlock(this.blockPos.func_177984_a().func_177972_a(this.direction), EnumFacing.UP, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f);
                    field_71174_a2.func_147297_a((Packet)cPacketPlayerTryUseItemOnBlock);
                }
                BedAura.mc.field_71439_g.func_184609_a(offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                field_71174_a3 = BedAura.mc.field_71439_g.field_71174_a;
                new CPacketPlayerTryUseItemOnBlock(this.blockPos, EnumFacing.UP, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, f, f2, f3);
                field_71174_a3.func_147297_a((Packet)cPacketPlayerTryUseItemOnBlock2);
                field_71174_a4 = BedAura.mc.field_71439_g.field_71174_a;
                new CPacketAnimation(offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                field_71174_a4.func_147297_a((Packet)cPacketAnimation2);
                field_71174_a5 = BedAura.mc.field_71439_g.field_71174_a;
                new CPacketPlayerTryUseItemOnBlock(this.blockPos.func_177984_a(), EnumFacing.UP, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f);
                field_71174_a5.func_147297_a((Packet)cPacketPlayerTryUseItemOnBlock3);
                if (switched) {
                    BedAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(BedAura.mc.field_71439_g.field_71071_by.field_70461_c));
                }
                if (sneak) {
                    BedAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)BedAura.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
                }
            }
            return;
        }, (Predicate<EventPlayerPostMotionUpdate>[])new Predicate[0]);
        this.fontRenderer = new CFontRenderer(new Font("Verdana", 0, 18), true, true);
    }
    
    @Override
    public void onUpdate() {
        final ArrayList<EntityPlayer> entities;
        final List<BlockPos> sphereBlocks;
        final Iterator<EntityPlayer> iterator;
        Entity x;
        float f;
        float f2;
        Entity y;
        final List<BedSaver> bedPos;
        double damage;
        final Iterator<EntityPlayer> iterator2;
        EntityPlayer entity;
        final Iterator<BedSaver> iterator3;
        BedSaver pos;
        int i;
        BlockPos boost2;
        double d;
        MultiThreading.runAsync(() -> {
            entities = new ArrayList<EntityPlayer>((Collection<? extends EntityPlayer>)BedAura.mc.field_71441_e.field_73010_i.stream().filter(e -> !Friends.isFriend(((EntityPlayer)e).func_70005_c_()) && e != BedAura.mc.field_71439_g && ((EntityPlayer)e).func_110143_aJ() > 0.0f && !((EntityPlayer)e).field_70128_L).collect(Collectors.toList()));
            sphereBlocks = this.getSphere(BedAura.mc.field_71439_g.func_180425_c(), this.range.getValue().floatValue(), this.range.getValue().intValue(), false, true, 0);
            if (BedAura.mc.field_71439_g != null && BedAura.mc.field_71441_e != null && this.prediction.getValue()) {
                entities.iterator();
                while (iterator.hasNext()) {
                    x = (Entity)iterator.next();
                    if (x.func_70032_d((Entity)BedAura.mc.field_71439_g) > 15.0f) {
                        continue;
                    }
                    else if (!(x instanceof EntityPlayer)) {
                        continue;
                    }
                    else {
                        f = x.field_70130_N / 2.0f;
                        f2 = x.field_70131_O;
                        x.func_174826_a(new AxisAlignedBB(x.field_70165_t - f, x.field_70163_u, x.field_70161_v - f, x.field_70165_t + f, x.field_70163_u + f2, x.field_70161_v + f));
                        y = EntityUtil.getPredictedPosition(x, this.predictedTicks.getValue());
                        x.func_174826_a(y.func_174813_aQ());
                    }
                }
            }
            bedPos = this.canPlaceBed(entities, sphereBlocks);
            damage = this.minDmg.getValue();
            entities.iterator();
            while (iterator2.hasNext()) {
                entity = iterator2.next();
                if (entity.func_184812_l_()) {
                    continue;
                }
                else {
                    bedPos.iterator();
                    while (iterator3.hasNext()) {
                        pos = iterator3.next();
                        if (entity.func_174818_b(pos.blockPos) > Math.pow(this.distance.getValue(), 2.0)) {
                            continue;
                        }
                        else {
                            for (i = 0; i < pos.canPlaceDirection.size(); ++i) {
                                boost2 = pos.blockPos.func_177982_a(0, 1, 0).func_177972_a((EnumFacing)pos.canPlaceDirection.get(i));
                                d = calculateDamage(boost2.field_177962_a + 0.5, boost2.field_177960_b + 0.5, boost2.field_177961_c + 0.5, (Entity)entity);
                                if (d >= pos.selfDamage.get(i) || d > entity.func_110143_aJ() + entity.func_110139_bj()) {
                                    if (d >= damage) {
                                        if (d - pos.selfDamage.get(i) >= this.minDifference.getValue()) {
                                            damage = d;
                                            this.blockPos = pos.blockPos;
                                            this.direction = pos.canPlaceDirection.get(i);
                                            this.renderEnt = entity;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (damage != this.minDmg.getValue()) {
                this.shouldPlace = true;
                if (this.direction == EnumFacing.EAST) {
                    ((Aimbot)ModuleManager.getModuleByName("Aimbot")).setRotation(-91.0f, BedAura.mc.field_71439_g.field_70125_A);
                }
                else if (this.direction == EnumFacing.NORTH) {
                    ((Aimbot)ModuleManager.getModuleByName("Aimbot")).setRotation(179.0f, BedAura.mc.field_71439_g.field_70125_A);
                }
                else if (this.direction == EnumFacing.WEST) {
                    ((Aimbot)ModuleManager.getModuleByName("Aimbot")).setRotation(89.0f, BedAura.mc.field_71439_g.field_70125_A);
                }
                else {
                    ((Aimbot)ModuleManager.getModuleByName("Aimbot")).setRotation(-1.0f, BedAura.mc.field_71439_g.field_70125_A);
                }
            }
        });
    }
    
    public void onEnable() {
        this.shouldPlace = false;
        this.blockPos = null;
        if (BedAura.mc.field_71439_g == null || BedAura.mc.field_71441_e == null) {
            this.disable();
        }
    }
    
    public void onDisable() {
        if (BedAura.mc.field_71439_g != null && BedAura.mc.field_71441_e != null) {
            ((Aimbot)ModuleManager.getModuleByName("Aimbot")).resetRotation();
        }
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
    
    public List<BedSaver> canPlaceBed(final List<EntityPlayer> entityPlayerList, final List<BlockPos> blockPosList) {
        final List<BedSaver> bedSaverList = new ArrayList<BedSaver>();
        final List<EnumFacing> list = new ArrayList<EnumFacing>();
        final List<Double> damage = new ArrayList<Double>();
        for (final BlockPos pos : blockPosList) {
            boolean x = false;
            for (final EntityPlayer entityPlayer : entityPlayerList) {
                if (entityPlayer.func_174818_b(pos) <= Math.pow(this.distance.getValue(), 2.0)) {
                    x = true;
                    break;
                }
            }
            if (!x) {
                continue;
            }
            for (final EnumFacing facing : EnumFacing.field_176754_o) {
                final BlockPos side = pos.func_177972_a(facing);
                final BlockPos boost = pos.func_177982_a(0, 1, 0);
                final BlockPos boost2 = pos.func_177982_a(0, 1, 0).func_177972_a(facing);
                final Block boostBlock = BedAura.mc.field_71441_e.func_180495_p(boost).func_177230_c();
                final Block boostBlock2 = BedAura.mc.field_71441_e.func_180495_p(boost2).func_177230_c();
                if ((boostBlock == Blocks.field_150350_a || boostBlock == Blocks.field_150324_C) && (boostBlock2 == Blocks.field_150350_a || boostBlock2 == Blocks.field_150324_C) && BedAura.mc.field_71441_e.func_180495_p(side).func_185904_a().func_76218_k() && BedAura.mc.field_71441_e.func_180495_p(side).func_185917_h() && BedAura.mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76218_k() && BedAura.mc.field_71441_e.func_180495_p(pos).func_185917_h()) {
                    final double selfDmg = calculateDamage(boost2.field_177962_a + 0.5, boost2.field_177960_b + 0.5, boost2.field_177961_c + 0.5, (Entity)BedAura.mc.field_71439_g);
                    if (selfDmg <= this.maxSelfDmg.getValue()) {
                        if (selfDmg < BedAura.mc.field_71439_g.func_110143_aJ() + BedAura.mc.field_71439_g.func_110139_bj() + 2.0f) {
                            list.add(facing);
                            damage.add(selfDmg);
                        }
                    }
                }
            }
            if (list.size() <= 0) {
                continue;
            }
            bedSaverList.add(new BedSaver(pos, list, damage));
            list.clear();
            damage.clear();
        }
        return bedSaverList;
    }
    
    public static float calculateDamage(final double posX, final double posY, final double posZ, final Entity entity) {
        final float doubleExplosionSize = 10.0f;
        final Vec3d playerBoundingBox = entity.field_70121_D.func_189972_c();
        final double distancedsize = BlockInteractionHelper.blockDistance(posX, posY, posZ, playerBoundingBox.field_72450_a, playerBoundingBox.field_72448_b, playerBoundingBox.field_72449_c) / 10.0;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        final double blockDensity = getBlockDensity3(vec3d, entity.func_174813_aQ(), entity.field_70170_p);
        final double v = (1.0 - distancedsize) * blockDensity;
        final float damage = (float)(int)((v * v + v) / 2.0 * 7.0 * 10.0 + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion((World)BedAura.mc.field_71441_e, (Entity)null, posX, posY, posZ, 5.0f, true, true));
        }
        return (float)finald;
    }
    
    public static float getBlockDensity(final Vec3d vec, final AxisAlignedBB bbox) {
        final Vec3d bboxDelta = new Vec3d(1.0 / ((bbox.field_72336_d - bbox.field_72340_a) * 2.0 + 1.0), 1.0 / ((bbox.field_72337_e - bbox.field_72338_b) * 2.0 + 1.0), 1.0 / ((bbox.field_72334_f - bbox.field_72339_c) * 2.0 + 1.0));
        final double xOff = (1.0 - Math.floor(1.0 / bboxDelta.field_72450_a) * bboxDelta.field_72450_a) / 2.0;
        final double zOff = (1.0 - Math.floor(1.0 / bboxDelta.field_72449_c) * bboxDelta.field_72449_c) / 2.0;
        if (bboxDelta.field_72450_a >= 0.0 && bboxDelta.field_72448_b >= 0.0 && bboxDelta.field_72449_c >= 0.0) {
            int nonSolid = 0;
            int total = 0;
            for (double x = 0.0; x <= 1.0; x += bboxDelta.field_72450_a) {
                for (double y = 0.0; y <= 1.0; y += bboxDelta.field_72448_b) {
                    for (double z = 0.0; z <= 1.0; z += bboxDelta.field_72449_c) {
                        final Vec3d startPos = new Vec3d(xOff + bbox.field_72340_a + (bbox.field_72336_d - bbox.field_72340_a) * x, bbox.field_72338_b + (bbox.field_72337_e - bbox.field_72338_b) * y, zOff + bbox.field_72339_c + (bbox.field_72334_f - bbox.field_72339_c) * z);
                        if (!rayTraceSolidCheck(startPos, vec, true)) {
                            ++nonSolid;
                        }
                        ++total;
                    }
                }
            }
            return (float)(nonSolid / total);
        }
        return 0.0f;
    }
    
    public static float getBlockDensity2(final Vec3d vec, final AxisAlignedBB bb, final World world) {
        final double d0 = 1.0 / ((bb.field_72336_d - bb.field_72340_a) * 2.0 + 1.0);
        final double d2 = 1.0 / ((bb.field_72337_e - bb.field_72338_b) * 2.0 + 1.0);
        final double d3 = 1.0 / ((bb.field_72334_f - bb.field_72339_c) * 2.0 + 1.0);
        final double d4 = (1.0 - Math.floor(1.0 / d0) * d0) / 2.0;
        final double d5 = (1.0 - Math.floor(1.0 / d3) * d3) / 2.0;
        if (d0 >= 0.0 && d2 >= 0.0 && d3 >= 0.0) {
            int j2 = 0;
            int k2 = 0;
            for (float f = 0.0f; f <= 1.0f; f += (float)d0) {
                for (float f2 = 0.0f; f2 <= 1.0f; f2 += (float)d2) {
                    for (float f3 = 0.0f; f3 <= 1.0f; f3 += (float)d3) {
                        final double d6 = bb.field_72340_a + (bb.field_72336_d - bb.field_72340_a) * f;
                        final double d7 = bb.field_72338_b + (bb.field_72337_e - bb.field_72338_b) * f2;
                        final double d8 = bb.field_72339_c + (bb.field_72334_f - bb.field_72339_c) * f3;
                        if (world.func_72933_a(new Vec3d(d6 + d4, d7, d8 + d5), vec) == null || (world.func_72933_a(new Vec3d(d6 + d4, d7, d8 + d5), vec).field_72313_a == RayTraceResult.Type.BLOCK && BedAura.mc.field_71441_e.func_180495_p(world.func_72933_a(new Vec3d(d6 + d4, d7, d8 + d5), vec).func_178782_a()).func_177230_c() == Blocks.field_150324_C)) {
                            ++j2;
                        }
                        ++k2;
                    }
                }
            }
            return j2 / (float)k2;
        }
        return 0.0f;
    }
    
    public static float getBlockDensity3(final Vec3d vec, final AxisAlignedBB bb, final World world) {
        final double d0 = 1.0 / ((bb.field_72336_d - bb.field_72340_a) * 2.0 + 1.0);
        final double d2 = 1.0 / ((bb.field_72337_e - bb.field_72338_b) * 2.0 + 1.0);
        final double d3 = 1.0 / ((bb.field_72334_f - bb.field_72339_c) * 2.0 + 1.0);
        final double d4 = (1.0 - Math.floor(1.0 / d0) * d0) / 2.0;
        final double d5 = (1.0 - Math.floor(1.0 / d3) * d3) / 2.0;
        if (d0 >= 0.0 && d2 >= 0.0 && d3 >= 0.0) {
            int j2 = 0;
            int k2 = 0;
            for (float f = 0.0f; f <= 1.0f; f += (float)d0) {
                for (float f2 = 0.0f; f2 <= 1.0f; f2 += (float)d2) {
                    for (float f3 = 0.0f; f3 <= 1.0f; f3 += (float)d3) {
                        final double d6 = bb.field_72340_a + (bb.field_72336_d - bb.field_72340_a) * f;
                        final double d7 = bb.field_72338_b + (bb.field_72337_e - bb.field_72338_b) * f2;
                        final double d8 = bb.field_72339_c + (bb.field_72334_f - bb.field_72339_c) * f3;
                        if (world.func_72933_a(new Vec3d(d6 + d4, d7, d8 + d5), vec) == null || (world.func_72933_a(new Vec3d(d6 + d4, d7, d8 + d5), vec).field_72313_a == RayTraceResult.Type.BLOCK && BedAura.mc.field_71441_e.func_180495_p(world.func_72933_a(new Vec3d(d6 + d4, d7, d8 + d5), vec).func_178782_a()).func_177230_c() == Blocks.field_150324_C)) {
                            ++j2;
                        }
                        ++k2;
                    }
                }
            }
            return j2 / (float)k2;
        }
        return 0.0f;
    }
    
    public static float getBlastReduction(final EntityLivingBase entity, float damage, final Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            final DamageSource ds = DamageSource.func_94539_a(explosion);
            damage = CombatRules.func_189427_a(damage, (float)ep.func_70658_aO(), (float)ep.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
            final int k = getEnchantmentModifierDamage(ep.func_184193_aE(), ds);
            final float f = MathHelper.func_76131_a((float)k, 0.0f, 20.0f);
            damage *= 1.0f - f / 25.0f;
            final ResistanceDetector w = new ResistanceDetector();
            if (entity.func_70644_a((Potion)Objects.requireNonNull(Potion.func_188412_a(11))) || entity.func_110139_bj() >= 9.0f || w.resistanceList.containsKey(entity.func_70005_c_())) {
                damage -= damage / 5.0f;
            }
            return damage;
        }
        damage = CombatRules.func_189427_a(damage, (float)entity.func_70658_aO(), (float)entity.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
        return damage;
    }
    
    @Override
    public void onWorldRender(final RenderEvent event) {
        if (this.shouldPlace) {
            final BlockPos render = this.blockPos.func_177984_a().func_177972_a(this.direction);
            UwUGodTessellator.prepare(7);
            final Vec3d interp = MathUtil.interpolateEntity((Entity)BedAura.mc.field_71439_g, BedAura.mc.func_184121_ak());
            final AxisAlignedBB renderPos = new AxisAlignedBB((double)render.field_177962_a, (double)render.field_177960_b, (double)render.field_177961_c, (double)(render.field_177962_a + 1), render.field_177960_b + 0.5625, (double)(render.field_177961_c + 1));
            try {
                UwUGodTessellator.drawBox2(renderPos.func_72317_d(-interp.field_72450_a, -interp.field_72448_b, -interp.field_72449_c), 255, 148, 231, 128, 63);
            }
            catch (Exception ex) {}
            UwUGodTessellator.release();
            GlStateManager.func_179094_E();
            try {
                UwUGodTessellator.glBillboardDistanceScaled(render.func_177958_n() + 0.5f, render.func_177956_o() + 0.5f, render.func_177952_p() + 0.5f, (EntityPlayer)BedAura.mc.field_71439_g, 1.0f);
                final float damage = calculateDamage(render.func_177958_n() + 0.5, render.func_177956_o() + 0.5, render.func_177952_p() + 0.5, (Entity)this.renderEnt);
                final float damage2 = calculateDamage(render.func_177958_n() + 0.5, render.func_177956_o() + 0.5, render.func_177952_p() + 0.5, (Entity)BedAura.mc.field_71439_g);
                final String damageText = ((Math.floor(damage) == damage) ? Integer.valueOf((int)damage) : String.format("%.1f", damage)) + "";
                final String damageText2 = ((Math.floor(damage2) == damage2) ? Integer.valueOf((int)damage2) : String.format("%.1f", damage2)) + "";
                GlStateManager.func_179097_i();
                GlStateManager.func_179137_b(-(this.fontRenderer.getStringWidth(damageText + "/" + damageText2) / 2.0), 0.0, 0.0);
                this.fontRenderer.drawStringWithShadow("§b" + damageText + "/" + damageText2, 0.0, 10.0, -5592406);
                GlStateManager.func_179126_j();
            }
            catch (Exception ex2) {}
            GlStateManager.func_179121_F();
        }
    }
    
    @Override
    public String getHudInfo() {
        return this.shouldPlace ? "Killing" : "Idle";
    }
    
    public static boolean rayTraceSolidCheck(Vec3d start, final Vec3d end, final boolean shouldIgnore) {
        if (!Double.isNaN(start.field_72450_a) && !Double.isNaN(start.field_72448_b) && !Double.isNaN(start.field_72449_c) && !Double.isNaN(end.field_72450_a) && !Double.isNaN(end.field_72448_b) && !Double.isNaN(end.field_72449_c)) {
            int currX = MathHelper.func_76128_c(start.field_72450_a);
            int currY = MathHelper.func_76128_c(start.field_72448_b);
            int currZ = MathHelper.func_76128_c(start.field_72449_c);
            final int endX = MathHelper.func_76128_c(end.field_72450_a);
            final int endY = MathHelper.func_76128_c(end.field_72448_b);
            final int endZ = MathHelper.func_76128_c(end.field_72449_c);
            BlockPos blockPos = new BlockPos(currX, currY, currZ);
            IBlockState blockState = BedAura.mc.field_71441_e.func_180495_p(blockPos);
            Block block = blockState.func_177230_c();
            if (blockState.func_185890_d((IBlockAccess)BedAura.mc.field_71441_e, blockPos) != Block.field_185506_k && block.func_176209_a(blockState, false) && (getBlocks().contains(block) || !shouldIgnore)) {
                return true;
            }
            final double seDeltaX = end.field_72450_a - start.field_72450_a;
            final double seDeltaY = end.field_72448_b - start.field_72448_b;
            final double seDeltaZ = end.field_72449_c - start.field_72449_c;
            int steps = 200;
            while (steps-- >= 0) {
                if (Double.isNaN(start.field_72450_a) || Double.isNaN(start.field_72448_b) || Double.isNaN(start.field_72449_c)) {
                    return false;
                }
                if (currX == endX && currY == endY && currZ == endZ) {
                    return false;
                }
                boolean unboundedX = true;
                boolean unboundedY = true;
                boolean unboundedZ = true;
                double stepX = 999.0;
                double stepY = 999.0;
                double stepZ = 999.0;
                double deltaX = 999.0;
                double deltaY = 999.0;
                double deltaZ = 999.0;
                if (endX > currX) {
                    stepX = currX + 1.0;
                }
                else if (endX < currX) {
                    stepX = currX;
                }
                else {
                    unboundedX = false;
                }
                if (endY > currY) {
                    stepY = currY + 1.0;
                }
                else if (endY < currY) {
                    stepY = currY;
                }
                else {
                    unboundedY = false;
                }
                if (endZ > currZ) {
                    stepZ = currZ + 1.0;
                }
                else if (endZ < currZ) {
                    stepZ = currZ;
                }
                else {
                    unboundedZ = false;
                }
                if (unboundedX) {
                    deltaX = (stepX - start.field_72450_a) / seDeltaX;
                }
                if (unboundedY) {
                    deltaY = (stepY - start.field_72448_b) / seDeltaY;
                }
                if (unboundedZ) {
                    deltaZ = (stepZ - start.field_72449_c) / seDeltaZ;
                }
                if (deltaX == 0.0) {
                    deltaX = -1.0E-4;
                }
                if (deltaY == 0.0) {
                    deltaY = -1.0E-4;
                }
                if (deltaZ == 0.0) {
                    deltaZ = -1.0E-4;
                }
                EnumFacing facing;
                if (deltaX < deltaY && deltaX < deltaZ) {
                    facing = ((endX > currX) ? EnumFacing.WEST : EnumFacing.EAST);
                    start = new Vec3d(stepX, start.field_72448_b + seDeltaY * deltaX, start.field_72449_c + seDeltaZ * deltaX);
                }
                else if (deltaY < deltaZ) {
                    facing = ((endY > currY) ? EnumFacing.DOWN : EnumFacing.UP);
                    start = new Vec3d(start.field_72450_a + seDeltaX * deltaY, stepY, start.field_72449_c + seDeltaZ * deltaY);
                }
                else {
                    facing = ((endZ > currZ) ? EnumFacing.NORTH : EnumFacing.SOUTH);
                    start = new Vec3d(start.field_72450_a + seDeltaX * deltaZ, start.field_72448_b + seDeltaY * deltaZ, stepZ);
                }
                currX = MathHelper.func_76128_c(start.field_72450_a) - ((facing == EnumFacing.EAST) ? 1 : 0);
                currY = MathHelper.func_76128_c(start.field_72448_b) - ((facing == EnumFacing.UP) ? 1 : 0);
                currZ = MathHelper.func_76128_c(start.field_72449_c) - ((facing == EnumFacing.SOUTH) ? 1 : 0);
                blockPos = new BlockPos(currX, currY, currZ);
                blockState = BedAura.mc.field_71441_e.func_180495_p(blockPos);
                block = blockState.func_177230_c();
                if (block.func_176209_a(blockState, false) && (getBlocks().contains(block) || !shouldIgnore)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static List<Block> getBlocks() {
        return Arrays.asList(Blocks.field_150343_Z, Blocks.field_150357_h, Blocks.field_150483_bI, Blocks.field_180401_cv, Blocks.field_150381_bn, Blocks.field_150477_bB, Blocks.field_150378_br, (Block)Blocks.field_150461_bJ, Blocks.field_150467_bQ);
    }
    
    public static float getDamageMultiplied(final float damage) {
        final int diff = BedAura.mc.field_71441_e.func_175659_aa().func_151525_a();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }
    
    public static int getEnchantmentModifierDamage(final Iterable<ItemStack> stacks, final DamageSource source) {
        final ModifierDamage ENCHANTMENT_MODIFIER_DAMAGE = new ModifierDamage();
        ENCHANTMENT_MODIFIER_DAMAGE.damageModifier = 0;
        ENCHANTMENT_MODIFIER_DAMAGE.source = source;
        applyEnchantmentModifierArray(ENCHANTMENT_MODIFIER_DAMAGE, stacks);
        return ENCHANTMENT_MODIFIER_DAMAGE.damageModifier;
    }
    
    private static void applyEnchantmentModifier(final AutoCrystal2.IModifier modifier, final ItemStack stack) {
        if (!stack.func_190926_b()) {
            final NBTTagList nbttaglist = stack.func_77986_q();
            for (int i = 0; i < nbttaglist.func_74745_c(); ++i) {
                final int j = nbttaglist.func_150305_b(i).func_74765_d("id");
                final int k = nbttaglist.func_150305_b(i).func_74765_d("lvl");
                if (Enchantment.func_185262_c(j) != null) {
                    modifier.calculateModifier(Enchantment.func_185262_c(j), k);
                }
            }
        }
    }
    
    private static void applyEnchantmentModifierArray(final AutoCrystal2.IModifier modifier, final Iterable<ItemStack> stacks) {
        for (final ItemStack itemstack : stacks) {
            applyEnchantmentModifier(modifier, itemstack);
        }
    }
    
    static final class ModifierDamage implements AutoCrystal2.IModifier
    {
        public int damageModifier;
        public DamageSource source;
        
        private ModifierDamage() {
        }
        
        @Override
        public void calculateModifier(final Enchantment enchantmentIn, final int enchantmentLevel) {
            this.damageModifier += enchantmentIn.func_77318_a(enchantmentLevel, this.source);
        }
    }
    
    interface IModifier
    {
        void calculateModifier(final Enchantment p0, final int p1);
    }
}
