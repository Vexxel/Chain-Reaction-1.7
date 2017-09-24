package com.zerren.chainreaction.handler;

import com.zerren.chainreaction.item.baubles.SetBonus;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.reference.Reference;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by Zerren on 2/19/2015.
 */
public class ConfigHandler {

    public static Configuration config;

    public static boolean devDebug;
    public static float vaultResistance;
    public static boolean bedrockVault;
    public static int vaultPages;
    public static float steamFactor;
    public static boolean harderStainless;
    public static int gasTankVolume;
    public static int rtgPowerPu238;
    public static int rtgPowerPo210;
    public static int rtgPowerSr90;
    public static int rtgPowerAm241;
    public static int rtgPowerIC2;

    public static int temperature;

    public static int[] potionIDs = {66, 67, 68, 69, 70, 71};
    public static boolean uniSteam;
    public static float unbreakingChance;
    public static float powerModifier;
    public static float hasteModifier;
    public static int healthAmulet;
    public static float speedModifier;
    public static float deflectionChance;
    public static float knockbackResistChance;
    public static float protectionModifier;
    public static float jumpModifier;
    public static int regenFrequency;
    public static int shieldFrequency;
    public static float vampireModifier;
    public static float thornsModifier;


    public static float skullfireChance;
    public static float guardianKnockbackResistChance;
    public static float druidSpeedBonus;


    private static final ConfigCategory CATEGORY_IDS = new ConfigCategory("ids").setRequiresMcRestart(true).setShowInGui(true);
    private static final ConfigCategory CATEGORY_BAUBLES = new ConfigCategory("baubles").setShowInGui(true);
    private static final ConfigCategory CATEGORY_BAUBLESETS = new ConfigCategory("setbonus").setShowInGui(true);



    public static void init(File configFile){

        if (config == null) {
            config = new Configuration(configFile);
            loadConfigValues();
            if (config.hasChanged())
                config.save();
        }
    }

    private static void loadConfigValues() {
        //IDs
        config.addCustomCategoryComment(CATEGORY_IDS.getName(), "Below is used for changing the internal IDs that this mod uses: if you have potion ID conflicts, change them below.");
        potionIDs = config.get(CATEGORY_IDS.getName(), "potionIDs", potionIDs, "Potion ID array in descending order (Alpha Rad., Beta Rad., Gamma Rad., Neutron Rad., Rad. Sickness, Hypothermia)", 33, 127).getIntList();
        uniSteam = config.getBoolean("uniSteam", CATEGORY_IDS.getName(), false, "If this mod should produce steam compatible with most mods. (Changes registry name from 'saturatedsteam' to 'steam'.");
        //commit these values to the name registry
        Names.Fluids.initConfigValues();

        //tweaks
        devDebug = config.getBoolean("devDebug", "general", false, "System print for debugging--leave false unless you have a log spam fetish");
        vaultResistance = config.getFloat("vaultResistance", "general", 100F, 10F, 600F, "Blast resistance of a player owned and locked vault");
        bedrockVault = config.getBoolean("bedrockVault", "general", false, "If true, player owned and locked vaults are 100% immune to explosions--overwrites vaultResistance");
        vaultPages = config.getInt("vaultPages", "general", 10, 1, 20, "How many pages of 54 slots the vault will have. (1 page = double chest, default is 540 slots)");
        temperature = config.getInt("temperature", "general", 0, 0, 2, "What to display temperature as: 0 for Kelvin, 1 for Celsius, 2 for Fahrenheit");

        harderStainless = config.getBoolean("harderStainless", "general", false, "If true, the recipe for stainless steel dust requires steel dust in place of iron dust. For use with mods that add steel dust");
        steamFactor = config.getFloat("steamFactor", "general", 1F, 0.1F, 5F, "Multiplier on steam produced--(this is always a 1:160 water:steam ratio though)--best if used with 'uniSteam'");

        gasTankVolume = config.getInt("gasTankVolume", "general", 128, 32, 512, "Number of buckets of gas that the Gas Tank can hold");

        rtgPowerPu238 = config.getInt("rtgPowerPu238", "general", 64, 16, 128, "RF/t the RTG generates with Plutonium 238");
        rtgPowerPo210 = config.getInt("rtgPowerPo210", "general", 512, 64, 1024, "RF/t the RTG generates with Polonium 210");
        rtgPowerSr90 = config.getInt("rtgPowerSr90", "general", 32, 8, 64, "RF/t the RTG generates with Strontium 90");
        rtgPowerAm241 = config.getInt("rtgPowerAm241", "general", 16, 4, 64, "RF/t the RTG generates with Americium 241");
        rtgPowerIC2 = config.getInt("rtgPowerIC2", "general", 64, 16, 128, "RF/t the RTG generates with IC2's RTG Pebbles");

        //baubles
        unbreakingChance = config.getFloat("unbreakingChance", CATEGORY_BAUBLES.getName(), 0.3F, 0.1F, 0.5F, "Chance that the unbreaking ring will protect a used tool");
        powerModifier = config.getFloat("powerModifier", CATEGORY_BAUBLES.getName(), 0.2F, 0.1F, 1F, "Ring of Fury percent damage increase");
        hasteModifier = config.getFloat("hasteModifier", CATEGORY_BAUBLES.getName(), 0.3F, 0.1F, 1F, "Ring of Deft Hands percent haste increase");
        healthAmulet = config.getInt("healthModifier", CATEGORY_BAUBLES.getName(), 3, 1, 10, "Number of extra hearts that the Amulet of Vitality gives");
        speedModifier = config.getFloat("speedModifier", CATEGORY_BAUBLES.getName(), 0.3F, 0.1F, 1F, "Belt of Swiftness speed increase");
        deflectionChance = config.getFloat("deflectionChance", CATEGORY_BAUBLES.getName(), 0.4F, 0.1F, 1F, "Amulet of Deflection chance to deflect a projectile");
        knockbackResistChance = config.getFloat("knockbackResistChance", CATEGORY_BAUBLES.getName(), 0.5F, 0.1F, 1F, "Belt of the Mountain knockback resistance bonus");
        protectionModifier = config.getFloat("protectionModifier", CATEGORY_BAUBLES.getName(), 0.2F, 0.1F, 0.33F, "Ring of Protection percent damage reduction");
        jumpModifier = config.getFloat("jumpModifier", CATEGORY_BAUBLES.getName(), 1F, 0.5F, 2F, "Belt of Leaping jump height increase (in blocks)");
        regenFrequency = config.getInt("regenFrequency", CATEGORY_BAUBLES.getName(), 3, 1, 5, "Ring of Regeneration healing frequency (in seconds)");
        shieldFrequency = config.getInt("shieldFrequency", CATEGORY_BAUBLES.getName(), 8, 4, 20, "Amulet of Shielding cooldown (in seconds)");
        vampireModifier = config.getFloat("vampireModifier", CATEGORY_BAUBLES.getName(), 0.2F, 0.1F, 0.5F, "Ring of Vampirism life steal percentage on attack");
        thornsModifier = config.getFloat("thornsModifier", CATEGORY_BAUBLES.getName(), 0.3F, 0.2F, 0.5F, "Ring of Jagged Thorns damage reflection percentage upon being attacked");

        //set bonus
        skullfireChance = config.getFloat(SetBonus.SKULLFIRE.getBonusName(), CATEGORY_BAUBLESETS.getName(), 0.2F, 0.05F, 1F, "Skullfire set effect wither skull drop chance");
        guardianKnockbackResistChance = config.getFloat(SetBonus.GUARDIAN.getBonusName(), CATEGORY_BAUBLESETS.getName(), 0.5F, 0.1F, 1F, "Guardian set effect knockback resistance bonus");
        druidSpeedBonus = config.getFloat(SetBonus.DRUID.getBonusName(), CATEGORY_BAUBLESETS.getName(), 0.2F, 0.1F, 0.6F, "Druidic set effect speed bonus");


    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(Reference.ModInfo.MOD_ID)) {
            loadConfigValues();
        }
    }
}
