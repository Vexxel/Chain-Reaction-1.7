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

    private byte type;
    /**
     *  A Slot that can only hold liquid containers.
     * @param type The type of liquid container. 0 for empty, 1 for filled, 2 for tanks
     */
    public SlotLiquidContainer(IInventory inv, int slotIndex, int displayX, int displayY, int type) {
        super(inv, slotIndex, displayX, displayY);

        if (type > 2 || type < 0 ) this.type = 0;

        this.type = (byte)type;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        switch (type) {
            case 1: return FluidContainerRegistry.isFilledContainer(stack);

            case 2: return FluidContainerRegistry.isContainer(stack);

            default: return FluidContainerRegistry.isEmptyContainer(stack);
        }
    }
}
