package com.zerren.zedeng.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zerren on 4/3/2015.
 */
public class WorkingFluid {

    public static List<WorkingFluid> workingFluid = new ArrayList<WorkingFluid>();

    public final Fluid input;
    public final Fluid output;

    public WorkingFluid(Fluid input, Fluid output){
        this.input = input;
        this.output = output;
    }

    private Fluid getInput(){
        return input;
    }

    public static Fluid getOutput(Fluid input){
        for(WorkingFluid fluid : WorkingFluid.workingFluid) {
            if (input == fluid.getInput()) {
                return fluid.output;
            }
        }
        return null;
    }

    public static boolean validWorkingFluid(Fluid input) {
        for (WorkingFluid fluid : WorkingFluid.workingFluid) {
            if (input == fluid.getInput()) {
                return true;
            }
        }
        return false;
    }


    public static void addWorkingFluid(Fluid input, Fluid output) {
        workingFluid.add(new WorkingFluid(input, output));
    }
}
