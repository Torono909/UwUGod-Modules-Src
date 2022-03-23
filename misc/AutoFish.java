// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import java.util.function.Predicate;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.init.Items;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.module.Module;

@Info(name = "AutoFish", category = Category.MISC, description = "Automatically catch fish")
public class AutoFish extends Module
{
    @EventHandler
    private Listener<PacketEvent.Receive> receiveListener;
    
    public AutoFish() {
        this.receiveListener = new Listener<PacketEvent.Receive>(event -> {
            if (AutoFish.mc.field_71439_g != null && (AutoFish.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151112_aM || AutoFish.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_151112_aM) && event.getPacket() instanceof SPacketSoundEffect && SoundEvents.field_187609_F.equals(((SPacketSoundEffect)event.getPacket()).func_186978_a())) {
                new Thread(() -> {
                    try {
                        Thread.sleep(200L);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    AutoFish.mc.func_147121_ag();
                    try {
                        Thread.sleep(200L);
                    }
                    catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                    AutoFish.mc.func_147121_ag();
                }).start();
            }
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
    }
}
