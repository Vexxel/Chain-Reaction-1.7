package com.zerren.chainreaction.tile.container;

import chainreaction.api.item.ISolidReactorFuel;
import chainreaction.api.reactor.ReactorType;
import com.zerren.chainreaction.tile.container.slot.SlotBloomery;
import com.zerren.chainreaction.tile.container.slot.SlotSolidReactorFuel;
import com.zerren.chainreaction.tile.mechanism.TEBloomery;
import com.zerren.chainreaction.utility.CoreUtility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

/**
 * Created by Zerren on 5/20/2016.
 */
public class ContainerBloomery extends ContainerCR {

    private TEBloomery bloomery;

    public ContainerBloomery(InventoryPlayer inv, TEBloomery tile) {
        super();
        this.bloomery = tile;

        bindBloomeryInventory(tile);
        bindPlayerInventory(inv);
    }

    private void bindBloomeryInventory(TEBloomery te) {

        int slotIndex = 0;

        //Fuel inputs
        this.addSlotToContainer(new Slot(te, slotIndex++, 27, 17));
        this.addSlotToContainer(new Slot(te, slotIndex++, 45, 17));
        this.addSlotToContainer(new Slot(te, slotIndex++, 27, 35));
        this.addSlotToContainer(new Slot(te, slotIndex++, 45, 35));

        //Iron inputs
        for (int x = 0; x < 4; ++x) {
            this.addSlotToContainer(new Slot(te, slotIndex, 80 + x * 18, 17));
            slotIndex++;
        }

        //Bloom output
        this.addSlotToContainer(new SlotBloomery(te, slotIndex++, 80, 53));
        //Slag output
        this.addSlotToContainer(new SlotBloomery(te, slotIndex, 116, 53));

    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return bloomery.isUseableByPlayer(entityPlayer);
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
