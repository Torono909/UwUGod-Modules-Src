// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "AutoDamage", category = Category.COMBAT)
public class AutoDamage extends Module
{
    private Setting<Boolean> players;
    private Setting<Boolean> mobs;
    private Setting<Boolean> animals;
    public Setting<auras> damageModeSetting;
    public Setting<Boolean> killAuraEnabled;
    private Setting<Boolean> ghostSwitchSword;
    private Setting<Integer> hitDelay;
    private Setting<killAuraMode> KillAuraMode;
    private Setting<killAuraPriorityMode> KillAuraPriority;
    private Setting<Double> killAuraRange;
    
    public AutoDamage() {
        this.players = this.register(Settings.b("Player", true));
        this.mobs = this.register(Settings.b("Mobs", false));
        this.animals = this.register(Settings.b("Animal", false));
        this.damageModeSetting = this.register(Settings.e("DamageMode", auras.KillAura));
        this.killAuraEnabled = this.register(Settings.booleanBuilder("Enabled").withValue(true).withVisibility(e -> this.damageModeSetting.getValue().equals(auras.KillAura)).build());
        this.ghostSwitchSword = this.register(Settings.b("GhostSwitchSword", false));
        this.hitDelay = this.register(Settings.i("HitDelay", 37));
        this.KillAuraMode = this.register(Settings.e("Mode", killAuraMode.SINGLE));
        this.KillAuraPriority = this.register(Settings.e("Priority", killAuraPriorityMode.DISTANCE));
        this.killAuraRange = this.register(Settings.d("Range", 6.0));
    }
    
    public enum auras
    {
        KillAura, 
        AutoCrystal;
    }
    
    public enum killAuraMode
    {
        SINGLE, 
        SWITCH;
    }
    
    public enum killAuraPriorityMode
    {
        HEALTH, 
        DISTANCE;
    }
}
