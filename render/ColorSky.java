// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "ColorSky", description = "ChangeColors", category = Category.RENDER)
public class ColorSky extends Module
{
    private Setting<Float> red;
    private Setting<Float> green;
    private Setting<Float> blue;
    
    public ColorSky() {
        this.red = this.register((Setting<Float>)Settings.floatBuilder("Red").withRange(0.0f, 1.0f).withValue(0.5f).build());
        this.green = this.register((Setting<Float>)Settings.floatBuilder("Green").withRange(0.0f, 1.0f).withValue(0.5f).build());
        this.blue = this.register((Setting<Float>)Settings.floatBuilder("Blue").withRange(0.0f, 1.0f).withValue(0.5f).build());
    }
    
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @SubscribeEvent
    public void fogColors(final EntityViewRenderEvent.FogColors a) {
        a.setRed((float)this.red.getValue());
        a.setGreen((float)this.green.getValue());
        a.setBlue((float)this.blue.getValue());
    }
    
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void fogColorsss(final EntityViewRenderEvent.FogDensity a) {
        a.setDensity(0.1f);
    }
    
    @SubscribeEvent
    public void fogColorss(final EntityViewRenderEvent.RenderFogEvent w) {
    }
}
