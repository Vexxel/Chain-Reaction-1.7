package com.zerren.chainreaction.item.baubles.amulet;

import baubles.api.BaubleType;
import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.item.baubles.BaubleCore;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.reference.Reference;
import com.zerren.chainreaction.utility.BaubleHelper;
import com.zerren.chainreaction.utility.ItemRetriever;
import com.zerren.chainreaction.utility.NBTHelper;
import com.zerren.chainreaction.utility.TooltipHelper;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.Random;

/**
 * Created by Zerren on 8/24/2017.
 */
public class ShieldAmulet extends BaubleCore {

    public ShieldAmulet() {
        rarity = EnumRarity.rare;
        type = BaubleType.AMULET;
        name = "shieldAmulet";
        addCooldownValueInSeconds(ConfigHandler.shieldFrequency);

        //cooldownValue = " " + Math.round(ConfigHandler.shieldFrequency * 20) + " " + TooltipHelper.getSecondsTranslated();

        MinecraftForge.EVENT_BUS.register(this);

    }

    public void onEquipped(ItemStack stack, EntityLivingBase entity) {
        super.onEquipped(stack, entity);

        setCooldown(stack, 200);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onHurt(LivingAttackEvent event) {
        if (event.entityLiving instanceof EntityPlayer && event.source.getSourceOfDamage() != null) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            if (BaubleHelper.hasCorrectAmulet(player, getStack())) {

                ItemStack amulet = BaubleHelper.getAmulet(player);

                if (getCooldown(amulet) == 0) {
                    event.setCanceled(true);
                    setCooldown(amulet, ConfigHandler.shieldFrequency * 30);
                    ChainReaction.proxy.playSoundAtEntity(player.getEntityWorld(), player, Reference.Sounds.SHIELD_DISSIPATE, 1F, 1F);

                    for (int i = 0; i < 10; i++) {
                        ChainReaction.proxy.magicCritFX(player, (player.worldObj.rand.nextFloat() - 0.5), 0, (player.worldObj.rand.nextFloat() - 0.5));
                    }
                }
            }
        }
    }
}
