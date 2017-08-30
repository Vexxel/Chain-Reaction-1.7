package com.zerren.chainreaction.item.baubles.belt;

import baubles.api.BaubleType;
import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.item.baubles.BaubleCore;
import com.zerren.chainreaction.item.baubles.SetBonus;
import com.zerren.chainreaction.reference.Names;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 8/24/2017.
 */
public class KnockbackBelt extends BaubleCore {

    private static final AttributeModifier knockbackBeltBonus = (new AttributeModifier(Names.UUIDs.KNOCKBACK_BELT_BONUS_UUID, Names.UUIDs.KNOCKBACK_BELT_BONUS_NAME, ConfigHandler.knockbackResistChance, 0));

    public KnockbackBelt() {
        rarity = EnumRarity.uncommon;
        type = BaubleType.BELT;
        name = "knockbackBelt";
        setBonus = SetBonus.GUARDIAN;
        extraTooltipValue = " +" + Math.round(ConfigHandler.knockbackResistChance * 100) + "%";

    }

    public void onEquipped(ItemStack stack, EntityLivingBase entity) {
        super.onEquipped(stack, entity);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            setKnockbackResistance(player, true);
        }
    }

    public void onUnequipped(ItemStack stack, EntityLivingBase entity) {
        super.onUnequipped(stack, entity);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            setKnockbackResistance(player, false);
        }
    }

    private void setKnockbackResistance(EntityPlayer player, boolean activate) {
        IAttributeInstance knockback = player.getEntityAttribute(SharedMonsterAttributes.knockbackResistance);

        if (activate) {
            knockback.applyModifier(knockbackBeltBonus);
        }
        else {
            knockback.removeModifier(knockbackBeltBonus);
        }
    }
}
