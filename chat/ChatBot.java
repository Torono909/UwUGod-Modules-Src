// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.chat;

import net.minecraft.client.network.NetHandlerPlayClient;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.function.Predicate;
import meow.candycat.uwu.util.Friends;
import meow.candycat.uwu.util.LagCompensator;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import java.util.Collection;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.server.SPacketChat;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import java.util.regex.Pattern;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "UselessChatBot", category = Category.CHAT, description = "uwu")
public class ChatBot extends Module
{
    public Setting<Boolean> icegay;
    private final Pattern CHAT_PATTERN;
    private final Pattern CHAT_PATTERN2;
    @EventHandler
    private Listener<PacketEvent.Receive> receiveListener;
    
    public ChatBot() {
        this.icegay = this.register(Settings.b("Say Ice Gay", true));
        this.CHAT_PATTERN = Pattern.compile("<.*?> ");
        this.CHAT_PATTERN2 = Pattern.compile("(.*?)");
        String s;
        Matcher matcher;
        String username;
        Matcher matcher2;
        String username2;
        StringBuilder builder;
        String s2;
        String s3;
        ArrayList<Object> infoMap;
        final Iterator<Entity> iterator;
        Entity qwq;
        String finalS;
        NetworkPlayerInfo profile;
        StringBuilder message;
        String messageSanitized;
        ArrayList<Object> infoMap2;
        String finalUsername;
        NetworkPlayerInfo profile2;
        StringBuilder message2;
        String messageSanitized2;
        StringBuilder message3;
        String messageSanitized3;
        String s4;
        ArrayList<Object> infoMap3;
        final Iterator<Entity> iterator2;
        Entity qwq2;
        String finalS2;
        NetworkPlayerInfo profile3;
        StringBuilder message4;
        String messageSanitized4;
        String uwu;
        String messageSanitized5;
        String s5;
        ArrayList<Object> infoMap4;
        final Iterator<Entity> iterator3;
        Entity qwq3;
        String finalS3;
        NetworkPlayerInfo profile4;
        StringBuilder message5;
        String messageSanitized6;
        String s6;
        ArrayList<Object> infoMap5;
        final Iterator<Entity> iterator4;
        Entity qwq4;
        String finalS4;
        NetworkPlayerInfo profile5;
        NetHandlerPlayClient field_71174_a;
        final CPacketChatMessage cPacketChatMessage;
        String uwu2;
        String messageSanitized7;
        this.receiveListener = new Listener<PacketEvent.Receive>(event -> {
            if (event.getPacket() instanceof SPacketChat) {
                s = ((SPacketChat)event.getPacket()).func_148915_c().func_150260_c();
                matcher = this.CHAT_PATTERN.matcher(s);
                username = "unnamed";
                matcher2 = this.CHAT_PATTERN2.matcher(s);
                if (matcher2.find()) {
                    matcher2.group();
                    s = matcher2.replaceFirst("");
                }
                if (matcher.find()) {
                    username2 = matcher.group();
                    username = username2.substring(1, username2.length() - 2);
                    s = matcher.replaceFirst("");
                }
                if (s.toLowerCase().contains("notallowice") && this.icegay.getValue() && !s.startsWith("=")) {
                    ChatBot.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketChatMessage("Ice cream is gay AWAWAWA"));
                }
                builder = new StringBuilder();
                if (!(!s.startsWith("="))) {
                    s2 = s.substring(Math.min(s.length(), 1));
                    if (s2.startsWith("ping")) {
                        s3 = s2.substring(Math.min(s2.length(), 5));
                        infoMap = new ArrayList<Object>(Minecraft.func_71410_x().func_147114_u().func_175106_d());
                        ChatBot.mc.field_71441_e.field_72996_f.iterator();
                        while (iterator.hasNext()) {
                            qwq = iterator.next();
                            if (qwq instanceof EntityPlayer && s3.contains(qwq.func_70005_c_())) {
                                s3 = qwq.func_70005_c_();
                            }
                        }
                        finalS = s3;
                        profile = infoMap.stream().filter(networkPlayerInfo -> finalS.toLowerCase().contains(networkPlayerInfo.func_178845_a().getName().toLowerCase())).findFirst().orElse(null);
                        if (profile != null) {
                            message = new StringBuilder();
                            message.append(profile.func_178845_a().getName());
                            message.append("'s ping is ");
                            message.append(profile.func_178853_c());
                            message.append(" uwu");
                            messageSanitized = message.toString().replaceAll("\u79ae", "");
                            if (messageSanitized.length() > 255) {
                                messageSanitized = messageSanitized.substring(0, 255);
                            }
                            ChatBot.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketChatMessage(messageSanitized));
                        }
                    }
                    else if (s2.startsWith("myping")) {
                        infoMap2 = new ArrayList<Object>(Minecraft.func_71410_x().func_147114_u().func_175106_d());
                        finalUsername = username;
                        profile2 = infoMap2.stream().filter(networkPlayerInfo -> networkPlayerInfo.func_178845_a().getName().equalsIgnoreCase(finalUsername)).findFirst().orElse(null);
                        if (profile2 != null) {
                            message2 = new StringBuilder();
                            message2.append("Your ping is ");
                            message2.append(profile2.func_178853_c());
                            message2.append(" uwu");
                            messageSanitized2 = message2.toString().replaceAll("\u79ae", "");
                            if (messageSanitized2.length() > 255) {
                                messageSanitized2 = messageSanitized2.substring(0, 255);
                            }
                            ChatBot.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketChatMessage(messageSanitized2));
                        }
                    }
                    else if (s2.startsWith("tps")) {
                        message3 = new StringBuilder();
                        message3.append("The tps is now ");
                        message3.append(LagCompensator.INSTANCE.getTickRate());
                        message3.append(" uwu");
                        messageSanitized3 = message3.toString().replaceAll("\u79ae", "");
                        if (messageSanitized3.length() > 255) {
                            messageSanitized3 = messageSanitized3.substring(0, 255);
                        }
                        ChatBot.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketChatMessage(messageSanitized3));
                    }
                    else if (s2.startsWith("uwu")) {
                        s4 = s2.substring(Math.min(s2.length(), 4));
                        infoMap3 = new ArrayList<Object>(Minecraft.func_71410_x().func_147114_u().func_175106_d());
                        ChatBot.mc.field_71441_e.field_72996_f.iterator();
                        while (iterator2.hasNext()) {
                            qwq2 = iterator2.next();
                            if (qwq2 instanceof EntityPlayer && s4.contains(qwq2.func_70005_c_())) {
                                s4 = qwq2.func_70005_c_();
                            }
                        }
                        finalS2 = s4;
                        profile3 = infoMap3.stream().filter(networkPlayerInfo -> finalS2.toLowerCase().contains(networkPlayerInfo.func_178845_a().getName().toLowerCase())).findFirst().orElse(null);
                        if (profile3 != null) {
                            message4 = new StringBuilder();
                            message4.append(username);
                            message4.append(" uwu'd at ");
                            message4.append(profile3.func_178845_a().getName());
                            messageSanitized4 = message4.toString().replaceAll("\u79ae", "");
                            if (messageSanitized4.length() > 255) {
                                messageSanitized4 = messageSanitized4.substring(0, 255);
                            }
                            ChatBot.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketChatMessage(messageSanitized4));
                        }
                    }
                    else if (s2.startsWith("help")) {
                        uwu = "The commands are : tps, myping, ping playername, uwu playername, fight playername";
                        messageSanitized5 = uwu.replaceAll("\u79ae", "");
                        if (messageSanitized5.length() > 255) {
                            messageSanitized5 = messageSanitized5.substring(0, 255);
                        }
                        ChatBot.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketChatMessage(messageSanitized5));
                    }
                    else if (s2.startsWith("fight")) {
                        s5 = s2.substring(Math.min(s2.length(), 6));
                        infoMap4 = new ArrayList<Object>(Minecraft.func_71410_x().func_147114_u().func_175106_d());
                        ChatBot.mc.field_71441_e.field_72996_f.iterator();
                        while (iterator3.hasNext()) {
                            qwq3 = iterator3.next();
                            if (qwq3 instanceof EntityPlayer && s5.contains(qwq3.func_70005_c_())) {
                                s5 = qwq3.func_70005_c_();
                            }
                        }
                        finalS3 = s5;
                        profile4 = infoMap4.stream().filter(networkPlayerInfo -> finalS3.toLowerCase().contains(networkPlayerInfo.func_178845_a().getName().toLowerCase())).findFirst().orElse(null);
                        if (profile4 != null) {
                            message5 = new StringBuilder();
                            message5.append(username);
                            message5.append(" fighted with ");
                            message5.append(profile4.func_178845_a().getName() + "! ");
                            message5.append(Friends.uwu.contains(username) ? username : ((Math.ceil(Math.random() * 2.0) == 1.0) ? username : profile4.func_178845_a().getName()));
                            message5.append(" won!");
                            messageSanitized6 = message5.toString().replaceAll("\u79ae", "");
                            if (messageSanitized6.length() > 255) {
                                messageSanitized6 = messageSanitized6.substring(0, 255);
                            }
                            ChatBot.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketChatMessage(messageSanitized6));
                        }
                    }
                    else if (s2.startsWith("gay")) {
                        s6 = s2.substring(Math.min(s2.length(), 4));
                        infoMap5 = new ArrayList<Object>(Minecraft.func_71410_x().func_147114_u().func_175106_d());
                        ChatBot.mc.field_71441_e.field_72996_f.iterator();
                        while (iterator4.hasNext()) {
                            qwq4 = iterator4.next();
                            if (qwq4 instanceof EntityPlayer && s6.contains(qwq4.func_70005_c_())) {
                                s6 = qwq4.func_70005_c_();
                            }
                        }
                        finalS4 = s6;
                        profile5 = infoMap5.stream().filter(networkPlayerInfo -> finalS4.toLowerCase().contains(networkPlayerInfo.func_178845_a().getName().toLowerCase())).findFirst().orElse(null);
                        if (profile5 != null) {
                            field_71174_a = ChatBot.mc.field_71439_g.field_71174_a;
                            new CPacketChatMessage(profile5.func_178845_a().getName() + " is " + ((!profile5.func_178845_a().getName().equalsIgnoreCase("icecreammnn") || !profile5.func_178845_a().getName().equalsIgnoreCase("notallowice") || !profile5.func_178845_a().getName().equalsIgnoreCase("antichainpop")) ? (Math.random() * 100.0) : 100.0) + "% gay");
                            field_71174_a.func_147297_a((Packet)cPacketChatMessage);
                        }
                    }
                    else {
                        uwu2 = "Sorry, I cant understand this command";
                        messageSanitized7 = uwu2.replaceAll("\u79ae", "");
                        if (messageSanitized7.length() > 255) {
                            messageSanitized7 = messageSanitized7.substring(0, 255);
                        }
                        ChatBot.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketChatMessage(messageSanitized7));
                    }
                }
            }
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
    }
}
