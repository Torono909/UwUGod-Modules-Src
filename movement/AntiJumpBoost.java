// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.movement;

import net.minecraft.init.MobEffects;
import meow.candycat.uwu.module.Module;

@Info(name = "AntiJumpBoost", category = Category.RENDER)
public class AntiJumpBoost extends Module
{
    @Override
    public void onUpdate() {
        if (AntiJumpBoost.mc.field_71439_g.func_70644_a(MobEffects.field_76430_j)) {
            AntiJumpBoost.mc.field_71439_g.func_184596_c(MobEffects.field_76430_j);
        }
    }
}
