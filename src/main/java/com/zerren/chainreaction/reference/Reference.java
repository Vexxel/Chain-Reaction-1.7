package com.zerren.chainreaction.reference;

import com.zerren.chainreaction.handler.ConfigHandler;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Zerren on 2/19/2015.
 */
public final class Reference {

    public static final class ModInfo {
        public static final String MOD_ID = "ChainReaction";
        public static final String MOD_NAME = "Chain Reaction";
        public static final String VERSION = "1.7.10-1.0.1";
        public static final String CLIENTPROXY_CLASS = "com.zerren.chainreaction.core.proxy.ClientProxy";
        public static final String COMMONPROXY_CLASS = "com.zerren.chainreaction.core.proxy.CommonProxy";
        public static final String GUIFACTORY_CLASS = "com.zerren.chainreaction.client.gui.GuiFactory";

        public static final String CR_RESOURCE_PREFIX = Reference.ModInfo.MOD_ID.toLowerCase() + ":";
    }

    public static final class Textures {


        public static final class Folders {
            public static final String MATERIAL_FOLDER = "material/";
            public static final String VAULT_FOLDER = "vault/";
            public static final String KEY_FOLDER = "key/";
            public static final String TOOL_FOLDER = "tool/";
            public static final String ARMOR_FOLDER = "armor/";
            public static final String REACTOR_FOLDER = "reactor/";
            public static final String PLUMBING_FOLDER = "plumbing/";
            public static final String FLUID_FOLDER = "fluid/";
            public static final String MECHANISM_FOLDER = "mechanism/";
            public static final String ORE_FOLDER = "ore/";
            public static final String BAUBLE_FOLDER = "bauble/";
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
            public static final ResourceLocation PRESSURIZED_WATER_REACTOR = getResourceLocation(GUI_SHEET_LOCATION + "pressurizedWaterReactor.png");
            public static final ResourceLocation BLOOMERY = getResourceLocation(GUI_SHEET_LOCATION + "bloomery.png");
        }

        public static final class Models {
            public static final String MODEL_LOCATION = "textures/models/";
            public static final String ARMOR_LOCATION = MODEL_LOCATION + "armor/";

            public static final ResourceLocation CHEST_BRICK = getResourceLocation(MODEL_LOCATION + "chest_brick.png");
            public static final ResourceLocation CHEST_THAUMIUM = getResourceLocation(MODEL_LOCATION + "chest_thaumium.png");
            public static final ResourceLocation CHEST_VOID = getResourceLocation(MODEL_LOCATION + "chest_voidmetal.png");
            public static final ResourceLocation HEAT_EXCHANGER  = getResourceLocation(MODEL_LOCATION + "exchanger.png");
            public static final ResourceLocation TUBES  = getResourceLocation(MODEL_LOCATION + "tubes.png");
            public static final ResourceLocation GAS_TANK  = getResourceLocation(MODEL_LOCATION + "gasTank.png");
            public static final ResourceLocation PRESSURIZED_WATER_REACTOR  = getResourceLocation(MODEL_LOCATION + "pressurizedWaterReactor.png");
            public static final ResourceLocation TELEPORTER  = getResourceLocation(MODEL_LOCATION + "teleporter.png");
            public static final ResourceLocation BLOOMERY_OFF  = getResourceLocation(MODEL_LOCATION + "bloomery_off.png");
            public static final ResourceLocation BLOOMERY_ON  = getResourceLocation(MODEL_LOCATION + "bloomery_on.png");

            //Armor
            public static final ResourceLocation O2_MASK  = getResourceLocation(ARMOR_LOCATION + "o2mask.png");
            public static final ResourceLocation THRUST_PACK  = getResourceLocation(ARMOR_LOCATION + "thrustpack.png");
        }

        public static final class Misc {
            public static final String MISC_LOCATION = "textures/misc/";

            public static final ResourceLocation PARTICLES = getResourceLocation(MISC_LOCATION + "particles.png");
            public static final ResourceLocation DEFAULT_BLOCK_TEXTURES = TextureMap.locationBlocksTexture;
        }
    }

    public static final class Sounds {

        public static final String CHEST_OPEN = "random.chestopen";
        public static final String CHEST_CLOSE = "random.chestclosed";
        public static final String PISTON_OUT = "tile.piston.out";
        public static final String FIZZ = "random.fizz";

        public static final String LOCK_SUCCESS = ModInfo.CR_RESOURCE_PREFIX + "lock_success";
        public static final String LOCK_FAILURE = ModInfo.CR_RESOURCE_PREFIX + "lock_failure";
        public static final String LOCK_RATTLE = ModInfo.CR_RESOURCE_PREFIX + "lock_rattle";

        public static final String RECORDS = ModInfo.CR_RESOURCE_PREFIX;
    }

    public static ResourceLocation getResourceLocation(String path) {
        return new ResourceLocation(ModInfo.MOD_ID.toLowerCase(), path);
    }

    public static final class Tweaks {
        public static final double TILE_PACKET_RANGE = 256;
    }

    public enum GUIs {
        KEY,
        VAULT,
        CHEST,
        PWR,
        BLOOMERY
    }
}