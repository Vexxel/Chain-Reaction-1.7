package com.zerren.zedeng.core.registry;

import com.zerren.zedeng.tile.chest.TEChest;
import com.zerren.zedeng.tile.chest.TEChestThaumium;
import com.zerren.zedeng.tile.chest.TEChestVoid;
import com.zerren.zedeng.tile.plumbing.TEDistroChamber;
import com.zerren.zedeng.tile.plumbing.TEGasTank;
import com.zerren.zedeng.tile.plumbing.TEHeatExchanger;
import com.zerren.zedeng.tile.plumbing.TEPressurePipe;
import com.zerren.zedeng.tile.vault.TEVaultBase;
import com.zerren.zedeng.tile.vault.TEVaultController;
import com.zerren.zedeng.tile.vault.TEVaultLock;
import com.zerren.zedeng.reference.Names;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by Zerren on 2/22/2015.
 */
public final class TileEntities {
    public static void init() {
        GameRegistry.registerTileEntityWithAlternatives(TEVaultBase.class, Names.Blocks.VAULT, "tile." + Names.Blocks.VAULT);
        GameRegistry.registerTileEntityWithAlternatives(TEVaultLock.class, Names.Blocks.VAULT + "Lock", "tile." + Names.Blocks.VAULT + "Lock");
        GameRegistry.registerTileEntityWithAlternatives(TEVaultController.class, Names.Blocks.VAULT + "Controller", "tile." + Names.Blocks.VAULT + "Controller");

        GameRegistry.registerTileEntityWithAlternatives(TEChest.class, Names.Blocks.CHEST + "Brick", "tile." + Names.Blocks.CHEST + "Brick");
        GameRegistry.registerTileEntityWithAlternatives(TEChestThaumium.class, Names.Blocks.CHEST + "Thaumium", "tile." + Names.Blocks.CHEST + "Thaumium");
        GameRegistry.registerTileEntityWithAlternatives(TEChestVoid.class, Names.Blocks.CHEST + "Void", "tile." + Names.Blocks.CHEST + "Void");

        GameRegistry.registerTileEntityWithAlternatives(TEHeatExchanger.class, Names.Blocks.PLUMBING, "tile." + Names.Blocks.PLUMBING);
        GameRegistry.registerTileEntityWithAlternatives(TEDistroChamber.class, Names.Blocks.PLUMBING + "DistroChamber", "tile." + Names.Blocks.PLUMBING + "DistroChamber");
        GameRegistry.registerTileEntityWithAlternatives(TEPressurePipe.class, Names.Blocks.PLUMBING + "PressurePipe", "tile." + Names.Blocks.PLUMBING + "PressurePipe");
        GameRegistry.registerTileEntityWithAlternatives(TEGasTank.class, Names.Blocks.PLUMBING + "GasTank", "tile." + Names.Blocks.PLUMBING + "GasTank");

    }
}
