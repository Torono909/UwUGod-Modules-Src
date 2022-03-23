// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.player;

import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "LowerCooldownPeriod", category = Category.PLAYER)
public class LowerCooldownPeriod extends Module
{
    private Setting<Float> cooldownPeriod;
    private static LowerCooldownPeriod INSTANCE;
    
    public LowerCooldownPeriod() {
        this.cooldownPeriod = this.register(Settings.f("CooldownSpeedUp", 1.1f));
        LowerCooldownPeriod.INSTANCE = this;
    }
    
    public static float getCooldownPeriod() {
        return LowerCooldownPeriod.INSTANCE.cooldownPeriod.getValue();
    }
    
    public static boolean isOn() {
        return LowerCooldownPeriod.INSTANCE.isEnabled();
    }
}
