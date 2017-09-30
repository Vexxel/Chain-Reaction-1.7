package com.zerren.chainreaction.tile.container;

import com.zerren.chainreaction.tile.container.slot.SlotLiquidContainer;
import com.zerren.chainreaction.tile.container.slot.SlotMachineUpgrade;
import com.zerren.chainreaction.tile.mechanism.TELiquifier;
import com.zerren.chainreaction.utility.CoreUtility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidTank;

/**
 * Created by Zerren on 5/20/2016.
 */
public class ContainerLiquifier extends ContainerCR {

    public ContainerLiquifier(InventoryPlayer inv, TELiquifier tile) {
        super(tile);

        bindTileInventory(tile);
        bindPlayerInventory(inv, 8, 95);
    }

    private void bindTileInventory(TELiquifier te) {

        int slotIndex = 0;

        this.addSlotToContainer(new SlotLiquidContainer(te, slotIndex++, 130, 19, 2));

        this.addSlotToContainer(new SlotMachineUpgrade(te, slotIndex++, 176, 6));
        this.addSlotToContainer(new SlotMachineUpgrade(te, slotIndex++, 176, 24));
        this.addSlotToContainer(new SlotMachineUpgrade(te, slotIndex++, 176, 42));

    }
}
