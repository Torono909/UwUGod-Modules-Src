// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.player;

import java.util.function.Predicate;
import net.minecraft.network.play.client.CPacketAnimation;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.module.Module;

@Info(name = "NoSwing", category = Category.PLAYER, description = "Cancels server and client swinging packets")
public class NoSwing extends Module
{
    @EventHandler
    private Listener<PacketEvent.Send> listener;
    
    public NoSwing() {
        this.listener = new Listener<PacketEvent.Send>(event -> {
            if (event.getPacket() instanceof CPacketAnimation) {
                event.cancel();
            }
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
    }
}
