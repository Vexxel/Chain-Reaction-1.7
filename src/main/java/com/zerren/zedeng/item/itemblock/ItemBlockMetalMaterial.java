package com.zerren.zedeng.item.itemblock;

import com.zerren.zedeng.api.block.ZedBlocks;
import com.zerren.zedeng.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

/**
 * Created by Zerren on 2/19/2015.
 */
public class ItemBlockMetalMaterial extends ItemMultiTexture {
    public ItemBlockMetalMaterial(Block block) {
        super(ZedBlocks.metals, ZedBlocks.metals, Names.Blocks.METAL_SUBTYPES);
    }
}