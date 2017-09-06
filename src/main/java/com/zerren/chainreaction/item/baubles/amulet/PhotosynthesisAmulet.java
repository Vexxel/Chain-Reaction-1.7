package com.zerren.chainreaction.item.baubles.amulet;

import baubles.api.BaubleType;
import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.core.tick.SetBonusHandler;
import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.item.baubles.BaubleCore;
import com.zerren.chainreaction.item.baubles.SetBonus;
import com.zerren.chainreaction.reference.Reference;
import com.zerren.chainreaction.utility.BaubleHelper;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

/**
 * Created by Zerren on 8/24/2017.
 */
public class PhotosynthesisAmulet extends BaubleCore {

    public PhotosynthesisAmulet() {
        rarity = EnumRarity.rare;
        type = BaubleType.AMULET;
        name = "sunAmulet";
        setBonus = SetBonus.DRUID;
    }

    @Override
    public void onEquipped(ItemStack stack, EntityLivingBase entity) {
        super.onEquipped(stack, entity);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            SetBonusHandler.setDruidSpeed(player);
        }
    }

    @Override
    public void onUnequipped(ItemStack stack, EntityLivingBase entity) {
        super.onUnequipped(stack, entity);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            SetBonusHandler.setDruidSpeed(player);
        }
    }

    @Override
    public void tick(ItemStack stack, EntityLivingBase entity) {
        super.tick(stack, entity);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            //not creative mode, not on ground
            if (!player.capabilities.isCreativeMode && isPlayerInSunlight(player) && !player.worldObj.isRemote) {
                player.getFoodStats().addStats(1, 0F);
            }
        }
    }

    private static boolean isPlayerInSunlight(EntityPlayer player) {
        boolean flag = false;
        if (player.worldObj.isDaytime()) {
            float brightness = player.getBrightness(1.0F);

            if (brightness > 0.5F && player.worldObj.rand.nextFloat() * 30.0F < (brightness - 0.4F) * 2.0F && player.worldObj.canBlockSeeTheSky(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ))) {
                flag = true;
            }
        }
        return flag;

    }
}
