package com.zerren.chainreaction.item.itemblock;

import chainreaction.api.block.CRBlocks;
import com.zerren.chainreaction.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

/**
 * Created by Zerren on 9/22/2015.
 */
public class ItemBlockMechanism extends ItemMultiTexture {
    public ItemBlockMechanism(Block block) {
        super(CRBlocks.mechanism, CRBlocks.mechanism, Names.Blocks.MECHANISM_SUBTYPES);
    }
}
