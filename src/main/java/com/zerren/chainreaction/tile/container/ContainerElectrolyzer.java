package com.zerren.chainreaction.tile.container;

import com.zerren.chainreaction.tile.container.slot.SlotLiquidContainer;
import com.zerren.chainreaction.tile.container.slot.SlotMachineUpgrade;
import com.zerren.chainreaction.tile.mechanism.TEElectrolyzer;
import com.zerren.chainreaction.utility.CoreUtility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fluids.FluidTank;

/**
 * Created by Zerren on 5/20/2016.
 */
public class ContainerElectrolyzer extends ContainerCR {

    public ContainerElectrolyzer(InventoryPlayer inv, TEElectrolyzer tile) {
        super(tile);

        bindTileInventory(tile);
        bindPlayerInventory(inv, 8, 95);
    }

    private void bindTileInventory(TEElectrolyzer te) {

        int slotIndex = 0;

        this.addSlotToContainer(new SlotLiquidContainer(te, slotIndex++, 32, 19, 1));
        this.addSlotToContainer(new SlotLiquidContainer(te, slotIndex++, 32, 61, 0));

        this.addSlotToContainer(new SlotMachineUpgrade(te, slotIndex++, 176, 12));
        this.addSlotToContainer(new SlotMachineUpgrade(te, slotIndex++, 176, 30));
        this.addSlotToContainer(new SlotMachineUpgrade(te, slotIndex++, 176, 48));

    }
}
