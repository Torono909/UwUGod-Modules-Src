// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.state.IBlockState;
import java.util.function.Predicate;
import meow.candycat.uwu.module.modules.combat.HoleDetect;
import meow.candycat.uwu.module.ModuleManager;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.EventPlayerTravel;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "Anchor", category = Category.MOVEMENT)
public class Anchor extends Module
{
    public Setting<Boolean> toggleStrafe;
    public Setting<Boolean> disable;
    @EventHandler
    private Listener<EventPlayerTravel> OnTravel;
    
    public Anchor() {
        this.toggleStrafe = this.register(Settings.b("AutoToggleOffStrafe"));
        this.disable = this.register(Settings.b("Toggleable"));
        BlockPos pos;
        int i;
        BlockPos newPos;
        IBlockState state;
        boolean type;
        Vec3d center;
        double xDiff;
        double zDiff;
        double x;
        double z;
        EntityPlayerSP field_71439_g;
        this.OnTravel = new Listener<EventPlayerTravel>(event -> {
            if (!event.isCancelled()) {
                pos = new BlockPos(Math.floor(Anchor.mc.field_71439_g.field_70165_t), Math.floor(Anchor.mc.field_71439_g.field_70163_u), Math.floor(Anchor.mc.field_71439_g.field_70161_v));
                if (!Anchor.mc.field_71439_g.field_70122_E) {
                    for (i = 0; i < 5; ++i) {
                        newPos = pos.func_177982_a(0, -i, 0);
                        state = Anchor.mc.field_71441_e.func_180495_p(newPos);
                        if (state.func_177230_c() != Blocks.field_150350_a) {
                            type = isBlockValid(Anchor.mc, state, newPos);
                            if (!(!type)) {
                                if (ModuleManager.getModuleByName("Speed").isEnabled() && this.toggleStrafe.getValue()) {
                                    ModuleManager.getModuleByName("Speed").toggle();
                                }
                                Anchor.mc.field_71439_g.field_70159_w = 0.0;
                                Anchor.mc.field_71439_g.field_70179_y = 0.0;
                                Anchor.mc.field_71439_g.field_71158_b.field_192832_b = 0.0f;
                                Anchor.mc.field_71439_g.field_71158_b.field_78902_a = 0.0f;
                                center = GetCenter(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
                                xDiff = Math.abs(center.field_72450_a - Anchor.mc.field_71439_g.field_70165_t);
                                zDiff = Math.abs(center.field_72449_c - Anchor.mc.field_71439_g.field_70161_v);
                                if (xDiff <= 0.1 && zDiff <= 0.1) {
                                    return;
                                }
                                else {
                                    x = center.field_72450_a - Anchor.mc.field_71439_g.field_70165_t;
                                    z = center.field_72449_c - Anchor.mc.field_71439_g.field_70161_v;
                                    Anchor.mc.field_71439_g.field_70159_w = x / 2.0;
                                    if (Anchor.mc.field_71439_g.field_70181_x >= 0.0) {
                                        field_71439_g = Anchor.mc.field_71439_g;
                                        --field_71439_g.field_70181_x;
                                    }
                                    Anchor.mc.field_71439_g.field_70179_y = z / 2.0;
                                    event.cancel();
                                    return;
                                }
                            }
                        }
                    }
                }
                else if (HoleDetect.inhole && this.disable.getValue()) {
                    this.toggle();
                }
            }
        }, (Predicate<EventPlayerTravel>[])new Predicate[0]);
    }
    
    public static boolean isBlockValid(final Minecraft mc, final IBlockState blockState, final BlockPos blockPos) {
        if (blockState.func_177230_c() != Blocks.field_150350_a) {
            return false;
        }
        if (mc.field_71441_e.func_180495_p(blockPos.func_177984_a()).func_177230_c() != Blocks.field_150350_a) {
            return false;
        }
        if (mc.field_71441_e.func_180495_p(blockPos.func_177981_b(2)).func_177230_c() != Blocks.field_150350_a) {
            return false;
        }
        if (mc.field_71441_e.func_180495_p(blockPos.func_177977_b()).func_177230_c() == Blocks.field_150350_a) {
            return false;
        }
        final BlockPos[] touchingBlocks = { blockPos.func_177978_c(), blockPos.func_177968_d(), blockPos.func_177974_f(), blockPos.func_177976_e() };
        boolean l_Bedrock = true;
        boolean l_Obsidian = true;
        int validHorizontalBlocks = 0;
        for (final BlockPos touching : touchingBlocks) {
            final IBlockState touchingState = mc.field_71441_e.func_180495_p(touching);
            if (touchingState.func_177230_c() != Blocks.field_150350_a) {
                if (touchingState.func_185913_b()) {
                    ++validHorizontalBlocks;
                    if (touchingState.func_177230_c() != Blocks.field_150357_h && l_Bedrock) {
                        l_Bedrock = false;
                    }
                    if (!l_Bedrock && touchingState.func_177230_c() != Blocks.field_150343_Z) {
                        if (touchingState.func_177230_c() != Blocks.field_150357_h) {
                            l_Obsidian = false;
                        }
                    }
                }
            }
        }
        return validHorizontalBlocks >= 4 && (l_Bedrock || !l_Obsidian || true);
    }
    
    public static Vec3d GetCenter(final double posX, final double posY, final double posZ) {
        final double x = Math.floor(posX) + 0.5;
        final double y = Math.floor(posY);
        final double z = Math.floor(posZ) + 0.5;
        return new Vec3d(x, y, z);
    }
}
