package com.zerren.chainreaction.item.itemblock;

import chainreaction.api.block.CRBlocks;
import com.zerren.chainreaction.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

/**
 * Created by Zerren on 2/19/2015.
 */
public class ItemBlockMetalMaterial extends ItemMultiTexture {
    public ItemBlockMetalMaterial(Block block) {
        super(CRBlocks.metals, CRBlocks.metals, Names.Blocks.METAL_SUBTYPES);
    }
}