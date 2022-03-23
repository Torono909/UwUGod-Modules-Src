// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import meow.candycat.uwu.MeowUwU;
import net.minecraft.client.entity.EntityPlayerSP;
import java.util.function.Predicate;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.CPacketUseEntity;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "Criticals", description = "Crits", category = Category.COMBAT)
public class Criticals extends Module
{
    public Setting<mode> Mode;
    @EventHandler
    private Listener<PacketEvent.Send> sendListener;
    
    public Criticals() {
        this.Mode = this.register(Settings.e("Mode", mode.Packet));
        CPacketUseEntity w;
        EntityPlayerSP field_71439_g;
        this.sendListener = new Listener<PacketEvent.Send>(event -> {
            if (event.getPacket() instanceof CPacketUseEntity && Criticals.mc.field_71439_g != null && Criticals.mc.field_71441_e != null && Criticals.mc.field_71439_g.field_70122_E) {
                w = (CPacketUseEntity)event.getPacket();
                if (w.func_149564_a((World)Criticals.mc.field_71441_e) instanceof EntityLivingBase && w.func_149565_c().equals((Object)CPacketUseEntity.Action.ATTACK) && Criticals.mc.field_71439_g.field_70122_E) {
                    switch (this.Mode.getValue()) {
                        case Packet: {
                            Criticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u + 0.05, Criticals.mc.field_71439_g.field_70161_v, false));
                            Criticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u, Criticals.mc.field_71439_g.field_70161_v, false));
                            Criticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u + 0.03, Criticals.mc.field_71439_g.field_70161_v, false));
                            Criticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u, Criticals.mc.field_71439_g.field_70161_v, false));
                            break;
                        }
                        case OldPacket: {
                            Criticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u, Criticals.mc.field_71439_g.field_70161_v, true));
                            Criticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u + 0.10000000149011612, Criticals.mc.field_71439_g.field_70161_v, false));
                            Criticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u, Criticals.mc.field_71439_g.field_70161_v, false));
                            break;
                        }
                        case MiniJump: {
                            Criticals.mc.field_71439_g.func_70664_aZ();
                            field_71439_g = Criticals.mc.field_71439_g;
                            field_71439_g.field_70181_x -= 0.3;
                            break;
                        }
                        case Jump: {
                            Criticals.mc.field_71439_g.func_70664_aZ();
                            break;
                        }
                        case Offset: {
                            Criticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u + 0.0625, Criticals.mc.field_71439_g.field_70161_v, true));
                            Criticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u, Criticals.mc.field_71439_g.field_70161_v, false));
                            Criticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u + 1.1E-5, Criticals.mc.field_71439_g.field_70161_v, false));
                            Criticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u, Criticals.mc.field_71439_g.field_70161_v, false));
                            break;
                        }
                        case NCP: {
                            Criticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u + 0.11, Criticals.mc.field_71439_g.field_70161_v, false));
                            Criticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u + 0.1100013579, Criticals.mc.field_71439_g.field_70161_v, false));
                            Criticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u + 1.3579E-6, Criticals.mc.field_71439_g.field_70161_v, false));
                            break;
                        }
                        case Bypass: {
                            Criticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u + 0.11, Criticals.mc.field_71439_g.field_70161_v, false));
                            Criticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u + 0.11, Criticals.mc.field_71439_g.field_70161_v, false));
                            Criticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u + 0.1100013579, Criticals.mc.field_71439_g.field_70161_v, false));
                            Criticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u + 0.1100013579, Criticals.mc.field_71439_g.field_70161_v, false));
                            Criticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u + 0.1100013579, Criticals.mc.field_71439_g.field_70161_v, false));
                            Criticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u + 0.1100013579, Criticals.mc.field_71439_g.field_70161_v, false));
                            Criticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u, Criticals.mc.field_71439_g.field_70161_v, false));
                            break;
                        }
                    }
                }
            }
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
    }
    
    public void onEnable() {
        MeowUwU.EVENT_BUS.subscribe(this);
    }
    
    @Override
    public String getHudInfo() {
        return this.Mode.getValue().toString();
    }
    
    public void onDisable() {
        MeowUwU.EVENT_BUS.unsubscribe(this);
    }
    
    public enum mode
    {
        Packet, 
        OldPacket, 
        MiniJump, 
        Jump, 
        Offset, 
        NCP, 
        Bypass;
    }
}
