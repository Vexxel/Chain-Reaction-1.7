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
        super(tile);
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
}
