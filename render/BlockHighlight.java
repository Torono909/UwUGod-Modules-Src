// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.GameType;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.state.IBlockState;
import meow.candycat.uwu.util.UwUGodTessellator;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import meow.candycat.uwu.util.MathUtil;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.client.Minecraft;
import meow.candycat.uwu.event.events.RenderEvent;
import net.minecraftforge.common.MinecraftForge;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import net.minecraft.util.math.BlockPos;
import meow.candycat.uwu.module.Module;

@Info(name = "BlockHighlight", category = Category.RENDER, description = "Make selected block bounding box more visible")
public class BlockHighlight extends Module
{
    private static BlockPos position;
    private Setting<Integer> red;
    private Setting<Integer> green;
    private Setting<Integer> blue;
    private Setting<Integer> alpha;
    private Setting<Float> width;
    
    public BlockHighlight() {
        this.red = this.register((Setting<Integer>)Settings.integerBuilder("Red").withRange(0, 255).withValue(255).build());
        this.green = this.register((Setting<Integer>)Settings.integerBuilder("Green").withRange(0, 255).withValue(0).build());
        this.blue = this.register((Setting<Integer>)Settings.integerBuilder("Blue").withRange(0, 255).withValue(0).build());
        this.alpha = this.register((Setting<Integer>)Settings.integerBuilder("Transparency").withRange(0, 255).withValue(70).build());
        this.width = this.register((Setting<Float>)Settings.floatBuilder("Thickness").withRange(1.0f, 10.0f).withValue(1.0f).build());
    }
    
    @Override
    protected void onEnable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Override
    protected void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        BlockHighlight.position = null;
    }
    
    @Override
    public void onWorldRender(final RenderEvent event) {
        final Minecraft mc = Minecraft.func_71410_x();
        final RayTraceResult ray = mc.field_71476_x;
        if (ray != null && ray.field_72313_a == RayTraceResult.Type.BLOCK) {
            final BlockPos blockpos = ray.func_178782_a();
            final IBlockState iblockstate = mc.field_71441_e.func_180495_p(blockpos);
            if (iblockstate.func_185904_a() != Material.field_151579_a && mc.field_71441_e.func_175723_af().func_177746_a(blockpos)) {
                final Vec3d interp = MathUtil.interpolateEntity((Entity)mc.field_71439_g, mc.func_184121_ak());
                UwUGodTessellator.drawBoundingBox(iblockstate.func_185918_c((World)mc.field_71441_e, blockpos).func_186662_g(0.0020000000949949026).func_72317_d(-interp.field_72450_a, -interp.field_72448_b, -interp.field_72449_c), this.width.getValue(), this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue());
            }
        }
    }
    
    @SubscribeEvent
    public void onDrawBlockHighlight(final DrawBlockHighlightEvent event) {
        if (BlockHighlight.mc.field_71439_g == null || BlockHighlight.mc.field_71441_e == null || (!BlockHighlight.mc.field_71442_b.func_178889_l().equals((Object)GameType.SURVIVAL) && !BlockHighlight.mc.field_71442_b.func_178889_l().equals((Object)GameType.CREATIVE))) {
            return;
        }
        event.setCanceled(true);
    }
}
