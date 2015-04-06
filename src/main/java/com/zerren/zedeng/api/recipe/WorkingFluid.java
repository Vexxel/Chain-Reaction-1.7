package com.zerren.zedeng.api.recipe;

import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zerren on 4/2/2015.
 */
public class WorkingFluid {

    public static List<WorkingFluid> workingFluid = new ArrayList<WorkingFluid>();

    public final FluidStack input;
    public final FluidStack output;

    public WorkingFluid(FluidStack input, FluidStack output) {
        this.input = input;
        this.output = output;
    }

    public FluidStack getInput(){
        return input;
    }

    public FluidStack getOutput(FluidStack input){
        for(WorkingFluid fluid : WorkingFluid.workingFluid) {
            if (input.getFluid() == fluid.getInput().getFluid()) {
                return fluid.output;
            }
        }
        return null;
    }

    /**
     * Adds a fluid to the heat exchanger's registry to be heated. Example is water-steam 1->160
     * @param input
     * @param output
     */
    public static void addWorkingFluid(FluidStack input, FluidStack output) {
        workingFluid.add(new WorkingFluid(input, output));
    }
}
