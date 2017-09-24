package com.zerren.chainreaction.tile.container;

import com.zerren.chainreaction.tile.vault.TEVaultController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 2/24/2015.
 */
public class ContainerVault extends ContainerCR {

    private TEVaultController controller;

    private int page, selection;

    public ContainerVault(InventoryPlayer inv, TEVaultController tile, int p) {
        super(tile);
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
}
