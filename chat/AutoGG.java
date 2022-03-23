// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.chat;

import java.util.Objects;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import java.util.Iterator;
import java.util.function.Predicate;
import meow.candycat.uwu.util.EntityUtil;
import net.minecraft.world.World;
import meow.candycat.uwu.setting.Settings;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import meow.candycat.uwu.setting.Setting;
import java.util.concurrent.ConcurrentHashMap;
import meow.candycat.uwu.module.Module;

@Info(name = "AutoGG", category = Category.CHAT, description = "Announce killed Players")
public class AutoGG extends Module
{
    private ConcurrentHashMap<String, Integer> targetedPlayers;
    private Setting<String> owo;
    private Setting<String> kami;
    private Setting<Boolean> clientName;
    private Setting<Boolean> uwumode;
    private Setting<Integer> timeoutTicks;
    private EntityLivingBase entity;
    private EntityPlayer player;
    private String name;
    private CPacketUseEntity cPacketUseEntity;
    private Entity targetEntity;
    @EventHandler
    public Listener<PacketEvent.Send> sendListener;
    @EventHandler
    public Listener<LivingDeathEvent> livingDeathEventListener;
    
    public AutoGG() {
        this.uwumode = this.register(Settings.b("UwUMode"));
        this.targetedPlayers = null;
        this.owo = this.register(Settings.s("Wordsbeforename", "uwu don't be sad "));
        this.kami = this.register(Settings.s("Wordsaftername", ""));
        this.clientName = this.register(Settings.b("ClientName", true));
        this.timeoutTicks = this.register(Settings.i("TimeoutTicks", 20));
        this.sendListener = new Listener<PacketEvent.Send>(event -> {
            if (AutoGG.mc.field_71439_g == null) {
                return;
            }
            else {
                if (this.targetedPlayers == null) {
                    this.targetedPlayers = new ConcurrentHashMap<String, Integer>();
                }
                if (!(event.getPacket() instanceof CPacketUseEntity)) {
                    return;
                }
                else {
                    this.cPacketUseEntity = (CPacketUseEntity)event.getPacket();
                    if (!this.cPacketUseEntity.func_149565_c().equals((Object)CPacketUseEntity.Action.ATTACK)) {
                        return;
                    }
                    else {
                        this.targetEntity = this.cPacketUseEntity.func_149564_a((World)AutoGG.mc.field_71441_e);
                        if (!EntityUtil.isPlayer(this.targetEntity)) {
                            return;
                        }
                        else {
                            this.addTargetedPlayer(this.targetEntity.func_70005_c_());
                            return;
                        }
                    }
                }
            }
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
        this.livingDeathEventListener = new Listener<LivingDeathEvent>(event -> {
            if (AutoGG.mc.field_71439_g != null) {
                if (this.targetedPlayers == null) {
                    this.targetedPlayers = new ConcurrentHashMap<String, Integer>();
                }
                this.entity = event.getEntityLiving();
                if (this.entity != null && EntityUtil.isPlayer((Entity)this.entity)) {
                    this.player = (EntityPlayer)this.entity;
                    if (this.player.func_110143_aJ() <= 0.0f) {
                        this.name = this.player.func_70005_c_();
                        if (this.shouldAnnounce(this.name)) {
                            this.doAnnounce(this.name);
                        }
                    }
                }
            }
        }, (Predicate<LivingDeathEvent>[])new Predicate[0]);
    }
    
    public void onEnable() {
        this.targetedPlayers = new ConcurrentHashMap<String, Integer>();
    }
    
    public void onDisable() {
        this.targetedPlayers = null;
    }
    
    @Override
    public void onUpdate() {
        if (this.isDisabled() || AutoGG.mc.field_71439_g == null) {
            return;
        }
        if (this.targetedPlayers == null) {
            this.targetedPlayers = new ConcurrentHashMap<String, Integer>();
        }
        for (final Entity entity : AutoGG.mc.field_71441_e.func_72910_y()) {
            if (!EntityUtil.isPlayer(entity)) {
                continue;
            }
            final EntityPlayer player = (EntityPlayer)entity;
            if (player.func_110143_aJ() > 0.0f) {
                continue;
            }
            final String name2 = player.func_70005_c_();
            if (this.shouldAnnounce(name2)) {
                this.doAnnounce(name2);
                break;
            }
        }
        this.targetedPlayers.forEach((name, timeout) -> {
            if (timeout <= 0) {
                this.targetedPlayers.remove(name);
            }
            else {
                this.targetedPlayers.put(name, timeout - 1);
            }
        });
    }
    
    private boolean shouldAnnounce(final String name) {
        return this.targetedPlayers.containsKey(name);
    }
    
    private void doAnnounce(final String name) {
        this.targetedPlayers.remove(name);
        final StringBuilder message = new StringBuilder();
        if (!this.uwumode.getValue()) {
            message.append(this.owo.getValue());
            message.append(" ");
            message.append(name);
            message.append(this.kami.getValue());
        }
        else {
            message.append("uwu don't be sad ");
            message.append(name);
        }
        if (this.clientName.getValue()) {
            message.append(" ");
            message.append("UwUGod still highly hated but being skidded!");
        }
        String messageSanitized = message.toString().replaceAll("\u79ae", "");
        if (messageSanitized.length() > 255) {
            messageSanitized = messageSanitized.substring(0, 255);
        }
        AutoGG.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketChatMessage(messageSanitized));
    }
    
    public void addTargetedPlayer(final String name) {
        if (Objects.equals(name, AutoGG.mc.field_71439_g.func_70005_c_())) {
            return;
        }
        if (this.targetedPlayers == null) {
            this.targetedPlayers = new ConcurrentHashMap<String, Integer>();
        }
        this.targetedPlayers.put(name, this.timeoutTicks.getValue());
    }
}
