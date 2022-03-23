// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.render;

import java.util.Objects;
import net.minecraft.potion.Potion;
import net.minecraft.init.MobEffects;
import meow.candycat.uwu.module.Module;

@Info(name = "AntiNausea", category = Category.RENDER)
public class AntiNausea extends Module
{
    @Override
    public void onUpdate() {
        if (AntiNausea.mc.field_71439_g.func_70644_a(MobEffects.field_76431_k)) {
            AntiNausea.mc.field_71439_g.func_184596_c((Potion)Objects.requireNonNull(Potion.func_180142_b("nausea")));
        }
    }
}
