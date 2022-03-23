// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.gui;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import java.util.Iterator;
import net.minecraft.item.ItemStack;
import meow.candycat.uwu.command.Command;
import meow.candycat.uwu.util.Notification;
import meow.candycat.uwu.util.Enemies;
import java.util.Collection;
import java.util.ArrayList;
import meow.candycat.uwu.setting.Settings;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "Notifications", category = Category.GUI)
public class Notifications extends Module
{
    public Setting<Boolean> enemyAlert;
    public Setting<Boolean> armorAlert;
    public Setting<Integer> alertValue;
    private Setting<colour> mode;
    public Setting<Integer> amount;
    public Setting<Integer> notifTime;
    public Setting<sortDirection> SortDirection;
    List<EntityPlayer> spottedEnemies;
    List<EntityPlayer> deadEnemies;
    private final int[] colorCode;
    private int[] alert;
    public static Notifications INSTANCE;
    
    public Notifications() {
        this.enemyAlert = this.register(Settings.b("EnemyAlert"));
        this.armorAlert = this.register(Settings.b("ArmorAlert"));
        this.alertValue = this.register(Settings.integerBuilder("ArmorDura").withValue(25).withVisibility(v -> this.armorAlert.getValue()).build());
        this.mode = this.register(Settings.e("Colour", colour.RED));
        this.amount = this.register(Settings.i("Amount", 5));
        this.notifTime = this.register(Settings.i("Time", 5));
        this.SortDirection = this.register(Settings.e("SortDirection", sortDirection.DOWN));
        this.spottedEnemies = new ArrayList<EntityPlayer>();
        this.deadEnemies = new ArrayList<EntityPlayer>();
        this.colorCode = new int[32];
        this.alert = new int[4];
        (Notifications.INSTANCE = this).setupMinecraftColorcodes();
    }
    
    public static int getAmount() {
        return Notifications.INSTANCE.amount.getValue();
    }
    
    public static long getTime() {
        return Notifications.INSTANCE.notifTime.getValue() * 1000;
    }
    
    public static boolean sortFromDown() {
        return Notifications.INSTANCE.SortDirection.getValue() == sortDirection.DOWN;
    }
    
    @Override
    public void onUpdate() {
        if (this.enemyAlert.getValue() && !Notifications.mc.field_71439_g.field_70128_L && Notifications.mc.field_71439_g.func_110143_aJ() > 0.0f) {
            for (final EntityPlayer player : new ArrayList<EntityPlayer>(Notifications.mc.field_71441_e.field_73010_i)) {
                if (Enemies.isEnemy(player.func_70005_c_())) {
                    if (player.field_70128_L || player.func_110143_aJ() <= 0.0f) {
                        if (this.deadEnemies.contains(player)) {
                            continue;
                        }
                        Command.sendNotification(new Notification(-16777216, this.notificationColourChoice() + "Your enemy " + player.func_70005_c_() + " has died at X: " + (int)player.field_70165_t + ", Y: " + (int)player.field_70163_u + ", Z: " + (int)player.field_70161_v, System.currentTimeMillis(), false));
                        this.deadEnemies.add(player);
                        this.spottedEnemies.remove(player);
                    }
                    else if (this.deadEnemies.contains(player)) {
                        Command.sendNotification(new Notification(-16777216, this.notificationColourChoice() + "Your enemy " + player.func_70005_c_() + " has respawned at X: " + (int)player.field_70165_t + ", Y: " + (int)player.field_70163_u + ", Z: " + (int)player.field_70161_v, System.currentTimeMillis(), false));
                        this.spottedEnemies.add(player);
                        this.deadEnemies.remove(player);
                    }
                    else {
                        if (this.spottedEnemies.contains(player)) {
                            continue;
                        }
                        Command.sendNotification(new Notification(-16777216, this.notificationColourChoice() + "Your enemy " + player.func_70005_c_() + " has appeared at X: " + (int)player.field_70165_t + ", Y: " + (int)player.field_70163_u + ", Z: " + (int)player.field_70161_v, System.currentTimeMillis(), false));
                        this.spottedEnemies.add(player);
                    }
                }
            }
            for (final EntityPlayer player : new ArrayList<EntityPlayer>(this.spottedEnemies)) {
                if (!Notifications.mc.field_71441_e.field_73010_i.contains(player)) {
                    Command.sendNotification(new Notification(-16777216, this.notificationColourChoice() + "Your enemy " + player.func_70005_c_() + " has disappeared at X: " + (int)player.field_70165_t + ", Y: " + (int)player.field_70163_u + ", Z: " + (int)player.field_70161_v, System.currentTimeMillis(), false));
                    this.spottedEnemies.remove(player);
                }
            }
        }
        if (this.armorAlert.getValue()) {
            for (int i = 0; i < Notifications.mc.field_71439_g.field_71071_by.field_70460_b.size(); ++i) {
                final ItemStack piece = (ItemStack)Notifications.mc.field_71439_g.field_71071_by.field_70460_b.get(i);
                if (getDamageInPercent(piece) < this.alertValue.getValue()) {
                    String armorPiece = null;
                    if (this.alert[i] != 1) {
                        switch (i) {
                            case 0: {
                                armorPiece = "boots";
                                break;
                            }
                            case 1: {
                                armorPiece = "leggings";
                                break;
                            }
                            case 2: {
                                armorPiece = "chestplate";
                                break;
                            }
                            case 3: {
                                armorPiece = "helmet";
                                break;
                            }
                        }
                        this.alert[i] = 1;
                        Command.sendNotification(new Notification(-16777216, this.notificationColourChoice() + "Your " + armorPiece + " is under " + this.alertValue.getValue() + "% durability", System.currentTimeMillis(), false));
                    }
                }
                else if (this.alert[i] != 0) {
                    this.alert[i] = 0;
                }
            }
        }
    }
    
    public static int getItemDamage(final ItemStack stack) {
        return stack.func_77958_k() - stack.func_77952_i();
    }
    
    public static float getDamageInPercent(final ItemStack stack) {
        return getItemDamage(stack) / (float)stack.func_77958_k() * 100.0f;
    }
    
    @SubscribeEvent
    public void joinWorldEvent(final EntityJoinWorldEvent x) {
        if (x.getEntity() == Notifications.mc.field_71439_g) {
            this.spottedEnemies.clear();
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
    
    public enum sortDirection
    {
        UP, 
        DOWN;
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
