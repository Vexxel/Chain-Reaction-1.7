package com.zerren.zedeng.core.registry;

import com.zerren.zedeng.block.tile.chest.TEChest;
import com.zerren.zedeng.block.tile.chest.TEChestBrick;
import com.zerren.zedeng.block.tile.chest.TEChestThaumium;
import com.zerren.zedeng.block.tile.chest.TEChestVoid;
import com.zerren.zedeng.block.tile.reactor.TEHeatExchanger;
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

        GameRegistry.registerTileEntityWithAlternatives(TEChestBrick.class, Names.Blocks.CHEST + "Brick", "tile." + Names.Blocks.CHEST + "Brick");
        GameRegistry.registerTileEntityWithAlternatives(TEChestThaumium.class, Names.Blocks.CHEST + "Thaumium", "tile." + Names.Blocks.CHEST + "Thaumium");
        GameRegistry.registerTileEntityWithAlternatives(TEChestVoid.class, Names.Blocks.CHEST + "Void", "tile." + Names.Blocks.CHEST + "Void");

        GameRegistry.registerTileEntityWithAlternatives(TEHeatExchanger.class, Names.Blocks.EXCHANGER, "tile." + Names.Blocks.EXCHANGER);
    }
}
