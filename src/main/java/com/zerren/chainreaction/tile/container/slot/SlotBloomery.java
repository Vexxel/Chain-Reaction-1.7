package com.zerren.chainreaction.tile.container.slot;

import chainreaction.api.item.ISolidReactorFuel;
import chainreaction.api.reactor.ReactorType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 5/21/2016.
 */
public class SlotBloomery extends Slot {

    public SlotBloomery(IInventory inv, int i, int j, int k) {
        super(inv, i, j, k);
    }


    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }
}
