// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.movement;

import java.util.function.Predicate;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.entity.Entity;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.EventPlayerMove;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "Step", category = Category.MOVEMENT)
public class Step extends Module
{
    private Setting<Mode> mode;
    private Setting<Double> height;
    private Setting<Boolean> reverse;
    private Setting<Boolean> togglable;
    private Setting<Boolean> useTimer;
    public boolean stepped;
    public boolean timer;
    private int ticks;
    @EventHandler
    private Listener<EventPlayerMove> moveListener;
    
    public Step() {
        this.mode = this.register(Settings.e("Mode", Mode.NCP));
        this.height = this.register(Settings.d("Height", 2.0));
        this.reverse = this.register(Settings.b("ReverseStep"));
        this.togglable = this.register(Settings.b("Togglable"));
        this.useTimer = this.register(Settings.booleanBuilder("UseTimer").withValue(true).withVisibility(v -> this.mode.getValue() == Mode.NCP).build());
        this.ticks = 0;
        double[] dir;
        boolean twofive;
        boolean two;
        boolean onefive;
        boolean one;
        double yAdd;
        double y;
        double[] oneOffset;
        int i;
        double[] oneFiveOffset;
        int j;
        double[] twoOffset;
        int k;
        double[] twoFiveOffset;
        int l;
        this.moveListener = new Listener<EventPlayerMove>(event -> {
            if (this.mode.getValue() == Mode.NCP) {
                this.stepped = false;
                dir = new double[] { event.X, event.Z };
                twofive = false;
                two = false;
                onefive = false;
                one = false;
                yAdd = 0.0;
                y = 0.0;
                while (y < this.height.getValue() + 0.5) {
                    if (Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, Step.mc.field_71439_g.func_174813_aQ().func_72317_d(dir[0], y + 0.01, dir[1])).isEmpty() && !Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, Step.mc.field_71439_g.func_174813_aQ().func_72317_d(dir[0], y - 0.01, dir[1])).isEmpty() && Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, Step.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, y, 0.0)).isEmpty()) {
                        yAdd = y;
                        break;
                    }
                    else {
                        y += 0.01;
                    }
                }
                if (Step.mc.field_71439_g.field_70123_F && (Step.mc.field_71439_g.field_191988_bg != 0.0f || Step.mc.field_71439_g.field_70702_br != 0.0f) && Step.mc.field_71439_g.field_70122_E && this.height.getValue() >= yAdd) {
                    if (yAdd <= 0.5) {
                        return;
                    }
                    else if (yAdd <= 1.0) {
                        this.stepped = true;
                        for (oneOffset = new double[] { 0.42, 0.753 }, i = 0; i < oneOffset.length; ++i) {
                            Step.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + oneOffset[i], Step.mc.field_71439_g.field_70161_v, Step.mc.field_71439_g.field_70122_E));
                        }
                        Step.mc.field_71439_g.func_70107_b(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + yAdd, Step.mc.field_71439_g.field_70161_v);
                        this.ticks = 1;
                        if (this.stepped && this.useTimer.getValue()) {
                            Step.mc.field_71428_T.field_194149_e = 125.0f;
                            this.timer = true;
                        }
                        return;
                    }
                    else if (yAdd <= 1.5) {
                        this.stepped = true;
                        for (oneFiveOffset = new double[] { 0.42, 0.75, 1.0, 1.16, 1.23, 1.2 }, j = 0; j < oneFiveOffset.length; ++j) {
                            Step.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + oneFiveOffset[j], Step.mc.field_71439_g.field_70161_v, Step.mc.field_71439_g.field_70122_E));
                        }
                        Step.mc.field_71439_g.func_70107_b(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + yAdd, Step.mc.field_71439_g.field_70161_v);
                        this.ticks = 1;
                        if (this.stepped && this.useTimer.getValue()) {
                            Step.mc.field_71428_T.field_194149_e = 187.5f;
                            this.timer = true;
                        }
                        return;
                    }
                    else if (yAdd <= 2.0) {
                        this.stepped = true;
                        for (twoOffset = new double[] { 0.42, 0.78, 0.63, 0.51, 0.9, 1.21, 1.45, 1.43 }, k = 0; k < twoOffset.length; ++k) {
                            Step.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + twoOffset[k], Step.mc.field_71439_g.field_70161_v, Step.mc.field_71439_g.field_70122_E));
                        }
                        Step.mc.field_71439_g.func_70107_b(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + yAdd, Step.mc.field_71439_g.field_70161_v);
                        this.ticks = 2;
                        if (this.stepped && this.useTimer.getValue()) {
                            Step.mc.field_71428_T.field_194149_e = 250.0f;
                            this.timer = true;
                        }
                        return;
                    }
                    else if (yAdd <= 2.5) {
                        this.stepped = true;
                        for (twoFiveOffset = new double[] { 0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907 }, l = 0; l < twoFiveOffset.length; ++l) {
                            Step.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + twoFiveOffset[l], Step.mc.field_71439_g.field_70161_v, Step.mc.field_71439_g.field_70122_E));
                        }
                        Step.mc.field_71439_g.func_70107_b(Step.mc.field_71439_g.field_70165_t, Step.mc.field_71439_g.field_70163_u + yAdd, Step.mc.field_71439_g.field_70161_v);
                        this.ticks = 2;
                        if (this.stepped && this.useTimer.getValue()) {
                            Step.mc.field_71428_T.field_194149_e = 312.5f;
                            this.timer = true;
                        }
                        return;
                    }
                }
            }
            if (this.timer) {
                Step.mc.field_71428_T.field_194149_e = 50.0f;
            }
        }, (Predicate<EventPlayerMove>[])new Predicate[0]);
    }
    
    public void onDisable() {
        Step.mc.field_71428_T.field_194149_e = 50.0f;
        Step.mc.field_71439_g.field_70138_W = 0.6f;
    }
    
    @Override
    public void onUpdate() {
        if (this.stepped && !this.togglable.getValue()) {
            this.stepped = false;
            this.disable();
        }
        if (Step.mc.field_71441_e == null || Step.mc.field_71439_g == null || Step.mc.field_71439_g.func_70090_H() || Step.mc.field_71439_g.func_180799_ab() || Step.mc.field_71439_g.func_70617_f_() || Step.mc.field_71474_y.field_74314_A.func_151470_d()) {
            return;
        }
        if (Step.mc.field_71439_g != null && Step.mc.field_71439_g.field_70122_E && !Step.mc.field_71439_g.func_70090_H() && !Step.mc.field_71439_g.func_70617_f_() && this.reverse.getValue()) {
            for (double y = 0.0; y < this.height.getValue() + 0.5; y += 0.01) {
                if (!Step.mc.field_71441_e.func_184144_a((Entity)Step.mc.field_71439_g, Step.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, -y, 0.0)).isEmpty()) {
                    Step.mc.field_71439_g.field_70181_x = -10.0;
                    break;
                }
            }
        }
        if (this.mode.getValue() == Mode.VANILLA) {
            Step.mc.field_71439_g.field_70138_W = (float)(double)this.height.getValue();
        }
    }
    
    @Override
    public String getHudInfo() {
        return this.mode.getValue().equals(Mode.NCP) ? "NCP" : "Vanilla";
    }
    
    public enum Mode
    {
        NCP, 
        VANILLA;
    }
}
