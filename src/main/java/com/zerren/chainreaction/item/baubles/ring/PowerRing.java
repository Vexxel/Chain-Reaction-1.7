package com.zerren.chainreaction.item.baubles.ring;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import com.zerren.chainreaction.core.PlayerSetBonus;
import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.item.baubles.BaubleCore;
import com.zerren.chainreaction.item.baubles.SetBonus;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.utility.*;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
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

    public PowerRing() {
        rarity = EnumRarity.uncommon;
        type = BaubleType.RING;
        name = "powerRing";
        setBonus = SetBonus.SKULLFIRE;
        extraTooltipValue = " +" + Math.round(ConfigHandler.powerModifier * 100) + "%";

        MinecraftForge.EVENT_BUS.register(this);
    }


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onHurt(LivingHurtEvent event) {
        if (event.source.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.source.getEntity();

            if (BaubleHelper.hasCorrectRing(player, ItemRetriever.Items.bauble("powerRing"))) {
                //System.out.println("Equipped");
                amplifyDamageOnce(event, player);
            }
        }
    }


    private void amplifyDamageOnce(LivingHurtEvent event, EntityPlayer player) {
        ItemStack ring1 = BaublesApi.getBaubles(player).getStackInSlot(1);
        ItemStack ring2 = BaublesApi.getBaubles(player).getStackInSlot(2);

        int ringsEquipped = 0;
        float original = event.ammount;

        if (ring1 != null && ring1.isItemEqual(ItemRetriever.Items.bauble("powerRing"))) {
            //System.out.println("1");
            if (NBTHelper.getShort(ring1, Names.NBT.BAUBLE_COOLDOWN) == 0) {
                NBTHelper.setShort(ring1, Names.NBT.BAUBLE_COOLDOWN, (short)5);
                ringsEquipped++;
            }
        }
        if (ring2 != null && ring2.isItemEqual(ItemRetriever.Items.bauble("powerRing"))) {
            if (NBTHelper.getShort(ring2, Names.NBT.BAUBLE_COOLDOWN) == 0) {
                NBTHelper.setShort(ring2, Names.NBT.BAUBLE_COOLDOWN, (short)5);
                ringsEquipped++;
            }
        }
        if (ringsEquipped > 0) {
            event.ammount = original * (1 + (ConfigHandler.powerModifier * ringsEquipped));
        }
    }

    public void onEquipped(ItemStack stack, EntityLivingBase entity) {
        super.onEquipped(stack, entity);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;

            if (BaubleHelper.hasCorrectAmulet(player, ItemRetriever.Items.bauble("witherAmulet"))) {
                PlayerSetBonus bonus = PlayerSetBonus.get(player);
                bonus.setSkullfire(true);
                //System.out.println("Skullfire: " + bonus.getSkullfire());
            }
        }

    }

    public void onUnequipped(ItemStack stack, EntityLivingBase entity) {
        super.onUnequipped(stack, entity);
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;

            PlayerSetBonus bonus = PlayerSetBonus.get(player);
            bonus.setSkullfire(false);
            //System.out.println("Skullfire: " + bonus.getSkullfire());
        }
    }
}
