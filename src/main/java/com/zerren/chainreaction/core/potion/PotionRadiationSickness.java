package com.zerren.chainreaction.core.potion;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;

/**
 * Created by Zerren on 3/8/2015.
 */
public class PotionRadiationSickness extends PotionCR {

    public PotionRadiationSickness(int id, String name) {
        super(id, name, true, 7378797, 4);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {

        EntityLivingBase e = event.entityLiving;

        if (e != null) {
            if (e instanceof EntityPlayerMP) {
                //PacketHandler.INSTANCE.sendTo(new MessageShader((byte)0), (EntityPlayerMP)e);
            }
        }
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase e = event.entityLiving;

        //WIP shader access
        /*if (e instanceof EntityPlayerMP) {
            //has radiation sickness level 3+
            if (hasEffect(e) && getEffectLevel(e) >= 2) {
                //duration is longer than 2 seconds remaining
                if (getEffectTicks(e) > 40) {
                    //if the entity isn't dead (isDead doesn't work)
                    if (e.getHealth() > 0)
                        PacketHandler.netHandler.sendTo(new MessageShader((byte)1), (EntityPlayerMP)e);
                }
                //duration is shorter than 2 seconds remaining
                else {
                    PacketHandler.netHandler.sendTo(new MessageShader((byte)0), (EntityPlayerMP)e);
                }
            }
        }*/
    }

    @SubscribeEvent
    public void onLivingHeal(LivingHealEvent event) {

        if (hasEffect(event.entityLiving)) {
            float amount = event.amount;
            int level = getEffectLevel(event.entityLiving);

            //50% heal effectiveness
            if (level == 0) {
                amount *= 0.5;
            }
            //50% heal effectiveness, no natural regeneration (regen potion OK)
            else if (level == 1) {
                if (amount >= 2 || hasEffect(event.entityLiving, Potion.regeneration))
                    amount *= 0.5;
                else {
                    amount = 0;
                    event.setCanceled(true);
                }
            }
            //33% heal effectiveness & no heals under 2 hearts (no natural regen/regen potions)
            else if (level == 2) {
                if (amount >= 4)
                    amount *= 0.33;
                else {
                    amount = 0;
                    event.setCanceled(true);
                }
            }
            //10% heal effectiveness
            else {
                if (amount >= 4)
                    amount *= 0.1;
                else {
                    amount = 0;
                    event.setCanceled(true);
                }
            }
            event.amount = amount;
        }
    }
}
