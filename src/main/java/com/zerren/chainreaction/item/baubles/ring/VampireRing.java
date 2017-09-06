package com.zerren.chainreaction.item.baubles.ring;

import baubles.api.BaubleType;
import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.item.baubles.BaubleCore;
import com.zerren.chainreaction.item.baubles.SetBonus;
import com.zerren.chainreaction.utility.BaubleHelper;
import com.zerren.chainreaction.utility.CRMath;
import com.zerren.chainreaction.utility.ItemRetriever;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

/**
 * Created by Zerren on 8/25/2017.
 */
public class VampireRing extends BaubleCore {

    public VampireRing() {
        rarity = EnumRarity.rare;
        type = BaubleType.RING;
        name = "vampireRing";
        setBonus = SetBonus.SKULLFIRE;
        extraTooltipValue = " +" + Math.round(ConfigHandler.vampireModifier * 100) + "%";
        addCooldownValueInSeconds(3);

        MinecraftForge.EVENT_BUS.register(this);

    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onHurt(LivingHurtEvent event) {
        if (event.source.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.source.getEntity();
            if (BaubleHelper.hasCorrectRing(player, ItemRetriever.Items.bauble(name)) && event.source.getEntity() != event.entity) {

                vampireStrikeOnce(event, player);
            }
        }
    }

    private void vampireStrikeOnce(LivingHurtEvent event, EntityPlayer player) {
        ItemStack[] rings = BaubleHelper.getRings(player);

        for (ItemStack ring : rings) {
            if (ring != null && ring.isItemEqual(ItemRetriever.Items.bauble(name))) {
                if (getCooldown(ring) == 0) {
                    setCooldown(ring, 80);
                    player.heal(event.ammount * ConfigHandler.vampireModifier);
                    System.out.println("healing for "+ event.ammount * ConfigHandler.vampireModifier);
                }
            }
        }
    }
}
