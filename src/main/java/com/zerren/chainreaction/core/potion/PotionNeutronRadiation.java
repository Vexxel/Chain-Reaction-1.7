package com.zerren.chainreaction.core.potion;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;

/**
 * Created by Zerren on 3/8/2015.
 */
public class PotionNeutronRadiation extends PotionCR {

    public PotionNeutronRadiation(int id, String name) {
        super(id, name, true, 5223566, 3);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        if (hasEffect(event.entityLiving)) {

        }
    }
}
