package com.zerren.chainreaction.utility;

import chainreaction.api.block.CRBlocks;
import chainreaction.api.item.CRItems;
import com.zerren.chainreaction.reference.Names;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

/**
 * Created by Zerren on 2/19/2015.
 */
public final class ItemRetriever {
    public static class Blocks {
        public static ItemStack vault(int count, String name) {
            return new ItemStack(CRBlocks.vault, count, Arrays.asList(Names.Blocks.VAULT_SUBTYPES).indexOf(name));
        }

        public static ItemStack ore(int count, String name) {
            return new ItemStack(CRBlocks.ores, count, Arrays.asList(Names.Blocks.ORE_SUBTYPES).indexOf(name));
        }

        public static ItemStack metal(int count, String name) {
            return new ItemStack(CRBlocks.metals, count, Arrays.asList(Names.Blocks.METAL_SUBTYPES).indexOf(name));
        }

        public static ItemStack plumbing(int count, String name) {
            return new ItemStack(CRBlocks.plumbing, count, Arrays.asList(Names.Blocks.PLUMBING_SUBTYPES).indexOf(name));
        }
    }

    public static class Items {
        public static ItemStack key(int count, String name) {
            return new ItemStack(CRItems.keys, count, Arrays.asList(Names.Items.KEY_SUBTYPES).indexOf(name));
        }

        public static ItemStack material(int count, String name) {
            return new ItemStack(CRItems.materials, count, Arrays.asList(Names.Items.MATERIAL_SUBTYPES).indexOf(name));
        }

        public static ItemStack ingot(int count, String name) {
            return new ItemStack(CRItems.ingots, count, Arrays.asList(Names.Items.INGOT_SUBTYPES).indexOf(name));
        }

        public static ItemStack dust(int count, String name) {
            return new ItemStack(CRItems.dusts, count, Arrays.asList(Names.Items.DUST_SUBTYPES).indexOf(name));
        }

        public static ItemStack fuelParts(String name) {
            return new ItemStack(CRItems.fuelParts, 1, Arrays.asList(Names.Items.FUEL_PART_SUBTYPES).indexOf(name));
        }

        public static ItemStack fuel(String name) {
            return new ItemStack(CRItems.fuel, 1, Arrays.asList(Names.Items.FUEL_SUBTYPES).indexOf(name));
        }

        public static ItemStack bauble(String name) {
            return new ItemStack(CRItems.baubles, 1, Arrays.asList(Names.Items.BAUBLE_SUBTYPES).indexOf(name));
        }
    }

    public ItemStack getOredictItem(String oredict) {
        return null;
    }
}
