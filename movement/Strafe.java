// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.movement;

import java.util.Objects;
import net.minecraft.potion.PotionEffect;
import meow.candycat.uwu.module.ModuleManager;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import java.util.function.Predicate;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.event.events.EventPlayerMove;
import meow.candycat.uwu.event.events.EventPlayerJump;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.EventPlayerPreMotionUpdate;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "Strafe", category = Category.MOVEMENT)
public class Strafe extends Module
{
    private static Strafe INSTANCE;
    private Setting<mode> Mode;
    int stage;
    private double lastDist;
    private double moveSpeed;
    public boolean antiShake;
    @EventHandler
    private Listener<EventPlayerPreMotionUpdate> w;
    @EventHandler
    private Listener<EventPlayerJump> y;
    @EventHandler
    private Listener<EventPlayerMove> x;
    
    public Strafe() {
        this.Mode = this.register(Settings.e("Mode", mode.NORMAL));
        this.stage = 1;
        this.antiShake = false;
        this.w = new Listener<EventPlayerPreMotionUpdate>(event -> this.lastDist = Math.sqrt((Strafe.mc.field_71439_g.field_70165_t - Strafe.mc.field_71439_g.field_70169_q) * (Strafe.mc.field_71439_g.field_70165_t - Strafe.mc.field_71439_g.field_70169_q) + (Strafe.mc.field_71439_g.field_70161_v - Strafe.mc.field_71439_g.field_70166_s) * (Strafe.mc.field_71439_g.field_70161_v - Strafe.mc.field_71439_g.field_70166_s)), (Predicate<EventPlayerPreMotionUpdate>[])new Predicate[0]);
        this.y = new Listener<EventPlayerJump>(event -> {
            if (!this.shouldReturn() && !Strafe.mc.field_71439_g.func_70090_H() && !Strafe.mc.field_71439_g.func_180799_ab()) {
                event.cancel();
            }
            return;
        }, (Predicate<EventPlayerJump>[])new Predicate[0]);
        double motionY;
        EntityPlayerSP field_71439_g;
        final double field_70181_x;
        double minusSpeed;
        double shouldDivide;
        double forward;
        double strafe;
        double yaw;
        double shouldMulti;
        this.x = new Listener<EventPlayerMove>(event -> {
            if (!this.shouldReturn() && !Strafe.mc.field_71439_g.func_70090_H() && !Strafe.mc.field_71439_g.func_180799_ab()) {
                if (Strafe.mc.field_71439_g.field_70122_E) {
                    this.stage = 2;
                }
                switch (this.stage) {
                    case 0: {
                        ++this.stage;
                        this.lastDist = 0.0;
                        break;
                    }
                    case 2: {
                        motionY = 0.40123128;
                        if (Strafe.mc.field_71439_g.field_70122_E && Strafe.mc.field_71474_y.field_74314_A.func_151470_d()) {
                            if (Strafe.mc.field_71439_g.func_70644_a(MobEffects.field_76430_j)) {
                                motionY += (Strafe.mc.field_71439_g.func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1f;
                            }
                            field_71439_g = Strafe.mc.field_71439_g;
                            event.setY(field_71439_g.field_70181_x = field_70181_x);
                            this.moveSpeed *= (this.Mode.getValue().equals(mode.NORMAL) ? 1.67 : 2.149);
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case 3: {
                        minusSpeed = (this.Mode.getValue().equals(mode.NORMAL) ? 0.6896 : 0.795);
                        this.moveSpeed = this.lastDist - minusSpeed * (this.lastDist - this.getBaseMoveSpeed());
                        break;
                    }
                    default: {
                        if ((Strafe.mc.field_71441_e.func_184144_a((Entity)Strafe.mc.field_71439_g, Strafe.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, Strafe.mc.field_71439_g.field_70181_x, 0.0)).size() > 0 || Strafe.mc.field_71439_g.field_70124_G) && this.stage > 0) {
                            this.stage = ((Strafe.mc.field_71439_g.field_191988_bg != 0.0f || Strafe.mc.field_71439_g.field_70702_br != 0.0f) ? 1 : 0);
                        }
                        shouldDivide = (this.Mode.getValue().equals(mode.NORMAL) ? 730.0 : 159.0);
                        this.moveSpeed = this.lastDist - this.lastDist / shouldDivide;
                        break;
                    }
                }
                if (!Strafe.mc.field_71474_y.field_74314_A.func_151470_d() && Strafe.mc.field_71439_g.field_70122_E) {
                    this.moveSpeed = this.getBaseMoveSpeed();
                }
                else {
                    this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
                }
                forward = Strafe.mc.field_71439_g.field_71158_b.field_192832_b;
                strafe = Strafe.mc.field_71439_g.field_71158_b.field_78902_a;
                yaw = Strafe.mc.field_71439_g.field_70177_z;
                if (forward == 0.0 && strafe == 0.0) {
                    event.setX(0.0);
                    event.setZ(0.0);
                }
                else if (forward != 0.0 && strafe != 0.0) {
                    forward *= Math.sin(0.7853981633974483);
                    strafe *= Math.cos(0.7853981633974483);
                }
                shouldMulti = (this.Mode.getValue().equals(mode.NORMAL) ? 0.993 : 0.99);
                event.setX((forward * this.moveSpeed * -Math.sin(Math.toRadians(yaw)) + strafe * this.moveSpeed * Math.cos(Math.toRadians(yaw))) * shouldMulti);
                event.setZ((forward * this.moveSpeed * Math.cos(Math.toRadians(yaw)) - strafe * this.moveSpeed * -Math.sin(Math.toRadians(yaw))) * shouldMulti);
                ++this.stage;
                event.cancel();
            }
            return;
        }, (Predicate<EventPlayerMove>[])new Predicate[0]);
        Strafe.INSTANCE = this;
    }
    
    public static Strafe getInstance() {
        if (Strafe.INSTANCE == null) {
            Strafe.INSTANCE = new Strafe();
        }
        return Strafe.INSTANCE;
    }
    
    private boolean shouldReturn() {
        return ModuleManager.getModuleByName("Speed").isEnabled() || ModuleManager.getModuleByName("LongJump").isEnabled();
    }
    
    public void onDisable() {
        this.antiShake = false;
    }
    
    public double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (Strafe.mc.field_71439_g.func_70644_a(MobEffects.field_76424_c)) {
            final int amplifier = Objects.requireNonNull(Strafe.mc.field_71439_g.func_70660_b(MobEffects.field_76424_c)).func_76458_c() + 1;
            baseSpeed *= 1.0 + 0.2 * amplifier;
        }
        return baseSpeed;
    }
    
    @Override
    public String getHudInfo() {
        return this.Mode.getValue().equals(mode.NORMAL) ? "Normal" : "Strict";
    }
    
    public enum mode
    {
        NORMAL, 
        STRICT;
    }
}
