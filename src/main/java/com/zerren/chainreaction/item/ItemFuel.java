package com.zerren.chainreaction.item;

import chainreaction.api.item.IRTGFuel;
import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.utility.TooltipHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
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
public class ItemFuel extends ItemCRBase implements ISolidReactorFuel, IRTGFuel {

    public ItemFuel(String name, String[] subtypes, int stacksize, String folder, CreativeTabs tab) {
        super(name, subtypes, stacksize, folder, tab);
    }

    @Override
    public float getTemperature(ItemStack stack) {
        if (isReactorFuel(stack)) {
            if (NBTHelper.hasTag(stack, Names.NBT.FUEL_TEMPERATURE)) {
                return NBTHelper.getFloat(stack, Names.NBT.FUEL_TEMPERATURE);
            }
            else {
                NBTHelper.setFloat(stack, Names.NBT.FUEL_TEMPERATURE, 300F);
                return NBTHelper.getFloat(stack, Names.NBT.FUEL_TEMPERATURE);
            }
        }
        return 295F;
    }

    @Override
    public void setTemperature(ItemStack stack, float temp) {
        if (isReactorFuel(stack))
            NBTHelper.setFloat(stack, Names.NBT.FUEL_TEMPERATURE, temp);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (isReactorFuel(stack)) {
            float temp = getTemperature(stack);
            float fuel = getFuelRemaining(stack);
            float rad = getRadioactivity(stack);

            switch (ConfigHandler.temperature) {
                case 0:
                    TooltipHelper.addTemperatureInfoKelvin(list, temp); break;
                case 1:
                    TooltipHelper.addTemperatureInfoCelcius(list, temp); break;
                default:
                    TooltipHelper.addTemperatureInfoFahrenheit(list, temp); break;
            }

            TooltipHelper.addFuelLevelInfo(list, fuel);
            TooltipHelper.addRadiationInfo(list, (int)rad);
        }
        if (isRTGFuel(stack)) {
            int power = getBasePowerOutput(stack);
            float fuel = getFuelRemaining(stack);

            TooltipHelper.addFuelLevelInfo(list, fuel);
            TooltipHelper.addRTGPowerInfo(list, power);
        }
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
    public void pulseFuel(ItemStack stack, int neutronPulse) {
        float temp = NBTHelper.getFloat(stack, Names.NBT.FUEL_TEMPERATURE);
        if (neutronPulse > 0)
            temp += neutronPulse;
        else
            temp--;
        NBTHelper.setFloat(stack, Names.NBT.FUEL_TEMPERATURE, temp);
    }

    @Override
    public int getBasePowerOutput(ItemStack stack) {
        if (isRTGFuel(stack)) {
            switch (stack.getItemDamage()) {
                case 1: return ConfigHandler.rtgPowerPu238;
                case 2: return ConfigHandler.rtgPowerPo210;
            }
        }
        return 0;
    }

    @Override
    public void setFuelRemaining(ItemStack stack, float fuel) {
        if (isReactorFuel(stack) || isRTGFuel(stack))
            NBTHelper.setFloat(stack, Names.NBT.FUEL_REMAINING, fuel);
    }

    @Override
    public float getFuelRemaining(ItemStack stack) {
        if (isReactorFuel(stack) || isRTGFuel(stack)) {
            if (NBTHelper.hasTag(stack, Names.NBT.FUEL_REMAINING)) {
                return NBTHelper.getFloat(stack, Names.NBT.FUEL_REMAINING);
            }
            else {
                NBTHelper.setFloat(stack, Names.NBT.FUEL_REMAINING, 1F);
                return NBTHelper.getFloat(stack, Names.NBT.FUEL_REMAINING);
            }
        }
        return 0F;
    }

    @Override
    public ReactorType.FuelType getFuelType(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case 0: return ReactorType.FuelType.FISSION_ROD;
            case 1:case 2: return ReactorType.FuelType.RTG_FUEL;
        }
        return null;
    }

    @Override
    public boolean emitsRadiation(ItemStack stack) {
        return isReactorFuel(stack);
    }

    @Override
    public float getRadioactivity(ItemStack stack) {
        if (isReactorFuel(stack)) {
            if (NBTHelper.hasTag(stack, Names.NBT.RADIOACTIVITY)) {
                return NBTHelper.getFloat(stack, Names.NBT.RADIOACTIVITY);
            }
            else {
                NBTHelper.setFloat(stack, Names.NBT.RADIOACTIVITY, 30F);
                return NBTHelper.getFloat(stack, Names.NBT.RADIOACTIVITY);
            }
        }
        return 0F;
    }

    @Override
    public void setRadioactivity(ItemStack stack, float radioactivity) {
        if (isReactorFuel(stack))
            NBTHelper.setFloat(stack, Names.NBT.RADIOACTIVITY, radioactivity);
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
    public boolean isRTGFuel(ItemStack stack) {
        return getFuelType(stack) != null && getFuelType(stack).equals(ReactorType.FuelType.RTG_FUEL);
    }

    @Override
    public boolean isReactorFuel(ItemStack stack) {
        return getFuelType(stack) != null && getFuelType(stack) != ReactorType.FuelType.RTG_FUEL;
    }
}
