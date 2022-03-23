// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraft.util.StringUtils;
import java.util.Iterator;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketSpawnPlayer;
import java.util.function.Predicate;
import net.minecraft.entity.Entity;
import java.util.Collection;
import net.minecraft.entity.player.EntityPlayer;
import java.util.ArrayList;
import java.util.HashMap;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.EventPlayerUpdate;
import meow.candycat.eventsystem.listener.Listener;
import java.util.UUID;
import java.util.Map;
import meow.candycat.uwu.module.Module;

@Info(name = "AntiBot", category = Category.COMBAT)
public class AntiBot extends Module
{
    private Map<Integer, UUID> _playersMap;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private Listener<PacketEvent.Receive> onPacketEvent;
    
    public AntiBot() {
        this._playersMap = new HashMap<Integer, UUID>();
        final Iterator<EntityPlayer> iterator;
        EntityPlayer player;
        this.onPlayerUpdate = new Listener<EventPlayerUpdate>(event -> {
            if (AntiBot.mc.func_147104_D() == null) {
                return;
            }
            else {
                new ArrayList<EntityPlayer>(AntiBot.mc.field_71441_e.field_73010_i).iterator();
                while (iterator.hasNext()) {
                    player = iterator.next();
                    if (this.isBot(player)) {
                        AntiBot.mc.field_71441_e.func_72900_e((Entity)player);
                    }
                }
                return;
            }
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
        SPacketSpawnPlayer packet;
        SPacketDestroyEntities packet2;
        final int[] array;
        int length;
        int i = 0;
        int e;
        this.onPacketEvent = new Listener<PacketEvent.Receive>(event -> {
            if (AntiBot.mc.field_71441_e != null && AntiBot.mc.field_71439_g != null) {
                if (event.getPacket() instanceof SPacketSpawnPlayer) {
                    packet = (SPacketSpawnPlayer)event.getPacket();
                    if (Math.sqrt((AntiBot.mc.field_71439_g.field_70165_t - packet.func_186898_d() / 0.0) * (AntiBot.mc.field_71439_g.field_70165_t - packet.func_186898_d() / 0.0) + (AntiBot.mc.field_71439_g.field_70163_u - packet.func_186897_e() / 0.0) * (AntiBot.mc.field_71439_g.field_70163_u - packet.func_186897_e() / 0.0) + (AntiBot.mc.field_71439_g.field_70161_v - packet.func_186899_f() / 0.0) * (AntiBot.mc.field_71439_g.field_70161_v - packet.func_186899_f() / 0.0)) <= 0.0 && packet.func_186898_d() / 0.0 != AntiBot.mc.field_71439_g.field_70165_t && packet.func_186897_e() / 0.0 != AntiBot.mc.field_71439_g.field_70163_u && packet.func_186899_f() / 0.0 != AntiBot.mc.field_71439_g.field_70161_v) {
                        this._playersMap.put(packet.func_148943_d(), packet.func_179819_c());
                    }
                }
                else if (event.getPacket() instanceof SPacketDestroyEntities) {
                    packet2 = (SPacketDestroyEntities)event.getPacket();
                    packet2.func_149098_c();
                    for (length = array.length; i < length; ++i) {
                        e = array[i];
                        if (this._playersMap.containsKey(e)) {
                            this._playersMap.remove(e);
                        }
                    }
                }
            }
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
    }
    
    public boolean isBot(final EntityPlayer entity) {
        return entity.func_110124_au().toString().startsWith(entity.func_70005_c_()) || !StringUtils.func_76338_a(entity.func_146103_bH().getName()).equals(entity.func_70005_c_()) || entity.func_146103_bH().getId() != entity.func_110124_au() || this._playersMap.containsKey(entity.func_145782_y());
    }
    
    @SubscribeEvent
    public void joinWorldEvent(final EntityJoinWorldEvent x) {
        if (x.getEntity() == AntiBot.mc.field_71439_g) {
            this._playersMap.clear();
        }
    }
}
