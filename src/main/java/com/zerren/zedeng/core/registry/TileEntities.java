package com.zerren.zedeng.core.registry;

import com.zerren.zedeng.block.tile.vault.TEVaultBase;
import com.zerren.zedeng.block.tile.vault.TEVaultController;
import com.zerren.zedeng.block.tile.vault.TEVaultLock;
import com.zerren.zedeng.reference.Names;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by Zerren on 2/22/2015.
 */
public class TileEntities {
    public static void init() {
        GameRegistry.registerTileEntityWithAlternatives(TEVaultBase.class, Names.Blocks.VAULT, "tile." + Names.Blocks.VAULT);
        GameRegistry.registerTileEntityWithAlternatives(TEVaultLock.class, Names.Blocks.VAULT + "Lock", "tile." + Names.Blocks.VAULT + "Lock");
        GameRegistry.registerTileEntityWithAlternatives(TEVaultController.class, Names.Blocks.VAULT + "Controller", "tile." + Names.Blocks.VAULT + "Controller");
    }
}
