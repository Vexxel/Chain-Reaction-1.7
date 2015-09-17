package com.zerren.chainreaction.core.registry;

import chainreaction.api.recipe.HeatingFluid;
import chainreaction.api.recipe.WorkingFluid;
import com.zerren.chainreaction.core.ModFluids;
import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.utility.ItemRetriever;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * Created by Zerren on 2/26/2015.
 */
public class Recipes {

    private static final short any = Short.MAX_VALUE;
    private static ItemStack ingotChromium;
    private static ItemStack ingotNickel;
    private static ItemStack ingotSS;
    private static ItemStack ingotInconel;
    private static ItemStack ingotGraphite;
    private static ItemStack ingotDepletedUranium;


    public static void init() {
        assignOreDict();

        shapedRecipes();
        shapelessRecipes();
        smeltingRecipes();
        fluidExchanger();
    }

    private static void assignOreDict() {
        ingotChromium = OreDictionary.getOres("ingotChromium").get(0);
        ingotNickel = OreDictionary.getOres("ingotNickel").get(0);
        ingotSS = OreDictionary.getOres("ingotStainlessSteel").get(0);
        ingotInconel = OreDictionary.getOres("ingotInconel").get(0);
        ingotGraphite = OreDictionary.getOres("ingotGraphite").get(0);
        ingotDepletedUranium = OreDictionary.getOres("ingotDepletedUranium").get(0);
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

        //Block of Chrome
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemRetriever.Blocks.metal(1, "blockChromium"),
                "III",
                "III",
                "III", 'I', "ingotChromium"));

        //Block of Nickel
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemRetriever.Blocks.metal(1, "blockNickel"),
                "III",
                "III",
                "III", 'I', "ingotNickel"));

        //Block of DU
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemRetriever.Blocks.metal(1, "blockDepletedUranium"),
                "III",
                "III",
                "III", 'I', "ingotDepletedUranium"));

        //Block of Graphite
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemRetriever.Blocks.metal(1, "blockGraphite"),
                "III",
                "III",
                "III", 'I', "ingotGraphite"));

        //Block of SS
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemRetriever.Blocks.metal(1, "blockStainlessSteel"),
                "III",
                "III",
                "III", 'I', "ingotStainlessSteel"));

        //Block of Inconel
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemRetriever.Blocks.metal(1, "blockInconel"),
                "III",
                "III",
                "III", 'I', "ingotInconel"));
    }

    private static void shapelessRecipes() {
        //9 SS dust = 2Cr + 1Ni + 6Fe
        String dust = ConfigHandler.harderStainless ? "dustSteel" : "dustIron";
        GameRegistry.addRecipe(new ShapelessOreRecipe(ItemRetriever.Items.dust(9, "dustStainlessSteel"),
                "dustChromium", "dustChromium", "dustNickel", dust, dust, dust, dust, dust, dust));

        //9 Inconel dust = 3Cr + 5Ni + 1SS
        GameRegistry.addRecipe(new ShapelessOreRecipe(ItemRetriever.Items.dust(9, "dustInconel"),
                "dustChromium", "dustChromium", "dustChromium", "dustNickel", "dustNickel", "dustNickel", "dustNickel", "dustNickel", "dustStainlessSteel"));
    }

    private static void smeltingRecipes() {
        FurnaceRecipes.smelting().func_151394_a(ItemRetriever.Items.dust(1, "dustChromium"), ingotChromium, 0.5F);
        FurnaceRecipes.smelting().func_151394_a(ItemRetriever.Items.dust(1, "dustNickel"), ingotNickel, 0.5F);
        FurnaceRecipes.smelting().func_151394_a(ItemRetriever.Items.dust(1, "dustStainlessSteel"), ingotSS, 0.5F);
        FurnaceRecipes.smelting().func_151394_a(ItemRetriever.Items.dust(1, "dustInconel"), ingotInconel, 0.5F);
        FurnaceRecipes.smelting().func_151394_a(ItemRetriever.Items.dust(1, "dustGraphite"), ingotGraphite, 0.5F);

        FurnaceRecipes.smelting().func_151394_a(ItemRetriever.Blocks.ore(1, "oreChromium"), ingotChromium, 1F);
        FurnaceRecipes.smelting().func_151394_a(ItemRetriever.Blocks.ore(1, "oreNickel"), ingotNickel, 1F);
        //FurnaceRecipes.smelting().func_151394_a(ItemRetriever.Items.dust(1, "dustDepletedUranium"), ingotDepletedUranium, 0.8F);
    }

    private static void fluidExchanger() {
        HeatingFluid.addHeatingFluid(ModFluids.coolantHotFluid, ModFluids.coolantColdFluid, 20);

        WorkingFluid.addWorkingFluid(new FluidStack(FluidRegistry.WATER, 1), new FluidStack(ModFluids.steam, 160));
        WorkingFluid.addWorkingFluid(new FluidStack(ModFluids.distilledWater, 1), new FluidStack(ModFluids.steam, 160));
    }
}