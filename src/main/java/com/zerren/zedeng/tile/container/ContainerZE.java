package com.zerren.zedeng.tile.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

/**
 * Created by Zerren on 2/28/2015.
 */
public class ContainerZE extends Container {

    public ContainerZE() {
        super();
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return false;
    }

    protected void bindPlayerInventory(InventoryPlayer playerInventory, int xStart, int yStart) {
        int slotplayer = 0;

        // Hotbar
        for(int x = 0; x < 9; x++) {
            addSlotToContainer(new Slot(playerInventory, slotplayer, xStart + x * 18, yStart + 58));
            slotplayer++;
        }
        // Inventory
        for(int j = 0; j < 3; j++)
            for(int k = 0; k < 9; k++) {
                addSlotToContainer(new Slot(playerInventory, slotplayer, xStart + k * 18, yStart + j * 18));
                slotplayer++;
            }
    }
}
