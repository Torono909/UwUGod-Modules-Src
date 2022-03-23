// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.player;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import java.util.function.Predicate;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import java.util.ArrayList;
import meow.candycat.uwu.setting.Settings;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import net.minecraft.network.Packet;
import java.util.List;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "Blink", category = Category.PLAYER, description = "Cancels server side packets")
public class Blink extends Module
{
    public Setting<modes> mode;
    List<Packet> packets;
    @EventHandler
    public Listener<PacketEvent.Send> listener;
    private EntityOtherPlayerMP clonedPlayer;
    
    public Blink() {
        this.mode = this.register(Settings.e("Mode", modes.All));
        this.packets = new ArrayList<Packet>();
        this.listener = new Listener<PacketEvent.Send>(event -> {
            if (this.isEnabled() && ((this.mode.getValue() == modes.All && !(event.getPacket() instanceof CPacketPlayerTryUseItem)) || event.getPacket() instanceof CPacketPlayer)) {
                event.cancel();
                this.packets.add(event.getPacket());
            }
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
    }
    
    @Override
    protected void onEnable() {
        if (Blink.mc.field_71439_g != null) {
            (this.clonedPlayer = new EntityOtherPlayerMP((World)Blink.mc.field_71441_e, Blink.mc.func_110432_I().func_148256_e())).func_82149_j((Entity)Blink.mc.field_71439_g);
            this.clonedPlayer.field_70759_as = Blink.mc.field_71439_g.field_70759_as;
            Blink.mc.field_71441_e.func_73027_a(-100, (Entity)this.clonedPlayer);
        }
    }
    
    @Override
    protected void onDisable() {
        try {
            for (final Packet packet : this.packets) {
                Blink.mc.field_71439_g.field_71174_a.func_147297_a(packet);
            }
        }
        catch (Exception ex) {}
        this.packets.clear();
        final EntityPlayer localPlayer = (EntityPlayer)Blink.mc.field_71439_g;
        if (localPlayer != null) {
            try {
                Blink.mc.field_71441_e.func_73028_b(-100);
            }
            catch (Exception ex2) {}
            this.clonedPlayer = null;
        }
    }
    
    @Override
    public String getHudInfo() {
        return String.valueOf(this.packets.size());
    }
    
    @SubscribeEvent
    public void joinWorldEvent(final EntityJoinWorldEvent x) {
        if (x.getEntity() == Blink.mc.field_71439_g) {
            this.disable();
        }
    }
    
    public enum modes
    {
        All, 
        CPacketPlayer;
    }
}
