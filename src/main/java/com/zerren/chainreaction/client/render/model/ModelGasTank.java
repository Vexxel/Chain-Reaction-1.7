package com.zerren.chainreaction.client.render.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * Created by Zerren on 4/13/2015.
 */
@SideOnly(Side.CLIENT)
public class ModelGasTank extends ModelBase {

    public ModelRenderer Tank;
    public ModelRenderer IOTop;
    public ModelRenderer IOBottom;
    public ModelRenderer PlateTop;
    public ModelRenderer PlateBottom;
    public ModelRenderer Rod1;
    public ModelRenderer Rod2;
    public ModelRenderer Rod3;
    public ModelRenderer Rod4;
    public ModelRenderer Needle = (new ModelRenderer(this, 4, 0)).setTextureSize(64, 64);

    public ModelGasTank() {
        textureWidth = 64;
        textureHeight = 64;

        Tank = new ModelRenderer(this, 0, 0);
        Tank.addBox(0F, 0F, 0F, 14, 12, 14);
        Tank.setRotationPoint(-7F, 10F, -7F);
        Tank.setTextureSize(64, 64);

        IOTop = new ModelRenderer(this, 0, 43);
        IOTop.addBox(0F, 0F, 0F, 10, 1, 10);
        IOTop.setRotationPoint(-5F, 8F, -5F);
        IOTop.setTextureSize(64, 64);

        IOBottom = new ModelRenderer(this, 0, 43);
        IOBottom.addBox(0F, 0F, 0F, 10, 1, 10);
        IOBottom.setRotationPoint(-5F, 23F, -5F);
        IOBottom.setTextureSize(64, 64);

        PlateTop = new ModelRenderer(this, 0, 26);
        PlateTop.addBox(0F, 0F, 0F, 16, 1, 16);
        PlateTop.setRotationPoint(-8F, 9F, -8F);
        PlateTop.setTextureSize(64, 64);

        PlateBottom = new ModelRenderer(this, 0, 26);
        PlateBottom.addBox(0F, 0F, 0F, 16, 1, 16);
        PlateBottom.setRotationPoint(-8F, 22F, -8F);
        PlateBottom.setTextureSize(64, 64);

        Rod1 = new ModelRenderer(this, 0, 0);
        Rod1.addBox(0F, 0F, 0F, 1, 12, 1);
        Rod1.setRotationPoint(7F, 10F, -8F);
        Rod1.setTextureSize(64, 64);

        Rod2 = new ModelRenderer(this, 0, 0);
        Rod2.addBox(0F, 0F, 0F, 1, 12, 1);
        Rod2.setRotationPoint(7F, 10F, 7F);
        Rod2.setTextureSize(64, 64);

        Rod3 = new ModelRenderer(this, 0, 0);
        Rod3.addBox(0F, 0F, 0F, 1, 12, 1);
        Rod3.setRotationPoint(-8F, 10F, 7F);
        Rod3.setTextureSize(64, 64);

        Rod4 = new ModelRenderer(this, 0, 0);
        Rod4.addBox(0F, 0F, 0F, 1, 12, 1);
        Rod4.setRotationPoint(-8F, 10F, -8F);
        Rod4.setTextureSize(64, 64);

        Needle.addBox(-0.5F, 0F, 0F, 1, 4, 1);
        Needle.setRotationPoint(0F, 16F, -7.5F);
        Needle.setTextureSize(64, 64);
    }

    public void render() {
        this.Tank.render(0.0625F);
        this.IOTop.render(0.0625F);
        this.IOBottom.render(0.0625F);
        this.PlateTop.render(0.0625F);
        this.PlateBottom.render(0.0625F);
        this.Rod1.render(0.0625F);
        this.Rod2.render(0.0625F);
        this.Rod3.render(0.0625F);
        this.Rod4.render(0.0625F);
    }

    public void renderNeedle() {
        this.Needle.render(0.0625F);
    }
}
