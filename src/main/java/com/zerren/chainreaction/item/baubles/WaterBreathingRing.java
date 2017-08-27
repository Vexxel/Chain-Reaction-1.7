package com.zerren.chainreaction.item.baubles;

import baubles.api.BaubleType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 8/24/2017.
 */
public class WaterBreathingRing extends BaubleCore {


    public WaterBreathingRing() {
        rarity = EnumRarity.uncommon;
        type = BaubleType.RING;
        name = "waterBreathingRing";
    }

    public void tick(ItemStack stack, EntityLivingBase entity) {
        super.tick(stack, entity);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;

            if (player.isInsideOfMaterial(Material.water))
                player.setAir(280);
        }
    }

}
