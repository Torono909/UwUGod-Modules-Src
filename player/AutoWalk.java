// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.player;

import net.minecraft.entity.player.EntityPlayer;
import meow.candycat.uwu.util.EntityUtil;
import java.util.function.Predicate;
import net.minecraft.pathfinding.PathPoint;
import meow.candycat.uwu.module.ModuleManager;
import meow.candycat.uwu.module.modules.render.Pathfind;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.eventsystem.listener.EventHandler;
import net.minecraftforge.client.event.InputUpdateEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "AutoWalk", category = Category.PLAYER)
public class AutoWalk extends Module
{
    private Setting<AutoWalkMode> mode;
    @EventHandler
    private Listener<InputUpdateEvent> inputUpdateEventListener;
    
    public AutoWalk() {
        this.mode = this.register(Settings.e("Mode", AutoWalkMode.FORWARD));
        PathPoint next;
        this.inputUpdateEventListener = new Listener<InputUpdateEvent>(event -> {
            switch (this.mode.getValue()) {
                case FORWARD: {
                    event.getMovementInput().field_192832_b = 1.0f;
                    break;
                }
                case BACKWARDS: {
                    event.getMovementInput().field_192832_b = -1.0f;
                    break;
                }
                case PATH: {
                    if (Pathfind.points.isEmpty()) {
                        return;
                    }
                    else {
                        event.getMovementInput().field_192832_b = 1.0f;
                        if (AutoWalk.mc.field_71439_g.func_70090_H() || AutoWalk.mc.field_71439_g.func_180799_ab()) {
                            AutoWalk.mc.field_71439_g.field_71158_b.field_78901_c = true;
                        }
                        else if (AutoWalk.mc.field_71439_g.field_70123_F && AutoWalk.mc.field_71439_g.field_70122_E) {
                            AutoWalk.mc.field_71439_g.func_70664_aZ();
                        }
                        if (!ModuleManager.isModuleEnabled("Pathfind") || Pathfind.points.isEmpty()) {
                            return;
                        }
                        else {
                            next = Pathfind.points.get(0);
                            this.lookAt(next);
                            break;
                        }
                    }
                    break;
                }
            }
        }, (Predicate<InputUpdateEvent>[])new Predicate[0]);
    }
    
    private void lookAt(final PathPoint pathPoint) {
        final double[] v = EntityUtil.calculateLookAt(pathPoint.field_75839_a + 0.5f, pathPoint.field_75837_b, pathPoint.field_75838_c + 0.5f, (EntityPlayer)AutoWalk.mc.field_71439_g);
        AutoWalk.mc.field_71439_g.field_70177_z = (float)v[0];
        AutoWalk.mc.field_71439_g.field_70125_A = (float)v[1];
    }
    
    private enum AutoWalkMode
    {
        FORWARD, 
        BACKWARDS, 
        PATH;
    }
}
