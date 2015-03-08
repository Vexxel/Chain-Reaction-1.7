package com.zerren.zedeng.handler;

import com.zerren.zedeng.reference.Reference;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by Zerren on 2/19/2015.
 */
public class ConfigHandler {

    public static Configuration config;

    public static float vaultResistance = 100F;
    public static boolean bedrockVault = false;
    public static int vaultPages = 10;

    public static int potionIDAlpha = 166,
            potionIDBeta = 167,
            potionIDGamma = 168,
            potionIDNeutron = 169;


    public static void init(File configFile){

        if (config == null) {
            config = new Configuration(configFile);
            loadConfig();
        }
    }

    private static void loadConfig() {
        potionIDAlpha = config.getInt("potionIDAlpha", "general", 166, 33, 255, "Alpha Radiation potion effect ID");
        potionIDBeta = config.getInt("potionIDBeta", "general", 167, 33, 255, "Beta Radiation potion effect ID");
        potionIDGamma = config.getInt("potionIDGamma", "general", 168, 33, 255, "Gamma Radiation potion effect ID");
        potionIDNeutron = config.getInt("potionIDNeutron", "general", 169, 33, 255, "Neutron Radiation potion effect ID");

        vaultResistance = config.getFloat("vaultResistance", "tweaks", 100F, 10F, 600F, "Blast resistance of a player owned and locked vault");
        bedrockVault = config.getBoolean("bedrockVault", "tweaks", false, "If true, player owned and locked vaults are immune to explosions--overwrites vaultResistance");
        vaultPages = config.getInt("vaultPages", "tweaks", 10, 1, 20, "How many pages of 54 slots the vault will have. Each page is a double chest worth of space");

        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
            loadConfig();
        }
    }
}
