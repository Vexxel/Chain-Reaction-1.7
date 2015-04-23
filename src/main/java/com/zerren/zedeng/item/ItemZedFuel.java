package com.zerren.zedeng.item;

import zedeng.api.item.IReactorFuel;
import com.zerren.zedeng.reference.Names;
import com.zerren.zedeng.utility.NBTHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 4/15/2015.
 */
public class ItemZedFuel extends ItemZE implements IReactorFuel {
    public ItemZedFuel(String name, String[] subtypes, String folder, CreativeTabs tab) {
        super(name, subtypes, folder, tab);
    }

    @Override
    public float getTemperature(ItemStack stack) {
        if (isReactorFuel(stack) && NBTHelper.hasTag(stack, Names.NBT.FUEL_TEMPERATURE)) {
            return NBTHelper.getFloat(stack, Names.NBT.FUEL_TEMPERATURE);
        }
        return 295.0F;
    }

    @Override
    public void setTemperature(ItemStack stack, float temp) {
        if (isReactorFuel(stack))
            NBTHelper.setFloat(stack, Names.NBT.FUEL_TEMPERATURE, temp);
    }

    @Override
    public boolean isReactorFuel(ItemStack stack) {
        switch(stack.getItemDamage()) {
            case 2: return true;
        }
        return false;
    }

    @Override
    public void pulseFuel(ItemStack stack, int neutronPulse) {
        float temp = NBTHelper.getFloat(stack, Names.NBT.FUEL_TEMPERATURE);
        if (neutronPulse > 0)
            temp += neutronPulse;
        else
            temp--;
        NBTHelper.setFloat(stack, Names.NBT.FUEL_TEMPERATURE, temp);
    }
}
