// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.player;

import net.minecraft.util.math.Vec3d;
import java.util.Iterator;
import meow.candycat.uwu.util.UwUGodTessellator;
import meow.candycat.uwu.util.BlockInteractionHelper;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.Entity;
import meow.candycat.uwu.event.events.RenderEvent;
import javax.annotation.Nonnull;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.block.material.Material;
import meow.candycat.uwu.util.MathUtil;
import java.util.Objects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.init.MobEffects;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketAnimation;
import meow.candycat.uwu.util.LagCompensator;
import meow.candycat.uwu.module.ModuleManager;
import meow.candycat.uwu.module.modules.combat.Aimbot;
import net.minecraft.entity.player.EntityPlayer;
import meow.candycat.uwu.util.EntityUtil;
import java.util.ArrayList;
import java.util.function.Predicate;
import net.minecraft.network.Packet;
import net.minecraft.util.EnumHand;
import meow.candycat.uwu.setting.Settings;
import meow.candycat.uwu.event.events.PacketEvent;
import meow.candycat.uwu.event.events.EventPlayerPreMotionUpdateForPacketMine;
import java.util.Timer;
import meow.candycat.uwu.event.events.BlockEvent;
import java.util.List;
import meow.candycat.eventsystem.listener.EventHandler;
import meow.candycat.uwu.event.events.EventPlayerDamageBlock;
import meow.candycat.eventsystem.listener.Listener;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.math.BlockPos;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "FastBreak", category = Category.PLAYER, description = "Faster Mining")
public class Fastbreak extends Module
{
    public Setting<Float> speedminevalue;
    public Setting<Mode> mode;
    public Setting<Boolean> rotate;
    public Setting<Float> estimatedTime;
    public Setting<Boolean> legitAnimations;
    private static Fastbreak INSTANCE;
    BlockPos lastPos;
    BlockPos breakPos;
    double[] vec;
    boolean shouldRotate;
    long ms;
    long waitTime;
    boolean mined;
    CPacketPlayerDigging shouldSend;
    boolean shouldSendBoolean;
    boolean brokenBefore;
    int bestSlot;
    @EventHandler
    private Listener<EventPlayerDamageBlock> uwu;
    List<Float> tickList;
    @EventHandler
    Listener<BlockEvent> awa;
    boolean couldBeMined;
    boolean switched;
    long switchMs;
    Timer timer;
    int currentItem;
    boolean rotated;
    boolean onGround;
    @EventHandler
    private Listener<EventPlayerPreMotionUpdateForPacketMine> listener;
    @EventHandler
    private Listener<PacketEvent.Send> sendPacketListener;
    
    public Fastbreak() {
        this.speedminevalue = this.register(Settings.f("Speed", 0.3f));
        this.mode = this.register(Settings.e("BreakMode", Mode.NORMAL));
        this.rotate = this.register(Settings.b("Rotate", false));
        this.estimatedTime = this.register(Settings.f("EstimatedTime", 0.9f));
        this.legitAnimations = this.register(Settings.b("LegitAnimations", true));
        this.lastPos = null;
        this.breakPos = null;
        this.vec = new double[] { 0.0, 0.0 };
        this.shouldRotate = false;
        this.ms = -1L;
        this.mined = false;
        this.shouldSend = null;
        this.shouldSendBoolean = false;
        this.brokenBefore = false;
        this.bestSlot = -1;
        this.uwu = new Listener<EventPlayerDamageBlock>(event -> {
            if (this.mode.getValue().equals(Mode.PACKET)) {
                event.cancel();
                Fastbreak.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                if (this.canBreak(event.getPos())) {
                    Fastbreak.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getDirection()));
                    this.shouldSend = new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), event.getDirection());
                    this.breakPos = event.getPos();
                    this.ms = System.currentTimeMillis();
                    this.waitTime = (long)(this.neededTime(Fastbreak.mc.field_71441_e.func_180495_p(event.getPos()), event.getPos()) * this.estimatedTime.getValue());
                    if (this.rotate.getValue()) {
                        this.shouldSendBoolean = true;
                    }
                    this.couldBeMined = false;
                    Fastbreak.mc.field_71442_b.field_78781_i = 5;
                    this.mined = false;
                }
            }
            return;
        }, (Predicate<EventPlayerDamageBlock>[])new Predicate[0]);
        this.tickList = new ArrayList<Float>();
        this.awa = new Listener<BlockEvent>(e -> Fastbreak.mc.field_71442_b.field_78778_j = true, (Predicate<BlockEvent>[])new Predicate[0]);
        this.couldBeMined = false;
        this.switched = false;
        this.switchMs = -1L;
        this.timer = new Timer();
        this.currentItem = -1;
        this.rotated = false;
        this.onGround = false;
        this.listener = new Listener<EventPlayerPreMotionUpdateForPacketMine>(e -> {
            if (this.rotate.getValue()) {
                if (this.breakPos != null) {
                    if ((Fastbreak.mc.field_71439_g.field_71071_by.field_70461_c == this.bestSlot || this.bestSlot == -1) && this.couldBeMined) {
                        this.vec = EntityUtil.calculateLookAt(this.breakPos.field_177962_a + 0.5, this.breakPos.field_177960_b + 0.12, this.breakPos.field_177961_c + 0.5, (EntityPlayer)Fastbreak.mc.field_71439_g);
                        this.vec = new double[] { this.getTrueYaw(this.vec[0]), this.vec[1] };
                        ((Aimbot)ModuleManager.getModuleByName("Aimbot")).setRotation((float)this.vec[0], (float)this.vec[1], false);
                        this.rotated = true;
                    }
                }
                else if (((Aimbot)ModuleManager.getModuleByName("Aimbot")).pitch == this.vec[1] && ((Aimbot)ModuleManager.getModuleByName("Aimbot")).yaw == this.vec[0]) {
                    ((Aimbot)ModuleManager.getModuleByName("Aimbot")).resetRotation();
                }
            }
            if (this.breakPos != null) {
                this.tickList.add(LagCompensator.INSTANCE.getInstantTickRate());
                if (this.legitAnimations.getValue()) {
                    Fastbreak.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                }
            }
            return;
        }, (Predicate<EventPlayerPreMotionUpdateForPacketMine>[])new Predicate[0]);
        CPacketPlayerDigging packet;
        this.sendPacketListener = new Listener<PacketEvent.Send>(e -> {
            if (e.getPacket() instanceof CPacketPlayerDigging && this.mode.getValue() == Mode.PACKET) {
                packet = (CPacketPlayerDigging)e.getPacket();
                if ((packet.func_180762_c() == CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK || packet.func_180762_c() == CPacketPlayerDigging.Action.START_DESTROY_BLOCK || packet.func_180762_c() == CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK) && !this.canBreak(packet.func_179715_a())) {
                    e.cancel();
                }
            }
            else if (e.getPacket() instanceof CPacketHeldItemChange) {
                this.currentItem = ((CPacketHeldItemChange)e.getPacket()).func_149614_c();
            }
            else if (e.getPacket() instanceof CPacketPlayer) {
                this.onGround = ((CPacketPlayer)e.getPacket()).field_149474_g;
            }
            return;
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
        Fastbreak.INSTANCE = this;
    }
    
    public static boolean isSync2() {
        return Fastbreak.INSTANCE.isEnabled() && Fastbreak.INSTANCE.mode.getValue().equals(Mode.NORMAL);
    }
    
    public static float speed() {
        return Fastbreak.INSTANCE.speedminevalue.getValue();
    }
    
    private float neededTime(final IBlockState blockState, final BlockPos pos) {
        double max = 0.0;
        float speed = 0.0f;
        float damage = 0.0f;
        this.bestSlot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Fastbreak.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack.field_190928_g) {
                speed = 1.0f;
            }
            else {
                speed = stack.func_150997_a(blockState);
            }
            final boolean shouldBestSlot = speed != 1.0f;
            if (speed > 1.0f) {
                final int eff;
                speed += (float)(((eff = EnchantmentHelper.func_77506_a(Enchantments.field_185305_q, stack)) > 0) ? (Math.pow(eff, 2.0) + 1.0) : 0.0);
            }
            if (Fastbreak.mc.field_71439_g.func_70644_a(MobEffects.field_76422_e)) {
                speed *= (float)(1.0 + (0.2 * Objects.requireNonNull(Fastbreak.mc.field_71439_g.func_70660_b(MobEffects.field_76422_e)).func_76458_c() + 1.0));
            }
            if (Fastbreak.mc.field_71439_g.func_70644_a(MobEffects.field_76419_f)) {
                speed *= MathUtil.clamp((float)Math.pow(0.3, Objects.requireNonNull(Fastbreak.mc.field_71439_g.func_70660_b(MobEffects.field_76419_f)).func_76458_c() + 1), 8.1E-4f, 0.3f);
            }
            if (Fastbreak.mc.field_71439_g.func_70055_a(Material.field_151586_h) && !EnchantmentHelper.func_185287_i((EntityLivingBase)Fastbreak.mc.field_71439_g)) {
                speed /= 5.0f;
            }
            damage = speed / blockState.func_185887_b((World)Fastbreak.mc.field_71441_e, pos);
            if (this.canHarvestBlock(blockState.func_177230_c(), (EntityPlayer)Fastbreak.mc.field_71439_g, (IBlockAccess)Fastbreak.mc.field_71441_e, pos, i)) {
                damage /= 30.0f;
            }
            else {
                damage /= 100.0f;
            }
            if (damage > 1.0f) {
                return 0.0f;
            }
            if (damage > max) {
                max = damage;
                if (shouldBestSlot) {
                    this.bestSlot = i;
                }
            }
        }
        final float ticks = (float)Math.round(1.0 / max);
        final float seconds = ticks * 50.0f;
        return seconds;
    }
    
    public boolean canHarvestBlock(@Nonnull final Block block, @Nonnull final EntityPlayer player, @Nonnull final IBlockAccess world, @Nonnull final BlockPos pos, final int slot) {
        IBlockState state = world.func_180495_p(pos);
        state = state.func_177230_c().func_176221_a(state, world, pos);
        if (state.func_185904_a().func_76229_l()) {
            return true;
        }
        final ItemStack stack = Fastbreak.mc.field_71439_g.field_71071_by.func_70301_a(slot);
        final String tool = block.getHarvestTool(state);
        if (stack.func_190926_b() || tool == null) {
            return this.canHarvestBlock(state, slot);
        }
        final int toolLevel = stack.func_77973_b().getHarvestLevel(stack, tool, player, state);
        if (toolLevel < 0) {
            return this.canHarvestBlock(state, slot);
        }
        return toolLevel >= block.getHarvestLevel(state);
    }
    
    public boolean canHarvestBlock(final IBlockState state, final int slot) {
        if (state.func_185904_a().func_76229_l()) {
            return true;
        }
        final ItemStack itemstack = Fastbreak.mc.field_71439_g.field_71071_by.func_70301_a(slot);
        return !itemstack.func_190926_b() && itemstack.func_150998_b(state);
    }
    
    @Override
    public void onWorldRender(final RenderEvent event) {
        if (this.breakPos != null) {
            float tickRate = 0.0f;
            for (final Float aFloat : this.tickList) {
                tickRate += aFloat;
            }
            tickRate /= this.tickList.size();
            tickRate /= 20.0f;
            final IBlockState iblockstate = Fastbreak.mc.field_71441_e.func_180495_p(this.breakPos);
            final Vec3d interp = MathUtil.interpolateEntity((Entity)Fastbreak.mc.field_71439_g, Fastbreak.mc.func_184121_ak());
            this.waitTime = (long)(this.neededTime(iblockstate, this.breakPos) * this.estimatedTime.getValue());
            final float time = (float)((System.currentTimeMillis() - this.ms) * (double)tickRate / this.waitTime);
            final float render = MathUtil.clamp(time, 0.0f, 1.0f);
            if (BlockInteractionHelper.getBlock(this.breakPos) instanceof BlockAir || (this.switched && System.currentTimeMillis() - this.switchMs > 750L)) {
                this.breakPos = null;
                this.brokenBefore = true;
                this.tickList.clear();
                this.switched = false;
                this.couldBeMined = false;
                this.bestSlot = -1;
                this.timer.cancel();
                this.rotated = false;
                return;
            }
            if (time >= 1.0f) {
                this.couldBeMined = true;
            }
            UwUGodTessellator.prepare(7);
            double blockLengthX = iblockstate.func_185918_c((World)Fastbreak.mc.field_71441_e, this.breakPos).field_72336_d - iblockstate.func_185918_c((World)Fastbreak.mc.field_71441_e, this.breakPos).field_72340_a;
            double blockLengthZ = iblockstate.func_185918_c((World)Fastbreak.mc.field_71441_e, this.breakPos).field_72334_f - iblockstate.func_185918_c((World)Fastbreak.mc.field_71441_e, this.breakPos).field_72339_c;
            double blockLengthY = iblockstate.func_185918_c((World)Fastbreak.mc.field_71441_e, this.breakPos).field_72337_e - iblockstate.func_185918_c((World)Fastbreak.mc.field_71441_e, this.breakPos).field_72338_b;
            blockLengthY /= 2.0;
            blockLengthX /= 2.0;
            blockLengthZ /= 2.0;
            UwUGodTessellator.drawBox2(iblockstate.func_185918_c((World)Fastbreak.mc.field_71441_e, this.breakPos).func_72317_d(-interp.field_72450_a, -interp.field_72448_b, -interp.field_72449_c).func_72314_b(render * blockLengthX - blockLengthX, render * blockLengthY - blockLengthY, render * blockLengthZ - blockLengthZ), 255, 148, 231, 128, 63);
            UwUGodTessellator.release();
            if ((this.currentItem == this.bestSlot || this.bestSlot == -1) && this.couldBeMined && !this.switched && (!this.rotate.getValue() || this.rotated) && (time >= 5.0f || this.onGround)) {
                this.switched = true;
                this.switchMs = System.currentTimeMillis();
                Fastbreak.mc.field_71439_g.field_71174_a.func_147297_a((Packet)this.shouldSend);
                Fastbreak.mc.field_71442_b.func_187103_a(this.breakPos);
                Fastbreak.mc.field_71441_e.func_175698_g(this.breakPos);
            }
        }
    }
    
    public double getTrueYaw(double yaw) {
        while (yaw < -180.0) {
            yaw += 360.0;
        }
        while (yaw > 180.0) {
            yaw -= 360.0;
        }
        return yaw;
    }
    
    @Override
    public String getHudInfo() {
        return (this.mode.getValue() == Mode.NORMAL) ? "Normal" : ((this.mode.getValue() == Mode.PACKET) ? "Packet" : "Instant");
    }
    
    private boolean canBreak(final BlockPos pos) {
        final IBlockState blockState = Fastbreak.mc.field_71441_e.func_180495_p(pos);
        final Block block = blockState.func_177230_c();
        return block.func_176195_g(blockState, (World)Fastbreak.mc.field_71441_e, pos) != -1.0f;
    }
    
    public enum Mode
    {
        NORMAL, 
        PACKET;
    }
}
