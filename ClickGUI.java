// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules;

import net.minecraft.client.gui.GuiScreen;
import meow.candycat.uwu.gui.uwugod.DisplayGuiScreen;
import meow.candycat.uwu.module.Module;

@Info(name = "ClickGUI", description = "Opens the Click GUI", category = Category.HIDDEN)
public class ClickGUI extends Module
{
    public ClickGUI() {
        this.getBind().setKey(21);
    }
    
    @Override
    protected void onEnable() {
        if (!(ClickGUI.mc.field_71462_r instanceof DisplayGuiScreen)) {
            ClickGUI.mc.func_147108_a((GuiScreen)new DisplayGuiScreen());
        }
    }
    
    @Override
    public void onUpdate() {
        if (!(ClickGUI.mc.field_71462_r instanceof DisplayGuiScreen)) {
            this.disable();
        }
    }
}
