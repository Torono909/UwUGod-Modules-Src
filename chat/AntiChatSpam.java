// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.chat;

import java.util.Iterator;
import java.util.Scanner;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.nio.charset.StandardCharsets;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonElement;
import com.mojang.util.UUIDTypeAdapter;
import com.google.gson.JsonParser;
import java.util.Collection;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.Minecraft;
import java.util.TimerTask;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.function.Predicate;
import meow.candycat.uwu.util.MultiThreading;
import meow.candycat.uwu.command.Command;
import meow.candycat.uwu.util.ChatColor;
import meow.candycat.uwu.util.Notification;
import meow.candycat.uwu.util.Ignores;
import net.minecraft.network.play.server.SPacketChat;
import java.util.ArrayList;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import java.util.Map;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "AntiSpam", category = Category.CHAT)
public class AntiChatSpam extends Module
{
    private Setting<Boolean> greenText;
    private Setting<Boolean> discordLinks;
    private Setting<Boolean> webLinks;
    private Setting<Boolean> announcers;
    private Setting<Boolean> spammers;
    private Setting<Boolean> insulters;
    private Setting<Boolean> greeters;
    private Setting<Boolean> tradeChat;
    private Setting<Boolean> ips;
    private Setting<Boolean> ipsAgr;
    private Setting<Boolean> numberSuffix;
    private Setting<Boolean> duplicates;
    private Setting<Integer> duplicatesTimeout;
    private Setting<Boolean> filterOwn;
    private Setting<Boolean> showBlocked;
    private Setting<Boolean> autoIgnore;
    private Setting<Integer> ignoreDuration;
    private Setting<Integer> violations;
    private final Pattern CHAT_PATTERN;
    private ConcurrentHashMap<String, Long> messageHistory;
    public List<String> ignoredBySpamCheck;
    public Map<String, Integer> violate;
    long time;
    @EventHandler
    public Listener<PacketEvent.Receive> listener;
    
    public AntiChatSpam() {
        this.greenText = this.register(Settings.b("Green Text", false));
        this.discordLinks = this.register(Settings.b("Discord Links", true));
        this.webLinks = this.register(Settings.b("Web Links", false));
        this.announcers = this.register(Settings.b("Announcers", true));
        this.spammers = this.register(Settings.b("Spammers", true));
        this.insulters = this.register(Settings.b("Insulters", true));
        this.greeters = this.register(Settings.b("Greeters", true));
        this.tradeChat = this.register(Settings.b("Trade Chat", true));
        this.ips = this.register(Settings.b("Server Ips", true));
        this.ipsAgr = this.register(Settings.b("Ips Aggressive", false));
        this.numberSuffix = this.register(Settings.b("Number Suffix", true));
        this.duplicates = this.register(Settings.b("Duplicates", true));
        this.duplicatesTimeout = this.register((Setting<Integer>)Settings.integerBuilder("Duplicates Timeout").withMinimum(1).withValue(30).withMaximum(600).build());
        this.filterOwn = this.register(Settings.b("Filter Own", false));
        this.showBlocked = this.register(Settings.b("Show Blocked", false));
        this.autoIgnore = this.register(Settings.b("AutoIgnore"));
        this.ignoreDuration = this.register(Settings.integerBuilder("IgnoreDuration").withValue(120).withVisibility(v -> this.autoIgnore.getValue()).build());
        this.violations = this.register(Settings.integerBuilder("Violations").withValue(3).withVisibility(v -> this.autoIgnore.getValue()).build());
        this.CHAT_PATTERN = Pattern.compile("<.*?> ");
        this.ignoredBySpamCheck = new ArrayList<String>();
        this.violate = new ConcurrentHashMap<String, Integer>();
        String s;
        Matcher matcher;
        String username;
        String username2;
        SPacketChat sPacketChat;
        final Notification notif;
        String finalUsername;
        this.listener = new Listener<PacketEvent.Receive>(event -> {
            if (AntiChatSpam.mc.field_71439_g != null && !this.isDisabled()) {
                if (!(!(event.getPacket() instanceof SPacketChat))) {
                    s = ((SPacketChat)event.getPacket()).func_148915_c().func_150260_c();
                    matcher = this.CHAT_PATTERN.matcher(s);
                    username = "null";
                    if (matcher.find()) {
                        username2 = matcher.group();
                        username = username2.substring(1, username2.length() - 2);
                    }
                    if (username == "null" || !Ignores.isIgnored(username)) {
                        sPacketChat = (SPacketChat)event.getPacket();
                        if (this.detectSpam(sPacketChat.func_148915_c().func_150260_c()) && username != "null" && !username.equalsIgnoreCase(AntiChatSpam.mc.field_71439_g.func_70005_c_())) {
                            if (this.autoIgnore.getValue()) {
                                if ((this.violate.get(username) != null && this.violate.get(username) >= this.violations.getValue()) || this.violations.getValue() == 0) {
                                    if (username != "null" && !Ignores.isIgnored(username)) {
                                        new Notification(-16777216, ChatColor.translateAlternateColorCodes('&', ChatColor.RED + username + " has exceeded the limitation of spam violation, ignoring."), System.currentTimeMillis(), false);
                                        Command.sendNotification(notif);
                                        finalUsername = username;
                                        MultiThreading.runAsync(() -> this.startIgnore(finalUsername));
                                        this.violate.remove(username);
                                    }
                                }
                                else if (this.violate.get(username) == null) {
                                    this.violate.put(username, 1);
                                }
                                else {
                                    this.violate.put(username, this.violate.get(username) + 1);
                                }
                            }
                            event.cancel();
                        }
                    }
                }
            }
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
    }
    
    public void startIgnore(final String finalUsername) {
        if (AntiChatSpam.mc.field_71439_g.func_70005_c_().equalsIgnoreCase(finalUsername)) {
            return;
        }
        if (Ignores.isIgnored(finalUsername)) {
            return;
        }
        final Ignores.ignore f = getFriendByName(finalUsername);
        if (f == null) {
            return;
        }
        Ignores.Ignore.getValue().add(f);
        this.ignoredBySpamCheck.add(f.getUsername());
        Command.sendNotification(new Notification(-16777216, ChatColor.translateAlternateColorCodes('&', ChatColor.RED + f.getUsername() + " has been auto ignored by AntiSpam for " + this.ignoreDuration.getValue() + ((this.ignoreDuration.getValue() > 1) ? " seconds" : " second")), System.currentTimeMillis(), false));
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (!Ignores.isIgnored(finalUsername)) {
                    return;
                }
                final Ignores.ignore friend2 = Ignores.Ignore.getValue().stream().filter(friend1 -> friend1.getUsername().equalsIgnoreCase(finalUsername)).findFirst().get();
                Ignores.Ignore.getValue().remove(friend2);
                AntiChatSpam.this.ignoredBySpamCheck.remove(friend2.getUsername());
            }
        }, this.ignoreDuration.getValue() * 1000);
    }
    
    public static Ignores.ignore getFriendByName(final String input) {
        final ArrayList<NetworkPlayerInfo> infoMap = new ArrayList<NetworkPlayerInfo>(Minecraft.func_71410_x().func_147114_u().func_175106_d());
        final NetworkPlayerInfo profile = infoMap.stream().filter(networkPlayerInfo -> networkPlayerInfo.func_178845_a().getName().equalsIgnoreCase(input)).findFirst().orElse(null);
        if (profile == null) {
            Command.sendChatMessage("Player isn't online. Looking up UUID..");
            final String s = requestIDs("[\"" + input + "\"]");
            if (s == null || s.isEmpty()) {
                Command.sendChatMessage("Couldn't find player ID. Are you connected to the internet? (0)");
            }
            else {
                final JsonElement element = new JsonParser().parse(s);
                if (element.getAsJsonArray().size() == 0) {
                    Command.sendChatMessage("Couldn't find player ID. (1)");
                }
                else {
                    try {
                        final String id = element.getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString();
                        final String username = element.getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
                        final Ignores.ignore friend = new Ignores.ignore(username, UUIDTypeAdapter.fromString(id));
                        return friend;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        Command.sendChatMessage("Couldn't find player ID. (2)");
                    }
                }
            }
            return null;
        }
        final Ignores.ignore f = new Ignores.ignore(profile.func_178845_a().getName(), profile.func_178845_a().getId());
        return f;
    }
    
    private static String requestIDs(final String data) {
        try {
            final String query = "https://api.mojang.com/profiles/minecraft";
            final String json = data;
            final URL url = new URL(query);
            final HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            final OutputStream os = conn.getOutputStream();
            os.write(json.getBytes(StandardCharsets.UTF_8));
            os.close();
            final InputStream in = new BufferedInputStream(conn.getInputStream());
            final String res = convertStreamToString(in);
            in.close();
            conn.disconnect();
            return res;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    private static String convertStreamToString(final InputStream is) {
        final Scanner s = new Scanner(is).useDelimiter("\\A");
        final String r = s.hasNext() ? s.next() : "/";
        return r;
    }
    
    public void onEnable() {
        this.messageHistory = new ConcurrentHashMap<String, Long>();
    }
    
    public void onDisable() {
        this.messageHistory = null;
    }
    
    @Override
    public void destroy() {
        for (final String name : this.ignoredBySpamCheck) {
            if (!Ignores.isIgnored(name)) {
                return;
            }
            final Ignores.ignore friend2 = Ignores.Ignore.getValue().stream().filter(friend1 -> friend1.getUsername().equalsIgnoreCase(name)).findFirst().get();
            Ignores.Ignore.getValue().remove(friend2);
        }
    }
    
    @Override
    public void onUpdate() {
    }
    
    private boolean detectSpam(final String message) {
        if (!this.filterOwn.getValue() && this.findPatterns(FilterPatterns.OWN_MESSAGE, message)) {
            return false;
        }
        if (this.greenText.getValue() && this.findPatterns(FilterPatterns.GREEN_TEXT, message)) {
            if (this.showBlocked.getValue()) {
                Command.sendChatMessage("[AntiSpam] Green Text: " + message);
            }
            return true;
        }
        if (this.discordLinks.getValue() && this.findPatterns(FilterPatterns.DISCORD, message)) {
            if (this.showBlocked.getValue()) {
                Command.sendChatMessage("[AntiSpam] Discord Link: " + message);
            }
            return true;
        }
        if (this.webLinks.getValue() && this.findPatterns(FilterPatterns.WEB_LINK, message)) {
            if (this.showBlocked.getValue()) {
                Command.sendChatMessage("[AntiSpam] Web Link: " + message);
            }
            return true;
        }
        if (this.ips.getValue() && this.findPatterns(FilterPatterns.IP_ADDR, message)) {
            if (this.showBlocked.getValue()) {
                Command.sendChatMessage("[AntiSpam] IP Address: " + message);
            }
            return true;
        }
        if (this.ipsAgr.getValue() && this.findPatterns(FilterPatterns.IP_ADDR_AGR, message)) {
            if (this.showBlocked.getValue()) {
                Command.sendChatMessage("[AntiSpam] IP Aggressive: " + message);
            }
            return true;
        }
        if (this.tradeChat.getValue() && this.findPatterns(FilterPatterns.TRADE_CHAT, message)) {
            if (this.showBlocked.getValue()) {
                Command.sendChatMessage("[AntiSpam] Trade Chat: " + message);
            }
            return true;
        }
        if (this.numberSuffix.getValue() && this.findPatterns(FilterPatterns.NUMBER_SUFFIX, message)) {
            if (this.showBlocked.getValue()) {
                Command.sendChatMessage("[AntiSpam] Number Suffix: " + message);
            }
            return true;
        }
        if (this.announcers.getValue() && this.findPatterns(FilterPatterns.ANNOUNCER, message)) {
            if (this.showBlocked.getValue()) {
                Command.sendChatMessage("[AntiSpam] Announcer: " + message);
            }
            return true;
        }
        if (this.spammers.getValue() && this.findPatterns(FilterPatterns.SPAMMER, message)) {
            if (this.showBlocked.getValue()) {
                Command.sendChatMessage("[AntiSpam] Spammers: " + message);
            }
            return true;
        }
        if (this.insulters.getValue() && this.findPatterns(FilterPatterns.INSULTER, message)) {
            if (this.showBlocked.getValue()) {
                Command.sendChatMessage("[AntiSpam] Insulter: " + message);
            }
            return true;
        }
        if (this.greeters.getValue() && this.findPatterns(FilterPatterns.GREETER, message)) {
            if (this.showBlocked.getValue()) {
                Command.sendChatMessage("[AntiSpam] Greeter: " + message);
            }
            return true;
        }
        if (this.duplicates.getValue()) {
            if (this.messageHistory == null) {
                this.messageHistory = new ConcurrentHashMap<String, Long>();
            }
            boolean isDuplicate = false;
            if (this.messageHistory.containsKey(message) && (System.currentTimeMillis() - this.messageHistory.get(message)) / 1000L < this.duplicatesTimeout.getValue()) {
                isDuplicate = true;
            }
            this.messageHistory.put(message, System.currentTimeMillis());
            if (isDuplicate) {
                if (this.showBlocked.getValue()) {
                    Command.sendChatMessage("[AntiSpam] Duplicate: " + message);
                }
                return true;
            }
        }
        return false;
    }
    
    private boolean findPatterns(final String[] patterns, final String string) {
        for (final String pattern : patterns) {
            if (Pattern.compile(pattern).matcher(string).find()) {
                return true;
            }
        }
        return false;
    }
    
    private static class FilterPatterns
    {
        private static final String[] ANNOUNCER;
        private static final String[] SPAMMER;
        private static final String[] INSULTER;
        private static final String[] GREETER;
        private static final String[] DISCORD;
        private static final String[] NUMBER_SUFFIX;
        private static final String[] GREEN_TEXT;
        private static final String[] TRADE_CHAT;
        private static final String[] WEB_LINK;
        private static final String[] IP_ADDR;
        private static final String[] IP_ADDR_AGR;
        private static final String[] OWN_MESSAGE;
        
        static {
            ANNOUNCER = new String[] { "I just walked .+ feet!", "I just placed a .+!", "I just attacked .+ with a .+!", "I just dropped a .+!", "I just opened chat!", "I just opened my console!", "I just opened my GUI!", "I just went into full screen mode!", "I just paused my game!", "I just opened my inventory!", "I just looked at the player list!", "I just took a screen shot!", "I just swaped hands!", "I just ducked!", "I just changed perspectives!", "I just jumped!", "I just ate a .+!", "I just crafted .+ .+!", "I just picked up a .+!", "I just smelted .+ .+!", "I just respawned!", "I just attacked .+ with my hands", "I just broke a .+!", "I recently walked .+ blocks", "I just droped a .+ called, .+!", "I just placed a block called, .+!", "Im currently breaking a block called, .+!", "I just broke a block called, .+!", "I just opened chat!", "I just opened chat and typed a slash!", "I just paused my game!", "I just opened my inventory!", "I just looked at the player list!", "I just changed perspectives, now im in .+!", "I just crouched!", "I just jumped!", "I just attacked a entity called, .+ with a .+", "Im currently eatting a peice of food called, .+!", "Im currently using a item called, .+!", "I just toggled full screen mode!", "I just took a screen shot!", "I just swaped hands and now theres a .+ in my main hand and a .+ in my off hand!", "I just used pick block on a block called, .+!", "Ra just completed his blazing ark", "Its a new day yes it is", "I just placed .+ thanks to (http:\\/\\/)?DotGod\\.CC!", "I just flew .+ meters like a butterfly thanks to (http:\\/\\/)?DotGod\\.CC!" };
            SPAMMER = new String[] { "WWE Client's spammer", "Lol get gud", "Future client is bad", "WWE > Future", "WWE > Impact", "Default Message", "IKnowImEZ is a god", "THEREALWWEFAN231 is a god", "WWE Client made by IKnowImEZ/THEREALWWEFAN231", "WWE Client was the first public client to have Path Finder/New Chunks", "WWE Client was the first public client to have color signs", "WWE Client was the first client to have Teleport Finder", "WWE Client was the first client to have Tunneller & Tunneller Back Fill" };
            INSULTER = new String[] { ".+ Download WWE utility mod, Its free!", ".+ 4b4t is da best mintscreft serber", ".+ dont abouse", ".+ you cuck", ".+ https://www.youtube.com/channel/UCJGCNPEjvsCn0FKw3zso0TA", ".+ is my step dad", ".+ again daddy!", "dont worry .+ it happens to every one", ".+ dont buy future it's crap, compared to WWE!", "What are you, fucking gay, .+?", "Did you know? .+ hates you, .+", "You are literally 10, .+", ".+ finally lost their virginity, sadly they lost it to .+... yeah, that's unfortunate.", ".+, don't be upset, it's not like anyone cares about you, fag.", ".+, see that rubbish bin over there? Get your ass in it, or I'll get .+ to whoop your ass.", ".+, may I borrow that dirt block? that guy named .+ needs it...", "Yo, .+, btfo you virgin", "Hey .+ want to play some High School RP with me and .+?", ".+ is an Archon player. Why is he on here? Fucking factions player.", "Did you know? .+ just joined The Vortex Coalition!", ".+ has successfully conducted the cactus dupe and duped a itemhand!", ".+, are you even human? You act like my dog, holy shit.", ".+, you were never loved by your family.", "Come on .+, you hurt .+'s feelings. You meany.", "Stop trying to meme .+, you can't do that. kek", ".+, .+ is gay. Don't go near him.", "Whoa .+ didn't mean to offend you, .+.", ".+ im not pvping .+, im WWE'ing .+.", "Did you know? .+ just joined The Vortex Coalition!", ".+, are you even human? You act like my dog, holy shit." };
            GREETER = new String[] { "Bye, Bye .+", "Farwell, .+" };
            DISCORD = new String[] { "discord.gg", "discordapp.com", "discord.io", "invite.gg" };
            NUMBER_SUFFIX = new String[] { ".+\\d{3,}$" };
            GREEN_TEXT = new String[] { "^<.+> >" };
            TRADE_CHAT = new String[] { "buy", "sell" };
            WEB_LINK = new String[] { "http:\\/\\/", "https:\\/\\/", "www." };
            IP_ADDR = new String[] { "\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\:\\d{1,5}\\b", "\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}", "^(?:http(?:s)?:\\/\\/)?(?:[^\\.]+\\.)?.*\\..*\\..*$", ".*\\..*\\:\\d{1,5}$" };
            IP_ADDR_AGR = new String[] { ".*\\..*$" };
            OWN_MESSAGE = new String[] { "^<" + AntiChatSpam.mc.field_71439_g.func_70005_c_() + "> ", "^To .+: " };
        }
    }
}
