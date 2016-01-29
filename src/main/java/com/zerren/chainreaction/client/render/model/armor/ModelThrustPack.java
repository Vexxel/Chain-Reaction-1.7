package com.zerren.chainreaction.client.render.model.armor;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

/**
 * Created by Zerren on 9/25/2015.
 */
public class ModelThrustPack extends ModelBiped {
    //fields
    ModelRenderer ThrusterBody;
    ModelRenderer WingLargeR;
    ModelRenderer WingLargeL;
    ModelRenderer ThrusterL;
    ModelRenderer ThrusterR;
    ModelRenderer WingTipR;
    ModelRenderer Strap1;
    ModelRenderer WingTipL;
    ModelRenderer Strap2;
    ModelRenderer WingSmallR;
    ModelRenderer WingSmallL;
    ModelRenderer Vent;

    public ModelThrustPack() {
        textureWidth = 64;
        textureHeight = 32;

        ThrusterBody = new ModelRenderer(this, 0, 0);
        ThrusterBody.addBox(-3F, 1F, 2F, 6, 10, 3);
        ThrusterBody.setRotationPoint(0F, 0F, 0F);
        ThrusterBody.setTextureSize(64, 32);
        bipedBody.addChild(ThrusterBody);

        WingLargeR = new ModelRenderer(this, 19, 0);
        WingLargeR.addBox(-10F, 2F, 3F, 10, 5, 1);
        WingLargeR.setRotationPoint(0F, 0F, 0F);
        WingLargeR.setTextureSize(64, 32);
        setRotation(WingLargeR, 0F, 0F, 0.3490659F);
        bipedBody.addChild(WingLargeR);

        WingLargeL = new ModelRenderer(this, 19, 0);
        WingLargeL.mirror = true;
        WingLargeL.addBox(0F, 2F, 3F, 10, 5, 1);
        WingLargeL.setRotationPoint(0F, 0F, 0F);
        WingLargeL.setTextureSize(64, 32);
        setRotation(WingLargeL, 0F, 0F, -0.3490659F);
        bipedBody.addChild(WingLargeL);

        ThrusterL = new ModelRenderer(this, 0, 14);
        ThrusterL.addBox(-0.4F, 9F, 0F, 3, 4, 3);
        ThrusterL.setRotationPoint(0F, 0F, 0F);
        ThrusterL.setTextureSize(64, 32);
        setRotation(ThrusterL, 0.2617994F, 0F, -0.1396263F);
        bipedBody.addChild(ThrusterL);

        ThrusterR = new ModelRenderer(this, 0, 14);
        ThrusterR.addBox(-2.6F, 9F, 0F, 3, 4, 3);
        ThrusterR.setRotationPoint(0F, 0F, 0F);
        ThrusterR.setTextureSize(64, 32);
        setRotation(ThrusterR, 0.2617994F, 0F, 0.1396263F);
        bipedBody.addChild(ThrusterR);

        WingTipR = new ModelRenderer(this, 42, 0);
        WingTipR.addBox(-12F, 0.7F, 2.5F, 2, 6, 2);
        WingTipR.setRotationPoint(0F, 0F, 0F);
        WingTipR.setTextureSize(64, 32);
        setRotation(WingTipR, 0F, 0F, 0.2617994F);
        bipedBody.addChild(WingTipR);

        Strap1 = new ModelRenderer(this, 51, 0);
        Strap1.addBox(-4F, -2F, -2.5F, 1, 14, 5);
        Strap1.setRotationPoint(0F, 0F, 0F);
        Strap1.setTextureSize(64, 32);
        setRotation(Strap1, 0F, 0F, -0.6108652F);
        bipedBody.addChild(Strap1);

        WingTipL = new ModelRenderer(this, 42, 0);
        WingTipL.mirror = true;
        WingTipL.addBox(10F, 0.7F, 2.5F, 2, 6, 2);
        WingTipL.setRotationPoint(0F, 0F, 0F);
        WingTipL.setTextureSize(64, 32);
        setRotation(WingTipL, 0F, 0F, -0.2617994F);
        bipedBody.addChild(WingTipL);

        Strap2 = new ModelRenderer(this, 51, 0);
        Strap2.addBox(3F, -2F, -2.6F, 1, 14, 5);
        Strap2.setRotationPoint(0F, 0F, 0F);
        Strap2.setTextureSize(64, 32);
        setRotation(Strap2, 0F, 0F, 0.6108652F);
        bipedBody.addChild(Strap2);

        WingSmallR = new ModelRenderer(this, 19, 7);
        WingSmallR.addBox(-13F, 1F, 3F, 7, 3, 1);
        WingSmallR.setRotationPoint(0F, 0F, 0F);
        WingSmallR.setTextureSize(64, 32);
        setRotation(WingSmallR, 0F, 0F, -0.9075712F);
        bipedBody.addChild(WingSmallR);

        WingSmallL = new ModelRenderer(this, 19, 7);
        WingSmallL.addBox(6F, 1F, 3F, 7, 3, 1);
        WingSmallL.setRotationPoint(0F, 0F, 0F);
        WingSmallL.setTextureSize(64, 32);
        setRotation(WingSmallL, 0F, 0F, 0.9075712F);
        bipedBody.addChild(WingSmallL);

        Vent = new ModelRenderer(this, 13, 14);
        Vent.addBox(-2F, 3F, 5F, 4, 5, 1);
        Vent.setRotationPoint(0F, 0F, 0F);
        Vent.setTextureSize(64, 32);
        bipedBody.addChild(Vent);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        bipedBody.cubeList.clear();
        float s = 1/16F;
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        bipedBody.render(s);
    }

    @Override
    public void setRotationAngles(float v1, float v2, float v3, float v4, float v5, float v6, Entity entity) {
        isSneak = ((entity != null) && entity.isSneaking());
        super.setRotationAngles(v1, v2, v3, v4, v5, v6, entity);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}