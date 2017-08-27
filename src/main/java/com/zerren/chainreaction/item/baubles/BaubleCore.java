package com.zerren.chainreaction.item.baubles;

import baubles.api.BaubleType;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.utility.BaubleHelper;
import com.zerren.chainreaction.utility.ItemRetriever;
import com.zerren.chainreaction.utility.NBTHelper;
import com.zerren.chainreaction.utility.TooltipHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * Created by Zerren on 8/25/2017.
 */
public class BaubleCore {

    public static EnumRarity rarity;
    public static BaubleType type;
    public static String name;
    public static SetBonus setBonus;

    public BaubleCore() {
        rarity = EnumRarity.common;
        type = null;
        name = "null";
        setBonus = SetBonus.NONE;
    }

    public void tick(ItemStack stack, EntityLivingBase entity) {
        //cooldown
        if (NBTHelper.hasTag(stack, Names.NBT.BAUBLE_COOLDOWN) && NBTHelper.getShort(stack, Names.NBT.BAUBLE_COOLDOWN) > 0) {
            NBTHelper.setShort(stack, Names.NBT.BAUBLE_COOLDOWN, (short)(NBTHelper.getShort(stack, Names.NBT.BAUBLE_COOLDOWN) - 1));
            //System.out.println("cooldown: " + NBTHelper.getShort(stack, Names.NBT.BAUBLE_COOLDOWN));
        }
    }

    public boolean isRingEquipped(EntityPlayer player) {
        return BaubleHelper.hasCorrectRing(player, ItemRetriever.Items.bauble(getName()));
    }

    public boolean isAmuletEquipped(EntityPlayer player) {
        return BaubleHelper.hasCorrectAmulet(player, ItemRetriever.Items.bauble(getName()));
    }

    public boolean isBeltEquipped(EntityPlayer player) {
        return BaubleHelper.hasCorrectBelt(player, ItemRetriever.Items.bauble(getName()));
    }

    public String getName() {
        return name;
    }

    public void addTooltip(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        TooltipHelper.addBaubleInfo(list, name);

        if (isPartOfSet()) {
            TooltipHelper.addSetBonusInfo(stack, getSetBonus(), player, list, name);
        }
    }

    public boolean isPartOfSet() {
        return setBonus != SetBonus.NONE;
    }

    public SetBonus getSetBonus() {
        return setBonus;
    }

    public void onEquipped(ItemStack stack, EntityLivingBase entity) {

    }

    public void onUnequipped(ItemStack stack, EntityLivingBase entity) {

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
