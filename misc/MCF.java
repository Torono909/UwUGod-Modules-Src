// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import net.minecraft.entity.Entity;
import meow.candycat.uwu.command.commands.FriendCommand;
import meow.candycat.uwu.command.Command;
import java.util.ArrayList;
import meow.candycat.uwu.util.Friends;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;
import net.minecraft.client.Minecraft;
import meow.candycat.uwu.module.Module;

@Info(name = "MCF", category = Category.MISC)
public class MCF extends Module
{
    private boolean clicked;
    
    @Override
    public void onUpdate() {
        final Minecraft mc = Minecraft.func_71410_x();
        if (mc.field_71462_r == null) {
            if (Mouse.isButtonDown(2)) {
                if (!this.clicked) {
                    final RayTraceResult result = mc.field_71476_x;
                    if (result != null && result.field_72313_a == RayTraceResult.Type.ENTITY) {
                        final Entity entity = result.field_72308_g;
                        if (entity != null && entity instanceof EntityPlayer) {
                            final String name = entity.func_70005_c_();
                            if (Friends.isFriend(name)) {
                                final Friends.Friend friend2 = Friends.friends.getValue().stream().filter(friend1 -> friend1.getUsername().equalsIgnoreCase(name)).findFirst().get();
                                Friends.friends.getValue().remove(friend2);
                                Command.sendChatMessage("&b" + name + "&r has been unfriended.");
                            }
                            else {
                                final Friends.Friend f = FriendCommand.getFriendByName(name);
                                Friends.friends.getValue().add(f);
                                Command.sendChatMessage("&b" + name + "&r has been friended.");
                            }
                        }
                    }
                }
                this.clicked = true;
            }
            else {
                this.clicked = false;
            }
        }
    }
}
