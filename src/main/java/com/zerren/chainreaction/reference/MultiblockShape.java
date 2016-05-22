package com.zerren.chainreaction.reference;

import chainreaction.api.block.CRBlocks;
import com.zerren.chainreaction.block.BlockCR;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 2/3/2016.
 */
public final class MultiblockShape {

    private Block block;
    private int meta;

    //length, height, depth
    public static MultiblockShape[][][] pwr = new MultiblockShape[3][7][3];
    static {
        for (int h = 0; h < 7; h++) {
            for (int l = 0; l < 3; l++) {
                for (int d = 0; d < 3; d++) {
                    //bottom and top, pure casing
                    if (h == 0 || h == 6) pwr[l][h][d] = new MultiblockShape(CRBlocks.reactor, 1);
                    //levels 2 and 4 are 5 assemblies + 4 casings
                    if (h == 1 || h == 3) {
                        //front row & back row, not the middle
                        if ((d == 0 || d == 2) && l != 1) pwr[l][h][d] = new MultiblockShape(CRBlocks.reactor, 1);
                        if (((d == 0 || d == 2) && l == 1) || d == 1) pwr[l][h][d] = new MultiblockShape(CRBlocks.reactor, 0);
                    }
                    //levels 3, 5, and 6 are a single assembly surrounded by casings
                    if (h == 2 || h == 4 || h == 5) {
                        if (d == 1 && l == 1) pwr[l][h][d] = new MultiblockShape(CRBlocks.reactor, 0);
                        else pwr[l][h][d] = new MultiblockShape(CRBlocks.reactor, 1);
                    }
                }
            }
        }
    }

    public static MultiblockShape[][][] bloomery = new MultiblockShape[3][4][3];
    static {
        for (int h = 0; h < 4; h++) {
            for (int l = 0; l < 3; l++) {
                for (int d = 0; d < 3; d++) {
                    bloomery[l][h][d] = new MultiblockShape(CRBlocks.mechanism, 1);
                }
            }
        }
    }

    public MultiblockShape(Block block, int meta) {
        this.block = block;
        this.meta = meta;
    }

    public int getMetadata() {
        return this.meta;
    }

    public Block getBlock() {
        if (this.block == null) return Blocks.air;
        return this.block;
    }
}
