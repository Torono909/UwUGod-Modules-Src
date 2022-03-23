// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import net.minecraft.client.renderer.DestroyBlockProgress;
import meow.candycat.uwu.UUIDStuff;
import net.minecraft.util.NonNullList;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemChorusFruit;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import meow.candycat.uwu.util.UwUGodTessellator;
import meow.candycat.uwu.util.MathUtil;
import java.awt.Color;
import meow.candycat.uwu.event.events.RenderEvent;
import net.minecraft.init.Blocks;
import meow.candycat.uwu.util.CrystalPosSaver;
import java.util.Arrays;
import meow.candycat.uwu.util.Enemies;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemSword;
import meow.candycat.uwu.util.Wrapper;
import net.minecraft.potion.PotionEffect;
import net.minecraft.init.MobEffects;
import meow.candycat.uwu.module.modules.chat.AutoGG;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import meow.candycat.uwu.util.Friends;
import java.util.Comparator;
import java.util.Objects;
import net.minecraft.potion.Potion;
import meow.candycat.uwu.module.modules.util.ResistanceDetector;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.CombatRules;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Explosion;
import net.minecraft.entity.EntityLivingBase;
import meow.candycat.uwu.util.BlockInteractionHelper;
import meow.candycat.uwu.util.EntityUtil;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.util.math.RayTraceResult;
import java.util.Iterator;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import meow.candycat.uwu.module.modules.player.Scaffold;
import meow.candycat.uwu.module.ModuleManager;
import net.minecraft.init.Items;
import net.minecraft.entity.player.EntityPlayer;
import java.util.function.Predicate;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.SPacketSpawnPlayer;
import net.minecraft.network.play.server.SPacketSpawnPainting;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;
import meow.candycat.uwu.util.MultiThreading;
import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.world.World;
import net.minecraft.network.play.server.SPacketSpawnObject;
import java.util.Collection;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.network.play.server.SPacketSoundEffect;
import java.util.ArrayList;
import meow.candycat.uwu.setting.builder.SettingBuilder;
import meow.candycat.uwu.setting.Settings;
import net.minecraft.util.math.Vec3d;
import meow.candycat.uwu.event.events.EventPlayerPostMotionUpdate;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.item.EntityEnderCrystal;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "AutoCrystalLite", category = Category.COMBAT, description = "Best AutoCrystal Rn in the community ;)")
public class AutoCrystal2 extends Module
{
    public Setting<typesetting> typeset;
    public Setting<Boolean> autoSwitch;
    public Setting<Boolean> ghostAutoSwitch;
    public Setting<Boolean> prediction;
    public Setting<Boolean> place;
    public Setting<Boolean> explode;
    public Setting<Boolean> fastExplode;
    public Setting<Boolean> kamihit;
    public Setting<Boolean> offhandhit;
    public Setting<Boolean> legitplace;
    public Setting<Boolean> box;
    public Setting<Boolean> animals;
    public Setting<Boolean> mobs;
    public Setting<Boolean> rotate;
    public Setting<Boolean> placeRotate;
    public Setting<Boolean> betterplacements;
    public Setting<Boolean> mode4b4t;
    public Setting<Double> placerange;
    public Setting<Double> placeleastrange;
    public Setting<Double> range;
    public Setting<TargetMode> mode;
    public Setting<owouwu> owouwuisgud;
    public Setting<Double> playerdistance;
    public Setting<Double> distance;
    public Setting<Boolean> antiWeakness;
    public Setting<Boolean> renderdamage;
    public Setting<Boolean> accuratedamagerender;
    public Setting<Integer> placedelay;
    public Setting<Integer> hitdelay;
    public Setting<Integer> placepredictedTicks;
    public Setting<Integer> predictedTicks;
    public Setting<Boolean> nodesync;
    public Setting<Boolean> smartPredictHit;
    public Setting<Boolean> safePredictHit;
    public Setting<Boolean> suicideprot;
    public Setting<Boolean> maxselfcalculations;
    public Setting<Boolean> wall;
    public Setting<Double> walls;
    public Setting<Integer> Placements;
    public Setting<Double> mindmg;
    public Setting<Double> mindiff;
    public Setting<Double> minhitdmg;
    public Setting<Double> minhitdiff;
    public Setting<Double> afterdamagevalue;
    public Setting<Double> faceplacehealth;
    public Setting<Integer> selfdmg;
    public Setting<Integer> maxhitdmg;
    public Setting<Integer> antideath;
    public Setting<Integer> red;
    public Setting<Integer> green;
    public Setting<Integer> blue;
    public Setting<Integer> alpha;
    public Setting<Boolean> rainbow;
    public Setting<Integer> width;
    public Setting<Boolean> entityignore;
    public Setting<Boolean> clientSide;
    public Setting<Boolean> multiplace;
    public Setting<Boolean> autoSetAntiDeath;
    public Setting<Boolean> noGappleSwitch;
    public Setting<Boolean> nomineSwitch;
    public Setting<Integer> dangerous;
    public Setting<Boolean> rainbow2;
    public int oldSlot;
    public int newSlot;
    public int hittick;
    public int placetick;
    public int uwu;
    public int placements;
    public boolean danger;
    public boolean rotated;
    public boolean isAttacking;
    public boolean switchCooldown;
    public EntityEnderCrystal crystal;
    public EntityEnderCrystal lastHitCrystal;
    public EntityEnderCrystal lastcrystal;
    private double damage;
    private double lastDamage;
    public BlockPos render;
    public static Entity renderEnt;
    public static float yaw;
    public static float pitch;
    private List<EntityEnderCrystal> finallyowo;
    boolean shouldHit;
    boolean shouldPlace;
    boolean shouldSwitch;
    BlockPos shouldPlacePos;
    EntityEnderCrystal shouldHitCrystal;
    int lastEntityID;
    public boolean isMending;
    long mendTime;
    boolean firstRun;
    List<EntityEnderCrystal> forFastHitting;
    @EventHandler
    public Listener<PacketEvent.Receive> packetListener;
    @EventHandler
    public Listener<EventPlayerPostMotionUpdate> rotateListener;
    double x;
    double y;
    double z;
    int stage;
    Vec3d lastHitCrystalPos;
    Vec3d lastPlayerPos;
    long ms;
    boolean placed;
    
    public AutoCrystal2() {
        this.typeset = this.register((SettingBuilder<typesetting>)Settings.enumBuilder(typesetting.class).withName("Type").withValue(typesetting.GENERAL));
        this.autoSwitch = this.register(Settings.booleanBuilder("Auto Switch").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL)).build());
        this.ghostAutoSwitch = this.register(Settings.booleanBuilder("Ghost Auto Switch").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL) && this.autoSwitch.getValue()).build());
        this.prediction = this.register(Settings.booleanBuilder("Prediction").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL)).build());
        this.place = this.register(Settings.booleanBuilder("Place").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL)).build());
        this.explode = this.register(Settings.booleanBuilder("Explode").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL)).build());
        this.fastExplode = this.register(Settings.booleanBuilder("FastExplode").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL) && this.explode.getValue()).build());
        this.kamihit = this.register(Settings.booleanBuilder("KamiHit").withValue(false).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL)).build());
        this.offhandhit = this.register(Settings.booleanBuilder("OffHandHit").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL) && this.explode.getValue()).build());
        this.legitplace = this.register(Settings.booleanBuilder("LegitPlace").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL) && this.place.getValue()).build());
        this.box = this.register(Settings.booleanBuilder("BlockHighlight").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL) && this.place.getValue()).build());
        this.animals = this.register(Settings.booleanBuilder("Animals").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL)).build());
        this.mobs = this.register(Settings.booleanBuilder("Mobs").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL)).build());
        this.rotate = this.register(Settings.booleanBuilder("Rotate").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL)).build());
        this.placeRotate = this.register(Settings.booleanBuilder("PlaceRotate").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL)).build());
        this.betterplacements = this.register(Settings.booleanBuilder("BetterPlacements").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL)).build());
        this.mode4b4t = this.register(Settings.booleanBuilder("4b4tmode").withValue(false).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL)).build());
        this.placerange = this.register(Settings.doubleBuilder("PlaceRange").withMinimum(1.0).withValue(4.5).withMaximum(7.0).withVisibility(v -> this.typeset.getValue().equals(typesetting.NUMBER) && this.place.getValue()).build());
        this.placeleastrange = this.register(Settings.doubleBuilder("BetterPlaceRange").withMinimum(1.0).withValue(4.5).withMaximum(7.0).withVisibility(v -> this.typeset.getValue().equals(typesetting.NUMBER) && this.place.getValue() && this.betterplacements.getValue()).build());
        this.range = this.register(Settings.doubleBuilder("HitRange").withMinimum(1.0).withValue(5.0).withMaximum(7.0).withVisibility(v -> this.typeset.getValue().equals(typesetting.NUMBER) && this.explode.getValue()).build());
        this.mode = this.register((Setting<TargetMode>)Settings.enumBuilder(TargetMode.class).withName("Target Mode").withValue(TargetMode.ORIGINAL).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL)).build());
        this.owouwuisgud = this.register((Setting<owouwu>)Settings.enumBuilder(owouwu.class).withName("DamageMode").withValue(owouwu.MostAdvantage).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL)).build());
        this.playerdistance = this.register(Settings.doubleBuilder("EnemyPlayerDist").withMinimum(0.0).withValue(14.0).withMaximum(20.0).withVisibility(v -> this.typeset.getValue().equals(typesetting.NUMBER)).build());
        this.distance = this.register(Settings.doubleBuilder("EnemyCrystalDist").withMinimum(1.0).withValue(8.0).withMaximum(13.0).withVisibility(v -> this.typeset.getValue().equals(typesetting.NUMBER)).build());
        this.antiWeakness = this.register(Settings.booleanBuilder("AntiWeakness").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL2)).build());
        this.renderdamage = this.register(Settings.booleanBuilder("RenderDamage").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL2) && this.place.getValue()).build());
        this.accuratedamagerender = this.register(Settings.booleanBuilder("RenderAccurateDamage").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL2) && this.renderdamage.getValue()).build());
        this.placedelay = this.register(Settings.integerBuilder("PlaceDelay").withMinimum(0).withValue(2).withMaximum(20).withVisibility(v -> this.typeset.getValue().equals(typesetting.NUMBER) && this.place.getValue()).build());
        this.hitdelay = this.register(Settings.integerBuilder("HitDelay").withMinimum(0).withValue(0).withMaximum(20).withVisibility(v -> this.typeset.getValue().equals(typesetting.NUMBER) && this.explode.getValue()).build());
        this.placepredictedTicks = this.register(Settings.integerBuilder("PlacePredictedTicks").withMinimum(0).withValue(0).withMaximum(20).withVisibility(v -> this.typeset.getValue().equals(typesetting.NUMBER) && this.prediction.getValue()).build());
        this.predictedTicks = this.register(Settings.integerBuilder("HitPredictedTicks").withMinimum(0).withValue(0).withMaximum(20).withVisibility(v -> this.typeset.getValue().equals(typesetting.NUMBER) && this.prediction.getValue()).build());
        this.nodesync = this.register(Settings.booleanBuilder("FastSync").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL2)).build());
        this.smartPredictHit = this.register(Settings.booleanBuilder("SmartPredictHit").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL2)).build());
        this.safePredictHit = this.register(Settings.booleanBuilder("SafePredictHit").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL2) && this.smartPredictHit.getValue()).build());
        this.suicideprot = this.register(Settings.booleanBuilder("SuicideProt").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL2)).build());
        this.maxselfcalculations = this.register(Settings.booleanBuilder("MaxSelfCalculation").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL2)).build());
        this.wall = this.register(Settings.booleanBuilder("Walls").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL2)).build());
        this.walls = this.register(Settings.doubleBuilder("WallsRange").withMinimum(0.0).withValue(3.5).withMaximum(5.0).withVisibility(v -> this.typeset.getValue().equals(typesetting.NUMBER) && this.wall.getValue()).build());
        this.Placements = this.register(Settings.integerBuilder("Place Break").withMinimum(0).withValue(2).withMaximum(10).withVisibility(v -> this.typeset.getValue().equals(typesetting.NUMBER)).build());
        this.mindmg = this.register(Settings.doubleBuilder("Min Dmg").withMinimum(0.0).withValue(2.0).withMaximum(10.0).withVisibility(v -> this.typeset.getValue().equals(typesetting.NUMBER) && this.place.getValue()).build());
        this.mindiff = this.register(Settings.doubleBuilder("MinDifference").withMinimum(0.0).withValue(4.0).withMaximum(10.0).withVisibility(v -> this.typeset.getValue().equals(typesetting.NUMBER) && this.place.getValue()).build());
        this.minhitdmg = this.register(Settings.doubleBuilder("MinHitDmg").withMinimum(0.0).withValue(2.0).withMaximum(10.0).withVisibility(v -> this.typeset.getValue().equals(typesetting.NUMBER) && this.explode.getValue()).build());
        this.minhitdiff = this.register(Settings.doubleBuilder("MinHitDifference").withMinimum(0.0).withValue(2.0).withMaximum(10.0).withVisibility(v -> this.typeset.getValue().equals(typesetting.NUMBER) && this.explode.getValue()).build());
        this.afterdamagevalue = this.register(Settings.doubleBuilder("AfterDamageValue").withMinimum(0.0).withValue(18.0).withMaximum(36.0).withVisibility(v -> this.typeset.getValue().equals(typesetting.NUMBER) && this.explode.getValue()).build());
        this.faceplacehealth = this.register(Settings.doubleBuilder("FacePlaceHealth").withMinimum(0.0).withValue(10.0).withMaximum(36.0).withVisibility(v -> this.typeset.getValue().equals(typesetting.NUMBER) && this.place.getValue()).build());
        this.selfdmg = this.register(Settings.integerBuilder("Max Self").withMinimum(0).withValue(4).withMaximum(16).withVisibility(v -> this.typeset.getValue().equals(typesetting.NUMBER) && this.place.getValue()).build());
        this.maxhitdmg = this.register(Settings.integerBuilder("MaxSelfHitDmg").withMinimum(0).withValue(6).withMaximum(16).withVisibility(v -> this.typeset.getValue().equals(typesetting.NUMBER) && this.explode.getValue()).build());
        this.antideath = this.register(Settings.integerBuilder("BeforeDeathValue").withMinimum(0).withValue(2).withMaximum(20).withVisibility(v -> this.typeset.getValue().equals(typesetting.NUMBER) && (this.place.getValue() || this.explode.getValue())).build());
        this.red = this.register(Settings.integerBuilder("Red").withMinimum(0).withValue(255).withMaximum(255).withVisibility(v -> this.typeset.getValue().equals(typesetting.COLOR)).build());
        this.green = this.register(Settings.integerBuilder("Green").withMinimum(0).withValue(255).withMaximum(255).withVisibility(v -> this.typeset.getValue().equals(typesetting.COLOR)).build());
        this.blue = this.register(Settings.integerBuilder("Blue").withMinimum(0).withValue(255).withMaximum(255).withVisibility(v -> this.typeset.getValue().equals(typesetting.COLOR)).build());
        this.alpha = this.register(Settings.integerBuilder("Alpha").withMinimum(0).withValue(75).withMaximum(255).withVisibility(v -> this.typeset.getValue().equals(typesetting.COLOR)).build());
        this.rainbow = this.register(Settings.booleanBuilder("Rainbow").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.COLOR) && this.box.getValue()).build());
        this.width = this.register(Settings.integerBuilder("Thickness").withValue(5).withVisibility(v -> this.typeset.getValue().equals(typesetting.COLOR) && this.box.getValue()).build());
        this.entityignore = this.register(Settings.booleanBuilder("EntityIgnore").withValue(false).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL2)).build());
        this.clientSide = this.register(Settings.booleanBuilder("ClientSide").withValue(false).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL2)).build());
        this.multiplace = this.register(Settings.booleanBuilder("MultiPlace").withValue(false).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL2)).build());
        this.autoSetAntiDeath = this.register(Settings.booleanBuilder("OffhandMode").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL2)).build());
        this.noGappleSwitch = this.register(Settings.booleanBuilder("NoGappleSwitch").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL2)).build());
        this.nomineSwitch = this.register(Settings.booleanBuilder("NoMiningSwitch").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.GENERAL2)).build());
        this.dangerous = this.register(Settings.integerBuilder("DangerCrystal").withMinimum(0).withValue(6).withMaximum(16).withVisibility(v -> this.typeset.getValue().equals(typesetting.NUMBER) && this.explode.getValue()).build());
        this.rainbow2 = this.register(Settings.booleanBuilder("HighLightRainbow").withValue(true).withVisibility(v -> this.typeset.getValue().equals(typesetting.COLOR) && this.box.getValue()).build());
        this.oldSlot = -1;
        this.hittick = this.hitdelay.getValue();
        this.placetick = this.placedelay.getValue();
        this.placements = 0;
        this.isAttacking = false;
        this.switchCooldown = false;
        this.finallyowo = new ArrayList<EntityEnderCrystal>();
        this.shouldHit = false;
        this.shouldPlace = false;
        this.shouldSwitch = false;
        this.shouldPlacePos = null;
        this.shouldHitCrystal = null;
        this.lastEntityID = -1;
        this.isMending = false;
        this.mendTime = -1L;
        this.firstRun = true;
        this.forFastHitting = new ArrayList<EntityEnderCrystal>();
        SPacketSoundEffect packet;
        final Iterator<Entity> iterator;
        Entity e;
        EntityEnderCrystal entity;
        EntityEnderCrystal entity2;
        CPacketUseEntity useEntity;
        this.packetListener = new Listener<PacketEvent.Receive>(event -> {
            if (event.getPacket() instanceof SPacketSoundEffect && this.nodesync.getValue()) {
                packet = (SPacketSoundEffect)event.getPacket();
                if (packet.func_186977_b() == SoundCategory.BLOCKS && packet.func_186978_a() == SoundEvents.field_187539_bB) {
                    new ArrayList<Entity>(AutoCrystal2.mc.field_71441_e.field_72996_f).iterator();
                    while (iterator.hasNext()) {
                        e = iterator.next();
                        if (e instanceof EntityEnderCrystal && e.func_70011_f(packet.func_149207_d(), packet.func_149211_e(), packet.func_149210_f()) <= 6.0) {
                            e.func_70106_y();
                        }
                    }
                }
            }
            else if (event.getPacket() instanceof SPacketSpawnObject) {
                this.lastEntityID = ((SPacketSpawnObject)event.getPacket()).func_149001_c();
                if (((SPacketSpawnObject)event.getPacket()).func_148993_l() == 51 && this.fastExplode.getValue()) {
                    MultiThreading.runAsync(() -> {
                        if (this.forFastHitting.size() > 0) {
                            this.forFastHitting.clear();
                        }
                        entity = new EntityEnderCrystal((World)AutoCrystal2.mc.field_71441_e, ((SPacketSpawnObject)event.getPacket()).func_186880_c(), ((SPacketSpawnObject)event.getPacket()).func_186882_d(), ((SPacketSpawnObject)event.getPacket()).func_186881_e());
                        this.forFastHitting.add(entity);
                        if (AutoCrystal2.mc.field_71439_g.func_70032_d((Entity)entity) < this.range.getValue()) {
                            entity2 = this.getBestCrystal(this.forFastHitting);
                            if (entity2 != null) {
                                useEntity = new CPacketUseEntity();
                                useEntity.field_149567_a = ((SPacketSpawnObject)event.getPacket()).func_149001_c();
                                useEntity.field_149566_b = CPacketUseEntity.Action.ATTACK;
                                AutoCrystal2.mc.field_71439_g.field_71174_a.func_147297_a((Packet)useEntity);
                                AutoCrystal2.mc.field_71439_g.func_184609_a(((boolean)this.offhandhit.getValue()) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                            }
                        }
                    });
                }
            }
            else if (event.getPacket() instanceof SPacketSpawnExperienceOrb) {
                this.lastEntityID = ((SPacketSpawnExperienceOrb)event.getPacket()).func_148985_c();
                this.isMending = true;
                this.mendTime = System.currentTimeMillis();
            }
            else if (event.getPacket() instanceof SPacketSpawnMob) {
                this.lastEntityID = ((SPacketSpawnMob)event.getPacket()).func_149024_d();
            }
            else if (event.getPacket() instanceof SPacketSpawnPainting) {
                this.lastEntityID = ((SPacketSpawnPainting)event.getPacket()).func_148965_c();
            }
            else if (event.getPacket() instanceof SPacketSpawnPlayer) {
                this.lastEntityID = ((SPacketSpawnPlayer)event.getPacket()).func_148943_d();
            }
            else if (event.getPacket() instanceof SPacketSpawnGlobalEntity) {
                this.lastEntityID = ((SPacketSpawnGlobalEntity)event.getPacket()).func_149052_c();
            }
            return;
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
        int crystalSlot;
        int l;
        boolean offhand;
        KillAura killAura;
        RayTraceResult result;
        EnumFacing f;
        NetHandlerPlayClient field_71174_a;
        final CPacketPlayerTryUseItemOnBlock cPacketPlayerTryUseItemOnBlock;
        this.rotateListener = new Listener<EventPlayerPostMotionUpdate>(event -> {
            if (this.shouldHit) {
                this.shouldHit = false;
                AutoCrystal2.mc.field_71442_b.func_78764_a((EntityPlayer)AutoCrystal2.mc.field_71439_g, (Entity)this.shouldHitCrystal);
                AutoCrystal2.mc.field_71439_g.func_184609_a(((boolean)this.offhandhit.getValue()) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            }
            if (this.shouldPlace) {
                this.shouldPlace = false;
                crystalSlot = ((AutoCrystal2.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_185158_cP) ? AutoCrystal2.mc.field_71439_g.field_71071_by.field_70461_c : -1);
                if (crystalSlot == -1) {
                    l = 0;
                    while (l < 9) {
                        if (AutoCrystal2.mc.field_71439_g.field_71071_by.func_70301_a(l).func_77973_b() == Items.field_185158_cP) {
                            crystalSlot = l;
                            break;
                        }
                        else {
                            ++l;
                        }
                    }
                }
                offhand = false;
                if (AutoCrystal2.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_185158_cP) {
                    offhand = true;
                }
                else if (crystalSlot == -1) {
                    if (!this.rotated) {
                        killAura = (KillAura)ModuleManager.getModuleByName("KillAura");
                        if (!KillAura.isAiming && !((Scaffold)ModuleManager.getModuleByName("Scaffold")).rotated && !((AntiSurround)ModuleManager.getModuleByName("AntiSurround")).rotated && !((PacketXP)ModuleManager.getModuleByName("PacketXP")).rotated) {
                            resetRotation();
                        }
                    }
                    return;
                }
                if (this.shouldSwitch) {
                    AutoCrystal2.mc.field_71439_g.field_71071_by.field_70461_c = crystalSlot;
                    this.shouldSwitch = false;
                }
                result = AutoCrystal2.mc.field_71441_e.func_72933_a(new Vec3d(AutoCrystal2.mc.field_71439_g.field_70165_t, AutoCrystal2.mc.field_71439_g.field_70163_u + AutoCrystal2.mc.field_71439_g.func_70047_e(), AutoCrystal2.mc.field_71439_g.field_70161_v), new Vec3d(this.shouldPlacePos.field_177962_a + 0.5, (double)(this.shouldPlacePos.field_177960_b + 1), this.shouldPlacePos.field_177961_c + 0.5));
                if (result == null || result.field_178784_b == null) {
                    f = EnumFacing.UP;
                }
                else {
                    f = result.field_178784_b;
                }
                if (offhand || AutoCrystal2.mc.field_71439_g.field_71071_by.field_70461_c == crystalSlot) {
                    if (this.legitplace.getValue()) {
                        AutoCrystal2.mc.field_71442_b.func_187099_a(AutoCrystal2.mc.field_71439_g, AutoCrystal2.mc.field_71441_e, this.shouldPlacePos, f, new Vec3d(this.shouldPlacePos.field_177962_a + 0.5, (double)(this.shouldPlacePos.field_177960_b + 1), this.shouldPlacePos.field_177961_c + 0.5), offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                    }
                    else {
                        field_71174_a = AutoCrystal2.mc.field_71439_g.field_71174_a;
                        new CPacketPlayerTryUseItemOnBlock(this.shouldPlacePos, f, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f);
                        field_71174_a.func_147297_a((Packet)cPacketPlayerTryUseItemOnBlock);
                    }
                }
            }
            return;
        }, (Predicate<EventPlayerPostMotionUpdate>[])new Predicate[0]);
        this.stage = 0;
        this.lastHitCrystalPos = new Vec3d(0.0, 0.0, 0.0);
        this.lastPlayerPos = new Vec3d(0.0, 0.0, 0.0);
        this.ms = -1L;
        this.placed = false;
    }
    
    public static boolean lookAtPacket(final double px, final double py, final double pz, final EntityPlayer me, final boolean shouldSmooth) {
        final double[] v = EntityUtil.calculateLookAt(px, py, pz, me);
        if (ModuleManager.getModuleByName("Aimbot").isDisabled()) {
            ModuleManager.getModuleByName("Aimbot").enable();
        }
        return ((Aimbot)ModuleManager.getModuleByName("Aimbot")).setRotation((float)v[0], (float)v[1], shouldSmooth);
    }
    
    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(AutoCrystal2.mc.field_71439_g.field_70165_t), Math.floor(AutoCrystal2.mc.field_71439_g.field_70163_u), Math.floor(AutoCrystal2.mc.field_71439_g.field_70161_v));
    }
    
    public static boolean canBlockBeSeen(final double x, final double y, final double z) {
        return AutoCrystal2.mc.field_71441_e.func_147447_a(new Vec3d(AutoCrystal2.mc.field_71439_g.field_70165_t, AutoCrystal2.mc.field_71439_g.field_70163_u + AutoCrystal2.mc.field_71439_g.func_70047_e(), AutoCrystal2.mc.field_71439_g.field_70161_v), new Vec3d(x, y + 1.7, z), false, true, false) == null;
    }
    
    public static float calculateDamage(final double posX, final double posY, final double posZ, final Entity entity) {
        final float doubleExplosionSize = 12.0f;
        final Vec3d playerBoundingBox = entity.field_70121_D.func_189972_c();
        final double distancedsize = BlockInteractionHelper.blockDistance(posX, posY, posZ, playerBoundingBox.field_72450_a, playerBoundingBox.field_72448_b, playerBoundingBox.field_72449_c) / 12.0;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        final double blockDensity = entity.field_70170_p.func_72842_a(vec3d, entity.func_174813_aQ());
        final double v = (1.0 - distancedsize) * blockDensity;
        final float damage = (float)(int)((v * v + v) / 2.0 * 7.0 * 12.0 + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion((World)AutoCrystal2.mc.field_71441_e, (Entity)null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finald;
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
    
    public static float getDamageMultiplied(final float damage) {
        final int diff = AutoCrystal2.mc.field_71441_e.func_175659_aa().func_151525_a();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }
    
    public static float calculateDamage(final EntityEnderCrystal crystal, final Entity entity) {
        return calculateDamage(crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, entity);
    }
    
    public static float calculateDamage(final Entity crystal, final Entity entity) {
        return calculateDamage(crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, entity);
    }
    
    public static void resetRotation() {
        ((Aimbot)ModuleManager.getModuleByName("Aimbot")).resetRotation();
    }
    
    @Override
    public void onRender() {
        if (this.isMending && System.currentTimeMillis() - this.mendTime >= 100L) {
            this.isMending = false;
        }
    }
    
    public EntityEnderCrystal getBestCrystal(final List<EntityEnderCrystal> finallyowo) {
        EntityEnderCrystal crystal = null;
        if (this.kamihit.getValue()) {
            return (EntityEnderCrystal)AutoCrystal2.mc.field_71441_e.field_72996_f.stream().filter(entity -> entity instanceof EntityEnderCrystal && entity.func_70032_d((Entity)AutoCrystal2.mc.field_71439_g) < this.range.getValue()).map(entity -> entity).min(Comparator.comparing(c -> AutoCrystal2.mc.field_71439_g.func_70032_d(c))).orElse(null);
        }
        double enemydmg = (AutoCrystal2.renderEnt != null) ? ((((EntityLivingBase)AutoCrystal2.renderEnt).func_110143_aJ() + ((EntityLivingBase)AutoCrystal2.renderEnt).func_110139_bj() > this.faceplacehealth.getValue()) ? this.minhitdmg.getValue() : 0.0) : this.minhitdmg.getValue();
        double enemydmgdiff = (AutoCrystal2.renderEnt != null) ? ((((EntityLivingBase)AutoCrystal2.renderEnt).func_110143_aJ() + ((EntityLivingBase)AutoCrystal2.renderEnt).func_110139_bj() > this.faceplacehealth.getValue()) ? this.minhitdiff.getValue() : 0.0) : this.minhitdiff.getValue();
        double selfowo = 10000.0;
        this.danger = false;
        final List<Entity> entityrefresh = new ArrayList<Entity>((Collection<? extends Entity>)AutoCrystal2.mc.field_71441_e.field_73010_i.stream().filter(e -> !Friends.isFriend(e.func_70005_c_()) && e != AutoCrystal2.mc.field_71439_g && e.func_110143_aJ() > 0.0f && !e.field_70128_L && !e.func_184812_l_()).sorted(Comparator.comparing(e -> AutoCrystal2.mc.field_71439_g.func_70032_d(e))).collect(Collectors.toList()));
        if (AutoCrystal2.mc.field_71439_g != null && AutoCrystal2.mc.field_71441_e != null && this.prediction.getValue()) {
            for (final Entity x : entityrefresh) {
                if (x.func_70032_d((Entity)AutoCrystal2.mc.field_71439_g) > 15.0f) {
                    continue;
                }
                final float f = x.field_70130_N / 2.0f;
                final float f2 = x.field_70131_O;
                x.func_174826_a(new AxisAlignedBB(x.field_70165_t - f, x.field_70163_u, x.field_70161_v - f, x.field_70165_t + f, x.field_70163_u + f2, x.field_70161_v + f));
                final Entity y = EntityUtil.getPredictedPosition(x, this.predictedTicks.getValue());
                x.func_174826_a(y.func_174813_aQ());
            }
        }
        if (this.mobs.getValue() || this.animals.getValue()) {
            final boolean b;
            entityrefresh.addAll((Collection<? extends Entity>)AutoCrystal2.mc.field_71441_e.field_72996_f.stream().filter(entity -> {
                if (EntityUtil.isLiving(entity) && !(entity instanceof EntityPlayer)) {
                    if (EntityUtil.isPassive(entity) ? this.animals.getValue() : this.mobs.getValue()) {
                        return b;
                    }
                }
                return b;
            }).collect(Collectors.toList()));
        }
        boolean beKilled = false;
        boolean lasthitcrystalexistance = false;
        boolean canImmediateHit = false;
        if (AutoCrystal2.renderEnt != null) {
            for (final Entity uwu : entityrefresh) {
                if (((EntityLivingBase)uwu).func_110143_aJ() > 0.0f) {
                    if (uwu.field_70128_L) {
                        continue;
                    }
                    if (!uwu.func_70005_c_().equals(AutoCrystal2.renderEnt.func_70005_c_())) {
                        continue;
                    }
                    for (final EntityEnderCrystal crystal2 : finallyowo) {
                        if (crystal2.equals((Object)this.lastHitCrystal) && this.multiplace.getValue()) {
                            lasthitcrystalexistance = true;
                        }
                        else {
                            if (Math.sqrt(AutoCrystal2.mc.field_71439_g.func_70092_e(crystal2.field_70165_t, crystal2.field_70163_u, crystal2.field_70161_v)) > this.range.getValue()) {
                                break;
                            }
                            if (!AutoCrystal2.mc.field_71439_g.func_70685_l((Entity)crystal2) && Math.sqrt(AutoCrystal2.mc.field_71439_g.func_70092_e(crystal2.field_70165_t, crystal2.field_70163_u, crystal2.field_70161_v)) > this.walls.getValue() && this.wall.getValue()) {
                                break;
                            }
                            if (uwu.func_70032_d((Entity)crystal2) > 13.0f) {
                                continue;
                            }
                            final double enemydmgcal = calculateDamage(crystal2, uwu);
                            final double owo = calculateDamage(crystal2, (Entity)AutoCrystal2.mc.field_71439_g);
                            final boolean faceplace = ((EntityLivingBase)uwu).func_110143_aJ() + ((EntityLivingBase)uwu).func_110139_bj() <= this.faceplacehealth.getValue() && crystal == null;
                            final boolean bekilled = enemydmgcal > ((EntityLivingBase)uwu).func_110143_aJ() + ((EntityLivingBase)uwu).func_110139_bj();
                            if (enemydmgcal <= owo) {
                                if (!bekilled) {
                                    continue;
                                }
                                if (AutoCrystal2.mc.field_71439_g.func_110143_aJ() + AutoCrystal2.mc.field_71439_g.func_110139_bj() - owo > this.afterdamagevalue.getValue() && !faceplace) {
                                    continue;
                                }
                            }
                            Label_1109: {
                                if (!bekilled || beKilled) {
                                    if (this.owouwuisgud.getValue().equals(owouwu.MostDamage)) {
                                        if (enemydmgcal > enemydmg) {
                                            break Label_1109;
                                        }
                                    }
                                    else if (beKilled) {
                                        if (enemydmgcal - owo > enemydmgdiff && bekilled) {
                                            break Label_1109;
                                        }
                                    }
                                    else if (enemydmgcal - owo > enemydmgdiff) {
                                        break Label_1109;
                                    }
                                    if (!faceplace && (!beKilled || !bekilled || canImmediateHit || !this.rotate.getValue() || !lookAtPacket(crystal2.field_70165_t, crystal2.field_70163_u, crystal2.field_70161_v, (EntityPlayer)AutoCrystal2.mc.field_71439_g, true))) {
                                        continue;
                                    }
                                }
                            }
                            if (owo + this.antideath.getValue() > AutoCrystal2.mc.field_71439_g.func_110143_aJ() + AutoCrystal2.mc.field_71439_g.func_110139_bj() && this.suicideprot.getValue()) {
                                continue;
                            }
                            if (owo > this.maxhitdmg.getValue() && this.maxselfcalculations.getValue()) {
                                continue;
                            }
                            if ((enemydmgcal < this.minhitdmg.getValue() || enemydmgcal - owo < this.minhitdiff.getValue()) && !faceplace && !bekilled) {
                                continue;
                            }
                            if (!ModuleManager.getModuleByName("AutoGG").isDisabled()) {
                                final AutoGG autoGG = (AutoGG)ModuleManager.getModuleByName("AutoGG");
                                autoGG.addTargetedPlayer(AutoCrystal2.renderEnt.func_70005_c_());
                            }
                            crystal = crystal2;
                            enemydmg = enemydmgcal;
                            beKilled = (beKilled || bekilled);
                            enemydmgdiff = enemydmgcal - owo;
                            if (!this.rotate.getValue()) {
                                continue;
                            }
                            canImmediateHit = lookAtPacket(crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, (EntityPlayer)AutoCrystal2.mc.field_71439_g, true);
                        }
                    }
                }
            }
        }
        if (crystal != null) {
            return crystal;
        }
        for (final Entity uwu : entityrefresh) {
            if (uwu == AutoCrystal2.mc.field_71439_g) {
                continue;
            }
            if (((EntityLivingBase)uwu).func_110143_aJ() <= 0.0f) {
                continue;
            }
            if (uwu.field_70128_L) {
                continue;
            }
            for (final EntityEnderCrystal crystal2 : finallyowo) {
                if (crystal2.equals((Object)this.lastHitCrystal) && this.multiplace.getValue()) {
                    lasthitcrystalexistance = true;
                }
                else {
                    if (Math.sqrt(AutoCrystal2.mc.field_71439_g.func_70092_e(crystal2.field_70165_t, crystal2.field_70163_u, crystal2.field_70161_v)) > this.range.getValue()) {
                        break;
                    }
                    if (!AutoCrystal2.mc.field_71439_g.func_70685_l((Entity)crystal2) && Math.sqrt(AutoCrystal2.mc.field_71439_g.func_70092_e(crystal2.field_70165_t, crystal2.field_70163_u, crystal2.field_70161_v)) > this.walls.getValue() && this.wall.getValue()) {
                        break;
                    }
                    if (uwu.func_70032_d((Entity)crystal2) > 13.0f) {
                        continue;
                    }
                    final double enemydmgcal = calculateDamage(crystal2, uwu);
                    final double owo = calculateDamage(crystal2, (Entity)AutoCrystal2.mc.field_71439_g);
                    final boolean faceplace = ((EntityLivingBase)uwu).func_110143_aJ() + ((EntityLivingBase)uwu).func_110139_bj() <= this.faceplacehealth.getValue() && crystal == null;
                    final boolean bekilled = enemydmgcal > ((EntityLivingBase)uwu).func_110143_aJ() + ((EntityLivingBase)uwu).func_110139_bj();
                    if (enemydmgcal <= owo) {
                        if (!bekilled) {
                            continue;
                        }
                        if (AutoCrystal2.mc.field_71439_g.func_110143_aJ() + AutoCrystal2.mc.field_71439_g.func_110139_bj() - owo > this.afterdamagevalue.getValue() && !faceplace) {
                            continue;
                        }
                    }
                    Label_1947: {
                        if (!bekilled || beKilled) {
                            if (this.owouwuisgud.getValue().equals(owouwu.MostDamage)) {
                                if (enemydmgcal > enemydmg) {
                                    break Label_1947;
                                }
                            }
                            else if (beKilled) {
                                if (enemydmgcal - owo > enemydmgdiff && bekilled) {
                                    break Label_1947;
                                }
                            }
                            else if (enemydmgcal - owo > enemydmgdiff) {
                                break Label_1947;
                            }
                            if (!faceplace && (!beKilled || !bekilled || canImmediateHit || !this.rotate.getValue() || !lookAtPacket(crystal2.field_70165_t, crystal2.field_70163_u, crystal2.field_70161_v, (EntityPlayer)AutoCrystal2.mc.field_71439_g, true))) {
                                continue;
                            }
                        }
                    }
                    if (owo + this.antideath.getValue() > AutoCrystal2.mc.field_71439_g.func_110143_aJ() + AutoCrystal2.mc.field_71439_g.func_110139_bj() && this.suicideprot.getValue()) {
                        continue;
                    }
                    if (owo > this.maxhitdmg.getValue() && this.maxselfcalculations.getValue()) {
                        continue;
                    }
                    if ((enemydmgcal < this.minhitdmg.getValue() || enemydmgcal - owo < this.minhitdiff.getValue()) && !faceplace && !bekilled) {
                        continue;
                    }
                    if (!ModuleManager.getModuleByName("AutoGG").isDisabled()) {
                        final AutoGG autoGG = (AutoGG)ModuleManager.getModuleByName("AutoGG");
                        autoGG.addTargetedPlayer(uwu.func_70005_c_());
                    }
                    crystal = crystal2;
                    enemydmg = enemydmgcal;
                    beKilled = (beKilled || bekilled);
                    enemydmgdiff = enemydmgcal - owo;
                    if (!this.rotate.getValue()) {
                        continue;
                    }
                    canImmediateHit = lookAtPacket(crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, (EntityPlayer)AutoCrystal2.mc.field_71439_g, true);
                }
            }
        }
        if (crystal != null) {
            return crystal;
        }
        if (lasthitcrystalexistance) {
            for (final Entity uwu : entityrefresh) {
                if (uwu == AutoCrystal2.mc.field_71439_g) {
                    continue;
                }
                if (((EntityLivingBase)uwu).func_110143_aJ() <= 0.0f) {
                    continue;
                }
                if (uwu.field_70128_L) {
                    continue;
                }
                if (Math.sqrt(AutoCrystal2.mc.field_71439_g.func_70092_e(this.lastHitCrystal.field_70165_t, this.lastHitCrystal.field_70163_u, this.lastHitCrystal.field_70161_v)) > this.range.getValue()) {
                    break;
                }
                if (!AutoCrystal2.mc.field_71439_g.func_70685_l((Entity)this.lastHitCrystal) && Math.sqrt(AutoCrystal2.mc.field_71439_g.func_70092_e(this.lastHitCrystal.field_70165_t, this.lastHitCrystal.field_70163_u, this.lastHitCrystal.field_70161_v)) > this.walls.getValue() && this.wall.getValue()) {
                    break;
                }
                if (uwu.func_70032_d((Entity)this.lastHitCrystal) > 13.0f) {
                    continue;
                }
                final double enemydmgcal2 = calculateDamage(this.lastHitCrystal, uwu);
                final double owo2 = calculateDamage(this.lastHitCrystal, (Entity)AutoCrystal2.mc.field_71439_g);
                final boolean faceplace2 = ((EntityLivingBase)uwu).func_110143_aJ() + ((EntityLivingBase)uwu).func_110139_bj() <= this.faceplacehealth.getValue() && crystal == null;
                final boolean bekilled = enemydmgcal2 > ((EntityLivingBase)uwu).func_110143_aJ() + ((EntityLivingBase)uwu).func_110139_bj();
                if (enemydmgcal2 <= owo2) {
                    if (!bekilled) {
                        continue;
                    }
                    if (AutoCrystal2.mc.field_71439_g.func_110143_aJ() + AutoCrystal2.mc.field_71439_g.func_110139_bj() - owo2 > this.afterdamagevalue.getValue() && !faceplace2) {
                        continue;
                    }
                }
                Label_2693: {
                    if (!bekilled || beKilled) {
                        if (this.owouwuisgud.getValue().equals(owouwu.MostDamage)) {
                            if (enemydmgcal2 > enemydmg) {
                                break Label_2693;
                            }
                        }
                        else if (beKilled) {
                            if (enemydmgcal2 - owo2 > enemydmgdiff && bekilled) {
                                break Label_2693;
                            }
                        }
                        else if (enemydmgcal2 - owo2 > enemydmgdiff) {
                            break Label_2693;
                        }
                        if (!faceplace2) {
                            continue;
                        }
                    }
                }
                if (owo2 + this.antideath.getValue() > AutoCrystal2.mc.field_71439_g.func_110143_aJ() + AutoCrystal2.mc.field_71439_g.func_110139_bj() && this.suicideprot.getValue()) {
                    continue;
                }
                if (owo2 > this.maxhitdmg.getValue() && this.maxselfcalculations.getValue()) {
                    continue;
                }
                if ((enemydmgcal2 < this.minhitdmg.getValue() || enemydmgcal2 - owo2 < this.minhitdiff.getValue()) && !faceplace2 && !bekilled) {
                    continue;
                }
                if (!ModuleManager.getModuleByName("AutoGG").isDisabled()) {
                    final AutoGG autoGG2 = (AutoGG)ModuleManager.getModuleByName("AutoGG");
                    autoGG2.addTargetedPlayer(uwu.func_70005_c_());
                }
                crystal = this.lastHitCrystal;
                enemydmg = enemydmgcal2;
                beKilled = (beKilled || bekilled);
                enemydmgdiff = enemydmgcal2 - owo2;
            }
        }
        if (crystal == null) {
            for (final EntityEnderCrystal crystal3 : finallyowo) {
                if (Math.sqrt(AutoCrystal2.mc.field_71439_g.func_70092_e(crystal3.field_70165_t, crystal3.field_70163_u, crystal3.field_70161_v)) <= this.range.getValue()) {
                    if (!AutoCrystal2.mc.field_71439_g.func_70685_l((Entity)crystal3) && Math.sqrt(AutoCrystal2.mc.field_71439_g.func_70092_e(crystal3.field_70165_t, crystal3.field_70163_u, crystal3.field_70161_v)) > this.walls.getValue() && this.wall.getValue()) {
                        continue;
                    }
                    final double selfexplodedmg = calculateDamage(crystal3, (Entity)AutoCrystal2.mc.field_71439_g);
                    if (selfexplodedmg >= this.dangerous.getValue()) {
                        this.danger = true;
                    }
                    else {
                        if (selfexplodedmg < selfowo && selfexplodedmg + 0.5 > AutoCrystal2.mc.field_71439_g.func_110143_aJ() + AutoCrystal2.mc.field_71439_g.func_110139_bj()) {
                            continue;
                        }
                        crystal = crystal3;
                        selfowo = selfexplodedmg;
                    }
                }
            }
            if (!this.danger) {
                crystal = null;
            }
        }
        return crystal;
    }
    
    @Override
    public void onUpdate() {
        if (this.autoSetAntiDeath.getValue() && ModuleManager.getModuleByName("SmartOffHand").isEnabled()) {
            this.antideath.setValue((int)Math.ceil(((SmartOffHand)ModuleManager.getModuleByName("SmartOffHand")).totemhealths));
        }
        boolean didSomething;
        boolean switched;
        int i;
        ItemStack stack;
        KillAura killAura;
        boolean t;
        final Iterator<Entity> iterator;
        Entity e2;
        EntityEnderCrystal crystal;
        KillAura killAura2;
        KillAura killAura3;
        int crystalSlot;
        int l;
        boolean offhand;
        KillAura killAura4;
        ArrayList<Entity> entities;
        ArrayList enemies;
        final void void1;
        List<BlockPos> list;
        List<BlockPos> sphereBlocks;
        boolean shouldEntityIgnore;
        ArrayList allEntities;
        List<CrystalPosSaver> blocks;
        final Iterator<Entity> iterator2;
        Entity x;
        float f;
        float f2;
        Entity y;
        final Iterator<Entity> iterator3;
        Entity x2;
        float f3;
        float f4;
        Entity y2;
        BlockPos q;
        boolean canBeKilled;
        double difference;
        final Iterator<Entity> iterator4;
        Entity entity2;
        boolean faceplace;
        final Iterator<CrystalPosSaver> iterator5;
        CrystalPosSaver blockPos;
        double d;
        double self;
        boolean canbekilled;
        KillAura killAura5;
        final Iterator<Entity> iterator6;
        Entity entity3;
        boolean faceplace2;
        final Iterator<CrystalPosSaver> iterator7;
        CrystalPosSaver blockPos2;
        double d2;
        double self2;
        boolean canbekilled2;
        KillAura killAura6;
        ArrayList<BlockPos> blockPosList;
        final Iterator<Entity> iterator8;
        Entity x3;
        float f5;
        float f6;
        Entity y3;
        List<CrystalPosSaver> blocks2;
        double difference2;
        final Iterator<Entity> iterator9;
        Entity x4;
        float f7;
        float f8;
        Entity y4;
        boolean shouldSkip;
        final Iterator<Entity> iterator10;
        Entity entity4;
        boolean faceplace3;
        final Iterator<CrystalPosSaver> iterator11;
        CrystalPosSaver blockPos3;
        double d3;
        double self3;
        boolean canbekilled3;
        KillAura killAura7;
        final Iterator<Entity> iterator12;
        Entity entity5;
        boolean faceplace4;
        final Iterator<CrystalPosSaver> iterator13;
        CrystalPosSaver blockPos4;
        double d4;
        double self4;
        boolean canbekilled4;
        KillAura killAura8;
        KillAura killAura9;
        boolean switched2;
        KillAura killAura10;
        KillAura killAura11;
        KillAura killAura12;
        RayTraceResult result;
        EnumFacing f9;
        NetHandlerPlayClient field_71174_a;
        final CPacketPlayerTryUseItemOnBlock cPacketPlayerTryUseItemOnBlock;
        BlockPos blockPos5;
        final Iterator<EntityPlayer> iterator14;
        EntityPlayer player;
        boolean switched3;
        int j;
        CPacketUseEntity useEntity;
        MultiThreading.runAsync(() -> {
            if (this.firstRun) {
                this.firstRun = false;
            }
            else {
                didSomething = false;
                this.finallyowo.clear();
                this.crystal = null;
                this.lastcrystal = null;
                this.rotated = false;
                this.switchCooldown = false;
                this.finallyowo.addAll((Collection<? extends EntityEnderCrystal>)AutoCrystal2.mc.field_71441_e.field_72996_f.stream().filter(entity -> entity instanceof EntityEnderCrystal).map(entity -> (EntityEnderCrystal)entity).sorted(Comparator.comparing(e -> AutoCrystal2.mc.field_71439_g.func_70032_d(e))).collect(Collectors.toList()));
                if (this.hitdelay.getValue() <= this.hittick) {
                    this.hittick = 0;
                    this.crystal = this.getBestCrystal(this.finallyowo);
                    if (this.crystal == null) {
                        this.lastHitCrystal = null;
                        this.hittick = this.hitdelay.getValue();
                    }
                    else if (this.explode.getValue()) {
                        switched = false;
                        if (this.antiWeakness.getValue() && AutoCrystal2.mc.field_71439_g.func_70644_a(MobEffects.field_76437_t) && (!AutoCrystal2.mc.field_71439_g.func_70644_a(MobEffects.field_76420_g) || Objects.requireNonNull(AutoCrystal2.mc.field_71439_g.func_70660_b(MobEffects.field_76420_g)).func_76458_c() < 1)) {
                            if (!this.isAttacking) {
                                this.oldSlot = Wrapper.getPlayer().field_71071_by.field_70461_c;
                                this.isAttacking = true;
                            }
                            this.newSlot = -1;
                            for (i = 0; i < 9; ++i) {
                                stack = Wrapper.getPlayer().field_71071_by.func_70301_a(i);
                                if (stack != ItemStack.field_190927_a) {
                                    if (stack.func_77973_b() instanceof ItemSword) {
                                        this.newSlot = i;
                                        break;
                                    }
                                    else if (stack.func_77973_b() instanceof ItemTool) {
                                        this.newSlot = i;
                                        break;
                                    }
                                }
                            }
                            if ((this.noGappleSwitch.getValue() && this.isEatingGap()) || (this.nomineSwitch.getValue() && this.isMiningBlock())) {
                                if (!this.rotated) {
                                    killAura = (KillAura)ModuleManager.getModuleByName("KillAura");
                                    if (!KillAura.isAiming && !((Scaffold)ModuleManager.getModuleByName("Scaffold")).rotated && !((AntiSurround)ModuleManager.getModuleByName("AntiSurround")).rotated && !((PacketXP)ModuleManager.getModuleByName("PacketXP")).rotated) {
                                        resetRotation();
                                    }
                                }
                                return;
                            }
                            else if (this.newSlot != -1) {
                                if (this.ghostAutoSwitch.getValue()) {
                                    AutoCrystal2.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.newSlot));
                                    switched = true;
                                }
                                else {
                                    Wrapper.getPlayer().field_71071_by.field_70461_c = this.newSlot;
                                    this.switchCooldown = true;
                                }
                            }
                        }
                        if (this.rotate.getValue()) {
                            this.rotated = true;
                            t = lookAtPacket(this.crystal.field_70165_t, this.crystal.field_70163_u, this.crystal.field_70161_v, (EntityPlayer)AutoCrystal2.mc.field_71439_g, true);
                            if (!t || this.isDisabled()) {
                                this.hittick = this.hitdelay.getValue();
                                return;
                            }
                            else if (this.crystal.field_70165_t == this.lastHitCrystalPos.field_72450_a && this.crystal.field_70163_u == this.lastHitCrystalPos.field_72448_b && this.crystal.field_70161_v == this.lastHitCrystalPos.field_72449_c && ((Aimbot)ModuleManager.getModuleByName("Aimbot")).smoothRotated && AutoCrystal2.mc.field_71439_g.field_70165_t == this.lastPlayerPos.field_72450_a && AutoCrystal2.mc.field_71439_g.field_70163_u == this.lastPlayerPos.field_72448_b && AutoCrystal2.mc.field_71439_g.field_70161_v == this.lastPlayerPos.field_72449_c) {
                                AutoCrystal2.mc.field_71442_b.func_78764_a((EntityPlayer)AutoCrystal2.mc.field_71439_g, (Entity)this.crystal);
                                AutoCrystal2.mc.field_71439_g.func_184609_a(((boolean)this.offhandhit.getValue()) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                            }
                            else {
                                this.shouldHit = true;
                                this.shouldHitCrystal = this.crystal;
                            }
                        }
                        else {
                            AutoCrystal2.mc.field_71442_b.func_78764_a((EntityPlayer)AutoCrystal2.mc.field_71439_g, (Entity)this.crystal);
                            AutoCrystal2.mc.field_71439_g.func_184609_a(((boolean)this.offhandhit.getValue()) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                        }
                        this.stage = 1;
                        didSomething = true;
                        if (switched) {
                            AutoCrystal2.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(AutoCrystal2.mc.field_71439_g.field_71071_by.field_70461_c));
                        }
                        if (this.clientSide.getValue()) {
                            new ArrayList<Entity>(AutoCrystal2.mc.field_71441_e.field_72996_f).iterator();
                            while (iterator.hasNext()) {
                                e2 = iterator.next();
                                if (e2 instanceof EntityEnderCrystal && e2.func_70011_f(this.crystal.field_70165_t, this.crystal.field_70163_u, this.crystal.field_70161_v) <= 6.0) {
                                    e2.func_70106_y();
                                }
                            }
                            AutoCrystal2.mc.field_71441_e.func_73022_a();
                            AutoCrystal2.mc.field_71441_e.func_72910_y();
                        }
                        crystal = this.crystal;
                        this.lastHitCrystal = crystal;
                        this.lastcrystal = crystal;
                        this.lastHitCrystalPos = new Vec3d(this.lastHitCrystal.field_70165_t, this.lastHitCrystal.field_70163_u, this.lastHitCrystal.field_70161_v);
                        this.lastPlayerPos = new Vec3d(AutoCrystal2.mc.field_71439_g.field_70165_t, AutoCrystal2.mc.field_71439_g.field_70163_u, AutoCrystal2.mc.field_71439_g.field_70161_v);
                        if (!this.entityignore.getValue() && this.placements >= this.Placements.getValue() && this.Placements.getValue() != 0) {
                            this.placements = 0;
                            if (!this.rotated) {
                                killAura2 = (KillAura)ModuleManager.getModuleByName("KillAura");
                                if (!KillAura.isAiming && !((Scaffold)ModuleManager.getModuleByName("Scaffold")).rotated && !((AntiSurround)ModuleManager.getModuleByName("AntiSurround")).rotated && !((PacketXP)ModuleManager.getModuleByName("PacketXP")).rotated) {
                                    resetRotation();
                                }
                            }
                            if (!didSomething) {
                                this.stage = 0;
                            }
                            return;
                        }
                        else if (!this.multiplace.getValue() && !this.entityignore.getValue()) {
                            if (!this.rotated) {
                                killAura3 = (KillAura)ModuleManager.getModuleByName("KillAura");
                                if (!KillAura.isAiming && !((Scaffold)ModuleManager.getModuleByName("Scaffold")).rotated && !((AntiSurround)ModuleManager.getModuleByName("AntiSurround")).rotated && !((PacketXP)ModuleManager.getModuleByName("PacketXP")).rotated) {
                                    resetRotation();
                                }
                            }
                            if (!didSomething) {
                                this.stage = 0;
                            }
                            return;
                        }
                    }
                    else {
                        if (this.oldSlot != -1) {
                            Wrapper.getPlayer().field_71071_by.field_70461_c = this.oldSlot;
                            this.oldSlot = -1;
                        }
                        this.isAttacking = false;
                    }
                }
                else {
                    ++this.hittick;
                    if (this.entityignore.getValue() || !this.multiplace.getValue()) {
                        return;
                    }
                }
                if (this.entityignore.getValue() || !this.multiplace.getValue() || this.placedelay.getValue() <= this.placetick) {
                    this.placetick = 0;
                    crystalSlot = ((AutoCrystal2.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_185158_cP) ? AutoCrystal2.mc.field_71439_g.field_71071_by.field_70461_c : -1);
                    if (crystalSlot == -1) {
                        l = 0;
                        while (l < 9) {
                            if (AutoCrystal2.mc.field_71439_g.field_71071_by.func_70301_a(l).func_77973_b() == Items.field_185158_cP) {
                                crystalSlot = l;
                                break;
                            }
                            else {
                                ++l;
                            }
                        }
                    }
                    offhand = false;
                    if (AutoCrystal2.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_185158_cP) {
                        offhand = true;
                    }
                    else if (crystalSlot == -1) {
                        if (!this.rotated) {
                            killAura4 = (KillAura)ModuleManager.getModuleByName("KillAura");
                            if (!KillAura.isAiming && !((Scaffold)ModuleManager.getModuleByName("Scaffold")).rotated && !((AntiSurround)ModuleManager.getModuleByName("AntiSurround")).rotated && !((PacketXP)ModuleManager.getModuleByName("PacketXP")).rotated) {
                                resetRotation();
                            }
                        }
                        if (!didSomething) {
                            this.stage = 0;
                        }
                        return;
                    }
                    entities = new ArrayList<Entity>((Collection<? extends Entity>)AutoCrystal2.mc.field_71441_e.field_73010_i.stream().filter(e -> !Friends.isFriend(((EntityPlayer)e).func_70005_c_()) && e != AutoCrystal2.mc.field_71439_g && ((EntityPlayer)e).func_110143_aJ() > 0.0f && !((EntityPlayer)e).field_70128_L).collect(Collectors.toList()));
                    enemies = new ArrayList<Object>(entities.stream().filter(e -> Enemies.isEnemy(e.func_70005_c_())).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
                    entities.remove(enemies);
                    if (this.mobs.getValue() || this.animals.getValue()) {
                        entities.addAll((Collection<?>)AutoCrystal2.mc.field_71441_e.field_72996_f.stream().filter(entity -> {
                            if (EntityUtil.isLiving(entity) && !(entity instanceof EntityPlayer)) {
                                if (EntityUtil.isPassive(entity) ? this.animals.getValue() : this.mobs.getValue()) {
                                    return (boolean)void1;
                                }
                            }
                            return (boolean)void1;
                        }).collect(Collectors.toList()));
                    }
                    if (!this.rotated || !this.placeRotate.getValue()) {
                        list = this.getSphere(new BlockPos(getPlayerPos().field_177962_a, getPlayerPos().field_177960_b + 1, getPlayerPos().field_177961_c), this.placerange.getValue().floatValue() + 1.0f, this.placerange.getValue().intValue() + 1, false, true, 0);
                    }
                    else {
                        list = Arrays.asList(new BlockPos(this.lastcrystal.field_70165_t - 0.5, this.lastcrystal.field_70163_u - 1.0, this.lastcrystal.field_70161_v - 0.5));
                    }
                    sphereBlocks = list;
                    shouldEntityIgnore = (this.entityignore.getValue() && (!this.multiplace.getValue() || HoleDetect.inhole));
                    allEntities = new ArrayList<Entity>(entities);
                    allEntities.addAll((Collection<?>)enemies);
                    blocks = this.findCrystalBlocks(shouldEntityIgnore, sphereBlocks, (List<Entity>)allEntities);
                    if (AutoCrystal2.mc.field_71439_g != null && AutoCrystal2.mc.field_71441_e != null && this.prediction.getValue()) {
                        entities.iterator();
                        while (iterator2.hasNext()) {
                            x = iterator2.next();
                            if (x.func_70032_d((Entity)AutoCrystal2.mc.field_71439_g) > 15.0f) {
                                continue;
                            }
                            else if (!(x instanceof EntityPlayer)) {
                                continue;
                            }
                            else {
                                f = x.field_70130_N / 2.0f;
                                f2 = x.field_70131_O;
                                x.func_174826_a(new AxisAlignedBB(x.field_70165_t - f, x.field_70163_u, x.field_70161_v - f, x.field_70165_t + f, x.field_70163_u + f2, x.field_70161_v + f));
                                y = EntityUtil.getPredictedPosition(x, this.placepredictedTicks.getValue());
                                x.func_174826_a(y.func_174813_aQ());
                            }
                        }
                        enemies.iterator();
                        while (iterator3.hasNext()) {
                            x2 = iterator3.next();
                            if (x2.func_70032_d((Entity)AutoCrystal2.mc.field_71439_g) > 15.0f) {
                                continue;
                            }
                            else if (!(x2 instanceof EntityPlayer)) {
                                continue;
                            }
                            else {
                                f3 = x2.field_70130_N / 2.0f;
                                f4 = x2.field_70131_O;
                                x2.func_174826_a(new AxisAlignedBB(x2.field_70165_t - f3, x2.field_70163_u, x2.field_70161_v - f3, x2.field_70165_t + f3, x2.field_70163_u + f4, x2.field_70161_v + f3));
                                y2 = EntityUtil.getPredictedPosition(x2, this.placepredictedTicks.getValue());
                                x2.func_174826_a(y2.func_174813_aQ());
                            }
                        }
                    }
                    q = null;
                    canBeKilled = false;
                    this.damage = this.mindmg.getValue();
                    difference = this.mindiff.getValue();
                    if (blocks.size() > 0) {
                        if (enemies.size() > 0) {
                            enemies.iterator();
                            while (iterator4.hasNext()) {
                                entity2 = iterator4.next();
                                if (this.mode.getValue().equals(TargetMode.SINGLE) && AutoCrystal2.renderEnt != null && !AutoCrystal2.renderEnt.func_70005_c_().equals(entity2.func_70005_c_()) && !Enemies.isEnemy(entity2.func_70005_c_())) {
                                    continue;
                                }
                                else if (entity2.func_70032_d((Entity)AutoCrystal2.mc.field_71439_g) > this.playerdistance.getValue()) {
                                    continue;
                                }
                                else if (entity2 instanceof EntityPlayer && ((EntityPlayer)entity2).func_184812_l_()) {
                                    continue;
                                }
                                else if (Friends.uwu.toLowerCase().contains(entity2.func_70005_c_().toLowerCase())) {
                                    continue;
                                }
                                else {
                                    faceplace = (((EntityLivingBase)entity2).func_110143_aJ() + ((EntityLivingBase)entity2).func_110139_bj() <= this.faceplacehealth.getValue());
                                    blocks.sort(Comparator.comparing(e -> entity2.func_70011_f(e.crystalPos.field_177962_a + 0.5, (double)(e.crystalPos.field_177960_b + 1), e.crystalPos.field_177961_c + 0.5)));
                                    blocks.iterator();
                                    while (iterator5.hasNext()) {
                                        blockPos = iterator5.next();
                                        if (BlockInteractionHelper.blockDistance(blockPos.crystalPos.field_177962_a + 0.5, blockPos.crystalPos.field_177960_b + 1, blockPos.crystalPos.field_177961_c + 0.5, entity2.func_174813_aQ().func_189972_c().field_72450_a, entity2.func_174813_aQ().func_189972_c().field_72448_b, entity2.func_174813_aQ().func_189972_c().field_72449_c) > this.distance.getValue()) {
                                            continue;
                                        }
                                        else {
                                            d = calculateDamage(blockPos.crystalPos.field_177962_a + 0.5, blockPos.crystalPos.field_177960_b + 1, blockPos.crystalPos.field_177961_c + 0.5, entity2);
                                            self = blockPos.selfDamage;
                                            canbekilled = (d > ((EntityLivingBase)entity2).func_110143_aJ() + ((EntityLivingBase)entity2).func_110139_bj());
                                            if (self > d) {
                                                if (!canbekilled) {
                                                    continue;
                                                }
                                                else if (AutoCrystal2.mc.field_71439_g.func_110143_aJ() + AutoCrystal2.mc.field_71439_g.func_110139_bj() - self <= this.afterdamagevalue.getValue() && !faceplace) {
                                                    continue;
                                                }
                                            }
                                            Label_3126_1: {
                                                if (!canbekilled || canBeKilled) {
                                                    if (this.owouwuisgud.getValue().equals(owouwu.MostDamage)) {
                                                        if (d > this.damage) {
                                                            break Label_3126_1;
                                                        }
                                                    }
                                                    else if (canBeKilled) {
                                                        if (canbekilled && d - self > difference) {
                                                            break Label_3126_1;
                                                        }
                                                    }
                                                    else if (d - self > difference) {
                                                        break Label_3126_1;
                                                    }
                                                    killAura5 = (KillAura)ModuleManager.getModuleByName("KillAura");
                                                    if (!KillAura.isAiming) {
                                                        if (this.owouwuisgud.getValue().equals(owouwu.MostDamage)) {
                                                            if (this.damage != this.mindmg.getValue()) {
                                                                continue;
                                                            }
                                                        }
                                                        else if (difference != this.mindiff.getValue()) {
                                                            continue;
                                                        }
                                                        if (faceplace) {
                                                            if (self > d && d < ((EntityLivingBase)entity2).func_110143_aJ() + ((EntityLivingBase)entity2).func_110139_bj()) {
                                                                continue;
                                                            }
                                                            else if (self + this.antideath.getValue() > AutoCrystal2.mc.field_71439_g.func_110143_aJ() + AutoCrystal2.mc.field_71439_g.func_110139_bj() && this.suicideprot.getValue()) {
                                                                continue;
                                                            }
                                                            else if (self > this.selfdmg.getValue() && this.maxselfcalculations.getValue()) {
                                                                continue;
                                                            }
                                                            else {
                                                                this.damage = d;
                                                                q = blockPos.crystalPos;
                                                                canBeKilled = (canBeKilled || canbekilled);
                                                                difference = d - self;
                                                                AutoCrystal2.renderEnt = entity2;
                                                                continue;
                                                            }
                                                        }
                                                        else {
                                                            continue;
                                                        }
                                                    }
                                                    else {
                                                        continue;
                                                    }
                                                }
                                            }
                                            if ((d < this.mindmg.getValue() || d - self < this.mindiff.getValue()) && !faceplace && !canbekilled) {
                                                continue;
                                            }
                                            else if (self + this.antideath.getValue() > AutoCrystal2.mc.field_71439_g.func_110143_aJ() + AutoCrystal2.mc.field_71439_g.func_110139_bj() && this.suicideprot.getValue()) {
                                                continue;
                                            }
                                            else if (self > this.selfdmg.getValue() && this.maxselfcalculations.getValue()) {
                                                continue;
                                            }
                                            else {
                                                this.damage = d;
                                                canBeKilled = (canBeKilled || canbekilled);
                                                difference = d - self;
                                                q = blockPos.crystalPos;
                                                AutoCrystal2.renderEnt = entity2;
                                            }
                                        }
                                    }
                                    if (canBeKilled && q != null) {
                                        break;
                                    }
                                    else {
                                        continue;
                                    }
                                }
                            }
                        }
                        if (this.damage == this.mindmg.getValue() || this.damage < 1.0) {
                            entities.iterator();
                            while (iterator6.hasNext()) {
                                entity3 = iterator6.next();
                                if (this.mode.getValue().equals(TargetMode.SINGLE) && AutoCrystal2.renderEnt != null && !AutoCrystal2.renderEnt.func_70005_c_().equals(entity3.func_70005_c_())) {
                                    continue;
                                }
                                else if (entity3.func_70032_d((Entity)AutoCrystal2.mc.field_71439_g) > this.playerdistance.getValue()) {
                                    continue;
                                }
                                else if (entity3 instanceof EntityPlayer && ((EntityPlayer)entity3).func_184812_l_()) {
                                    continue;
                                }
                                else if (Friends.uwu.toLowerCase().contains(entity3.func_70005_c_().toLowerCase())) {
                                    continue;
                                }
                                else {
                                    faceplace2 = (((EntityLivingBase)entity3).func_110143_aJ() + ((EntityLivingBase)entity3).func_110139_bj() <= this.faceplacehealth.getValue());
                                    blocks.sort(Comparator.comparing(e -> entity3.func_70011_f(e.crystalPos.field_177962_a + 0.5, (double)(e.crystalPos.field_177960_b + 1), e.crystalPos.field_177961_c + 0.5)));
                                    blocks.iterator();
                                    while (iterator7.hasNext()) {
                                        blockPos2 = iterator7.next();
                                        if (BlockInteractionHelper.blockDistance(blockPos2.crystalPos.field_177962_a + 0.5, blockPos2.crystalPos.field_177960_b + 1, blockPos2.crystalPos.field_177961_c + 0.5, entity3.func_174813_aQ().func_189972_c().field_72450_a, entity3.func_174813_aQ().func_189972_c().field_72448_b, entity3.func_174813_aQ().func_189972_c().field_72449_c) > this.distance.getValue()) {
                                            continue;
                                        }
                                        else {
                                            d2 = calculateDamage(blockPos2.crystalPos.field_177962_a + 0.5, blockPos2.crystalPos.field_177960_b + 1, blockPos2.crystalPos.field_177961_c + 0.5, entity3);
                                            self2 = blockPos2.selfDamage;
                                            canbekilled2 = (d2 > ((EntityLivingBase)entity3).func_110143_aJ() + ((EntityLivingBase)entity3).func_110139_bj());
                                            if (self2 > d2) {
                                                if (!canbekilled2) {
                                                    continue;
                                                }
                                                else if (AutoCrystal2.mc.field_71439_g.func_110143_aJ() + AutoCrystal2.mc.field_71439_g.func_110139_bj() - self2 <= this.afterdamagevalue.getValue() && !faceplace2) {
                                                    continue;
                                                }
                                            }
                                            Label_4192_1: {
                                                if (!canbekilled2 || canBeKilled) {
                                                    if (this.owouwuisgud.getValue().equals(owouwu.MostDamage)) {
                                                        if (d2 > this.damage) {
                                                            break Label_4192_1;
                                                        }
                                                    }
                                                    else if (canBeKilled) {
                                                        if (canbekilled2 && d2 - self2 > difference) {
                                                            break Label_4192_1;
                                                        }
                                                    }
                                                    else if (d2 - self2 > difference) {
                                                        break Label_4192_1;
                                                    }
                                                    killAura6 = (KillAura)ModuleManager.getModuleByName("KillAura");
                                                    if (!KillAura.isAiming) {
                                                        if (this.owouwuisgud.getValue().equals(owouwu.MostDamage)) {
                                                            if (this.damage != this.mindmg.getValue()) {
                                                                continue;
                                                            }
                                                        }
                                                        else if (difference != this.mindiff.getValue()) {
                                                            continue;
                                                        }
                                                        if (faceplace2) {
                                                            if (self2 > d2 && d2 < ((EntityLivingBase)entity3).func_110143_aJ() + ((EntityLivingBase)entity3).func_110139_bj()) {
                                                                continue;
                                                            }
                                                            else if (self2 + this.antideath.getValue() > AutoCrystal2.mc.field_71439_g.func_110143_aJ() + AutoCrystal2.mc.field_71439_g.func_110139_bj() && this.suicideprot.getValue()) {
                                                                continue;
                                                            }
                                                            else if (self2 > this.selfdmg.getValue() && this.maxselfcalculations.getValue()) {
                                                                continue;
                                                            }
                                                            else {
                                                                this.damage = d2;
                                                                q = blockPos2.crystalPos;
                                                                canBeKilled = (canBeKilled || canbekilled2);
                                                                difference = d2 - self2;
                                                                AutoCrystal2.renderEnt = entity3;
                                                                continue;
                                                            }
                                                        }
                                                        else {
                                                            continue;
                                                        }
                                                    }
                                                    else {
                                                        continue;
                                                    }
                                                }
                                            }
                                            if ((d2 < this.mindmg.getValue() || d2 - self2 < this.mindiff.getValue()) && !faceplace2 && !canbekilled2) {
                                                continue;
                                            }
                                            else if (self2 + this.antideath.getValue() > AutoCrystal2.mc.field_71439_g.func_110143_aJ() + AutoCrystal2.mc.field_71439_g.func_110139_bj() && this.suicideprot.getValue()) {
                                                continue;
                                            }
                                            else if (self2 > this.selfdmg.getValue() && this.maxselfcalculations.getValue()) {
                                                continue;
                                            }
                                            else {
                                                this.damage = d2;
                                                canBeKilled = (canBeKilled || canbekilled2);
                                                difference = d2 - self2;
                                                q = blockPos2.crystalPos;
                                                AutoCrystal2.renderEnt = entity3;
                                            }
                                        }
                                    }
                                    if (canBeKilled && q != null) {
                                        break;
                                    }
                                    else {
                                        continue;
                                    }
                                }
                            }
                            if ((q == null || this.lastDamage - this.damage > 5.0) && this.entityignore.getValue() && this.multiplace.getValue() && !shouldEntityIgnore) {
                                blockPosList = new ArrayList<BlockPos>();
                                blocks.forEach(e -> blockPosList.add(e.crystalPos));
                                if (AutoCrystal2.mc.field_71439_g != null && AutoCrystal2.mc.field_71441_e != null && this.prediction.getValue()) {
                                    entities.iterator();
                                    while (iterator8.hasNext()) {
                                        x3 = iterator8.next();
                                        if (x3.func_70032_d((Entity)AutoCrystal2.mc.field_71439_g) > 15.0f) {
                                            continue;
                                        }
                                        else if (!(x3 instanceof EntityPlayer)) {
                                            continue;
                                        }
                                        else {
                                            f5 = x3.field_70130_N / 2.0f;
                                            f6 = x3.field_70131_O;
                                            x3.func_174826_a(new AxisAlignedBB(x3.field_70165_t - f5, x3.field_70163_u, x3.field_70161_v - f5, x3.field_70165_t + f5, x3.field_70163_u + f6, x3.field_70161_v + f5));
                                            y3 = EntityUtil.getPredictedPosition(x3, this.predictedTicks.getValue());
                                            x3.func_174826_a(y3.func_174813_aQ());
                                        }
                                    }
                                }
                                blocks2 = this.findCrystalBlocks(true, blockPosList, sphereBlocks, (List<Entity>)allEntities);
                                canBeKilled = false;
                                this.damage = this.mindmg.getValue();
                                difference2 = this.mindiff.getValue();
                                if (AutoCrystal2.mc.field_71439_g != null && AutoCrystal2.mc.field_71441_e != null && this.prediction.getValue()) {
                                    entities.iterator();
                                    while (iterator9.hasNext()) {
                                        x4 = iterator9.next();
                                        if (x4.func_70032_d((Entity)AutoCrystal2.mc.field_71439_g) > 15.0f) {
                                            continue;
                                        }
                                        else if (!(x4 instanceof EntityPlayer)) {
                                            continue;
                                        }
                                        else {
                                            f7 = x4.field_70130_N / 2.0f;
                                            f8 = x4.field_70131_O;
                                            x4.func_174826_a(new AxisAlignedBB(x4.field_70165_t - f7, x4.field_70163_u, x4.field_70161_v - f7, x4.field_70165_t + f7, x4.field_70163_u + f8, x4.field_70161_v + f7));
                                            y4 = EntityUtil.getPredictedPosition(x4, this.placepredictedTicks.getValue());
                                            x4.func_174826_a(y4.func_174813_aQ());
                                        }
                                    }
                                }
                                shouldSkip = false;
                                if (enemies.size() > 0) {
                                    enemies.iterator();
                                    while (iterator10.hasNext()) {
                                        entity4 = iterator10.next();
                                        if (this.mode.getValue().equals(TargetMode.SINGLE) && AutoCrystal2.renderEnt != null && !AutoCrystal2.renderEnt.func_70005_c_().equals(entity4.func_70005_c_()) && Enemies.isEnemy(entity4.func_70005_c_())) {
                                            continue;
                                        }
                                        else if (entity4.func_70032_d((Entity)AutoCrystal2.mc.field_71439_g) > this.playerdistance.getValue()) {
                                            continue;
                                        }
                                        else if (entity4 instanceof EntityPlayer && ((EntityPlayer)entity4).func_184812_l_()) {
                                            continue;
                                        }
                                        else if (Friends.uwu.toLowerCase().contains(entity4.func_70005_c_().toLowerCase())) {
                                            continue;
                                        }
                                        else {
                                            faceplace3 = (((EntityLivingBase)entity4).func_110143_aJ() + ((EntityLivingBase)entity4).func_110139_bj() <= this.faceplacehealth.getValue());
                                            blocks2.sort(Comparator.comparing(e -> entity4.func_70011_f(e.crystalPos.field_177962_a + 0.5, (double)(e.crystalPos.field_177960_b + 1), e.crystalPos.field_177961_c + 0.5)));
                                            blocks2.iterator();
                                            while (iterator11.hasNext()) {
                                                blockPos3 = iterator11.next();
                                                if (BlockInteractionHelper.blockDistance(blockPos3.crystalPos.field_177962_a + 0.5, blockPos3.crystalPos.field_177960_b + 1, blockPos3.crystalPos.field_177961_c + 0.5, entity4.func_174813_aQ().func_189972_c().field_72450_a, entity4.func_174813_aQ().func_189972_c().field_72448_b, entity4.func_174813_aQ().func_189972_c().field_72449_c) > this.distance.getValue()) {
                                                    continue;
                                                }
                                                else {
                                                    d3 = calculateDamage(blockPos3.crystalPos.field_177962_a + 0.5, blockPos3.crystalPos.field_177960_b + 1, blockPos3.crystalPos.field_177961_c + 0.5, entity4);
                                                    self3 = blockPos3.selfDamage;
                                                    canbekilled3 = (d3 > ((EntityLivingBase)entity4).func_110143_aJ() + ((EntityLivingBase)entity4).func_110139_bj());
                                                    if (self3 > d3) {
                                                        if (!canbekilled3) {
                                                            continue;
                                                        }
                                                        else if (AutoCrystal2.mc.field_71439_g.func_110143_aJ() + AutoCrystal2.mc.field_71439_g.func_110139_bj() - self3 <= this.afterdamagevalue.getValue() && !faceplace3) {
                                                            continue;
                                                        }
                                                    }
                                                    Label_5799_1: {
                                                        if (!canbekilled3 || canBeKilled) {
                                                            if (this.owouwuisgud.getValue().equals(owouwu.MostDamage)) {
                                                                if (d3 > this.damage) {
                                                                    break Label_5799_1;
                                                                }
                                                            }
                                                            else if (canBeKilled) {
                                                                if (canbekilled3 && d3 - self3 > difference2) {
                                                                    break Label_5799_1;
                                                                }
                                                            }
                                                            else if (d3 - self3 > difference2) {
                                                                break Label_5799_1;
                                                            }
                                                            killAura7 = (KillAura)ModuleManager.getModuleByName("KillAura");
                                                            if (!KillAura.isAiming) {
                                                                if (this.owouwuisgud.getValue().equals(owouwu.MostDamage)) {
                                                                    if (this.damage != this.mindmg.getValue()) {
                                                                        continue;
                                                                    }
                                                                }
                                                                else if (difference2 != this.mindiff.getValue()) {
                                                                    continue;
                                                                }
                                                                if (faceplace3) {
                                                                    if (self3 > d3 && d3 < ((EntityLivingBase)entity4).func_110143_aJ() + ((EntityLivingBase)entity4).func_110139_bj()) {
                                                                        continue;
                                                                    }
                                                                    else if (self3 + this.antideath.getValue() > AutoCrystal2.mc.field_71439_g.func_110143_aJ() + AutoCrystal2.mc.field_71439_g.func_110139_bj() && this.suicideprot.getValue()) {
                                                                        continue;
                                                                    }
                                                                    else if (self3 > this.selfdmg.getValue() && this.maxselfcalculations.getValue()) {
                                                                        continue;
                                                                    }
                                                                    else {
                                                                        this.damage = d3;
                                                                        q = blockPos3.crystalPos;
                                                                        canBeKilled = (canBeKilled || canbekilled3);
                                                                        difference2 = d3 - self3;
                                                                        AutoCrystal2.renderEnt = entity4;
                                                                        shouldSkip = true;
                                                                        continue;
                                                                    }
                                                                }
                                                                else {
                                                                    continue;
                                                                }
                                                            }
                                                            else {
                                                                continue;
                                                            }
                                                        }
                                                    }
                                                    if ((d3 < this.mindmg.getValue() || d3 - self3 < this.mindiff.getValue()) && !faceplace3 && !canbekilled3) {
                                                        continue;
                                                    }
                                                    else if (self3 + this.antideath.getValue() > AutoCrystal2.mc.field_71439_g.func_110143_aJ() + AutoCrystal2.mc.field_71439_g.func_110139_bj() && this.suicideprot.getValue()) {
                                                        continue;
                                                    }
                                                    else if (self3 > this.selfdmg.getValue() && this.maxselfcalculations.getValue()) {
                                                        continue;
                                                    }
                                                    else {
                                                        this.damage = d3;
                                                        canBeKilled = (canBeKilled || canbekilled3);
                                                        difference2 = d3 - self3;
                                                        q = blockPos3.crystalPos;
                                                        AutoCrystal2.renderEnt = entity4;
                                                        shouldSkip = true;
                                                    }
                                                }
                                            }
                                            if (canBeKilled && q != null) {
                                                break;
                                            }
                                            else {
                                                continue;
                                            }
                                        }
                                    }
                                }
                                if (!shouldSkip) {
                                    entities.iterator();
                                    while (iterator12.hasNext()) {
                                        entity5 = iterator12.next();
                                        if (this.mode.getValue().equals(TargetMode.SINGLE) && AutoCrystal2.renderEnt != null && !AutoCrystal2.renderEnt.func_70005_c_().equals(entity5.func_70005_c_())) {
                                            continue;
                                        }
                                        else if (entity5.func_70032_d((Entity)AutoCrystal2.mc.field_71439_g) > this.playerdistance.getValue()) {
                                            continue;
                                        }
                                        else if (entity5 instanceof EntityPlayer && ((EntityPlayer)entity5).func_184812_l_()) {
                                            continue;
                                        }
                                        else if (Friends.uwu.toLowerCase().contains(entity5.func_70005_c_().toLowerCase())) {
                                            continue;
                                        }
                                        else {
                                            faceplace4 = (((EntityLivingBase)entity5).func_110143_aJ() + ((EntityLivingBase)entity5).func_110139_bj() <= this.faceplacehealth.getValue());
                                            blocks2.sort(Comparator.comparing(e -> entity5.func_70011_f(e.crystalPos.field_177962_a + 0.5, (double)(e.crystalPos.field_177960_b + 1), e.crystalPos.field_177961_c + 0.5)));
                                            blocks2.iterator();
                                            while (iterator13.hasNext()) {
                                                blockPos4 = iterator13.next();
                                                if (BlockInteractionHelper.blockDistance(blockPos4.crystalPos.field_177962_a + 0.5, blockPos4.crystalPos.field_177960_b + 1, blockPos4.crystalPos.field_177961_c + 0.5, entity5.func_174813_aQ().func_189972_c().field_72450_a, entity5.func_174813_aQ().func_189972_c().field_72448_b, entity5.func_174813_aQ().func_189972_c().field_72449_c) > this.distance.getValue()) {
                                                    continue;
                                                }
                                                else {
                                                    d4 = calculateDamage(blockPos4.crystalPos.field_177962_a + 0.5, blockPos4.crystalPos.field_177960_b + 1, blockPos4.crystalPos.field_177961_c + 0.5, entity5);
                                                    self4 = blockPos4.selfDamage;
                                                    canbekilled4 = (d4 > ((EntityLivingBase)entity5).func_110143_aJ() + ((EntityLivingBase)entity5).func_110139_bj());
                                                    if (self4 > d4) {
                                                        if (!canbekilled4) {
                                                            continue;
                                                        }
                                                        else if (AutoCrystal2.mc.field_71439_g.func_110143_aJ() + AutoCrystal2.mc.field_71439_g.func_110139_bj() - self4 <= this.afterdamagevalue.getValue() && !faceplace4) {
                                                            continue;
                                                        }
                                                    }
                                                    Label_6846_1: {
                                                        if (!canbekilled4 || canBeKilled) {
                                                            if (this.owouwuisgud.getValue().equals(owouwu.MostDamage)) {
                                                                if (d4 > this.damage) {
                                                                    break Label_6846_1;
                                                                }
                                                            }
                                                            else if (canBeKilled) {
                                                                if (canbekilled4 && d4 - self4 > difference2) {
                                                                    break Label_6846_1;
                                                                }
                                                            }
                                                            else if (d4 - self4 > difference2) {
                                                                break Label_6846_1;
                                                            }
                                                            killAura8 = (KillAura)ModuleManager.getModuleByName("KillAura");
                                                            if (!KillAura.isAiming) {
                                                                if (this.owouwuisgud.getValue().equals(owouwu.MostDamage)) {
                                                                    if (this.damage != this.mindmg.getValue()) {
                                                                        continue;
                                                                    }
                                                                }
                                                                else if (difference2 != this.mindiff.getValue()) {
                                                                    continue;
                                                                }
                                                                if (faceplace4) {
                                                                    if (self4 > d4 && d4 < ((EntityLivingBase)entity5).func_110143_aJ() + ((EntityLivingBase)entity5).func_110139_bj()) {
                                                                        continue;
                                                                    }
                                                                    else if (self4 + this.antideath.getValue() > AutoCrystal2.mc.field_71439_g.func_110143_aJ() + AutoCrystal2.mc.field_71439_g.func_110139_bj() && this.suicideprot.getValue()) {
                                                                        continue;
                                                                    }
                                                                    else if (self4 > this.selfdmg.getValue() && this.maxselfcalculations.getValue()) {
                                                                        continue;
                                                                    }
                                                                    else {
                                                                        this.damage = d4;
                                                                        q = blockPos4.crystalPos;
                                                                        canBeKilled = (canBeKilled || canbekilled4);
                                                                        difference2 = d4 - self4;
                                                                        AutoCrystal2.renderEnt = entity5;
                                                                        continue;
                                                                    }
                                                                }
                                                                else {
                                                                    continue;
                                                                }
                                                            }
                                                            else {
                                                                continue;
                                                            }
                                                        }
                                                    }
                                                    if ((d4 < this.mindmg.getValue() || d4 - self4 < this.mindiff.getValue()) && !faceplace4 && !canbekilled4) {
                                                        continue;
                                                    }
                                                    else if (self4 + this.antideath.getValue() > AutoCrystal2.mc.field_71439_g.func_110143_aJ() + AutoCrystal2.mc.field_71439_g.func_110139_bj() && this.suicideprot.getValue()) {
                                                        continue;
                                                    }
                                                    else if (self4 > this.selfdmg.getValue() && this.maxselfcalculations.getValue()) {
                                                        continue;
                                                    }
                                                    else {
                                                        this.damage = d4;
                                                        canBeKilled = (canBeKilled || canbekilled4);
                                                        difference2 = d4 - self4;
                                                        q = blockPos4.crystalPos;
                                                        AutoCrystal2.renderEnt = entity5;
                                                    }
                                                }
                                            }
                                            if (canBeKilled && q != null) {
                                                break;
                                            }
                                            else {
                                                continue;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (this.damage == this.mindmg.getValue() || this.damage < 1.0) {
                        this.render = null;
                        if (this.mode.getValue().equals(TargetMode.SINGLE)) {
                            AutoCrystal2.renderEnt = null;
                        }
                        if (!this.rotated) {
                            killAura9 = (KillAura)ModuleManager.getModuleByName("KillAura");
                            if (!KillAura.isAiming && !((Scaffold)ModuleManager.getModuleByName("Scaffold")).rotated && !((AntiSurround)ModuleManager.getModuleByName("AntiSurround")).rotated && !((PacketXP)ModuleManager.getModuleByName("PacketXP")).rotated) {
                                resetRotation();
                            }
                        }
                        if (!didSomething) {
                            this.stage = 0;
                        }
                    }
                    else {
                        this.render = q;
                        if (this.place.getValue()) {
                            switched2 = false;
                            if (!offhand && AutoCrystal2.mc.field_71439_g.field_71071_by.field_70461_c != crystalSlot) {
                                if (this.autoSwitch.getValue() && !this.switchCooldown) {
                                    if ((this.noGappleSwitch.getValue() && this.isEatingGap()) || (this.nomineSwitch.getValue() && this.isMiningBlock())) {
                                        if (!this.rotated) {
                                            killAura10 = (KillAura)ModuleManager.getModuleByName("KillAura");
                                            if (!KillAura.isAiming && !((Scaffold)ModuleManager.getModuleByName("Scaffold")).rotated && !((AntiSurround)ModuleManager.getModuleByName("AntiSurround")).rotated && !((PacketXP)ModuleManager.getModuleByName("PacketXP")).rotated) {
                                                resetRotation();
                                            }
                                        }
                                        if (!didSomething) {
                                            this.stage = 0;
                                        }
                                        return;
                                    }
                                    else if (!this.rotate.getValue() || !this.shouldHit) {
                                        if (this.ghostAutoSwitch.getValue()) {
                                            AutoCrystal2.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(crystalSlot));
                                            switched2 = true;
                                        }
                                        else {
                                            AutoCrystal2.mc.field_71439_g.field_71071_by.field_70461_c = crystalSlot;
                                        }
                                    }
                                    else {
                                        this.shouldSwitch = true;
                                    }
                                }
                                else {
                                    if (!this.rotated) {
                                        killAura11 = (KillAura)ModuleManager.getModuleByName("KillAura");
                                        if (!KillAura.isAiming && !((Scaffold)ModuleManager.getModuleByName("Scaffold")).rotated && !((AntiSurround)ModuleManager.getModuleByName("AntiSurround")).rotated && !((PacketXP)ModuleManager.getModuleByName("PacketXP")).rotated) {
                                            resetRotation();
                                        }
                                    }
                                    if (!didSomething) {
                                        this.stage = 0;
                                    }
                                    return;
                                }
                            }
                            if (this.rotate.getValue() && this.placeRotate.getValue()) {
                                if (this.isDisabled()) {
                                    if (!didSomething) {
                                        this.stage = 0;
                                    }
                                    return;
                                }
                                else if (!this.rotated) {
                                    lookAtPacket(q.field_177962_a + 0.5, q.field_177960_b + 1, q.field_177961_c + 0.5, (EntityPlayer)AutoCrystal2.mc.field_71439_g, false);
                                    this.rotated = true;
                                }
                                else if (this.lastcrystal.field_70165_t != q.field_177962_a + 0.5 || this.lastcrystal.field_70163_u != q.field_177960_b + 1 || this.lastcrystal.field_70161_v != q.field_177961_c + 0.5) {
                                    killAura12 = (KillAura)ModuleManager.getModuleByName("KillAura");
                                    if (!KillAura.isAiming && !((Scaffold)ModuleManager.getModuleByName("Scaffold")).rotated && !((AntiSurround)ModuleManager.getModuleByName("AntiSurround")).rotated && !((PacketXP)ModuleManager.getModuleByName("PacketXP")).rotated) {
                                        resetRotation();
                                    }
                                    if (!didSomething) {
                                        this.stage = 0;
                                    }
                                    return;
                                }
                            }
                            if (!this.shouldHit) {
                                result = AutoCrystal2.mc.field_71441_e.func_72933_a(new Vec3d(AutoCrystal2.mc.field_71439_g.field_70165_t, AutoCrystal2.mc.field_71439_g.field_70163_u + AutoCrystal2.mc.field_71439_g.func_70047_e(), AutoCrystal2.mc.field_71439_g.field_70161_v), new Vec3d(q.field_177962_a + 0.5, (double)q.field_177960_b, q.field_177961_c + 0.5));
                                if (result == null || result.field_178784_b == null) {
                                    f9 = EnumFacing.UP;
                                }
                                else {
                                    f9 = result.field_178784_b;
                                }
                                if (this.legitplace.getValue()) {
                                    AutoCrystal2.mc.field_71442_b.func_187099_a(AutoCrystal2.mc.field_71439_g, AutoCrystal2.mc.field_71441_e, q, f9, new Vec3d(q.field_177962_a + 0.5, (double)(q.field_177960_b + 1), q.field_177961_c + 0.5), offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                                }
                                else {
                                    field_71174_a = AutoCrystal2.mc.field_71439_g.field_71174_a;
                                    new CPacketPlayerTryUseItemOnBlock(q, f9, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f);
                                    field_71174_a.func_147297_a((Packet)cPacketPlayerTryUseItemOnBlock);
                                }
                                this.placed = true;
                                this.ms = System.currentTimeMillis();
                                if (switched2) {
                                    AutoCrystal2.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(AutoCrystal2.mc.field_71439_g.field_71071_by.field_70461_c));
                                }
                                if (this.smartPredictHit.getValue() && AutoCrystal2.renderEnt instanceof EntityPlayer) {
                                    try {
                                        if (ModuleManager.getModuleByName("PredictHit").isDisabled()) {
                                            ModuleManager.getModuleByName("PredictHit").enable();
                                        }
                                        if (this.isMending || ModuleManager.getModuleByName("PacketXP").isEnabled()) {
                                            ModuleManager.getModuleByName("PredictHit").disable();
                                            return;
                                        }
                                        else {
                                            if (canBeKilled) {
                                                if (this.safePredictHit.getValue()) {
                                                    ModuleManager.getModuleByName("PredictHit").disable();
                                                    return;
                                                }
                                                else if (((EntityPlayer)AutoCrystal2.renderEnt).func_184614_ca().func_77973_b() != Items.field_190929_cY && ((EntityPlayer)AutoCrystal2.renderEnt).func_184592_cb().func_77973_b() != Items.field_190929_cY) {
                                                    ModuleManager.getModuleByName("PredictHit").disable();
                                                    return;
                                                }
                                            }
                                            AutoCrystal2.mc.field_71438_f.field_72738_E.forEach((integer, destroyBlockProgress) -> {
                                                if (destroyBlockProgress != null) {
                                                    blockPos5 = destroyBlockProgress.func_180246_b();
                                                    if (AutoCrystal2.mc.field_71441_e.func_180495_p(blockPos5).func_177230_c() != Blocks.field_150350_a && AutoCrystal2.mc.field_71441_e.func_180495_p(blockPos5).func_177230_c() != Blocks.field_150357_h) {
                                                        if (blockPos5.func_185332_f((int)AutoCrystal2.mc.field_71439_g.field_70165_t, (int)AutoCrystal2.mc.field_71439_g.field_70163_u, (int)AutoCrystal2.mc.field_71439_g.field_70161_v) <= 7.0) {
                                                            ModuleManager.getModuleByName("PredictHit").disable();
                                                        }
                                                    }
                                                }
                                                return;
                                            });
                                            if (ModuleManager.getModuleByName("PredictHit").isDisabled()) {
                                                return;
                                            }
                                            else {
                                                AutoCrystal2.mc.field_71441_e.field_73010_i.iterator();
                                                while (iterator14.hasNext()) {
                                                    player = iterator14.next();
                                                    if (player.func_70032_d((Entity)AutoCrystal2.mc.field_71439_g) > 10.0f) {
                                                        continue;
                                                    }
                                                    else if (player.func_184614_ca().func_77973_b() == Items.field_151062_by || player.func_184614_ca().func_77973_b() == Items.field_151031_f || (player.func_184614_ca().func_77973_b() instanceof ItemTool && !(player.func_184614_ca().func_77973_b() instanceof ItemSword))) {
                                                        ModuleManager.getModuleByName("PredictHit").disable();
                                                        return;
                                                    }
                                                    else if (player.func_110143_aJ() + player.func_110139_bj() < 16.0f && !player.equals((Object)AutoCrystal2.mc.field_71439_g) && this.safePredictHit.getValue()) {
                                                        ModuleManager.getModuleByName("PredictHit").disable();
                                                        return;
                                                    }
                                                    else {
                                                        continue;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    catch (Exception ex) {}
                                }
                                if (ModuleManager.isModuleEnabled("PredictHit")) {
                                    switched3 = false;
                                    if (this.newSlot != -1 && this.ghostAutoSwitch.getValue()) {
                                        AutoCrystal2.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.newSlot));
                                        switched3 = true;
                                    }
                                    for (j = 0; j < ((AutoCrystalLitePredictHit)ModuleManager.getModuleByName("PredictHit")).predictHitValue.getValue(); ++j) {
                                        useEntity = new CPacketUseEntity();
                                        useEntity.field_149567_a = this.lastEntityID + j + 1;
                                        useEntity.field_149566_b = CPacketUseEntity.Action.ATTACK;
                                        AutoCrystal2.mc.field_71439_g.field_71174_a.func_147297_a((Packet)useEntity);
                                    }
                                    if (switched3) {
                                        AutoCrystal2.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(AutoCrystal2.mc.field_71439_g.field_71071_by.field_70461_c));
                                    }
                                }
                            }
                            else {
                                this.shouldPlace = true;
                                this.shouldPlacePos = q;
                                this.placed = true;
                                this.ms = System.currentTimeMillis();
                            }
                            this.lastDamage = this.damage;
                            ++this.placements;
                        }
                    }
                }
                else {
                    ++this.placetick;
                }
            }
        });
    }
    
    @Override
    public void onWorldRender(final RenderEvent event) {
        if (this.render != null) {
            final float[] hue = { System.currentTimeMillis() % 11520L / 11520.0f * 2.0f };
            final int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
            final int red2 = rgb >> 16 & 0xFF;
            final int green2 = rgb >> 8 & 0xFF;
            final int blue2 = rgb & 0xFF;
            final IBlockState iblockstate = AutoCrystal2.mc.field_71441_e.func_180495_p(this.render);
            final Vec3d interp = MathUtil.interpolateEntity((Entity)AutoCrystal2.mc.field_71439_g, AutoCrystal2.mc.func_184121_ak());
            UwUGodTessellator.prepare(7);
            try {
                if (this.rainbow.getValue()) {
                    UwUGodTessellator.drawBox2(iblockstate.func_185918_c((World)AutoCrystal2.mc.field_71441_e, this.render).func_72317_d(-interp.field_72450_a, -interp.field_72448_b, -interp.field_72449_c), red2, green2, blue2, this.alpha.getValue(), 63);
                }
                else {
                    UwUGodTessellator.drawBox2(iblockstate.func_185918_c((World)AutoCrystal2.mc.field_71441_e, this.render).func_72317_d(-interp.field_72450_a, -interp.field_72448_b, -interp.field_72449_c), this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue(), 63);
                }
            }
            catch (Exception ex) {}
            UwUGodTessellator.release();
            try {
                if (this.box.getValue()) {
                    if (this.rainbow2.getValue()) {
                        UwUGodTessellator.drawBoundingBox(iblockstate.func_185918_c((World)AutoCrystal2.mc.field_71441_e, this.render).func_186662_g(0.0020000000949949026).func_72317_d(-interp.field_72450_a, -interp.field_72448_b, -interp.field_72449_c), this.width.getValue(), red2, green2, blue2, 255);
                    }
                    else {
                        UwUGodTessellator.drawBoundingBox(iblockstate.func_185918_c((World)AutoCrystal2.mc.field_71441_e, this.render).func_186662_g(0.0020000000949949026).func_72317_d(-interp.field_72450_a, -interp.field_72448_b, -interp.field_72449_c), this.width.getValue(), this.red.getValue(), this.green.getValue(), this.blue.getValue(), 255);
                    }
                }
            }
            catch (Exception ex2) {}
            if (this.renderdamage.getValue()) {
                GlStateManager.func_179094_E();
                try {
                    UwUGodTessellator.glBillboardDistanceScaled(this.render.func_177958_n() + 0.5f, this.render.func_177956_o() + 0.5f, this.render.func_177952_p() + 0.5f, (EntityPlayer)AutoCrystal2.mc.field_71439_g, 1.0f);
                    if (this.accuratedamagerender.getValue()) {
                        final double damage = calculateDamage(this.render.func_177958_n() + 0.5, this.render.func_177956_o() + 1, this.render.func_177952_p() + 0.5, AutoCrystal2.renderEnt);
                        final String damageText = ((Math.floor(damage) == damage) ? Integer.valueOf((int)damage) : String.format("%.1f", damage)) + "";
                        GlStateManager.func_179097_i();
                        GlStateManager.func_179137_b(-(AutoCrystal2.mc.field_71466_p.func_78256_a(damageText) / 2.0), 0.0, 0.0);
                        AutoCrystal2.mc.field_71466_p.func_175063_a("§b" + damage, 0.0f, 10.0f, -5592406);
                    }
                    else {
                        final float damage2 = calculateDamage(this.render.func_177958_n() + 0.5, this.render.func_177956_o() + 1, this.render.func_177952_p() + 0.5, AutoCrystal2.renderEnt);
                        final String damageText2 = ((Math.floor(damage2) == damage2) ? Integer.valueOf((int)damage2) : String.format("%.1f", damage2)) + "";
                        GlStateManager.func_179097_i();
                        GlStateManager.func_179137_b(-(AutoCrystal2.mc.field_71466_p.func_78256_a(damageText2) / 2.0), 0.0, 0.0);
                        AutoCrystal2.mc.field_71466_p.func_175063_a("§b" + damageText2, 0.0f, 10.0f, -5592406);
                    }
                }
                catch (Exception ex3) {}
                GlStateManager.func_179121_F();
            }
        }
    }
    
    public boolean isEatingGap() {
        return (AutoCrystal2.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemAppleGold || AutoCrystal2.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemChorusFruit) && AutoCrystal2.mc.field_71439_g.func_184587_cr();
    }
    
    public boolean isMiningBlock() {
        try {
            return AutoCrystal2.mc.field_71442_b.field_78778_j && AutoCrystal2.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemTool;
        }
        catch (Exception o) {
            return false;
        }
    }
    
    public boolean canPlaceCrystal(final BlockPos blockPos, final boolean entityIgnore, final List<Entity> targetList) {
        final BlockPos boost = blockPos.func_177982_a(0, 1, 0);
        final BlockPos boost2 = blockPos.func_177982_a(0, 2, 0);
        if (entityIgnore) {
            boolean w = false;
            for (final Entity qwqawaawa : targetList) {
                if (BlockInteractionHelper.blockDistance(blockPos.field_177962_a + 0.5, blockPos.field_177960_b + 1, blockPos.field_177961_c + 0.5, qwqawaawa.func_174813_aQ().func_189972_c().field_72450_a, qwqawaawa.func_174813_aQ().func_189972_c().field_72448_b, qwqawaawa.func_174813_aQ().func_189972_c().field_72449_c) <= this.distance.getValue()) {
                    w = true;
                    break;
                }
            }
            if (!w) {
                return false;
            }
            if (!AutoCrystal2.mc.field_71441_e.func_72872_a((Class)EntityPlayer.class, new AxisAlignedBB(boost)).isEmpty() || !AutoCrystal2.mc.field_71441_e.func_72872_a((Class)EntityPlayer.class, new AxisAlignedBB(boost2)).isEmpty()) {
                return false;
            }
            if (Math.sqrt(AutoCrystal2.mc.field_71439_g.func_70092_e((double)blockPos.field_177962_a, (double)(blockPos.field_177960_b + 1), (double)blockPos.field_177961_c)) > this.placerange.getValue() || Math.sqrt(AutoCrystal2.mc.field_71439_g.func_70092_e((double)blockPos.field_177962_a, (double)blockPos.field_177960_b, (double)blockPos.field_177961_c)) > this.placerange.getValue()) {
                return false;
            }
            if (Math.sqrt(AutoCrystal2.mc.field_71439_g.func_70092_e(blockPos.field_177962_a + 0.5, (double)(blockPos.field_177960_b + 1), blockPos.field_177961_c + 0.5)) > this.range.getValue()) {
                return false;
            }
            if (this.betterplacements.getValue() && !canBlockBeSeen(blockPos.field_177962_a + 0.5, blockPos.field_177960_b + 1, blockPos.field_177961_c + 0.5) && Math.sqrt(AutoCrystal2.mc.field_71439_g.func_70092_e(blockPos.field_177962_a + 0.5, (double)(blockPos.field_177960_b + 1), blockPos.field_177961_c + 0.5)) > this.placeleastrange.getValue()) {
                return false;
            }
            for (final Entity endercrystal2 : AutoCrystal2.mc.field_71441_e.field_72996_f) {
                if (endercrystal2 instanceof EntityEnderCrystal) {
                    final EntityEnderCrystal endercrystal3 = (EntityEnderCrystal)endercrystal2;
                    final double b = (this.lastcrystal != null) ? this.lastcrystal.func_70011_f(blockPos.field_177962_a + 0.5, (double)(blockPos.field_177960_b + 1), blockPos.field_177961_c + 0.5) : 10000.0;
                    if (b <= 6.0) {
                        continue;
                    }
                    final double a = BlockInteractionHelper.blockDistance2d(blockPos.field_177962_a + 0.5, blockPos.field_177961_c + 0.5, (Entity)endercrystal3);
                    if (a < 2.0 && Math.abs(endercrystal3.field_70163_u - (blockPos.field_177960_b + 1)) < 2.0) {
                        return false;
                    }
                    continue;
                }
            }
            return (AutoCrystal2.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150357_h || AutoCrystal2.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150343_Z) && AutoCrystal2.mc.field_71441_e.func_180495_p(boost).func_177230_c() == Blocks.field_150350_a && (this.mode4b4t.getValue() || AutoCrystal2.mc.field_71441_e.func_180495_p(boost2).func_177230_c() == Blocks.field_150350_a) && AutoCrystal2.mc.field_71441_e.func_72872_a((Class)EntityItem.class, new AxisAlignedBB(boost)).isEmpty() && AutoCrystal2.mc.field_71441_e.func_72872_a((Class)EntityArrow.class, new AxisAlignedBB(boost)).isEmpty();
        }
        else {
            if (Math.sqrt(AutoCrystal2.mc.field_71439_g.func_70092_e((double)blockPos.field_177962_a, (double)(blockPos.field_177960_b + 1), (double)blockPos.field_177961_c)) > this.placerange.getValue() || Math.sqrt(AutoCrystal2.mc.field_71439_g.func_70092_e((double)blockPos.field_177962_a, (double)blockPos.field_177960_b, (double)blockPos.field_177961_c)) > this.placerange.getValue()) {
                return false;
            }
            boolean w = false;
            for (final Entity qwqawaawa : targetList) {
                if (Math.sqrt(qwqawaawa.func_70092_e(blockPos.field_177962_a + 0.5, (double)(blockPos.field_177960_b + 1), blockPos.field_177961_c + 0.5)) <= this.distance.getValue()) {
                    w = true;
                    break;
                }
            }
            return w && (!this.betterplacements.getValue() || canBlockBeSeen(blockPos.field_177962_a + 0.5, blockPos.field_177960_b + 1, blockPos.field_177961_c + 0.5) || Math.sqrt(AutoCrystal2.mc.field_71439_g.func_70092_e(blockPos.field_177962_a + 0.5, (double)(blockPos.field_177960_b + 1), blockPos.field_177961_c + 0.5)) <= this.placeleastrange.getValue()) && (AutoCrystal2.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150357_h || AutoCrystal2.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150343_Z) && AutoCrystal2.mc.field_71441_e.func_180495_p(boost).func_177230_c() == Blocks.field_150350_a && (this.mode4b4t.getValue() || AutoCrystal2.mc.field_71441_e.func_180495_p(boost2).func_177230_c() == Blocks.field_150350_a) && AutoCrystal2.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(boost)).isEmpty() && AutoCrystal2.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(boost2)).isEmpty();
        }
    }
    
    public List<CrystalPosSaver> findCrystalBlocks(final boolean entityIgnore, final List<BlockPos> sphereBlocks, final List<Entity> targetList) {
        final NonNullList<BlockPos> positions = (NonNullList<BlockPos>)NonNullList.func_191196_a();
        positions.addAll((Collection)sphereBlocks.stream().filter(e -> this.canPlaceCrystal(e, entityIgnore, targetList)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        final List<CrystalPosSaver> crystalPos = new ArrayList<CrystalPosSaver>();
        for (final BlockPos blockPos : positions) {
            crystalPos.add(new CrystalPosSaver(blockPos, calculateDamage(blockPos.field_177962_a + 0.5, blockPos.field_177960_b + 1, blockPos.field_177961_c + 0.5, (Entity)AutoCrystal2.mc.field_71439_g)));
        }
        return crystalPos;
    }
    
    public List<CrystalPosSaver> findCrystalBlocks(final boolean entityIgnore, final List<BlockPos> listBlocks, final List<BlockPos> sphereBlocks, final List<Entity> targetList) {
        final NonNullList<BlockPos> positions = (NonNullList<BlockPos>)NonNullList.func_191196_a();
        positions.addAll((Collection)sphereBlocks);
        positions.remove((Object)listBlocks);
        positions.removeIf(e -> !this.canPlaceCrystal(e, entityIgnore, targetList));
        final List<CrystalPosSaver> crystalPos = new ArrayList<CrystalPosSaver>();
        for (final BlockPos blockPos : positions) {
            crystalPos.add(new CrystalPosSaver(blockPos, calculateDamage(blockPos.field_177962_a + 0.5, blockPos.field_177960_b + 1, blockPos.field_177961_c + 0.5, (Entity)AutoCrystal2.mc.field_71439_g)));
        }
        return crystalPos;
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
    
    @Override
    public String getHudInfo() {
        if (this.placed && System.currentTimeMillis() - this.ms > 30L) {
            this.placed = false;
            this.stage = 2;
        }
        return (this.stage == 0) ? null : ((this.stage == 1) ? "Attacking" : "Placing");
    }
    
    public void onEnable() {
        if (AutoCrystal2.mc.field_71439_g == null || AutoCrystal2.mc.field_71441_e == null) {
            this.disable();
            return;
        }
        AutoCrystal2.renderEnt = null;
        this.firstRun = true;
        this.hittick = this.hitdelay.getValue();
        this.placetick = this.placedelay.getValue();
        this.shouldHit = false;
        this.shouldPlace = false;
        this.shouldSwitch = false;
    }
    
    public void onDisable() {
        if (AutoCrystal2.mc.field_71439_g == null || AutoCrystal2.mc.field_71441_e == null) {
            return;
        }
        if (!UUIDStuff.hasAccess()) {
            System.exit(0);
        }
        this.render = null;
        resetRotation();
    }
    
    public static boolean isArmorLow(final EntityPlayer player, final int durability) {
        for (final ItemStack piece : player.field_71071_by.field_70460_b) {
            if (piece == null) {
                return true;
            }
            if (getItemDamage(piece) < durability) {
                return true;
            }
        }
        return false;
    }
    
    public static int getItemDamage(final ItemStack stack) {
        return stack.func_77958_k() - stack.func_77952_i();
    }
    
    public static float getDamageInPercent(final ItemStack stack) {
        return getItemDamage(stack) / (float)stack.func_77958_k() * 100.0f;
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
    
    public enum typesetting
    {
        GENERAL, 
        GENERAL2, 
        NUMBER, 
        COLOR;
    }
    
    public enum TargetMode
    {
        SINGLE, 
        ORIGINAL;
    }
    
    public enum owouwu
    {
        MostDamage, 
        MostAdvantage;
    }
    
    interface IModifier
    {
        void calculateModifier(final Enchantment p0, final int p1);
    }
}
