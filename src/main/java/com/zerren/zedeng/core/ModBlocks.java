package com.zerren.zedeng.core;

import com.zerren.zedeng.ZederrianEngineering;
import com.zerren.zedeng.block.BlockGlass;
import com.zerren.zedeng.block.BlockVault;
import com.zerren.zedeng.block.BlockZE;
import com.zerren.zedeng.item.itemblock.ItemBlockGlass;
import com.zerren.zedeng.item.itemblock.ItemBlockVault;
import com.zerren.zedeng.reference.Names;
import com.zerren.zedeng.reference.Textures;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Created by Zerren on 2/19/2015.
 */
public class ModBlocks {

    public static BlockZE glass = new BlockGlass(Names.Blocks.GLASS, Names.Blocks.GLASS_SUBTYPES, Material.glass, 0.4F, 2F, Block.soundTypeGlass, Textures.folders.GLASS_FOLDER, ZederrianEngineering.cTabZE);
    public static BlockZE vault = new BlockVault(Names.Blocks.VAULT, Names.Blocks.VAULT_SUBTYPES, Material.rock, 3F, 15F, Block.soundTypeStone, Textures.folders.VAULT_FOLDER, ZederrianEngineering.cTabZE);

    public static void init() {
        GameRegistry.registerBlock(glass, ItemBlockGlass.class, Names.Blocks.GLASS);
        GameRegistry.registerBlock(vault, ItemBlockVault.class, Names.Blocks.VAULT);
    }
}