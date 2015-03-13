package com.zerren.zedeng.reference;

import net.minecraft.util.ResourceLocation;

/**
 * Created by Zerren on 2/19/2015.
 */
public class Textures {

    public static final String RESOURCE_PREFIX = Reference.MOD_ID.toLowerCase() + ":";

    public static final class Folders {
        public static final String GLASS_FOLDER = "glass/";
        public static final String VAULT_FOLDER = "vault/";
        public static final String KEY_FOLDER = "key/";
        public static final String REACTOR_FOLDER = "reactor/";
        public static final String FLUID_FOLDER = "fluid/";
    }

    public static final class GUIs {
        public static final String GUI_SHEET_LOCATION = "textures/gui/";

        public static final ResourceLocation WIDGETS = getResourceLocation(GUI_SHEET_LOCATION + "widgets.png");
        public static final ResourceLocation POTIONS = getResourceLocation(GUI_SHEET_LOCATION + "potions.png");
        public static final ResourceLocation KEY = getResourceLocation(GUI_SHEET_LOCATION + "key.png");
        public static final ResourceLocation VAULT = getResourceLocation(GUI_SHEET_LOCATION + "vault.png");
        public static final ResourceLocation CHEST_BRICK = getResourceLocation(GUI_SHEET_LOCATION + "chest_brick.png");
        public static final ResourceLocation CHEST_THAUMIUM = getResourceLocation(GUI_SHEET_LOCATION + "chest_thaumium.png");
        public static final ResourceLocation CHEST_VOID = getResourceLocation(GUI_SHEET_LOCATION + "chest_voidmetal.png");

    }

    public static final class Models {
        public static final String MODEL_LOCATION = "textures/models/";

        public static final ResourceLocation CHEST_BRICK = getResourceLocation(MODEL_LOCATION + "chest_brick.png");
        public static final ResourceLocation CHEST_THAUMIUM = getResourceLocation(MODEL_LOCATION + "chest_thaumium.png");
        public static final ResourceLocation CHEST_VOID = getResourceLocation(MODEL_LOCATION + "chest_voidmetal.png");

        public static final ResourceLocation HEAT_EXCHANGER  = getResourceLocation(MODEL_LOCATION + "exchanger.png");
    }

    public static final class Misc {
        public static final String MISC_LOCATION = "textures/misc/";

        public static final ResourceLocation PARTICLES = getResourceLocation(MISC_LOCATION + "particles.png");
    }

    public static ResourceLocation getResourceLocation(String path) {
        return new ResourceLocation(Reference.MOD_ID.toLowerCase(), path);
    }
}