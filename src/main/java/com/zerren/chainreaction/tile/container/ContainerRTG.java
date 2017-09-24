package com.zerren.chainreaction.tile.container;

import chainreaction.api.item.ISolidReactorFuel;
import chainreaction.api.reactor.ReactorType;
import com.zerren.chainreaction.tile.container.slot.SlotSolidReactorFuel;
import com.zerren.chainreaction.tile.reactor.TERTG;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 2/24/2015.
 */
public class ContainerRTG extends ContainerCR {

    private TERTG rtg;

    public ContainerRTG(InventoryPlayer inv, TERTG tile) {
        super(tile);
        this.rtg = tile;

        bindReactorInventory(tile);
        bindPlayerInventory(inv, 8, 50);
    }

    private void bindReactorInventory(TERTG te) {

        this.addSlotToContainer(new SlotSolidReactorFuel(te, 0, 80, 17, ReactorType.FuelType.RTG_FUEL));
    }

}
