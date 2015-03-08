package com.zerren.zedeng.inventory;

import com.zerren.zedeng.block.tile.vault.TEVaultController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 2/24/2015.
 */
public class ContainerVault extends ContainerZE {

    private TEVaultController controller;

    private int page, selection;

    public ContainerVault(InventoryPlayer inv, TEVaultController tile, int p) {
        super();
        this.controller = tile;

        this.page = p;
        this.selection = p;

        bindVaultInventory(tile);
        bindPlayerInventory(inv, 8, 140);
    }

    private void bindVaultInventory(TEVaultController te) {

        int index = page * 54;

        for (int j = 0; j < 6; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlotToContainer(new Slot(te, index, 8 + k * 18, 18 + j * 18));
                index++;
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return controller.isUseableByPlayer(entityPlayer);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (page != controller.page) {
                icrafting.sendProgressBarUpdate(this, 0, controller.page);
            }
            if (selection != controller.selection) {
                icrafting.sendProgressBarUpdate(this, 1, controller.selection);
            }
        }

        this.page = controller.page;
        this.selection = controller.selection;
    }

    @Override
    public void updateProgressBar(int par1, int par2) {
        if (par1 == 0) {
            controller.page = par2;
        }
        if (par1 == 1) {
            controller.selection = par2;
        }
    }

        @Override
    public void addCraftingToCrafters(ICrafting crafter) {
        super.addCraftingToCrafters(crafter);
        crafter.sendProgressBarUpdate(this, 0, this.controller.page);
        crafter.sendProgressBarUpdate(this, 1, this.controller.selection);
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int slotPos) {

        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotPos);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotPos < 54)
            {
                if (!this.mergeItemStack(itemstack1, 54, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, 54, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
}
