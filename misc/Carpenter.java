// 
// Decompiled by Procyon v0.5.36
// 

package meow.candycat.uwu.module.modules.misc;

import meow.candycat.uwu.util.ColourUtils;
import java.util.List;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import meow.candycat.uwu.module.Module;

@Info(name = "Carpenter", category = Category.MISC)
public class Carpenter extends Module
{
    private int displayList;
    
    public Carpenter() {
        this.displayList = -1;
    }
    
    public static class ShapeBuilder
    {
        private static BlockPos from(final double x, final double y, final double z) {
            return new BlockPos(Math.floor(x), Math.floor(y), Math.floor(z));
        }
        
        public static Shape oval(final BlockPos origin, final double width, final double length) {
            return null;
        }
    }
    
    public static class Selection
    {
        private BlockPos first;
        private BlockPos second;
        
        public Selection(final BlockPos pos1, final BlockPos pos2) {
            this.first = pos1;
            this.second = pos2;
        }
        
        public BlockPos getFirst() {
            return this.first;
        }
        
        public BlockPos getSecond() {
            return this.second;
        }
        
        public void setFirst(final BlockPos first) {
            this.first = first;
        }
        
        public void setSecond(final BlockPos second) {
            this.second = second;
        }
        
        public boolean isInvalid() {
            return this.first == null || this.second == null;
        }
        
        public int getWidth() {
            final int x1 = Math.min(this.first.func_177958_n(), this.second.func_177958_n());
            final int x2 = Math.max(this.first.func_177958_n(), this.second.func_177958_n()) + 1;
            return Math.abs(x1 - x2);
        }
        
        public int getLength() {
            final int z1 = Math.min(this.first.func_177952_p(), this.second.func_177952_p());
            final int z2 = Math.max(this.first.func_177952_p(), this.second.func_177952_p()) + 1;
            return Math.abs(z1 - z2);
        }
        
        public int getHeight() {
            final int y1 = Math.min(this.first.func_177956_o(), this.second.func_177956_o()) + 1;
            final int y2 = Math.max(this.first.func_177956_o(), this.second.func_177956_o());
            return Math.abs(y1 - y2);
        }
        
        public int getSize() {
            return this.getWidth() * this.getLength() * this.getHeight();
        }
        
        public BlockPos getMinimum() {
            final int x1 = Math.min(this.first.func_177958_n(), this.second.func_177958_n());
            final int y1 = Math.min(this.first.func_177956_o(), this.second.func_177956_o());
            final int z1 = Math.min(this.first.func_177952_p(), this.second.func_177952_p());
            return new BlockPos(x1, y1, z1);
        }
        
        public BlockPos getMaximum() {
            final int x1 = Math.min(this.first.func_177958_n(), this.second.func_177958_n()) + 1;
            final int y1 = Math.min(this.first.func_177956_o(), this.second.func_177956_o());
            final int z1 = Math.min(this.first.func_177952_p(), this.second.func_177952_p()) + 1;
            return new BlockPos(x1, y1, z1);
        }
        
        public BlockPos getFurthest(final int x, final int y, final int z) {
            if (x > 0) {
                if (this.first.func_177958_n() > this.second.func_177958_n()) {
                    return this.first;
                }
                return this.second;
            }
            else if (x < 0) {
                if (this.first.func_177958_n() < this.second.func_177958_n()) {
                    return this.first;
                }
                return this.second;
            }
            else if (y > 0) {
                if (this.first.func_177958_n() > this.second.func_177958_n()) {
                    return this.first;
                }
                return this.second;
            }
            else if (y < 0) {
                if (this.first.func_177956_o() < this.second.func_177956_o()) {
                    return this.first;
                }
                return this.second;
            }
            else if (z > 0) {
                if (this.first.func_177952_p() > this.second.func_177952_p()) {
                    return this.first;
                }
                return this.second;
            }
            else {
                if (z >= 0) {
                    return null;
                }
                if (this.first.func_177952_p() < this.second.func_177952_p()) {
                    return this.first;
                }
                return this.second;
            }
        }
        
        public void moveSelection(final int x, final int y, final int z) {
            this.first = this.first.func_177982_a(x, y, z);
            this.second = this.second.func_177982_a(x, y, z);
        }
        
        public void expand(final int amount, final EnumFacing facing) {
            BlockPos affect = this.second;
            switch (facing) {
                case DOWN: {
                    affect = ((this.second.func_177956_o() < this.first.func_177956_o()) ? (this.second = this.second.func_177982_a(0, -amount, 0)) : (this.first = this.first.func_177982_a(0, -amount, 0)));
                    break;
                }
                case UP: {
                    affect = ((this.second.func_177956_o() > this.first.func_177956_o()) ? (this.second = this.second.func_177982_a(0, amount, 0)) : (this.first = this.first.func_177982_a(0, amount, 0)));
                    break;
                }
                case NORTH: {
                    affect = ((this.second.func_177952_p() < this.first.func_177952_p()) ? (this.second = this.second.func_177982_a(0, 0, -amount)) : (this.first = this.first.func_177982_a(0, 0, -amount)));
                    break;
                }
                case SOUTH: {
                    affect = ((this.second.func_177952_p() > this.first.func_177952_p()) ? (this.second = this.second.func_177982_a(0, 0, amount)) : (this.first = this.first.func_177982_a(0, 0, amount)));
                    break;
                }
                case WEST: {
                    affect = ((this.second.func_177958_n() < this.first.func_177958_n()) ? (this.second = this.second.func_177982_a(-amount, 0, 0)) : (this.first = this.first.func_177982_a(-amount, 0, 0)));
                    break;
                }
                case EAST: {
                    affect = ((this.second.func_177958_n() > this.first.func_177958_n()) ? (this.second = this.second.func_177982_a(amount, 0, 0)) : (this.first = this.first.func_177982_a(amount, 0, 0)));
                    break;
                }
            }
        }
    }
    
    public class Shape
    {
        final BlockPos[] blocks;
        private final int colour;
        
        Shape(final List<BlockPos> blocks) {
            this.blocks = blocks.toArray(new BlockPos[0]);
            this.colour = ColourUtils.toRGBA(0.5 + Math.random() * 0.5, 0.5 + Math.random() * 0.5, 0.5 + Math.random() * 0.5, 1.0);
        }
        
        public BlockPos[] getBlocks() {
            return this.blocks;
        }
        
        public int getColour() {
            return this.colour;
        }
    }
}
