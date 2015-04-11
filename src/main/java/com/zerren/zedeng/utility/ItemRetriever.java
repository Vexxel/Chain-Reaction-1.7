package com.zerren.zedeng.utility;

import com.zerren.zedeng.api.materials.ZedBlocks;
import com.zerren.zedeng.api.materials.ZedItems;
import com.zerren.zedeng.core.ModBlocks;
import com.zerren.zedeng.core.ModItems;
import com.zerren.zedeng.reference.Names;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

/**
 * Created by Zerren on 2/19/2015.
 */
public final class ItemRetriever {
    public static class Blocks {
        public static ItemStack vault(int count, String name) {
            return new ItemStack(ZedBlocks.vault, count, Arrays.asList(Names.Blocks.VAULT_SUBTYPES).indexOf(name));
        }

        public static ItemStack ore(int count, String name) {
            return new ItemStack(ZedBlocks.ores, count, Arrays.asList(Names.Blocks.ORE_SUBTYPES).indexOf(name));
        }

        public static ItemStack metal(int count, String name) {
            return new ItemStack(ZedBlocks.metals, count, Arrays.asList(Names.Blocks.METAL_SUBTYPES).indexOf(name));
        }
    }

    public static class Items {
        public static ItemStack key(int count, String name) {
            return new ItemStack(ZedItems.keys, count, Arrays.asList(Names.Items.KEY_SUBTYPES).indexOf(name));
        }

        public static ItemStack material(int count, String name) {
            return new ItemStack(ZedItems.materials, count, Arrays.asList(Names.Items.MATERIAL_SUBTYPES).indexOf(name));
        }

        public static ItemStack ingot(int count, String name) {
            return new ItemStack(ZedItems.ingots, count, Arrays.asList(Names.Items.INGOT_SUBTYPES).indexOf(name));
        }

        public static ItemStack dust(int count, String name) {
            return new ItemStack(ZedItems.dusts, count, Arrays.asList(Names.Items.DUST_SUBTYPES).indexOf(name));
        }
    }
}
