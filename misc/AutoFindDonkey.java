// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import net.minecraft.entity.Entity;
import java.util.Iterator;
import meow.candycat.uwu.command.Command;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.ArrayList;
import net.minecraft.entity.passive.EntityDonkey;
import java.util.List;
import meow.candycat.uwu.module.Module;

@Info(name = "AutoFindDonkey", category = Category.MISC)
public class AutoFindDonkey extends Module
{
    private List<EntityDonkey> donkeyList;
    
    public AutoFindDonkey() {
        this.donkeyList = new ArrayList<EntityDonkey>();
    }
    
    @Override
    public void onUpdate() {
        final List<EntityDonkey> w = (List<EntityDonkey>)AutoFindDonkey.mc.field_71441_e.field_72996_f.stream().filter(e -> e instanceof EntityDonkey).map(e -> e).collect(Collectors.toList());
        for (final EntityDonkey Donkey : w) {
            if (!this.donkeyList.contains(Donkey)) {
                Command.sendChatMessage("Found a donkey in X: " + Donkey.field_70165_t + " Y: " + Donkey.field_70163_u + " Z: " + Donkey.field_70161_v);
                this.donkeyList.add(Donkey);
            }
        }
    }
}
