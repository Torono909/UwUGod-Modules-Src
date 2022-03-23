// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.render;

import meow.candycat.uwu.util.Notification;
import meow.candycat.uwu.command.Command;
import java.util.Date;
import java.text.SimpleDateFormat;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemArmor;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import meow.candycat.uwu.gui.uwugod.theme.uwu.UwUGodAnimatedActiveModuleUI;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;
import meow.candycat.uwu.util.UwUGodTessellator;
import java.awt.Color;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import java.util.ArrayList;
import net.minecraft.init.Items;
import net.minecraft.entity.EntityLivingBase;
import meow.candycat.uwu.module.ModuleManager;
import meow.candycat.uwu.module.modules.combat.TotemPopCounter;
import org.lwjgl.opengl.GL11;
import meow.candycat.uwu.event.events.RenderEvent;
import java.util.Iterator;
import meow.candycat.uwu.gui.font.CFontRenderer;
import net.minecraftforge.event.world.WorldEvent;
import meow.candycat.uwu.event.events.PlayerLeaveEvent;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.PlayerJoinEvent;
import meow.candycat.eventsystem.listener.Listener;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import net.minecraft.entity.Entity;
import java.util.Map;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "LogoutSpots", category = Category.RENDER)
public class LogoutSpots extends Module
{
    public Setting<Boolean> notify;
    public Setting<mode> Mode;
    private Setting<colour> colourMode;
    private final int[] colorCode;
    Map<Entity, String> loggedPlayers;
    List<EntityPlayer> worldPlayers;
    @EventHandler
    private final Listener<PlayerJoinEvent> playerJoinEventListener1;
    @EventHandler
    private final Listener<PlayerLeaveEvent> playerLeaveEventListener2;
    @EventHandler
    private final Listener<WorldEvent.Unload> unloadListener1;
    @EventHandler
    private final Listener<WorldEvent.Load> unloadListener2;
    CFontRenderer cFontRenderer;
    
    public LogoutSpots() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   meow/candycat/uwu/module/Module.<init>:()V
        //     4: aload_0         /* this */
        //     5: aload_0         /* this */
        //     6: ldc             "Notify"
        //     8: invokestatic    meow/candycat/uwu/setting/Settings.b:(Ljava/lang/String;)Lmeow/candycat/uwu/setting/Setting;
        //    11: invokevirtual   meow/candycat/uwu/module/modules/render/LogoutSpots.register:(Lmeow/candycat/uwu/setting/Setting;)Lmeow/candycat/uwu/setting/Setting;
        //    14: putfield        meow/candycat/uwu/module/modules/render/LogoutSpots.notify:Lmeow/candycat/uwu/setting/Setting;
        //    17: aload_0         /* this */
        //    18: aload_0         /* this */
        //    19: ldc             Lmeow/candycat/uwu/module/modules/render/LogoutSpots$mode;.class
        //    21: invokestatic    meow/candycat/uwu/setting/Settings.enumBuilder:(Ljava/lang/Class;)Lmeow/candycat/uwu/setting/builder/primitive/EnumSettingBuilder;
        //    24: ldc             "NotifyMode"
        //    26: invokevirtual   meow/candycat/uwu/setting/builder/primitive/EnumSettingBuilder.withName:(Ljava/lang/String;)Lmeow/candycat/uwu/setting/builder/SettingBuilder;
        //    29: getstatic       meow/candycat/uwu/module/modules/render/LogoutSpots$mode.NOTIFICATION:Lmeow/candycat/uwu/module/modules/render/LogoutSpots$mode;
        //    32: invokevirtual   meow/candycat/uwu/setting/builder/SettingBuilder.withValue:(Ljava/lang/Object;)Lmeow/candycat/uwu/setting/builder/SettingBuilder;
        //    35: aload_0         /* this */
        //    36: invokedynamic   BootstrapMethod #0, test:(Lmeow/candycat/uwu/module/modules/render/LogoutSpots;)Ljava/util/function/Predicate;
        //    41: invokevirtual   meow/candycat/uwu/setting/builder/SettingBuilder.withVisibility:(Ljava/util/function/Predicate;)Lmeow/candycat/uwu/setting/builder/SettingBuilder;
        //    44: invokevirtual   meow/candycat/uwu/setting/builder/SettingBuilder.build:()Lmeow/candycat/uwu/setting/Setting;
        //    47: invokevirtual   meow/candycat/uwu/module/modules/render/LogoutSpots.register:(Lmeow/candycat/uwu/setting/Setting;)Lmeow/candycat/uwu/setting/Setting;
        //    50: putfield        meow/candycat/uwu/module/modules/render/LogoutSpots.Mode:Lmeow/candycat/uwu/setting/Setting;
        //    53: aload_0         /* this */
        //    54: aload_0         /* this */
        //    55: ldc             "Colour"
        //    57: getstatic       meow/candycat/uwu/module/modules/render/LogoutSpots$colour.DARK_PURPLE:Lmeow/candycat/uwu/module/modules/render/LogoutSpots$colour;
        //    60: invokestatic    meow/candycat/uwu/setting/Settings.e:(Ljava/lang/String;Ljava/lang/Enum;)Lmeow/candycat/uwu/setting/Setting;
        //    63: invokevirtual   meow/candycat/uwu/module/modules/render/LogoutSpots.register:(Lmeow/candycat/uwu/setting/Setting;)Lmeow/candycat/uwu/setting/Setting;
        //    66: putfield        meow/candycat/uwu/module/modules/render/LogoutSpots.colourMode:Lmeow/candycat/uwu/setting/Setting;
        //    69: aload_0         /* this */
        //    70: bipush          32
        //    72: newarray        I
        //    74: putfield        meow/candycat/uwu/module/modules/render/LogoutSpots.colorCode:[I
        //    77: aload_0         /* this */
        //    78: new             Ljava/util/concurrent/ConcurrentHashMap;
        //    81: dup            
        //    82: invokespecial   java/util/concurrent/ConcurrentHashMap.<init>:()V
        //    85: putfield        meow/candycat/uwu/module/modules/render/LogoutSpots.loggedPlayers:Ljava/util/Map;
        //    88: aload_0         /* this */
        //    89: new             Ljava/util/ArrayList;
        //    92: dup            
        //    93: invokespecial   java/util/ArrayList.<init>:()V
        //    96: putfield        meow/candycat/uwu/module/modules/render/LogoutSpots.worldPlayers:Ljava/util/List;
        //    99: aload_0         /* this */
        //   100: new             Lmeow/candycat/eventsystem/listener/Listener;
        //   103: dup            
        //   104: aload_0         /* this */
        //   105: invokedynamic   BootstrapMethod #1, invoke:(Lmeow/candycat/uwu/module/modules/render/LogoutSpots;)Lmeow/candycat/eventsystem/listener/EventHook;
        //   110: iconst_0       
        //   111: anewarray       Ljava/util/function/Predicate;
        //   114: invokespecial   meow/candycat/eventsystem/listener/Listener.<init>:(Lmeow/candycat/eventsystem/listener/EventHook;[Ljava/util/function/Predicate;)V
        //   117: putfield        meow/candycat/uwu/module/modules/render/LogoutSpots.playerJoinEventListener1:Lmeow/candycat/eventsystem/listener/Listener;
        //   120: aload_0         /* this */
        //   121: new             Lmeow/candycat/eventsystem/listener/Listener;
        //   124: dup            
        //   125: aload_0         /* this */
        //   126: invokedynamic   BootstrapMethod #2, invoke:(Lmeow/candycat/uwu/module/modules/render/LogoutSpots;)Lmeow/candycat/eventsystem/listener/EventHook;
        //   131: iconst_0       
        //   132: anewarray       Ljava/util/function/Predicate;
        //   135: invokespecial   meow/candycat/eventsystem/listener/Listener.<init>:(Lmeow/candycat/eventsystem/listener/EventHook;[Ljava/util/function/Predicate;)V
        //   138: putfield        meow/candycat/uwu/module/modules/render/LogoutSpots.playerLeaveEventListener2:Lmeow/candycat/eventsystem/listener/Listener;
        //   141: aload_0         /* this */
        //   142: new             Lmeow/candycat/eventsystem/listener/Listener;
        //   145: dup            
        //   146: aload_0         /* this */
        //   147: invokedynamic   BootstrapMethod #3, invoke:(Lmeow/candycat/uwu/module/modules/render/LogoutSpots;)Lmeow/candycat/eventsystem/listener/EventHook;
        //   152: iconst_0       
        //   153: anewarray       Ljava/util/function/Predicate;
        //   156: invokespecial   meow/candycat/eventsystem/listener/Listener.<init>:(Lmeow/candycat/eventsystem/listener/EventHook;[Ljava/util/function/Predicate;)V
        //   159: putfield        meow/candycat/uwu/module/modules/render/LogoutSpots.unloadListener1:Lmeow/candycat/eventsystem/listener/Listener;
        //   162: aload_0         /* this */
        //   163: new             Lmeow/candycat/eventsystem/listener/Listener;
        //   166: dup            
        //   167: aload_0         /* this */
        //   168: invokedynamic   BootstrapMethod #4, invoke:(Lmeow/candycat/uwu/module/modules/render/LogoutSpots;)Lmeow/candycat/eventsystem/listener/EventHook;
        //   173: iconst_0       
        //   174: anewarray       Ljava/util/function/Predicate;
        //   177: invokespecial   meow/candycat/eventsystem/listener/Listener.<init>:(Lmeow/candycat/eventsystem/listener/EventHook;[Ljava/util/function/Predicate;)V
        //   180: putfield        meow/candycat/uwu/module/modules/render/LogoutSpots.unloadListener2:Lmeow/candycat/eventsystem/listener/Listener;
        //   183: aload_0         /* this */
        //   184: new             Lmeow/candycat/uwu/gui/font/CFontRenderer;
        //   187: dup            
        //   188: new             Lmeow/candycat/uwu/gui/font/CFont$CustomFont;
        //   191: dup            
        //   192: ldc             "/assets/minecraft/font/Comfortaa-Bold.ttf"
        //   194: ldc             16.0
        //   196: iconst_0       
        //   197: invokespecial   meow/candycat/uwu/gui/font/CFont$CustomFont.<init>:(Ljava/lang/String;FI)V
        //   200: iconst_1       
        //   201: iconst_0       
        //   202: invokespecial   meow/candycat/uwu/gui/font/CFontRenderer.<init>:(Lmeow/candycat/uwu/gui/font/CFont$CustomFont;ZZ)V
        //   205: putfield        meow/candycat/uwu/module/modules/render/LogoutSpots.cFontRenderer:Lmeow/candycat/uwu/gui/font/CFontRenderer;
        //   208: aload_0         /* this */
        //   209: invokespecial   meow/candycat/uwu/module/modules/render/LogoutSpots.setupMinecraftColorcodes:()V
        //   212: return         
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
    
    @Override
    public void onUpdate() {
        for (final EntityPlayer entityPlayer : LogoutSpots.mc.field_71441_e.field_73010_i) {
            if (entityPlayer == LogoutSpots.mc.field_71439_g) {
                continue;
            }
            if (this.worldPlayers.contains(entityPlayer)) {
                continue;
            }
            this.worldPlayers.add(entityPlayer);
        }
        this.worldPlayers.removeIf(e -> !LogoutSpots.mc.field_71441_e.field_73010_i.contains(e));
    }
    
    @Override
    public void onWorldRender(final RenderEvent event) {
        if (LogoutSpots.mc.field_71439_g != null && LogoutSpots.mc.field_71441_e != null) {
            double x;
            double y;
            double z;
            int popStr;
            String target;
            TotemPopCounter totemPopCounter;
            this.loggedPlayers.forEach((entity, string) -> {
                if (LogoutSpots.mc.field_71439_g.func_70032_d((Entity)entity) < 500.0f) {
                    GL11.glPushMatrix();
                    this.drawLogoutBox(((Entity)entity).func_184177_bl(), 1, 0, 0, 0, 255);
                    GL11.glPopMatrix();
                    x = ((Entity)entity).field_70165_t - LogoutSpots.mc.func_175598_ae().field_78725_b;
                    y = ((Entity)entity).field_70163_u - LogoutSpots.mc.func_175598_ae().field_78726_c;
                    z = ((Entity)entity).field_70161_v - LogoutSpots.mc.func_175598_ae().field_78723_d;
                    popStr = 0;
                    target = ((Entity)entity).func_70005_c_();
                    totemPopCounter = (TotemPopCounter)ModuleManager.getModuleByName("TotemPopCounter");
                    if (totemPopCounter.popList.get(target) != null) {
                        popStr += totemPopCounter.popList.get(target);
                    }
                    this.renderNameTag(((Entity)entity).func_70005_c_(), x, y, z, event.getPartialTicks(), ((Entity)entity).field_70165_t, ((Entity)entity).field_70163_u, ((Entity)entity).field_70161_v, entity.func_110143_aJ() + entity.func_110139_bj(), ((EntityPlayer)entity).func_184592_cb().func_77973_b() == Items.field_190929_cY || ((EntityPlayer)entity).func_184614_ca().func_77973_b() == Items.field_190929_cY, popStr);
                }
            });
        }
    }
    
    public void onEnable() {
        this.loggedPlayers.clear();
        this.worldPlayers = new ArrayList<EntityPlayer>();
    }
    
    public void onDisable() {
        this.worldPlayers.clear();
    }
    
    @SubscribeEvent
    public void joinWorldEvent(final EntityJoinWorldEvent x) {
        if (x.getEntity() == LogoutSpots.mc.field_71439_g) {
            this.loggedPlayers.clear();
            this.worldPlayers.clear();
        }
    }
    
    public void drawLogoutBox(final AxisAlignedBB bb, final int width, final int r, final int b, final int g, final int a) {
        final String text = this.notificationColourChoice();
        final int colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(1));
        final int colorcode = this.colorCode[colorIndex];
        final Color color = new Color(colorcode >> 16 & 0xFF, colorcode >> 8 & 0xFF, colorcode & 0xFF, 255);
        UwUGodTessellator.drawBoundingBoxForLogoutSpot(bb, (float)width, color.getRGB());
    }
    
    private String notificationColourChoice() {
        switch (this.colourMode.getValue()) {
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
    
    public static Vec3d getInterpolatedPos(final Entity entity, final float ticks) {
        return new Vec3d(entity.field_70142_S, entity.field_70137_T, entity.field_70136_U).func_178787_e(getInterpolatedAmount(entity, ticks));
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double x, final double y, final double z) {
        return new Vec3d((entity.field_70165_t - entity.field_70142_S) * x, (entity.field_70163_u - entity.field_70137_T) * y, (entity.field_70161_v - entity.field_70136_U) * z);
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }
    
    private void renderNameTag(final String name, final double x, final double yi, final double z, final float delta, final double xPos, final double yPos, final double zPos, final double hp, final boolean hasTotem, final int pops) {
        final double y = yi + 0.7;
        final Entity camera = LogoutSpots.mc.func_175606_aa();
        assert camera != null;
        final double originalPositionX = camera.field_70165_t;
        final double originalPositionY = camera.field_70163_u;
        final double originalPositionZ = camera.field_70161_v;
        camera.field_70165_t = this.interpolate(camera.field_70169_q, camera.field_70165_t, delta);
        camera.field_70163_u = this.interpolate(camera.field_70167_r, camera.field_70163_u, delta);
        camera.field_70161_v = this.interpolate(camera.field_70166_s, camera.field_70161_v, delta);
        final String displayTag = name + " [" + (int)xPos + ", " + (int)yPos + ", " + (int)zPos + "]";
        final String displayTag2 = "HP [" + hp + "] : Totem [" + hasTotem + "] : Pops [" + pops + "]";
        final double distance = camera.func_70011_f(x + LogoutSpots.mc.func_175598_ae().field_78730_l, y + LogoutSpots.mc.func_175598_ae().field_78731_m, z + LogoutSpots.mc.func_175598_ae().field_78728_n);
        final int width = this.cFontRenderer.getStringWidth(displayTag) / 2;
        final int width2 = this.cFontRenderer.getStringWidth(displayTag2) / 2;
        double scale = (0.0018 + 12.0 * distance * 0.3) / 1000.0;
        if (distance <= 8.0) {
            scale = 0.0245;
        }
        if (distance <= 2.0) {
            scale = 0.0125;
        }
        GlStateManager.func_179094_E();
        RenderHelper.func_74519_b();
        GlStateManager.func_179088_q();
        GlStateManager.func_179136_a(1.0f, -1500000.0f);
        GlStateManager.func_179140_f();
        GlStateManager.func_179109_b((float)x, (float)y + 1.4f, (float)z);
        GlStateManager.func_179114_b(-LogoutSpots.mc.func_175598_ae().field_78735_i, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(LogoutSpots.mc.func_175598_ae().field_78732_j, (LogoutSpots.mc.field_71474_y.field_74320_O == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179139_a(-scale, -scale, scale);
        GlStateManager.func_179097_i();
        GlStateManager.func_179147_l();
        GlStateManager.func_179147_l();
        UwUGodAnimatedActiveModuleUI.drawRect(-width2 - 2, -(this.cFontRenderer.getHeight() + 1), width2 + 2.0f, 14.0, 1426063360);
        GlStateManager.func_179084_k();
        final String text = this.notificationColourChoice();
        final int colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(1));
        final int colorcode = this.colorCode[colorIndex];
        final Color color = new Color(colorcode >> 16 & 0xFF, colorcode >> 8 & 0xFF, colorcode & 0xFF, 255);
        this.cFontRenderer.drawStringWithShadow(displayTag, -width, -(this.cFontRenderer.getHeight() - 1), color.getRGB());
        this.cFontRenderer.drawStringWithShadow(displayTag2, -width2, -(this.cFontRenderer.getHeight() - 1 - 12), color.getRGB());
        camera.field_70165_t = originalPositionX;
        camera.field_70163_u = originalPositionY;
        camera.field_70161_v = originalPositionZ;
        GlStateManager.func_179126_j();
        GlStateManager.func_179084_k();
        GlStateManager.func_179113_r();
        GlStateManager.func_179136_a(1.0f, 1500000.0f);
        GlStateManager.func_179121_F();
    }
    
    private void renderEnchantmentText(final ItemStack stack, final int x) {
        int enchantmentY = -74;
        final NBTTagList enchants = stack.func_77986_q();
        for (int index = 0; index < enchants.func_74745_c(); ++index) {
            final short id = enchants.func_150305_b(index).func_74765_d("id");
            final short level = enchants.func_150305_b(index).func_74765_d("lvl");
            final Enchantment enc = Enchantment.func_185262_c((int)id);
            if (enc != null) {
                String encName = enc.func_190936_d() ? (TextFormatting.RED + enc.func_77316_c((int)level).substring(11).substring(0, 1).toLowerCase()) : enc.func_77316_c((int)level).substring(0, 1).toLowerCase();
                encName += level;
                GlStateManager.func_179152_a(0.5f, 0.5f, 0.5f);
                this.cFontRenderer.drawString(encName, (float)(x * 4), (float)enchantmentY, -1);
                GlStateManager.func_179139_a(2.0, 2.0, 2.0);
                enchantmentY -= 16;
            }
        }
        if (hasDurability(stack)) {
            final int percent = getRoundedDamage(stack);
            String color;
            if (percent >= 60) {
                color = this.section_sign() + "a";
            }
            else if (percent >= 25) {
                color = this.section_sign() + "e";
            }
            else {
                color = this.section_sign() + "c";
            }
            GlStateManager.func_179152_a(0.5f, 0.5f, 0.5f);
            this.cFontRenderer.drawString(color + percent + "%", (float)(x * 4), (enchantmentY < -124) ? ((float)enchantmentY) : -124.0f, -1);
            GlStateManager.func_179139_a(2.0, 2.0, 2.0);
        }
    }
    
    private double interpolate(final double previous, final double current, final float delta) {
        return previous + (current - previous) * delta;
    }
    
    public String section_sign() {
        return "§";
    }
    
    public static int getItemDamage(final ItemStack stack) {
        return stack.func_77958_k() - stack.func_77952_i();
    }
    
    public static float getDamageInPercent(final ItemStack stack) {
        return getItemDamage(stack) / (float)stack.func_77958_k() * 100.0f;
    }
    
    public static int getRoundedDamage(final ItemStack stack) {
        return (int)getDamageInPercent(stack);
    }
    
    public static boolean hasDurability(final ItemStack stack) {
        final Item item = stack.func_77973_b();
        return item instanceof ItemArmor || item instanceof ItemSword || item instanceof ItemTool || item instanceof ItemShield;
    }
    
    public enum mode
    {
        CHAT, 
        NOTIFICATION;
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
