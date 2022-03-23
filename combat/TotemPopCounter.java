// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import java.util.Iterator;
import meow.candycat.uwu.util.Notification;
import meow.candycat.uwu.command.Command;
import net.minecraft.entity.player.EntityPlayer;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.TotemPopEvent;
import meow.candycat.eventsystem.listener.Listener;
import meow.candycat.uwu.setting.Setting;
import java.util.HashMap;
import meow.candycat.uwu.module.Module;

@Info(name = "TotemPopCounter", description = "Counts the times your enemy pops", category = Category.COMBAT)
public class TotemPopCounter extends Module
{
    public HashMap<String, Integer> popList;
    private Setting<colour> mode;
    public Setting<Boolean> notification;
    int popCounter;
    int newPopCounter;
    public static TotemPopCounter totemPopCounter;
    private final int[] colorCode;
    @EventHandler
    public Listener<TotemPopEvent> totemPopEvent;
    
    public TotemPopCounter() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   meow/candycat/uwu/module/Module.<init>:()V
        //     4: aload_0         /* this */
        //     5: new             Ljava/util/HashMap;
        //     8: dup            
        //     9: invokespecial   java/util/HashMap.<init>:()V
        //    12: putfield        meow/candycat/uwu/module/modules/combat/TotemPopCounter.popList:Ljava/util/HashMap;
        //    15: aload_0         /* this */
        //    16: aload_0         /* this */
        //    17: ldc             "Colour"
        //    19: getstatic       meow/candycat/uwu/module/modules/combat/TotemPopCounter$colour.DARK_PURPLE:Lmeow/candycat/uwu/module/modules/combat/TotemPopCounter$colour;
        //    22: invokestatic    meow/candycat/uwu/setting/Settings.e:(Ljava/lang/String;Ljava/lang/Enum;)Lmeow/candycat/uwu/setting/Setting;
        //    25: invokevirtual   meow/candycat/uwu/module/modules/combat/TotemPopCounter.register:(Lmeow/candycat/uwu/setting/Setting;)Lmeow/candycat/uwu/setting/Setting;
        //    28: putfield        meow/candycat/uwu/module/modules/combat/TotemPopCounter.mode:Lmeow/candycat/uwu/setting/Setting;
        //    31: aload_0         /* this */
        //    32: aload_0         /* this */
        //    33: ldc             "Notifications"
        //    35: invokestatic    meow/candycat/uwu/setting/Settings.b:(Ljava/lang/String;)Lmeow/candycat/uwu/setting/Setting;
        //    38: invokevirtual   meow/candycat/uwu/module/modules/combat/TotemPopCounter.register:(Lmeow/candycat/uwu/setting/Setting;)Lmeow/candycat/uwu/setting/Setting;
        //    41: putfield        meow/candycat/uwu/module/modules/combat/TotemPopCounter.notification:Lmeow/candycat/uwu/setting/Setting;
        //    44: aload_0         /* this */
        //    45: bipush          32
        //    47: newarray        I
        //    49: putfield        meow/candycat/uwu/module/modules/combat/TotemPopCounter.colorCode:[I
        //    52: aload_0         /* this */
        //    53: new             Lmeow/candycat/eventsystem/listener/Listener;
        //    56: dup            
        //    57: aload_0         /* this */
        //    58: invokedynamic   BootstrapMethod #0, invoke:(Lmeow/candycat/uwu/module/modules/combat/TotemPopCounter;)Lmeow/candycat/eventsystem/listener/EventHook;
        //    63: iconst_0       
        //    64: anewarray       Ljava/util/function/Predicate;
        //    67: invokespecial   meow/candycat/eventsystem/listener/Listener.<init>:(Lmeow/candycat/eventsystem/listener/EventHook;[Ljava/util/function/Predicate;)V
        //    70: putfield        meow/candycat/uwu/module/modules/combat/TotemPopCounter.totemPopEvent:Lmeow/candycat/eventsystem/listener/Listener;
        //    73: aload_0         /* this */
        //    74: invokespecial   meow/candycat/uwu/module/modules/combat/TotemPopCounter.setupMinecraftColorcodes:()V
        //    77: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Could not infer any expression.
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:374)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:344)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createConstructor(AstBuilder.java:713)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:549)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static TotemPopCounter getInstance() {
        return TotemPopCounter.totemPopCounter;
    }
    
    @Override
    public void onUpdate() {
        for (final EntityPlayer player : TotemPopCounter.mc.field_71441_e.field_73010_i) {
            if ((player.func_110143_aJ() <= 0.0f || player.field_70128_L) && this.popList.containsKey(player.func_70005_c_())) {
                if (!this.notification.getValue()) {
                    Command.sendChatMessage(this.colourChoice() + player.func_70005_c_() + " died after popping " + this.popList.get(player.func_70005_c_()) + " totems!");
                }
                else {
                    final String text = this.notificationColourChoice();
                    final int colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(1));
                    final int colorcode = this.colorCode[colorIndex];
                    Command.sendNotification(new Notification(colorcode, player.func_70005_c_() + " died after popping " + this.popList.get(player.func_70005_c_()) + " totems!", System.currentTimeMillis(), false));
                }
                this.popList.remove(player.func_70005_c_(), this.popList.get(player.func_70005_c_()));
            }
        }
    }
    
    private String colourChoice() {
        switch (this.mode.getValue()) {
            case BLACK: {
                return "&0";
            }
            case RED: {
                return "&c";
            }
            case AQUA: {
                return "&b";
            }
            case BLUE: {
                return "&9";
            }
            case GOLD: {
                return "&6";
            }
            case GRAY: {
                return "&7";
            }
            case WHITE: {
                return "&f";
            }
            case GREEN: {
                return "&a";
            }
            case YELLOW: {
                return "&e";
            }
            case DARK_RED: {
                return "&4";
            }
            case DARK_AQUA: {
                return "&3";
            }
            case DARK_BLUE: {
                return "&1";
            }
            case DARK_GRAY: {
                return "&8";
            }
            case DARK_GREEN: {
                return "&2";
            }
            case DARK_PURPLE: {
                return "&5";
            }
            case LIGHT_PURPLE: {
                return "&d";
            }
            default: {
                return "";
            }
        }
    }
    
    private String notificationColourChoice() {
        switch (this.mode.getValue()) {
            case BLACK: {
                return "§0";
            }
            case RED: {
                return "§c";
            }
            case AQUA: {
                return "§b";
            }
            case BLUE: {
                return "§9";
            }
            case GOLD: {
                return "§6";
            }
            case GRAY: {
                return "§7";
            }
            case WHITE: {
                return "§f";
            }
            case GREEN: {
                return "§a";
            }
            case YELLOW: {
                return "§e";
            }
            case DARK_RED: {
                return "§4";
            }
            case DARK_AQUA: {
                return "§3";
            }
            case DARK_BLUE: {
                return "§1";
            }
            case DARK_GRAY: {
                return "§8";
            }
            case DARK_GREEN: {
                return "§2";
            }
            case DARK_PURPLE: {
                return "§5";
            }
            case LIGHT_PURPLE: {
                return "§d";
            }
            default: {
                return "";
            }
        }
    }
    
    private void setupMinecraftColorcodes() {
        for (int index = 0; index < 32; ++index) {
            final int noClue = (index >> 3 & 0x1) * 85;
            int red = (index >> 2 & 0x1) * 170 + noClue;
            int green = (index >> 1 & 0x1) * 170 + noClue;
            int blue = (index >> 0 & 0x1) * 170 + noClue;
            if (index == 6) {
                red += 85;
            }
            if (index >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }
            this.colorCode[index] = ((red & 0xFF) << 16 | (green & 0xFF) << 8 | (blue & 0xFF));
        }
    }
    
    private enum colour
    {
        BLACK, 
        DARK_BLUE, 
        DARK_GREEN, 
        DARK_AQUA, 
        DARK_RED, 
        DARK_PURPLE, 
        GOLD, 
        GRAY, 
        DARK_GRAY, 
        BLUE, 
        GREEN, 
        AQUA, 
        RED, 
        LIGHT_PURPLE, 
        YELLOW, 
        WHITE;
    }
}
