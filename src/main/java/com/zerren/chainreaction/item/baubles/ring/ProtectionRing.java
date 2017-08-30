package com.zerren.chainreaction.item.baubles.ring;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import com.zerren.chainreaction.core.DamageSourceCR;
import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.item.baubles.BaubleCore;
import com.zerren.chainreaction.item.baubles.SetBonus;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.utility.BaubleHelper;
import com.zerren.chainreaction.utility.CRMath;
import com.zerren.chainreaction.utility.ItemRetriever;
import com.zerren.chainreaction.utility.NBTHelper;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

/**
 * Created by Zerren on 8/25/2017.
 */
public class ProtectionRing extends BaubleCore {

    public ProtectionRing() {
        rarity = EnumRarity.uncommon;
        type = BaubleType.RING;
        name = "protectionRing";
        extraTooltipValue = " +" + Math.round(ConfigHandler.protectionModifier * 100) + "%";

        MinecraftForge.EVENT_BUS.register(this);
    }


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onHurt(LivingHurtEvent event) {
        if (event.entity instanceof EntityPlayer && event.source.getEntity() != null && DamageSourceCR.canResist(event.source)) {
            EntityPlayer player = (EntityPlayer) event.entity;
            if (BaubleHelper.hasCorrectRing(player, ItemRetriever.Items.bauble(name))) {

                reduceDamageOnce(event, player);
            }
        }
    }


    private void reduceDamageOnce(LivingHurtEvent event, EntityPlayer player) {
        ItemStack[] rings = BaubleHelper.getRings(player);

        int ringsEquipped = 0;

        for (ItemStack ring : rings) {
            if (ring != null && ring.isItemEqual(ItemRetriever.Items.bauble(name))) {
                if (NBTHelper.getShort(ring, Names.NBT.BAUBLE_COOLDOWN) == 0) {
                    NBTHelper.setShort(ring, Names.NBT.BAUBLE_COOLDOWN, (short)5);
                    ringsEquipped++;
                }
            }
        }
        if (ringsEquipped > 0) {
            event.ammount = (float)CRMath.reducedByPercent(event.ammount, ConfigHandler.protectionModifier * ringsEquipped);
            //System.out.println(event.ammount);
        }
    }
}
