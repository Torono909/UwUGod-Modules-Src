// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.render;

import meow.candycat.uwu.util.MathsUtils;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "WallHack", category = Category.RENDER)
public class WallHack extends Module
{
    public Setting<Integer> opacity;
    public static WallHack INSTANCE;
    
    public WallHack() {
        this.opacity = this.register(Settings.i("Opacity", 150));
        WallHack.INSTANCE = this;
    }
    
    public static int getOpacity() {
        return MathsUtils.clamp(WallHack.INSTANCE.opacity.getValue(), 0, 255);
    }
}
