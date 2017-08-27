package com.zerren.chainreaction.item.baubles;

import baubles.api.BaubleType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Zerren on 8/24/2017.
 */
public class FallDamageAmulet extends BaubleCore {

    public FallDamageAmulet() {
        rarity = EnumRarity.uncommon;
        type = BaubleType.AMULET;
        name = "fallDamageAmulet";
    }

    public void tick(ItemStack stack, EntityLivingBase entity) {
        super.tick(stack, entity);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            //not creative mode, not on ground
            if (!player.capabilities.isCreativeMode && !player.onGround) {
                player.fallDistance = 0;
            }
        }
    }
}
