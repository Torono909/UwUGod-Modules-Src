// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.chat;

import java.util.function.Predicate;
import net.minecraft.network.play.client.CPacketChatMessage;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.module.Module;

@Info(name = "Green-Chat", category = Category.CHAT)
public class GreenChat extends Module
{
    @EventHandler
    public Listener<PacketEvent.Send> listener;
    
    public GreenChat() {
        String s;
        String s2;
        String greenChatMsg;
        this.listener = new Listener<PacketEvent.Send>(event -> {
            if (event.getPacket() instanceof CPacketChatMessage) {
                s = ((CPacketChatMessage)event.getPacket()).func_149439_c();
                if (!s.startsWith("/")) {
                    greenChatMsg = (s2 = ">" + s);
                    if (s2.length() >= 256) {
                        s2 = s2.substring(0, 256);
                    }
                    ((CPacketChatMessage)event.getPacket()).field_149440_a = s2;
                }
            }
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
    }
}
