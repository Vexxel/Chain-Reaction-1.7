package com.zerren.chainreaction.item.baubles;

import baubles.api.BaubleType;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 8/24/2017.
 */
public class FireResistanceAmulet extends BaubleCore {


    public FireResistanceAmulet() {
        rarity = EnumRarity.uncommon;
        type = BaubleType.AMULET;
        name = "fireResistanceAmulet";
    }

    public void tick(ItemStack stack, EntityLivingBase entity) {
        super.tick(stack, entity);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;

            if (!player.isImmuneToFire())
                setFireImmunity(player, true);
        }
    }

    public void onEquipped(ItemStack stack, EntityLivingBase entity) {
        super.onEquipped(stack, entity);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            setFireImmunity(player, true);
        }
    }

    public void onUnequipped(ItemStack stack, EntityLivingBase entity) {
        super.onUnequipped(stack, entity);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            setFireImmunity(player, false);
        }
    }

    private void setFireImmunity(Entity entity, boolean immunity) {
        ReflectionHelper.setPrivateValue(Entity.class, entity, immunity, "isImmuneToFire", "field_70178_ae", "ag");
    }

}
