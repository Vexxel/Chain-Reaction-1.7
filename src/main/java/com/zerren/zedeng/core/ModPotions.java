package com.zerren.zedeng.core;

import com.zerren.zedeng.core.potion.PotionAlphaRadiation;
import com.zerren.zedeng.core.potion.PotionZE;
import com.zerren.zedeng.handler.ConfigHandler;
import net.minecraft.potion.Potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by Zerren on 3/8/2015.
 */
public class ModPotions {
    public static Potion alphaRad, betaRad, gammaRad, neutronRad;

    public static void init() {

        if(Potion.potionTypes.length < 256)
            extendPotionArray();

        alphaRad = new PotionAlphaRadiation(ConfigHandler.potionIDAlpha, "alpha", true, 0, 0, 0);
        betaRad = new PotionZE(ConfigHandler.potionIDBeta, "beta", true, 0, 1, 0);
        gammaRad = new PotionZE(ConfigHandler.potionIDGamma, "gamma", true, 0, 2, 0);
        neutronRad = new PotionZE(ConfigHandler.potionIDNeutron, "neutron", true, 0, 3, 0);
    }

    private static void extendPotionArray() {
        Potion[] potionTypes = null;

        for (Field f : Potion.class.getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
                    Field modfield = Field.class.getDeclaredField("modifiers");
                    modfield.setAccessible(true);
                    modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);

                    potionTypes = (Potion[])f.get(null);
                    final Potion[] newPotionTypes = new Potion[256];
                    System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
                    f.set(null, newPotionTypes);
                }
            } catch (Exception e) {
                System.err.println("Severe error, please report this to the mod author:");
                System.err.println(e);
            }
        }
    }
}
