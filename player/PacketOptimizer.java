// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.player;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import org.lwjgl.input.Mouse;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.item.ItemFood;
import net.minecraft.util.EnumHand;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import java.util.function.Predicate;
import net.minecraft.network.play.client.CPacketPlayer;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.event.events.EventPlayerPreMotionUpdate;
import meow.candycat.uwu.event.events.EventPlayerPreMotionUpdateBeforeAimbot;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import net.minecraft.network.Packet;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "PacketOptimizer", category = Category.PLAYER)
public class PacketOptimizer extends Module
{
    private Setting<Boolean> cPacketPlayer;
    private Setting<Boolean> sPacketPlayerPosLook;
    private Setting<Boolean> badSoundEffect;
    public Setting<Boolean> antiPacketKick;
    public Setting<Boolean> fastPlaceOptimizer;
    public Setting<Boolean> betterXP;
    public static PacketOptimizer INSTANCE;
    private Packet lastPacketSent;
    int tick;
    int currentSlot;
    @EventHandler
    private Listener<PacketEvent.Send> w;
    @EventHandler
    private Listener<PacketEvent.Receive> awa;
    @EventHandler
    private Listener<PacketEvent.Send> listener2;
    @EventHandler
    public Listener<EventPlayerPreMotionUpdateBeforeAimbot> listener;
    @EventHandler
    private Listener<EventPlayerPreMotionUpdate> x;
    
    public PacketOptimizer() {
        this.cPacketPlayer = this.register(Settings.b("CPacketPlayer"));
        this.sPacketPlayerPosLook = this.register(Settings.b("SPacketPlayerPosLook"));
        this.badSoundEffect = this.register(Settings.b("BadSoundEffect"));
        this.antiPacketKick = this.register(Settings.b("AntiPacketKick"));
        this.fastPlaceOptimizer = this.register(Settings.b("BetterEating"));
        this.betterXP = this.register(Settings.b("BetterXP"));
        this.currentSlot = -1;
        this.w = new Listener<PacketEvent.Send>(event -> {
            if (event.getPacket() instanceof CPacketPlayer && this.cPacketPlayer.getValue()) {
                if (event.getPacket() != this.lastPacketSent) {
                    this.lastPacketSent = event.getPacket();
                    this.tick = 0;
                }
                else if (this.tick < 20) {
                    event.cancel();
                }
            }
            return;
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
        SPacketPlayerPosLook packet;
        SPacketSoundEffect soundPacket;
        this.awa = new Listener<PacketEvent.Receive>(event -> {
            if (event.getPacket() instanceof SPacketPlayerPosLook) {
                this.tick = 0;
                if (PacketOptimizer.mc.field_71439_g != null && this.sPacketPlayerPosLook.getValue()) {
                    packet = (SPacketPlayerPosLook)event.getPacket();
                    packet.field_148936_d = PacketOptimizer.mc.field_71439_g.field_70177_z;
                    packet.field_148937_e = PacketOptimizer.mc.field_71439_g.field_70125_A;
                }
            }
            else if (event.getPacket() instanceof SPacketSoundEffect && PacketOptimizer.mc.field_71439_g != null && this.badSoundEffect.getValue()) {
                soundPacket = (SPacketSoundEffect)event.getPacket();
                if (soundPacket.func_186977_b() == SoundCategory.PLAYERS && soundPacket.func_186978_a() == SoundEvents.field_187719_p) {
                    event.cancel();
                }
            }
            return;
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
        this.listener2 = new Listener<PacketEvent.Send>(e -> {
            if (e.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && ((CPacketPlayerTryUseItemOnBlock)e.getPacket()).func_187022_c() == EnumHand.MAIN_HAND) {
                Label_0125_3: {
                    if (this.fastPlaceOptimizer.getValue()) {
                        if (this.currentSlot == -1) {
                            if (!(PacketOptimizer.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemFood)) {
                                break Label_0125_3;
                            }
                        }
                        else if (!(PacketOptimizer.mc.field_71439_g.field_71071_by.func_70301_a(this.currentSlot).func_77973_b() instanceof ItemFood)) {
                            break Label_0125_3;
                        }
                        PacketOptimizer.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                        e.cancel();
                        return;
                    }
                }
                if (this.betterXP.getValue()) {
                    if (this.currentSlot == -1) {
                        if (PacketOptimizer.mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_151062_by) {
                            return;
                        }
                    }
                    else if (PacketOptimizer.mc.field_71439_g.field_71071_by.func_70301_a(this.currentSlot).func_77973_b() != Items.field_151062_by) {
                        return;
                    }
                    e.cancel();
                }
            }
            else if (e.getPacket() instanceof CPacketHeldItemChange) {
                this.currentSlot = ((CPacketHeldItemChange)e.getPacket()).func_149614_c();
            }
            return;
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
        this.listener = new Listener<EventPlayerPreMotionUpdateBeforeAimbot>(e -> {
            if (Mouse.isButtonDown(PacketOptimizer.mc.field_71474_y.field_74313_G.func_151463_i()) && PacketOptimizer.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151062_by && this.betterXP.getValue()) {
                PacketOptimizer.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            }
            return;
        }, (Predicate<EventPlayerPreMotionUpdateBeforeAimbot>[])new Predicate[0]);
        this.x = new Listener<EventPlayerPreMotionUpdate>(event -> ++this.tick, (Predicate<EventPlayerPreMotionUpdate>[])new Predicate[0]);
        PacketOptimizer.INSTANCE = this;
    }
    
    public static boolean shouldPacketKick() {
        return !PacketOptimizer.INSTANCE.antiPacketKick.getValue();
    }
    
    @SubscribeEvent
    public void joinWorldEvent(final EntityJoinWorldEvent x) {
        if (x.getEntity() == PacketOptimizer.mc.field_71439_g) {
            this.currentSlot = -1;
        }
    }
}
