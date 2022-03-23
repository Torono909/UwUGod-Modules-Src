// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.player;

import net.minecraft.client.Minecraft;
import meow.candycat.uwu.util.LagCompensator;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "Timer", category = Category.PLAYER, description = "Changes your client tick speed")
public class Timer extends Module
{
    private Setting<Double> speed;
    private Setting<Boolean> tpssync;
    
    public Timer() {
        this.speed = this.register(Settings.d("Speed", 1.0));
        this.tpssync = this.register(Settings.b("TPSSync"));
    }
    
    @Override
    public String getHudInfo() {
        return this.tpssync.getValue() ? Float.toString(LagCompensator.INSTANCE.getTickRate() / 20.0f) : ((this.speed.getValue() > 0.1) ? this.speed.getValue().toString() : "0.1");
    }
    
    public void onDisable() {
        Minecraft.func_71410_x().field_71428_T.field_194149_e = 50.0f;
    }
    
    @Override
    public void onUpdate() {
        if (Timer.mc.field_71439_g != null) {
            double a;
            if (this.tpssync.getValue()) {
                a = 50.0;
                a /= LagCompensator.INSTANCE.getTickRate();
                a *= 20.0;
            }
            else {
                a = 50.0;
                a /= Math.max(this.speed.getValue(), 0.1);
            }
            Minecraft.func_71410_x().field_71428_T.field_194149_e = (float)a;
        }
    }
}
