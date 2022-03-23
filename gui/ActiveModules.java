// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.gui;

import java.awt.Color;
import meow.candycat.uwu.util.ColourConverter;
import net.minecraft.util.text.TextFormatting;
import meow.candycat.uwu.util.ColourTextFormatting;
import meow.candycat.uwu.util.MathsUtils;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "ActiveModules", category = Category.GUI, description = "Configures ActiveModules colours and modes", showOnArray = ShowOnArray.OFF)
public class ActiveModules extends Module
{
    private Setting<Boolean> forgeHax;
    public Setting<Boolean> potion;
    public Setting<Boolean> hidden;
    public Setting<Boolean> epicStuff;
    private Setting<Integer> animationFrame;
    public Setting<Mode> mode;
    private Setting<Integer> rainbowSpeed;
    public Setting<Integer> saturationR;
    public Setting<Integer> brightnessR;
    public Setting<Integer> hueC;
    public Setting<Integer> saturationC;
    public Setting<Integer> brightnessC;
    private Setting<Boolean> alternate;
    public Setting<String> chat;
    public Setting<String> combat;
    public Setting<String> experimental;
    public Setting<String> client;
    public Setting<String> render;
    public Setting<String> player;
    public Setting<String> movement;
    public Setting<String> misc;
    public static ActiveModules INSTANCE;
    
    public ActiveModules() {
        this.forgeHax = this.register(Settings.b("ForgeHax", false));
        this.potion = this.register(Settings.b("PotionsMove", false));
        this.hidden = this.register(Settings.b("ShowHidden", false));
        this.epicStuff = this.register(Settings.b("EpicStuff"));
        this.animationFrame = this.register(Settings.i("AnimationFrame", 45));
        this.mode = this.register(Settings.e("Mode", Mode.RAINBOW));
        this.rainbowSpeed = this.register(Settings.integerBuilder().withName("SpeedR").withValue(30).withMinimum(0).withMaximum(100).withVisibility(v -> this.mode.getValue().equals(Mode.RAINBOW)).build());
        this.saturationR = this.register(Settings.integerBuilder().withName("SaturationR").withValue(117).withMinimum(0).withMaximum(255).withVisibility(v -> this.mode.getValue().equals(Mode.RAINBOW)).build());
        this.brightnessR = this.register(Settings.integerBuilder().withName("BrightnessR").withValue(255).withMinimum(0).withMaximum(255).withVisibility(v -> this.mode.getValue().equals(Mode.RAINBOW)).build());
        this.hueC = this.register(Settings.integerBuilder().withName("HueC").withValue(178).withMinimum(0).withMaximum(255).withVisibility(v -> this.mode.getValue().equals(Mode.CUSTOM)).build());
        this.saturationC = this.register(Settings.integerBuilder().withName("SaturationC").withValue(156).withMinimum(0).withMaximum(255).withVisibility(v -> this.mode.getValue().equals(Mode.CUSTOM)).build());
        this.brightnessC = this.register(Settings.integerBuilder().withName("BrightnessC").withValue(255).withMinimum(0).withMaximum(255).withVisibility(v -> this.mode.getValue().equals(Mode.CUSTOM)).build());
        this.alternate = this.register(Settings.booleanBuilder().withName("Alternate").withValue(true).withVisibility(v -> this.mode.getValue().equals(Mode.INFO_OVERLAY)).build());
        this.chat = this.register(Settings.s("Chat", "162,136,227"));
        this.combat = this.register(Settings.s("Combat", "229,68,109"));
        this.experimental = this.register(Settings.s("Experimental", "211,188,192"));
        this.client = this.register(Settings.s("Client", "56,2,59"));
        this.render = this.register(Settings.s("Render", "105,48,109"));
        this.player = this.register(Settings.s("Player", "255,137,102"));
        this.movement = this.register(Settings.s("Movement", "111,60,145"));
        this.misc = this.register(Settings.s("Misc", "165,102,139"));
        ActiveModules.INSTANCE = this;
    }
    
    private static int getRgb(final String input, final int arrayPos) {
        final String[] toConvert = input.split(",");
        return Integer.parseInt(toConvert[arrayPos]);
    }
    
    public static int getAnimationFrame() {
        return ActiveModules.INSTANCE.animationFrame.getValue();
    }
    
    public static boolean getEpicStuff() {
        return ActiveModules.INSTANCE.epicStuff.getValue();
    }
    
    public int getInfoColour(final int position) {
        if (!this.alternate.getValue()) {
            return this.settingsToColour(false);
        }
        if (MathsUtils.isNumberEven(position)) {
            return this.settingsToColour(true);
        }
        return this.settingsToColour(false);
    }
    
    private int settingsToColour(final boolean isOne) {
        Color localColor = null;
        switch (this.infoGetSetting(isOne)) {
            case UNDERLINE:
            case ITALIC:
            case RESET:
            case STRIKETHROUGH:
            case OBFUSCATED:
            case BOLD: {
                localColor = ColourTextFormatting.colourEnumMap.get(TextFormatting.WHITE).colorLocal;
                break;
            }
            default: {
                localColor = ColourTextFormatting.colourEnumMap.get(this.infoGetSetting(isOne)).colorLocal;
                break;
            }
        }
        return ColourConverter.rgbToInt(localColor.getRed(), localColor.getGreen(), localColor.getBlue());
    }
    
    private TextFormatting infoGetSetting(final boolean isOne) {
        if (isOne) {
            return this.setToText(ColourTextFormatting.ColourCode.WHITE);
        }
        return this.setToText(ColourTextFormatting.ColourCode.BLUE);
    }
    
    private TextFormatting setToText(final ColourTextFormatting.ColourCode colourCode) {
        return ColourTextFormatting.toTextMap.get(colourCode);
    }
    
    public int getCategoryColour(final Module module) {
        switch (module.getCategory()) {
            case CHAT: {
                return ColourConverter.rgbToInt(getRgb(this.chat.getValue(), 0), getRgb(this.chat.getValue(), 1), getRgb(this.chat.getValue(), 2));
            }
            case COMBAT: {
                return ColourConverter.rgbToInt(getRgb(this.combat.getValue(), 0), getRgb(this.combat.getValue(), 1), getRgb(this.combat.getValue(), 2));
            }
            case EXPLOITS: {
                return ColourConverter.rgbToInt(getRgb(this.experimental.getValue(), 0), getRgb(this.experimental.getValue(), 1), getRgb(this.experimental.getValue(), 2));
            }
            case GUI: {
                return ColourConverter.rgbToInt(getRgb(this.client.getValue(), 0), getRgb(this.client.getValue(), 1), getRgb(this.client.getValue(), 2));
            }
            case RENDER: {
                return ColourConverter.rgbToInt(getRgb(this.render.getValue(), 0), getRgb(this.render.getValue(), 1), getRgb(this.render.getValue(), 2));
            }
            case PLAYER: {
                return ColourConverter.rgbToInt(getRgb(this.player.getValue(), 0), getRgb(this.player.getValue(), 1), getRgb(this.player.getValue(), 2));
            }
            case MOVEMENT: {
                return ColourConverter.rgbToInt(getRgb(this.movement.getValue(), 0), getRgb(this.movement.getValue(), 1), getRgb(this.movement.getValue(), 2));
            }
            case MISC: {
                return ColourConverter.rgbToInt(getRgb(this.misc.getValue(), 0), getRgb(this.misc.getValue(), 1), getRgb(this.misc.getValue(), 2));
            }
            default: {
                return ColourConverter.rgbToInt(1, 1, 1);
            }
        }
    }
    
    public int getRainbowSpeed() {
        final int rSpeed = MathsUtils.reverseNumber(this.rainbowSpeed.getValue(), 1, 100);
        if (rSpeed == 0) {
            return 1;
        }
        return rSpeed;
    }
    
    public String getAlignedText(final String name, final String hudInfo, final boolean right) {
        String aligned;
        if (right) {
            aligned = hudInfo + " " + name;
        }
        else {
            aligned = (hudInfo.equals("") ? name : (name + " " + hudInfo));
        }
        if (!this.forgeHax.getValue()) {
            return aligned;
        }
        if (right) {
            return aligned + "<";
        }
        return ">" + aligned;
    }
    
    public void onDisable() {
    }
    
    public enum Mode
    {
        RAINBOW, 
        CUSTOM, 
        CATEGORY, 
        INFO_OVERLAY;
    }
}
