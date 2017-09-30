package com.zerren.chainreaction.tile.container.slot;

import chainreaction.api.item.MachineUpgrade;
import chainreaction.api.item.MachineUpgradeRegistry;
import chainreaction.api.tile.IUpgradeableTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 2/6/2016.
 */
public class SlotMachineUpgrade extends Slot {

    private IUpgradeableTile tile;
    private final MachineUpgrade[] validUpgrades;

    public SlotMachineUpgrade(IInventory inv, int i, int j, int k) {
        super(inv, i, j, k);
        tile = (IUpgradeableTile)inv;

        validUpgrades = tile.getValidUpgrades();
    }

    @Override
    public boolean isItemValid(ItemStack stack) {

        //The tile is upgradable and the stack is an upgrade item
        if (tile != null && MachineUpgradeRegistry.isValidUpgrade(stack) && !tile.areUpgradesActive()) {
            for (MachineUpgrade upgrade : validUpgrades) {
                if (MachineUpgradeRegistry.getUpgradeType(stack) == upgrade) return true;
            }
        }
        return false;
    }

    @Override
    public boolean canTakeStack(EntityPlayer player) {
        return !tile.areUpgradesActive();
    }
}
