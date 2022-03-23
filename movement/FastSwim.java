// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.movement;

import java.util.Objects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.init.MobEffects;
import net.minecraft.util.MovementInput;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.EventPlayerMove;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "FastSwim", category = Category.MOVEMENT)
public class FastSwim extends Module
{
    public Setting<mode> modeSetting;
    public Setting<Float> speed;
    @EventHandler
    private Listener<EventPlayerMove> awa;
    
    public FastSwim() {
        this.modeSetting = this.register(Settings.e("Mode", mode.NORMAL));
        this.speed = this.register(Settings.f("Speed", 1.2f));
        double moveSpeed;
        MovementInput movementInput;
        float forward;
        float strafe;
        float yaw;
        double mx;
        double mz;
        this.awa = new Listener<EventPlayerMove>(event -> {
            if (this.modeSetting.getValue() == mode.STATIC) {
                if (FastSwim.mc.field_71439_g.func_70090_H() || FastSwim.mc.field_71439_g.func_180799_ab()) {
                    event.cancel();
                    moveSpeed = this.getBaseMoveSpeed();
                    movementInput = FastSwim.mc.field_71439_g.field_71158_b;
                    forward = movementInput.field_192832_b;
                    strafe = movementInput.field_78902_a;
                    yaw = Minecraft.func_71410_x().field_71439_g.field_70177_z;
                    if (forward == 0.0f && strafe == 0.0f) {
                        event.X = 0.0;
                        event.Z = 0.0;
                    }
                    else if (forward != 0.0f) {
                        if (strafe > 0.0f) {
                            yaw += ((forward > 0.0f) ? -45 : 45);
                            strafe = 0.0f;
                        }
                        else if (strafe < 0.0f) {
                            yaw += ((forward > 0.0f) ? 45 : -45);
                            strafe = 0.0f;
                        }
                        if (forward > 0.0f) {
                            forward = 1.0f;
                        }
                        else if (forward < 0.0f) {
                            forward = -1.0f;
                        }
                    }
                    mx = Math.cos(Math.toRadians(yaw + 90.0f));
                    mz = Math.sin(Math.toRadians(yaw + 90.0f));
                    event.X = forward * moveSpeed * mx + strafe * moveSpeed * mz;
                    event.Z = forward * moveSpeed * mz - strafe * moveSpeed * mx;
                    if (forward == 0.0f && strafe == 0.0f) {
                        event.X = 0.0;
                        event.Z = 0.0;
                    }
                }
            }
            else if (FastSwim.mc.field_71439_g.func_70090_H() || FastSwim.mc.field_71439_g.func_180799_ab()) {
                event.cancel();
            }
        }, (Predicate<EventPlayerMove>[])new Predicate[0]);
    }
    
    @Override
    public void onUpdate() {
        if (this.modeSetting.getValue() == mode.NORMAL && (FastSwim.mc.field_71439_g.func_70090_H() || FastSwim.mc.field_71439_g.func_180799_ab())) {
            if (Math.sqrt(Math.pow(FastSwim.mc.field_71439_g.field_70159_w, 2.0) + Math.pow(FastSwim.mc.field_71439_g.field_70179_y, 2.0)) < this.getBaseMoveSpeed()) {
                final double moveSpeed = this.getBaseMoveSpeed();
                final MovementInput movementInput = FastSwim.mc.field_71439_g.field_71158_b;
                float forward = movementInput.field_192832_b;
                float strafe = movementInput.field_78902_a;
                float yaw = Minecraft.func_71410_x().field_71439_g.field_70177_z;
                if (forward == 0.0f && strafe == 0.0f) {
                    FastSwim.mc.field_71439_g.field_70159_w = 0.0;
                    FastSwim.mc.field_71439_g.field_70179_y = 0.0;
                }
                else if (forward != 0.0f) {
                    if (strafe > 0.0f) {
                        yaw += ((forward > 0.0f) ? -45 : 45);
                        strafe = 0.0f;
                    }
                    else if (strafe < 0.0f) {
                        yaw += ((forward > 0.0f) ? 45 : -45);
                        strafe = 0.0f;
                    }
                    if (forward > 0.0f) {
                        forward = 1.0f;
                    }
                    else if (forward < 0.0f) {
                        forward = -1.0f;
                    }
                }
                final double mx = Math.cos(Math.toRadians(yaw + 90.0f));
                final double mz = Math.sin(Math.toRadians(yaw + 90.0f));
                FastSwim.mc.field_71439_g.field_70159_w = forward * moveSpeed * mx + strafe * moveSpeed * mz;
                FastSwim.mc.field_71439_g.field_70179_y = forward * moveSpeed * mz - strafe * moveSpeed * mx;
                if (forward == 0.0f && strafe == 0.0f) {
                    FastSwim.mc.field_71439_g.field_70159_w = 0.0;
                    FastSwim.mc.field_71439_g.field_70179_y = 0.0;
                }
                return;
            }
            final double moveSpeed = Math.sqrt(Math.pow(FastSwim.mc.field_71439_g.field_70159_w, 2.0) + Math.pow(FastSwim.mc.field_71439_g.field_70179_y, 2.0)) * this.speed.getValue();
            final MovementInput movementInput = FastSwim.mc.field_71439_g.field_71158_b;
            float forward = movementInput.field_192832_b;
            float strafe = movementInput.field_78902_a;
            float yaw = Minecraft.func_71410_x().field_71439_g.field_70177_z;
            if (forward == 0.0f && strafe == 0.0f) {
                FastSwim.mc.field_71439_g.field_70159_w = 0.0;
                FastSwim.mc.field_71439_g.field_70179_y = 0.0;
            }
            else if (forward != 0.0f) {
                if (strafe > 0.0f) {
                    yaw += ((forward > 0.0f) ? -45 : 45);
                    strafe = 0.0f;
                }
                else if (strafe < 0.0f) {
                    yaw += ((forward > 0.0f) ? 45 : -45);
                    strafe = 0.0f;
                }
                if (forward > 0.0f) {
                    forward = 1.0f;
                }
                else if (forward < 0.0f) {
                    forward = -1.0f;
                }
            }
            final double mx = Math.cos(Math.toRadians(yaw + 90.0f));
            final double mz = Math.sin(Math.toRadians(yaw + 90.0f));
            FastSwim.mc.field_71439_g.field_70159_w = forward * moveSpeed * mx + strafe * moveSpeed * mz;
            FastSwim.mc.field_71439_g.field_70179_y = forward * moveSpeed * mz - strafe * moveSpeed * mx;
            if (forward == 0.0f && strafe == 0.0f) {
                FastSwim.mc.field_71439_g.field_70159_w = 0.0;
                FastSwim.mc.field_71439_g.field_70179_y = 0.0;
            }
        }
    }
    
    public double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (FastSwim.mc.field_71439_g.func_70644_a(MobEffects.field_76424_c)) {
            final int amplifier = Objects.requireNonNull(FastSwim.mc.field_71439_g.func_70660_b(MobEffects.field_76424_c)).func_76458_c() + 1;
            baseSpeed *= 1.0 + 0.2 * amplifier;
        }
        return baseSpeed;
    }
    
    public enum mode
    {
        NORMAL, 
        STATIC;
    }
}
