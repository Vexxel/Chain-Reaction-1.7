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

    public PotionAlphaRadiation(int id, String name, boolean isBad, int color, int ssX, int ssY) {
        super(id, name, isBad, color, ssX, ssY);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.entityLiving.isPotionActive(ModPotions.alphaRad)) {

        }
    }
}
