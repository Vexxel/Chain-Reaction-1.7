package com.zerren.zedeng.utility;

import com.zerren.zedeng.core.ModBlocks;
import com.zerren.zedeng.core.ModItems;
import com.zerren.zedeng.reference.Names;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

/**
 * Created by Zerren on 2/19/2015.
 */
public final class ItemRetriever {
    public static final class Blocks {
        public static ItemStack vault(int count, String name) {
            return new ItemStack(ModBlocks.vault, count, Arrays.asList(Names.Blocks.VAULT_SUBTYPES).indexOf(name));
        }
    }

    public static final class Items {
        public static ItemStack key(int count, String name) {
            return new ItemStack(ModItems.keys, count, Arrays.asList(Names.Items.KEY_SUBTYPES).indexOf(name));
        }

        public static ItemStack material(int count, String name) {
            return new ItemStack(ModItems.materials, count, Arrays.asList(Names.Items.MATERIAL_SUBTYPES).indexOf(name));
        }
    }
}
