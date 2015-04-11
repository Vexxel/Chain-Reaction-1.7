package com.zerren.zedeng.core.registry;

import com.zerren.zedeng.api.materials.ZedBlocks;
import com.zerren.zedeng.api.materials.ZedItems;
import com.zerren.zedeng.core.ModItems;
import com.zerren.zedeng.handler.ConfigHandler;
import com.zerren.zedeng.reference.Names;
import com.zerren.zedeng.utility.ItemRetriever;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by Zerren on 2/20/2015.
 */
public final class ZEDictionary {

    public static void init() {
        registerBlocks();
        registerItems();
        registerOptional();
    }

    private static void registerBlocks() {
        //Ore
        register(Names.Blocks.ORE_SUBTYPES, ZedBlocks.ores);
        //Metals
        register(Names.Blocks.METAL_SUBTYPES, ZedBlocks.metals);

        //blockChrome <-> blockChromium
        OreDictionary.registerOre("blockChrome", ItemRetriever.Blocks.metal(1, "blockChromium"));
    }

    private static void registerItems() {
        //materials
        register(Names.Items.MATERIAL_SUBTYPES, ZedItems.materials);
        //ingots
        register(Names.Items.INGOT_SUBTYPES, ZedItems.ingots);
        //dusts
        register(Names.Items.DUST_SUBTYPES, ZedItems.dusts);

        //ingotChrome <-> ingotChromium
        OreDictionary.registerOre("ingotChrome", ItemRetriever.Items.ingot(1, "ingotChromium"));
    }

    private static void registerOptional() {

    }

    private static void register(String[] types, Block block) {
        for (int i = 0; i < types.length; i++)
            OreDictionary.registerOre(types[i], new ItemStack(block, 1, i));
    }

    private static void register(String[] types, Item item) {
        for (int i = 0; i < types.length; i++)
            OreDictionary.registerOre(types[i], new ItemStack(item, 1, i));
    }
}
