package com.zerren.chainreaction.tile.container.slot;

import chainreaction.api.item.ISolidReactorFuel;
import chainreaction.api.reactor.ReactorType;
import chainreaction.api.recipe.RTGFuels;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

/**
 * Created by Zerren on 2/6/2016.
 */
public class SlotLiquidContainer extends Slot {

    private boolean filled;
    public SlotLiquidContainer(IInventory inv, int i, int j, int k, boolean filled) {
        super(inv, i, j, k);

        this.filled = filled;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return filled && FluidContainerRegistry.isFilledContainer(stack) || !filled && FluidContainerRegistry.isEmptyContainer(stack);
    }
}
