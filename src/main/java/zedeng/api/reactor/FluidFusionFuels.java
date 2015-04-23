package zedeng.api.reactor;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zerren on 4/15/2015.
 */
public class FluidFusionFuels {

    private static List<FluidFusionFuels> fluidFuels = new ArrayList<FluidFusionFuels>();

    private final FluidStack input1, input2;
    private final float energyPerFusion;
    private final boolean isDirectConversion;

    private FluidFusionFuels(FluidStack input1, FluidStack input2, float energyPerFusion, boolean isDirectConversion) {
        this.input1 = input1;
        this.input2 = input2;
        this.energyPerFusion = energyPerFusion;
        this.isDirectConversion = isDirectConversion;
    }

    private Fluid getInput1() {
        return input1.getFluid();
    }

    private Fluid getInput2() {
        return input2.getFluid();
    }

    public static float getEnergyPerFusion(Fluid input1, Fluid input2) {
        for (FluidFusionFuels fuel : fluidFuels) {
            if (input1 == fuel.getInput1() && input2 == fuel.getInput2()) {
                return fuel.energyPerFusion;
            }
        }
        return 0;
    }

    public static boolean isDirectConversion(Fluid input1, Fluid input2) {
        for (FluidFusionFuels fuel : fluidFuels) {
            if (input1 == fuel.getInput1() && input2 == fuel.getInput2()) {
                return fuel.isDirectConversion;
            }
        }
        return false;
    }

    public static boolean isValidFusionCombo(Fluid input1, Fluid input2) {
        return getEnergyPerFusion(input1, input2) > 0;
    }

    public static void addFusionFuel(FluidStack input1, FluidStack input2, float energyPerFusion, boolean isDirectConversion) {
        fluidFuels.add(new FluidFusionFuels(input1, input2, energyPerFusion, isDirectConversion));
    }
}