package com.zerren.zedeng.reference;

/**
 * Created by Zerren on 2/19/2015.
 */
public class Names {
    public static final class Blocks {
        public static final String GLASS = "glass";
        public static final String GLASSPANE = "glasspane";

        public static final String[] GLASS_SUBTYPES = {
                "ruby",
                "fire",
                "orange",
                "amber",
                "gold",
                "lime", //5
                "green",
                "spring",
                "emerald",
                "cyan",
                "electric", //10
                "sapphire",
                "ultramarine",
                "indigo",
                "violet",
                "magenta" //15
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

        public static final String EXCHANGER = "exchanger";
        public static final String[] EXCHANGER_SUBTYPES = {
                "liquidHeatExchanger"
        };
    }

    public static final class Items {
        public static final String MATERIAL = "material";

        public static final String[] MATERIAL_SUBTYPES = {
                "dustChroma"
        };

        public static final String KEY = "key";

        public static final String[] KEY_SUBTYPES = {
                "bedrock",
                "iron",
                "gold",
                "emerald",
                "diamond"
        };
    }

    public static final class Fluids {
        public static final String COOLANT_COLD = "pressurizedWaterCold";
        public static final String COOLANT_HOT = "pressurizedWaterHot";
    }

    public static final class NBT {
        public static final String ITEMS = "Items";
        public static final String MODE = "mode";
        public static final String CRAFTING_GUI_OPEN = "craftingGuiOpen";
        public static final String UUID_MOST_SIG = "UUIDMostSig";
        public static final String UUID_LEAST_SIG = "UUIDLeastSig";
        public static final String DISPLAY = "display";
        public static final String COLOR = "color";
        public static final String STATE = "teState";
        public static final String CUSTOM_NAME = "CustomName";
        public static final String DIRECTION = "teDirection";
        public static final String OWNER = "ownerName";
        public static final String OWNER_UUID_MOST_SIG = "ownerUUIDMostSig";
        public static final String OWNER_UUID_LEAST_SIG = "ownerUUIDLeastSig";
    }
}
