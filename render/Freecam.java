// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.render;

import meow.candycat.uwu.util.CameraEntity;
import net.minecraft.world.World;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketRespawn;
import java.util.function.Predicate;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.uwu.event.events.EventSetOpaqueCube;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.EventPlayerMove;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "Freecam", category = Category.RENDER, description = "Leave your body and trascend into the realm of the gods")
public class Freecam extends Module
{
    public Setting<Double> speed;
    double vertical;
    double strafe;
    double forward;
    boolean shouldCancel;
    @EventHandler
    private Listener<EventPlayerMove> x;
    @EventHandler
    private Listener<EventSetOpaqueCube> OnEventSetOpaqueCube;
    @EventHandler
    private Listener<PacketEvent.Receive> onServerPacket;
    @EventHandler
    private Listener<PacketEvent.Send> onClientPacket;
    
    public Freecam() {
        this.speed = this.register(Settings.d("Speed", 1.0));
        this.shouldCancel = true;
        final double x;
        this.x = new Listener<EventPlayerMove>(event -> {
            if (this.shouldCancel) {
                event.Z = x;
                event.Y = x;
                event.X = x;
                event.cancel();
            }
            return;
        }, (Predicate<EventPlayerMove>[])new Predicate[0]);
        this.OnEventSetOpaqueCube = new Listener<EventSetOpaqueCube>(p_Event -> p_Event.cancel(), (Predicate<EventSetOpaqueCube>[])new Predicate[0]);
        this.onServerPacket = new Listener<PacketEvent.Receive>(event -> {
            if (event.getPacket() instanceof SPacketRespawn) {
                this.toggle();
            }
            return;
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
        CPacketUseEntity packet;
        this.onClientPacket = new Listener<PacketEvent.Send>(event -> {
            if (event.getPacket() instanceof CPacketUseEntity) {
                packet = (CPacketUseEntity)event.getPacket();
                if (packet.func_149564_a((World)Freecam.mc.field_71441_e) == Freecam.mc.field_71439_g) {
                    event.cancel();
                }
            }
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
    }
    
    public void onEnable() {
        if (Freecam.mc.field_71441_e == null) {
            return;
        }
        CameraEntity.setCameraState(true);
    }
    
    public void onDisable() {
        if (Freecam.mc.field_71441_e == null) {
            return;
        }
        CameraEntity.setCameraState(false);
    }
    
    @Override
    public void onUpdate() {
        this.shouldCancel = false;
        CameraEntity.movementTick(Freecam.mc.field_71439_g.field_71158_b.field_192832_b, Freecam.mc.field_71439_g.field_71158_b.field_78901_c ? (Freecam.mc.field_71439_g.field_71158_b.field_78899_d ? 0.0 : 1.0) : (Freecam.mc.field_71439_g.field_71158_b.field_78899_d ? -1.0 : 0.0), Freecam.mc.field_71439_g.field_71158_b.field_78902_a);
        this.shouldCancel = true;
    }
}
