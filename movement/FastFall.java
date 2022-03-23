// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "FastFall", category = Category.MOVEMENT)
public class FastFall extends Module
{
    private Setting<Double> fallingspeed;
    
    public FastFall() {
        this.fallingspeed = this.register(Settings.d("FallingSpeed", 0.6));
    }
    
    @Override
    public void onUpdate() {
        if (FastFall.mc.field_71439_g.field_70122_E && !FastFall.mc.field_71439_g.func_70090_H() && !FastFall.mc.field_71439_g.func_180799_ab()) {
            final EntityPlayerSP field_71439_g = FastFall.mc.field_71439_g;
            field_71439_g.field_70181_x -= this.fallingspeed.getValue();
        }
    }
}
