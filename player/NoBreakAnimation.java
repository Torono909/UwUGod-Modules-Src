// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.player;

import net.minecraft.network.Packet;
import java.util.Iterator;
import java.util.function.Predicate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import meow.candycat.uwu.module.Module;

@Info(name = "NoBreakAnimation", category = Category.PLAYER, description = "Prevents block break animation server side")
public class NoBreakAnimation extends Module
{
    private boolean isMining;
    private BlockPos lastPos;
    private EnumFacing lastFacing;
    @EventHandler
    public Listener<PacketEvent.Send> listener;
    
    public NoBreakAnimation() {
        this.isMining = false;
        this.lastPos = null;
        this.lastFacing = null;
        CPacketPlayerDigging cPacketPlayerDigging;
        final Iterator<Entity> iterator;
        Entity entity;
        this.listener = new Listener<PacketEvent.Send>(event -> {
            if (event.getPacket() instanceof CPacketPlayerDigging) {
                cPacketPlayerDigging = (CPacketPlayerDigging)event.getPacket();
                NoBreakAnimation.mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(cPacketPlayerDigging.func_179715_a())).iterator();
                while (iterator.hasNext()) {
                    entity = iterator.next();
                    if (entity instanceof EntityEnderCrystal) {
                        this.resetMining();
                        return;
                    }
                    else if (entity instanceof EntityLivingBase) {
                        this.resetMining();
                        return;
                    }
                    else {
                        continue;
                    }
                }
                if (cPacketPlayerDigging.func_180762_c().equals((Object)CPacketPlayerDigging.Action.START_DESTROY_BLOCK)) {
                    this.isMining = true;
                    this.setMiningInfo(cPacketPlayerDigging.func_179715_a(), cPacketPlayerDigging.func_179714_b());
                }
                if (cPacketPlayerDigging.func_180762_c().equals((Object)CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK)) {
                    this.resetMining();
                }
            }
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
    }
    
    @Override
    public void onUpdate() {
        if (!NoBreakAnimation.mc.field_71474_y.field_74312_F.func_151470_d()) {
            this.resetMining();
            return;
        }
        if (this.isMining && this.lastPos != null && this.lastFacing != null) {
            NoBreakAnimation.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.lastPos, this.lastFacing));
        }
    }
    
    private void setMiningInfo(final BlockPos lastPos, final EnumFacing lastFacing) {
        this.lastPos = lastPos;
        this.lastFacing = lastFacing;
    }
    
    public void resetMining() {
        this.isMining = false;
        this.lastPos = null;
        this.lastFacing = null;
    }
}
