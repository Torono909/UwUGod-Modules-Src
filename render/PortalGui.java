// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.render;

import net.minecraft.client.Minecraft;
import meow.candycat.uwu.module.Module;

@Info(category = Category.RENDER, description = "", name = "PortalGui")
public class PortalGui extends Module
{
    @Override
    public void onUpdate() {
        if (Minecraft.func_71410_x().field_71439_g.field_71087_bX) {
            Minecraft.func_71410_x().field_71439_g.field_71087_bX = false;
        }
    }
}
