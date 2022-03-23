// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import java.util.function.Predicate;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.EventPlayerPreMotionUpdate;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.module.Module;

@Info(name = "AntiAim", category = Category.MISC)
public class AntiAim extends Module
{
    @EventHandler
    private Listener<EventPlayerPreMotionUpdate> x;
    
    public AntiAim() {
        this.x = new Listener<EventPlayerPreMotionUpdate>(event -> {}, (Predicate<EventPlayerPreMotionUpdate>[])new Predicate[0]);
    }
    
    private enum pitchmode
    {
        ZERO, 
        DOWN, 
        UP, 
        ROCKER, 
        NONE;
    }
    
    private enum yawmode
    {
        SPIN, 
        STATIC, 
        DIRECTIONS, 
        ZERO, 
        NONE;
    }
}
