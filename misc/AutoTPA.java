// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import java.util.function.Predicate;
import meow.candycat.uwu.util.Wrapper;
import net.minecraft.network.play.server.SPacketChat;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "AutoTPA", description = "Auto Accepts/Declines TPA requests", category = Category.MISC)
public class AutoTPA extends Module
{
    private Setting<mode> mod;
    @EventHandler
    public Listener<PacketEvent.Receive> receiveListener;
    
    public AutoTPA() {
        this.mod = this.register(Settings.e("Response", mode.DENY));
        SPacketChat packet;
        final Object o;
        this.receiveListener = new Listener<PacketEvent.Receive>(event -> {
            if (event.getPacket() instanceof SPacketChat) {
                packet = (SPacketChat)event.getPacket();
                if (((SPacketChat)o).func_148915_c().func_150260_c().contains(" has requested to teleport to you.")) {
                    switch (this.mod.getValue()) {
                        case ACCEPT: {
                            Wrapper.getPlayer().func_71165_d("/tpaccept");
                            break;
                        }
                        case DENY: {
                            Wrapper.getPlayer().func_71165_d("/tpdeny");
                            break;
                        }
                    }
                }
            }
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
    }
    
    public enum mode
    {
        ACCEPT, 
        DENY;
    }
}
