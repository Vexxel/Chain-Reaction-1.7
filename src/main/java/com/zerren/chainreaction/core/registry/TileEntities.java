package com.zerren.chainreaction.core.registry;

import com.zerren.chainreaction.tile.TEMultiBlockBase;
import com.zerren.chainreaction.tile.chest.TEChestBrick;
import com.zerren.chainreaction.tile.chest.TEChestThaumium;
import com.zerren.chainreaction.tile.chest.TEChestVoid;
import com.zerren.chainreaction.tile.mechanism.TEBloomery;
import com.zerren.chainreaction.tile.mechanism.TEElectricHeater;
import com.zerren.chainreaction.tile.mechanism.TEElectrolyzer;
import com.zerren.chainreaction.tile.mechanism.TEStirlingEngine;
import com.zerren.chainreaction.tile.plumbing.*;
import com.zerren.chainreaction.tile.reactor.TERTG;
import com.zerren.chainreaction.tile.reactor.TEPressurizedWaterReactor;
import com.zerren.chainreaction.tile.vault.TEVaultBase;
import com.zerren.chainreaction.tile.vault.TEVaultController;
import com.zerren.chainreaction.tile.vault.TEVaultLock;
import com.zerren.chainreaction.reference.Names;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by Zerren on 2/22/2015.
 */
public final class TileEntities {
    public static void init() {
        GameRegistry.registerTileEntityWithAlternatives(TEMultiBlockBase.class, "multiblock", "tile.multiblock");

        GameRegistry.registerTileEntityWithAlternatives(TEVaultBase.class, Names.Blocks.VAULT, "tile." + Names.Blocks.VAULT);
        GameRegistry.registerTileEntityWithAlternatives(TEVaultLock.class, Names.Blocks.VAULT + "Lock", "tile." + Names.Blocks.VAULT + "Lock");
        GameRegistry.registerTileEntityWithAlternatives(TEVaultController.class, Names.Blocks.VAULT + "Controller", "tile." + Names.Blocks.VAULT + "Controller");

        GameRegistry.registerTileEntityWithAlternatives(TEChestBrick.class, Names.Blocks.CHEST + "Brick", "tile." + Names.Blocks.CHEST + "Brick");
        GameRegistry.registerTileEntityWithAlternatives(TEChestThaumium.class, Names.Blocks.CHEST + "Thaumium", "tile." + Names.Blocks.CHEST + "Thaumium");
        GameRegistry.registerTileEntityWithAlternatives(TEChestVoid.class, Names.Blocks.CHEST + "Void", "tile." + Names.Blocks.CHEST + "Void");

        GameRegistry.registerTileEntityWithAlternatives(TEHeatExchanger.class, Names.Blocks.PLUMBING + "HeatExchanger", "tile." + Names.Blocks.PLUMBING + "HeatExchanger");
        GameRegistry.registerTileEntityWithAlternatives(TEDistroChamber.class, Names.Blocks.PLUMBING + "DistroChamber", "tile." + Names.Blocks.PLUMBING + "DistroChamber");
        GameRegistry.registerTileEntityWithAlternatives(TEPressurePipe.class, Names.Blocks.PLUMBING + "PressurePipe", "tile." + Names.Blocks.PLUMBING + "PressurePipe");
        GameRegistry.registerTileEntityWithAlternatives(TEGasTank.class, Names.Blocks.PLUMBING + "GasTank", "tile." + Names.Blocks.PLUMBING + "GasTank");
        GameRegistry.registerTileEntityWithAlternatives(TEHeatExchangerSmall.class, Names.Blocks.PLUMBING + "HeatExchangerSmall", "tile." + Names.Blocks.PLUMBING + "HeatExchangerSmall");

        GameRegistry.registerTileEntityWithAlternatives(TEPressurizedWaterReactor.class, Names.Blocks.REACTOR + "PWR", "tile." + Names.Blocks.REACTOR + "PWR");

        GameRegistry.registerTileEntityWithAlternatives(TEBloomery.class, Names.Blocks.MECHANISM + "Bloomery", "tile." + Names.Blocks.MECHANISM + "Bloomery");
        GameRegistry.registerTileEntityWithAlternatives(TERTG.class, Names.Blocks.MECHANISM + "RTG", "tile." + Names.Blocks.MECHANISM + "RTG");
        GameRegistry.registerTileEntityWithAlternatives(TEStirlingEngine.class, Names.Blocks.MECHANISM + "StirlingEngine", "tile." + Names.Blocks.MECHANISM + "StirlingEngine");
        GameRegistry.registerTileEntityWithAlternatives(TEElectricHeater.class, Names.Blocks.MECHANISM + "ElectricHeater", "tile." + Names.Blocks.MECHANISM + "ElectricHeater");
        GameRegistry.registerTileEntityWithAlternatives(TEElectrolyzer.class, Names.Blocks.MECHANISM + "Electrolyzer", "tile." + Names.Blocks.MECHANISM + "Electrolyzer");

    }
}
