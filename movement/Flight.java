// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.client.Minecraft;
import meow.candycat.uwu.util.EntityUtil;
import net.minecraft.network.play.client.CPacketPlayer;
import java.util.function.Predicate;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.event.events.EventPlayerPostMotionUpdate;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.EventPlayerPreMotionUpdate;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(category = Category.MOVEMENT, description = "Makes the player fly", name = "Flight")
public class Flight extends Module
{
    private Setting<Float> speed;
    private Setting<Double> glide;
    private Setting<FlightMode> mode;
    private Setting<Boolean> antikick;
    private Setting<Boolean> antifd;
    private boolean lowered;
    @EventHandler
    private Listener<EventPlayerPreMotionUpdate> w;
    @EventHandler
    private Listener<PacketEvent.Send> y;
    @EventHandler
    private Listener<EventPlayerPostMotionUpdate> x;
    
    public Flight() {
        this.speed = this.register(Settings.f("Speed", 1.0f));
        this.glide = this.register(Settings.d("glidespeed", 1.0));
        this.mode = this.register(Settings.e("Mode", FlightMode.VANILLA));
        this.antikick = this.register(Settings.b("AntiKick"));
        this.antifd = this.register(Settings.b("AntiFallDamage"));
        this.lowered = false;
        this.w = new Listener<EventPlayerPreMotionUpdate>(event -> {
            if (this.antikick.getValue()) {
                if (this.lowered) {
                    Flight.mc.field_71439_g.func_70107_b(Flight.mc.field_71439_g.field_70165_t, Flight.mc.field_71439_g.field_70163_u + 0.10000000149011612, Flight.mc.field_71439_g.field_70161_v);
                    this.lowered = false;
                }
                else {
                    Flight.mc.field_71439_g.func_70107_b(Flight.mc.field_71439_g.field_70165_t, Flight.mc.field_71439_g.field_70163_u - 0.10000000149011612, Flight.mc.field_71439_g.field_70161_v);
                    this.lowered = true;
                }
            }
            return;
        }, (Predicate<EventPlayerPreMotionUpdate>[])new Predicate[0]);
        this.y = new Listener<PacketEvent.Send>(event -> {
            if (event.getPacket() instanceof CPacketPlayer) {
                ((CPacketPlayer)event.getPacket()).field_149474_g = true;
            }
            return;
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
        this.x = new Listener<EventPlayerPostMotionUpdate>(event -> {
            if (this.antikick.getValue()) {
                if (this.lowered) {
                    Flight.mc.field_71439_g.func_70107_b(Flight.mc.field_71439_g.field_70165_t, Flight.mc.field_71439_g.field_70163_u + 0.10000000149011612, Flight.mc.field_71439_g.field_70161_v);
                }
                else {
                    Flight.mc.field_71439_g.func_70107_b(Flight.mc.field_71439_g.field_70165_t, Flight.mc.field_71439_g.field_70163_u - 0.10000000149011612, Flight.mc.field_71439_g.field_70161_v);
                }
            }
        }, (Predicate<EventPlayerPostMotionUpdate>[])new Predicate[0]);
    }
    
    @Override
    protected void onEnable() {
        if (Flight.mc.field_71439_g == null) {
            return;
        }
        switch (this.mode.getValue()) {
            case VANILLA: {
                Flight.mc.field_71439_g.field_71075_bZ.field_75100_b = true;
                if (Flight.mc.field_71439_g.field_71075_bZ.field_75098_d) {
                    return;
                }
                Flight.mc.field_71439_g.field_71075_bZ.field_75101_c = true;
                break;
            }
        }
    }
    
    @Override
    public void onUpdate() {
        switch (this.mode.getValue()) {
            case STATIC: {
                Flight.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
                Flight.mc.field_71439_g.field_70159_w = 0.0;
                Flight.mc.field_71439_g.field_70181_x = this.glide.getValue() / 10.0;
                Flight.mc.field_71439_g.field_70179_y = 0.0;
                Flight.mc.field_71439_g.field_70747_aH = this.speed.getValue();
                if (Flight.mc.field_71474_y.field_74314_A.func_151470_d()) {
                    final EntityPlayerSP field_71439_g = Flight.mc.field_71439_g;
                    field_71439_g.field_70181_x += this.speed.getValue();
                }
                if (Flight.mc.field_71474_y.field_74311_E.func_151470_d()) {
                    final EntityPlayerSP field_71439_g2 = Flight.mc.field_71439_g;
                    field_71439_g2.field_70181_x -= this.speed.getValue();
                    break;
                }
                break;
            }
            case VANILLA: {
                Flight.mc.field_71439_g.field_71075_bZ.func_75092_a(this.speed.getValue() / 100.0f);
                double forward1 = Flight.mc.field_71439_g.field_71158_b.field_192832_b;
                double strafe = Flight.mc.field_71439_g.field_71158_b.field_78902_a;
                final double yaw = Flight.mc.field_71439_g.field_70177_z;
                if (forward1 == 0.0 && strafe == 0.0) {
                    Flight.mc.field_71439_g.field_70159_w = 0.0;
                    Flight.mc.field_71439_g.field_70179_y = 0.0;
                }
                else if (forward1 != 0.0 && strafe != 0.0) {
                    forward1 *= Math.sin(0.7853981633974483);
                    strafe *= Math.cos(0.7853981633974483);
                }
                Flight.mc.field_71439_g.field_70159_w = (forward1 * this.speed.getValue() * -Math.sin(Math.toRadians(yaw)) + strafe * this.speed.getValue() * Math.cos(Math.toRadians(yaw))) * 0.99;
                Flight.mc.field_71439_g.field_70179_y = (forward1 * this.speed.getValue() * Math.cos(Math.toRadians(yaw)) - strafe * this.speed.getValue() * -Math.sin(Math.toRadians(yaw))) * 0.99;
                if (Flight.mc.field_71474_y.field_74314_A.func_151470_d()) {
                    Flight.mc.field_71439_g.field_70181_x = 1.0;
                }
                if (Flight.mc.field_71474_y.field_74311_E.func_151470_d()) {
                    Flight.mc.field_71439_g.field_70181_x = -1.0;
                }
                Flight.mc.field_71439_g.field_71075_bZ.field_75100_b = true;
                if (Flight.mc.field_71439_g.field_71075_bZ.field_75098_d) {
                    return;
                }
                Flight.mc.field_71439_g.field_71075_bZ.field_75101_c = true;
                break;
            }
            case PACKET: {
                final boolean forward2 = Flight.mc.field_71474_y.field_74351_w.func_151470_d();
                final boolean left = Flight.mc.field_71474_y.field_74370_x.func_151470_d();
                final boolean right = Flight.mc.field_71474_y.field_74366_z.func_151470_d();
                final boolean back = Flight.mc.field_71474_y.field_74368_y.func_151470_d();
                int angle;
                if (left && right) {
                    angle = (forward2 ? 0 : (back ? 180 : -1));
                }
                else if (forward2 && back) {
                    angle = (left ? -90 : (right ? 90 : -1));
                }
                else {
                    angle = (left ? -90 : (right ? 90 : 0));
                    if (forward2) {
                        angle /= 2;
                    }
                    else if (back) {
                        angle = 180 - angle / 2;
                    }
                }
                if (angle != -1 && (forward2 || left || right || back)) {
                    final float yaw2 = Flight.mc.field_71439_g.field_70177_z + angle;
                    Flight.mc.field_71439_g.field_70159_w = EntityUtil.getRelativeX(yaw2) * 0.20000000298023224;
                    Flight.mc.field_71439_g.field_70179_y = EntityUtil.getRelativeZ(yaw2) * 0.20000000298023224;
                }
                Flight.mc.field_71439_g.field_70181_x = 0.0;
                Flight.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.PositionRotation(Flight.mc.field_71439_g.field_70165_t + Flight.mc.field_71439_g.field_70159_w, Flight.mc.field_71439_g.field_70163_u + (Minecraft.func_71410_x().field_71474_y.field_74314_A.func_151470_d() ? 0.0622 : 0.0) - (Minecraft.func_71410_x().field_71474_y.field_74311_E.func_151470_d() ? 0.0622 : 0.0), Flight.mc.field_71439_g.field_70161_v + Flight.mc.field_71439_g.field_70179_y, Flight.mc.field_71439_g.field_70177_z, Flight.mc.field_71439_g.field_70125_A, false));
                Flight.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.PositionRotation(Flight.mc.field_71439_g.field_70165_t + Flight.mc.field_71439_g.field_70159_w, Flight.mc.field_71439_g.field_70163_u - 42069.0, Flight.mc.field_71439_g.field_70161_v + Flight.mc.field_71439_g.field_70179_y, Flight.mc.field_71439_g.field_70177_z, Flight.mc.field_71439_g.field_70125_A, true));
                break;
            }
        }
    }
    
    @Override
    protected void onDisable() {
        switch (this.mode.getValue()) {
            case VANILLA: {
                Flight.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
                Flight.mc.field_71439_g.field_71075_bZ.func_75092_a(0.05f);
                if (Flight.mc.field_71439_g.field_71075_bZ.field_75098_d) {
                    return;
                }
                Flight.mc.field_71439_g.field_71075_bZ.field_75101_c = false;
                break;
            }
        }
    }
    
    public double[] moveLooking() {
        return new double[] { Flight.mc.field_71439_g.field_70177_z * 360.0f / 360.0f * 180.0f / 180.0f, 0.0 };
    }
    
    @Override
    public String getHudInfo() {
        return (this.mode.getValue() == FlightMode.VANILLA) ? "Vanilla" : ((this.mode.getValue() == FlightMode.STATIC) ? "Static" : "Packet");
    }
    
    public enum FlightMode
    {
        VANILLA, 
        STATIC, 
        PACKET;
    }
}
