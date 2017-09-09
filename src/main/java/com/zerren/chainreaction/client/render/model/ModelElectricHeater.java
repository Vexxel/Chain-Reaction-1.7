package com.zerren.chainreaction.client.render.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * Created by Zerren on 4/13/2015.
 */
@SideOnly(Side.CLIENT)
public class ModelElectricHeater extends ModelBase {


    //fields
    ModelRenderer Base;
    ModelRenderer inputPlate;
    ModelRenderer heatPlate;
    ModelRenderer heaterBody;
    ModelRenderer heatBody;
    ModelRenderer coil1;
    ModelRenderer coil2;
    ModelRenderer coil3;
    ModelRenderer coil4;
    ModelRenderer coil5;

    public ModelElectricHeater()
    {
        textureWidth = 64;
        textureHeight = 64;

        Base = new ModelRenderer(this, 0, 0);
        Base.addBox(0F, 0F, 0F, 16, 1, 16);
        Base.setRotationPoint(-8F, 23F, -8F);
        Base.setTextureSize(64, 64);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);
        inputPlate = new ModelRenderer(this, 0, 51);
        inputPlate.addBox(0F, 0F, 0F, 8, 11, 1);
        inputPlate.setRotationPoint(-4F, 12F, -8F);
        inputPlate.setTextureSize(64, 64);
        inputPlate.mirror = true;
        setRotation(inputPlate, 0F, 0F, 0F);
        heatPlate = new ModelRenderer(this, 0, 38);
        heatPlate.addBox(0F, 0F, 0F, 12, 12, 1);
        heatPlate.setRotationPoint(-6F, 10F, 7F);
        heatPlate.setTextureSize(64, 64);
        heatPlate.mirror = true;
        setRotation(heatPlate, 0F, 0F, 0F);
        heaterBody = new ModelRenderer(this, 23, 43);
        heaterBody.addBox(0F, 0F, 0F, 10, 10, 10);
        heaterBody.setRotationPoint(-5F, 11F, -7F);
        heaterBody.setTextureSize(64, 64);
        heaterBody.mirror = true;
        setRotation(heaterBody, 0F, 0F, 0F);
        heatBody = new ModelRenderer(this, 0, 18);
        heatBody.addBox(0F, 0F, 0F, 14, 15, 4);
        heatBody.setRotationPoint(-7F, 8F, 3F);
        heatBody.setTextureSize(64, 64);
        heatBody.mirror = true;
        setRotation(heatBody, 0F, 0F, 0F);
        coil1 = new ModelRenderer(this, 37, 18);
        coil1.addBox(-6F, 0F, -0.5F, 12, 12, 1);
        coil1.setRotationPoint(0F, 10F, -5F);
        coil1.setTextureSize(64, 64);
        coil1.mirror = true;
        setRotation(coil1, 0F, -0.0872665F, 0F);
        coil2 = new ModelRenderer(this, 37, 18);
        coil2.addBox(-6F, 0F, -0.5F, 12, 12, 1);
        coil2.setRotationPoint(0F, 10F, -3F);
        coil2.setTextureSize(64, 64);
        coil2.mirror = true;
        setRotation(coil2, 0F, -0.0872665F, 0F);
        coil3 = new ModelRenderer(this, 37, 18);
        coil3.addBox(-6F, 0F, -0.5F, 12, 12, 1);
        coil3.setRotationPoint(0F, 10F, -1F);
        coil3.setTextureSize(64, 64);
        coil3.mirror = true;
        setRotation(coil3, 0F, -0.0872665F, 0F);
        coil4 = new ModelRenderer(this, 37, 18);
        coil4.addBox(-5F, 0F, -0.5F, 12, 12, 1);
        coil4.setRotationPoint(-1F, 10F, 1F);
        coil4.setTextureSize(64, 64);
        coil4.mirror = true;
        setRotation(coil4, 0F, -0.0872665F, 0F);
        coil5 = new ModelRenderer(this, 37, 18);
        coil5.addBox(-5F, 0F, -0.5F, 12, 12, 1);
        coil5.setRotationPoint(-1F, 10F, 3F);
        coil5.setTextureSize(64, 64);
        coil5.mirror = true;
        setRotation(coil5, 0F, 0F, 0F);
    }

    public void render() {
        this.Base.render(0.0625F);
        this.inputPlate.render(0.0625F);

        this.heatPlate.render(0.0625F);
        this.heaterBody.render(0.0625F);
        this.heatBody.render(0.0625F);

        this.coil1.render(0.0625F);
        this.coil2.render(0.0625F);
        this.coil3.render(0.0625F);
        this.coil4.render(0.0625F);
        this.coil5.render(0.0625F);

    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
