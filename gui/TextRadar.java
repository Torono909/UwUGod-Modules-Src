// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.gui;

import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "TextRadar", category = Category.GUI)
public class TextRadar extends Module
{
    public Setting<Boolean> showCoords;
    public static TextRadar Instance;
    
    public TextRadar() {
        this.showCoords = this.register(Settings.b("ShowCoords"));
        TextRadar.Instance = this;
    }
    
    public static boolean shouldShowCoords() {
        return TextRadar.Instance.showCoords.getValue();
    }
}
