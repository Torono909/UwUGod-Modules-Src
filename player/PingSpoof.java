// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.player;

import net.minecraft.client.Minecraft;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;
import java.util.Collection;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import meow.candycat.uwu.module.ModuleManager;
import java.util.function.Predicate;
import net.minecraft.network.play.client.CPacketKeepAlive;
import java.util.TimerTask;
import net.minecraft.network.play.server.SPacketKeepAlive;
import java.util.ArrayList;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import java.util.concurrent.ScheduledExecutorService;
import java.util.Timer;
import net.minecraft.network.Packet;
import java.util.List;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "PingSpoof", category = Category.PLAYER, description = "owo")
public class PingSpoof extends Module
{
    private Setting<Integer> pingSpoof;
    private Setting<Boolean> realPingSpoof;
    List<Packet> packetList;
    List<Packet> shouldSendPacketList;
    Timer timer;
    boolean firstRun;
    Runnable helloRunnable;
    ScheduledExecutorService executor;
    @EventHandler
    public Listener<PacketEvent.Receive> listener;
    @EventHandler
    public Listener<PacketEvent.Send> listener2;
    
    public PingSpoof() {
        this.pingSpoof = this.register(Settings.i("Ping", 100));
        this.realPingSpoof = this.register(Settings.b("RealPingSpoof", true));
        this.packetList = new ArrayList<Packet>();
        this.shouldSendPacketList = new ArrayList<Packet>();
        this.timer = new Timer();
        this.firstRun = true;
        this.listener = new Listener<PacketEvent.Receive>(event -> {
            if (event.getPacket() instanceof SPacketKeepAlive && !this.realPingSpoof.getValue()) {
                event.cancel();
                new Timer().schedule(new TimerTask() {
                    final /* synthetic */ PacketEvent.Receive val$event;
                    
                    @Override
                    public void run() {
                        PingSpoof.mc.func_147114_u().func_147297_a((Packet)new CPacketKeepAlive(((SPacketKeepAlive)this.val$event.getPacket()).func_149134_c()));
                    }
                }, this.pingSpoof.getValue());
            }
            return;
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
        int i;
        Packet packet;
        ArrayList<Packet> list;
        this.listener2 = new Listener<PacketEvent.Send>(event -> {
            if (PingSpoof.mc.field_71441_e != null && PingSpoof.mc.field_71439_g != null) {
                if (this.packetList.contains(event.getPacket())) {
                    i = 0;
                    while (i < this.packetList.size()) {
                        packet = this.packetList.get(i);
                        if (packet.equals(event.getPacket())) {
                            this.packetList.remove(i);
                            break;
                        }
                        else {
                            ++i;
                        }
                    }
                }
                else if (this.realPingSpoof.getValue() && ModuleManager.getModuleByName("Blink").isDisabled()) {
                    event.cancel();
                    if ((!(event.getPacket() instanceof CPacketPlayer) && !(event.getPacket() instanceof CPacketConfirmTeleport)) || this.pingSpoof.getValue() > 50) {
                        this.packetList.add(event.getPacket());
                        this.shouldSendPacketList.add(event.getPacket());
                        if (this.firstRun) {
                            this.executor.scheduleAtFixedRate(() -> {
                                if (this.shouldSendPacketList.size() == 0) {
                                    return;
                                }
                                else {
                                    list = new ArrayList<Packet>(this.shouldSendPacketList);
                                    this.shouldSendPacketList.clear();
                                    this.timer.schedule(new TimerTask() {
                                        final /* synthetic */ List val$list;
                                        
                                        @Override
                                        public void run() {
                                            for (final Packet x : this.val$list) {
                                                PingSpoof.mc.func_147114_u().func_147297_a(x);
                                            }
                                        }
                                    }, this.pingSpoof.getValue());
                                    return;
                                }
                            }, 0L, 5L, TimeUnit.MILLISECONDS);
                            this.firstRun = false;
                        }
                    }
                }
            }
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
    }
    
    @Override
    public String getHudInfo() {
        return String.valueOf(this.pingSpoof.getValue());
    }
    
    public void onDisable() {
        this.executor.shutdown();
    }
    
    public void onEnable() {
        this.packetList.clear();
        this.firstRun = true;
        this.executor = Executors.newScheduledThreadPool(1);
    }
}
