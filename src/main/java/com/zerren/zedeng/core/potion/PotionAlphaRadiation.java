package com.zerren.zedeng.core.potion;


import com.zerren.zedeng.core.ModPotions;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;

/**
 * Created by Zerren on 3/8/2015.
 */
public class PotionAlphaRadiation extends PotionZE {

    public PotionAlphaRadiation(int id, String name) {
        super(id, name, true, 0x000000, 0);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        if (hasEffect(event.entityLiving)) {

        }
    }
}
