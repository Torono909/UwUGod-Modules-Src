// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.gui;

import meow.candycat.uwu.module.ModuleManager;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "Gui", category = Category.GUI, description = "Changes options with the gui", showOnArray = ShowOnArray.OFF)
public class Gui extends Module
{
    public Setting backToDefault;
    public Setting<Mode> mode;
    private Setting<ButtonMode> buttonMode;
    public Setting<Boolean> background;
    private Setting<Boolean> blur;
    private Setting<Boolean> particles;
    public Setting<Boolean> rainbow;
    public Setting MRed;
    public Setting MGreen;
    public Setting MBlue;
    public Setting MAlpha;
    public Setting BRed;
    public Setting BGreen;
    public Setting BBlue;
    public Setting SAlpha;
    
    public Gui() {
        this.backToDefault = this.register(Settings.b("Default", false));
        this.mode = this.register(Settings.e("Theme", Mode.s2));
        this.buttonMode = this.register(Settings.e("ButtonMode", ButtonMode.HIGHLIGHT));
        this.background = this.register(Settings.booleanBuilder("Background").withValue(false).build());
        this.blur = this.register(Settings.booleanBuilder("Blur").withValue(true).withVisibility(b -> this.background.getValue()).build());
        this.particles = this.register(Settings.booleanBuilder("Particles").withValue(true).withVisibility(b -> this.background.getValue()).build());
        this.rainbow = this.register(Settings.booleanBuilder("Rainbow").withValue(false).build());
        this.MRed = this.register((Setting<Object>)Settings.floatBuilder("Red Main").withMinimum(0.0f).withMaximum(1.0f).withValue(0.0f).build());
        this.MGreen = this.register((Setting<Object>)Settings.floatBuilder("Green Main").withMinimum(0.0f).withMaximum(1.0f).withValue(0.0f).build());
        this.MBlue = this.register((Setting<Object>)Settings.floatBuilder("Blue Main").withMinimum(0.0f).withMaximum(1.0f).withValue(0.0f).build());
        this.MAlpha = this.register((Setting<Object>)Settings.floatBuilder("Alpha Main").withMinimum(0.0f).withMaximum(1.0f).withValue(0.5f).build());
        this.BRed = this.register((Setting<Object>)Settings.floatBuilder("Red Border").withMinimum(0.0f).withMaximum(1.0f).withValue(0.1f).build());
        this.BGreen = this.register((Setting<Object>)Settings.floatBuilder("Green Border").withMinimum(0.0f).withMaximum(1.0f).withValue(0.25f).build());
        this.BBlue = this.register((Setting<Object>)Settings.floatBuilder("Blue Border").withMinimum(0.0f).withMaximum(1.0f).withValue(0.5f).build());
        this.SAlpha = this.register((Setting<Object>)Settings.floatBuilder("Alpha SettingUI").withMinimum(0.0f).withMaximum(1.0f).withValue(0.7f).build());
    }
    
    public boolean getBlur() {
        return this.blur.getValue();
    }
    
    public boolean getParticles() {
        return this.particles.getValue();
    }
    
    public Enum getBTMode() {
        return this.buttonMode.getValue();
    }
    
    @Override
    public void onUpdate() {
        if (this.backToDefault.getValue()) {
            this.MRed.setValue(0.0f);
            this.MGreen.setValue(0.0f);
            this.MBlue.setValue(0.0f);
            this.MAlpha.setValue(0.75f);
            this.BRed.setValue(0.1f);
            this.BGreen.setValue(0.25f);
            this.BBlue.setValue(0.5f);
            this.SAlpha.setValue(0.7f);
        }
    }
    
    public void onDisable() {
        final Module module = ModuleManager.getModuleByName("Gui");
        module.enable();
    }
    
    public enum Mode
    {
        s1, 
        s2, 
        s3, 
        KAMI, 
        Modern, 
        Modern2;
    }
    
    public enum ButtonMode
    {
        FONT, 
        HIGHLIGHT;
    }
}
