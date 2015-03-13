package com.zerren.zedeng.core;

import com.zerren.zedeng.handler.ConfigHandler;
import com.zerren.zedeng.reference.Names;
import com.zerren.zedeng.utility.ZedMath;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.Dictionary;

/**
 * Created by Zerren on 3/7/2015.
 */
public class ModFluids {
    public static Fluid coolantColdFluid = new Fluid(Names.Fluids.COOLANT_COLD).setLuminosity(0).setDensity(1000).setViscosity(1000).setTemperature(295);
    public static Fluid coolantHotFluid = new Fluid(Names.Fluids.COOLANT_HOT).setLuminosity(1).setDensity(900).setViscosity(750).setTemperature(600);

    public static Fluid steam = new Fluid(ConfigHandler.uniSteam ? "steam" : Names.Fluids.STEAM).setLuminosity(1).setDensity(-500).setViscosity(20).setTemperature(550).setGaseous(true);
    public static Fluid uf6 = new Fluid(Names.Fluids.UF6).setLuminosity(0).setDensity(5100).setViscosity(200).setTemperature(350).setGaseous(true);

    public static void init() {
        FluidRegistry.registerFluid(coolantColdFluid);
        FluidRegistry.registerFluid(coolantHotFluid);
        FluidRegistry.registerFluid(steam);
        FluidRegistry.registerFluid(uf6);
    }
}