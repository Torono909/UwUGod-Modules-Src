// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import meow.candycat.uwu.command.Command;
import java.util.function.Predicate;
import net.minecraft.init.Items;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "AutoExp", category = Category.COMBAT, description = "Auto Switch to XP and throw fast")
public class AutoExp extends Module
{
    private Setting<Boolean> autoThrow;
    private Setting<Boolean> autoSwitch;
    private Setting<Boolean> autoDisable;
    private int initHotbarSlot;
    @EventHandler
    private Listener<PacketEvent.Receive> receiveListener;
    
    public AutoExp() {
        this.autoThrow = this.register(Settings.b("Auto Throw", true));
        this.autoSwitch = this.register(Settings.b("Auto Switch", true));
        this.autoDisable = this.register(Settings.booleanBuilder("Auto Disable").withValue(true).withVisibility(o -> this.autoSwitch.getValue()).build());
        this.initHotbarSlot = -1;
        this.receiveListener = new Listener<PacketEvent.Receive>(event -> {
            if (AutoExp.mc.field_71439_g != null && AutoExp.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151062_by) {
                AutoExp.mc.field_71467_ac = 0;
            }
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
    }
    
    @Override
    protected void onEnable() {
        if (AutoExp.mc.field_71439_g == null) {
            return;
        }
        if (this.autoSwitch.getValue()) {
            this.initHotbarSlot = AutoExp.mc.field_71439_g.field_71071_by.field_70461_c;
        }
    }
    
    @Override
    protected void onDisable() {
        if (AutoExp.mc.field_71439_g == null) {
            return;
        }
        if (this.autoSwitch.getValue() && this.initHotbarSlot != -1 && this.initHotbarSlot != AutoExp.mc.field_71439_g.field_71071_by.field_70461_c) {
            AutoExp.mc.field_71439_g.field_71071_by.field_70461_c = this.initHotbarSlot;
        }
    }
    
    @Override
    public void onUpdate() {
        if (AutoExp.mc.field_71439_g == null) {
            return;
        }
        if (this.autoSwitch.getValue() && AutoExp.mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_151062_by) {
            final int xpSlot = this.findXpPots();
            if (xpSlot == -1) {
                if (this.autoDisable.getValue()) {
                    Command.sendWarningMessage("[AutoExp] No XP in hotbar, disabling");
                    this.disable();
                }
                return;
            }
            AutoExp.mc.field_71439_g.field_71071_by.field_70461_c = xpSlot;
        }
        if (this.autoThrow.getValue() && AutoExp.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151062_by) {
            AutoExp.mc.func_147121_ag();
        }
    }
    
    private int findXpPots() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            if (AutoExp.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() == Items.field_151062_by) {
                slot = i;
                break;
            }
        }
        return slot;
    }
}
