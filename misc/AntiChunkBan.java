// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.client.Minecraft;
import meow.candycat.uwu.command.Command;
import java.util.function.Predicate;
import net.minecraft.network.play.server.SPacketChunkData;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "AntiChunkBan", description = "Spams /kill, gets out of ban chunks.", category = Category.MISC)
public class AntiChunkBan extends Module
{
    private static long startTime;
    private Setting<ModeThing> modeThing;
    private Setting<Float> delayTime;
    private Setting<Boolean> disable;
    private Setting<Boolean> warn;
    @EventHandler
    Listener<PacketEvent.Receive> receiveListener;
    
    public AntiChunkBan() {
        this.modeThing = this.register(Settings.e("Mode", ModeThing.PACKET));
        this.delayTime = this.register(Settings.f("Kill Delay", 10.0f));
        this.disable = this.register(Settings.b("Disable After Kill", false));
        this.warn = this.register(Settings.b("Warning", true));
        this.receiveListener = new Listener<PacketEvent.Receive>(event -> {
            if (this.modeThing.getValue().equals(ModeThing.PACKET) || this.modeThing.getValue().equals(ModeThing.BOTH)) {
                if (AntiChunkBan.mc.field_71439_g != null) {
                    if (event.getPacket() instanceof SPacketChunkData) {
                        event.cancel();
                    }
                }
            }
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
    }
    
    public void onEnable() {
        if (AntiChunkBan.mc.field_71439_g == null) {
            return;
        }
        Command.sendChatMessage("[AntiChunkBan] Note: this disables chunks loading in. If you want to be able to play normally you have to disable it");
    }
    
    @Override
    public void onUpdate() {
        if (AntiChunkBan.mc.field_71439_g == null) {
            return;
        }
        if ((this.modeThing.getValue().equals(ModeThing.KILL) || this.modeThing.getValue().equals(ModeThing.BOTH)) && Minecraft.func_71410_x().func_147104_D() != null) {
            if (AntiChunkBan.startTime == 0L) {
                AntiChunkBan.startTime = System.currentTimeMillis();
            }
            if (AntiChunkBan.startTime + this.delayTime.getValue() <= System.currentTimeMillis()) {
                if (Minecraft.func_71410_x().func_147104_D() != null) {
                    Minecraft.func_71410_x().field_71442_b.field_78774_b.func_147297_a((Packet)new CPacketChatMessage("/kill"));
                }
                if (AntiChunkBan.mc.field_71439_g.func_110143_aJ() <= 0.0f) {
                    AntiChunkBan.mc.field_71439_g.func_71004_bE();
                    AntiChunkBan.mc.func_147108_a((GuiScreen)null);
                    if (this.disable.getValue()) {
                        this.disable();
                    }
                }
                AntiChunkBan.startTime = System.currentTimeMillis();
            }
        }
    }
    
    static {
        AntiChunkBan.startTime = 0L;
    }
    
    private enum ModeThing
    {
        PACKET, 
        KILL, 
        BOTH;
    }
}
