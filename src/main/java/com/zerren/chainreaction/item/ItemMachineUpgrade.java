package com.zerren.chainreaction.item;

import chainreaction.api.item.IMachineUpgrade;
import chainreaction.api.item.IRadioactiveMaterial;
import com.zerren.chainreaction.utility.CoreUtility;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Zerren on 4/15/2015.
 */
public class ItemMachineUpgrade extends ItemCRBase implements IMachineUpgrade {

    public ItemMachineUpgrade(String name, String[] subtypes, String folder, CreativeTabs tab) {
        super(name, subtypes, folder, tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4) {
        String s1 = EnumChatFormatting.BLUE + CoreUtility.translate("gui.item.upgrade." + getUpgradeType(stack).name().toLowerCase() + ".name");

        String levelRoman = null;
        switch (getLevel(stack)) {
            case 1: levelRoman = "I"; break;
            case 2: levelRoman = "II"; break;
            case 3: levelRoman = "III"; break;
        }
        String s2 = EnumChatFormatting.BLUE + (getLevel(stack) > 0 ? levelRoman : "");

        list.add(s1 + " " + s2);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        int level = getLevel(stack);
        switch (level) {
            case 0: case 1:
                return EnumRarity.uncommon;
            case 2:
                return EnumRarity.rare;
            case 3:
                return EnumRarity.epic;
            default:
                return EnumRarity.common;
        }
    }

    @Override
    public int getLevel(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case 0: case 3: case 6: case 9:
                return 1;
            case 1: case 4: case 7: case 10:
                return 2;
            case 2: case 5: case 8: case 11:
                return 3;
        }
        return -1;
    }

    @Override
    public MachineUpgrade getUpgradeType(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case 0: case 1: case 2:
                return MachineUpgrade.CAPACITY;
            case 3: case 4: case 5:
                return MachineUpgrade.EFFICIENCY;
            case 6: case 7: case 8:
                return MachineUpgrade.OVERCLOCKER;
            case 9: case 10: case 11:
                return MachineUpgrade.RTG;
        }
        return MachineUpgrade.UNKNOWN;
    }

    @Override
    public boolean isUpgrade(ItemStack stack) {
        return getUpgradeType(stack) != MachineUpgrade.UNKNOWN;
    }
}
