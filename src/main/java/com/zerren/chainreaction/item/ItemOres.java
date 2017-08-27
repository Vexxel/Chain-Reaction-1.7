package com.zerren.chainreaction.item;

import chainreaction.api.item.CRItems;
import chainreaction.api.item.IRadioactiveMaterial;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.utility.NBTHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Zerren on 5/22/2016.
 */
public class ItemOres extends ItemCRBase {

    public static final int BLOOM_MAX = 64;
    public ItemOres(String name, String[] subtypes, String folder, CreativeTabs tab) {
        super(name, subtypes, folder, tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (stack.getItemDamage() == 0)
            list.add(StatCollector.translateToLocalFormatted("Size: " + getBloomSize(stack)));
    }

    public static int getBloomSize(ItemStack stack) {
        if (isBloom(stack)) {
            return NBTHelper.getInt(stack, Names.NBT.BLOOM);
        }

        return -1;
    }

    public static boolean isBloom(ItemStack stack) {
        return stack != null && stack.getItem() == CRItems.ores && stack.getItemDamage() == 0;
    }

    public static boolean setBloomSize(ItemStack stack, int size) {
        if (isBloom(stack)) {
            //bloom + increase fits
            if (size <= BLOOM_MAX) {
                NBTHelper.setInt(stack, Names.NBT.BLOOM, size);
                return true;
            }
        }
        return false;
    }
}
