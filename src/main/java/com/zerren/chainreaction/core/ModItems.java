package com.zerren.chainreaction.core;

import com.zerren.chainreaction.ChainReaction;
import chainreaction.api.item.CRItems;
import com.zerren.chainreaction.item.ItemKey;
import com.zerren.chainreaction.item.ItemCRBase;
import com.zerren.chainreaction.item.ItemFuel;
import com.zerren.chainreaction.item.armor.ItemOxygenMask;
import com.zerren.chainreaction.item.tool.ItemToolCR;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by Zerren on 2/19/2015.
 */
public class ModItems {

    public static void init() {
        CRItems.materials = new ItemCRBase(Names.Items.MATERIAL, Names.Items.MATERIAL_SUBTYPES, Reference.Textures.Folders.MATERIAL_FOLDER, ChainReaction.cTabZE);
        CRItems.ingots = new ItemCRBase(Names.Items.INGOT, Names.Items.INGOT_SUBTYPES, Reference.Textures.Folders.MATERIAL_FOLDER, ChainReaction.cTabZE);
        CRItems.dusts = new ItemCRBase(Names.Items.DUST, Names.Items.DUST_SUBTYPES, Reference.Textures.Folders.MATERIAL_FOLDER, ChainReaction.cTabZE);
        CRItems.keys = new ItemKey(Names.Items.KEY, Names.Items.KEY_SUBTYPES, 1, Reference.Textures.Folders.KEY_FOLDER, ChainReaction.cTabZE);
        CRItems.tools = new ItemToolCR(Names.Items.TOOL, Names.Items.TOOL_SUBTYPES, 1, Reference.Textures.Folders.TOOL_FOLDER, ChainReaction.cTabZE);
        CRItems.fuel = new ItemFuel(Names.Items.FUEL, Names.Items.FUEL_SUBTYPES, Reference.Textures.Folders.MATERIAL_FOLDER, ChainReaction.cTabZE);
        CRItems.o2mask = new ItemOxygenMask(Names.Items.O2_MASK, Reference.Textures.Folders.ARMOR_FOLDER, ItemOxygenMask.material, 0, 0);

        register();
    }

    private static void register() {
        GameRegistry.registerItem(CRItems.materials, Names.Items.MATERIAL);
        GameRegistry.registerItem(CRItems.ingots, Names.Items.INGOT);
        GameRegistry.registerItem(CRItems.dusts, Names.Items.DUST);
        GameRegistry.registerItem(CRItems.keys, Names.Items.KEY);
        GameRegistry.registerItem(CRItems.tools, Names.Items.TOOL);
        GameRegistry.registerItem(CRItems.fuel, Names.Items.FUEL);
        GameRegistry.registerItem(CRItems.o2mask, Names.Items.O2_MASK);
    }
}