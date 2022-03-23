// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import java.util.Iterator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import meow.candycat.uwu.util.Friends;
import net.minecraft.entity.Entity;
import java.util.List;
import meow.candycat.uwu.util.WorldUtils;
import net.minecraft.init.Blocks;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "AutoWeb", category = Category.COMBAT)
public class AutoWeb extends Module
{
    private Setting<Double> range;
    private Setting<Boolean> onlyInHole;
    
    public AutoWeb() {
        this.range = this.register(Settings.d("Range", 4.0));
        this.onlyInHole = this.register(Settings.b("Only in hole", true));
    }
    
    @Override
    public void onUpdate() {
        if (!this.isEnabled()) {
            return;
        }
        final int slot = WorldUtils.findBlock(Blocks.field_150321_G);
        if (slot == -1) {
            return;
        }
        final List<EntityPlayer> players = (List<EntityPlayer>)AutoWeb.mc.field_71441_e.field_73010_i.stream().filter(p -> AutoWeb.mc.field_71439_g.func_70032_d((Entity)p) <= this.range.getValue() && AutoWeb.mc.field_71439_g != p && !Friends.isFriend(p.func_70005_c_())).collect(Collectors.toList());
        if (players.size() > 0) {
            AutoWeb.mc.field_71439_g.field_71071_by.field_70461_c = slot;
        }
        for (final EntityPlayer player : players) {
            final BlockPos playerPos = new BlockPos((int)player.field_70165_t, (int)player.field_70163_u, (int)player.field_70161_v);
            if (this.onlyInHole.getValue() && !HoleDetect.inhole) {
                continue;
            }
            if (AutoWeb.mc.field_71441_e.func_180495_p(playerPos).func_185904_a().func_76222_j()) {
                WorldUtils.placeBlockMainHand(playerPos);
            }
            if (this.onlyInHole.getValue()) {
                continue;
            }
            if (AutoWeb.mc.field_71441_e.func_180495_p(playerPos.func_177982_a(1, 0, 0)).func_185904_a().func_76222_j()) {
                WorldUtils.placeBlockMainHand(playerPos.func_177982_a(1, 0, 0));
            }
            if (AutoWeb.mc.field_71441_e.func_180495_p(playerPos.func_177982_a(0, 0, 1)).func_185904_a().func_76222_j()) {
                WorldUtils.placeBlockMainHand(playerPos.func_177982_a(0, 0, 1));
            }
            if (AutoWeb.mc.field_71441_e.func_180495_p(playerPos.func_177982_a(0, 0, -1)).func_185904_a().func_76222_j()) {
                WorldUtils.placeBlockMainHand(playerPos.func_177982_a(0, 0, -1));
            }
            if (!AutoWeb.mc.field_71441_e.func_180495_p(playerPos.func_177982_a(-1, 0, 0)).func_185904_a().func_76222_j()) {
                continue;
            }
            WorldUtils.placeBlockMainHand(playerPos.func_177982_a(-1, 0, 0));
        }
    }
    
    private boolean lambdaonUpdate0(final EntityPlayer p) {
        return AutoWeb.mc.field_71439_g.func_70032_d((Entity)p) <= this.range.getValue() && !AutoWeb.mc.field_71439_g.equals((Object)p);
    }
}
