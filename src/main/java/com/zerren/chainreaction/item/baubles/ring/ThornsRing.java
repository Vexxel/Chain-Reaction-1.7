package com.zerren.chainreaction.item.baubles.ring;

import baubles.api.BaubleType;
import com.zerren.chainreaction.core.DamageSourceCR;
import com.zerren.chainreaction.core.tick.SetBonusHandler;
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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

/**
 * Created by Zerren on 8/24/2017.
 */
public class ThornsRing extends BaubleCore {

    public ThornsRing() {
        rarity = EnumRarity.uncommon;
        type = BaubleType.RING;
        name = "thornsRing";
        setBonus = SetBonus.DRUID;
        extraTooltipValue = " +" + Math.round(ConfigHandler.thornsModifier * 100) + "%";

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onEquipped(ItemStack stack, EntityLivingBase entity) {
        super.onEquipped(stack, entity);

        //System.out.println("Load running");

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

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onHurt(LivingHurtEvent event) {
        if (event.entity instanceof EntityPlayer && event.source.getEntity() != null) {
            if (event.source.getEntity() instanceof EntityLivingBase && event.source.getEntity() != event.entity) {
                EntityPlayer player = (EntityPlayer) event.entity;
                EntityLivingBase attacker = (EntityLivingBase) event.source.getEntity();

                if (BaubleHelper.hasCorrectRing(player, ItemRetriever.Items.bauble(name))) {
                    reflectDamagePortion(event, player, attacker);
                }
            }
        }
    }

    private void reflectDamagePortion(LivingHurtEvent event, EntityPlayer player, EntityLivingBase attacker) {
        ItemStack[] rings = BaubleHelper.getRings(player);

        int ringsEquipped = 0;

        for (ItemStack ring : rings) {
            if (ring != null && ring.isItemEqual(ItemRetriever.Items.bauble(name))) {
                if (getCooldown(ring) <= 0) {
                    setCooldown(ring, 5);
                    ringsEquipped++;
                }
            }
        }
        if (ringsEquipped > 0) {
            float reflect = event.ammount * ConfigHandler.thornsModifier * ringsEquipped;
            if (reflect < 1) reflect = 1F;

            attacker.attackEntityFrom(DamageSourceCR.THORNS, reflect);
            System.out.println("Hit for: "+ event.ammount);
            System.out.println("Reflected: " + reflect);
            //System.out.println(event.ammount);

        }
    }
}
