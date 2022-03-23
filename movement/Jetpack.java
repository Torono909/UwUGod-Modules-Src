// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(category = Category.MOVEMENT, description = "idk", name = "Jetpack")
public class Jetpack extends Module
{
    private Boolean owo;
    private Setting<Float> speed;
    private Setting<Float> movementspeed;
    
    public Jetpack() {
        this.owo = false;
        this.speed = this.register(Settings.f("Speed", 8.0f));
        this.movementspeed = this.register(Settings.f("MoveSpeed", 10.0f));
    }
    
    @Override
    protected void onEnable() {
        if (Jetpack.mc.field_71439_g == null) {
            return;
        }
    }
    
    @Override
    public void onUpdate() {
        Jetpack.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
        Jetpack.mc.field_71439_g.field_70747_aH = this.movementspeed.getValue() / 100.0f;
        if (Jetpack.mc.field_71474_y.field_74314_A.func_151470_d()) {
            final EntityPlayerSP field_71439_g = Jetpack.mc.field_71439_g;
            field_71439_g.field_70181_x += this.speed.getValue() / 100.0f;
        }
    }
}
