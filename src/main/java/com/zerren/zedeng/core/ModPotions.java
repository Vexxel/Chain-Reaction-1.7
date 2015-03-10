package com.zerren.zedeng.core;

import com.zerren.zedeng.core.potion.*;
import com.zerren.zedeng.handler.ConfigHandler;
import net.minecraft.potion.Potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by Zerren on 3/8/2015.
 */
public class ModPotions {
    public static Potion alphaRad, betaRad, gammaRad, neutronRad, radSickness;

    public static void init() {

        if(Potion.potionTypes.length < 256)
            extendPotionArray();

        alphaRad = new PotionAlphaRadiation(ConfigHandler.potionIDAlpha);
        betaRad = new PotionBetaRadiation(ConfigHandler.potionIDBeta);
        gammaRad = new PotionGammaRadiation(ConfigHandler.potionIDGamma);
        neutronRad = new PotionNeutronRadiation(ConfigHandler.potionIDNeutron);
        radSickness = new PotionRadiationSickness(ConfigHandler.potionIDSickness);
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
                    System.out.println("Reflection Success");
                }
            } catch (Exception e) {
                System.err.println("Severe error, please report this to the mod author:");
                System.err.println(e);
            }
        }
    }
}
