// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import java.util.function.Predicate;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "AntiGapDisease", description = "Attempts to stop gapple disease and makes eating easier in low tps", category = Category.COMBAT)
public class BetterGapple extends Module
{
    boolean timer;
    double waitTicks;
    boolean switched;
    int lastSlot;
    private Setting<Double> wait;
    public Setting<Boolean> cancelUseItemPacketWhenSwitched;
    int currentSlot;
    @EventHandler
    private Listener<PacketEvent.Send> listener;
    
    public BetterGapple() {
        this.timer = false;
        this.switched = false;
        this.wait = this.register(Settings.d("Switch wait", 3.0));
        this.cancelUseItemPacketWhenSwitched = this.register(Settings.b("CancelUseItemPacketWhenSwitched"));
        this.currentSlot = -1;
        this.listener = new Listener<PacketEvent.Send>(e -> {
            if (e.getPacket() instanceof CPacketHeldItemChange) {
                this.currentSlot = ((CPacketHeldItemChange)e.getPacket()).func_149614_c();
            }
            if (this.switched && this.cancelUseItemPacketWhenSwitched.getValue()) {
                if (e.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                    if (((CPacketPlayerTryUseItemOnBlock)e.getPacket()).func_187022_c() == EnumHand.MAIN_HAND) {
                        e.cancel();
                    }
                }
                else if (e.getPacket() instanceof CPacketPlayerTryUseItem && ((CPacketPlayerTryUseItem)e.getPacket()).func_187028_a() == EnumHand.MAIN_HAND) {
                    e.cancel();
                }
            }
            else {
                Label_0201_1: {
                    if (e.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                        if (this.currentSlot == -1) {
                            if (BetterGapple.mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_151153_ao) {
                                break Label_0201_1;
                            }
                        }
                        else if (BetterGapple.mc.field_71439_g.field_71071_by.func_70301_a(this.currentSlot).func_77973_b() != Items.field_151153_ao) {
                            break Label_0201_1;
                        }
                        if (((CPacketPlayerTryUseItemOnBlock)e.getPacket()).func_187022_c() == EnumHand.MAIN_HAND) {
                            this.timer = true;
                            return;
                        }
                        else {
                            return;
                        }
                    }
                }
                if (e.getPacket() instanceof CPacketPlayerTryUseItem) {
                    if (this.currentSlot == -1) {
                        if (BetterGapple.mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_151153_ao) {
                            return;
                        }
                    }
                    else if (BetterGapple.mc.field_71439_g.field_71071_by.func_70301_a(this.currentSlot).func_77973_b() != Items.field_151153_ao) {
                        return;
                    }
                    if (((CPacketPlayerTryUseItem)e.getPacket()).func_187028_a() == EnumHand.MAIN_HAND) {
                        this.timer = true;
                    }
                }
            }
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
    }
    
    @Override
    public void onUpdate() {
        if (!this.timer || BetterGapple.mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_151153_ao) {
            this.waitTicks = (double)Math.round(this.wait.getValue() * 40.0);
            this.switched = false;
        }
        --this.waitTicks;
        if (this.waitTicks <= 0.0) {
            this.lastSlot = BetterGapple.mc.field_71439_g.field_71071_by.field_70461_c;
            BetterGapple.mc.field_71439_g.field_71071_by.field_70461_c = 3;
            this.switched = true;
        }
        if (this.waitTicks <= -3.0) {
            this.timer = false;
            BetterGapple.mc.field_71439_g.field_71071_by.field_70461_c = this.lastSlot;
            this.switched = false;
        }
    }
    
    @SubscribeEvent
    public void awa(final LivingEntityUseItemEvent.Finish livingEntityUseItemEvent) {
        try {
            if (livingEntityUseItemEvent.getItem().func_77973_b() == Items.field_151153_ao) {
                this.timer = false;
            }
        }
        catch (Exception ex) {}
    }
    
    public void onEnable() {
        this.timer = false;
        this.switched = false;
    }
    
    @SubscribeEvent
    public void joinWorldEvent(final EntityJoinWorldEvent x) {
        if (x.getEntity() == BetterGapple.mc.field_71439_g) {
            this.currentSlot = -1;
        }
    }
}
