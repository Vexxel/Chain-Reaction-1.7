package chainreaction.api.recipe;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zerren on 4/3/2015.
 */
public class LiquifyingFluid {

    private static List<LiquifyingFluid> liquifyingFluids = new ArrayList<LiquifyingFluid>();

    public final FluidStack input;
    public final FluidStack output;

    private LiquifyingFluid(FluidStack input, FluidStack output){
        this.input = input;
        this.output = output;
    }

    private Fluid getInput(){
        return input.getFluid();
    }

    @Nullable
    public static FluidStack getOutput(Fluid input){
        for(LiquifyingFluid fluid : LiquifyingFluid.liquifyingFluids) {
            if (input == fluid.getInput()) {
                return fluid.output;
            }
        }
        return null;
    }

    public static int getInputRequiredAmount(Fluid input) {
        for (LiquifyingFluid fluid : LiquifyingFluid.liquifyingFluids) {
            if (input == fluid.getInput())
                return fluid.input.amount;
        }
        return 0;
    }

    public static boolean validLiquifyingFluid(Fluid input) {
        return getOutput(input) != null;
    }

    /**
     *  Adds a recipe to the Gas Liquifier for compression of gas into liquids.
     *  At base speed, the liquifier can liquify one bucket of gas per second.
     * @param input FluidStack of gas to be liquified
     * @param output FluidStack of the resulting liquid
     */
    public static void addLiquifyingFluid(FluidStack input, FluidStack output) {
        liquifyingFluids.add(new LiquifyingFluid(input, output));
    }
}
