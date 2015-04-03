package com.zerren.zedeng.core.potion;

import com.zerren.zedeng.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

/**
 * Created by Zerren on 3/8/2015.
 */
public class PotionZE extends Potion {

    /**
     *
     * @param id Potion ID
     * @param isBad Bad Effect?
     * @param color Color
     */
    public PotionZE(int id, String name, boolean isBad, int color, int index) {
        super(id, isBad, color);
        this.setPotionName(Reference.ModInfo.MOD_ID.toLowerCase() + ".potion." + name);
        this.setIconIndex(index % 8, index / 8);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex() {
        Minecraft.getMinecraft().renderEngine.bindTexture(Reference.Textures.GUIs.POTIONS);
        return super.getStatusIconIndex();
    }

    public boolean hasEffect(EntityLivingBase entity) {
        return hasEffect(entity, this);
    }

    public boolean hasEffect(EntityLivingBase entity, Potion potion) {
        return entity.getActivePotionEffect(potion) != null;
    }

    public int getEffectLevel(EntityLivingBase entity, Potion potion) {
        return entity.getActivePotionEffect(potion).getAmplifier();
    }

    public int getEffectLevel(EntityLivingBase entity) {
        return entity.getActivePotionEffect(this).getAmplifier();
    }

    public int getEffectTicks(EntityLivingBase entity) {
        return entity.getActivePotionEffect(this).getDuration();
    }

    public int getEffectTicks(EntityLivingBase entity, Potion potion) {
        return entity.getActivePotionEffect(potion).getDuration();
    }

    public void performEffect(EntityLivingBase target, int p2) {

    }
}