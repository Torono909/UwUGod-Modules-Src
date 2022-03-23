// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import java.util.function.Predicate;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.event.events.PacketEvent;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.EventPlayerMove;
import meow.candycat.eventsystem.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "NoClip", category = Category.MISC)
public class NoClip extends Module
{
    private Setting<Integer> speed;
    private double posX;
    private double posY;
    private double posZ;
    private float pitch;
    private float yaw;
    private EntityOtherPlayerMP clonedPlayer;
    private boolean isRidingEntity;
    private Entity ridingEntity;
    @EventHandler
    private Listener<EventPlayerMove> moveListener;
    @EventHandler
    private Listener<PlayerSPPushOutOfBlocksEvent> pushListener;
    @EventHandler
    private Listener<PacketEvent.Send> sendListener;
    
    public NoClip() {
        this.speed = this.register(Settings.i("Speed", 5));
        this.moveListener = new Listener<EventPlayerMove>(event -> NoClip.mc.field_71439_g.field_70145_X = true, (Predicate<EventPlayerMove>[])new Predicate[0]);
        this.pushListener = new Listener<PlayerSPPushOutOfBlocksEvent>(event -> event.setCanceled(true), (Predicate<PlayerSPPushOutOfBlocksEvent>[])new Predicate[0]);
        this.sendListener = new Listener<PacketEvent.Send>(event -> {
            if (event.getPacket() instanceof CPacketPlayer || event.getPacket() instanceof CPacketInput) {
                event.cancel();
            }
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
    }
    
    @Override
    protected void onEnable() {
        NoClip.mc.field_71439_g.field_70145_X = true;
        NoClip.mc.field_71439_g.field_70145_X = true;
        NoClip.mc.field_71439_g.field_70143_R = 0.0f;
        NoClip.mc.field_71439_g.field_70122_E = false;
        NoClip.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
        NoClip.mc.field_71439_g.field_70159_w = 0.0;
        NoClip.mc.field_71439_g.field_70181_x = 0.0;
        NoClip.mc.field_71439_g.field_70179_y = 0.0;
        final float speed = 0.2f;
        NoClip.mc.field_71439_g.field_70747_aH = 0.2f;
    }
    
    @Override
    protected void onDisable() {
        final EntityPlayer localPlayer = (EntityPlayer)NoClip.mc.field_71439_g;
        NoClip.mc.field_71439_g.field_70145_X = false;
    }
    
    @Override
    public void onUpdate() {
        NoClip.mc.field_71439_g.field_70145_X = true;
    }
}
