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

    public static boolean hasCorrectRing(EntityPlayer player, ItemStack bauble) {
        ItemStack ring1 = BaublesApi.getBaubles(player).getStackInSlot(1);
        ItemStack ring2 = BaublesApi.getBaubles(player).getStackInSlot(2);

        if (ring1 != null && ring1.getItem() == bauble.getItem() && ring1.getItemDamage() == bauble.getItemDamage() || ring2 != null && ring2.getItem() == bauble.getItem() && ring2.getItemDamage() == bauble.getItemDamage()) {
            return true;
        }
        return false;
    }

    public static boolean hasCorrectAmulet(EntityPlayer player, ItemStack bauble) {
        ItemStack amulet = BaublesApi.getBaubles(player).getStackInSlot(0);

        if (amulet.getItem() == bauble.getItem() && amulet.getItemDamage() == bauble.getItemDamage()) {
            return true;
        }
        return false;
    }

    public static boolean hasCorrectBelt(EntityPlayer player, ItemStack bauble) {
        ItemStack belt = BaublesApi.getBaubles(player).getStackInSlot(3);

        if (belt.getItem() == bauble.getItem() && belt.getItemDamage() == bauble.getItemDamage()) {
            return true;
        }
        return false;
    }

    public static boolean hasSetBonusEquipped(EntityPlayer player, SetBonus set) {
        boolean match1 = false;
        boolean match2 = false;

        /*for (int i = 0; i < 3; i++) {
            ItemStack currentBaubleForSlot = BaublesApi.getBaubles(player).getStackInSlot(i);
            if (currentBaubleForSlot.isItemEqual(set.getBauble1())) {
                match1 = true;
                System.out.println("Found first");
            }
            else if (currentBaubleForSlot.isItemEqual(set.getBauble2())) {
                match2 = true;
                System.out.println("Found second");
            }
        }*/
        return match1 && match2;
    }
}
