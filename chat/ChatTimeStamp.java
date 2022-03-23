// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.chat;

import net.minecraft.util.text.ITextComponent;
import java.util.function.Predicate;
import net.minecraft.util.text.TextComponentString;
import java.util.Date;
import net.minecraft.network.play.server.SPacketChat;
import java.text.SimpleDateFormat;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import java.text.DateFormat;
import meow.candycat.uwu.module.Module;

@Info(name = "ChatTimeStamp", category = Category.CHAT)
public class ChatTimeStamp extends Module
{
    DateFormat df;
    @EventHandler
    private Listener<PacketEvent.Receive> receiveListener;
    
    public ChatTimeStamp() {
        this.df = new SimpleDateFormat("HH:mm:ss");
        Date dateobj;
        SPacketChat sPacketChat;
        final ITextComponent field_148919_a;
        this.receiveListener = new Listener<PacketEvent.Receive>(event -> {
            if (event.getPacket() instanceof SPacketChat) {
                dateobj = new Date();
                sPacketChat = (SPacketChat)event.getPacket();
                new TextComponentString("§7<" + this.df.format(dateobj) + "> §f" + ((SPacketChat)event.getPacket()).func_148915_c().func_150254_d());
                sPacketChat.field_148919_a = field_148919_a;
            }
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
    }
}
