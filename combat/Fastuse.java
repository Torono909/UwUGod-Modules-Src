// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Mouse;
import java.util.function.Predicate;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.event.events.EventPlayerPostMotionUpdate;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(category = Category.COMBAT, description = "Changes delay when holding right click", name = "FastUse")
public class Fastuse extends Module
{
    private Setting<Integer> delay;
    private Setting<Boolean> bow;
    private Setting<Boolean> exp;
    private Setting<Boolean> crystal;
    private Setting<Boolean> antiDelay;
    private static long time;
    boolean shouldFastPlace;
    @EventHandler
    private Listener<PacketEvent.Send> listener;
    @EventHandler
    private Listener<EventPlayerPostMotionUpdate> listener2;
    
    public Fastuse() {
        this.delay = this.register((Setting<Integer>)Settings.integerBuilder("Delay").withMinimum(0).withMaximum(20).withValue(0).build());
        this.bow = this.register(Settings.b("FastBow", false));
        this.exp = this.register(Settings.b("FastExp", true));
        this.crystal = this.register(Settings.b("FastCrystal", true));
        this.antiDelay = this.register(Settings.b("AntiDelay", false));
        this.shouldFastPlace = false;
        this.listener = new Listener<PacketEvent.Send>(e -> {
            if (e.getPacket() instanceof CPacketHeldItemChange) {
                if (Fastuse.mc.field_71439_g.field_71071_by.func_70301_a(((CPacketHeldItemChange)e.getPacket()).func_149614_c()).func_77973_b() == Items.field_151062_by && this.exp.getValue()) {
                    this.shouldFastPlace = true;
                    Fastuse.mc.field_71467_ac = 1;
                }
                else {
                    this.shouldFastPlace = false;
                }
            }
            return;
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
        this.listener2 = new Listener<EventPlayerPostMotionUpdate>(e -> {
            if (this.antiDelay.getValue() && Fastuse.mc.field_71462_r == null && Mouse.isButtonDown(2) && this.shouldFastPlace) {
                Fastuse.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                Fastuse.mc.field_71467_ac = 2;
            }
        }, (Predicate<EventPlayerPostMotionUpdate>[])new Predicate[0]);
    }
    
    public void onDisable() {
        Fastuse.mc.field_71467_ac = 4;
    }
    
    @Override
    public void onUpdate() {
        if (this.delay.getValue() > 0) {
            if (Fastuse.time > 0L) {
                --Fastuse.time;
                Fastuse.mc.field_71467_ac = 1;
                return;
            }
            Fastuse.time = Math.round((float)(2 * Math.round(this.delay.getValue() / 2.0f)));
        }
        if (Fastuse.mc.field_71439_g == null) {
            return;
        }
        final Item itemMain = Fastuse.mc.field_71439_g.func_184614_ca().func_77973_b();
        final Item itemOff = Fastuse.mc.field_71439_g.func_184592_cb().func_77973_b();
        final boolean doExpMain = itemMain instanceof ItemExpBottle;
        final boolean doExpOff = itemOff instanceof ItemExpBottle;
        final boolean doCrystalMain = itemMain instanceof ItemEndCrystal;
        final boolean doObbyMain = itemMain instanceof ItemBlock;
        final boolean doCrystalOff = itemOff instanceof ItemEndCrystal;
        final boolean doBowMain = itemMain instanceof ItemBow;
        if (doBowMain && this.bow.getValue() && Fastuse.mc.field_71439_g.func_184587_cr() && Fastuse.mc.field_71439_g.func_184612_cw() >= 3) {
            Fastuse.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, Fastuse.mc.field_71439_g.func_174811_aO()));
            Fastuse.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(Fastuse.mc.field_71439_g.func_184600_cs()));
            Fastuse.mc.field_71439_g.func_184597_cx();
        }
        if ((doCrystalMain || doCrystalOff) && this.crystal.getValue() && !doObbyMain) {
            Fastuse.mc.field_71467_ac = 0;
        }
        if (!this.antiDelay.getValue() && (doExpMain || doExpOff) && this.exp.getValue()) {
            Fastuse.mc.field_71467_ac = 0;
        }
    }
    
    public void onEnable() {
        this.shouldFastPlace = false;
    }
    
    static {
        Fastuse.time = 0L;
    }
}
