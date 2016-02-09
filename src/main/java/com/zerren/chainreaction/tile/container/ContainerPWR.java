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
        super();
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

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return reactor.isUseableByPlayer(entityPlayer);
    }

    /*@Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (page != reactor.page) {
                icrafting.sendProgressBarUpdate(this, 0, reactor.page);
            }
            if (selection != reactor.selection) {
                icrafting.sendProgressBarUpdate(this, 1, reactor.selection);
            }
        }

        this.page = reactor.page;
        this.selection = reactor.selection;
    }*/

    /*@Override
    public void updateProgressBar(int par1, int par2) {
        if (par1 == 0) {
            reactor.page = par2;
        }
        if (par1 == 1) {
            reactor.selection = par2;
        }
    }*/

    /*@Override
    public void addCraftingToCrafters(ICrafting crafter) {
        super.addCraftingToCrafters(crafter);
        crafter.sendProgressBarUpdate(this, 0, this.reactor.page);
        crafter.sendProgressBarUpdate(this, 1, this.reactor.selection);
    }*/

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotPos) {

        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotPos);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            //PWR to player inventory
            if (slotPos < 19) {
                //can't move
                if (!this.mergeItemStack(itemstack1, 19, this.inventorySlots.size(), true))
                    return null;
            }
            //from player inventory
            else {
                //correct item
                if (itemstack1.getItem() instanceof ISolidReactorFuel && ((ISolidReactorFuel) itemstack1.getItem()).getFuelType(itemstack1) == ReactorType.FuelType.FISSION_ROD) {
                    if (!this.mergeItemStack(itemstack1, 0, 19, false)) return null;
                }
                //not correct item
                else return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            }
            else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
}
