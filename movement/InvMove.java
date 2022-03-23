// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.movement;

import java.util.function.Predicate;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.GuiChat;
import meow.candycat.eventsystem.listener.EventHandler;
import net.minecraftforge.client.event.InputUpdateEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.module.Module;

@Info(name = "InvMove", category = Category.MOVEMENT)
public class InvMove extends Module
{
    @EventHandler
    private Listener<InputUpdateEvent> inputUpdateEventListener;
    
    public InvMove() {
        this.inputUpdateEventListener = new Listener<InputUpdateEvent>(event -> {
            if (InvMove.mc.field_71462_r != null && !(InvMove.mc.field_71462_r instanceof GuiChat)) {
                event.getMovementInput().field_78902_a = 0.0f;
                event.getMovementInput().field_192832_b = 0.0f;
                if (Keyboard.isKeyDown(InvMove.mc.field_71474_y.field_74351_w.func_151463_i()) && !Keyboard.isKeyDown(InvMove.mc.field_71474_y.field_74368_y.func_151463_i())) {
                    event.getMovementInput().field_192832_b = 1.0f;
                }
                if (Keyboard.isKeyDown(InvMove.mc.field_71474_y.field_74368_y.func_151463_i()) && !Keyboard.isKeyDown(InvMove.mc.field_71474_y.field_74351_w.func_151463_i())) {
                    event.getMovementInput().field_192832_b = -1.0f;
                }
                if (Keyboard.isKeyDown(InvMove.mc.field_71474_y.field_74370_x.func_151463_i()) && !Keyboard.isKeyDown(InvMove.mc.field_71474_y.field_74366_z.func_151463_i())) {
                    event.getMovementInput().field_78902_a = 1.0f;
                }
                if (Keyboard.isKeyDown(InvMove.mc.field_71474_y.field_74366_z.func_151463_i()) && !Keyboard.isKeyDown(InvMove.mc.field_71474_y.field_74370_x.func_151463_i())) {
                    event.getMovementInput().field_78902_a = -1.0f;
                }
                if (Keyboard.isKeyDown(InvMove.mc.field_71474_y.field_74314_A.func_151463_i())) {
                    event.getMovementInput().field_78901_c = true;
                }
            }
        }, (Predicate<InputUpdateEvent>[])new Predicate[0]);
    }
}
