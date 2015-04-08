package com.zerren.zedeng.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zerren on 4/3/2015.
 */
public class WorkingFluid {

    private static List<WorkingFluid> workingFluid = new ArrayList<WorkingFluid>();

    public final Fluid input;
    public final Fluid output;
    public final int expansionFactor;

    private WorkingFluid(Fluid input, Fluid output, int expansionFactor){
        this.input = input;
        this.output = output;
        this.expansionFactor = expansionFactor;
    }

    private Fluid getInput(){
        return input;
    }

    @Nullable
    public static Fluid getOutput(Fluid input){
        for(WorkingFluid fluid : WorkingFluid.workingFluid) {
            if (input == fluid.getInput()) {
                return fluid.output;
            }
        }
        return null;
    }

    public static int getExpansionFactor(Fluid input) {
        for(WorkingFluid fluid : WorkingFluid.workingFluid) {
            if (input == fluid.getInput()) {
                return fluid.expansionFactor;
            }
        }
        return 0;
    }

    public static boolean validWorkingFluid(Fluid input) {
        return getOutput(input) != null;
    }


    public static void addWorkingFluid(Fluid input, Fluid output, int expansionFactor) {
        workingFluid.add(new WorkingFluid(input, output, expansionFactor));
    }
}
