package com.zerren.chainreaction.item.baubles.ring;

import baubles.api.BaubleType;
import com.zerren.chainreaction.core.PlayerSetBonus;
import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.item.baubles.BaubleCore;
import com.zerren.chainreaction.utility.BaubleHelper;
import com.zerren.chainreaction.utility.CoreUtility;
import com.zerren.chainreaction.utility.ItemRetriever;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

/**
 * Created by Zerren on 8/24/2017.
 */
public class FlightRing extends BaubleCore {

    public FlightRing() {
        rarity = EnumRarity.epic;
        type = BaubleType.RING;
        name = "flightRing";
    }

    public void onEquipped(ItemStack stack, EntityLivingBase entity) {
        super.onEquipped(stack, entity);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;

            player.capabilities.allowFlying = true;
            player.sendPlayerAbilities();
        }

    }

    public void onUnequipped(ItemStack stack, EntityLivingBase entity) {
        super.onUnequipped(stack, entity);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;

            if (!player.capabilities.isCreativeMode) {
                player.capabilities.allowFlying = false;
            }
            player.sendPlayerAbilities();
        }
    }
}
