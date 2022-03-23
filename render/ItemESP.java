// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.render;

import net.minecraftforge.common.MinecraftForge;
import net.minecraft.network.Packet;
import java.util.function.Predicate;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketSpawnObject;
import java.util.ArrayList;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.util.ItemRenderer;
import java.util.List;
import meow.candycat.uwu.module.Module;

@Info(name = "ItemESP", category = Category.RENDER)
public class ItemESP extends Module
{
    public List<ItemRenderer> itemRendererList;
    @EventHandler
    public Listener<PacketEvent.Receive> receiveListener;
    
    public ItemESP() {
        this.itemRendererList = new ArrayList<ItemRenderer>();
        final Packet packet;
        final int[] array;
        int length;
        int i = 0;
        int n;
        this.receiveListener = new Listener<PacketEvent.Receive>(event -> {
            packet = event.getPacket();
            if (packet instanceof SPacketSpawnObject && ((SPacketSpawnObject)packet).func_148993_l() == 2) {
                this.itemRendererList.add(new ItemRenderer(((SPacketSpawnObject)packet).func_149001_c(), ((SPacketSpawnObject)packet).func_186880_c(), ((SPacketSpawnObject)packet).func_186882_d(), ((SPacketSpawnObject)packet).func_186881_e()));
                event.cancel();
            }
            else if (packet instanceof SPacketDestroyEntities) {
                ((SPacketDestroyEntities)packet).func_149098_c();
                for (length = array.length; i < length; ++i) {
                    n = array[i];
                }
            }
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
    }
    
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
}
