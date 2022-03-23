// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.player;

import meow.candycat.uwu.module.Module;

@Info(name = "AntiChunkLoadPatch", category = Category.PLAYER, description = "Prevents loading of overloaded chunks", showOnArray = ShowOnArray.OFF)
public class AntiChunkLoadPatch extends Module
{
    private static AntiChunkLoadPatch INSTANCE;
    
    public AntiChunkLoadPatch() {
        AntiChunkLoadPatch.INSTANCE = this;
    }
    
    public static boolean enabled() {
        return AntiChunkLoadPatch.INSTANCE.isEnabled();
    }
    
    static {
        AntiChunkLoadPatch.INSTANCE = new AntiChunkLoadPatch();
    }
}
