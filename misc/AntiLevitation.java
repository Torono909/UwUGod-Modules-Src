// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import net.minecraft.init.MobEffects;
import meow.candycat.uwu.module.Module;

@Info(name = "AntiLevitation", category = Category.MISC)
public class AntiLevitation extends Module
{
    @Override
    public void onUpdate() {
        if (AntiLevitation.mc.field_71439_g.func_70644_a(MobEffects.field_188424_y)) {
            AntiLevitation.mc.field_71439_g.func_184596_c(MobEffects.field_188424_y);
        }
    }
}
