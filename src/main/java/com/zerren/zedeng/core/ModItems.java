package com.zerren.zedeng.core;

import com.zerren.zedeng.ZederrianEngineering;
import com.zerren.zedeng.item.ItemKey;
import com.zerren.zedeng.item.ItemZE;
import com.zerren.zedeng.item.tool.ItemZedTool;
import com.zerren.zedeng.reference.Names;
import com.zerren.zedeng.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

/**
 * Created by Zerren on 2/19/2015.
 */
public class ModItems {

    public static Item materials = new ItemZE(Names.Items.MATERIAL, Names.Items.MATERIAL_SUBTYPES, ZederrianEngineering.cTabZE);
    public static Item keys = new ItemKey(Names.Items.KEY, Names.Items.KEY_SUBTYPES, 1, Reference.Textures.Folders.KEY_FOLDER, ZederrianEngineering.cTabZE);
    public static Item tools = new ItemZedTool(Names.Items.TOOL, Names.Items.TOOL_SUBTYPES, 1, Reference.Textures.Folders.TOOL_FOLDER, ZederrianEngineering.cTabZE);

    public static void init() {
        GameRegistry.registerItem(materials, Names.Items.MATERIAL);
        GameRegistry.registerItem(keys, Names.Items.KEY);
        GameRegistry.registerItem(tools, Names.Items.TOOL);
    }
}
