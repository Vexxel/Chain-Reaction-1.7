package com.zerren.zedeng.core;

import com.zerren.zedeng.reference.Names;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by Zerren on 3/7/2015.
 */
public class ModFluids {
    public static Fluid coolantColdFluid = new Fluid(Names.Fluids.COOLANT_COLD).setLuminosity(0).setDensity(1000).setViscosity(1000).setTemperature(295);
    public static Fluid coolantHotFluid = new Fluid(Names.Fluids.COOLANT_HOT).setLuminosity(3).setDensity(900).setViscosity(750).setTemperature(600);

    public static void init() {
        FluidRegistry.registerFluid(coolantColdFluid);
        FluidRegistry.registerFluid(coolantHotFluid);
    }
}
