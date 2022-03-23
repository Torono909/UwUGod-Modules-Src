// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import java.util.function.Predicate;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.event.events.EventPlayerPreMotionUpdate;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.EventPlayerPostMotionUpdate;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "Aimbot", category = Category.COMBAT, showOnArray = ShowOnArray.OFF, alwaysListening = true)
public class Aimbot extends Module
{
    public float yaw;
    public float pitch;
    public boolean shouldRotate;
    private boolean shouldReset;
    public Setting<Boolean> shouldSmooth;
    public Setting<Float> yawRate;
    float rotationYaw;
    float rotationPitch;
    float smoothYaw;
    float smoothPitch;
    public boolean smoothRotateYaw;
    public boolean smoothRotatePitch;
    public boolean smoothRotated;
    public boolean positive;
    public boolean smoothReset;
    @EventHandler
    private Listener<EventPlayerPostMotionUpdate> w;
    @EventHandler
    private Listener<EventPlayerPreMotionUpdate> OnPlayerUpdate;
    
    public Aimbot() {
        this.shouldRotate = false;
        this.shouldReset = false;
        this.shouldSmooth = this.register(Settings.b("ShouldSmooth"));
        this.yawRate = this.register(Settings.f("YawRate", 90.0f));
        this.smoothReset = false;
        this.w = new Listener<EventPlayerPostMotionUpdate>(event -> {
            if (this.shouldReset) {
                Aimbot.mc.field_71439_g.field_70125_A = this.rotationPitch;
                Aimbot.mc.field_71439_g.field_70177_z = this.rotationYaw;
                this.shouldReset = false;
                if (this.smoothRotated && this.smoothReset) {
                    this.shouldRotate = true;
                    this.resetRotation();
                }
            }
            return;
        }, (Predicate<EventPlayerPostMotionUpdate>[])new Predicate[0]);
        float yaw;
        this.OnPlayerUpdate = new Listener<EventPlayerPreMotionUpdate>(p_Event -> {
            if (!p_Event.isCancelled()) {
                if (this.shouldRotate) {
                    this.rotationPitch = Aimbot.mc.field_71439_g.field_70125_A;
                    this.rotationYaw = Aimbot.mc.field_71439_g.field_70177_z;
                    Aimbot.mc.field_71439_g.field_70125_A = this.pitch;
                    Aimbot.mc.field_71439_g.field_70177_z = this.yaw;
                    this.positive = !this.positive;
                    this.shouldReset = true;
                    this.smoothRotated = true;
                }
                else if (this.smoothReset) {
                    this.rotationPitch = Aimbot.mc.field_71439_g.field_70125_A;
                    this.rotationYaw = Aimbot.mc.field_71439_g.field_70177_z;
                    Aimbot.mc.field_71439_g.field_70125_A = this.pitch;
                    Aimbot.mc.field_71439_g.field_70177_z = this.yaw;
                    this.positive = !this.positive;
                    this.shouldReset = true;
                    this.smoothRotated = true;
                }
                else {
                    this.rotationYaw = Aimbot.mc.field_71439_g.field_70177_z;
                    this.rotationPitch = Aimbot.mc.field_71439_g.field_70125_A;
                    for (yaw = Aimbot.mc.field_71439_g.field_70177_z; yaw < -180.0f; yaw += 360.0f) {}
                    while (yaw > 180.0f) {
                        yaw -= 360.0f;
                    }
                    Aimbot.mc.field_71439_g.field_70177_z = yaw;
                    this.shouldReset = true;
                }
            }
        }, (Predicate<EventPlayerPreMotionUpdate>[])new Predicate[0]);
    }
    
    public boolean setRotation(float yaw1, float pitch1, final boolean shouldSmooth2) {
        final boolean b = false;
        this.smoothRotatePitch = b;
        this.smoothRotateYaw = b;
        if (this.shouldSmooth.getValue() && shouldSmooth2) {
            this.yaw = Aimbot.mc.field_71439_g.field_175164_bL;
            this.pitch = Aimbot.mc.field_71439_g.field_175165_bM;
            while (yaw1 < -180.0f) {
                yaw1 += 360.0f;
            }
            while (yaw1 > 180.0f) {
                yaw1 -= 360.0f;
            }
            while (this.yaw < -180.0f) {
                this.yaw += 360.0f;
            }
            while (this.yaw > 180.0f) {
                this.yaw -= 360.0f;
            }
            final double a = calculateDirectionDifference(yaw1, this.yaw);
            if (a > this.yawRate.getValue()) {
                yaw1 = this.setSmoothRotationYaw(yaw1, this.yaw);
                this.smoothRotated = false;
            }
            if (Math.abs(pitch1 - this.pitch) > 89.0f) {
                pitch1 = (pitch1 + this.pitch) / 2.0f;
                this.smoothRotatePitch = true;
                this.smoothRotated = false;
            }
        }
        while (yaw1 < -180.0f) {
            yaw1 += 360.0f;
        }
        while (yaw1 > 180.0f) {
            yaw1 -= 360.0f;
        }
        this.yaw = yaw1;
        this.pitch = pitch1;
        this.shouldRotate = true;
        this.smoothReset = false;
        return !this.smoothRotatePitch && !this.smoothRotateYaw;
    }
    
    public void setRotation(final float yaw1, final float pitch1) {
        this.setRotation(yaw1, pitch1, false);
    }
    
    public float setSmoothRotationYaw(float yaw1, float yaw) {
        this.smoothRotateYaw = true;
        this.smoothYaw = yaw1;
        while (yaw1 < -180.0f) {
            yaw1 += 360.0f;
        }
        while (yaw1 > 180.0f) {
            yaw1 -= 360.0f;
        }
        while (yaw < -180.0f) {
            yaw += 360.0f;
        }
        while (yaw > 180.0f) {
            yaw -= 360.0f;
        }
        float diff = yaw1 - yaw;
        final boolean left = diff > 180.0f || diff > -180.0f;
        diff = calculateDirectionDifference(yaw1, yaw);
        final float v = this.yawRate.getValue() - diff;
        if (left) {
            yaw1 -= v;
        }
        else {
            yaw1 += v;
        }
        return yaw1;
    }
    
    public static float calculateDirectionDifference(final float alpha, final float beta) {
        final float phi = Math.abs(beta - alpha) % 360.0f;
        return (phi > 180.0f) ? (360.0f - phi) : phi;
    }
    
    public void resetRotation() {
        if (!this.shouldRotate) {
            final float field_70177_z = Aimbot.mc.field_71439_g.field_70177_z;
            this.smoothYaw = field_70177_z;
            this.yaw = field_70177_z;
            final float field_70125_A = Aimbot.mc.field_71439_g.field_70125_A;
            this.smoothPitch = field_70125_A;
            this.pitch = field_70125_A;
            final boolean b = false;
            this.smoothRotateYaw = b;
            this.smoothRotatePitch = b;
            this.smoothRotated = true;
        }
        else {
            this.smoothRotated = true;
            float yaw1 = Aimbot.mc.field_71439_g.field_70177_z;
            float pitch1 = Aimbot.mc.field_71439_g.field_70125_A;
            this.yaw = Aimbot.mc.field_71439_g.field_175164_bL;
            this.pitch = Aimbot.mc.field_71439_g.field_175165_bM;
            while (yaw1 < -180.0f) {
                yaw1 += 360.0f;
            }
            while (yaw1 > 180.0f) {
                yaw1 -= 360.0f;
            }
            while (this.yaw < -180.0f) {
                this.yaw += 360.0f;
            }
            while (this.yaw > 180.0f) {
                this.yaw -= 360.0f;
            }
            final double a = calculateDirectionDifference(yaw1, this.yaw);
            if (a > this.yawRate.getValue()) {
                yaw1 = this.setSmoothRotationYaw(yaw1, this.yaw);
                this.smoothRotated = false;
            }
            if (Math.abs(pitch1 - this.pitch) > 85.0f) {
                pitch1 = (pitch1 + this.pitch) / 2.0f;
                this.smoothRotatePitch = true;
                this.smoothRotated = false;
            }
            while (yaw1 < -180.0f) {
                yaw1 += 360.0f;
            }
            while (yaw1 > 180.0f) {
                yaw1 -= 360.0f;
            }
            this.shouldRotate = false;
            if (!this.smoothRotated) {
                this.yaw = yaw1;
                this.pitch = pitch1;
                this.smoothReset = true;
            }
            else {
                final float field_70177_z2 = Aimbot.mc.field_71439_g.field_70177_z;
                this.smoothYaw = field_70177_z2;
                this.yaw = field_70177_z2;
                final float field_70125_A2 = Aimbot.mc.field_71439_g.field_70125_A;
                this.smoothPitch = field_70125_A2;
                this.pitch = field_70125_A2;
                final boolean b2 = false;
                this.smoothRotateYaw = b2;
                this.smoothRotatePitch = b2;
                this.smoothRotated = true;
            }
        }
    }
}
