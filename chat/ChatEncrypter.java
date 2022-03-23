// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.chat;

import net.minecraft.util.text.ITextComponent;
import meow.candycat.uwu.command.Command;
import java.util.function.Function;
import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.nio.CharBuffer;
import java.util.List;
import java.util.Random;
import java.util.Map;
import java.util.regex.Matcher;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.network.play.server.SPacketChat;
import java.util.function.Predicate;
import meow.candycat.uwu.util.MessageSendHelper;
import net.minecraft.network.play.client.CPacketChatMessage;
import meow.candycat.uwu.module.ModuleManager;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import java.util.regex.Pattern;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "ChatEncryption", description = "Encrypts and decrypts chat messages (Delimiter %)", category = Category.CHAT, showOnArray = ShowOnArray.OFF)
public class ChatEncrypter extends Module
{
    private Setting<Boolean> enc;
    private Setting<Boolean> dec;
    private Setting<Boolean> delim;
    private final Pattern CHAT_PATTERN;
    private final Pattern CHAT_PATTERN2;
    private static final char[] ORIGIN_CHARS;
    @EventHandler
    private Listener<PacketEvent.Send> sendListener;
    @EventHandler
    private Listener<PacketEvent.Receive> receiveListener;
    
    public ChatEncrypter() {
        this.enc = this.register(Settings.b("Encrypt", true));
        this.dec = this.register(Settings.b("Decrypt", true));
        this.delim = this.register(Settings.b("Delimiter", true));
        this.CHAT_PATTERN = Pattern.compile("<.*?> ");
        this.CHAT_PATTERN2 = Pattern.compile("(.*?)");
        String s;
        StringBuilder builder;
        int x;
        String s2;
        String s3;
        this.sendListener = new Listener<PacketEvent.Send>(event -> {
            if (this.enc.getValue() && !ModuleManager.getModuleByName("ChatEncryption").isDisabled() && event.getPacket() instanceof CPacketChatMessage) {
                s = ((CPacketChatMessage)event.getPacket()).func_149439_c();
                if (this.delim.getValue()) {
                    if (!s.startsWith("!")) {
                        return;
                    }
                    else {
                        s = s.substring(1);
                    }
                }
                if (!s.startsWith("/")) {
                    builder = new StringBuilder();
                    x = (int)Math.ceil(Math.random() * 20.0);
                    s2 = s + "uw2a";
                    builder.append(this.shuffle(x, s2));
                    builder.append("\ud83d\ude4d");
                    s3 = builder.toString();
                    if (s3.length() > 256) {
                        MessageSendHelper.sendChatMessage("Encrypted message length was too long, couldn't send!");
                        event.cancel();
                    }
                    else {
                        ((CPacketChatMessage)event.getPacket()).field_149440_a = s3;
                    }
                }
            }
            return;
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
        String s4;
        Matcher matcher2;
        Matcher matcher3;
        String username;
        String username2;
        StringBuilder builder2;
        String s5;
        boolean iwi;
        int i;
        String s6;
        SPacketChat sPacketChat;
        final TextComponentString field_148919_a;
        this.receiveListener = new Listener<PacketEvent.Receive>(event -> {
            if (this.dec.getValue() && !ModuleManager.getModuleByName("ChatEncryption").isDisabled() && event.getPacket() instanceof SPacketChat) {
                s4 = ((SPacketChat)event.getPacket()).func_148915_c().func_150260_c();
                matcher2 = this.CHAT_PATTERN2.matcher(s4);
                if (matcher2.find()) {
                    matcher2.group();
                    s4 = matcher2.replaceFirst("");
                }
                matcher3 = this.CHAT_PATTERN.matcher(s4);
                username = "unnamed";
                if (matcher3.find()) {
                    username2 = matcher3.group();
                    username = username2.substring(1, username2.length() - 2);
                    s4 = matcher3.replaceFirst("");
                }
                builder2 = new StringBuilder();
                if (!(!s4.endsWith("\ud83d\ude4d"))) {
                    s5 = s4.substring(0, s4.length() - 2);
                    iwi = false;
                    i = 1;
                    while (i <= 20) {
                        builder2.delete(0, builder2.length());
                        builder2.append(this.unshuffle(i, s5));
                        if (builder2.toString().endsWith("uw2a")) {
                            iwi = true;
                            break;
                        }
                        else {
                            ++i;
                        }
                    }
                    if (iwi) {
                        s6 = builder2.toString().substring(0, builder2.toString().length() - 4);
                        sPacketChat = (SPacketChat)event.getPacket();
                        new TextComponentString("§b" + username + '§' + "r: " + s6);
                        sPacketChat.field_148919_a = (ITextComponent)field_148919_a;
                    }
                }
            }
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
    }
    
    private Map<Character, Character> generateShuffleMap(final int seed) {
        final Random r = new Random(seed);
        final List<Character> characters = CharBuffer.wrap(ChatEncrypter.ORIGIN_CHARS).chars().mapToObj(value -> Character.valueOf((char)value)).collect((Collector<? super Object, ?, List<Character>>)Collectors.toList());
        final List<Character> counter = new ArrayList<Character>(characters);
        Collections.shuffle(counter, r);
        final Map<Character, Character> map = new LinkedHashMap<Character, Character>();
        for (int i = 0; i < characters.size(); ++i) {
            map.put(characters.get(i), counter.get(i));
        }
        return map;
    }
    
    private String shuffle(final int seed, final String input) {
        final Map<Character, Character> s = this.generateShuffleMap(seed);
        final StringBuilder builder = new StringBuilder();
        this.swapCharacters(input, s, builder);
        return builder.toString();
    }
    
    private String unshuffle(final int seed, final String input) {
        final Map<Character, Character> s = this.generateShuffleMap(seed);
        final StringBuilder builder = new StringBuilder();
        this.swapCharacters(input, reverseMap(s), builder);
        return builder.toString();
    }
    
    private void swapCharacters(final String input, final Map<Character, Character> s, final StringBuilder builder) {
        final char c;
        CharBuffer.wrap(input.toCharArray()).chars().forEachOrdered(value -> {
            c = (char)value;
            if (s.containsKey(c)) {
                builder.append(s.get(c));
            }
            else {
                builder.append(c);
            }
        });
    }
    
    private static <K, V> Map<V, K> reverseMap(final Map<K, V> map) {
        return map.entrySet().stream().collect(Collectors.toMap((Function<? super Object, ? extends V>)Map.Entry::getValue, (Function<? super Object, ? extends K>)Map.Entry::getKey));
    }
    
    public void onEnable() {
        if (this.delim.getValue()) {
            Command.sendChatMessage("say ! before your message to encrypt ur message uwu");
        }
    }
    
    static {
        ORIGIN_CHARS = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '-', '_', '/', ';', '=', '?', '+', 'µ', '£', '*', '^', '\u00f9', '$', '!', '{', '}', '\'', '\"', '|', '&' };
    }
    
    private enum EncryptionMode
    {
        SHUFFLE, 
        SHIFT;
    }
}
