package com.zerren.zedeng.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zerren on 4/3/2015.
 */
public class HeatingFluid {

    public static List<HeatingFluid> heatingFluid = new ArrayList<HeatingFluid>();

    public final Fluid input;
    public final Fluid output;
    public final float conductivity;

    public HeatingFluid(Fluid input, Fluid output, float conductivity){
        this.input = input;
        this.output = output;
        this.conductivity = conductivity;
    }

    private Fluid getInput(){
        return input;
    }

    public static Fluid getOutput(Fluid input){
        for(HeatingFluid fluid : HeatingFluid.heatingFluid) {
            if (input == fluid.getInput()) {
                return fluid.output;
            }
        }
        return null;
    }

    public static float getConductivity(FluidStack input) {
        for(HeatingFluid fluid : HeatingFluid.heatingFluid) {
            if (input.getFluid() == fluid.getInput()) {
                return fluid.conductivity;
            }
        }
        return 1.0F;
    }

    public static boolean validCoolant(Fluid input) {
        for (HeatingFluid fluid : HeatingFluid.heatingFluid) {
            if (input == fluid.getInput()) {
                return true;
            }
        }
        return false;
    }

    public static void addHeatingFluid(Fluid input, Fluid output, float conductivity) {
        heatingFluid.add(new HeatingFluid(input, output, conductivity));
    }

    public static void addHeatingFluid(Fluid input, Fluid output) {
        heatingFluid.add(new HeatingFluid(input, output, 1.0F));
    }
}
