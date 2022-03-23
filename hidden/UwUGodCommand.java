// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.hidden;

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
import java.util.function.Predicate;
import meow.candycat.uwu.util.MessageSendHelper;
import net.minecraft.network.play.client.CPacketChatMessage;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.module.Module;

@Info(name = "UwUGodCommand", category = Category.HIDDEN)
public class UwUGodCommand extends Module
{
    public static UwUGodCommand uwugodcommand;
    public static final char[] ORIGIN_CHARS;
    @EventHandler
    public Listener<PacketEvent.Send> sendListener;
    
    public UwUGodCommand() {
        String s;
        String s2;
        String s3;
        this.sendListener = new Listener<PacketEvent.Send>(event -> {
            if (event.getPacket() instanceof CPacketChatMessage) {
                s = ((CPacketChatMessage)event.getPacket()).func_149439_c();
                if (!(!s.startsWith("!"))) {
                    s2 = s.substring(1);
                    if (!s2.startsWith("/")) {
                        s3 = s2 + "Command";
                        if (s3.length() > 256) {
                            MessageSendHelper.sendChatMessage("Command message length was too long, couldn't send!");
                            event.cancel();
                        }
                        else {
                            ((CPacketChatMessage)event.getPacket()).field_149440_a = s3;
                        }
                    }
                }
            }
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
    }
    
    public static UwUGodCommand getUwUGodCommand() {
        return UwUGodCommand.uwugodcommand;
    }
    
    public String shuffle(final int seed, final String input) {
        final Map<Character, Character> s = this.generateShuffleMap(seed);
        final StringBuilder builder = new StringBuilder();
        this.swapCharacters(input, s, builder);
        return builder.toString();
    }
    
    public static final String decode(final String in) {
        String working = in;
        for (int index = working.indexOf("\\u005c"); index > -1; index = working.indexOf("\\u005c")) {
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
    
    public String unshuffle(final int seed, final String input) {
        final Map<Character, Character> s = this.generateShuffleMap(seed);
        final StringBuilder builder = new StringBuilder();
        this.swapCharacters(input, reverseMap(s), builder);
        return builder.toString();
    }
    
    public Map<Character, Character> generateShuffleMap(final int seed) {
        final Random r = new Random(seed);
        final List<Character> characters = CharBuffer.wrap(UwUGodCommand.ORIGIN_CHARS).chars().mapToObj(value -> Character.valueOf((char)value)).collect((Collector<? super Object, ?, List<Character>>)Collectors.toList());
        final List<Character> counter = new ArrayList<Character>(characters);
        Collections.shuffle(counter, r);
        final Map<Character, Character> map = new LinkedHashMap<Character, Character>();
        for (int i = 0; i < characters.size(); ++i) {
            map.put(characters.get(i), counter.get(i));
        }
        return map;
    }
    
    public void swapCharacters(final String input, final Map<Character, Character> s, final StringBuilder builder) {
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
    
    public static <K, V> Map<V, K> reverseMap(final Map<K, V> map) {
        return map.entrySet().stream().collect(Collectors.toMap((Function<? super Object, ? extends V>)Map.Entry::getValue, (Function<? super Object, ? extends K>)Map.Entry::getKey));
    }
    
    static {
        UwUGodCommand.uwugodcommand = new UwUGodCommand();
        ORIGIN_CHARS = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '-', '_', '/', ';', '=', '?', '+', 'µ', '£', '*', '^', '\u00f9', '$', '!', '{', '}', '\'', '\"', '|', '&' };
    }
}
