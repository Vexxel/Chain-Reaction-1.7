package com.zerren.zedeng.core.potion;

import com.zerren.zedeng.reference.Reference;
import com.zerren.zedeng.reference.Textures;
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
     * @param p1 Potion ID
     * @param p2 Bad Effect?
     * @param p3 Color
     */
    public PotionZE(int p1, String name, boolean p2, int p3, int ssX, int ssY) {
        super(p1, p2, p3);
        this.setIconIndex(ssX, ssY);
        this.setPotionName(Reference.MOD_ID.toLowerCase() + ".potion." + name);
    }

    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex() {
        Minecraft.getMinecraft().getTextureManager().bindTexture(Textures.guis.POTIONS);
        return super.getStatusIconIndex();
    }
}