package com.zerren.chainreaction.core;

import net.minecraft.util.DamageSource;

/**
 * Created by Zerren on 3/8/2015.
 */
public final class DamageSourceCR extends DamageSource {

    public static final DamageSource THERMAL_LOW = (new DamageSourceCR("thermal_low")).setFireDamage();
    public static final DamageSource THERMAL_HIGH = (new DamageSourceCR("thermal_high")).setFireDamage();
    public static final DamageSource RAD_LOW = (new DamageSourceCR("rad_low")).setMagicDamage();
    public static final DamageSource RAD_HIGH = (new DamageSourceCR("rad_high")).setMagicDamage().setDamageBypassesArmor();
    public static final DamageSource ANNIHILATION = (new DamageSourceCR("annihilation")).setAnnihilationDamage();

    private boolean annihilationDamage;

    public DamageSourceCR(String name) {
        super(name);
    }

    public DamageSourceCR setAnnihilationDamage() {
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
