package com.zerren.chainreaction.item.baubles.ring;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.zerren.chainreaction.core.PlayerSetBonus;
import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.item.baubles.BaubleCore;
import com.zerren.chainreaction.item.baubles.SetBonus;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.utility.*;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;

/**
 * Created by Zerren on 8/25/2017.
 */
public class PowerRing extends BaubleCore {

    private Multimap<String, AttributeModifier> attributes = HashMultimap.create();
    //private static final AttributeModifier powerBonus = (new AttributeModifier(Names.UUIDs.POWER_RING_BONUS_UUID, Names.UUIDs.POWER_RING_BONUS_NAME, 1.2, 1));


    public PowerRing() {
        rarity = EnumRarity.rare;
        type = BaubleType.RING;
        name = "powerRing";
        extraTooltipValue = " +" + Math.round(ConfigHandler.powerModifier * 100) + "%";

        //attributes.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), powerBonus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onEquipped(ItemStack stack, EntityLivingBase entity) {
        super.onEquipped(stack, entity);

        //entity.getAttributeMap().applyAttributeModifiers(attributes);
    }

    public void onUnequipped(ItemStack stack, EntityLivingBase entity) {
        super.onUnequipped(stack, entity);

        //entity.getAttributeMap().removeAttributeModifiers(attributes);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onHurt(LivingHurtEvent event) {
        if (event.source.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.source.getEntity();
            if (BaubleHelper.hasCorrectRing(player, ItemRetriever.Items.bauble(name))) {

                amplifyDamageOnce(event, player);
            }
        }
    }


    private void amplifyDamageOnce(LivingHurtEvent event, EntityPlayer player) {
        ItemStack[] rings = BaubleHelper.getRings(player);

        int ringsEquipped = 0;
        float original = event.ammount;

        for (ItemStack ring : rings) {
            if (ring != null && ring.isItemEqual(ItemRetriever.Items.bauble(name))) {
                if (getCooldown(ring) == 0) {
                    setCooldown(ring, 5);
                    ringsEquipped++;
                }
            }
        }
        if (ringsEquipped > 0) {
            event.ammount = (float)CRMath.increasedByPercent(event.ammount, ConfigHandler.powerModifier * ringsEquipped);
        }
    }
}
