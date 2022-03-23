// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import meow.candycat.uwu.command.Command;
import java.util.Iterator;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import com.mojang.realmsclient.gui.ChatFormatting;
import meow.candycat.uwu.util.Friends;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import java.util.ArrayList;
import meow.candycat.uwu.setting.Settings;
import java.util.List;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "VisualRange", description = "Shows players who enter and leave range in chat", category = Category.MISC)
public class VisualRange extends Module
{
    private Setting<Boolean> leaving;
    private Setting<Boolean> announce;
    private Setting<Integer> delay;
    private int delayw;
    private boolean sent;
    private List<String> knownPlayers;
    private List<String> needtosend;
    
    public VisualRange() {
        this.leaving = this.register(Settings.b("Leaving", false));
        this.announce = this.register(Settings.b("Announce", true));
        this.delay = this.register(Settings.i("Delay", 3));
        this.delayw = this.delay.getValue();
        this.needtosend = new ArrayList<String>();
    }
    
    @Override
    public void onUpdate() {
        if (this.delayw >= this.delay.getValue()) {
            this.sent = false;
            this.delayw = 0;
        }
        else {
            ++this.delayw;
        }
        if (VisualRange.mc.field_71439_g == null) {
            return;
        }
        final List<String> tickPlayerList = new ArrayList<String>();
        for (final Entity entity : VisualRange.mc.field_71441_e.func_72910_y()) {
            if (entity instanceof EntityPlayer) {
                tickPlayerList.add(entity.func_70005_c_());
            }
        }
        if (tickPlayerList.size() > 0) {
            for (final String playerName : tickPlayerList) {
                if (playerName.equals(VisualRange.mc.field_71439_g.func_70005_c_())) {
                    continue;
                }
                if (!this.knownPlayers.contains(playerName)) {
                    this.knownPlayers.add(playerName);
                    if (Friends.isFriend(playerName)) {
                        this.sendNotification(ChatFormatting.GREEN.toString() + playerName + ChatFormatting.RESET.toString() + " entered the Battlefield!");
                    }
                    else {
                        this.sendNotification(ChatFormatting.RED.toString() + playerName + ChatFormatting.RESET.toString() + " entered the Battlefield!");
                    }
                    if (this.announce.getValue() && !this.sent) {
                        VisualRange.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketChatMessage("OwO Hi " + playerName + " UwU"));
                        this.sent = true;
                    }
                    else {
                        this.needtosend.add("OwO Hi " + playerName + " UwU");
                    }
                    return;
                }
            }
        }
        if (this.knownPlayers.size() > 0) {
            for (final String playerName : this.knownPlayers) {
                if (!tickPlayerList.contains(playerName)) {
                    this.knownPlayers.remove(playerName);
                    if (this.leaving.getValue()) {
                        if (Friends.isFriend(playerName)) {
                            this.sendNotification(ChatFormatting.GREEN.toString() + playerName + ChatFormatting.RESET.toString() + " left the Battlefield!");
                        }
                        else {
                            this.sendNotification(ChatFormatting.RED.toString() + playerName + ChatFormatting.RESET.toString() + " left the Battlefield!");
                        }
                    }
                    if (this.announce.getValue() && !this.sent) {
                        VisualRange.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketChatMessage("QwQ Bye " + playerName + " :("));
                        this.sent = true;
                    }
                    else {
                        this.needtosend.add("QwQ Bye " + playerName + " :(");
                    }
                    return;
                }
            }
        }
        if (this.needtosend.size() > 0 && !this.sent) {
            final Iterator<String> iterator4 = this.needtosend.iterator();
            if (iterator4.hasNext()) {
                final String uwu = iterator4.next();
                VisualRange.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketChatMessage(uwu));
                this.sent = true;
                this.needtosend.remove(uwu);
            }
        }
    }
    
    private void sendNotification(final String s) {
        Command.sendChatMessage(s);
    }
    
    public void onEnable() {
        this.knownPlayers = new ArrayList<String>();
    }
}
