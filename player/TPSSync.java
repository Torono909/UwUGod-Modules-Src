// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.player;

import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "TPSSync", description = "Synchronizes some actions with the server TPS", category = Category.PLAYER)
public class TPSSync extends Module
{
    private Setting<Boolean> attack;
    private Setting<Boolean> mine;
    private static TPSSync INSTANCE;
    
    public TPSSync() {
        this.attack = this.register(Settings.b("Attack", true));
        this.mine = this.register(Settings.b("Mine", true));
        TPSSync.INSTANCE = this;
    }
    
    @Override
    public String getHudInfo() {
        return (this.attack.getValue() && this.mine.getValue()) ? "Both" : (this.attack.getValue() ? "Attack" : (this.mine.getValue() ? "Mine" : "None"));
    }
    
    public static boolean shouldAttack() {
        return TPSSync.INSTANCE.attack.getValue() && TPSSync.INSTANCE.isEnabled();
    }
    
    public static boolean shouldMine() {
        return TPSSync.INSTANCE.mine.getValue();
    }
    
    public static boolean isOn() {
        return TPSSync.INSTANCE.isEnabled();
    }
}
