// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.chat;

import java.util.function.Predicate;
import net.minecraft.network.play.client.CPacketChatMessage;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "CandyCatSuffix", category = Category.CHAT, description = "uwu CandyCat is cute")
public class CandyCatSuffix extends Module
{
    private Setting<Boolean> commands;
    private final String KAMI_SUFFIX = " \u23d0 \u1d04\u1d00\u0274\u1d05\u028f\u1d04\u1d00\u1d1b";
    @EventHandler
    public Listener<PacketEvent.Send> listener;
    
    public CandyCatSuffix() {
        this.commands = this.register(Settings.b("Commands", false));
        String s;
        String s2;
        this.listener = new Listener<PacketEvent.Send>(event -> {
            if (event.getPacket() instanceof CPacketChatMessage) {
                s = ((CPacketChatMessage)event.getPacket()).func_149439_c();
                if (!s.startsWith("/") || this.commands.getValue()) {
                    s2 = s + " \u23d0 \u1d04\u1d00\u0274\u1d05\u028f\u1d04\u1d00\u1d1b";
                    if (s2.length() >= 256) {
                        s2 = s2.substring(0, 256);
                    }
                    ((CPacketChatMessage)event.getPacket()).field_149440_a = s2;
                }
            }
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
    }
}
