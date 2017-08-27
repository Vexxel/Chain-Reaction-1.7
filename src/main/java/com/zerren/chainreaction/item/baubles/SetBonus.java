package com.zerren.chainreaction.item.baubles;

import com.zerren.chainreaction.utility.ItemRetriever;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 8/26/2017.
 */
public enum SetBonus {
    SKULLFIRE("witherAmulet", "powerRing"),
    NONE;

    private final String piece1;
    private final String piece2;

    SetBonus(String p1, String p2) {
        this.piece1 = p1;
        this.piece2 = p2;
    }

    SetBonus() {
        this.piece1 = null;
        this.piece2 = null;
    }

    public ItemStack getBauble1() {
        return ItemRetriever.Items.bauble(piece1);
    }

    public ItemStack getBauble2() {
        return ItemRetriever.Items.bauble(piece2);
    }
}
