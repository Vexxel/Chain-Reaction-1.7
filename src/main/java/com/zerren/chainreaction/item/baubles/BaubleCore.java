package com.zerren.chainreaction.item.baubles;

import baubles.api.BaubleType;
import com.zerren.chainreaction.core.PlayerSetBonus;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.utility.BaubleHelper;
import com.zerren.chainreaction.utility.NBTHelper;
import com.zerren.chainreaction.utility.TooltipHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Zerren on 8/25/2017.
 */
public class BaubleCore {

    public EnumRarity rarity;
    public BaubleType type;
    protected String name;
    public SetBonus setBonus;
    protected String extraTooltipValue;

    public BaubleCore() {
        rarity = EnumRarity.common;
        type = null;
        name = "null";
        setBonus = null;
        extraTooltipValue = null;
    }

    public void tick(ItemStack stack, EntityLivingBase entity) {
        //cooldown
        if (NBTHelper.hasTag(stack, Names.NBT.BAUBLE_COOLDOWN) && NBTHelper.getShort(stack, Names.NBT.BAUBLE_COOLDOWN) > 0) {
            NBTHelper.setShort(stack, Names.NBT.BAUBLE_COOLDOWN, (short)(NBTHelper.getShort(stack, Names.NBT.BAUBLE_COOLDOWN) - 1));
        }
    }


    public void addTooltip(EntityPlayer player, List list, boolean par4) {
        TooltipHelper.addBaubleInfo(list, name, extraTooltipValue);

        if (isPartOfSet()) {
            TooltipHelper.addSetBonusInfo(getSetBonus(), player, list);
        }
    }

    public boolean isPartOfSet() {
        return setBonus != null;
    }

    public SetBonus getSetBonus() {
        return setBonus;
    }

    public void onEquipped(ItemStack stack, EntityLivingBase entity) {
        if (isPartOfSet()) {
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)entity;

                if (BaubleHelper.hasCorrectBauble(player, getSetBonus().getOtherSetPiece(name), getSetBonus().getOtherSetPieceSlot(name))) {
                    PlayerSetBonus bonus = PlayerSetBonus.get(player);
                    bonus.toggleSetStatus(getSetBonus(), true);

                }
            }
        }
    }

    public void onUnequipped(ItemStack stack, EntityLivingBase entity) {
        if (isPartOfSet()) {
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)entity;

                PlayerSetBonus bonus = PlayerSetBonus.get(player);
                if (bonus.getSetStatus(getSetBonus())) {
                    bonus.toggleSetStatus(getSetBonus(), false);
                }
            }
        }
    }

    public boolean canEquip(ItemStack stack, EntityLivingBase entity) {
        return true;
    }

    public boolean canUnequip(ItemStack stack, EntityLivingBase entity) {
        return true;
    }

    public EnumRarity getRarity() {
        return rarity;
    }

    public BaubleType getType() {
        return type;
    }
}
