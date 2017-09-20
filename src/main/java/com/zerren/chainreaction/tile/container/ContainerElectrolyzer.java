package com.zerren.chainreaction.tile.container;

import com.zerren.chainreaction.tile.container.slot.SlotBloomery;
import com.zerren.chainreaction.tile.container.slot.SlotLiquidContainer;
import com.zerren.chainreaction.tile.container.slot.SlotMachineUpgrade;
import com.zerren.chainreaction.tile.mechanism.TEBloomery;
import com.zerren.chainreaction.tile.mechanism.TEElectrolyzer;
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
public class ContainerElectrolyzer extends ContainerCR {

    private TEElectrolyzer electrolyzer;

    private FluidTank input;
    private FluidTank output1;
    private FluidTank output2;

    public ContainerElectrolyzer(InventoryPlayer inv, TEElectrolyzer tile) {
        super();
        this.electrolyzer = tile;

        bindElectrolyzerInventory(tile);
        bindPlayerInventory(inv, 8, 95);

        input = tile.inputTank;
        output1 = tile.outputTank1;
        output2 = tile.outputTank2;
    }

    private void bindElectrolyzerInventory(TEElectrolyzer te) {

        int slotIndex = 0;

        //Fuel inputs
        this.addSlotToContainer(new SlotLiquidContainer(te, slotIndex++, 32, 19, true));
        this.addSlotToContainer(new SlotLiquidContainer(te, slotIndex++, 32, 61, false));
        this.addSlotToContainer(new SlotMachineUpgrade(te, slotIndex++, 152, 61));

    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return electrolyzer.isUseableByPlayer(entityPlayer);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < crafters.size(); i++) {
            electrolyzer.sendGUINetworkData(this, (ICrafting) crafters.get(i));
        }
    }

    @Override
    public void updateProgressBar(int id, int v) {
        electrolyzer.getGUINetworkData(id, v);
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafter) {
        super.addCraftingToCrafters(crafter);

        electrolyzer.sendGUINetworkData(this, crafter);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotPos) {

        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotPos);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            //Bloomery outputs to player inventory
            if (slotPos < 10) {
                //can't move
                if (!this.mergeItemStack(itemstack1, 10, this.inventorySlots.size(), true)) {
                    return null;
                }
                slot.onSlotChange(itemstack1, itemstack);
            }
            //from player inventory
            else if (slotPos >= 10){
                //fuel slots
                if (itemstack1.getItem() == Items.coal && itemstack1.getItemDamage() == 1) {
                    if (!this.mergeItemStack(itemstack1, 0, 4, false)) return null;
                }
                //ore slots
                if (CoreUtility.hasDictionaryMatch(itemstack1, "oreIron")) {
                    if (!this.mergeItemStack(itemstack1, 4, 8, false)) return null;
                }
            }

            else if (!this.mergeItemStack(itemstack1, 10, this.inventorySlots.size(), false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            }
            else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }

}
