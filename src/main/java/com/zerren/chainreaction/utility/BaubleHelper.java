package com.zerren.chainreaction.utility;

import baubles.api.BaublesApi;
import com.zerren.chainreaction.item.baubles.SetBonus;
import com.zerren.chainreaction.item.tool.ItemBaubleCR;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 8/24/2017.
 */
public class BaubleHelper {

    public static boolean hasCorrectAmulet(EntityPlayer player, ItemStack bauble) {
        ItemStack amulet = getAmulet(player);

        return amulet != null && amulet.isItemEqual(bauble);
    }

    public static boolean hasCorrectRing(EntityPlayer player, ItemStack bauble) {
        ItemStack[] rings = getRings(player);

        return rings[0] != null && rings[0].isItemEqual(bauble) || rings[1] != null && rings[1].isItemEqual(bauble);
    }

    public static boolean hasCorrectBelt(EntityPlayer player, ItemStack bauble) {
        ItemStack belt = getBelt(player);

        return belt != null && belt.isItemEqual(bauble);
    }

    public static boolean hasCorrectBauble(EntityPlayer player, ItemStack bauble, int index) {
        switch (index) {
            case 0: {
                return hasCorrectAmulet(player, bauble);
            }
            case 1: {
                return hasCorrectRing(player, bauble);
            }
            case 2: {
                return hasCorrectBelt(player, bauble);
            }
        }
        return false;
    }

    public static ItemStack getAmulet(EntityPlayer player) {
        return BaublesApi.getBaubles(player).getStackInSlot(0);
    }
    public static ItemStack[] getRings(EntityPlayer player) {
        return new ItemStack[] {BaublesApi.getBaubles(player).getStackInSlot(1), BaublesApi.getBaubles(player).getStackInSlot(2)};
    }
    public static ItemStack getBelt(EntityPlayer player) {
        return BaublesApi.getBaubles(player).getStackInSlot(3);
    }

    public static ItemStack[] getBaubles(EntityPlayer player) {
        return new ItemStack[] {BaublesApi.getBaubles(player).getStackInSlot(0), BaublesApi.getBaubles(player).getStackInSlot(1), BaublesApi.getBaubles(player).getStackInSlot(2), BaublesApi.getBaubles(player).getStackInSlot(3)};
    }
}
