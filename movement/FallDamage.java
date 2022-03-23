// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.movement;

import net.minecraft.util.DamageSource;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "FallDamageProducer", category = Category.MOVEMENT)
public class FallDamage extends Module
{
    private Setting<Integer> shouldSendPacket;
    
    public FallDamage() {
        this.shouldSendPacket = this.register(Settings.i("PacketAmount", 4));
    }
    
    public void onEnable() {
        FallDamage.mc.field_71439_g.func_70097_a(DamageSource.field_76379_h, 0.1f);
    }
}
