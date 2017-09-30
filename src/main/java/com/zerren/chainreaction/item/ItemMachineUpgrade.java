package com.zerren.chainreaction.item;

import chainreaction.api.item.MachineUpgrade;
import chainreaction.api.item.MachineUpgradeRegistry;
import com.zerren.chainreaction.utility.CoreUtility;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

/**
 * Created by Zerren on 4/15/2015.
 */
public class ItemMachineUpgrade extends ItemCRBase {

    public ItemMachineUpgrade(String name, String[] subtypes, String folder, CreativeTabs tab) {
        super(name, subtypes, folder, tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4) {
        MachineUpgrade type = MachineUpgradeRegistry.getUpgradeType(stack);
        if (type == MachineUpgrade.UNKNOWN) return;

        double value1 = MachineUpgradeRegistry.getValue1(stack);
        double value2 = MachineUpgradeRegistry.getValue2(stack);

        list.add(CoreUtility.translate("gui.item.upgrade.general.name"));

        switch (type) {
            case CAPACITY: {
                list.add(EnumChatFormatting.BLUE + CoreUtility.translate("gui.item.upgrade.capacity.name") + " " + EnumChatFormatting.GREEN + "+" + (int)value1);
                break;
            }
            case EFFICIENCY: {
                list.add(EnumChatFormatting.BLUE + CoreUtility.translate("gui.item.upgrade.cost.name") + " " + EnumChatFormatting.GREEN + (1D - value1) * 100 + "%");
                break;
            }
            case RTG: {
                list.add(EnumChatFormatting.BLUE + CoreUtility.translate("gui.item.upgrade.generation.name") + " " + EnumChatFormatting.GREEN + "+" + (int)value1 + " RF/t");
                break;
            }
            case OVERCLOCKER: {
                list.add(EnumChatFormatting.BLUE + CoreUtility.translate("gui.item.upgrade.speed.name") + " " + EnumChatFormatting.GREEN + "+" + value1 * 100 + "%");
                list.add(EnumChatFormatting.BLUE + CoreUtility.translate("gui.item.upgrade.cost.name") + " " + EnumChatFormatting.RED + "+" + value2 * 100 + "%");
                break;
            }
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case 0: case 3: case 6: case 9: {
                return EnumRarity.uncommon;
            }
            case 1: case 4: case 7: case 10: {
                return EnumRarity.rare;
            }
            case 2: case 5: case 8: case 11: {
                return EnumRarity.epic;
            }
            default:
                return EnumRarity.common;
        }
    }
}
