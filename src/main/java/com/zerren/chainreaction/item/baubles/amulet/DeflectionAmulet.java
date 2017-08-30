package com.zerren.chainreaction.item.baubles.amulet;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.item.baubles.BaubleCore;
import com.zerren.chainreaction.item.baubles.SetBonus;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.utility.BaubleHelper;
import com.zerren.chainreaction.utility.ItemRetriever;
import com.zerren.chainreaction.utility.NBTHelper;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.Random;

/**
 * Created by Zerren on 8/24/2017.
 */
public class DeflectionAmulet extends BaubleCore {

    public DeflectionAmulet() {
        rarity = EnumRarity.uncommon;
        type = BaubleType.AMULET;
        name = "deflectionAmulet";
        setBonus = SetBonus.GUARDIAN;
        extraTooltipValue = " +" + Math.round(ConfigHandler.deflectionChance * 100) + "%";


        MinecraftForge.EVENT_BUS.register(this);

    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onAttacked(LivingAttackEvent event) {
        if (event.source.getSourceOfDamage() instanceof IProjectile && event.entity instanceof EntityPlayer) {

            EntityPlayer playerHit = (EntityPlayer) event.entityLiving;

            if (BaubleHelper.hasCorrectAmulet(playerHit, ItemRetriever.Items.bauble("deflectionAmulet"))) {
                if (playerHit.worldObj.rand.nextFloat() <= ConfigHandler.deflectionChance) {
                    tryNegateProjectileOnce(event, playerHit, true);
                }
                else {
                    tryNegateProjectileOnce(event, playerHit, false);
                }
            }
        }
    }

    private void tryNegateProjectileOnce(LivingAttackEvent event, EntityPlayer player, boolean negate) {
        ItemStack amulet = BaubleHelper.getAmulet(player);

        if (amulet != null) {
            //System.out.println("1");
            if (NBTHelper.getShort(amulet, Names.NBT.BAUBLE_COOLDOWN) == 0) {
                NBTHelper.setShort(amulet, Names.NBT.BAUBLE_COOLDOWN, (short)5);

                //System.out.println(negate ? "Arrow deflected!" : "Failed to deflect!");
                event.setCanceled(negate);
            }
        }
    }
}
