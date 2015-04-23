package zedeng.api.recipe;

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

    public final FluidStack input;
    public final FluidStack output;

    private WorkingFluid(FluidStack input, FluidStack output){
        this.input = input;
        this.output = output;
    }

    private Fluid getInput(){
        return input.getFluid();
    }

    @Nullable
    public static FluidStack getOutput(Fluid input){
        for(WorkingFluid fluid : WorkingFluid.workingFluid) {
            if (input == fluid.getInput()) {
                return fluid.output;
            }
        }
        return null;
    }

    public static int getInputRequiredAmount(Fluid input) {
        for (WorkingFluid fluid : WorkingFluid.workingFluid) {
            if (input == fluid.getInput())
                return fluid.input.amount;
        }
        return 0;
    }

    public static boolean validWorkingFluid(Fluid input) {
        return getOutput(input) != null;
    }

    /**
     *  FluidStack amounts MUST be divisible or weird things will happen! The exchange happens for every 10TU the plumbing has.
     *  Ex: water -- 1:160 -- for every 10 TU, 1mb water becomes 160mb steam
     * @param input FluidStack of fluid to be heated
     * @param output FluidStack of what the input turns into once heated
     */
    public static void addWorkingFluid(FluidStack input, FluidStack output) {
        workingFluid.add(new WorkingFluid(input, output));
    }
}
