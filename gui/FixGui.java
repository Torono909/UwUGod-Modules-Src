// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.gui;

import meow.candycat.uwu.util.GuiFrameUtil;
import meow.candycat.uwu.module.Module;

@Info(name = "FixGui", category = Category.GUI, showOnArray = ShowOnArray.OFF, description = "Moves GUI elements back on screen")
public class FixGui extends Module
{
    @Override
    public void onUpdate() {
        if (FixGui.mc.field_71439_g == null) {
            return;
        }
        GuiFrameUtil.fixFrames(FixGui.mc);
        this.disable();
    }
}
