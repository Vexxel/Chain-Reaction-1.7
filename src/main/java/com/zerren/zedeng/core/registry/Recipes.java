package com.zerren.zedeng.core.registry;

import com.zerren.zedeng.utility.ItemRetriever;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * Created by Zerren on 2/26/2015.
 */
public class Recipes {

    private static final short any = Short.MAX_VALUE;

    public static void init() {
        shapedRecipes();
        shapelessRecipes();
    }

    private static void shapedRecipes() {
        //blocks

        //vault wall
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemRetriever.Blocks.vault(4, "wall"),
                "BIB",
                "IHI",
                "BIB", 'I', "ingotIron", 'B', Blocks.brick_block, 'H', Blocks.heavy_weighted_pressure_plate));

        //vault door (closed)
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemRetriever.Blocks.vault(1, "door_closed"),
                "IWI",
                "RDR",
                "HPH", 'I', "ingotIron", 'H', Blocks.heavy_weighted_pressure_plate, 'D', Items.iron_door,
                        'W', ItemRetriever.Blocks.vault(1, "wall"), 'R', Items.redstone, 'P', Blocks.piston));

        //vault lock mechanism
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemRetriever.Blocks.vault(1, "lock"),
                "IWI",
                "CLC",
                "H H", 'I', "ingotIron", 'H', Blocks.heavy_weighted_pressure_plate, 'L', Blocks.light_weighted_pressure_plate,
                'W', ItemRetriever.Blocks.vault(1, "wall"), 'C', Items.comparator));

        //vault storage container
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemRetriever.Blocks.vault(1, "container"),
                " W ",
                "RIR",
                " C ", 'I', "ingotIron", 'C', Blocks.chest,
                'W', ItemRetriever.Blocks.vault(1, "wall"), 'R', Items.redstone));

        //vault controller
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemRetriever.Blocks.vault(1, "controller"),
                "DCD",
                "PWP",
                "DCD", 'D', Items.diamond, 'C', Items.comparator,
                'W', ItemRetriever.Blocks.vault(1, "wall"), 'P', Blocks.piston));

        //items

        //iron key
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemRetriever.Items.key(1, "iron"),
                "III",
                "IIS", 'I', "ingotIron", 'S', Items.string));

        //gold key
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemRetriever.Items.key(1, "gold"),
                "III",
                "IIS", 'I', "ingotGold", 'S', Items.string));

        //emerald key
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemRetriever.Items.key(1, "emerald"),
                "III",
                "IIS", 'I', Items.emerald, 'S', Items.string));

        //diamond key
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemRetriever.Items.key(1, "diamond"),
                "III",
                "IIS", 'I', Items.diamond, 'S', Items.string));

        //bedrock
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemRetriever.Items.key(1, "bedrock"),
                "III",
                "IIS", 'I', Blocks.bedrock, 'S', Items.nether_star));
    }

    private static void shapelessRecipes() {

    }
}
