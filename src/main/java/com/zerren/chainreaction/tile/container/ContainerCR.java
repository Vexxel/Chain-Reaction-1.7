package com.zerren.chainreaction.tile.container;

import chainreaction.api.tile.IGuiTileData;
import com.zerren.chainreaction.tile.TileEntityCRBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 2/28/2015.
 */
public class ContainerCR extends Container {

    public final TileEntityCRBase tile;

    public ContainerCR(TileEntityCRBase tile) {
        super();
        this.tile = tile;
    }

    protected void bindPlayerInventory(InventoryPlayer inv, int xStart, int yStart) {
        int slotplayer = 0;

        // Hotbar
        for(int x = 0; x < 9; x++) {
            addSlotToContainer(new Slot(inv, slotplayer, xStart + x * 18, yStart + 58));
            slotplayer++;
        }
        // Inventory
        for(int j = 0; j < 3; j++)
            for(int k = 0; k < 9; k++) {
                addSlotToContainer(new Slot(inv, slotplayer, xStart + k * 18, yStart + j * 18));
                slotplayer++;
            }
    }

    protected void bindPlayerInventory(InventoryPlayer inv) {
        bindPlayerInventory(inv, 8, 84);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {

        if (!supportsShiftClick(player, slotIndex)) {
            return null;
        }

        ItemStack stack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            stack = stackInSlot.copy();

            if (!performMerge(player, slotIndex, stackInSlot)) {
                return null;
            }

            slot.onSlotChange(stackInSlot, stack);

            if (stackInSlot.stackSize <= 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.putStack(stackInSlot);
            }

            if (stackInSlot.stackSize == stack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(player, stackInSlot);
        }
        return stack;
    }

    protected boolean supportsShiftClick(EntityPlayer player, int slotIndex) {

        return supportsShiftClick(slotIndex);
    }

    protected boolean supportsShiftClick(int slotIndex) {

        return true;
    }

    protected boolean performMerge(EntityPlayer player, int slotIndex, ItemStack stack) {

        return performMerge(slotIndex, stack);
    }

    protected boolean performMerge(int slotIndex, ItemStack stack) {

        int invBase = getSizeInventory();
        int invFull = inventorySlots.size();

        if (slotIndex < invBase) {
            return mergeItemStack(stack, invBase, invFull, true);
        }
        return mergeItemStack(stack, 0, invBase, false);
    }

    protected int getSizeInventory() {
        if (tile instanceof IInventory)
            return ((IInventory) tile).getSizeInventory();
        return 0;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        if (tile instanceof IInventory)
            return ((IInventory)tile).isUseableByPlayer(entityPlayer);
        else return false;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < crafters.size(); i++) {
            if (tile instanceof IGuiTileData)
                ((IGuiTileData)tile).sendGUINetworkData(this, (ICrafting) crafters.get(i));
        }
    }

    @Override
    public void updateProgressBar(int id, int v) {
        if (tile instanceof IGuiTileData)
            ((IGuiTileData)tile).getGUINetworkData(id, v);
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafter) {
        super.addCraftingToCrafters(crafter);

        if (tile instanceof IGuiTileData)
            ((IGuiTileData)tile).sendGUINetworkData(this, crafter);
    }
}
