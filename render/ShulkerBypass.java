// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.render;

import meow.candycat.uwu.command.Command;
import net.minecraft.client.Minecraft;
import meow.candycat.uwu.module.Module;

@Info(name = "ShulkerBypass", category = Category.RENDER, description = "Bypasses the shulker preview patch on 2b2t")
public class ShulkerBypass extends Module
{
    public void onEnable() {
        if (Minecraft.func_71410_x().field_71439_g == null) {
            return;
        }
        Command.sendChatMessage("[ShulkerBypass] To use this throw a shulker on the ground");
    }
}
