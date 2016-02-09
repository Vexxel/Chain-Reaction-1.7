package com.zerren.chainreaction.tile.container.slot;

import chainreaction.api.item.ISolidReactorFuel;
import chainreaction.api.reactor.ReactorType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 2/6/2016.
 */
public class SlotSolidReactorFuel extends Slot {

    ReactorType.FuelType fuelType;
    public SlotSolidReactorFuel(IInventory inv, int i, int j, int k, ReactorType.FuelType fuelType) {
        super(inv, i, j, k);

        this.fuelType = fuelType;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {

        Item item = stack.getItem();

        return item instanceof ISolidReactorFuel && ((ISolidReactorFuel) item).getFuelType(stack) == this.fuelType;
    }
}
