package com.zerren.zedeng.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 2/23/2015.
 */
public interface IKey {

    boolean hasCode(ItemStack stack);

    String getCode(ItemStack stack);
}
