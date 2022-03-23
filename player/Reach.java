// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.player;

import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "Reach", category = Category.PLAYER)
public class Reach extends Module
{
    public static Reach INSTANCE;
    public Setting<Float> range;
    
    public Reach() {
        this.range = this.register(Settings.f("Reach", 6.0f));
        Reach.INSTANCE = this;
    }
    
    public static boolean isEnable() {
        return Reach.INSTANCE.isEnabled();
    }
    
    public static float getReach() {
        return Reach.INSTANCE.range.getValue();
    }
    
    @Override
    public String getHudInfo() {
        return this.range.getValue().toString();
    }
}
