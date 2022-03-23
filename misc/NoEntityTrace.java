// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "NoEntityTrace", category = Category.MISC, description = "Blocks entities from stopping you from mining")
public class NoEntityTrace extends Module
{
    private Setting<TraceMode> mode;
    private static NoEntityTrace INSTANCE;
    
    public NoEntityTrace() {
        this.mode = this.register(Settings.e("Mode", TraceMode.DYNAMIC));
        NoEntityTrace.INSTANCE = this;
    }
    
    public static boolean shouldBlock() {
        return NoEntityTrace.INSTANCE.isEnabled() && (NoEntityTrace.INSTANCE.mode.getValue() == TraceMode.STATIC || NoEntityTrace.mc.field_71442_b.field_78778_j);
    }
    
    private enum TraceMode
    {
        STATIC, 
        DYNAMIC;
    }
}
