package com.zerren.chainreaction.tile.container.slot;

import chainreaction.api.item.IMachineUpgrade;
import chainreaction.api.item.ISolidReactorFuel;
import chainreaction.api.reactor.ReactorType;
import chainreaction.api.recipe.RTGFuels;
import chainreaction.api.tile.IUpgradeableTile;
import com.zerren.chainreaction.tile.TileEntityCRBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

/**
 * Created by Zerren on 2/6/2016.
 */
public class SlotMachineUpgrade extends Slot {

    private TileEntityCRBase tile;
    private final IMachineUpgrade.MachineUpgrade[] validUpgrades;

    public SlotMachineUpgrade(IInventory inv, int i, int j, int k) {
        super(inv, i, j, k);
        tile = (TileEntityCRBase)inv;

        if (tile instanceof IUpgradeableTile) {
            validUpgrades = ((IUpgradeableTile) tile).getValidUpgrades();
        }
        else validUpgrades = new IMachineUpgrade.MachineUpgrade[] { IMachineUpgrade.MachineUpgrade.UNKNOWN };
    }

    @Override
    public boolean isItemValid(ItemStack stack) {

        //The tile is upgradable and the stack is an upgrade item
        if (tile instanceof IUpgradeableTile && stack.getItem() instanceof IMachineUpgrade) {
            IMachineUpgrade item = (IMachineUpgrade)stack.getItem();

            for (IMachineUpgrade.MachineUpgrade upgrade : validUpgrades) {
                if (item.getUpgradeType(stack) == upgrade) return true;
            }
        }
        return false;
    }
}
