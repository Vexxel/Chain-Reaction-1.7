package com.zerren.zedeng.item.itemblock;

import com.zerren.zedeng.core.ModBlocks;
import com.zerren.zedeng.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

/**
 * Created by Zerren on 3/6/2015.
 */
public class ItemBlockExchanger extends ItemMultiTexture {
    public ItemBlockExchanger(Block block) {
        super(ModBlocks.exchanger, ModBlocks.exchanger, Names.Blocks.EXCHANGER_SUBTYPES);
    }
}