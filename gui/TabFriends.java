// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.gui;

import meow.candycat.uwu.util.Friends;
import net.minecraft.scoreboard.Team;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.client.network.NetworkPlayerInfo;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "TabFriends", description = "Highlights friends in the tab menu", category = Category.GUI, showOnArray = ShowOnArray.OFF)
public class TabFriends extends Module
{
    public Setting<Boolean> startupGlobal;
    public static TabFriends INSTANCE;
    
    public TabFriends() {
        this.startupGlobal = this.register(Settings.b("Enable Automatically", true));
        TabFriends.INSTANCE = this;
    }
    
    public static String getPlayerName(final NetworkPlayerInfo networkPlayerInfoIn) {
        final String dname = (networkPlayerInfoIn.func_178854_k() != null) ? networkPlayerInfoIn.func_178854_k().func_150254_d() : ScorePlayerTeam.func_96667_a((Team)networkPlayerInfoIn.func_178850_i(), networkPlayerInfoIn.func_178845_a().getName());
        if (Friends.isFriend(dname)) {
            return String.format("%sa%s", '§', dname);
        }
        return dname;
    }
}
