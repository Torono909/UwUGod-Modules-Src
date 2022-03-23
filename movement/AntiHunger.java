// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.movement;

import java.util.function.Predicate;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.client.Minecraft;
import meow.candycat.uwu.module.ModuleManager;
import meow.candycat.uwu.module.modules.combat.KillAura;
import net.minecraft.network.play.client.CPacketPlayer;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.module.Module;

@Info(name = "AntiHunger", category = Category.MOVEMENT, description = "Never lose hunger except for regen")
public class AntiHunger extends Module
{
    @EventHandler
    public Listener<PacketEvent.Send> packetListener;
    
    public AntiHunger() {
        CPacketPlayer packet;
        CPacketEntityAction packet2;
        this.packetListener = new Listener<PacketEvent.Send>(event -> {
            if (event.getPacket() instanceof CPacketPlayer) {
                packet = (CPacketPlayer)event.getPacket();
                if (((KillAura)ModuleManager.getModuleByName("KillAura")).isAttacking) {
                    return;
                }
                else if (Minecraft.func_71410_x().field_71439_g.field_70143_R > 0.0f || Minecraft.func_71410_x().field_71442_b.field_78778_j) {
                    packet.field_149474_g = true;
                }
                else {
                    packet.field_149474_g = false;
                }
            }
            if (event.getPacket() instanceof CPacketEntityAction) {
                packet2 = (CPacketEntityAction)event.getPacket();
                if (packet2.func_180764_b() == CPacketEntityAction.Action.START_SPRINTING || packet2.func_180764_b() == CPacketEntityAction.Action.STOP_SPRINTING) {
                    event.cancel();
                }
            }
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
    }
}
