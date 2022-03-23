// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.util;

import meow.candycat.uwu.module.modules.combat.SmartOffHand;
import meow.candycat.uwu.module.ModuleManager;
import meow.candycat.uwu.module.modules.combat.AutoCrystal;
import java.util.ArrayList;
import net.minecraft.util.math.BlockPos;
import java.util.List;
import meow.candycat.uwu.module.Module;

@Info(name = "CrystalUtil", category = Category.UTIL)
public class CrystalUtil extends Module
{
    public List<BlockPos> sphere;
    
    public CrystalUtil() {
        this.sphere = new ArrayList<BlockPos>();
    }
    
    @Override
    public void onUpdate() {
        final double radius = (double)((((AutoCrystal)ModuleManager.getModuleByName("AutoCrystal")).placerange.getValue() + 0.5 > ((SmartOffHand)ModuleManager.getModuleByName("SmartOffHand")).detectrange.getValue()) ? (((AutoCrystal)ModuleManager.getModuleByName("AutoCrystal")).placerange.getValue() + 0.5) : ((SmartOffHand)ModuleManager.getModuleByName("SmartOffHand")).detectrange.getValue());
    }
}
