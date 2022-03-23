// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.gui;

import java.util.Iterator;
import meow.candycat.uwu.module.ModuleManager;
import meow.candycat.uwu.gui.uwugod.UwUGodGUI;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.gui.rgui.component.container.use.Frame;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "HUD", category = Category.GUI, showOnArray = ShowOnArray.OFF)
public class HUD extends Module
{
    private Setting<Boolean> coord;
    private Setting<Boolean> Radar;
    private Setting<Boolean> Entity;
    private Setting<Boolean> welcomer;
    private Setting<Boolean> Friends;
    private Setting<Boolean> Time;
    private Setting<Boolean> Date;
    private Setting<Boolean> TabGUI;
    private Setting<Boolean> holeInfo;
    private Setting<Boolean> pvpInfo;
    private Setting<Boolean> crystalAura;
    Frame coords;
    Frame textRadar;
    Frame entities;
    Frame welcome;
    Frame list;
    Frame radaR;
    Frame friends;
    Frame inventory;
    Frame time;
    Frame date;
    Frame tabgui;
    Frame crystalaurainfo;
    Frame pvpinfo;
    Frame holeinfo;
    Frame log;
    Frame Potion;
    Frame notifications;
    
    public HUD() {
        this.coord = this.register(Settings.b("Coordinates", false));
        this.Radar = this.register(Settings.b("Radar", false));
        this.Entity = this.register(Settings.b("Entities", false));
        this.welcomer = this.register(Settings.b("Welcomer", false));
        this.Friends = this.register(Settings.b("Friends", false));
        this.Time = this.register(Settings.b("Time", false));
        this.Date = this.register(Settings.b("Date", false));
        this.TabGUI = this.register(Settings.b("TabGUI", false));
        this.holeInfo = this.register(Settings.b("HoleInfo", false));
        this.pvpInfo = this.register(Settings.b("PvPInfo", false));
        this.crystalAura = this.register(Settings.b("CrystalAuraInfo", false));
    }
    
    @Override
    public void onUpdate() {
        if (UwUGodGUI.framesArray != null) {
            for (final Frame frame : UwUGodGUI.framesArray) {
                final String title = frame.getTitle();
                switch (title) {
                    case "Coordinates": {
                        this.coords = frame;
                        continue;
                    }
                    case "Notifications": {
                        this.notifications = frame;
                        continue;
                    }
                    case "Text Radar": {
                        this.textRadar = frame;
                        continue;
                    }
                    case "Entities": {
                        this.entities = frame;
                        continue;
                    }
                    case "Welcomer": {
                        this.welcome = frame;
                        continue;
                    }
                    case "Active modules": {
                        this.list = frame;
                    }
                    case "Radar": {
                        this.radaR = frame;
                        continue;
                    }
                    case "Friends": {
                        this.friends = frame;
                        continue;
                    }
                    case "Inventory Viewer": {
                        this.inventory = frame;
                        continue;
                    }
                    case "Time": {
                        this.time = frame;
                        continue;
                    }
                    case "Date": {
                        this.date = frame;
                        continue;
                    }
                    case "TabGUI": {
                        this.tabgui = frame;
                        continue;
                    }
                    case "PvP Info": {
                        this.pvpinfo = frame;
                        continue;
                    }
                    case "HoleInfo": {
                        this.holeinfo = frame;
                        continue;
                    }
                    case "Log": {
                        this.log = frame;
                        continue;
                    }
                    case "CrystalAura Info": {
                        this.crystalaurainfo = frame;
                        continue;
                    }
                    case "Info": {
                        this.Potion = frame;
                        continue;
                    }
                }
            }
            this.check(this.coord, this.coords);
            this.check(ModuleManager.getModuleByName("TextRadar").isEnabled(), this.textRadar);
            this.check(this.Radar, this.radaR);
            this.check(this.Entity, this.entities);
            this.check(this.welcomer, this.welcome);
            this.check(ModuleManager.getModuleByName("ActiveModules").isEnabled(), this.list);
            this.check(this.Friends, this.friends);
            this.check(ModuleManager.getModuleByName("InventoryViewer").isEnabled(), this.inventory);
            this.check(this.Time, this.time);
            this.check(this.Date, this.date);
            this.check(this.TabGUI, this.tabgui);
            this.check(this.pvpInfo, this.pvpinfo);
            this.check(this.crystalAura, this.crystalaurainfo);
            this.check(ModuleManager.getModuleByName("GuiLog").isEnabled(), this.log);
            this.check(this.holeInfo, this.holeinfo);
            this.check(ModuleManager.getModuleByName("Info").isEnabled(), this.Potion);
            this.check(ModuleManager.getModuleByName("Notifications").isEnabled(), this.notifications);
        }
    }
    
    public void onDisable() {
        this.enable();
    }
    
    public void check(final Setting<Boolean> setting, final Frame frame) {
        final boolean y = setting.getValue();
        frame.setPinned(y);
        frame.setBox(y);
        frame.setMinimized(!y);
    }
    
    public void check(final boolean setting, final Frame frame) {
        frame.setPinned(setting);
        frame.setBox(setting);
        frame.setMinimized(!setting);
    }
}
