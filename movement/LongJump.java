// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.movement;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import meow.candycat.uwu.module.ModuleManager;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import java.util.function.Predicate;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.event.events.EventPlayerMove;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.EventPlayerPreMotionUpdate;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "LongJump", category = Category.MOVEMENT)
public class LongJump extends Module
{
    boolean speedTick;
    boolean shouldToggleOnStrafe;
    boolean shouldToggleOff;
    boolean mustToggle;
    double startY;
    public static long lastVelocityTime;
    boolean jumped;
    int cooldown;
    public boolean falldmg;
    private double lastDist;
    public Setting<Double> speed;
    public Setting<mode> Mode;
    @EventHandler
    private Listener<EventPlayerPreMotionUpdate> w;
    @EventHandler
    private Listener<PacketEvent.Receive> posLookReceiver;
    @EventHandler
    private Listener<EventPlayerMove> onAirControl;
    
    public LongJump() {
        this.shouldToggleOnStrafe = false;
        this.shouldToggleOff = false;
        this.mustToggle = false;
        this.jumped = false;
        this.cooldown = 0;
        this.falldmg = false;
        this.speed = this.register(Settings.d("Speed", 1.261));
        this.Mode = this.register(Settings.e("Mode", mode.NORMAL));
        this.w = new Listener<EventPlayerPreMotionUpdate>(event -> this.lastDist = Math.sqrt((LongJump.mc.field_71439_g.field_70165_t - LongJump.mc.field_71439_g.field_70169_q) * (LongJump.mc.field_71439_g.field_70165_t - LongJump.mc.field_71439_g.field_70169_q) + (LongJump.mc.field_71439_g.field_70161_v - LongJump.mc.field_71439_g.field_70166_s) * (LongJump.mc.field_71439_g.field_70161_v - LongJump.mc.field_71439_g.field_70166_s)), (Predicate<EventPlayerPreMotionUpdate>[])new Predicate[0]);
        this.posLookReceiver = new Listener<PacketEvent.Receive>(event -> {
            if (event.getPacket() instanceof SPacketPlayerPosLook) {
                this.mustToggle = true;
            }
            return;
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
        double moveSpeed;
        double forward;
        double strafe;
        double yaw;
        EntityPlayerSP field_71439_g;
        double field_70159_w;
        EntityPlayerSP field_71439_g2;
        double field_70179_y;
        EntityPlayerSP field_71439_g3;
        final double field_70159_w2;
        EntityPlayerSP field_71439_g4;
        final double field_70179_y2;
        this.onAirControl = new Listener<EventPlayerMove>(event -> {
            if (this.shouldToggleOff && !LongJump.mc.field_71439_g.field_70122_E && !ModuleManager.getModuleByName("Speed").isEnabled() && !event.isCancelled() && this.cooldown == 0) {
                moveSpeed = Math.max(this.lastDist - this.lastDist / 159.0, Math.sqrt(event.X * event.X + event.Z * event.Z));
                forward = LongJump.mc.field_71439_g.field_71158_b.field_192832_b;
                strafe = LongJump.mc.field_71439_g.field_71158_b.field_78902_a;
                yaw = LongJump.mc.field_71439_g.field_70177_z;
                if (forward == 0.0 && strafe == 0.0) {
                    event.setX(0.0);
                    event.setZ(0.0);
                }
                else if (forward != 0.0 && strafe != 0.0) {
                    forward *= Math.sin(0.7853981633974483);
                    strafe *= Math.cos(0.7853981633974483);
                }
                if (event.Y < 0.0) {
                    event.Y += 0.015;
                }
                LongJump.mc.field_71439_g.field_70181_x = event.Y;
                field_71439_g = LongJump.mc.field_71439_g;
                field_70159_w = forward * moveSpeed * -Math.sin(Math.toRadians(yaw)) + strafe * moveSpeed * Math.cos(Math.toRadians(yaw));
                event.setX(field_71439_g.field_70159_w = field_70159_w);
                field_71439_g2 = LongJump.mc.field_71439_g;
                field_70179_y = forward * moveSpeed * Math.cos(Math.toRadians(yaw)) - strafe * moveSpeed * -Math.sin(Math.toRadians(yaw));
                event.setZ(field_71439_g2.field_70179_y = field_70179_y);
                event.cancel();
            }
            else if (this.cooldown > 0) {
                field_71439_g3 = LongJump.mc.field_71439_g;
                event.setX(field_71439_g3.field_70159_w = field_70159_w2);
                field_71439_g4 = LongJump.mc.field_71439_g;
                event.setZ(field_71439_g4.field_70179_y = field_70179_y2);
            }
        }, (Predicate<EventPlayerMove>[])new Predicate[0]);
    }
    
    public void onEnable() {
        if (ModuleManager.getModuleByName("Speed").isEnabled()) {
            ModuleManager.getModuleByName("Speed").disable();
            this.shouldToggleOnStrafe = true;
        }
        this.falldmg = false;
        this.mustToggle = false;
        this.shouldToggleOff = false;
        this.speedTick = false;
        this.jumped = false;
    }
    
    @Override
    public void onUpdate() {
        if (this.isDisabled()) {
            return;
        }
        if (this.mustToggle) {
            this.mustToggle = false;
            this.toggle();
            return;
        }
        if (!this.shouldToggleOff || !LongJump.mc.field_71439_g.field_70122_E) {
            if ((LongJump.mc.field_71474_y.field_74351_w.func_151470_d() || LongJump.mc.field_71474_y.field_74370_x.func_151470_d() || LongJump.mc.field_71474_y.field_74366_z.func_151470_d() || LongJump.mc.field_71474_y.field_74368_y.func_151470_d()) && this.canSpeed() && !this.shouldToggleOff) {
                if (LongJump.mc.field_71439_g.field_184841_cd) {
                    if (this.Mode.getValue() == mode.DAMAGE && !this.falldmg) {
                        for (int i = 0; i < 10; ++i) {
                            LongJump.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(LongJump.mc.field_71439_g.field_70165_t, LongJump.mc.field_71439_g.field_70163_u, LongJump.mc.field_71439_g.field_70161_v, true));
                        }
                        for (double fallDistance = 3.0; fallDistance > 0.0; fallDistance -= 0.0624986421) {
                            LongJump.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(LongJump.mc.field_71439_g.field_70165_t, LongJump.mc.field_71439_g.field_70163_u + 0.0624986421, LongJump.mc.field_71439_g.field_70161_v, false));
                            LongJump.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(LongJump.mc.field_71439_g.field_70165_t, LongJump.mc.field_71439_g.field_70163_u + 0.625, LongJump.mc.field_71439_g.field_70161_v, false));
                            LongJump.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(LongJump.mc.field_71439_g.field_70165_t, LongJump.mc.field_71439_g.field_70163_u + 0.0624986421, LongJump.mc.field_71439_g.field_70161_v, false));
                            LongJump.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(LongJump.mc.field_71439_g.field_70165_t, LongJump.mc.field_71439_g.field_70163_u + 1.3579E-6, LongJump.mc.field_71439_g.field_70161_v, false));
                        }
                        LongJump.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(LongJump.mc.field_71439_g.field_70165_t, LongJump.mc.field_71439_g.field_70163_u, LongJump.mc.field_71439_g.field_70161_v, true));
                        this.falldmg = true;
                        return;
                    }
                    this.startY = LongJump.mc.field_71439_g.field_70163_u;
                    LongJump.mc.field_71439_g.func_70664_aZ();
                    this.jumped = true;
                }
                this.cooldown = 0;
                final float direction = LongJump.mc.field_71439_g.field_70177_z + ((LongJump.mc.field_71439_g.field_191988_bg < 0.0f) ? 180 : 0) + ((LongJump.mc.field_71439_g.field_70702_br > 0.0f) ? (-90.0f * ((LongJump.mc.field_71439_g.field_191988_bg < 0.0f) ? -0.5f : ((LongJump.mc.field_71439_g.field_191988_bg > 0.0f) ? 0.5f : 1.0f))) : 0.0f) - ((LongJump.mc.field_71439_g.field_70702_br < 0.0f) ? (-90.0f * ((LongJump.mc.field_71439_g.field_191988_bg < 0.0f) ? -0.5f : ((LongJump.mc.field_71439_g.field_191988_bg > 0.0f) ? 0.5f : 1.0f))) : 0.0f);
                final float xDir = (float)Math.cos((direction + 90.0f) * 3.141592653589793 / 180.0);
                final float zDir = (float)Math.sin((direction + 90.0f) * 3.141592653589793 / 180.0);
                if (LongJump.mc.field_71439_g.field_70181_x == 0.33319999363422365 && (LongJump.mc.field_71474_y.field_74351_w.func_151470_d() || LongJump.mc.field_71474_y.field_74370_x.func_151470_d() || LongJump.mc.field_71474_y.field_74366_z.func_151470_d() || LongJump.mc.field_71474_y.field_74368_y.func_151470_d()) && this.jumped) {
                    LongJump.mc.field_71439_g.field_70159_w = xDir * this.speed.getValue();
                    LongJump.mc.field_71439_g.field_70179_y = zDir * this.speed.getValue();
                    this.shouldToggleOff = true;
                }
            }
            return;
        }
        if (this.cooldown < 3) {
            ++this.cooldown;
            return;
        }
        this.cooldown = 0;
        this.disable();
        this.speedTick = false;
    }
    
    public void onDisable() {
        if (this.shouldToggleOnStrafe) {
            ModuleManager.getModuleByName("Speed").toggle();
            this.shouldToggleOnStrafe = false;
        }
        this.speedTick = false;
        this.shouldToggleOff = false;
    }
    
    @Override
    public String getHudInfo() {
        return (this.Mode.getValue() == mode.DAMAGE) ? "Damage" : "Normal";
    }
    
    private boolean canSpeed() {
        return !LongJump.mc.field_71439_g.func_70617_f_() && !LongJump.mc.field_71439_g.func_70090_H() && !LongJump.mc.field_71439_g.func_70093_af();
    }
    
    private enum mode
    {
        NORMAL, 
        DAMAGE;
    }
}
