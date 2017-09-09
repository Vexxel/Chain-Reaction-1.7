package com.zerren.chainreaction.client.render.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * Created by Zerren on 4/13/2015.
 */
@SideOnly(Side.CLIENT)
public class ModelStirlingEngine extends ModelBase {

    ModelRenderer Base;
    ModelRenderer heatPlate;
    ModelRenderer heatBody;
    ModelRenderer stirlingBody1;
    ModelRenderer stirlingBody2;
    ModelRenderer piston1;
    ModelRenderer piston2;
    ModelRenderer alternatorPlate;
    ModelRenderer alternator;
    ModelRenderer support1;
    ModelRenderer support2;
    ModelRenderer support3;
    ModelRenderer support4;
    ModelRenderer supportBody;

    public ModelStirlingEngine() {
        textureWidth = 128;
        textureHeight = 64;

        Base = new ModelRenderer(this, 0, 0);
        Base.addBox(0F, 0F, 0F, 16, 1, 16);
        Base.setRotationPoint(-8F, 23F, -8F);
        Base.setTextureSize(128, 64);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);
        heatPlate = new ModelRenderer(this, 65, 0);
        heatPlate.addBox(0F, 0F, 0F, 12, 12, 1);
        heatPlate.setRotationPoint(-6F, 10F, 7F);
        heatPlate.setTextureSize(128, 64);
        heatPlate.mirror = true;
        setRotation(heatPlate, 0F, 0F, 0F);
        heatBody = new ModelRenderer(this, 0, 18);
        heatBody.addBox(0F, 0F, 0F, 14, 15, 3);
        heatBody.setRotationPoint(-7F, 8F, 4F);
        heatBody.setTextureSize(128, 64);
        heatBody.mirror = true;
        setRotation(heatBody, 0F, 0F, 0F);
        stirlingBody1 = new ModelRenderer(this, 0, 44);
        stirlingBody1.addBox(0F, 0F, 0F, 8, 8, 7);
        stirlingBody1.setRotationPoint(-4F, 12F, -3F);
        stirlingBody1.setTextureSize(128, 64);
        stirlingBody1.mirror = true;
        setRotation(stirlingBody1, 0F, 0F, 0F);
        stirlingBody2 = new ModelRenderer(this, 0, 44);
        stirlingBody2.addBox(-4F, -4F, -3F, 8, 8, 7);
        stirlingBody2.setRotationPoint(0F, 16F, 0F);
        stirlingBody2.setTextureSize(128, 64);
        stirlingBody2.mirror = true;
        setRotation(stirlingBody2, 0F, 0F, 0.7853982F);
        piston1 = new ModelRenderer(this, 0, 37);
        piston1.addBox(-9F, -1.5F, -2F, 18, 3, 3);
        piston1.setRotationPoint(0F, 16F, 1F);
        piston1.setTextureSize(128, 64);
        piston1.mirror = true;
        setRotation(piston1, 0F, 0F, 0.7853982F);
        piston2 = new ModelRenderer(this, 0, 37);
        piston2.addBox(-9F, -1.5F, -2F, 18, 3, 3);
        piston2.setRotationPoint(0F, 16F, 1F);
        piston2.setTextureSize(128, 64);
        piston2.mirror = true;
        setRotation(piston2, 0F, 0F, -0.7853982F);
        alternatorPlate = new ModelRenderer(this, 36, 18);
        alternatorPlate.addBox(0F, 0F, 0F, 8, 11, 1);
        alternatorPlate.setRotationPoint(-4F, 12F, -8F);
        alternatorPlate.setTextureSize(128, 64);
        alternatorPlate.mirror = true;
        setRotation(alternatorPlate, 0F, 0F, 0F);
        alternator = new ModelRenderer(this, 31, 44);
        alternator.addBox(0F, 0F, 0F, 12, 12, 4);
        alternator.setRotationPoint(-6F, 10F, -7F);
        alternator.setTextureSize(128, 64);
        alternator.mirror = true;
        setRotation(alternator, 0F, 0F, 0F);
        support1 = new ModelRenderer(this, 43, 32);
        support1.addBox(0F, 0F, 0F, 1, 1, 7);
        support1.setRotationPoint(5F, 10F, -3F);
        support1.setTextureSize(128, 64);
        support1.mirror = true;
        setRotation(support1, 0F, 0F, 0F);
        support2 = new ModelRenderer(this, 43, 32);
        support2.addBox(0F, 0F, 0F, 1, 1, 7);
        support2.setRotationPoint(5F, 21F, -3F);
        support2.setTextureSize(128, 64);
        support2.mirror = true;
        setRotation(support2, 0F, 0F, 0F);
        support3 = new ModelRenderer(this, 43, 32);
        support3.addBox(0F, 0F, 0F, 1, 1, 7);
        support3.setRotationPoint(-6F, 21F, -3F);
        support3.setTextureSize(128, 64);
        support3.mirror = true;
        setRotation(support3, 0F, 0F, 0F);
        support4 = new ModelRenderer(this, 43, 32);
        support4.addBox(0F, 0F, 0F, 1, 1, 7);
        support4.setRotationPoint(-6F, 10F, -3F);
        support4.setTextureSize(128, 64);
        support4.mirror = true;
        setRotation(support4, 0F, 0F, 0F);
        supportBody = new ModelRenderer(this, 56, 18);
        supportBody.addBox(0F, 0F, 0F, 10, 10, 1);
        supportBody.setRotationPoint(-5F, 11F, 0F);
        supportBody.setTextureSize(128, 64);
        supportBody.mirror = true;
        setRotation(supportBody, 0F, 0F, 0F);
    }

    public void render() {
        this.Base.render(0.0625F);
        this.heatPlate.render(0.0625F);

        this.heatBody.render(0.0625F);

        this.stirlingBody1.render(0.0625F);
        this.stirlingBody2.render(0.0625F);
        this.piston1.render(0.0625F);
        this.piston2.render(0.0625F);
        this.alternatorPlate.render(0.0625F);
        this.alternator.render(0.0625F);

        this.support1.render(0.0625F);
        this.support2.render(0.0625F);
        this.support3.render(0.0625F);
        this.support4.render(0.0625F);

        this.supportBody.render(0.0625F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
