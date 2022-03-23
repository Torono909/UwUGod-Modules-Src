// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import meow.candycat.uwu.command.Command;
import net.minecraft.client.Minecraft;
import java.util.function.Predicate;
import net.minecraft.network.Packet;
import meow.candycat.uwu.util.Wrapper;
import net.minecraft.network.play.client.CPacketChatMessage;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.module.Module;

@Info(name = "FormatChat", description = "Add color and linebreak support to upstream chat packets", category = Category.MISC)
public class FormatChat extends Module
{
    @EventHandler
    public Listener<PacketEvent.Send> sendListener;
    
    public FormatChat() {
        String message;
        String message2;
        String message3;
        this.sendListener = new Listener<PacketEvent.Send>(event -> {
            if (event.getPacket() instanceof CPacketChatMessage) {
                message = ((CPacketChatMessage)event.getPacket()).field_149440_a;
                if (message.contains("&") || message.contains("#n")) {
                    message2 = message.replaceAll("&", "§");
                    message3 = message2.replaceAll("#n", "\n");
                    Wrapper.getPlayer().field_71174_a.func_147297_a((Packet)new CPacketChatMessage(message3));
                    event.cancel();
                }
            }
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
    }
    
    public void onEnable() {
        if (Minecraft.func_71410_x().func_147104_D() == null) {
            Command.sendWarningMessage("[FormatChat] &6&lWarning: &r&6This does not work in singleplayer");
            this.disable();
        }
        else {
            Command.sendWarningMessage("[FormatChat] &6&lWarning: &r&6This will kick you on most servers!");
        }
    }
}
