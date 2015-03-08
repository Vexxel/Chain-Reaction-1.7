package com.zerren.zedeng.core;

import net.minecraft.util.DamageSource;

/**
 * Created by Zerren on 3/8/2015.
 */
public class DamageSourceZE extends DamageSource {

    public static DamageSource thermal = (new DamageSourceZE("thermal")).setFireDamage();
    public static DamageSource betaRad = (new DamageSourceZE("betaRadiation")).setFireDamage().setMagicDamage();
    public static DamageSource gammaRad = (new DamageSourceZE("gammaRadiation")).setDamageBypassesArmor().setMagicDamage();
    public static DamageSource neutronRad = (new DamageSourceZE("neutronRadiation")).setFireDamage().setDamageBypassesArmor();

    public DamageSourceZE(String name) {
        super(name);
    }
}
