package com.zerren.zedeng.core;

import net.minecraft.util.DamageSource;

/**
 * Created by Zerren on 3/8/2015.
 */
public final class DamageSourceZE extends DamageSource {

    public static final DamageSource THERMAL_LOW = (new DamageSourceZE("thermal_low")).setFireDamage();
    public static final DamageSource THERMAL_HIGH = (new DamageSourceZE("thermal_high")).setFireDamage();
    public static final DamageSource RAD_LOW = (new DamageSourceZE("rad_low")).setMagicDamage();
    public static final DamageSource RAD_HIGH = (new DamageSourceZE("rad_high")).setMagicDamage().setDamageBypassesArmor();
    public static final DamageSource ANNIHILATION = (new DamageSourceZE("annihilation")).setAnnihilationDamage();

    private boolean annihilationDamage;

    public DamageSourceZE(String name) {
        super(name);
    }

    public DamageSourceZE setAnnihilationDamage() {
        this.annihilationDamage = true;
        this.setExplosion();
        this.setDamageIsAbsolute();
        this.setDamageBypassesArmor();
        return this;
    }

    public boolean isAnnihilationDamage() {
        return this.annihilationDamage;
    }
}
