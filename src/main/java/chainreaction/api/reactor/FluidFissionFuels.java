package chainreaction.api.reactor;

import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zerren on 4/15/2015.
 */
public class FluidFissionFuels {

    private static List<FluidFissionFuels> fluidFuels = new ArrayList<FluidFissionFuels>();

    private final FluidStack input;
    private final FluidStack waste;
    private final float heatPerFission;

    private FluidFissionFuels(FluidStack input, FluidStack waste, float heatPerFission) {
        this.input = input;
        this.waste = waste;
        this.heatPerFission = heatPerFission;
    }

    public static void addFissionFuel(FluidStack input, FluidStack waste, float heatPerFission) {
        fluidFuels.add(new FluidFissionFuels(input, waste, heatPerFission));
    }

    public static FluidStack getOutputWaste(FluidStack input) {
        for (FluidFissionFuels fuel : fluidFuels) {
            if (input == fuel.input) {
                return fuel.waste;
            }
        }
        return null;
    }

    public static float getHeatPerFission(FluidStack input) {
        for (FluidFissionFuels fuel : fluidFuels) {
            if (input == fuel.input) {
                return fuel.heatPerFission;
            }
        }
        return 0;
    }

    public static boolean isValidFuel(FluidStack input) {
        return getHeatPerFission(input) > 0;
    }
}