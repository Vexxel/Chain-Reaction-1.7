package chainreaction.api.recipe;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zerren on 4/3/2015.
 */
public class ElectrolyzingFluid {

    private static List<ElectrolyzingFluid> electrolyzingFluids = new ArrayList<ElectrolyzingFluid>();

    public final FluidStack input;
    public final FluidStack output1;
    public final FluidStack output2;

    private ElectrolyzingFluid(FluidStack input, FluidStack output1, FluidStack output2){
        this.input = input;
        this.output1 = output1;
        this.output2 = output2;
    }

    private Fluid getInput(){
        return input.getFluid();
    }

    @Nullable
    public static FluidStack getOutput1(Fluid input){
        for(ElectrolyzingFluid fluid : ElectrolyzingFluid.electrolyzingFluids) {
            if (input == fluid.getInput()) {
                return fluid.output1;
            }
        }
        return null;
    }

    @Nullable
    public static FluidStack getOutput2(Fluid input){
        for(ElectrolyzingFluid fluid : ElectrolyzingFluid.electrolyzingFluids) {
            if (input == fluid.getInput()) {
                return fluid.output2;
            }
        }
        return null;
    }

    public static int getInputRequiredAmount(Fluid input) {
        for (ElectrolyzingFluid fluid : ElectrolyzingFluid.electrolyzingFluids) {
            if (input == fluid.getInput())
                return fluid.input.amount;
        }
        return 0;
    }

    public static boolean validElectrolyzingFluid(Fluid input) {
        return getOutput1(input) != null;
    }

    /**
     *  Adds a recipe to the Fluid Electrolyzer for electrolysis of fluids into their component parts. This operation is run once per second,
     *  so reduce the amount of fluid here to make it run slower for more energy intensive electrolysis.
     *  For example, 200mb water (200 liters, 11100 mol) electrolyzes into 11100 mol oxygen and 22200 mol hydrogen. There are 22.4 liters of gas in a mol at STP,
     *  which means you get 1243 buckets of oxygen and 2486 buckets of hydrogen at STP from 1 bucket of water.. I'm definitely assuming these are compressed, so I'm valuing
     *  200mb of water to output 1000mb hydrogen and 500mb oxygen.
     * @param input FluidStack of fluid to be electrolyzed
     * @param output1 FluidStack of the first product the input electrolyzes into
     * @param output2 FluidStack of the second product the input electrolyzes into
     */
    public static void addElectrolyzingFluid(FluidStack input, FluidStack output1, FluidStack output2) {
        electrolyzingFluids.add(new ElectrolyzingFluid(input, output1, output2));
    }
}
