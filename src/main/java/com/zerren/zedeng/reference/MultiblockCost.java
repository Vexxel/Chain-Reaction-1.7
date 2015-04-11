package com.zerren.zedeng.reference;

import com.zerren.zedeng.utility.ItemRetriever;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 4/8/2015.
 */
public final class MultiblockCost {

    public static ItemStack[] LIQUID_HEAT_EXCHANGER;

    public static void init() {
        LIQUID_HEAT_EXCHANGER = new ItemStack[]{
                ItemRetriever.Items.material(16, "boltStainlessSteel"),
                ItemRetriever.Items.material(4, "plateStainlessSteel"),
                ItemRetriever.Items.material(4, "tubeInconel")
        };
    }
}
