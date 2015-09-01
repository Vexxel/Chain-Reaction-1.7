package com.zerren.chainreaction.core.registry;

import chainreaction.api.block.CRBlocks;
import chainreaction.api.item.CRItems;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.utility.ItemRetriever;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by Zerren on 2/20/2015.
 */
public final class CRDictionary {

    public static void init() {
        registerBlocks();
        registerItems();
        registerOptional();
    }

    private static void registerBlocks() {
        //Ore
        register(Names.Blocks.ORE_SUBTYPES, CRBlocks.ores);
        //Metals
        register(Names.Blocks.METAL_SUBTYPES, CRBlocks.metals);

        //blockChrome <-> blockChromium
        OreDictionary.registerOre("blockChrome", ItemRetriever.Blocks.metal(1, "blockChromium"));
    }

    private static void registerItems() {
        //block
        register(Names.Items.MATERIAL_SUBTYPES, CRItems.materials);
        //ingots
        register(Names.Items.INGOT_SUBTYPES, CRItems.ingots);
        //dusts
        register(Names.Items.DUST_SUBTYPES, CRItems.dusts);

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
