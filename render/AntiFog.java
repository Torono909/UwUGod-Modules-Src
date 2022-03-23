// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.render;

import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "AntiFog", description = "Disables or reduces fog", category = Category.RENDER)
public class AntiFog extends Module
{
    public static Setting<VisionMode> mode;
    private static AntiFog INSTANCE;
    
    public AntiFog() {
        (AntiFog.INSTANCE = this).register(AntiFog.mode);
    }
    
    public static boolean enabled() {
        return AntiFog.INSTANCE.isEnabled();
    }
    
    static {
        AntiFog.mode = Settings.e("Mode", VisionMode.NOFOG);
        AntiFog.INSTANCE = new AntiFog();
    }
    
    public enum VisionMode
    {
        NOFOG, 
        AIR;
    }
}
