package com.zerren.zedeng.item.itemblock;

import com.zerren.zedeng.api.materials.ZedBlocks;
import com.zerren.zedeng.core.ModBlocks;
import com.zerren.zedeng.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

/**
 * Created by Zerren on 2/20/2015.
 */
public class ItemBlockVault extends ItemMultiTexture {
    public ItemBlockVault(Block block) {
        super(ZedBlocks.vault, ZedBlocks.vault, Names.Blocks.VAULT_SUBTYPES);
    }
}