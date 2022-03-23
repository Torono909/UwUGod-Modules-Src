// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

public class AutoCrystalRewrite extends Module
{
    public Setting<Boolean> multiThreading;
    
    public AutoCrystalRewrite() {
        this.multiThreading = this.register(Settings.b("MultiThread", true));
    }
    
    @Override
    public void onRender() {
        if (this.multiThreading.getValue()) {
            return;
        }
    }
}
