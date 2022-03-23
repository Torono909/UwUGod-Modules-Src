// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import java.util.Date;
import java.text.SimpleDateFormat;
import meow.candycat.uwu.command.Command;
import net.minecraft.client.Minecraft;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "AutoQMain", description = "Automatically does \"/queue main\" every X minutes.", category = Category.MISC)
public class AutoQMain extends Module
{
    private Setting<Boolean> debug;
    private Setting<Boolean> debugWarn;
    private Setting<Boolean> endDi;
    private Setting<Double> delay;
    private double delayTime;
    private double oldDelay;
    
    public AutoQMain() {
        this.debug = this.register(Settings.b("Debug", true));
        this.debugWarn = this.register(Settings.b("Connection Warning", true));
        this.endDi = this.register(Settings.b("Dimension Warning", true));
        this.delay = this.register((Setting<Double>)Settings.doubleBuilder("Wait time").withMinimum(0.2).withValue(7.1).withMaximum(10.0).build());
        this.oldDelay = 0.0;
    }
    
    @Override
    public void onUpdate() {
        if (AutoQMain.mc.field_71439_g == null) {
            return;
        }
        if (this.oldDelay == 0.0) {
            this.oldDelay = this.delay.getValue();
        }
        else if (this.oldDelay != this.delay.getValue()) {
            this.delayTime = this.delay.getValue();
            this.oldDelay = this.delay.getValue();
        }
        if (this.delayTime <= 0.0) {
            this.delayTime = (int)(this.delay.getValue() * 2400.0);
        }
        else if (this.delayTime > 0.0) {
            --this.delayTime;
            return;
        }
        if (AutoQMain.mc.field_71439_g == null) {
            return;
        }
        if (Minecraft.func_71410_x().func_147104_D() == null) {
            Command.sendWarningMessage("[AutoQMain] &l&6Warning: &r&6You are on singleplayer");
            return;
        }
        if (!Minecraft.func_71410_x().func_147104_D().field_78845_b.equalsIgnoreCase("2b2t.org") && this.debugWarn.getValue()) {
            Command.sendWarningMessage("[AutoQMain] &l&6Warning: &r&6You are not connected to 2b2t.org");
        }
        if (AutoQMain.mc.field_71439_g.field_71093_bK != 1 && this.endDi.getValue()) {
            Command.sendWarningMessage("[AutoQMain] &l&6Warning: &r&6You are not in the end. Not running &b/queue main&7.");
            return;
        }
        if (this.debug.getValue()) {
            final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            final Date date = new Date(System.currentTimeMillis());
            System.out.println(formatter.format(date));
            Command.sendChatMessage("&7Run &b/queue main&7 at " + formatter.format(date));
        }
        Minecraft.func_71410_x().field_71442_b.field_78774_b.func_147297_a((Packet)new CPacketChatMessage("/queue main"));
    }
    
    public void onDisable() {
        this.delayTime = 0.0;
    }
}
