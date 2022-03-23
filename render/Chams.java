// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.render;

import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.util.EntityUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "Chams", category = Category.RENDER, description = "See entities through walls")
public class Chams extends Module
{
    private static Setting<Boolean> players;
    private static Setting<Boolean> animals;
    private static Setting<Boolean> mobs;
    
    public Chams() {
        this.registerAll(Chams.players, Chams.animals, Chams.mobs);
    }
    
    public static boolean renderChams(final Entity entity) {
        return (entity instanceof EntityPlayer) ? Chams.players.getValue() : (EntityUtil.isPassive(entity) ? Chams.animals.getValue() : ((boolean)Chams.mobs.getValue()));
    }
    
    static {
        Chams.players = Settings.b("Players", true);
        Chams.animals = Settings.b("Animals", false);
        Chams.mobs = Settings.b("Mobs", false);
    }
}
