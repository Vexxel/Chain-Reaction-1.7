package com.zerren.zedeng.api.recipe;

import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zerren on 4/3/2015.
 */
public class HeatingFluid {

    public static List<HeatingFluid> heatingFluid = new ArrayList<HeatingFluid>();

    public final FluidStack input;
    public final FluidStack output;
    public final float conductivity;

    public HeatingFluid(FluidStack input, FluidStack output, float conductivity){
        this.input = input;
        this.output = output;
        this.conductivity = conductivity;
    }

    public FluidStack getInput(){
        return input;
    }

    public FluidStack getOutput(FluidStack input){
        for(HeatingFluid fluid : HeatingFluid.heatingFluid) {
            if (input.getFluid() == fluid.getInput().getFluid()) {
                return fluid.output;
            }
        }
        return null;
    }

    public float getConductivity(FluidStack input) {
        for(HeatingFluid fluid : HeatingFluid.heatingFluid) {
            if (input.getFluid() == fluid.getInput().getFluid()) {
                return fluid.conductivity;
            }
        }
        return 1.0F;
    }

    public static void addHeatingFluid(FluidStack input, FluidStack output, float conductivity) {
        heatingFluid.add(new HeatingFluid(input, output, conductivity));
    }
}
