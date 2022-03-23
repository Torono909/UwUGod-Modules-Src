// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.util;

import net.minecraft.entity.EntityLivingBase;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.ConcurrentModificationException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.HashMap;
import meow.candycat.uwu.module.Module;

@Info(name = "ResistanceUtil", category = Category.UTIL, showOnArray = ShowOnArray.OFF)
public class ResistanceDetector extends Module
{
    public HashMap<String, Integer> resistanceList;
    
    public ResistanceDetector() {
        this.resistanceList = new HashMap<String, Integer>();
    }
    
    public void onEnable() {
        final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        HashMap<String, Integer> a;
        ArrayList<Object> i;
        final HashMap<String, Integer> hashMap;
        final List<String> list;
        final Object o;
        scheduler.scheduleAtFixedRate(() -> {
            try {
                a = new HashMap<String, Integer>();
                i = new ArrayList<Object>();
                this.resistanceList.forEach((k, v) -> {
                    if (v > 0) {
                        hashMap.put(k, v - 1);
                    }
                    else {
                        list.add(k);
                    }
                    return;
                });
                a.forEach((k, v) -> {
                    if (this.resistanceList.containsKey(k)) {
                        this.resistanceList.replace(k, v);
                    }
                    return;
                });
                a.clear();
                i.forEach(w -> {
                    if (this.resistanceList.containsKey(o)) {
                        this.resistanceList.remove(o);
                    }
                });
            }
            catch (ConcurrentModificationException ex) {}
        }, 0L, 1L, TimeUnit.SECONDS);
    }
    
    @Override
    public void onUpdate() {
        if (ResistanceDetector.mc.field_71441_e != null && ResistanceDetector.mc.field_71439_g != null) {
            for (final EntityPlayer uwu : ResistanceDetector.mc.field_71441_e.field_73010_i) {
                if (((EntityLivingBase)uwu).func_110139_bj() >= 9.0f) {
                    if (this.resistanceList.containsKey(uwu.func_70005_c_())) {
                        this.resistanceList.remove(uwu.func_70005_c_());
                    }
                    this.resistanceList.put(uwu.func_70005_c_(), 180);
                }
            }
        }
    }
}
