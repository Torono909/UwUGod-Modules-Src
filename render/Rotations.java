// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.render;

import java.util.function.Predicate;
import meow.candycat.uwu.setting.Settings;
import net.minecraftforge.client.event.RenderPlayerEvent;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.EventPlayerPreMotionUpdate;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "Rotations", category = Category.RENDER)
public class Rotations extends Module
{
    public Setting<Mode> modeSetting;
    private float playerPitch;
    private float lastPlayerPitch;
    private boolean shouldReset;
    float lastLastReportedPitch;
    float lastLastReportedYaw;
    @EventHandler
    private Listener<EventPlayerPreMotionUpdate> listener;
    @EventHandler
    private Listener<RenderPlayerEvent.Pre> y;
    @EventHandler
    private Listener<RenderPlayerEvent.Post> z;
    
    public Rotations() {
        this.modeSetting = this.register(Settings.e("Mode", Mode.FullBody));
        this.shouldReset = false;
        this.lastLastReportedPitch = 0.0f;
        this.lastLastReportedYaw = 0.0f;
        this.listener = new Listener<EventPlayerPreMotionUpdate>(e -> {
            this.lastLastReportedPitch = Rotations.mc.field_71439_g.field_175165_bM;
            this.lastLastReportedYaw = Rotations.mc.field_71439_g.field_175164_bL;
            return;
        }, (Predicate<EventPlayerPreMotionUpdate>[])new Predicate[0]);
        this.y = new Listener<RenderPlayerEvent.Pre>(event -> {
            if (Rotations.mc.field_71439_g == null || Rotations.mc.field_175616_W.field_78734_h != event.getEntity()) {
                return;
            }
            else {
                this.playerPitch = Rotations.mc.field_71439_g.field_70125_A;
                this.lastPlayerPitch = Rotations.mc.field_71439_g.field_70127_C;
                Rotations.mc.field_71439_g.field_70125_A = Rotations.mc.field_71439_g.field_175165_bM;
                Rotations.mc.field_71439_g.field_70127_C = this.lastLastReportedPitch;
                Rotations.mc.field_71439_g.field_70759_as = Rotations.mc.field_71439_g.field_175164_bL;
                if (this.modeSetting.getValue() == Mode.FullBody) {
                    Rotations.mc.field_71439_g.field_70761_aq = Rotations.mc.field_71439_g.field_175164_bL;
                }
                this.shouldReset = true;
                return;
            }
        }, (Predicate<RenderPlayerEvent.Pre>[])new Predicate[0]);
        this.z = new Listener<RenderPlayerEvent.Post>(event -> {
            if (this.shouldReset) {
                Rotations.mc.field_71439_g.field_70125_A = this.playerPitch;
                Rotations.mc.field_71439_g.field_70127_C = this.lastPlayerPitch;
                this.shouldReset = false;
            }
        }, (Predicate<RenderPlayerEvent.Post>[])new Predicate[0]);
    }
    
    @Override
    public String getHudInfo() {
        return this.modeSetting.getValue().toString();
    }
    
    public enum Mode
    {
        FullBody, 
        Head;
    }
}
