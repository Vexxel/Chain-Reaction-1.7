package com.zerren.zedeng.item.itemblock;

import zedeng.api.block.ZedBlocks;
import com.zerren.zedeng.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

/**
 * Created by Zerren on 2/28/2015.
 */
public class ItemBlockChest extends ItemMultiTexture {
    public ItemBlockChest(Block block) {
        super(ZedBlocks.chest, ZedBlocks.chest, Names.Blocks.CHEST_SUBTYPES);
    }
}