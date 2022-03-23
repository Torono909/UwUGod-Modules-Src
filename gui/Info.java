// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.gui;

import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Module.Info(name = "Info", category = Category.GUI)
public class Info extends Module
{
    public Setting<Boolean> potion;
    private Setting<Boolean> bps;
    private Setting<Boolean> tps;
    private Setting<Boolean> fps;
    private Setting<Boolean> ping;
    private Setting<Boolean> coords;
    private Setting<Boolean> nethercoords;
    private Setting<Boolean> health;
    public static Info INSTANCE;
    
    public Info() {
        this.potion = this.register(Settings.b("Potions"));
        this.bps = this.register(Settings.b("Speed"));
        this.tps = this.register(Settings.b("TPS"));
        this.fps = this.register(Settings.b("FPS"));
        this.ping = this.register(Settings.b("Ping"));
        this.coords = this.register(Settings.b("Coords"));
        this.nethercoords = this.register(Settings.b("NetherCoords"));
        this.health = this.register(Settings.b("Health"));
        Info.INSTANCE = this;
    }
    
    public static boolean shouldPotion() {
        return Info.INSTANCE.isEnabled() && Info.INSTANCE.potion.getValue();
    }
    
    public static boolean shouldBps() {
        return Info.INSTANCE.isEnabled() && Info.INSTANCE.bps.getValue();
    }
    
    public static boolean shouldTps() {
        return Info.INSTANCE.isEnabled() && Info.INSTANCE.tps.getValue();
    }
    
    public static boolean shouldFps() {
        return Info.INSTANCE.isEnabled() && Info.INSTANCE.fps.getValue();
    }
    
    public static boolean shouldPing() {
        return Info.INSTANCE.isEnabled() && Info.INSTANCE.ping.getValue();
    }
    
    public static boolean shouldCoords() {
        return Info.INSTANCE.isEnabled() && Info.INSTANCE.coords.getValue();
    }
    
    public static boolean shouldNetherCoords() {
        return Info.INSTANCE.isEnabled() && Info.INSTANCE.nethercoords.getValue();
    }
    
    public static boolean shouldHealth() {
        return Info.INSTANCE.isEnabled() && Info.INSTANCE.health.getValue();
    }
}
