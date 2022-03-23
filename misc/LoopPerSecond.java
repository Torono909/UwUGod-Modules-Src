// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import meow.candycat.uwu.command.Command;
import meow.candycat.uwu.module.ModuleManager;
import java.util.concurrent.Executors;
import meow.candycat.uwu.module.Module;

@Info(name = "LoopPerSecond", category = Category.MISC)
public class LoopPerSecond extends Module
{
    private int loopcount;
    
    public LoopPerSecond() {
        this.loopcount = 0;
    }
    
    @Override
    public void onUpdate() {
        ++this.loopcount;
    }
    
    public void onEnable() {
        final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        final ExecutorService executorService;
        scheduler.scheduleAtFixedRate(() -> {
            if (ModuleManager.getModuleByName("LoopPerSecond").isDisabled()) {
                executorService.shutdown();
            }
            Command.sendChatMessage("The client looped for " + this.loopcount + " times");
            this.loopcount = 0;
        }, 0L, 1L, TimeUnit.SECONDS);
    }
}
