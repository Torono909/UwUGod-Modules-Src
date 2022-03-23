// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import java.util.function.Predicate;
import net.minecraft.client.gui.GuiScreen;
import meow.candycat.uwu.command.Command;
import net.minecraft.client.gui.GuiGameOver;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.GuiScreenEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "AutoRespawn", description = "Respawn utility", category = Category.MISC)
public class AutoRespawn extends Module
{
    private Setting<Boolean> respawn;
    private Setting<Boolean> deathCoords;
    private Setting<Boolean> antiGlitchScreen;
    @EventHandler
    public Listener<GuiScreenEvent.Displayed> listener;
    
    public AutoRespawn() {
        this.respawn = this.register(Settings.b("Respawn", true));
        this.deathCoords = this.register(Settings.b("DeathCoords", true));
        this.antiGlitchScreen = this.register(Settings.b("Anti Glitch Screen", true));
        this.listener = new Listener<GuiScreenEvent.Displayed>(event -> {
            if (!(!(event.getScreen() instanceof GuiGameOver))) {
                if (this.deathCoords.getValue() && AutoRespawn.mc.field_71439_g.func_110143_aJ() <= 0.0f) {
                    Command.sendChatMessage(String.format("You died at x %d y %d z %d", (int)AutoRespawn.mc.field_71439_g.field_70165_t, (int)AutoRespawn.mc.field_71439_g.field_70163_u, (int)AutoRespawn.mc.field_71439_g.field_70161_v));
                }
                if (this.respawn.getValue() || (this.antiGlitchScreen.getValue() && AutoRespawn.mc.field_71439_g.func_110143_aJ() > 0.0f)) {
                    AutoRespawn.mc.field_71439_g.func_71004_bE();
                    AutoRespawn.mc.func_147108_a((GuiScreen)null);
                }
            }
        }, (Predicate<GuiScreenEvent.Displayed>[])new Predicate[0]);
    }
}
