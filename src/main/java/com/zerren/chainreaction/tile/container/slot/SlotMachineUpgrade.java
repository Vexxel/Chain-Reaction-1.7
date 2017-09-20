package com.zerren.chainreaction.tile.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

/**
 * Created by Zerren on 2/6/2016.
 */
public class SlotMachineUpgrade extends Slot {

    public SlotMachineUpgrade(IInventory inv, int i, int j, int k) {
        super(inv, i, j, k);

    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }
}
