package com.zerren.zedeng.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 2/23/2015.
 */
public interface IKey {

    public boolean hasCode(ItemStack stack);
}
