package com.zerren.zedeng.client.render.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * Created by Zerren on 3/31/2015.
 */
@SideOnly(Side.CLIENT)
public class ModelExchangerTubes extends ModelBase {

    ModelRenderer Plate2;
    ModelRenderer Plate1;
    ModelRenderer Tubes;
    ModelRenderer Rod4;
    ModelRenderer Rod3;
    ModelRenderer Rod2;
    ModelRenderer Rod1;

    public ModelExchangerTubes() {
        textureWidth = 64;
        textureHeight = 64;

        Plate2 = new ModelRenderer(this, 0, 0);
        Plate2.addBox(0F, 0F, 0F, 16, 16, 1);
        Plate2.setRotationPoint(-8F, 8F, -7F);
        Plate2.setTextureSize(64, 64);

        Plate1 = new ModelRenderer(this, 0, 0);
        Plate1.addBox(0F, 0F, 0F, 16, 16, 1);
        Plate1.setRotationPoint(8F, 8F, 7F);
        Plate1.setTextureSize(64, 64);
        Plate1.rotateAngleY = 3.141592F;

        Tubes = new ModelRenderer(this, 0, 21);
        Tubes.addBox(0F, 0F, 0F, 10, 10, 16);
        Tubes.setRotationPoint(-5F, 11F, -8F);
        Tubes.setTextureSize(64, 64);

        Rod4 = new ModelRenderer(this, 0, 18);
        Rod4.addBox(0F, 0F, 0F, 16, 1, 1);
        Rod4.setRotationPoint(6F, 22F, 8F);
        Rod4.setTextureSize(64, 64);
        Rod4.rotateAngleY = 1.570796F;

        Rod3 = new ModelRenderer(this, 0, 18);
        Rod3.addBox(0F, 0F, 0F, 16, 1, 1);
        Rod3.setRotationPoint(-7F, 22F, 8F);
        Rod3.setTextureSize(64, 64);
        Rod3.rotateAngleY = 1.570796F;

        Rod2 = new ModelRenderer(this, 0, 18);
        Rod2.addBox(0F, 0F, 0F, 16, 1, 1);
        Rod2.setRotationPoint(-7F, 9F, 8F);
        Rod2.setTextureSize(64, 64);
        Rod2.rotateAngleY = 1.570796F;

        Rod1 = new ModelRenderer(this, 0, 18);
        Rod1.addBox(0F, 0F, 0F, 16, 1, 1);
        Rod1.setRotationPoint(6F, 9F, 8F);
        Rod1.setTextureSize(64, 64);
        Rod1.rotateAngleY = 1.570796F;
    }

    public void renderAll() {
        Plate1.render(0.0625F);
        Plate2.render(0.0625F);
        Tubes.render(0.0625F);
        Rod1.render(0.0625F);
        Rod2.render(0.0625F);
        Rod3.render(0.0625F);
        Rod4.render(0.0625F);
    }
}
