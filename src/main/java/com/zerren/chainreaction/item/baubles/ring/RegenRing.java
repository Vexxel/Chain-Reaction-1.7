package com.zerren.chainreaction.item.baubles.ring;

import baubles.api.BaubleType;
import com.zerren.chainreaction.core.PlayerSetBonus;
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
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

/**
 * Created by Zerren on 8/25/2017.
 */
public class RegenRing extends BaubleCore {

    public RegenRing() {
        rarity = EnumRarity.uncommon;
        type = BaubleType.RING;
        name = "regenRing";
    }

    public void tick(ItemStack ring, EntityLivingBase entity) {
        super.tick(ring, entity);

        if (getCooldown(ring) <= 0) {
            entity.heal(1F);
            setCooldown(ring, ConfigHandler.regenFrequency * 20);
        }
        //System.out.println(getCooldown(ring));
    }

    public void onEquipped(ItemStack stack, EntityLivingBase entity) {
        super.onEquipped(stack, entity);

        tick(stack, entity);
    }
}
