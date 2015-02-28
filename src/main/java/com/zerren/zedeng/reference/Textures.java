package com.zerren.zedeng.reference;

import net.minecraft.util.ResourceLocation;

/**
 * Created by Zerren on 2/19/2015.
 */
public class Textures {

    public static final String RESOURCE_PREFIX = Reference.MOD_ID.toLowerCase() + ":";

    public static final class folders {
        public static final String GLASS_FOLDER = "glass/";
        public static final String VAULT_FOLDER = "vault/";
        public static final String KEY_FOLDER = "key/";
    }

    public static final class guis {
        public static final String GUI_SHEET_LOCATION = "textures/gui/";

        public static final ResourceLocation WIDGETS = getResourceLocation(GUI_SHEET_LOCATION + "widgets.png");
        public static final ResourceLocation KEY = getResourceLocation(GUI_SHEET_LOCATION + "key.png");
        public static final ResourceLocation VAULT = getResourceLocation(GUI_SHEET_LOCATION + "vault.png");
    }

    public static ResourceLocation getResourceLocation(String modId, String path) {
        return new ResourceLocation(modId, path);
    }

    public static ResourceLocation getResourceLocation(String path) {
        return getResourceLocation(Reference.MOD_ID.toLowerCase(), path);
    }

}