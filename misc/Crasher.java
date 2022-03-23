// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import java.util.function.Predicate;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.EventPlayerPreMotionUpdate;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.module.Module;

@Info(name = "Crasher", category = Category.EXPLOITS)
public class Crasher extends Module
{
    @EventHandler
    private Listener<EventPlayerPreMotionUpdate> x;
    
    public Crasher() {
        this.x = new Listener<EventPlayerPreMotionUpdate>(event -> this.disable(), (Predicate<EventPlayerPreMotionUpdate>[])new Predicate[0]);
    }
}
