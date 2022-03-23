// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import meow.candycat.uwu.module.ModuleManager;
import java.util.Iterator;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import meow.candycat.uwu.util.Friends;
import java.util.List;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import java.util.function.Predicate;
import meow.candycat.uwu.setting.Settings;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import meow.candycat.eventsystem.listener.EventHandler;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "AutoLog", description = "Automatically log when in danger or on low health", category = Category.COMBAT)
public class AutoLog extends Module
{
    private Setting<Integer> health;
    private boolean shouldLog;
    private Setting<Boolean> kdetectlog;
    public Setting<Double> range;
    long lastLog;
    @EventHandler
    private Listener<LivingDamageEvent> livingDamageEventListener;
    @EventHandler
    private Listener<EntityJoinWorldEvent> entityJoinWorldEventListener;
    
    public AutoLog() {
        this.health = this.register((Setting<Integer>)Settings.integerBuilder("Health").withRange(0, 36).withValue(6).build());
        this.shouldLog = false;
        this.kdetectlog = this.register(Settings.b("32kLog"));
        this.range = this.register(Settings.doubleBuilder("DetectRange").withMinimum(1.0).withValue(5.0).withMaximum(7.0).withVisibility(v -> this.kdetectlog.getValue()).build());
        this.lastLog = System.currentTimeMillis();
        this.livingDamageEventListener = new Listener<LivingDamageEvent>(event -> {
            if (AutoLog.mc.field_71439_g == null) {
                return;
            }
            else {
                if (event.getEntity() == AutoLog.mc.field_71439_g && AutoLog.mc.field_71439_g.func_110143_aJ() - event.getAmount() < this.health.getValue()) {
                    this.log();
                }
                return;
            }
        }, (Predicate<LivingDamageEvent>[])new Predicate[0]);
        this.entityJoinWorldEventListener = new Listener<EntityJoinWorldEvent>(event -> {
            if (AutoLog.mc.field_71439_g != null) {
                if (event.getEntity() instanceof EntityEnderCrystal && AutoLog.mc.field_71439_g.func_110143_aJ() - CrystalAura.calculateDamage((EntityEnderCrystal)event.getEntity(), (Entity)AutoLog.mc.field_71439_g) < this.health.getValue()) {
                    this.log();
                }
            }
        }, (Predicate<EntityJoinWorldEvent>[])new Predicate[0]);
    }
    
    private boolean is32k(final ItemStack stack) {
        if (stack.func_77973_b() instanceof ItemSword) {
            final NBTTagList enchants = stack.func_77986_q();
            if (enchants != null) {
                for (int i = 0; i < enchants.func_74745_c(); ++i) {
                    if (enchants.func_150305_b(i).func_74765_d("lvl") >= 32767) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public void onUpdate() {
        if (!this.shouldLog) {
            final List<EntityPlayer> w = (List<EntityPlayer>)AutoLog.mc.field_71441_e.field_73010_i.stream().filter(e -> !Friends.isFriend(e.func_70005_c_())).collect(Collectors.toList());
            if (this.kdetectlog.getValue()) {
                for (final EntityPlayer player : w) {
                    if (AutoLog.mc.field_71439_g != null) {
                        if (AutoLog.mc.field_71439_g == player) {
                            continue;
                        }
                        if (this.is32k(player.field_184831_bT) && player.func_70032_d((Entity)AutoLog.mc.field_71439_g) < this.range.getValue()) {
                            this.shouldLog = true;
                            break;
                        }
                        continue;
                    }
                }
            }
        }
        if (this.shouldLog) {
            this.shouldLog = false;
            if (System.currentTimeMillis() - this.lastLog < 2000L) {
                return;
            }
            Minecraft.func_71410_x().func_147114_u().func_147253_a(new SPacketDisconnect((ITextComponent)new TextComponentString("AutoLogged")));
        }
    }
    
    private void log() {
        ModuleManager.getModuleByName("AutoReconnect").disable();
        this.shouldLog = true;
        this.lastLog = System.currentTimeMillis();
    }
}
