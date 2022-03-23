// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import java.util.function.Predicate;
import net.minecraft.network.play.client.CPacketCloseWindow;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "XCarry", category = Category.MISC)
public class XCarry extends Module
{
    private Setting<Boolean> forcecancel;
    @EventHandler
    private Listener<PacketEvent.Send> xcarrylistener;
    
    public XCarry() {
        this.forcecancel = this.register(Settings.b("Force Cancel"));
        CPacketCloseWindow packet;
        this.xcarrylistener = new Listener<PacketEvent.Send>(p_Event -> {
            if (p_Event.getPacket() instanceof CPacketCloseWindow) {
                packet = (CPacketCloseWindow)p_Event.getPacket();
                if (packet.field_149556_a == XCarry.mc.field_71439_g.field_71069_bz.field_75152_c || this.forcecancel.getValue()) {
                    p_Event.cancel();
                }
            }
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
    }
}
