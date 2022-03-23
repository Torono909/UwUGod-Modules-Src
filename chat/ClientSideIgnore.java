// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.chat;

import java.util.regex.Matcher;
import java.util.Iterator;
import java.util.function.Predicate;
import meow.candycat.uwu.util.Ignores;
import java.util.ArrayList;
import net.minecraft.network.play.server.SPacketChat;
import meow.candycat.uwu.setting.builder.SettingBuilder;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import java.util.regex.Pattern;
import meow.candycat.uwu.module.Module;

@Info(name = "ClientSideIgnore", category = Category.CHAT)
public class ClientSideIgnore extends Module
{
    private final Pattern CHAT_PATTERN;
    public Setting<ignoremethod> typeset;
    @EventHandler
    private Listener<PacketEvent.Receive> receiveListener;
    
    public ClientSideIgnore() {
        this.CHAT_PATTERN = Pattern.compile("<.*?> ");
        this.typeset = this.register((SettingBuilder<ignoremethod>)Settings.enumBuilder(ignoremethod.class).withName("Type").withValue(ignoremethod.BLOCKALL));
        String s;
        final Iterator<Ignores.ignore> iterator;
        Ignores.ignore ignored;
        String s2;
        Matcher matcher;
        String username;
        String username2;
        String username3;
        this.receiveListener = new Listener<PacketEvent.Receive>(event -> {
            if (event.getPacket() instanceof SPacketChat) {
                if (this.typeset.getValue().equals(ignoremethod.BLOCKALL)) {
                    s = decode(((SPacketChat)event.getPacket()).func_148915_c().func_150260_c());
                    Ignores.Ignore.getValue().iterator();
                    while (iterator.hasNext()) {
                        ignored = iterator.next();
                        if (s.contains(ignored.getUsername())) {
                            event.cancel();
                        }
                    }
                }
                else {
                    s2 = ((SPacketChat)event.getPacket()).func_148915_c().func_150260_c();
                    matcher = this.CHAT_PATTERN.matcher(s2);
                    username = "unnamed";
                    if (matcher.find()) {
                        username2 = matcher.group();
                        username3 = username2.substring(1, username2.length() - 2);
                        if (Ignores.isIgnored(username3)) {
                            event.cancel();
                        }
                    }
                }
            }
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
    }
    
    static final String decode(final String in) {
        String working = in;
        for (int index = working.indexOf("\\u"); index > -1; index = working.indexOf("\\u")) {
            final int length = working.length();
            if (index > length - 6) {
                break;
            }
            final int numStart = index + 2;
            final int numFinish = numStart + 4;
            final String substring = working.substring(numStart, numFinish);
            final int number = Integer.parseInt(substring, 16);
            final String stringStart = working.substring(0, index);
            final String stringEnd = working.substring(numFinish);
            working = stringStart + (char)number + stringEnd;
        }
        return working;
    }
    
    public enum ignoremethod
    {
        BLOCKALL, 
        ONLYPlAYERSENT;
    }
}
