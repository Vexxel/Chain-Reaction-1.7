package com.zerren.chainreaction.client.render.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * Created by Zerren on 4/13/2015.
 */
@SideOnly(Side.CLIENT)
public class ModelExchangerSmall extends ModelBase {

    ModelRenderer Base;
    ModelRenderer steamInput;
    ModelRenderer waterOutput;
    ModelRenderer conductor;
    ModelRenderer support1;
    ModelRenderer exchangerPlate;
    ModelRenderer Shape1;
    ModelRenderer support2;
    ModelRenderer support1Top;
    ModelRenderer support2Top;

    public ModelExchangerSmall() {
        textureWidth = 128;
        textureHeight = 64;

        Base = new ModelRenderer(this, 0, 0);
        Base.addBox(0F, 0F, 0F, 16, 1, 16);
        Base.setRotationPoint(-8F, 23F, -8F);
        Base.setTextureSize(128, 64);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);
        steamInput = new ModelRenderer(this, 0, 40);
        steamInput.addBox(0F, 0F, 0F, 1, 10, 10);
        steamInput.setRotationPoint(-8F, 11F, -5F);
        steamInput.setTextureSize(128, 64);
        steamInput.mirror = true;
        setRotation(steamInput, 0F, 0F, 0F);
        waterOutput = new ModelRenderer(this, 0, 40);
        waterOutput.addBox(0F, 0F, 0F, 1, 10, 10);
        waterOutput.setRotationPoint(7F, 11F, -5F);
        waterOutput.setTextureSize(128, 64);
        waterOutput.mirror = true;
        setRotation(waterOutput, 0F, 0F, 0F);
        conductor = new ModelRenderer(this, 23, 40);
        conductor.addBox(0F, 0F, 0F, 12, 12, 1);
        conductor.setRotationPoint(-6F, 10F, 7F);
        conductor.setTextureSize(128, 64);
        conductor.mirror = true;
        setRotation(conductor, 0F, 0F, 0F);
        support1 = new ModelRenderer(this, 54, 34);
        support1.addBox(0F, 0F, 0F, 1, 14, 1);
        support1.setRotationPoint(-5F, 9F, -8F);
        support1.setTextureSize(128, 64);
        support1.mirror = true;
        setRotation(support1, 0F, 0F, 0F);
        exchangerPlate = new ModelRenderer(this, 65, 0);
        exchangerPlate.addBox(0F, 0F, 0F, 14, 15, 5);
        exchangerPlate.setRotationPoint(-7F, 8F, 2F);
        exchangerPlate.setTextureSize(128, 64);
        exchangerPlate.mirror = true;
        setRotation(exchangerPlate, 0F, 0F, 0F);
        Shape1 = new ModelRenderer(this, 0, 18);
        Shape1.addBox(0F, 0F, 0F, 14, 12, 9);
        Shape1.setRotationPoint(-7F, 10F, -7F);
        Shape1.setTextureSize(128, 64);
        Shape1.mirror = true;
        setRotation(Shape1, 0F, 0F, 0F);
        support2 = new ModelRenderer(this, 54, 34);
        support2.addBox(0F, 0F, 0F, 1, 14, 1);
        support2.setRotationPoint(4F, 9F, -8F);
        support2.setTextureSize(128, 64);
        support2.mirror = true;
        setRotation(support2, 0F, 0F, 0F);
        support1Top = new ModelRenderer(this, 47, 22);
        support1Top.addBox(0F, 0F, 0F, 1, 1, 9);
        support1Top.setRotationPoint(-5F, 9F, -7F);
        support1Top.setTextureSize(128, 64);
        support1Top.mirror = true;
        setRotation(support1Top, 0F, 0F, 0F);
        support2Top = new ModelRenderer(this, 47, 22);
        support2Top.addBox(0F, 0F, 0F, 1, 1, 9);
        support2Top.setRotationPoint(4F, 9F, -7F);
        support2Top.setTextureSize(128, 64);
        support2Top.mirror = true;
        setRotation(support2Top, 0F, 0F, 0F);

    }

    public void render() {
        this.Base.render(0.0625F);
        this.steamInput.render(0.0625F);
        this.waterOutput.render(0.0625F);
        this.conductor.render(0.0625F);
        this.support1.render(0.0625F);
        this.exchangerPlate.render(0.0625F);
        this.Shape1.render(0.0625F);
        this.support2.render(0.0625F);
        this.support1Top.render(0.0625F);
        this.support2Top.render(0.0625F);

    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
