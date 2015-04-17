package com.zerren.zedeng.core;

import com.zerren.zedeng.ZederrianEngineering;
import com.zerren.zedeng.api.item.ZedItems;
import com.zerren.zedeng.item.ItemKey;
import com.zerren.zedeng.item.ItemZE;
import com.zerren.zedeng.item.ItemZedFuel;
import com.zerren.zedeng.item.tool.ItemZedTool;
import com.zerren.zedeng.reference.Names;
import com.zerren.zedeng.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by Zerren on 2/19/2015.
 */
public class ModItems {

    public static void init() {
        ZedItems.materials = new ItemZE(Names.Items.MATERIAL, Names.Items.MATERIAL_SUBTYPES, Reference.Textures.Folders.MATERIAL_FOLDER, ZederrianEngineering.cTabZE);
        ZedItems.ingots = new ItemZE(Names.Items.INGOT, Names.Items.INGOT_SUBTYPES, Reference.Textures.Folders.MATERIAL_FOLDER, ZederrianEngineering.cTabZE);
        ZedItems.dusts = new ItemZE(Names.Items.DUST, Names.Items.DUST_SUBTYPES, Reference.Textures.Folders.MATERIAL_FOLDER, ZederrianEngineering.cTabZE);
        ZedItems.keys = new ItemKey(Names.Items.KEY, Names.Items.KEY_SUBTYPES, 1, Reference.Textures.Folders.KEY_FOLDER, ZederrianEngineering.cTabZE);
        ZedItems.tools = new ItemZedTool(Names.Items.TOOL, Names.Items.TOOL_SUBTYPES, 1, Reference.Textures.Folders.TOOL_FOLDER, ZederrianEngineering.cTabZE);
        ZedItems.fuel = new ItemZedFuel(Names.Items.FUEL, Names.Items.FUEL_SUBTYPES, Reference.Textures.Folders.MATERIAL_FOLDER, ZederrianEngineering.cTabZE);

        register();
    }

    private static void register() {
        GameRegistry.registerItem(ZedItems.materials, Names.Items.MATERIAL);
        GameRegistry.registerItem(ZedItems.ingots, Names.Items.INGOT);
        GameRegistry.registerItem(ZedItems.dusts, Names.Items.DUST);
        GameRegistry.registerItem(ZedItems.keys, Names.Items.KEY);
        GameRegistry.registerItem(ZedItems.tools, Names.Items.TOOL);
        GameRegistry.registerItem(ZedItems.fuel, Names.Items.FUEL);
    }
}
