// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.gui;

import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "ToggleAlert", category = Category.GUI)
public class ToggleAlert extends Module
{
    public Setting<mode> Mode;
    
    public ToggleAlert() {
        this.Mode = this.register(Settings.e("Mode", mode.CHAT));
    }
    
    public enum mode
    {
        CHAT, 
        NOTIFICATION;
    }
}
