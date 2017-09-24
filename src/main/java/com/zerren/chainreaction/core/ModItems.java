package com.zerren.chainreaction.core;

import com.zerren.chainreaction.ChainReaction;
import chainreaction.api.item.CRItems;
import com.zerren.chainreaction.item.*;
import com.zerren.chainreaction.item.armor.ItemOxygenMask;
import com.zerren.chainreaction.item.armor.ItemThrustPack;
import com.zerren.chainreaction.item.tool.ItemBaubleCR;
import com.zerren.chainreaction.item.tool.ItemToolCR;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

/**
 * Created by Zerren on 2/19/2015.
 */
public class ModItems {

    public static void init() {
        register(CRItems.materials = new ItemCRBase(Names.Items.MATERIAL, Names.Items.MATERIAL_SUBTYPES, Reference.Textures.Folders.MATERIAL_FOLDER, ChainReaction.cTabCR), Names.Items.MATERIAL);
        register(CRItems.ingots = new ItemCRBase(Names.Items.INGOT, Names.Items.INGOT_SUBTYPES, Reference.Textures.Folders.MATERIAL_FOLDER, ChainReaction.cTabCR), Names.Items.INGOT);
        register(CRItems.dusts = new ItemCRBase(Names.Items.DUST, Names.Items.DUST_SUBTYPES, Reference.Textures.Folders.MATERIAL_FOLDER, ChainReaction.cTabCR), Names.Items.DUST);
        register(CRItems.keys = new ItemKey(Names.Items.KEY, Names.Items.KEY_SUBTYPES, 1, Reference.Textures.Folders.KEY_FOLDER, ChainReaction.cTabCR), Names.Items.KEY);
        register(CRItems.tools = new ItemToolCR(Names.Items.TOOL, Names.Items.TOOL_SUBTYPES, Reference.Textures.Folders.TOOL_FOLDER, ChainReaction.cTabCR), Names.Items.TOOL);
        register(CRItems.fuelParts = new ItemFuelPart(Names.Items.FUEL_PARTS, Names.Items.FUEL_PART_SUBTYPES, Reference.Textures.Folders.MATERIAL_FOLDER, ChainReaction.cTabCR), Names.Items.FUEL_PARTS);
        register(CRItems.fuel = new ItemFuel(Names.Items.FUEL, Names.Items.FUEL_SUBTYPES, 1, Reference.Textures.Folders.MATERIAL_FOLDER, ChainReaction.cTabCR), Names.Items.FUEL);
        register(CRItems.o2mask = new ItemOxygenMask(Names.Items.O2_MASK, Reference.Textures.Folders.ARMOR_FOLDER, ItemOxygenMask.material, 0, 0, 250000), Names.Items.O2_MASK);
        register(CRItems.thrustPack = new ItemThrustPack(Names.Items.THRUST_PACK, Reference.Textures.Folders.ARMOR_FOLDER, ItemThrustPack.material, 0, 1, 0), Names.Items.THRUST_PACK);
        register(CRItems.musicDisc = new ItemCRMusic(Names.Items.RECORDS, Reference.Textures.Folders.TOOL_FOLDER, "industrial"), Names.Items.RECORDS);
        register(CRItems.ores = new ItemOres(Names.Items.ORES, Names.Items.ORE_SUBTYPES, Reference.Textures.Folders.ORE_FOLDER, ChainReaction.cTabCR), Names.Items.ORES);
        register(CRItems.baubles = new ItemBaubleCR(Names.Items.BAUBLES, Names.Items.BAUBLE_SUBTYPES, Reference.Textures.Folders.BAUBLE_FOLDER, ChainReaction.cTabCR), Names.Items.BAUBLES);
        register(CRItems.baubleMats = new ItemCRBase(Names.Items.BAUBLE_MATERIALS, Names.Items.BAUBLE_MATERIAL_SUBTYPES, Reference.Textures.Folders.BAUBLE_MATERIAL_FOLDER, ChainReaction.cTabCR), Names.Items.BAUBLE_MATERIALS);
        register(CRItems.machineUpgrades = new ItemMachineUpgrade(Names.Items.MACHINE_UPRADE, Names.Items.MACHINE_UPRADE_SUBTYPES, Reference.Textures.Folders.MACHINE_UPGRADE_FOLDER, ChainReaction.cTabCR), Names.Items.MACHINE_UPRADE);
    }

    private static void register(Item item, String name) {
        GameRegistry.registerItem(item, name);
    }
}
