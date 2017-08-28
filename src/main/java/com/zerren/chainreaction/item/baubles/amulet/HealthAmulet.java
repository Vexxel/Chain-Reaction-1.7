package com.zerren.chainreaction.item.baubles.amulet;

import baubles.api.BaubleType;
import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.item.baubles.BaubleCore;
import com.zerren.chainreaction.utility.CoreUtility;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

/**
 * Created by Zerren on 8/24/2017.
 */
public class HealthAmulet extends BaubleCore {

    public HealthAmulet() {
        rarity = EnumRarity.uncommon;
        type = BaubleType.AMULET;
        name = "healthAmulet";
        extraTooltipValue = " +" + ConfigHandler.healthAmulet;
    }

    public void tick(ItemStack stack, EntityLivingBase entity) {
        super.tick(stack, entity);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            if (player.getMaxHealth() <= 20)
                player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(player.getMaxHealth() + (ConfigHandler.healthAmulet * 2));
        }
    }

    public void onEquipped(ItemStack stack, EntityLivingBase entity) {
        super.onEquipped(stack, entity);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20 + (ConfigHandler.healthAmulet * 2));
        }
    }

    public void onUnequipped(ItemStack stack, EntityLivingBase entity) {
        super.onUnequipped(stack, entity);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20);
        }
    }
}
