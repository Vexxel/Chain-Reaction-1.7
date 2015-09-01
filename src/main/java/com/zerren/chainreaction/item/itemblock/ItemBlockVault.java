package com.zerren.chainreaction.item.itemblock;

import chainreaction.api.block.CRBlocks;
import com.zerren.chainreaction.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

/**
 * Created by Zerren on 2/20/2015.
 */
public class ItemBlockVault extends ItemMultiTexture {
    public ItemBlockVault(Block block) {
        super(CRBlocks.vault, CRBlocks.vault, Names.Blocks.VAULT_SUBTYPES);
    }
}