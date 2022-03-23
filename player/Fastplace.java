// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.player;

import meow.candycat.uwu.module.Module;

@Info(name = "FastPlace", category = Category.PLAYER, description = "Nullifies block place delay")
public class Fastplace extends Module
{
    @Override
    public void onUpdate() {
        Fastplace.mc.field_71467_ac = 0;
    }
}
