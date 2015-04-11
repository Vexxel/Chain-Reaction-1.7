package com.zerren.zedeng.core;

import com.zerren.zedeng.ZederrianEngineering;
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
    public static Fluid coolantColdFluid;
    public static Fluid coolantHotFluid;
    public static Fluid distilledWater;
    public static Fluid steam;
    public static Fluid uf6;

    //pretty hacky way of making a single method for the fluid register, but it works lol
    private static Fluid temp;

    public static void init() {
        tryFluidRegister(coolantColdFluid, Names.Fluids.COOLANT_COLD, 0, 1000, 1000, 295, false);
        tryFluidRegister(coolantHotFluid, Names.Fluids.COOLANT_HOT, 1, 900, 750, 600, false);
        tryFluidRegister(distilledWater, Names.Fluids.DISTILLED_WATER, 0, 1000, 1000, 295, false);
        tryFluidRegister(steam, Names.Fluids.STEAM, 1, -500, 20, 550, true);
        tryFluidRegister(uf6, Names.Fluids.UF6, 0, 5100, 200, 350, true);
    }

    private static void tryFluidRegister(Fluid fluid, String id, int luminosity, int density, int viscosity, int temperature, boolean gaseous) {
        temp = null;

        if (!FluidRegistry.isFluidRegistered(id)) {
            fluid = new Fluid(id).setLuminosity(luminosity).setDensity(density).setViscosity(viscosity).setTemperature(temperature).setGaseous(gaseous);

            FluidRegistry.registerFluid(fluid);

            temp = fluid;
        }
        else {
            ZederrianEngineering.log.warn("Already found fluid for " + fluid.getUnlocalizedName() + ", skipping. If issues occur, try first removing the fluid owner's mod!");

            temp = FluidRegistry.getFluid(id);
        }
        addFluidVar(id);
    }

    private static void addFluidVar(String id) {
        if (id.equals(Names.Fluids.COOLANT_COLD)) {
            coolantColdFluid = temp;
        }
        else if (id.equals(Names.Fluids.COOLANT_HOT)) {
            coolantHotFluid = temp;
        }
        else if (id.equals(Names.Fluids.DISTILLED_WATER)) {
            distilledWater = temp;
        }
        else if (id.equals(Names.Fluids.STEAM)) {
            steam = temp;
        }
        else if (id.equals(Names.Fluids.UF6)) {
            uf6 = temp;
        }

        temp = null;
    }
}