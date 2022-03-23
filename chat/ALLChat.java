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

@Info(name = "ALL-Chat", category = Category.CHAT, description = "Modifies your chat messages")
public class ALLChat extends Module
{
    private Setting<Boolean> commands;
    private Setting<Boolean> ZEROhack;
    private Setting<Boolean> acehack;
    private Setting<Boolean> apollyon;
    private Setting<Boolean> backdoored;
    private Setting<Boolean> baldhack;
    private Setting<Boolean> elementars;
    private Setting<Boolean> furryware;
    private Setting<Boolean> gayclient;
    private Setting<Boolean> keemycc;
    private Setting<Boolean> nutgod;
    private Setting<Boolean> penishack;
    private Setting<Boolean> souphack;
    private Setting<Boolean> owo;
    private Setting<Boolean> kami;
    private final String KAMI_SUFFIX = " \u00c2» \u02e2\u207f\u1d52\u02b7";
    private final String ZERO_SUFFIX = "| 027Hack";
    private final String ACE_SUFFIX = " \u23d0 \u1d00\u1d04\u1d07 \u029c\u1d00\u1d04\u1d0b";
    private final String APO_SUFFIX = " \u2713\u1d00\u1d18\u0150\u029f\u00c2¥\u028f\u1d0f\u0143.\u0493\u1d00\u0262";
    private final String BDH_SUFFIX = " \u00c2»\u0299\u1d00\u1d04\u1d0b\u1d05\u1d0f\u1d0f\u0280\u1d07\u1d05";
    private final String BAL_SUFFIX = " \u23d0 \u0299\u1d00\u029f\u1d05\u029c\u1d00\u1d04\u1d0b";
    private final String ELE_SUFFIX = " \u23d0 \u1d07\u029f\u1d07\u1d0d\u1d07\u0274\u1d1b\u1d00\ua731.\u1d04\u1d0f\u1d0d";
    private final String FUR_SUFFIX = " \u23d0 \u0493\u1d1c\u0280\u0280\u028f\u1d21\u1d00\u0280\u1d07";
    private final String GAY_SUFFIX = " \u23d0 \u0262\u1d00\u028f";
    private final String KEE_SUFFIX = " \u23d0 \u1d0b\u1d07\u1d07\u1d0d\u028f.\u1d04\u1d04\u30c4";
    private final String NUT_SUFFIX = " \u23d0 \u0274\u1d1c\u1d1b\u0262\u1d0f\u1d05.\u1d04\u1d04 \u0fc9";
    private final String PEN_SUFFIX = " \u23d0 PENIS";
    private final String SOU_SUFFIX = " \u23d0 \u0e23\u0e4f\u0e22\u05e7\u0452\u0e04\u03c2\u043a";
    private final String OWO_SUFFIX = " owo im a cat hiding";
    @EventHandler
    public Listener<PacketEvent.Send> listener;
    
    public ALLChat() {
        this.commands = this.register(Settings.b("Commands", false));
        this.owo = this.register(Settings.b("idk whats this", true));
        this.kami = this.register(Settings.b("idk whats this too", true));
        this.ZEROhack = this.register(Settings.b("027Hack", false));
        this.acehack = this.register(Settings.b("AceHack", false));
        this.apollyon = this.register(Settings.b("Apollyon", false));
        this.backdoored = this.register(Settings.b("Backdoored", false));
        this.baldhack = this.register(Settings.b("BaldHack", false));
        this.elementars = this.register(Settings.b("Elementars", false));
        this.furryware = this.register(Settings.b("Furryware", false));
        this.gayclient = this.register(Settings.b("Gayclient", false));
        this.keemycc = this.register(Settings.b("Keemycc", false));
        this.nutgod = this.register(Settings.b("Nutgod", false));
        this.penishack = this.register(Settings.b("PenisHack", false));
        this.souphack = this.register(Settings.b("Souphack", false));
        final String[] s = { null };
        final String[] s2 = { null };
        final Object o;
        StringBuilder sb;
        final int n;
        StringBuilder sb2;
        final int n2;
        StringBuilder sb3;
        final int n3;
        StringBuilder sb4;
        final int n4;
        StringBuilder sb5;
        final int n5;
        StringBuilder sb6;
        final int n6;
        StringBuilder sb7;
        final int n7;
        StringBuilder sb8;
        final int n8;
        StringBuilder sb9;
        final int n9;
        StringBuilder sb10;
        final int n10;
        StringBuilder sb11;
        final int n11;
        StringBuilder sb12;
        final int n12;
        StringBuilder sb13;
        final int n13;
        final Object o2;
        this.listener = new Listener<PacketEvent.Send>(event -> {
            if (event.getPacket() instanceof CPacketChatMessage) {
                o[0] = ((CPacketChatMessage)event.getPacket()).func_149439_c();
                if (!o[0].startsWith("/") || this.commands.getValue()) {
                    if (this.ZEROhack.getValue()) {
                        sb = new StringBuilder();
                        o[n] = sb.append(o[n]).append("| 027Hack").toString();
                    }
                    if (this.acehack.getValue()) {
                        sb2 = new StringBuilder();
                        o[n2] = sb2.append(o[n2]).append(" \u23d0 \u1d00\u1d04\u1d07 \u029c\u1d00\u1d04\u1d0b").toString();
                    }
                    if (this.apollyon.getValue()) {
                        sb3 = new StringBuilder();
                        o[n3] = sb3.append(o[n3]).append(" \u2713\u1d00\u1d18\u0150\u029f\u00c2¥\u028f\u1d0f\u0143.\u0493\u1d00\u0262").toString();
                    }
                    if (this.backdoored.getValue()) {
                        sb4 = new StringBuilder();
                        o[n4] = sb4.append(o[n4]).append(" \u00c2»\u0299\u1d00\u1d04\u1d0b\u1d05\u1d0f\u1d0f\u0280\u1d07\u1d05").toString();
                    }
                    if (this.baldhack.getValue()) {
                        sb5 = new StringBuilder();
                        o[n5] = sb5.append(o[n5]).append(" \u23d0 \u0299\u1d00\u029f\u1d05\u029c\u1d00\u1d04\u1d0b").toString();
                    }
                    if (this.elementars.getValue()) {
                        sb6 = new StringBuilder();
                        o[n6] = sb6.append(o[n6]).append(" \u23d0 \u1d07\u029f\u1d07\u1d0d\u1d07\u0274\u1d1b\u1d00\ua731.\u1d04\u1d0f\u1d0d").toString();
                    }
                    if (this.furryware.getValue()) {
                        sb7 = new StringBuilder();
                        o[n7] = sb7.append(o[n7]).append(" \u23d0 \u0493\u1d1c\u0280\u0280\u028f\u1d21\u1d00\u0280\u1d07").toString();
                    }
                    if (this.gayclient.getValue()) {
                        sb8 = new StringBuilder();
                        o[n8] = sb8.append(o[n8]).append(" \u23d0 \u0262\u1d00\u028f").toString();
                    }
                    if (this.keemycc.getValue()) {
                        sb9 = new StringBuilder();
                        o[n9] = sb9.append(o[n9]).append(" \u23d0 \u1d0b\u1d07\u1d07\u1d0d\u028f.\u1d04\u1d04\u30c4").toString();
                    }
                    if (this.nutgod.getValue()) {
                        sb10 = new StringBuilder();
                        o[n10] = sb10.append(o[n10]).append(" \u23d0 \u0274\u1d1c\u1d1b\u0262\u1d0f\u1d05.\u1d04\u1d04 \u0fc9").toString();
                    }
                    if (this.penishack.getValue()) {
                        sb11 = new StringBuilder();
                        o[n11] = sb11.append(o[n11]).append(" \u23d0 PENIS").toString();
                    }
                    if (this.souphack.getValue()) {
                        sb12 = new StringBuilder();
                        o[n12] = sb12.append(o[n12]).append(" \u23d0 \u0e23\u0e4f\u0e22\u05e7\u0452\u0e04\u03c2\u043a").toString();
                    }
                    if (this.owo.getValue()) {
                        sb13 = new StringBuilder();
                        o[n13] = sb13.append(o[n13]).append(" owo im a cat hiding").toString();
                    }
                    if (this.kami.getValue()) {
                        o2[0] = o[0] + " \u00c2» \u02e2\u207f\u1d52\u02b7";
                    }
                    else {
                        o2[0] = o[0];
                    }
                    if (o2[0].length() >= 256) {
                        o2[0] = o2[0].substring(0, 256);
                    }
                    ((CPacketChatMessage)event.getPacket()).field_149440_a = o2[0];
                }
            }
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
    }
}
