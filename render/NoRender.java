// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.render;

import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import meow.candycat.uwu.event.events.RenderEvent;
import net.minecraft.network.Packet;
import net.minecraft.block.material.Material;
import java.util.function.Predicate;
import net.minecraft.network.play.server.SPacketSpawnPainting;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.SPacketSpawnMob;
import meow.candycat.uwu.setting.Settings;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "NoRender", category = Category.RENDER, description = "Ignores entity spawn packets")
public class NoRender extends Module
{
    private Setting<Boolean> mob;
    private Setting<Boolean> sand;
    private Setting<Boolean> gentity;
    private Setting<Boolean> object;
    private Setting<Boolean> xp;
    private Setting<Boolean> paint;
    private Setting<Boolean> fire;
    private Setting<Boolean> explosion;
    private Setting<Boolean> noOverlay;
    public Setting<Boolean> noSkylight;
    @EventHandler
    public Listener<PacketEvent.Receive> receiveListener;
    @EventHandler
    public Listener<RenderBlockOverlayEvent> blockOverlayEventListener;
    @EventHandler
    private final Listener<RenderBlockOverlayEvent> renderBlockOverlayEventListener;
    @EventHandler
    private final Listener<EntityViewRenderEvent.FogDensity> fogDensityListener;
    
    public NoRender() {
        this.mob = this.register(Settings.b("Mob", false));
        this.sand = this.register(Settings.b("Sand", false));
        this.gentity = this.register(Settings.b("GEntity", false));
        this.object = this.register(Settings.b("Object", false));
        this.xp = this.register(Settings.b("XP", false));
        this.paint = this.register(Settings.b("Paintings", false));
        this.fire = this.register(Settings.b("Fire"));
        this.explosion = this.register(Settings.b("Explosions"));
        this.noOverlay = this.register(Settings.b("NoOverlay"));
        this.noSkylight = this.register(Settings.b("NoSkylight"));
        final Packet packet;
        this.receiveListener = new Listener<PacketEvent.Receive>(event -> {
            packet = event.getPacket();
            if ((packet instanceof SPacketSpawnMob && this.mob.getValue()) || (packet instanceof SPacketSpawnGlobalEntity && this.gentity.getValue()) || (packet instanceof SPacketSpawnObject && this.object.getValue()) || (packet instanceof SPacketSpawnExperienceOrb && this.xp.getValue()) || (packet instanceof SPacketSpawnObject && this.sand.getValue()) || (packet instanceof SPacketExplosion && this.explosion.getValue()) || (packet instanceof SPacketSpawnPainting && this.paint.getValue())) {
                event.cancel();
            }
            return;
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
        this.blockOverlayEventListener = new Listener<RenderBlockOverlayEvent>(event -> {
            if (this.fire.getValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE) {
                event.setCanceled(true);
            }
            if (this.noOverlay.getValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.WATER) {
                event.setCanceled(true);
            }
            if (this.noOverlay.getValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.BLOCK) {
                event.setCanceled(true);
            }
            return;
        }, (Predicate<RenderBlockOverlayEvent>[])new Predicate[0]);
        this.renderBlockOverlayEventListener = new Listener<RenderBlockOverlayEvent>(event -> event.setCanceled(true), (Predicate<RenderBlockOverlayEvent>[])new Predicate[0]);
        this.fogDensityListener = new Listener<EntityViewRenderEvent.FogDensity>(event -> {
            if (this.noOverlay.getValue() && (event.getState().func_185904_a().equals(Material.field_151586_h) || event.getState().func_185904_a().equals(Material.field_151587_i))) {
                event.setDensity(0.0f);
                event.setCanceled(true);
            }
        }, (Predicate<EntityViewRenderEvent.FogDensity>[])new Predicate[0]);
    }
    
    @Override
    public void onWorldRender(final RenderEvent x) {
        final ItemStack item = NoRender.mc.field_71460_t.field_190566_ab;
        if (item != null && item.func_77973_b() == Items.field_190929_cY) {
            NoRender.mc.field_71460_t.field_190566_ab = null;
        }
    }
}
