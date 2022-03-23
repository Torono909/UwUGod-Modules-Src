// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import java.util.function.Predicate;
import meow.candycat.uwu.module.ModuleManager;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.event.events.EventPlayerPostMotionUpdate;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.EventPlayerPreMotionUpdateBeforeAimbot;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "PacketXP", category = Category.COMBAT)
public class PacketXP extends Module
{
    public Setting<Boolean> rotate;
    public Setting<Boolean> autoSwitch;
    public boolean rotated;
    boolean firstRun;
    int currentSlot;
    @EventHandler
    public Listener<EventPlayerPreMotionUpdateBeforeAimbot> listener;
    @EventHandler
    public Listener<EventPlayerPostMotionUpdate> listener2;
    
    public PacketXP() {
        this.rotate = this.register(Settings.b("Rotate", true));
        this.autoSwitch = this.register(Settings.b("AutoSwitch"));
        this.rotated = false;
        this.firstRun = true;
        this.currentSlot = -1;
        this.listener = new Listener<EventPlayerPreMotionUpdateBeforeAimbot>(e -> {
            if (this.rotate.getValue()) {
                ((Aimbot)ModuleManager.getModuleByName("Aimbot")).setRotation(PacketXP.mc.field_71439_g.field_70177_z, 90.0f);
                this.rotated = true;
            }
            this.firstRun = false;
            return;
        }, (Predicate<EventPlayerPreMotionUpdateBeforeAimbot>[])new Predicate[0]);
        int i;
        boolean switched;
        int oldSlot;
        this.listener2 = new Listener<EventPlayerPostMotionUpdate>(e -> {
            if (!this.firstRun) {
                i = this.xpSlot();
                if (i != -1) {
                    switched = false;
                    oldSlot = -1;
                    if (PacketXP.mc.field_71439_g.field_71071_by.field_70461_c != i) {
                        if (!this.autoSwitch.getValue()) {
                            return;
                        }
                        else {
                            oldSlot = PacketXP.mc.field_71439_g.field_71071_by.field_70461_c;
                            PacketXP.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(i));
                            switched = true;
                        }
                    }
                    PacketXP.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    if (switched) {
                        PacketXP.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(oldSlot));
                    }
                }
            }
        }, (Predicate<EventPlayerPostMotionUpdate>[])new Predicate[0]);
    }
    
    public int xpSlot() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            if (PacketXP.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() == Items.field_151062_by) {
                slot = i;
                break;
            }
        }
        return slot;
    }
    
    public void onDisable() {
        if (this.rotated) {
            ((Aimbot)ModuleManager.getModuleByName("Aimbot")).resetRotation();
        }
        this.rotated = false;
    }
    
    public void onEnable() {
        this.firstRun = true;
    }
    
    @SubscribeEvent
    public void joinWorldEvent(final EntityJoinWorldEvent x) {
        if (x.getEntity() == PacketXP.mc.field_71439_g) {
            this.currentSlot = -1;
        }
    }
}
