package com.zerren.chainreaction.core;

import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.core.potion.*;
import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.reference.Names;
import net.minecraft.potion.Potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Created by Zerren on 3/8/2015.
 */
public class ModPotions {
    public static Potion[] potion = new Potion[Names.Potions.EFFECTS.length];

    public static void init() {

        if(Potion.potionTypes.length < 256) {
            ChainReaction.log.info("Potion array is default size--extending since no other mod has done this yet");
            extendPotionArray();
        }

        potion[0] = new PotionAlphaRadiation(ConfigHandler.potionIDs[0], Names.Potions.EFFECTS[0]);
        potion[1] = new PotionBetaRadiation(ConfigHandler.potionIDs[1], Names.Potions.EFFECTS[1]);
        potion[2] = new PotionGammaRadiation(ConfigHandler.potionIDs[2], Names.Potions.EFFECTS[2]);
        potion[3] = new PotionNeutronRadiation(ConfigHandler.potionIDs[3], Names.Potions.EFFECTS[3]);
        potion[4] = new PotionRadiationSickness(ConfigHandler.potionIDs[4], Names.Potions.EFFECTS[4]);
        potion[5] = new PotionHypothermia(ConfigHandler.potionIDs[5], Names.Potions.EFFECTS[5]);
    }

    public static int getEffectID(String name) {
        return potion[Arrays.asList(Names.Potions.EFFECTS).indexOf(name)].getId();
    }

    private static void extendPotionArray() {
        Potion[] potionTypes;

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
                    ChainReaction.log.info("Potion array reflection success--new size: " + newPotionTypes.length);
                }
            }
            catch (Exception e) {
                ChainReaction.log.error("Severe error, please report this to the mod author:");
                ChainReaction.log.error(e);
            }
        }
    }
}
