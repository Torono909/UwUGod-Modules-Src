// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.movement;

import java.util.function.Predicate;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import meow.candycat.uwu.event.UwUGodEvent;
import meow.candycat.uwu.setting.Settings;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import meow.candycat.uwu.event.events.EntityEvent;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "Velocity", description = "Modify knockback impact", category = Category.MOVEMENT)
public class Velocity extends Module
{
    private Setting<Float> horizontal;
    private Setting<Float> vertical;
    @EventHandler
    private Listener<PacketEvent.Receive> packetEventListener;
    @EventHandler
    private Listener<EntityEvent.EntityCollision> entityCollisionListener;
    @EventHandler
    private Listener<PlayerSPPushOutOfBlocksEvent> pushListener;
    
    public Velocity() {
        this.horizontal = this.register(Settings.f("Horizontal", 0.0f));
        this.vertical = this.register(Settings.f("Vertical", 0.0f));
        SPacketEntityVelocity velocity;
        final SPacketEntityVelocity sPacketEntityVelocity;
        final SPacketEntityVelocity sPacketEntityVelocity2;
        final SPacketEntityVelocity sPacketEntityVelocity3;
        SPacketExplosion velocity2;
        final SPacketExplosion sPacketExplosion;
        final SPacketExplosion sPacketExplosion2;
        final SPacketExplosion sPacketExplosion3;
        this.packetEventListener = new Listener<PacketEvent.Receive>(event -> {
            if (event.getEra() == UwUGodEvent.Era.PRE) {
                if (event.getPacket() instanceof SPacketEntityVelocity) {
                    velocity = (SPacketEntityVelocity)event.getPacket();
                    if (velocity.func_149412_c() == Velocity.mc.field_71439_g.field_145783_c) {
                        LongJump.lastVelocityTime = System.currentTimeMillis();
                        if (this.horizontal.getValue() == 0.0f && this.vertical.getValue() == 0.0f) {
                            event.cancel();
                        }
                        sPacketEntityVelocity.field_149415_b *= (int)(this.horizontal.getValue() / 100.0f);
                        sPacketEntityVelocity2.field_149416_c *= (int)(this.vertical.getValue() / 100.0f);
                        sPacketEntityVelocity3.field_149414_d *= (int)(this.horizontal.getValue() / 100.0f);
                    }
                }
                else if (event.getPacket() instanceof SPacketExplosion) {
                    velocity2 = (SPacketExplosion)event.getPacket();
                    if (velocity2.field_149152_f != 0.0f || velocity2.field_149159_h != 0.0f) {
                        LongJump.lastVelocityTime = System.currentTimeMillis();
                    }
                    if (this.horizontal.getValue() == 0.0f && this.vertical.getValue() == 0.0f) {
                        event.cancel();
                    }
                    sPacketExplosion.field_149152_f *= this.horizontal.getValue() / 100.0f;
                    sPacketExplosion2.field_149153_g *= this.vertical.getValue() / 100.0f;
                    sPacketExplosion3.field_149159_h *= this.horizontal.getValue() / 100.0f;
                }
                else if (event.getPacket() instanceof SPacketPlayerPosLook) {
                    LongJump.lastVelocityTime = -1L;
                }
            }
            return;
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
        this.entityCollisionListener = new Listener<EntityEvent.EntityCollision>(event -> {
            if (event.getEntity() == Velocity.mc.field_71439_g) {
                if (this.horizontal.getValue() == 0.0f && this.vertical.getValue() == 0.0f) {
                    event.cancel();
                }
                else {
                    event.setX(-event.getX() * this.horizontal.getValue() / 100.0);
                    event.setY(0.0);
                    event.setZ(-event.getZ() * this.horizontal.getValue() / 100.0);
                }
            }
            return;
        }, (Predicate<EntityEvent.EntityCollision>[])new Predicate[0]);
        this.pushListener = new Listener<PlayerSPPushOutOfBlocksEvent>(event -> event.setCanceled(true), (Predicate<PlayerSPPushOutOfBlocksEvent>[])new Predicate[0]);
    }
    
    @Override
    public String getHudInfo() {
        return "H:" + this.horizontal.getValue() + "%, V:" + this.vertical.getValue() + "%";
    }
}
