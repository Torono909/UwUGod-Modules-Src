// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.movement;

import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(category = Category.MOVEMENT, name = "AirJump")
public class AirJump extends Module
{
    private boolean owo;
    private Setting<Float> speed;
    private Setting<Float> movementspeed;
    
    public AirJump() {
        this.owo = false;
        this.speed = this.register(Settings.f("Speed", 5.0f));
        this.movementspeed = this.register(Settings.f("MoveSpeed", 10.0f));
    }
    
    @Override
    protected void onEnable() {
        if (AirJump.mc.field_71439_g == null) {
            return;
        }
    }
    
    @Override
    public void onUpdate() {
        AirJump.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
        AirJump.mc.field_71439_g.field_70747_aH = this.movementspeed.getValue() / 100.0f;
        if (AirJump.mc.field_71474_y.field_74314_A.func_151470_d()) {
            if (!this.owo) {
                AirJump.mc.field_71439_g.field_70181_x = this.speed.getValue() / 10.0f;
                this.owo = true;
            }
        }
        else if (!AirJump.mc.field_71474_y.field_74314_A.func_151470_d()) {
            this.owo = false;
        }
    }
}
