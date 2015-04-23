package com.zerren.zedeng.core;

import com.zerren.zedeng.ZederrianEngineering;
import zedeng.api.block.ZedBlocks;
import com.zerren.zedeng.block.*;
import com.zerren.zedeng.block.fluid.*;
import com.zerren.zedeng.item.itemblock.*;
import com.zerren.zedeng.reference.Names;
import com.zerren.zedeng.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;

/**
 * Created by Zerren on 2/19/2015.
 */
public class ModBlocks {

    public static void init() {
        ZedBlocks.ores = new BlockZE(Names.Blocks.ORE, Names.Blocks.ORE_SUBTYPES, Material.rock, 3F, 5F, Block.soundTypeStone, Reference.Textures.Folders.MATERIAL_FOLDER, ZederrianEngineering.cTabZE);
        ZedBlocks.metals = new BlockZE(Names.Blocks.METAL, Names.Blocks.METAL_SUBTYPES, Material.iron, 3F, 10F, Block.soundTypeMetal, Reference.Textures.Folders.MATERIAL_FOLDER, ZederrianEngineering.cTabZE);
        ZedBlocks.vault = new BlockVault(Names.Blocks.VAULT, Names.Blocks.VAULT_SUBTYPES, Material.rock, 3F, 15F, Block.soundTypeStone, Reference.Textures.Folders.VAULT_FOLDER, ZederrianEngineering.cTabZE);
        ZedBlocks.chest = new BlockZedChest(Names.Blocks.CHEST, Names.Blocks.CHEST_SUBTYPES, Material.rock, 3F, 15F, Block.soundTypeStone, Reference.Textures.Folders.VAULT_FOLDER, ZederrianEngineering.cTabZE);
        ZedBlocks.plumbing = new BlockPlumbing(Names.Blocks.PLUMBING, Names.Blocks.PLUMBING_SUBTYPES, Material.iron, 3F, 10F, Block.soundTypeMetal, Reference.Textures.Folders.PLUMBING_FOLDER, ZederrianEngineering.cTabZE);
        ZedBlocks.reactor = new BlockReactor(Names.Blocks.REACTOR, Names.Blocks.REACTOR_SUBTYPES, Material.iron, 3F, 15F, Block.soundTypeMetal, Reference.Textures.Folders.REACTOR_FOLDER, ZederrianEngineering.cTabZE);

        register();
    }
    
    private static void register() {

        GameRegistry.registerBlock(ZedBlocks.ores, ItemBlockStoneMaterial.class, Names.Blocks.ORE);
        GameRegistry.registerBlock(ZedBlocks.metals, ItemBlockMetalMaterial.class, Names.Blocks.METAL);
        GameRegistry.registerBlock(ZedBlocks.vault, ItemBlockVault.class, Names.Blocks.VAULT);
        GameRegistry.registerBlock(ZedBlocks.chest, ItemBlockChest.class, Names.Blocks.CHEST);
        GameRegistry.registerBlock(ZedBlocks.plumbing, ItemBlockExchanger.class, Names.Blocks.PLUMBING);
        GameRegistry.registerBlock(ZedBlocks.reactor, ItemBlockReactor.class, Names.Blocks.REACTOR);


        fluidBlocks();
    }

    private static void fluidBlocks() {
        if (ModFluids.coolantColdFluid.getBlock() == null) {
            ZedBlocks.coolantCold = new BlockFluidCoolantCold(ModFluids.coolantColdFluid, Material.water, Names.Fluids.COOLANT_COLD, 7, 5, 100F, 3);
            GameRegistry.registerBlock(ZedBlocks.coolantCold, Names.Fluids.COOLANT_COLD);
            ModFluids.coolantColdFluid.setBlock(ZedBlocks.coolantCold);
        }
        else {
            ZedBlocks.coolantCold = ModFluids.coolantColdFluid.getBlock();
        }

        if (ModFluids.coolantHotFluid.getBlock() == null) {
            ZedBlocks.coolantHot = new BlockFluidCoolantHot(ModFluids.coolantHotFluid, Material.water, Names.Fluids.COOLANT_HOT, 8, 5, 100F, 3);
            GameRegistry.registerBlock(ZedBlocks.coolantHot, Names.Fluids.COOLANT_HOT);
            ModFluids.coolantHotFluid.setBlock(ZedBlocks.coolantHot);
        }
        else {
            ZedBlocks.coolantHot = ModFluids.coolantHotFluid.getBlock();
        }

        if (ModFluids.distilledWater.getBlock() == null) {
            ZedBlocks.distilledWater = new BlockFluidZE(ModFluids.distilledWater, Material.water, Names.Fluids.DISTILLED_WATER, 8, 5, 100F, 3, true);
            GameRegistry.registerBlock(ZedBlocks.distilledWater, Names.Fluids.DISTILLED_WATER);
            ModFluids.distilledWater.setBlock(ZedBlocks.distilledWater);
        }
        else {
            ZedBlocks.distilledWater = ModFluids.distilledWater.getBlock();
        }

        if (ModFluids.steam.getBlock() == null) {
            ZedBlocks.steam = new BlockFluidSteam(ModFluids.steam, new MaterialLiquid(MapColor.silverColor), Names.Fluids.STEAM, 5, 2, 1F, 1);
            GameRegistry.registerBlock(ZedBlocks.steam, Names.Fluids.STEAM);
            ModFluids.steam.setBlock(ZedBlocks.steam);
        }
        else {
            ZedBlocks.steam = ModFluids.steam.getBlock();
        }

        if (ModFluids.uf6.getBlock() == null) {
            ZedBlocks.uf6 = new BlockFluidUF6(ModFluids.uf6, new MaterialLiquid(MapColor.greenColor), Names.Fluids.UF6, 4, 20, 1F, 4);
            GameRegistry.registerBlock(ZedBlocks.uf6, Names.Fluids.UF6);
            ModFluids.uf6.setBlock(ZedBlocks.uf6);
        }
        else {
            ZedBlocks.uf6 = ModFluids.uf6.getBlock();
        }
    }
}