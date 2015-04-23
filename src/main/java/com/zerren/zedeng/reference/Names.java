package com.zerren.zedeng.reference;

import com.zerren.zedeng.handler.ConfigHandler;

/**
 * Created by Zerren on 2/19/2015.
 */
public final class Names {
    public static final class Blocks {

        public static final String ORE = "ore";
        public static final String[] ORE_SUBTYPES = {
                "oreChromium",
                "oreNickel",
                "oreUranium"
        };

        public static final String METAL = "metal";
        public static final String[] METAL_SUBTYPES = {
                "blockChromium",
                "blockNickel",
                "blockDepletedUranium",
                "blockGraphite",
                "blockStainlessSteel",
                "blockInconel"
        };

        public static final String VAULT = "vault";
        public static final String[] VAULT_SUBTYPES = {
                "wall",
                "controller",
                "lock",
                "door_closed",
                "door_open",
                "container"
        };

        public static final String CHEST = "vaultChest";
        public static final String[] CHEST_SUBTYPES = {
                "brick",
                "thaumium",
                "voidmetal"
        };

        public static final String PLUMBING = "plumbing";
        public static final String[] PLUMBING_SUBTYPES = {
                "liquidHeatExchanger",
                "distributionChamber",
                "stainlessPipe",
                "gasTank"
        };

        public static final String REACTOR = "reactor";
        public static final String[] REACTOR_SUBTYPES = {
                "pressurizedWaterReactor"
        };
    }

    public static final class Items {
        public static final String MATERIAL = "material";
        public static final String[] MATERIAL_SUBTYPES = {
                "boltStainlessSteel",
                "plateStainlessSteel",
                "tubeStainlessSteel",
                "plateInconel",
                "tubeInconel"
        };

        public static final String INGOT = "ingot";
        public static final String[] INGOT_SUBTYPES = {
                "ingotChromium",
                "ingotNickel",
                "ingotStainlessSteel",
                "ingotInconel",
                "ingotGraphite",
                "ingotDepletedUranium"
        };

        public static final String DUST = "dust";
        public static final String[] DUST_SUBTYPES = {
                "dustIron",
                "dustChromium",
                "dustNickel",
                "dustStainlessSteel",
                "dustInconel",
                "dustGraphite",
                "dustUraniumDioxide"
        };

        public static final String KEY = "key";
        public static final String[] KEY_SUBTYPES = {
                "bedrock",
                "iron",
                "gold",
                "emerald",
                "diamond"
        };

        public static final String TOOL = "tool";
        public static final String[] TOOL_SUBTYPES = {
                "wrench"
        };

        public static final String FUEL = "fuel";
        public static final String[] FUEL_SUBTYPES = {
                "fuelPelletUO2",
                "fuelRodUO2",
                "fuelAssemblyUO2"
        };
    }

    public static final class Fluids {
        public static String COOLANT_COLD;
        public static String COOLANT_HOT;
        public static String UF6;
        public static String DISTILLED_WATER;
        public static String STEAM;

        public static void initConfigValues() {
            COOLANT_COLD = ConfigHandler.fluidNameCoolantCold;
            COOLANT_HOT = ConfigHandler.fluidNameCoolantHot;
            UF6 = ConfigHandler.fluidNameUF6;
            DISTILLED_WATER = ConfigHandler.fluidNameDistilledWater;
            STEAM = ConfigHandler.fluidNameSteam;
        }
    }

    public static final class Potions {
        public static final String[] EFFECTS = {
                "alphaRad",
                "betaRad",
                "gammaRad",
                "neutronRad",
                "radSickness"
        };
    }

    public static final class NBT {
        public static final String ITEMS = "items";
        public static final String TANK = "tank";
        public static final String UUID_MOST_SIG = "UUIDMostSig";
        public static final String UUID_LEAST_SIG = "UUIDLeastSig";
        public static final String STATE = "teState";
        public static final String CUSTOM_NAME = "customName";
        public static final String DIRECTION = "orientation";
        public static final String OWNER_UUID_MOST_SIG = "ownerUUIDMostSig";
        public static final String OWNER_UUID_LEAST_SIG = "ownerUUIDLeastSig";

        public static final String MASTER_UUID_MOST_SIG = "masterUUIDMostSig";
        public static final String MASTER_UUID_LEAST_SIG = "masterUUIDLeastSig";

        public static final String CONTROLLER_UUID_MOST_SIG = "controllerUUIDMostSig";
        public static final String CONTROLLER_UUID_LEAST_SIG = "controllerUUIDLeastSig";

        public static final String THERMAL_UNITS = "thermalUnits";
        public static final String WASTEHEAT_UNITS = "wasteHeatUnits";
        public static final String SLAVE_LOCATION = "slaveLocation";

        public static final String FUEL_TEMPERATURE = "temperature";
    }
}
