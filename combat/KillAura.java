// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import java.util.Iterator;
import net.minecraft.entity.EntityLivingBase;
import meow.candycat.uwu.util.EntityUtil;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Comparator;
import meow.candycat.uwu.util.Friends;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import net.minecraft.item.ItemSword;
import meow.candycat.uwu.module.ModuleManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import java.util.function.Predicate;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.world.World;
import net.minecraft.network.play.client.CPacketUseEntity;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import net.minecraft.entity.Entity;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "KillAura", description = "Better Killaura", category = Category.COMBAT)
public class KillAura extends Module
{
    private Setting<Boolean> ghostSwitchSword;
    private Setting<Integer> hitDelay;
    private Setting<mode> Mode;
    private Setting<priorityMode> prio;
    private Setting<Double> range;
    private Setting<Boolean> swordOnly;
    private Setting<Boolean> rotate;
    private Setting<Boolean> caCheck;
    private Setting<Boolean> player;
    private Setting<Boolean> mob;
    private Setting<Boolean> animal;
    private Setting<Boolean> betterAnimations;
    private boolean attack;
    public static boolean isAiming;
    public Entity lastentity;
    Entity target;
    public static KillAura INSTANCE;
    public boolean isAttacking;
    int hitDelays;
    @EventHandler
    private Listener<PacketEvent.Send> sendListener;
    public static boolean isSpoofingAngles;
    public static double yaw;
    public static double pitch;
    @EventHandler
    private Listener<PacketEvent.Receive> packetListener;
    
    public KillAura() {
        this.ghostSwitchSword = this.register(Settings.b("GhostSwitchSword", false));
        this.hitDelay = this.register(Settings.i("HitDelay", 37));
        this.Mode = this.register(Settings.e("Mode", mode.SINGLE));
        this.prio = this.register(Settings.e("Priority", priorityMode.DISTANCE));
        this.range = this.register(Settings.d("Range", 4.5));
        this.swordOnly = this.register(Settings.b("SwordOnly", false));
        this.rotate = this.register(Settings.b("Rotate", true));
        this.caCheck = this.register(Settings.b("CACheck", false));
        this.player = this.register(Settings.b("Player", true));
        this.mob = this.register(Settings.b("Mobs", false));
        this.animal = this.register(Settings.b("Animal", false));
        this.betterAnimations = this.register(Settings.b("BetterAnimations", true));
        this.attack = false;
        this.isAttacking = false;
        this.hitDelays = this.hitDelay.getValue();
        this.sendListener = new Listener<PacketEvent.Send>(event -> {
            if (event.getPacket() instanceof CPacketUseEntity && ((CPacketUseEntity)event.getPacket()).func_149565_c() == CPacketUseEntity.Action.ATTACK) {
                if (this.isAttacking) {
                    this.lastentity = ((CPacketUseEntity)event.getPacket()).func_149564_a((World)KillAura.mc.field_71441_e);
                }
                if (((CPacketUseEntity)event.getPacket()).func_149564_a((World)KillAura.mc.field_71441_e) instanceof EntityEnderCrystal) {
                    this.lastentity = null;
                }
            }
            return;
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
        final Packet packet;
        this.packetListener = new Listener<PacketEvent.Receive>(event -> {
            packet = event.getPacket();
            if (packet instanceof CPacketPlayer && KillAura.isSpoofingAngles) {
                ((CPacketPlayer)packet).field_149476_e = (float)KillAura.yaw;
                ((CPacketPlayer)packet).field_149473_f = (float)KillAura.pitch;
            }
            return;
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
        KillAura.INSTANCE = this;
    }
    
    @Override
    public void onUpdate() {
        if (ModuleManager.getModuleByName("AutoCrystal").isEnabled() && ((AutoCrystal)ModuleManager.getModuleByName("AutoCrystal")).rotated) {
            if (KillAura.isAiming) {
                KillAura.isAiming = false;
                this.lastentity = null;
                resetRotation();
            }
            return;
        }
        this.target = null;
        if (KillAura.mc.field_71439_g == null || KillAura.mc.field_71439_g.field_70128_L) {
            return;
        }
        if (this.swordOnly.getValue() && !(KillAura.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword)) {
            if (KillAura.isAiming) {
                KillAura.isAiming = false;
                this.lastentity = null;
                resetRotation();
            }
            return;
        }
        if (this.caCheck.getValue() && ModuleManager.isModuleEnabled("AutoCrystal")) {
            return;
        }
        if (this.betterAnimations.getValue()) {
            KillAura.mc.field_71460_t.field_78516_c.field_187469_f = 1.0f;
            KillAura.mc.field_71460_t.field_78516_c.field_187470_g = 1.0f;
            KillAura.mc.field_71460_t.field_78516_c.field_187467_d = KillAura.mc.field_71439_g.func_184614_ca();
        }
        final List<Entity> targets = (List<Entity>)KillAura.mc.field_71441_e.field_72996_f.stream().filter(entity -> entity != KillAura.mc.field_71439_g).filter(entity -> KillAura.mc.field_71439_g.func_70032_d(entity) <= this.range.getValue()).filter(entity -> !entity.field_70128_L).filter(entity -> entity instanceof EntityPlayer && this.player.getValue()).filter(entity -> entity.func_110143_aJ() > 0.0f).filter(entity -> !Friends.isFriend(entity.func_70005_c_())).sorted(Comparator.comparing(e -> KillAura.mc.field_71439_g.func_70032_d(e))).collect(Collectors.toList());
        final boolean b;
        targets.addAll((Collection<? extends Entity>)KillAura.mc.field_71441_e.field_72996_f.stream().filter(entity -> {
            if (EntityUtil.isLiving(entity) && !(entity instanceof EntityPlayer)) {
                if (EntityUtil.isPassive(entity) ? this.animal.getValue() : this.mob.getValue()) {
                    return b;
                }
            }
            return b;
        }).filter(entity -> entity.func_110143_aJ() > 0.0f).filter(entity -> !entity.field_70128_L).filter(entity -> KillAura.mc.field_71439_g.func_70032_d(entity) <= this.range.getValue()).sorted(Comparator.comparing(e -> KillAura.mc.field_71439_g.func_70032_d(e))).collect(Collectors.toList()));
        this.attack = false;
        this.target = this.findBestTarget(targets);
        if (this.target != null) {
            this.attack(this.target);
        }
        ++this.hitDelays;
        if (!this.attack) {
            for (final Entity e2 : targets) {
                if (this.lastentity != null && e2.func_145782_y() == this.lastentity.func_145782_y()) {
                    KillAura.isAiming = true;
                    this.target = e2;
                    lookAtPacket(e2.field_70165_t, e2.field_70163_u + e2.func_70047_e(), e2.field_70161_v, (EntityPlayer)KillAura.mc.field_71439_g);
                    return;
                }
            }
            if (KillAura.isAiming) {
                this.lastentity = null;
                KillAura.isAiming = false;
                resetRotation();
            }
        }
        else {
            this.hitDelays = 0;
        }
    }
    
    public Entity findBestTarget(final List<Entity> playerList) {
        if (!this.Mode.getValue().equals(mode.SINGLE) || this.lastentity == null) {
            return playerList.stream().sorted(Comparator.comparing(e -> this.prio.getValue().equals(priorityMode.HEALTH) ? (((EntityLivingBase)e).func_110143_aJ() + ((EntityLivingBase)e).func_110139_bj()) : KillAura.mc.field_71439_g.func_70032_d(e))).findFirst().orElse(null);
        }
        final Entity target1 = playerList.stream().filter(e -> e.func_145782_y() == this.lastentity.func_145782_y()).findFirst().orElse(null);
        if (target1 == null) {
            this.lastentity = null;
            if (KillAura.isAiming) {
                KillAura.isAiming = false;
                resetRotation();
            }
            return playerList.stream().sorted(Comparator.comparing(e -> this.prio.getValue().equals(priorityMode.HEALTH) ? (((EntityLivingBase)e).func_110143_aJ() + ((EntityLivingBase)e).func_110139_bj()) : KillAura.mc.field_71439_g.func_70032_d(e))).findFirst().orElse(null);
        }
        return target1;
    }
    
    public static void lookAtPacket(final double px, final double py, final double pz, final EntityPlayer me) {
        final double[] v = EntityUtil.calculateLookAt(px, py, pz, me);
        if (KillAura.INSTANCE.rotate.getValue()) {
            if (ModuleManager.getModuleByName("Aimbot").isDisabled()) {
                ModuleManager.getModuleByName("Aimbot").enable();
            }
            ((Aimbot)ModuleManager.getModuleByName("Aimbot")).setRotation((float)v[0], (float)v[1]);
        }
    }
    
    public void onEnable() {
        this.lastentity = null;
    }
    
    public void onDisable() {
        resetRotation();
        KillAura.isAiming = false;
    }
    
    public static void resetRotation() {
        if (KillAura.INSTANCE.rotate.getValue()) {
            ((Aimbot)ModuleManager.getModuleByName("Aimbot")).resetRotation();
        }
    }
    
    public void attack(final Entity e) {
        if (KillAura.mc.field_71439_g.func_184825_o(0.0f) >= 1.0f) {
            this.isAttacking = true;
            lookAtPacket(e.field_70165_t, e.field_70163_u + e.func_70047_e(), e.field_70161_v, (EntityPlayer)KillAura.mc.field_71439_g);
            KillAura.isAiming = true;
            KillAura.mc.field_71442_b.func_78764_a((EntityPlayer)KillAura.mc.field_71439_g, e);
            KillAura.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
            this.isAttacking = false;
            this.attack = true;
            if (!ModuleManager.getModuleByName("AutoCrystal").isDisabled()) {
                final AutoCrystal autoCrystal = (AutoCrystal)ModuleManager.getModuleByName("AutoCrystal");
                AutoCrystal.renderEnt = e;
            }
            return;
        }
        if (this.hitDelays >= this.hitDelay.getValue() && this.ghostSwitchSword.getValue()) {
            int swordSlot = (KillAura.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151048_u) ? KillAura.mc.field_71439_g.field_71071_by.field_70461_c : -1;
            if (swordSlot == -1) {
                for (int l = 0; l < 9; ++l) {
                    if (KillAura.mc.field_71439_g.field_71071_by.func_70301_a(l).func_77973_b() == Items.field_151048_u) {
                        swordSlot = l;
                        break;
                    }
                }
            }
            this.isAttacking = true;
            lookAtPacket(e.field_70165_t, e.field_70163_u + e.func_70047_e(), e.field_70161_v, (EntityPlayer)KillAura.mc.field_71439_g);
            KillAura.isAiming = true;
            boolean switched = false;
            if (KillAura.mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_151048_u) {
                switched = true;
                KillAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(swordSlot));
            }
            KillAura.mc.field_71442_b.func_78764_a((EntityPlayer)KillAura.mc.field_71439_g, e);
            KillAura.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
            if (switched) {
                KillAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(KillAura.mc.field_71439_g.field_71071_by.field_70461_c));
            }
            this.isAttacking = false;
            this.attack = true;
            if (!ModuleManager.getModuleByName("AutoCrystal").isDisabled()) {
                final AutoCrystal autoCrystal2 = (AutoCrystal)ModuleManager.getModuleByName("AutoCrystal");
                AutoCrystal.renderEnt = e;
            }
        }
    }
    
    @Override
    public String getHudInfo() {
        return this.Mode.getValue().equals(mode.SINGLE) ? "Single" : "Switch";
    }
    
    static {
        KillAura.isAiming = false;
    }
    
    public enum mode
    {
        SINGLE, 
        SWITCH;
    }
    
    public enum priorityMode
    {
        HEALTH, 
        DISTANCE;
    }
}
