package com.zerren.chainreaction.item.tool;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.zerren.chainreaction.item.ItemCRBase;
import com.zerren.chainreaction.item.baubles.*;
import com.zerren.chainreaction.item.baubles.amulet.*;
import com.zerren.chainreaction.item.baubles.belt.JumpBelt;
import com.zerren.chainreaction.item.baubles.belt.KnockbackBelt;
import com.zerren.chainreaction.item.baubles.belt.SpeedBelt;
import com.zerren.chainreaction.item.baubles.ring.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Zerren on 8/24/2017.
 */
public class ItemBaubleCR extends ItemCRBase implements IBauble {

    public ItemBaubleCR(String name, String[] subtypes, String folder, CreativeTabs tab) {
        super(name, subtypes, 1, folder, tab);

    }

    public static BaubleCore getBauble(ItemStack stack) {
        if (stack != null) {
            switch (stack.getItemDamage()) {
                case 0: return new FallDamageAmulet();
                case 1: return new HasteRing();
                case 2: return new UnbreakingRing();
                case 3: return new FireResistanceAmulet();
                case 4: return new WitherAmulet();
                case 5: return new WaterBreathingRing();
                case 6: return new PowerRing();
                case 7: return new HealthAmulet();
                case 8: return new SpeedBelt();
                case 9: return new DeflectionAmulet();
                case 10: return new KnockbackBelt();
                case 11: return new ProtectionRing();
                case 12: return new JumpBelt();

                default: return new BaubleCore();
            }
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (stack != null) {
            getBauble(stack).addTooltip(player, list, par4);
        }
    }

    @Override
    public BaubleType getBaubleType(ItemStack stack) {
        if (stack != null) {
            return getBauble(stack).getType();
        }
        return null;
    }

    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase player) {
        if (stack != null && player != null)
            getBauble(stack).tick(stack, player);
    }

    @Override
    public void onEquipped(ItemStack stack, EntityLivingBase player) {
        if (stack != null && player != null)
            getBauble(stack).onEquipped(stack, player);
    }

    @Override
    public void onUnequipped(ItemStack stack, EntityLivingBase player) {
        if (stack != null && player != null)
            getBauble(stack).onUnequipped(stack, player);
    }

    @Override
    public boolean canEquip(ItemStack stack, EntityLivingBase player) {
        return stack != null && player != null && getBauble(stack).canEquip(stack, player);
    }

    @Override
    public boolean canUnequip(ItemStack stack, EntityLivingBase player) {
        return stack != null && player != null && getBauble(stack).canUnequip(stack, player);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        if (stack != null) {
            return getBauble(stack).getRarity();
        }
        return EnumRarity.common;
    }
}
