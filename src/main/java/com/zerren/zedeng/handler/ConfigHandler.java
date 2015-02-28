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

    public static void init(File configFile){

        if (config == null) {
            config = new Configuration(configFile);
            loadConfig();
        }
    }

    private static void loadConfig() {
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
