package com.zerren.chainreaction.item;

import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.utility.TooltipHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import chainreaction.api.item.IRadioactiveMaterial;
import chainreaction.api.item.ISolidReactorFuel;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.utility.NBTHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import chainreaction.api.reactor.ReactorType;

import java.util.List;

/**
 * Created by Zerren on 4/15/2015.
 */
public class ItemFuelParts extends ItemCRBase implements IRadioactiveMaterial {

    public ItemFuelParts(String name, String[] subtypes, String folder, CreativeTabs tab) {
        super(name, subtypes, folder, tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4) {

    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
        if (!world.isRemote) {
            if (emitsRadiation(stack) && world.getTotalWorldTime() % 100 == 0) {
                pulseMaterial(stack);
            }
        }
    }

    @Override
    public boolean emitsRadiation(ItemStack stack) {
        //return isReactorFuel(stack);
        return false;
    }

    @Override
    public float getRadioactivity(ItemStack stack) {
        /*if (isReactorFuel(stack)) {
            if (NBTHelper.hasTag(stack, Names.NBT.RADIOACTIVITY)) {
                return NBTHelper.getFloat(stack, Names.NBT.RADIOACTIVITY);
            }
            else {
                NBTHelper.setFloat(stack, Names.NBT.RADIOACTIVITY, 30F);
                return NBTHelper.getFloat(stack, Names.NBT.RADIOACTIVITY);
            }
        }*/
        return 0F;
    }

    @Override
    public void setRadioactivity(ItemStack stack, float radioactivity) {
        /*if (isReactorFuel(stack))
            NBTHelper.setFloat(stack, Names.NBT.RADIOACTIVITY, radioactivity);*/
    }

    @Override
    public double getEmissionDistance(ItemStack stack) {
        if (emitsRadiation(stack)) return Math.sqrt(getRadioactivity(stack));
        return 0D;
    }

    @Override
    public void pulseMaterial(ItemStack stack) {
        if (getRadioactivity(stack) > 0)
            setRadioactivity(stack, getRadioactivity(stack) * 0.9994F);
    }

    @Override
    public float getTemperature(ItemStack stack) {
        return 0;
    }

    @Override
    public void setTemperature(ItemStack stack, float temp) {

    }
}
