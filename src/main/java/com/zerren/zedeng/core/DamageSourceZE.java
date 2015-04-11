package com.zerren.zedeng.core;

import net.minecraft.util.DamageSource;

/**
 * Created by Zerren on 3/8/2015.
 */
public final class DamageSourceZE extends DamageSource {

    public static final DamageSource THERMAL = (new DamageSourceZE("thermal")).setFireDamage();
    public static final DamageSource BETA_RAD = (new DamageSourceZE("betaRadiation")).setFireDamage().setMagicDamage();
    public static final DamageSource GAMMA_RAD = (new DamageSourceZE("gammaRadiation")).setDamageBypassesArmor().setMagicDamage();
    public static final DamageSource NEUTRON_RAD = (new DamageSourceZE("neutronRadiation")).setFireDamage().setDamageBypassesArmor();

    public DamageSourceZE(String name) {
        super(name);
    }
}
