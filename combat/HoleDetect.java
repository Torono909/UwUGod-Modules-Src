// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import meow.candycat.uwu.command.Command;
import meow.candycat.uwu.module.Module;

@Info(name = "HoleChangeInfo", category = Category.COMBAT, description = "tell u hole changes")
public class HoleDetect extends Module
{
    public static boolean inhole;
    public static boolean lastinhole;
    
    @Override
    public void onUpdate() {
        if (!HoleDetect.inhole && HoleDetect.lastinhole) {
            Command.sendChatMessage("§cYou are now not in a hole!");
        }
        if (HoleDetect.inhole && !HoleDetect.lastinhole) {
            Command.sendChatMessage("§aYou are now in a hole!");
        }
    }
}
