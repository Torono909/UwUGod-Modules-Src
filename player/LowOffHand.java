// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.player;

import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "LowOffHand", category = Category.PLAYER)
public class LowOffHand extends Module
{
    private Setting<Float> offhandHeight;
    
    public LowOffHand() {
        this.offhandHeight = this.register(Settings.f("OffHandHeight", 0.75f));
    }
    
    @Override
    public void onUpdate() {
        LowOffHand.mc.field_71460_t.field_78516_c.field_187471_h = this.offhandHeight.getValue();
    }
    
    @Override
    public String getHudInfo() {
        return this.offhandHeight.getValue().toString();
    }
}
