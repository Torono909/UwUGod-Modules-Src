// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.chat;

import net.minecraft.util.text.ITextComponent;
import java.util.regex.Matcher;
import java.util.function.Predicate;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.network.play.server.SPacketChat;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import java.util.regex.Pattern;
import meow.candycat.uwu.module.Module;

@Info(name = "AntiEmoji", category = Category.CHAT)
public class AntiEmoji extends Module
{
    private Pattern pattern;
    private Pattern pattern2;
    private Pattern pattern3;
    private Pattern pattern4;
    private Pattern pattern5;
    private Pattern pattern6;
    private Pattern pattern7;
    private Pattern pattern8;
    private Setting<Boolean> antiowo;
    private Setting<Boolean> antiqwq;
    private Setting<Boolean> antiawa;
    @EventHandler
    private Listener<PacketEvent.Receive> receiveListener;
    
    public AntiEmoji() {
        this.pattern = Pattern.compile(Pattern.quote("awa"), 2);
        this.pattern2 = Pattern.compile(Pattern.quote("owo"), 2);
        this.pattern3 = Pattern.compile(Pattern.quote("qwq"), 2);
        this.pattern4 = Pattern.compile(Pattern.quote("qaq"), 2);
        this.pattern5 = Pattern.compile(Pattern.quote("qrq"), 2);
        this.pattern6 = Pattern.compile(Pattern.quote("quq"), 2);
        this.pattern7 = Pattern.compile(Pattern.quote("ouo"), 2);
        this.pattern8 = Pattern.compile(Pattern.quote("awwa"), 2);
        this.antiowo = this.register(Settings.b("AntiOwO"));
        this.antiqwq = this.register(Settings.b("AntiQwQ"));
        this.antiawa = this.register(Settings.b("AntiAwA"));
        String s;
        String olds;
        Matcher matcher;
        Matcher matcher2;
        Matcher matcher3;
        Matcher matcher4;
        Matcher matcher5;
        Matcher matcher6;
        Matcher matcher7;
        Matcher matcher8;
        this.receiveListener = new Listener<PacketEvent.Receive>(event -> {
            if (event.getPacket() instanceof SPacketChat) {
                s = ((SPacketChat)event.getPacket()).func_148915_c().func_150254_d();
                olds = ((SPacketChat)event.getPacket()).func_148915_c().func_150254_d();
                if (this.antiawa.getValue()) {
                    matcher = this.pattern.matcher(s);
                    if (matcher.find()) {
                        s = matcher.replaceAll("");
                    }
                    matcher2 = this.pattern8.matcher(s);
                    if (matcher2.find()) {
                        s = matcher2.replaceAll("");
                    }
                }
                if (this.antiowo.getValue()) {
                    matcher3 = this.pattern2.matcher(s);
                    if (matcher3.find()) {
                        s = matcher3.replaceAll("");
                    }
                    matcher4 = this.pattern7.matcher(s);
                    if (matcher4.find()) {
                        s = matcher4.replaceAll("");
                    }
                }
                if (this.antiqwq.getValue()) {
                    matcher5 = this.pattern3.matcher(s);
                    if (matcher5.find()) {
                        s = matcher5.replaceAll("");
                    }
                    matcher6 = this.pattern4.matcher(s);
                    if (matcher6.find()) {
                        s = matcher6.replaceAll("");
                    }
                    matcher7 = this.pattern5.matcher(s);
                    if (matcher7.find()) {
                        s = matcher7.replaceAll("");
                    }
                    matcher8 = this.pattern6.matcher(s);
                    if (matcher8.find()) {
                        s = matcher8.replaceAll("");
                    }
                }
                if (!s.equals(olds)) {
                    ((SPacketChat)event.getPacket()).field_148919_a = (ITextComponent)new TextComponentString(s);
                }
            }
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
    }
}
