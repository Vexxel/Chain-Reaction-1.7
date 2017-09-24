package com.zerren.chainreaction.tile.container;

import chainreaction.api.item.ISolidReactorFuel;
import chainreaction.api.reactor.ReactorType;
import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.tile.container.slot.SlotSolidReactorFuel;
import com.zerren.chainreaction.tile.reactor.TEPressurizedWaterReactor;
import com.zerren.chainreaction.tile.vault.TEVaultController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 2/24/2015.
 */
public class ContainerPWR extends ContainerCR {

    private TEPressurizedWaterReactor reactor;

    public ContainerPWR(InventoryPlayer inv, TEPressurizedWaterReactor tile) {
        super(tile);
        this.reactor = tile;

        bindReactorInventory(tile);
        bindPlayerInventory(inv, 8, 120);
    }

    private void bindReactorInventory(TEPressurizedWaterReactor te) {

        int slotIndex = 0;
        int yStart = 17;

        for (int x = 0; x < 3; ++x) {
            this.addSlotToContainer(new SlotSolidReactorFuel(te, slotIndex, 62 + x * 18, yStart, ReactorType.FuelType.FISSION_ROD));
            slotIndex++;
        }
        for (int x = 0; x < 4; ++x) {
            this.addSlotToContainer(new SlotSolidReactorFuel(te, slotIndex, 53 + x * 18, yStart + 18, ReactorType.FuelType.FISSION_ROD));
            slotIndex++;
        }
        for (int x = 0; x < 5; ++x) {
            this.addSlotToContainer(new SlotSolidReactorFuel(te, slotIndex, 44 + x * 18, yStart + 18 * 2, ReactorType.FuelType.FISSION_ROD));
            slotIndex++;
        }
        for (int x = 0; x < 4; ++x) {
            this.addSlotToContainer(new SlotSolidReactorFuel(te, slotIndex, 53 + x * 18, yStart + 18 * 3, ReactorType.FuelType.FISSION_ROD));
            slotIndex++;
        }
        for (int x = 0; x < 3; ++x) {
            this.addSlotToContainer(new SlotSolidReactorFuel(te, slotIndex, 62 + x * 18, yStart + 18 * 4, ReactorType.FuelType.FISSION_ROD));
            slotIndex++;
        }
    }
}
