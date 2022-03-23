// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import java.util.function.Predicate;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiGameOver;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.GuiScreenEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.module.Module;

@Info(name = "AntiDeathScreen", description = "Fixes random death screen glitches", category = Category.COMBAT)
public class AntiDeathScreen extends Module
{
    @EventHandler
    public Listener<GuiScreenEvent.Displayed> listener;
    
    public AntiDeathScreen() {
        this.listener = new Listener<GuiScreenEvent.Displayed>(event -> {
            if (!(!(event.getScreen() instanceof GuiGameOver))) {
                if (AntiDeathScreen.mc.field_71439_g.func_110143_aJ() > 0.0f) {
                    AntiDeathScreen.mc.field_71439_g.func_71004_bE();
                    AntiDeathScreen.mc.func_147108_a((GuiScreen)null);
                }
            }
        }, (Predicate<GuiScreenEvent.Displayed>[])new Predicate[0]);
    }
}
