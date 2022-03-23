// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import java.util.Iterator;
import meow.candycat.uwu.module.ModuleManager;
import net.minecraft.entity.player.EntityPlayer;
import meow.candycat.uwu.util.EntityUtil;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.Entity;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "CrystalWarning", category = Category.COMBAT)
public class CrystalWarning extends Module
{
    private Setting<Integer> warninglevel;
    
    public CrystalWarning() {
        this.warninglevel = this.register(Settings.i("Warning Crystal Damage", 6));
    }
    
    @Override
    public void onUpdate() {
        for (final Entity kami : CrystalWarning.mc.field_71441_e.field_72996_f) {
            if (!(kami instanceof EntityEnderCrystal)) {
                continue;
            }
            if (CrystalWarning.mc.field_71439_g.func_70032_d(kami) > 13.0f) {
                continue;
            }
            final double qwq = AutoCrystal.calculateDamage(kami, (Entity)CrystalWarning.mc.field_71439_g);
            if (qwq >= this.warninglevel.getValue()) {
                final double[] awa = EntityUtil.calculateLookAt(kami.field_70165_t, kami.field_70163_u, kami.field_70161_v, (EntityPlayer)CrystalWarning.mc.field_71439_g);
                kami.func_184195_f(true);
                ((AutoCrystal)ModuleManager.getModuleByName("AutoCrystal")).danger = true;
            }
            else {
                kami.func_184195_f(false);
            }
        }
    }
    
    private String CalculateDirection(double yaw, final double playeryaw) {
        if (yaw < playeryaw) {
            yaw += 360.0;
        }
        final double qwq = yaw - playeryaw;
        if (qwq >= 45.0 && qwq <= 135.0) {
            return "right";
        }
        if (qwq <= 225.0) {
            return "back";
        }
        if (qwq <= 315.0) {
            return "left";
        }
        return "front";
    }
}
