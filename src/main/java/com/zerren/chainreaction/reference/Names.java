package com.zerren.chainreaction.reference;

import com.zerren.chainreaction.handler.ConfigHandler;

import java.util.UUID;

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
                "pwrAssembly",
                "pwrCasing"
        };

        public static final String MECHANISM = "mechanism";
        public static final String[] MECHANISM_SUBTYPES = {
                "teleporter",
                "bloomery",
                "rtg"
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
                "wrench",
                "scanner",
                "manual"
        };

        public static final String FUEL_PARTS = "fuelParts";
        public static final String[] FUEL_PART_SUBTYPES = {
                "fuelPelletUO2",
                "fuelRodUO2"
        };

        public static final String FUEL = "fuel";
        public static final String[] FUEL_SUBTYPES = {
                "fuelAssemblyUO2",
                "rtgFuelPu238",
                "rtgFuelPo210"
        };

        public static final String O2_MASK = "o2mask";
        public static final String THRUST_PACK = "thrustpack";

        public static final String RECORDS = "record";
        public static final String[] RECORD_SUBTYPES = {
                "industrial"
        };

        public static final String ORES = "oreItem";
        public static final String[] ORE_SUBTYPES = {
                "bloom",
                "iron",
                "gold",
                "tin",
                "lead",
                "silver",
                "nickel"
        };

        public static final String BAUBLES = "bauble";
        public static final String[] BAUBLE_SUBTYPES = {
                "fallDamageAmulet",
                "hasteRing",
                "unbreakingRing",
                "fireResistanceAmulet",
                "witherAmulet",
                "waterBreathingRing",
                "powerRing",
                "healthAmulet",
                "speedBelt",
                "deflectionAmulet",
                "knockbackBelt",
                "protectionRing",
                "jumpBelt",
                "regenRing",
                "shieldAmulet",
                "flightRing",
                "vampireRing",
                "thornsRing",
                "sunAmulet"
        };

        public static final String BAUBLE_MATERIALS = "baubleMats";
        public static final String[] BAUBLE_MATERIAL_SUBTYPES = {
                "ringIron",
                "ringGold",
                "ringSilver",
                "ringCopper",
                "ringBronze",
                "ringPlatinum",
                "ringThaumium",
                "ringTitanium",
                "ringElectrum",
                "ringMithril",
                "ringTungstenCarbide",
                "ringNickel",
                "ringVoidMetal",
                "amuletIron",
                "amuletGold",
                "amuletSilver",
                "amuletCopper",
                "amuletBronze",
                "amuletPlatinum",
                "amuletThaumium",
                "amuletTitanium",
                "amuletElectrum",
                "amuletMithril",
                "amuletTungstenCarbide",
                "amuletNickel",
                "amuletVoidMetal",
                "beltLeather",
                "beltIron",
                "beltGold",
                "beltSilver",
                "beltThaumium",
                "beltVoidMetal"
        };
    }

    public static final class Fluids {
        public static final String COOLANT_COLD = "coolantcold";
        public static final String COOLANT_HOT = "coolanthot";
        public static final String UF6 = "uraniumhexafluoride";
        public static final String DISTILLED_WATER = "distilledwater";
        public static String STEAM;

        public static void initConfigValues() {
            STEAM = ConfigHandler.uniSteam ? "steam" : "saturatedsteam";
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
        public static final String ENERGY_LEVEL = "energyLevel";
        public static final String SCANNER_MODE = "mode";

        public static final String MASTER_UUID_MOST_SIG = "masterUUIDMostSig";
        public static final String MASTER_UUID_LEAST_SIG = "masterUUIDLeastSig";

        public static final String CONTROLLER_UUID_MOST_SIG = "controllerUUIDMostSig";
        public static final String CONTROLLER_UUID_LEAST_SIG = "controllerUUIDLeastSig";

        public static final String THERMAL_UNITS = "thermalUnits";
        public static final String WASTEHEAT_UNITS = "wasteHeatUnits";
        public static final String MULTIBLOCK_LOCATION = "multiblockLocation";

        public static final String FUEL_TEMPERATURE = "temperature";
        public static final String FUEL_REMAINING = "fuelRemaining";
        public static final String RADIOACTIVITY = "radioactivity";

        public static final String BLOOM = "bloomSize";

        public static final String BAUBLE_COOLDOWN = "cooldown";

    }

    public static final class UUIDs {

        public static final UUID SPEED_BELT_BONUS_UUID = UUID.fromString("65a4e9c4-8968-419b-81f8-6c8af9715222");
        public static final String SPEED_BELT_BONUS_NAME = "CRSpeedBeltBonus";

        public static final UUID DRUID_SET_SPEED_BONUS_UUID = UUID.fromString("7b459a4e-67e0-4f1a-ba95-159f51ea4704");
        public static final String DRUID_SET_SPEED_BONUS_NAME = "CRDruidSpeedBonus";

        public static final UUID KNOCKBACK_BELT_BONUS_UUID = UUID.fromString("c29cecf3-6060-445f-a94d-0be3cbeb3893");
        public static final String KNOCKBACK_BELT_BONUS_NAME = "CRKnockbackBeltBonus";

        public static final UUID POWER_RING_BONUS_UUID = UUID.fromString("823ec755-2f32-4a69-96f7-e6c310dee20e");
        public static final String POWER_RING_BONUS_NAME = "CRPowerRingBonus";
    }
}
