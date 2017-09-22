package com.zerren.chainreaction.client.render.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * Created by Zerren on 4/13/2015.
 */
@SideOnly(Side.CLIENT)
public class ModelLiquifier extends ModelBase {

    ModelRenderer base;
    ModelRenderer outputSupport;
    ModelRenderer outputPlate;
    ModelRenderer baseUpper;
    ModelRenderer inputSupport;
    ModelRenderer inputPlate;
    ModelRenderer inputTank;
    ModelRenderer compressor;
    ModelRenderer outputTank;
    ModelRenderer topPlate;
    ModelRenderer energyChannel;
    ModelRenderer pipe;
    ModelRenderer energyPlate;
    ModelRenderer rodFR;
    ModelRenderer rodBR;
    ModelRenderer rodFL;
    ModelRenderer rodBL;
    ModelRenderer gauge;

    public ModelLiquifier()
    {
        textureWidth = 128;
        textureHeight = 64;

        base = new ModelRenderer(this, 0, 0);
        base.addBox(0F, 0F, 0F, 16, 1, 16);
        base.setRotationPoint(-8F, 23F, -8F);
        base.setTextureSize(128, 64);
        base.mirror = true;
        setRotation(base, 0F, 0F, 0F);
        outputSupport = new ModelRenderer(this, 41, 47);
        outputSupport.addBox(0F, 0F, 0F, 1, 8, 8);
        outputSupport.setRotationPoint(-6F, 12F, -4F);
        outputSupport.setTextureSize(128, 64);
        outputSupport.mirror = true;
        setRotation(outputSupport, 0F, 0F, 0F);
        outputPlate = new ModelRenderer(this, 65, 0);
        outputPlate.addBox(0F, 0F, 0F, 2, 10, 10);
        outputPlate.setRotationPoint(-8F, 11F, -5F);
        outputPlate.setTextureSize(128, 64);
        outputPlate.mirror = true;
        setRotation(outputPlate, 0F, 0F, 0F);
        baseUpper = new ModelRenderer(this, 0, 18);
        baseUpper.addBox(0F, 0F, 0F, 14, 3, 14);
        baseUpper.setRotationPoint(-7F, 20F, -7F);
        baseUpper.setTextureSize(128, 64);
        baseUpper.mirror = true;
        setRotation(baseUpper, 0F, 0F, 0F);
        inputSupport = new ModelRenderer(this, 41, 47);
        inputSupport.addBox(0F, 0F, 0F, 1, 8, 8);
        inputSupport.setRotationPoint(5F, 12F, -4F);
        inputSupport.setTextureSize(128, 64);
        inputSupport.mirror = true;
        setRotation(inputSupport, 0F, 0F, 0F);
        inputPlate = new ModelRenderer(this, 65, 0);
        inputPlate.addBox(0F, 0F, 0F, 2, 10, 10);
        inputPlate.setRotationPoint(6F, 11F, -5F);
        inputPlate.setTextureSize(128, 64);
        inputPlate.mirror = true;
        setRotation(inputPlate, 0F, 0F, 0F);
        inputTank = new ModelRenderer(this, 0, 49);
        inputTank.addBox(0F, 0F, 0F, 5, 10, 5);
        inputTank.setRotationPoint(0F, 10F, -5F);
        inputTank.setTextureSize(128, 64);
        inputTank.mirror = true;
        setRotation(inputTank, 0F, 0F, 0F);
        compressor = new ModelRenderer(this, 0, 36);
        compressor.addBox(0F, 0F, 0F, 10, 5, 6);
        compressor.setRotationPoint(-5F, 15F, 0F);
        compressor.setTextureSize(128, 64);
        compressor.mirror = true;
        setRotation(compressor, 0F, 0F, 0F);
        outputTank = new ModelRenderer(this, 20, 50);
        outputTank.addBox(0F, 0F, 0F, 5, 9, 5);
        outputTank.setRotationPoint(-5F, 11F, -2F);
        outputTank.setTextureSize(128, 64);
        outputTank.mirror = true;
        setRotation(outputTank, 0F, 0F, 0F);
        topPlate = new ModelRenderer(this, 0, 0);
        topPlate.addBox(0F, 0F, 0F, 16, 1, 16);
        topPlate.setRotationPoint(-8F, 8F, -8F);
        topPlate.setTextureSize(128, 64);
        topPlate.mirror = true;
        setRotation(topPlate, 0F, 0F, 0F);
        energyChannel = new ModelRenderer(this, 57, 35);
        energyChannel.addBox(0F, 0F, 0F, 6, 7, 4);
        energyChannel.setRotationPoint(-3F, 13F, -6F);
        energyChannel.setTextureSize(128, 64);
        energyChannel.mirror = true;
        setRotation(energyChannel, 0F, 0F, 0F);
        pipe = new ModelRenderer(this, 36, 37);
        pipe.addBox(0F, 0F, 0F, 3, 2, 5);
        pipe.setRotationPoint(1F, 13F, -0.1F);
        pipe.setTextureSize(128, 64);
        pipe.mirror = true;
        setRotation(pipe, 0F, 0F, 0F);
        energyPlate = new ModelRenderer(this, 57, 21);
        energyPlate.addBox(0F, 0F, 0F, 8, 11, 2);
        energyPlate.setRotationPoint(-4F, 12F, -8F);
        energyPlate.setTextureSize(128, 64);
        energyPlate.mirror = true;
        setRotation(energyPlate, 0F, 0F, 0F);
        rodFR = new ModelRenderer(this, 78, 21);
        rodFR.addBox(0F, 0F, 0F, 1, 14, 1);
        rodFR.setRotationPoint(-8F, 9F, 7F);
        rodFR.setTextureSize(128, 64);
        rodFR.mirror = true;
        setRotation(rodFR, 0F, 0F, 0F);
        rodBR = new ModelRenderer(this, 78, 21);
        rodBR.addBox(0F, 0F, 0F, 1, 14, 1);
        rodBR.setRotationPoint(-8F, 9F, -8F);
        rodBR.setTextureSize(128, 64);
        rodBR.mirror = true;
        setRotation(rodBR, 0F, 0F, 0F);
        rodFL = new ModelRenderer(this, 78, 21);
        rodFL.addBox(0F, 0F, 0F, 1, 14, 1);
        rodFL.setRotationPoint(7F, 9F, 7F);
        rodFL.setTextureSize(128, 64);
        rodFL.mirror = true;
        setRotation(rodFL, 0F, 0F, 0F);
        rodBL = new ModelRenderer(this, 78, 21);
        rodBL.addBox(0F, 0F, 0F, 1, 14, 1);
        rodBL.setRotationPoint(7F, 9F, -8F);
        rodBL.setTextureSize(128, 64);
        rodBL.mirror = true;
        setRotation(rodBL, 0F, 0F, 0F);
        gauge = new ModelRenderer(this, 62, 47);
        gauge.addBox(0F, 0F, 0F, 3, 3, 1);
        gauge.setRotationPoint(-4F, 16F, 5.7F);
        gauge.setTextureSize(128, 64);
        gauge.mirror = true;
        setRotation(gauge, 0F, 0F, 0F);
    }

    public void render() {
        this.base.render(0.0625F);
        this.outputSupport.render(0.0625F);
        this.outputPlate.render(0.0625F);
        this.baseUpper.render(0.0625F);
        this.inputSupport.render(0.0625F);
        this.inputPlate.render(0.0625F);
        this.inputTank.render(0.0625F);
        this.compressor.render(0.0625F);
        this.outputTank.render(0.0625F);
        this.topPlate.render(0.0625F);
        this.energyChannel.render(0.0625F);
        this.pipe.render(0.0625F);
        this.energyPlate.render(0.0625F);
        this.rodFR.render(0.0625F);
        this.rodBR.render(0.0625F);
        this.rodFL.render(0.0625F);
        this.rodBL.render(0.0625F);
        this.gauge.render(0.0625F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
