// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import meow.candycat.uwu.DiscordPresence;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "DiscordRPC", category = Category.MISC, description = "Discord Rich Presence")
public class BlueDiscordRPC extends Module
{
    public Setting<Boolean> startupGlobal;
    public Setting<Boolean> versionGlobal;
    public Setting<Boolean> usernameGlobal;
    public Setting<Boolean> hpGlobal;
    public Setting<Boolean> ipGlobal;
    
    public BlueDiscordRPC() {
        this.startupGlobal = this.register(Settings.b("Enable Automatically", true));
        this.versionGlobal = this.register(Settings.b("Version", true));
        this.usernameGlobal = this.register(Settings.b("Username", true));
        this.hpGlobal = this.register(Settings.b("Health", true));
        this.ipGlobal = this.register(Settings.b("Server IP", true));
    }
    
    public void onEnable() {
        DiscordPresence.start();
    }
    
    public void onDisable() {
        DiscordPresence.disable();
    }
}
