// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.player;

import java.util.function.Predicate;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.module.Module;

@Info(name = "PortalGodMode", category = Category.PLAYER, description = "Don't take damage in portals")
public class PortalGodMode extends Module
{
    @EventHandler
    public Listener<PacketEvent.Send> listener;
    
    public PortalGodMode() {
        this.listener = new Listener<PacketEvent.Send>(event -> {
            if (this.isEnabled() && event.getPacket() instanceof CPacketConfirmTeleport) {
                event.cancel();
            }
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
    }
}
