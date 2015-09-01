package com.zerren.chainreaction.item.itemblock;

import chainreaction.api.block.CRBlocks;
import com.zerren.chainreaction.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

/**
 * Created by Zerren on 2/28/2015.
 */
public class ItemBlockChest extends ItemMultiTexture {
    public ItemBlockChest(Block block) {
        super(CRBlocks.chest, CRBlocks.chest, Names.Blocks.CHEST_SUBTYPES);
    }
}