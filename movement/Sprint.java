// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.movement;

import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "Sprint", category = Category.MOVEMENT)
public class Sprint extends Module
{
    public Setting<Mode> mode;
    
    public Sprint() {
        this.mode = this.register(Settings.e("Mode", Mode.RAGE));
    }
    
    @Override
    public void onUpdate() {
        if (this.mode.getValue() == Mode.RAGE) {
            if ((Sprint.mc.field_71439_g.field_71158_b.field_192832_b != 0.0f || Sprint.mc.field_71439_g.field_71158_b.field_78902_a != 0.0f) && !Sprint.mc.field_71439_g.func_70093_af() && !Sprint.mc.field_71439_g.field_70123_F && Sprint.mc.field_71439_g.func_71024_bL().func_75116_a() > 6.0f) {
                Sprint.mc.field_71439_g.func_70031_b(true);
            }
        }
        else if (Sprint.mc.field_71474_y.field_74351_w.func_151470_d()) {
            Sprint.mc.field_71439_g.func_70031_b(true);
        }
    }
    
    @Override
    public String getHudInfo() {
        return (this.mode.getValue() == Mode.RAGE) ? "Rage" : "Legit";
    }
    
    public enum Mode
    {
        RAGE, 
        LEGIT;
    }
}
