// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "PredictHit", category = Category.COMBAT)
public class AutoCrystalLitePredictHit extends Module
{
    public Setting<Integer> predictHitValue;
    
    public AutoCrystalLitePredictHit() {
        this.predictHitValue = this.register((Setting<Integer>)Settings.integerBuilder("PredictHitValue").withMinimum(0).withValue(2).withMaximum(20).build());
    }
}
