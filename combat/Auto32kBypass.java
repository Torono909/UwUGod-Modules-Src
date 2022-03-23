// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.combat;

import meow.candycat.uwu.module.modules.player.Scaffold;
import net.minecraft.util.math.Vec3i;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.block.BlockAir;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import meow.candycat.uwu.setting.Settings;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import meow.candycat.uwu.setting.Setting;
import meow.candycat.uwu.module.Module;

@Info(name = "Auto32kBypass", category = Category.COMBAT)
public class Auto32kBypass extends Module
{
    private Setting<Integer> delay;
    int hopperIndex;
    int shulkerIndex;
    int redstoneIndex;
    int dispenserIndex;
    int obiIndex;
    int placeTick;
    BlockPos origin;
    BlockPos hopperPos;
    EnumFacing horizontalFace;
    
    public Auto32kBypass() {
        this.delay = this.register(Settings.i("Delay", 15));
        this.placeTick = 1;
    }
    
    public void onEnable() {
        if (Auto32kBypass.mc == null && Auto32kBypass.mc.field_71439_g == null) {
            return;
        }
        this.obiIndex = -1;
        this.dispenserIndex = -1;
        this.redstoneIndex = -1;
        this.shulkerIndex = -1;
        this.hopperIndex = -1;
        this.placeTick = 1;
        if (Auto32kBypass.mc != null && Auto32kBypass.mc.field_71439_g != null && Auto32kBypass.mc.field_71476_x != null) {
            this.origin = new BlockPos((double)Auto32kBypass.mc.field_71476_x.func_178782_a().func_177958_n(), (double)Auto32kBypass.mc.field_71476_x.func_178782_a().func_177956_o(), (double)Auto32kBypass.mc.field_71476_x.func_178782_a().func_177952_p());
            this.horizontalFace = Auto32kBypass.mc.field_71439_g.func_174811_aO();
            this.hopperPos = this.origin.func_177972_a(this.horizontalFace.func_176734_d()).func_177984_a();
        }
        else {
            this.toggle();
        }
    }
    
    @Override
    public void onUpdate() {
        if (Auto32kBypass.mc == null && Auto32kBypass.mc.field_71439_g == null) {
            return;
        }
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = (ItemStack)Minecraft.func_71410_x().field_71439_g.field_71071_by.field_70462_a.get(i);
            if (itemStack.func_77973_b().equals(Item.func_150898_a((Block)Blocks.field_150438_bZ))) {
                this.hopperIndex = i;
            }
            if (itemStack.func_77973_b().equals(Item.func_150898_a(Blocks.field_150343_Z))) {
                this.obiIndex = i;
            }
            if (itemStack.func_77973_b() instanceof ItemShulkerBox) {
                this.shulkerIndex = i;
            }
            if (itemStack.func_77973_b().equals(Item.func_150898_a(Blocks.field_150451_bX))) {
                this.redstoneIndex = i;
            }
            if (itemStack.func_77973_b().equals(Item.func_150898_a(Blocks.field_150367_z))) {
                this.dispenserIndex = i;
            }
        }
        ++this.placeTick;
        if (this.checkNulls()) {
            if (this.placeTick == 3) {
                final Vec3d vec = new Vec3d((double)this.origin.func_177958_n(), (double)this.origin.func_177956_o(), (double)this.origin.func_177952_p());
                this.changeItem(this.obiIndex);
                this.placeBlock(this.origin, EnumFacing.UP, vec);
                this.changeItem(this.dispenserIndex);
                this.placeBlock(this.origin.func_177984_a(), EnumFacing.UP, vec);
                final BlockPos dispenserPos = this.origin.func_177984_a().func_177984_a();
                this.faceBlock(dispenserPos, EnumFacing.DOWN);
                Auto32kBypass.mc.field_71442_b.func_187099_a(Auto32kBypass.mc.field_71439_g, Auto32kBypass.mc.field_71441_e, dispenserPos, EnumFacing.UP, new Vec3d((double)dispenserPos.func_177958_n(), (double)dispenserPos.func_177956_o(), (double)dispenserPos.func_177952_p()), EnumHand.MAIN_HAND);
                Auto32kBypass.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                this.changeItem(this.shulkerIndex);
                this.placeTick = 4;
            }
            if (this.placeTick == this.delay.getValue() + 4) {
                Auto32kBypass.mc.field_71442_b.func_187098_a(Auto32kBypass.mc.field_71439_g.field_71070_bA.field_75152_c, 1, Auto32kBypass.mc.field_71439_g.field_71071_by.field_70461_c, ClickType.SWAP, (EntityPlayer)Auto32kBypass.mc.field_71439_g);
                Auto32kBypass.mc.field_71439_g.func_71053_j();
                Auto32kBypass.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Minecraft.func_71410_x().field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
                EnumFacing left = null;
                EnumFacing right = null;
                if (this.horizontalFace == EnumFacing.NORTH) {
                    left = EnumFacing.WEST;
                    right = EnumFacing.EAST;
                }
                else if (this.horizontalFace == EnumFacing.EAST) {
                    left = EnumFacing.NORTH;
                    right = EnumFacing.SOUTH;
                }
                else if (this.horizontalFace == EnumFacing.SOUTH) {
                    left = EnumFacing.EAST;
                    right = EnumFacing.WEST;
                }
                else if (this.horizontalFace == EnumFacing.WEST) {
                    left = EnumFacing.SOUTH;
                    right = EnumFacing.NORTH;
                }
                this.changeItem(this.redstoneIndex);
                if (left != null && right != null) {
                    final BlockPos dispenserPos2 = this.origin.func_177984_a().func_177984_a();
                    if (this.isAir(dispenserPos2.func_177972_a(left))) {
                        this.placeBlock(dispenserPos2, left.func_176734_d(), new Vec3d((double)dispenserPos2.func_177958_n(), (double)dispenserPos2.func_177956_o(), (double)dispenserPos2.func_177952_p()));
                    }
                    else if (this.isAir(dispenserPos2.func_177972_a(right))) {
                        this.placeBlock(dispenserPos2, right.func_176734_d(), new Vec3d((double)dispenserPos2.func_177958_n(), (double)dispenserPos2.func_177956_o(), (double)dispenserPos2.func_177952_p()));
                    }
                }
                Auto32kBypass.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)Minecraft.func_71410_x().field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
                this.placeTick = this.delay.getValue() + 4;
            }
            if (this.placeTick == this.delay.getValue() + 15) {
                this.changeItem(this.hopperIndex);
                final BlockPos obiBase = this.origin.func_177984_a();
                this.placeBlock(obiBase, this.horizontalFace.func_176734_d(), new Vec3d((double)obiBase.func_177958_n(), (double)obiBase.func_177956_o(), (double)obiBase.func_177952_p()));
                this.faceBlock(this.hopperPos, EnumFacing.UP);
                Auto32kBypass.mc.field_71442_b.func_187099_a(Auto32kBypass.mc.field_71439_g, Auto32kBypass.mc.field_71441_e, this.hopperPos, EnumFacing.UP, new Vec3d((double)this.hopperPos.func_177958_n(), (double)this.hopperPos.func_177956_o(), (double)this.hopperPos.func_177952_p()), EnumHand.MAIN_HAND);
                Auto32kBypass.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                this.toggle();
            }
        }
        else {
            this.toggle();
        }
    }
    
    public boolean checkNulls() {
        return this.hopperIndex != -1 && this.shulkerIndex != -1 && this.redstoneIndex != -1 && this.dispenserIndex != -1 && this.obiIndex != -1 && this.origin != null && this.horizontalFace != null && this.hopperPos != null;
    }
    
    public boolean isAir(final BlockPos pos) {
        return this.getBlock(pos) instanceof BlockAir;
    }
    
    public Block getBlock(final BlockPos pos) {
        return Auto32kBypass.mc.field_71441_e.func_180495_p(pos).func_177230_c();
    }
    
    public void changeItem(final int slot) {
        Auto32kBypass.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot));
        Auto32kBypass.mc.field_71439_g.field_71071_by.field_70461_c = slot;
    }
    
    public void placeBlock(final BlockPos pos, final EnumFacing facing, final Vec3d vec) {
        final Vec3d hitVec = new Vec3d((Vec3i)pos.func_177972_a(facing)).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(facing.func_176730_m()).func_186678_a(0.5));
        Scaffold.faceVectorPacketInstant(hitVec);
        Auto32kBypass.mc.field_71442_b.func_187099_a(Minecraft.func_71410_x().field_71439_g, Minecraft.func_71410_x().field_71441_e, pos, facing, vec, EnumHand.MAIN_HAND);
        Auto32kBypass.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
    }
    
    public void faceBlock(final BlockPos pos, final EnumFacing face) {
        final Vec3d hitVec = new Vec3d((Vec3i)pos.func_177972_a(face)).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(face.func_176730_m()).func_186678_a(0.5));
        Scaffold.faceVectorPacketInstant(hitVec);
    }
}
