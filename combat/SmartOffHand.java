// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import net.minecraft.util.math.RayTraceResult;
import meow.candycat.uwu.util.BlockInteractionHelper;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import meow.candycat.uwu.module.ModuleManager;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemSword;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import meow.candycat.uwu.util.MultiThreading;
import meow.candycat.uwu.util.BedSaver;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.init.Blocks;
import java.util.Iterator;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.enchantment.Enchantment;
import java.util.Objects;
import net.minecraft.potion.Potion;
import meow.candycat.uwu.module.modules.util.ResistanceDetector;
import net.minecraft.util.math.MathHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.CombatRules;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.world.World;
import net.minecraft.world.Explosion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import java.util.List;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "SmartOffHand", category = Category.COMBAT, description = "smartoffhand that isnt smart")
public class SmartOffHand extends Module
{
    private Setting<Boolean> gap;
    private Setting<Boolean> crystal;
    private Setting<Boolean> beds;
    public Setting<Integer> detectrange;
    public Setting<Boolean> detectBed;
    public Setting<Integer> totemhealth;
    public double totemhealths;
    public double totemHealth;
    public double totemHealth2;
    int gaps;
    int crystals;
    int totem;
    int bed;
    
    public SmartOffHand() {
        this.gap = this.register(Settings.b("RightClickGapple", true));
        this.crystal = this.register(Settings.b("OffHandCrystalWhenCa", true));
        this.beds = this.register(Settings.b("OffhandBed", true));
        this.detectrange = this.register(Settings.i("Range", 6));
        this.detectBed = this.register(Settings.b("BedDetection", true));
        this.totemhealth = this.register((Setting<Integer>)Settings.integerBuilder("TotemHealth").withMinimum(1).withValue(4).withMaximum(36).build());
        this.totemhealths = 0.0;
        this.totemHealth = 0.0;
        this.totemHealth2 = 0.0;
        this.gaps = -1;
        this.crystals = -1;
        this.totem = -1;
        this.bed = -1;
    }
    
    public List<BlockPos> findCrystalBlocks(final List<BlockPos> blocks) {
        final NonNullList<BlockPos> positions = (NonNullList<BlockPos>)NonNullList.func_191196_a();
        positions.addAll((Collection)blocks.stream().filter((Predicate<? super Object>)this::canPlaceCrystal).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        return (List<BlockPos>)positions;
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
    
    public static float calculateDamage(final double posX, final double posY, final double posZ, final Entity entity) {
        final float doubleExplosionSize = 12.0f;
        final double distancedsize = entity.func_70011_f(posX, posY, posZ) / 12.0;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        final double blockDensity = entity.field_70170_p.func_72842_a(vec3d, entity.func_174813_aQ());
        final double v = (1.0 - distancedsize) * blockDensity;
        final float damage = (float)(int)((v * v + v) / 2.0 * 7.0 * 12.0 + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion((World)SmartOffHand.mc.field_71441_e, (Entity)null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finald;
    }
    
    public static float calculateDamage(final EntityEnderCrystal crystal, final Entity entity) {
        return calculateDamage(crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, entity);
    }
    
    public static float getDamageMultiplied(final float damage) {
        final int diff = SmartOffHand.mc.field_71441_e.func_175659_aa().func_151525_a();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
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
    
    public static int getEnchantmentModifierDamage(final Iterable<ItemStack> stacks, final DamageSource source) {
        final ModifierDamage ENCHANTMENT_MODIFIER_DAMAGE = new ModifierDamage();
        ENCHANTMENT_MODIFIER_DAMAGE.damageModifier = 0;
        ENCHANTMENT_MODIFIER_DAMAGE.source = source;
        applyEnchantmentModifierArray(ENCHANTMENT_MODIFIER_DAMAGE, stacks);
        return ENCHANTMENT_MODIFIER_DAMAGE.damageModifier;
    }
    
    private static void applyEnchantmentModifier(final IModifier modifier, final ItemStack stack) {
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
    
    private static void applyEnchantmentModifierArray(final IModifier modifier, final Iterable<ItemStack> stacks) {
        for (final ItemStack itemstack : stacks) {
            applyEnchantmentModifier(modifier, itemstack);
        }
    }
    
    public boolean canPlaceCrystal(final BlockPos blockPos) {
        final BlockPos boost = blockPos.func_177982_a(0, 1, 0);
        final BlockPos boost2 = blockPos.func_177982_a(0, 2, 0);
        return (SmartOffHand.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150357_h || SmartOffHand.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150343_Z) && SmartOffHand.mc.field_71441_e.func_180495_p(boost).func_177230_c() == Blocks.field_150350_a && SmartOffHand.mc.field_71441_e.func_180495_p(boost2).func_177230_c() == Blocks.field_150350_a && SmartOffHand.mc.field_71441_e.func_72872_a((Class)EntityPlayer.class, new AxisAlignedBB(boost)).isEmpty() && SmartOffHand.mc.field_71441_e.func_72872_a((Class)EntityPlayer.class, new AxisAlignedBB(boost2)).isEmpty();
    }
    
    @Override
    public void onUpdate() {
        final List<BlockPos> blockPosList;
        final List<BlockPos> uwu;
        final List crystalList;
        final Iterator<BlockPos> iterator;
        BlockPos awa;
        double w;
        final Iterator<EntityEnderCrystal> iterator2;
        EntityEnderCrystal crystal;
        double w2;
        List<BedSaver> bedPos;
        final Iterator<BedSaver> iterator3;
        BedSaver pos;
        int i;
        MultiThreading.runAsync(() -> {
            this.totemHealth = this.totemhealth.getValue();
            blockPosList = this.getSphere(SmartOffHand.mc.field_71439_g.func_180425_c(), this.detectrange.getValue(), this.detectrange.getValue(), false, true, 0);
            uwu = this.findCrystalBlocks(blockPosList);
            crystalList = (List)SmartOffHand.mc.field_71441_e.field_72996_f.stream().filter(e -> e instanceof EntityEnderCrystal && e.func_70032_d((Entity)SmartOffHand.mc.field_71439_g) <= this.detectrange.getValue()).map(e -> (EntityEnderCrystal)e).collect(Collectors.toList());
            uwu.iterator();
            while (iterator.hasNext()) {
                awa = iterator.next();
                w = calculateDamage(awa.field_177962_a + 0.5, awa.field_177960_b + 1, awa.field_177961_c + 0.5, (Entity)SmartOffHand.mc.field_71439_g);
                if (w > this.totemHealth) {
                    this.totemHealth = w;
                }
            }
            crystalList.iterator();
            while (iterator2.hasNext()) {
                crystal = iterator2.next();
                w2 = calculateDamage(crystal, (Entity)SmartOffHand.mc.field_71439_g);
                if (w2 > this.totemHealth) {
                    this.totemHealth = w2;
                }
            }
            if (this.detectBed.getValue()) {
                bedPos = this.canPlaceBed(blockPosList);
                bedPos.iterator();
                while (iterator3.hasNext()) {
                    for (pos = iterator3.next(), i = 0; i < pos.canPlaceDirection.size(); ++i) {
                        if (pos.selfDamage.get(i) > this.totemHealth) {
                            this.totemHealth = pos.selfDamage.get(i);
                        }
                    }
                }
            }
            this.totemHealth2 = this.totemHealth;
            return;
        });
        this.gaps = -1;
        this.crystals = -1;
        this.totem = -1;
        this.bed = -1;
        for (int j = 0; j < 44; ++j) {
            if (SmartOffHand.mc.field_71439_g.field_71071_by.func_70301_a(j) != SmartOffHand.mc.field_71439_g.func_184592_cb()) {
                if (SmartOffHand.mc.field_71439_g.field_71071_by.func_70301_a(j).func_77973_b() == Items.field_185158_cP) {
                    if ((this.crystals == -1 || SmartOffHand.mc.field_71439_g.field_71071_by.func_70301_a(this.crystals).func_190916_E() > SmartOffHand.mc.field_71439_g.field_71071_by.func_70301_a(j).func_190916_E()) && (SmartOffHand.mc.field_71439_g.func_184592_cb().func_77973_b() != Items.field_185158_cP || SmartOffHand.mc.field_71439_g.func_184592_cb().func_82833_r().equals(SmartOffHand.mc.field_71439_g.field_71071_by.func_70301_a(j).func_82833_r()))) {
                        this.crystals = j;
                    }
                }
                else if (SmartOffHand.mc.field_71439_g.field_71071_by.func_70301_a(j).func_77973_b() == Items.field_190929_cY) {
                    this.totem = j;
                }
                else if (SmartOffHand.mc.field_71439_g.field_71071_by.func_70301_a(j).func_77973_b() == Items.field_151153_ao) {
                    this.gaps = j;
                }
                else if (SmartOffHand.mc.field_71439_g.field_71071_by.func_70301_a(j).func_77973_b() == Items.field_151104_aV) {
                    this.bed = j;
                }
            }
        }
        this.totemhealths = Math.max(this.totemHealth2, this.totemhealth.getValue());
        if (SmartOffHand.mc.field_71462_r instanceof GuiContainer || SmartOffHand.mc.field_71439_g == null || SmartOffHand.mc.field_71441_e == null) {
            return;
        }
        if (SmartOffHand.mc.field_71439_g.func_110143_aJ() + SmartOffHand.mc.field_71439_g.func_110139_bj() > this.totemhealths || (this.totem == -1 && SmartOffHand.mc.field_71439_g.func_184592_cb().func_77973_b() != Items.field_190929_cY)) {
            if (SmartOffHand.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword && SmartOffHand.mc.field_71474_y.field_74313_G.func_151470_d() && this.gap.getValue()) {
                if (SmartOffHand.mc.field_71439_g.func_184592_cb().func_77973_b() != Items.field_151153_ao && this.gaps != -1) {
                    SmartOffHand.mc.field_71442_b.func_187098_a(0, (this.gaps < 9) ? (this.gaps + 36) : this.gaps, 0, ClickType.PICKUP, (EntityPlayer)SmartOffHand.mc.field_71439_g);
                    SmartOffHand.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)SmartOffHand.mc.field_71439_g);
                    SmartOffHand.mc.field_71442_b.func_187098_a(0, (this.gaps < 9) ? (this.gaps + 36) : this.gaps, 0, ClickType.PICKUP, (EntityPlayer)SmartOffHand.mc.field_71439_g);
                }
                return;
            }
            else if ((!ModuleManager.getModuleByName("AutoCrystal").isDisabled() || !ModuleManager.getModuleByName("AutoCrystalLite").isDisabled()) && this.crystal.getValue()) {
                if ((SmartOffHand.mc.field_71439_g.func_184592_cb().func_77973_b() != Items.field_185158_cP || SmartOffHand.mc.field_71439_g.func_184592_cb().func_190916_E() < 16) && this.crystals != -1) {
                    SmartOffHand.mc.field_71442_b.func_187098_a(0, (this.crystals < 9) ? (this.crystals + 36) : this.crystals, 0, ClickType.PICKUP, (EntityPlayer)SmartOffHand.mc.field_71439_g);
                    SmartOffHand.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)SmartOffHand.mc.field_71439_g);
                    SmartOffHand.mc.field_71442_b.func_187098_a(0, (this.crystals < 9) ? (this.crystals + 36) : this.crystals, 0, ClickType.PICKUP, (EntityPlayer)SmartOffHand.mc.field_71439_g);
                }
                return;
            }
            else if (!ModuleManager.getModuleByName("BedAura").isDisabled() && this.beds.getValue()) {
                if (SmartOffHand.mc.field_71439_g.func_184592_cb().func_77973_b() != Items.field_151104_aV && this.bed != -1) {
                    SmartOffHand.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)SmartOffHand.mc.field_71439_g);
                    SmartOffHand.mc.field_71442_b.func_187098_a(0, (this.bed < 9) ? (this.bed + 36) : this.bed, 0, ClickType.PICKUP, (EntityPlayer)SmartOffHand.mc.field_71439_g);
                    SmartOffHand.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)SmartOffHand.mc.field_71439_g);
                }
                return;
            }
        }
        if (this.totem != -1 && SmartOffHand.mc.field_71439_g.func_184592_cb().func_77973_b() != Items.field_190929_cY) {
            SmartOffHand.mc.field_71442_b.func_187098_a(0, (this.totem < 9) ? (this.totem + 36) : this.totem, 0, ClickType.PICKUP, (EntityPlayer)SmartOffHand.mc.field_71439_g);
            SmartOffHand.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)SmartOffHand.mc.field_71439_g);
            SmartOffHand.mc.field_71442_b.func_187098_a(0, (this.totem < 9) ? (this.totem + 36) : this.totem, 0, ClickType.PICKUP, (EntityPlayer)SmartOffHand.mc.field_71439_g);
        }
    }
    
    public List<BedSaver> canPlaceBed(final List<BlockPos> blockPosList) {
        final List<BedSaver> bedSaverList = new ArrayList<BedSaver>();
        final List<EnumFacing> list = new ArrayList<EnumFacing>();
        final List<Double> damage = new ArrayList<Double>();
        for (final BlockPos pos : blockPosList) {
            for (final EnumFacing facing : EnumFacing.field_176754_o) {
                final BlockPos side = pos.func_177972_a(facing);
                final BlockPos boost = pos.func_177982_a(0, 1, 0);
                final BlockPos boost2 = pos.func_177982_a(0, 1, 0).func_177972_a(facing);
                final Block boostBlock = SmartOffHand.mc.field_71441_e.func_180495_p(boost).func_177230_c();
                final Block boostBlock2 = SmartOffHand.mc.field_71441_e.func_180495_p(boost2).func_177230_c();
                if ((boostBlock == Blocks.field_150350_a || boostBlock == Blocks.field_150324_C) && (boostBlock2 == Blocks.field_150350_a || boostBlock2 == Blocks.field_150324_C) && SmartOffHand.mc.field_71441_e.func_180495_p(side).func_185904_a().func_76218_k() && SmartOffHand.mc.field_71441_e.func_180495_p(side).func_185917_h() && SmartOffHand.mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76218_k() && SmartOffHand.mc.field_71441_e.func_180495_p(pos).func_185917_h()) {
                    final double selfDmg = calculateDamage2(boost2.field_177962_a + 0.5, boost2.field_177960_b + 0.5, boost2.field_177961_c + 0.5, (Entity)SmartOffHand.mc.field_71439_g);
                    list.add(facing);
                    damage.add(selfDmg);
                }
            }
            if (list.size() > 0) {
                bedSaverList.add(new BedSaver(pos, list, damage));
                list.clear();
                damage.clear();
            }
        }
        return bedSaverList;
    }
    
    public static float calculateDamage2(final double posX, final double posY, final double posZ, final Entity entity) {
        final float doubleExplosionSize = 10.0f;
        final Vec3d playerBoundingBox = entity.field_70121_D.func_189972_c();
        final double distancedsize = BlockInteractionHelper.blockDistance(posX, posY, posZ, playerBoundingBox.field_72450_a, playerBoundingBox.field_72448_b, playerBoundingBox.field_72449_c) / 10.0;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        final double blockDensity = getBlockDensity3(vec3d, entity.func_174813_aQ(), entity.field_70170_p);
        final double v = (1.0 - distancedsize) * blockDensity;
        final float damage = (float)(int)((v * v + v) / 2.0 * 7.0 * 10.0 + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion((World)SmartOffHand.mc.field_71441_e, (Entity)null, posX, posY, posZ, 5.0f, true, true));
        }
        return (float)finald;
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
                        if (world.func_72933_a(new Vec3d(d6 + d4, d7, d8 + d5), vec) == null || (world.func_72933_a(new Vec3d(d6 + d4, d7, d8 + d5), vec).field_72313_a == RayTraceResult.Type.BLOCK && SmartOffHand.mc.field_71441_e.func_180495_p(world.func_72933_a(new Vec3d(d6 + d4, d7, d8 + d5), vec).func_178782_a()).func_177230_c() == Blocks.field_150324_C)) {
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
    
    @Override
    public String getHudInfo() {
        if (SmartOffHand.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_190929_cY) {
            return "Totem";
        }
        if (SmartOffHand.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_185158_cP) {
            return "Crystal";
        }
        if (SmartOffHand.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_151153_ao) {
            return "Gapple";
        }
        if (SmartOffHand.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_151104_aV) {
            return "Bed";
        }
        return "None";
    }
    
    static final class ModifierDamage implements IModifier
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
