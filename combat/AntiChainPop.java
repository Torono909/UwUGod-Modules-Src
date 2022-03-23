// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import meow.candycat.uwu.MeowUwU;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketTabComplete;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketChatMessage;
import java.util.function.Predicate;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketKeepAlive;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.EventHandler;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.module.Module;

@Info(name = "AntiChainPop", category = Category.COMBAT)
public class AntiChainPop extends Module
{
    boolean shouldCancel;
    @EventHandler
    private Listener<LivingDamageEvent> listener;
    @EventHandler
    private Listener<PacketEvent.Send> listener2;
    
    public AntiChainPop() {
        this.shouldCancel = false;
        float hp;
        this.listener = new Listener<LivingDamageEvent>(event -> {
            if (AntiChainPop.mc.field_71439_g != null || event.getEntity() != null) {
                return;
            }
            else {
                hp = AntiChainPop.mc.field_71439_g.func_110143_aJ() + AntiChainPop.mc.field_71439_g.func_110139_bj();
                if (event.getEntity() == AntiChainPop.mc.field_71439_g && event.getAmount() >= hp) {
                    AntiChainPop.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketKeepAlive());
                    this.shouldCancel = true;
                }
                else {
                    this.shouldCancel = false;
                }
                return;
            }
        }, (Predicate<LivingDamageEvent>[])new Predicate[0]);
        final Packet packet;
        this.listener2 = new Listener<PacketEvent.Send>(event -> {
            packet = event.getPacket();
            if (!(packet instanceof CPacketChatMessage) && !(packet instanceof CPacketConfirmTeleport) && !(packet instanceof CPacketKeepAlive) && !(packet instanceof CPacketTabComplete) && !(packet instanceof CPacketClientStatus) && !(packet instanceof CPacketHeldItemChange)) {
                if (this.shouldCancel) {
                    event.cancel();
                    this.shouldCancel = false;
                }
            }
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
    }
    
    public void onEnable() {
        MeowUwU.EVENT_BUS.subscribe(this);
        this.shouldCancel = false;
    }
    
    public void onDisable() {
        MeowUwU.EVENT_BUS.unsubscribe(this);
        this.shouldCancel = false;
    }
}
