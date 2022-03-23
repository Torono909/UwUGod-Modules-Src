// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import java.util.function.Predicate;
import meow.candycat.uwu.util.Wrapper;
import net.minecraft.network.play.server.SPacketChat;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.module.Module;

@Info(name = "AutoReply", category = Category.MISC, description = "automatically replies to messages")
public class AutoReply extends Module
{
    @EventHandler
    public Listener<PacketEvent.Receive> receiveListener;
    
    public AutoReply() {
        this.receiveListener = new Listener<PacketEvent.Receive>(event -> {
            if (event.getPacket() instanceof SPacketChat && (((SPacketChat)event.getPacket()).func_148915_c().func_150260_c().contains("whispers:") || ((SPacketChat)event.getPacket()).func_148915_c().func_150260_c().contains("- > "))) {
                Wrapper.getPlayer().func_71165_d("/r I don't speak to Gloom fanboys.");
            }
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
    }
}
