// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.gui;

import org.lwjgl.input.Keyboard;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "Zoom", category = Category.GUI, description = "Configures FOV", showOnArray = ShowOnArray.OFF)
public class Zoom extends Module
{
    private float fov;
    private float sensi;
    private Setting<Integer> fovChange;
    private Setting<Float> sensChange;
    private Setting<Boolean> smoothCamera;
    private Setting<Boolean> sens;
    private Setting<Boolean> smoothZoom;
    float i;
    
    public Zoom() {
        this.fov = 0.0f;
        this.sensi = 0.0f;
        this.fovChange = this.register(Settings.i("Fov", 90));
        this.sensChange = this.register((Setting<Float>)Settings.floatBuilder("Sensitivity").withMinimum(0.25f).withValue(1.3f).withMaximum(2.0f).build());
        this.smoothCamera = this.register(Settings.b("Cinematic Camera", true));
        this.sens = this.register(Settings.b("Sensitivity", true));
        this.smoothZoom = this.register(Settings.b("SmoothZoom", true));
        this.i = 0.0f;
    }
    
    public void onEnable() {
        if (Zoom.mc.field_71439_g == null) {
            return;
        }
        final float field_74334_X = Zoom.mc.field_71474_y.field_74334_X;
        this.i = field_74334_X;
        this.fov = field_74334_X;
        this.sensi = Zoom.mc.field_71474_y.field_74341_c;
        if (this.smoothCamera.getValue()) {
            Zoom.mc.field_71474_y.field_74326_T = true;
        }
    }
    
    public void onDisable() {
        Zoom.mc.field_71474_y.field_74334_X = this.fov;
        Zoom.mc.field_71474_y.field_74341_c = this.sensi;
        if (this.smoothCamera.getValue()) {
            Zoom.mc.field_71474_y.field_74326_T = false;
        }
    }
    
    @Override
    public void onUpdate() {
        if (Zoom.mc.field_71439_g == null) {
            return;
        }
        if (!Keyboard.isKeyDown(this.getBind().getKey())) {
            this.disable();
        }
        if (this.smoothZoom.getValue()) {
            if (this.fovChange.getValue() > this.i) {
                this.i -= 3.0f;
                if (this.i < this.fovChange.getValue()) {
                    this.i = this.fovChange.getValue();
                }
            }
            Zoom.mc.field_71474_y.field_74334_X = this.i;
        }
        else {
            Zoom.mc.field_71474_y.field_74334_X = this.fovChange.getValue();
        }
        Zoom.mc.field_71474_y.field_74326_T = this.smoothCamera.getValue();
        if (this.sens.getValue()) {
            Zoom.mc.field_71474_y.field_74341_c = this.sensi * this.sensChange.getValue();
        }
    }
    
    @Override
    public void onRender() {
    }
    
    @Override
    public String getHudInfo() {
        return this.fovChange.getValue().toString();
    }
}
