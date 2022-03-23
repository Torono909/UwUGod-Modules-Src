// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.movement;

import net.minecraft.util.math.Vec3d;
import net.minecraft.block.BlockWeb;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.MovementInput;
import java.util.function.Predicate;
import meow.candycat.uwu.event.events.EventPlayerMove;
import meow.candycat.eventsystem.listener.EventHandler;
import net.minecraftforge.client.event.InputUpdateEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.module.Module;

@Info(name = "NoSlowDown", category = Category.MOVEMENT)
public class NoSlowDown extends Module
{
    @EventHandler
    private Listener<InputUpdateEvent> eventListener;
    @EventHandler
    private Listener<EventPlayerMove> x;
    
    public NoSlowDown() {
        final MovementInput movementInput;
        final MovementInput movementInput2;
        this.eventListener = new Listener<InputUpdateEvent>(event -> {
            if (NoSlowDown.mc.field_71439_g.func_184587_cr() && !NoSlowDown.mc.field_71439_g.func_184218_aH()) {
                event.getMovementInput();
                movementInput.field_78902_a *= 5.0f;
                event.getMovementInput();
                movementInput2.field_192832_b *= 5.0f;
            }
            return;
        }, (Predicate<InputUpdateEvent>[])new Predicate[0]);
        this.x = new Listener<EventPlayerMove>(event -> {
            if (inWeb()) {
                event.cancel();
                if (NoSlowDown.mc.field_71439_g.field_70122_E) {
                    event.setX(event.X * 3.5);
                    event.setZ(event.Z * 3.5);
                }
                else if (NoSlowDown.mc.field_71439_g.field_71158_b.field_78899_d) {
                    event.setY(event.Y * 10.0);
                }
            }
        }, (Predicate<EventPlayerMove>[])new Predicate[0]);
    }
    
    public static boolean inWeb() {
        if (NoSlowDown.mc.field_71441_e == null || NoSlowDown.mc.field_71439_g == null) {
            return false;
        }
        final Vec3d y = NoSlowDown.mc.field_71439_g.func_174791_d();
        final BlockPos x = new BlockPos(y.field_72450_a, y.field_72448_b, y.field_72449_c);
        return NoSlowDown.mc.field_71439_g.field_70134_J || NoSlowDown.mc.field_71441_e.func_180495_p(x).func_177230_c() instanceof BlockWeb;
    }
}
