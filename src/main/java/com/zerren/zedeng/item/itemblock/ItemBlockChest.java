package com.zerren.zedeng.item.itemblock;

import com.zerren.zedeng.core.ModBlocks;
import com.zerren.zedeng.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

/**
 * Created by Zerren on 2/28/2015.
 */
public class ItemBlockChest extends ItemMultiTexture {
    public ItemBlockChest(Block block) {
        super(ModBlocks.chest, ModBlocks.chest, Names.Blocks.CHEST_SUBTYPES);
    }
}