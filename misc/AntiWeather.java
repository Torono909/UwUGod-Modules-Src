// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import meow.candycat.uwu.module.Module;

@Info(name = "AntiWeather", description = "Removes rain from your world", category = Category.MISC)
public class AntiWeather extends Module
{
    @Override
    public void onUpdate() {
        if (this.isDisabled()) {
            return;
        }
        if (AntiWeather.mc.field_71441_e.func_72896_J()) {
            AntiWeather.mc.field_71441_e.func_72894_k(0.0f);
        }
    }
}
