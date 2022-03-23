// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import net.minecraft.entity.Entity;
import java.util.function.Predicate;
import meow.candycat.uwu.util.Friends;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.network.play.client.CPacketUseEntity;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.module.Module;

@Info(name = "AntiFriendHit", description = "Don't hit your friends", category = Category.COMBAT)
public class AntiFriendHit extends Module
{
    @EventHandler
    private Listener<PacketEvent.Send> listener;
    
    public AntiFriendHit() {
        Entity e;
        this.listener = new Listener<PacketEvent.Send>(event -> {
            if (event.getPacket() instanceof CPacketUseEntity && ((CPacketUseEntity)event.getPacket()).func_149565_c() == CPacketUseEntity.Action.ATTACK) {
                e = ((CPacketUseEntity)event.getPacket()).func_149564_a((World)AntiFriendHit.mc.field_71441_e);
                if (e instanceof EntityPlayer && Friends.isFriend(e.func_70005_c_())) {
                    event.cancel();
                }
            }
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
    }
}
