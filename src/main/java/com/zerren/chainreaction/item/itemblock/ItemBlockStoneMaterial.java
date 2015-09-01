package com.zerren.chainreaction.item.itemblock;

import chainreaction.api.block.CRBlocks;
import com.zerren.chainreaction.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

/**
 * Created by Zerren on 2/19/2015.
 */
public class ItemBlockStoneMaterial extends ItemMultiTexture {
    public ItemBlockStoneMaterial(Block block) {
        super(CRBlocks.ores, CRBlocks.ores, Names.Blocks.ORE_SUBTYPES);
    }
}