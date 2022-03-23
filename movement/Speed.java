// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.movement;

import java.math.RoundingMode;
import java.math.BigDecimal;
import java.util.Objects;
import net.minecraft.potion.PotionEffect;
import meow.candycat.uwu.util.LagCompensator;
import meow.candycat.uwu.module.ModuleManager;
import java.util.List;
import net.minecraft.util.MovementInput;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import java.util.function.Predicate;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.event.events.EventPlayerMove;
import meow.candycat.uwu.event.events.EventPlayerPreMotionUpdate;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "Speed", category = Category.MOVEMENT)
public class Speed extends Module
{
    public final Setting<Mode> mode;
    private final Setting<Boolean> limiter;
    public Setting<Boolean> useTimer;
    public Setting<Boolean> tpsSync;
    private final Setting<Boolean> strict;
    private Setting<Double> onGroundSpeed;
    private Setting<Double> onAirSpeed;
    private final Setting<Boolean> potion;
    private Setting<Float> stepHeight;
    private int stage;
    private double moveSpeed;
    private boolean speedTick;
    private int level;
    private double lastDist;
    int tickPassed;
    @EventHandler
    private Listener<PacketEvent.Receive> posLookReceiver;
    @EventHandler
    private Listener<EventPlayerPreMotionUpdate> w;
    double jumpHeight;
    @EventHandler
    private Listener<EventPlayerMove> owo;
    
    public Speed() {
        this.mode = this.register(Settings.e("Mode", Mode.SLOWHOP));
        this.limiter = this.register(Settings.b("JumpCooldown"));
        this.useTimer = this.register(Settings.b("UseTimer"));
        this.tpsSync = this.register(Settings.booleanBuilder("TpsSync").withValue(false).withVisibility(v -> this.useTimer.getValue()).build());
        this.strict = this.register(Settings.booleanBuilder("Strict").withValue(false).withVisibility(v -> this.mode.getValue() == Mode.NCPHOP).build());
        this.onGroundSpeed = this.register(Settings.doubleBuilder("OnGroundSpeed").withValue(1.733).withVisibility(v -> this.mode.getValue() == Mode.MINIHOP).build());
        this.onAirSpeed = this.register(Settings.doubleBuilder("OnAirSpeed").withValue(1.14).withVisibility(v -> this.mode.getValue() == Mode.MINIHOP).build());
        this.potion = this.register(Settings.b("Potion", true));
        this.stepHeight = this.register(Settings.floatBuilder("StepHeight").withMinimum(0.0f).withValue(2.0f).withMaximum(3.0f).withVisibility(v -> this.mode.getValue() == Mode.LOWHOP || this.mode.getValue() == Mode.MINIHOP).build());
        this.stage = 1;
        this.level = 1;
        this.tickPassed = 0;
        this.posLookReceiver = new Listener<PacketEvent.Receive>(event -> {
            if (event.getPacket() instanceof SPacketPlayerPosLook) {
                this.lastDist = 0.0;
                this.moveSpeed = this.getBaseMoveSpeed();
                this.stage = 2;
            }
            return;
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
        this.w = new Listener<EventPlayerPreMotionUpdate>(event -> this.lastDist = Math.sqrt((Speed.mc.field_71439_g.field_70165_t - Speed.mc.field_71439_g.field_70169_q) * (Speed.mc.field_71439_g.field_70165_t - Speed.mc.field_71439_g.field_70169_q) + (Speed.mc.field_71439_g.field_70161_v - Speed.mc.field_71439_g.field_70166_s) * (Speed.mc.field_71439_g.field_70161_v - Speed.mc.field_71439_g.field_70166_s)), (Predicate<EventPlayerPreMotionUpdate>[])new Predicate[0]);
        double motionY;
        EntityPlayerSP field_71439_g;
        final double field_70181_x;
        double minusSpeed;
        double shouldDivide;
        double forward;
        double strafe;
        double yaw;
        double shouldMulti;
        MovementInput movementInput;
        float forward2;
        float strafe2;
        float yaw2;
        double mx;
        double mz;
        EntityPlayerSP field_71439_g2;
        final double n;
        EntityPlayerSP field_71439_g3;
        final double n2;
        EntityPlayerSP field_71439_g4;
        final double n3;
        EntityPlayerSP field_71439_g5;
        final double n4;
        EntityPlayerSP field_71439_g6;
        final double n5;
        double difference;
        List collidingList;
        EntityPlayerSP field_71439_g7;
        EntityPlayerSP field_71439_g8;
        double difference2;
        MovementInput movementInput2;
        float forward3;
        float strafe3;
        float yaw3;
        double mx2;
        double mz2;
        MovementInput movementInput3;
        float forward4;
        float strafe4;
        float yaw4;
        double mx3;
        double mz3;
        EntityPlayerSP field_71439_g9;
        final double n6;
        List collidingList2;
        this.owo = new Listener<EventPlayerMove>(event -> {
            if (!this.shouldReturn()) {
                if (this.mode.getValue() == Mode.LOWHOP && Speed.mc.field_71439_g.field_70122_E) {
                    Speed.mc.field_71439_g.field_70138_W = this.stepHeight.getValue();
                }
                else {
                    Speed.mc.field_71439_g.field_70138_W = 0.0f;
                }
                if (this.mode.getValue() == Mode.SLOWHOP || this.mode.getValue() == Mode.MODE2B2T || this.mode.getValue() == Mode.NCPHOP || this.mode.getValue() == Mode.FASTHOP) {
                    if (!this.limiter.getValue() && Speed.mc.field_71439_g.field_70122_E) {
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
                            if ((Speed.mc.field_71439_g.field_191988_bg != 0.0f || Speed.mc.field_71439_g.field_70702_br != 0.0f) && Speed.mc.field_71439_g.field_70122_E) {
                                if (Speed.mc.field_71439_g.func_70644_a(MobEffects.field_76430_j)) {
                                    motionY += (Speed.mc.field_71439_g.func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1f;
                                }
                                field_71439_g = Speed.mc.field_71439_g;
                                event.setY(field_71439_g.field_70181_x = field_70181_x);
                                this.moveSpeed *= ((this.mode.getValue() == Mode.SLOWHOP) ? 1.67 : ((this.mode.getValue() == Mode.FASTHOP) ? ((this.moveSpeed <= this.getBaseMoveSpeed()) ? 2.0 : 1.79) : 2.149));
                                break;
                            }
                            else {
                                break;
                            }
                            break;
                        }
                        case 3: {
                            minusSpeed = (this.strict.getValue() ? 0.771 : 0.77);
                            if (this.mode.getValue() == Mode.MODE2B2T) {
                                minusSpeed = 0.795;
                            }
                            if (this.mode.getValue() == Mode.SLOWHOP) {
                                minusSpeed = 0.6896;
                            }
                            if (this.mode.getValue() == Mode.FASTHOP) {
                                minusSpeed = 0.695;
                            }
                            this.moveSpeed = this.lastDist - minusSpeed * (this.lastDist - this.getBaseMoveSpeed());
                            break;
                        }
                        default: {
                            if ((Speed.mc.field_71441_e.func_184144_a((Entity)Speed.mc.field_71439_g, Speed.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, Speed.mc.field_71439_g.field_70181_x, 0.0)).size() > 0 || Speed.mc.field_71439_g.field_70124_G) && this.stage > 0) {
                                this.stage = ((Speed.mc.field_71439_g.field_191988_bg != 0.0f || Speed.mc.field_71439_g.field_70702_br != 0.0f) ? 1 : 0);
                            }
                            shouldDivide = ((this.mode.getValue() == Mode.SLOWHOP) ? 730.0 : ((this.mode.getValue() == Mode.FASTHOP) ? 165.0 : 159.0));
                            this.moveSpeed = this.lastDist - this.lastDist / shouldDivide;
                            break;
                        }
                    }
                    this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
                    forward = Speed.mc.field_71439_g.field_71158_b.field_192832_b;
                    strafe = Speed.mc.field_71439_g.field_71158_b.field_78902_a;
                    yaw = Speed.mc.field_71439_g.field_70177_z;
                    if (forward == 0.0 && strafe == 0.0) {
                        event.setX(0.0);
                        event.setZ(0.0);
                    }
                    else if (forward != 0.0 && strafe != 0.0) {
                        forward *= Math.sin(0.7853981633974483);
                        strafe *= Math.cos(0.7853981633974483);
                    }
                    shouldMulti = ((this.mode.getValue() == Mode.SLOWHOP) ? 0.993 : ((this.mode.getValue() == Mode.FASTHOP) ? 0.992 : 0.99));
                    event.setX((forward * this.moveSpeed * -Math.sin(Math.toRadians(yaw)) + strafe * this.moveSpeed * Math.cos(Math.toRadians(yaw))) * shouldMulti);
                    event.setZ((forward * this.moveSpeed * Math.cos(Math.toRadians(yaw)) - strafe * this.moveSpeed * -Math.sin(Math.toRadians(yaw))) * shouldMulti);
                    ++this.stage;
                }
                else if (this.mode.getValue() == Mode.LOWHOP) {
                    movementInput = Speed.mc.field_71439_g.field_71158_b;
                    forward2 = movementInput.field_192832_b;
                    strafe2 = movementInput.field_78902_a;
                    yaw2 = Speed.mc.field_71439_g.field_70177_z;
                    if (forward2 == 0.0f && strafe2 == 0.0f) {
                        event.X = 0.0;
                        event.Z = 0.0;
                    }
                    else if (forward2 != 0.0f) {
                        if (strafe2 >= 1.0f) {
                            yaw2 += ((forward2 > 0.0f) ? -45 : 45);
                            strafe2 = 0.0f;
                        }
                        else if (strafe2 <= -1.0f) {
                            yaw2 += ((forward2 > 0.0f) ? 45 : -45);
                            strafe2 = 0.0f;
                        }
                        if (forward2 > 0.0f) {
                            forward2 = 1.0f;
                        }
                        else if (forward2 < 0.0f) {
                            forward2 = -1.0f;
                        }
                    }
                    mx = Math.cos(Math.toRadians(yaw2 + 90.0f));
                    mz = Math.sin(Math.toRadians(yaw2 + 90.0f));
                    if (!this.shouldReturn() && !Speed.mc.field_71439_g.func_70090_H() && (forward2 != 0.0f || strafe2 != 0.0f)) {
                        if (Speed.mc.field_71439_g.field_70122_E && (forward2 != 0.0f || strafe2 != 0.0f)) {
                            this.level = 2;
                        }
                        if (round(Speed.mc.field_71439_g.field_70163_u - (int)Speed.mc.field_71439_g.field_70163_u, 3) == round(0.4, 3)) {
                            field_71439_g2 = Speed.mc.field_71439_g;
                            event.Y = n;
                            field_71439_g2.field_70181_x = n;
                        }
                        else if (round(Speed.mc.field_71439_g.field_70163_u - (int)Speed.mc.field_71439_g.field_70163_u, 3) == round(0.71, 3)) {
                            field_71439_g3 = Speed.mc.field_71439_g;
                            event.Y = n2;
                            field_71439_g3.field_70181_x = n2;
                        }
                        else if (round(Speed.mc.field_71439_g.field_70163_u - (int)Speed.mc.field_71439_g.field_70163_u, 3) == round(0.75, 3)) {
                            field_71439_g4 = Speed.mc.field_71439_g;
                            event.Y = n3;
                            field_71439_g4.field_70181_x = n3;
                        }
                        else if (round(Speed.mc.field_71439_g.field_70163_u - (int)Speed.mc.field_71439_g.field_70163_u, 3) == round(0.55, 3)) {
                            field_71439_g5 = Speed.mc.field_71439_g;
                            event.Y = n4;
                            field_71439_g5.field_70181_x = n4;
                        }
                        else if (round(Speed.mc.field_71439_g.field_70163_u - (int)Speed.mc.field_71439_g.field_70163_u, 3) == round(0.41, 3)) {
                            field_71439_g6 = Speed.mc.field_71439_g;
                            event.Y = n5;
                            field_71439_g6.field_70181_x = n5;
                        }
                        if (this.level == -1) {
                            event.X *= 0.3;
                            event.Z *= 0.3;
                        }
                        if (this.level != 1 || (Speed.mc.field_71439_g.field_191988_bg == 0.0f && Speed.mc.field_71439_g.field_70702_br == 0.0f)) {
                            if (this.level == 2) {
                                if (Speed.mc.field_71439_g.field_70124_G) {
                                    event.Y = 0.4;
                                }
                                this.moveSpeed *= 1.7;
                            }
                            else if (this.level == 3) {
                                difference = 0.76 * (this.lastDist - this.getBaseMoveSpeed());
                                this.moveSpeed = this.lastDist - difference;
                            }
                            else {
                                collidingList = Speed.mc.field_71441_e.func_184144_a((Entity)Speed.mc.field_71439_g, Speed.mc.field_71439_g.field_70121_D.func_72317_d(0.0, Speed.mc.field_71439_g.field_70181_x, 0.0));
                                if ((collidingList.size() > 0 || Speed.mc.field_71439_g.field_70124_G) && this.level > 0) {
                                    this.level = ((Speed.mc.field_71439_g.field_191988_bg != 0.0f || Speed.mc.field_71439_g.field_70702_br != 0.0f) ? 1 : 0);
                                }
                                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                            }
                        }
                        else {
                            this.moveSpeed = 1.35 * this.getBaseMoveSpeed();
                        }
                        this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
                        event.X = forward2 * this.moveSpeed * mx + strafe2 * this.moveSpeed * mz;
                        event.Z = forward2 * this.moveSpeed * mz - strafe2 * this.moveSpeed * mx;
                        if (forward2 == 0.0f && strafe2 == 0.0f) {
                            event.X = 0.0;
                            event.Z = 0.0;
                        }
                        ++this.level;
                    }
                    else {
                        this.level = -8;
                    }
                }
                else if (this.mode.getValue() == Mode.NCPBHOP) {
                    if (this.shouldReturn()) {
                        return;
                    }
                    else {
                        if (Speed.mc.field_71439_g.field_70122_E) {
                            this.level = 2;
                        }
                        if (round(Speed.mc.field_71439_g.field_70163_u - (int)Speed.mc.field_71439_g.field_70163_u, 3) == round(0.138, 3)) {
                            field_71439_g7 = Speed.mc.field_71439_g;
                            field_71439_g7.field_70181_x -= 0.08;
                            event.Y -= 0.09316090325960147;
                            field_71439_g8 = Speed.mc.field_71439_g;
                            field_71439_g8.field_70163_u -= 0.09316090325960147;
                        }
                        if (this.level != 1 || (Speed.mc.field_71439_g.field_191988_bg == 0.0f && Speed.mc.field_71439_g.field_70702_br == 0.0f)) {
                            if (this.level == 2) {
                                this.level = 3;
                                if (Speed.mc.field_71439_g.field_191988_bg != 0.0f || Speed.mc.field_71439_g.field_70702_br != 0.0f) {
                                    Speed.mc.field_71439_g.field_70181_x = 0.4;
                                    event.Y = 0.4;
                                    this.moveSpeed *= 1.662;
                                }
                            }
                            else if (this.level == 3) {
                                this.level = 4;
                                difference2 = 0.6896 * (this.lastDist - this.getBaseMoveSpeed());
                                this.moveSpeed = this.lastDist - difference2;
                            }
                            else {
                                if (Speed.mc.field_71439_g.field_70122_E && (Speed.mc.field_71441_e.func_184144_a((Entity)Speed.mc.field_71439_g, Speed.mc.field_71439_g.field_70121_D.func_72317_d(0.0, Speed.mc.field_71439_g.field_70181_x, 0.0)).size() > 0 || Speed.mc.field_71439_g.field_70124_G)) {
                                    this.level = 1;
                                }
                                this.moveSpeed = this.lastDist - this.lastDist / 101.0;
                            }
                        }
                        else {
                            this.level = 2;
                            this.moveSpeed = 1.35 * this.getBaseMoveSpeed() - 0.01;
                        }
                        this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
                        movementInput2 = Speed.mc.field_71439_g.field_71158_b;
                        forward3 = movementInput2.field_192832_b;
                        strafe3 = movementInput2.field_78902_a;
                        yaw3 = Minecraft.func_71410_x().field_71439_g.field_70177_z;
                        if (forward3 == 0.0f && strafe3 == 0.0f) {
                            event.X = 0.0;
                            event.Z = 0.0;
                        }
                        else if (forward3 != 0.0f) {
                            if (strafe3 > 0.0f) {
                                yaw3 += ((forward3 > 0.0f) ? -45 : 45);
                                strafe3 = 0.0f;
                            }
                            else if (strafe3 < 0.0f) {
                                yaw3 += ((forward3 > 0.0f) ? 45 : -45);
                                strafe3 = 0.0f;
                            }
                            if (forward3 > 0.0f) {
                                forward3 = 1.0f;
                            }
                            else if (forward3 < 0.0f) {
                                forward3 = -1.0f;
                            }
                        }
                        mx2 = Math.cos(Math.toRadians(yaw3 + 90.0f));
                        mz2 = Math.sin(Math.toRadians(yaw3 + 90.0f));
                        event.X = forward3 * this.moveSpeed * mx2 + strafe3 * this.moveSpeed * mz2;
                        event.Z = forward3 * this.moveSpeed * mz2 - strafe3 * this.moveSpeed * mx2;
                        Speed.mc.field_71439_g.field_70138_W = 0.6f;
                        if (forward3 == 0.0f && strafe3 == 0.0f) {
                            event.X = 0.0;
                            event.Z = 0.0;
                        }
                    }
                }
                else if (this.mode.getValue() == Mode.MINIHOP) {
                    Speed.mc.field_71439_g.field_70138_W = 0.6f;
                    movementInput3 = Speed.mc.field_71439_g.field_71158_b;
                    forward4 = movementInput3.field_192832_b;
                    strafe4 = movementInput3.field_78902_a;
                    yaw4 = Speed.mc.field_71439_g.field_70177_z;
                    if (forward4 == 0.0f && strafe4 == 0.0f) {
                        event.X = 0.0;
                        event.Z = 0.0;
                    }
                    else if (forward4 != 0.0f) {
                        if (strafe4 >= 1.0f) {
                            yaw4 += ((forward4 > 0.0f) ? -45 : 45);
                            strafe4 = 0.0f;
                        }
                        else if (strafe4 <= -1.0f) {
                            yaw4 += ((forward4 > 0.0f) ? 45 : -45);
                            strafe4 = 0.0f;
                        }
                        if (forward4 > 0.0f) {
                            forward4 = 1.0f;
                        }
                        else if (forward4 < 0.0f) {
                            forward4 = -1.0f;
                        }
                    }
                    mx3 = Math.cos(Math.toRadians(yaw4 + 90.0f));
                    mz3 = Math.sin(Math.toRadians(yaw4 + 90.0f));
                    if (!this.shouldReturn() && !Speed.mc.field_71439_g.func_70090_H() && (forward4 != 0.0f || strafe4 != 0.0f)) {
                        if (Speed.mc.field_71439_g.field_70122_E && (forward4 != 0.0f || strafe4 != 0.0f)) {
                            this.level = 2;
                        }
                        if (this.level == -1) {
                            event.X *= 0.3;
                            event.Z *= 0.3;
                        }
                        if (this.level != 1 || (Speed.mc.field_71439_g.field_191988_bg == 0.0f && Speed.mc.field_71439_g.field_70702_br == 0.0f)) {
                            if (this.level == 2) {
                                event.Y = 0.4;
                                this.moveSpeed = this.getBaseMoveSpeed() * this.onGroundSpeed.getValue();
                            }
                            else if (this.level == 3) {
                                this.moveSpeed = this.getBaseMoveSpeed() * this.onAirSpeed.getValue();
                                field_71439_g9 = Speed.mc.field_71439_g;
                                event.Y = n6;
                                field_71439_g9.field_70181_x = n6;
                            }
                            else {
                                collidingList2 = Speed.mc.field_71441_e.func_184144_a((Entity)Speed.mc.field_71439_g, Speed.mc.field_71439_g.field_70121_D.func_72317_d(0.0, Speed.mc.field_71439_g.field_70181_x, 0.0));
                                if ((collidingList2.size() > 0 || Speed.mc.field_71439_g.field_70124_G) && this.level > 0) {
                                    this.level = ((Speed.mc.field_71439_g.field_191988_bg != 0.0f || Speed.mc.field_71439_g.field_70702_br != 0.0f) ? 1 : 0);
                                    Speed.mc.field_71439_g.field_70138_W = this.stepHeight.getValue();
                                }
                                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                            }
                        }
                        this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
                        event.X = forward4 * this.moveSpeed * mx3 + strafe4 * this.moveSpeed * mz3;
                        event.Z = forward4 * this.moveSpeed * mz3 - strafe4 * this.moveSpeed * mx3;
                        if (forward4 == 0.0f && strafe4 == 0.0f) {
                            event.X = 0.0;
                            event.Z = 0.0;
                        }
                        ++this.level;
                        this.speedTick = !this.speedTick;
                    }
                    else {
                        this.level = -8;
                    }
                }
                event.cancel();
            }
        }, (Predicate<EventPlayerMove>[])new Predicate[0]);
    }
    
    public void onEnable() {
        if (Speed.mc.field_71439_g == null) {
            this.disable();
            return;
        }
        if ((this.mode.getValue() == Mode.LOWHOP || this.mode.getValue() == Mode.MINIHOP) && Speed.mc.field_71439_g.field_70122_E) {
            Speed.mc.field_71439_g.field_70138_W = this.stepHeight.getValue();
        }
        this.moveSpeed = this.getBaseMoveSpeed();
        if (ModuleManager.getModuleByName("LongJump").isEnabled()) {
            ModuleManager.getModuleByName("LongJump").disable();
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.useTimer.getValue()) {
            Minecraft.func_71410_x().field_71428_T.field_194149_e = (this.tpsSync.getValue() ? (919.1177f / LagCompensator.INSTANCE.getTickRate()) : 45.955883f);
        }
        else {
            Minecraft.func_71410_x().field_71428_T.field_194149_e = 50.0f;
        }
    }
    
    public void onDisable() {
        this.moveSpeed = 0.0;
        this.stage = 2;
        if (Speed.mc.field_71439_g != null) {
            Speed.mc.field_71439_g.field_70138_W = 0.6f;
            Minecraft.func_71410_x().field_71428_T.field_194149_e = 50.0f;
        }
    }
    
    public static boolean isMoving() {
        return Speed.mc.field_71439_g.field_191988_bg != 0.0 || Speed.mc.field_71439_g.field_70702_br != 0.0;
    }
    
    public double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (Speed.mc.field_71439_g.func_70644_a(MobEffects.field_76424_c) && this.potion.getValue()) {
            final int amplifier = Objects.requireNonNull(Speed.mc.field_71439_g.func_70660_b(MobEffects.field_76424_c)).func_76458_c() + 1;
            baseSpeed *= 1.0 + 0.2 * amplifier;
        }
        return baseSpeed;
    }
    
    private boolean shouldReturn() {
        return Speed.mc.field_71439_g.func_180799_ab() || Speed.mc.field_71439_g.func_70090_H() || this.isDisabled() || Speed.mc.field_71439_g.field_70134_J;
    }
    
    public static double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        final BigDecimal bigDecimal = new BigDecimal(value).setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
    
    @Override
    public String getHudInfo() {
        if (this.mode.getValue() == Mode.SLOWHOP) {
            return "SlowHop";
        }
        if (this.mode.getValue() == Mode.MODE2B2T) {
            return "2b2t";
        }
        if (this.mode.getValue() == Mode.NCPHOP) {
            return "NCPHop";
        }
        if (this.mode.getValue() == Mode.NCPBHOP) {
            return "NCPBhop";
        }
        if (this.mode.getValue() == Mode.LOWHOP) {
            return "LowHop";
        }
        if (this.mode.getValue() == Mode.MINIHOP) {
            return "MiniHop";
        }
        if (this.mode.getValue() == Mode.FASTHOP) {
            return "FastHop";
        }
        return "None";
    }
    
    public enum Mode
    {
        NONE, 
        SLOWHOP, 
        NCPHOP, 
        NCPBHOP, 
        MODE2B2T, 
        LOWHOP, 
        MINIHOP, 
        FASTHOP;
    }
}
