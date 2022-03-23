// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import meow.candycat.uwu.module.Module;

@Info(name = "FakePlayer", category = Category.MISC, description = "Spawns a fake Player")
public class FakePlayer extends Module
{
    @Override
    protected void onEnable() {
        if (FakePlayer.mc.field_71441_e == null) {
            return;
        }
        final EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP((World)FakePlayer.mc.field_71441_e, new GameProfile(UUID.fromString("e195d3d7-e6dc-456e-8393-e2f90816a1af"), "Hausemaster"));
        fakePlayer.func_82149_j((Entity)FakePlayer.mc.field_71439_g);
        fakePlayer.field_70759_as = FakePlayer.mc.field_71439_g.field_70759_as;
        FakePlayer.mc.field_71441_e.func_73027_a(-100, (Entity)fakePlayer);
        this.disable();
    }
}
