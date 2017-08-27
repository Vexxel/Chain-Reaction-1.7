package com.zerren.chainreaction.item.baubles;

import baubles.api.BaubleType;
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

    public void onEquipped(ItemStack stack, EntityLivingBase entity) {
        super.onEquipped(stack, entity);

    }

}
