package com.zerren.chainreaction.item.baubles.amulet;

import baubles.api.BaubleType;
import com.zerren.chainreaction.core.PlayerSetBonus;
import com.zerren.chainreaction.item.baubles.BaubleCore;
import com.zerren.chainreaction.item.baubles.SetBonus;
import com.zerren.chainreaction.utility.BaubleHelper;
import com.zerren.chainreaction.utility.ItemRetriever;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.potion.Potion;

/**
 * Created by Zerren on 8/24/2017.
 */
public class WitherAmulet extends BaubleCore {


    public WitherAmulet() {
        rarity = EnumRarity.uncommon;
        type = BaubleType.AMULET;
        name = "witherAmulet";
        setBonus = SetBonus.SKULLFIRE;
    }

    public void tick(ItemStack stack, EntityLivingBase entity) {
        super.tick(stack, entity);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;

            player.removePotionEffect(20);
        }
    }
}
