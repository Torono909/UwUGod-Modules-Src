// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.render;

import meow.candycat.uwu.event.events.RenderEvent;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.chunk.Chunk;
import java.util.function.Predicate;
import net.minecraft.block.BlockPortal;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.ChunkEvent;
import meow.candycat.eventsystem.listener.Listener;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "PortalTracers", category = Category.RENDER)
public class PortalTracers extends Module
{
    private Setting<Integer> range;
    private ArrayList<BlockPos> portals;
    @EventHandler
    private Listener<ChunkEvent> loadListener;
    
    public PortalTracers() {
        this.range = this.register(Settings.i("Range", 5000));
        this.portals = new ArrayList<BlockPos>();
        final Chunk chunk;
        final Chunk chunk2;
        final ExtendedBlockStorage[] array;
        int length;
        int i = 0;
        ExtendedBlockStorage storage;
        int x;
        int y;
        int z;
        int px;
        int py;
        int pz;
        this.loadListener = new Listener<ChunkEvent>(event -> {
            chunk = event.getChunk();
            this.portals.removeIf(blockPos -> blockPos.func_177958_n() / 16 == chunk2.field_76635_g && blockPos.func_177952_p() / 16 == chunk2.field_76647_h);
            chunk.func_76587_i();
            for (length = array.length; i < length; ++i) {
                storage = array[i];
                if (storage != null) {
                    for (x = 0; x < 16; ++x) {
                        for (y = 0; y < 16; ++y) {
                            for (z = 0; z < 16; ++z) {
                                if (storage.func_177485_a(x, y, z).func_177230_c() instanceof BlockPortal) {
                                    px = chunk.field_76635_g * 16 + x;
                                    py = storage.field_76684_a + y;
                                    pz = chunk.field_76647_h * 16 + z;
                                    this.portals.add(new BlockPos(px, py, pz));
                                    y += 6;
                                }
                            }
                        }
                    }
                }
            }
        }, (Predicate<ChunkEvent>[])new Predicate[0]);
    }
    
    @Override
    public void onWorldRender(final RenderEvent event) {
        this.portals.stream().filter(blockPos -> PortalTracers.mc.field_71439_g.func_70011_f((double)blockPos.field_177962_a, (double)blockPos.field_177960_b, (double)blockPos.field_177961_c) <= this.range.getValue()).forEach(blockPos -> Tracers.drawLine(blockPos.field_177962_a - PortalTracers.mc.func_175598_ae().field_78725_b, blockPos.field_177960_b - PortalTracers.mc.func_175598_ae().field_78726_c, blockPos.field_177961_c - PortalTracers.mc.func_175598_ae().field_78723_d, 0.0, 0.6f, 0.3f, 0.8f, 1.0f));
    }
}
