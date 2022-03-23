// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import meow.candycat.uwu.command.Command;
import java.util.function.Predicate;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "PearlDupe", description = "Duplicates your inventory", category = Category.MISC)
public class PearlDupe extends Module
{
    private Setting<Boolean> info;
    private Setting<Boolean> warn;
    private Setting<Boolean> singleplayer;
    private Setting<Boolean> disable;
    @EventHandler
    Listener<PacketEvent.Receive> receiveListener;
    
    public PearlDupe() {
        this.info = this.register(Settings.b("Info", true));
        this.warn = this.register(Settings.b("Warning", true));
        this.singleplayer = this.register(Settings.b("SinglePlayer Disable", true));
        this.disable = this.register(Settings.b("Disable on death", true));
        this.receiveListener = new Listener<PacketEvent.Receive>(event -> {
            if (event.getPacket() instanceof CPacketConfirmTeleport) {
                Minecraft.func_71410_x().field_71442_b.field_78774_b.func_147297_a((Packet)new CPacketChatMessage("/kill"));
                if (this.disable.getValue()) {
                    this.disable();
                }
            }
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
    }
    
    public void onEnable() {
        if (PearlDupe.mc.field_71439_g == null) {
            return;
        }
        if (Minecraft.func_71410_x().func_147104_D() == null) {
            Command.sendErrorMessage("[PearlDupe] Error: &r&4This doesn't work in singleplayer");
            if (this.singleplayer.getValue()) {
                this.disable();
            }
            return;
        }
        if (this.info.getValue()) {
            Command.sendChatMessage("[PearlDupe] Instructions: throw a pearl, it /kills on teleport ");
            Command.sendChatMessage("[PearlDupe] This doesn't always work, and it doesn't work for 2b2t and 9b9t");
        }
        if (this.warn.getValue()) {
            Command.sendWarningMessage("[PearlDupe] Warning is still on, please disable the option once you've read the instructions");
            this.disable();
        }
    }
}
