package com.zerren.chainreaction.item.baubles.belt;

import baubles.api.BaubleType;
import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.item.baubles.BaubleCore;
import com.zerren.chainreaction.reference.Names;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

import java.util.UUID;

/**
 * Created by Zerren on 8/24/2017.
 */
public class SpeedBelt extends BaubleCore {

    private static final AttributeModifier speedBeltBonus = (new AttributeModifier(Names.UUIDs.SPEED_BELT_BONUS_UUID, Names.UUIDs.SPEED_BELT_BONUS_NAME, ConfigHandler.speedModifier, 2)).setSaved(false);

    public SpeedBelt() {
        rarity = EnumRarity.uncommon;
        type = BaubleType.BELT;
        name = "speedBelt";
        extraTooltipValue = " +" + Math.round(ConfigHandler.speedModifier * 100) + "%";

    }

    public void tick(ItemStack stack, EntityLivingBase entity) {
        super.tick(stack, entity);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;

            if (player.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getModifier(Names.UUIDs.SPEED_BELT_BONUS_UUID) == null) {
                setSpeed(player, true);
            }
        }
    }

    public void onEquipped(ItemStack stack, EntityLivingBase entity) {
        super.onEquipped(stack, entity);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            setSpeed(player, true);
        }
    }

    public void onUnequipped(ItemStack stack, EntityLivingBase entity) {
        super.onUnequipped(stack, entity);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            setSpeed(player, false);
        }
    }

    private void setSpeed(EntityPlayer player, boolean activate) {
        IAttributeInstance speed = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        if (activate) {
            speed.applyModifier(speedBeltBonus);
        }
        else {
            speed.removeModifier(speedBeltBonus);
        }
    }
}
