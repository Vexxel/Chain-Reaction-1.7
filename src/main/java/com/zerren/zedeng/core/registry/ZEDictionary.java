package com.zerren.zedeng.core.registry;

import com.zerren.zedeng.api.materials.ZedItems;
import com.zerren.zedeng.core.ModItems;
import com.zerren.zedeng.reference.Names;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by Zerren on 2/20/2015.
 */
public class ZEDictionary {

    public static void init() {
        registerItems();
    }

    private static void registerItems() {
        for (int i = 0; i < Names.Items.MATERIAL_SUBTYPES.length; i++) {
            OreDictionary.registerOre(Names.Items.MATERIAL_SUBTYPES[i], new ItemStack(ZedItems.materials, 1, i));
        }
    }
}
