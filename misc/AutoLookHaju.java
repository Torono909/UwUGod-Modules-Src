// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import net.minecraft.entity.Entity;
import java.util.Iterator;
import meow.candycat.uwu.module.modules.combat.Aimbot;
import meow.candycat.uwu.module.modules.combat.KillAura;
import meow.candycat.uwu.module.ModuleManager;
import meow.candycat.uwu.module.modules.combat.AutoCrystal;
import meow.candycat.uwu.util.EntityUtil;
import net.minecraft.entity.player.EntityPlayer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "AutoLookHaju", category = Category.MISC)
public class AutoLookHaju extends Module
{
    private Setting<Double> range;
    private boolean rotated;
    
    public AutoLookHaju() {
        this.range = this.register(Settings.d("Range", 6.0));
    }
    
    @Override
    public void onUpdate() {
        this.rotated = false;
        for (final EntityPlayer player : (List)AutoLookHaju.mc.field_71441_e.field_73010_i.stream().filter(e -> AutoLookHaju.mc.field_71439_g.func_70032_d(e) < this.range.getValue()).collect(Collectors.toList())) {
            if (player.func_70005_c_().equals("Haju_")) {
                final double[] a = EntityUtil.calculateLookAt(player.field_70165_t, player.field_70163_u + player.func_70047_e(), player.field_70161_v, (EntityPlayer)AutoLookHaju.mc.field_71439_g);
                if (((AutoCrystal)ModuleManager.getModuleByName("AutoCrystal")).rotated) {
                    continue;
                }
                final KillAura killAura = (KillAura)ModuleManager.getModuleByName("KillAura");
                if (KillAura.isAiming) {
                    continue;
                }
                ((Aimbot)ModuleManager.getModuleByName("Aimbot")).setRotation((float)a[0], (float)a[1]);
                this.rotated = true;
            }
        }
        if (!this.rotated && !((AutoCrystal)ModuleManager.getModuleByName("AutoCrystal")).rotated) {
            final KillAura killAura2 = (KillAura)ModuleManager.getModuleByName("KillAura");
            if (!KillAura.isAiming) {
                ((Aimbot)ModuleManager.getModuleByName("Aimbot")).resetRotation();
            }
        }
    }
    
    public void onDisable() {
        if (!((AutoCrystal)ModuleManager.getModuleByName("AutoCrystal")).rotated) {
            final KillAura killAura = (KillAura)ModuleManager.getModuleByName("KillAura");
            if (!KillAura.isAiming) {
                ((Aimbot)ModuleManager.getModuleByName("Aimbot")).resetRotation();
            }
        }
    }
    
    @Override
    public String getHudInfo() {
        return this.rotated ? "Looking" : "Idling";
    }
}
