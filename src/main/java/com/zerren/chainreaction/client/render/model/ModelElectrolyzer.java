package com.zerren.chainreaction.client.render.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import scala.xml.dtd.impl.Base;

/**
 * Created by Zerren on 4/13/2015.
 */
@SideOnly(Side.CLIENT)
public class ModelElectrolyzer extends ModelBase {

    ModelRenderer base;
    ModelRenderer outputL;
    ModelRenderer outputR;
    ModelRenderer inputFluid;
    ModelRenderer chamberL;
    ModelRenderer chamberR;
    ModelRenderer bodyA;
    ModelRenderer bodyB;
    ModelRenderer bodyC;
    ModelRenderer topplate;
    ModelRenderer rodFR;
    ModelRenderer rodRL;
    ModelRenderer rodBR;
    ModelRenderer rodBL;
    ModelRenderer light1;
    ModelRenderer controlPlate;
    ModelRenderer energytower;
    ModelRenderer light2;

    public ModelElectrolyzer()
    {
        textureWidth = 128;
        textureHeight = 64;

        base = new ModelRenderer(this, 0, 0);
        base.addBox(0F, 0F, 0F, 16, 1, 16);
        base.setRotationPoint(-8F, 23F, -8F);
        base.setTextureSize(128, 64);
        base.mirror = true;
        setRotation(base, 0F, 0F, 0F);
        outputL = new ModelRenderer(this, 0, 44);
        outputL.addBox(0F, 0F, 0F, 2, 10, 10);
        outputL.setRotationPoint(-8F, 11F, -5F);
        outputL.setTextureSize(128, 64);
        outputL.mirror = true;
        setRotation(outputL, 0F, 0F, 0F);
        outputR = new ModelRenderer(this, 68, 15);
        outputR.addBox(0F, 0F, 0F, 2, 10, 10);
        outputR.setRotationPoint(6F, 11F, -5F);
        outputR.setTextureSize(128, 64);
        outputR.mirror = true;
        setRotation(outputR, 0F, 0F, 0F);
        inputFluid = new ModelRenderer(this, 25, 44);
        inputFluid.addBox(0F, 0F, 0F, 2, 10, 10);
        inputFluid.setRotationPoint(-5F, 11F, 8F);
        inputFluid.setTextureSize(128, 64);
        inputFluid.mirror = true;
        setRotation(inputFluid, 0F, 1.570796F, 0F);
        chamberL = new ModelRenderer(this, 0, 21);
        chamberL.addBox(0F, 0F, 0F, 4, 5, 4);
        chamberL.setRotationPoint(-6F, 10.5F, -4F);
        chamberL.setTextureSize(128, 64);
        chamberL.mirror = true;
        setRotation(chamberL, 0F, 0F, 0F);
        chamberR = new ModelRenderer(this, 0, 31);
        chamberR.addBox(0F, 0F, 0F, 6, 5, 6);
        chamberR.setRotationPoint(0F, 9.5F, -2F);
        chamberR.setTextureSize(128, 64);
        chamberR.mirror = true;
        setRotation(chamberR, 0F, 0F, 0F);
        bodyA = new ModelRenderer(this, 69, 45);
        bodyA.addBox(0F, 0F, 0F, 14, 6, 13);
        bodyA.setRotationPoint(-7F, 17F, -6F);
        bodyA.setTextureSize(128, 64);
        bodyA.mirror = true;
        setRotation(bodyA, 0F, 0F, 0F);
        bodyB = new ModelRenderer(this, 65, 0);
        bodyB.addBox(0F, 0F, 0F, 12, 4, 10);
        bodyB.setRotationPoint(-6F, 12.999F, -5F);
        bodyB.setTextureSize(128, 64);
        bodyB.mirror = true;
        setRotation(bodyB, 0F, 0F, 0F);
        bodyC = new ModelRenderer(this, 25, 18);
        bodyC.addBox(-4F, -4F, 0F, 8, 5, 12);
        bodyC.setRotationPoint(0F, 16F, -6F);
        bodyC.setTextureSize(128, 64);
        bodyC.mirror = true;
        setRotation(bodyC, 0F, 0F, 0F);
        topplate = new ModelRenderer(this, 0, 0);
        topplate.addBox(0F, 0F, 0F, 16, 1, 16);
        topplate.setRotationPoint(-8F, 8F, -8F);
        topplate.setTextureSize(128, 64);
        topplate.mirror = true;
        setRotation(topplate, 0F, 0F, 0F);
        rodFR = new ModelRenderer(this, 50, 38);
        rodFR.addBox(0F, 0F, 0F, 1, 14, 1);
        rodFR.setRotationPoint(-8F, 9F, -8F);
        rodFR.setTextureSize(128, 64);
        rodFR.mirror = true;
        setRotation(rodFR, 0F, 0F, 0F);
        rodRL = new ModelRenderer(this, 50, 38);
        rodRL.addBox(0F, 0F, 0F, 1, 14, 1);
        rodRL.setRotationPoint(7F, 9F, -8F);
        rodRL.setTextureSize(128, 64);
        rodRL.mirror = true;
        setRotation(rodRL, 0F, 0F, 0F);
        rodBR = new ModelRenderer(this, 50, 38);
        rodBR.addBox(0F, 0F, 0F, 1, 14, 1);
        rodBR.setRotationPoint(7F, 9F, 7F);
        rodBR.setTextureSize(128, 64);
        rodBR.mirror = true;
        setRotation(rodBR, 0F, 0F, 0F);
        rodBL = new ModelRenderer(this, 50, 38);
        rodBL.addBox(0F, 0F, 0F, 1, 14, 1);
        rodBL.setRotationPoint(-8F, 9F, 7F);
        rodBL.setTextureSize(128, 64);
        rodBL.mirror = true;
        setRotation(rodBL, 0F, 0F, 0F);
        light1 = new ModelRenderer(this, 0, 0);
        light1.addBox(0F, 0F, 0F, 2, 2, 1);
        light1.setRotationPoint(-5F, 14F, -7.6F);
        light1.setTextureSize(128, 64);
        light1.mirror = true;
        setRotation(light1, 0F, 0F, 0F);
        controlPlate = new ModelRenderer(this, 93, 15);
        controlPlate.addBox(0F, 0F, 0F, 12, 8, 1);
        controlPlate.setRotationPoint(-6F, 13F, -7F);
        controlPlate.setTextureSize(128, 64);
        controlPlate.mirror = true;
        setRotation(controlPlate, 0F, 0F, 0F);
        energytower = new ModelRenderer(this, 93, 25);
        energytower.addBox(0F, 0F, 0F, 6, 3, 6);
        energytower.setRotationPoint(-3F, 9F, -3F);
        energytower.setTextureSize(128, 64);
        energytower.mirror = true;
        setRotation(energytower, 0F, 0F, 0F);
        light2 = new ModelRenderer(this, 0, 0);
        light2.addBox(0F, 0F, 0F, 2, 2, 1);
        light2.setRotationPoint(-5F, 17F, -7.6F);
        light2.setTextureSize(128, 64);
        light2.mirror = true;
        setRotation(light2, 0F, 0F, 0F);
    }

    public void render() {
        this.base.render(0.0625F);
        this.outputL.render(0.0625F);
        this.outputR.render(0.0625F);
        this.inputFluid.render(0.0625F);
        this.chamberL.render(0.0625F);
        this.chamberR.render(0.0625F);
        this.bodyA.render(0.0625F);
        this.bodyB.render(0.0625F);
        this.bodyC.render(0.0625F);
        this.topplate.render(0.0625F);
        this.rodFR.render(0.0625F);
        this.rodRL.render(0.0625F);
        this.rodBR.render(0.0625F);
        this.rodBL.render(0.0625F);
        this.light1.render(0.0625F);
        this.light2.render(0.0625F);
        this.controlPlate.render(0.0625F);
        this.energytower.render(0.0625F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
