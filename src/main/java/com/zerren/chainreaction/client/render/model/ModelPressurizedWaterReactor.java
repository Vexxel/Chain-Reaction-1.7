package com.zerren.chainreaction.client.render.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * Created by Zerren on 4/24/2015.
 */
@SideOnly(Side.CLIENT)
public class ModelPressurizedWaterReactor extends ModelBase {

    ModelRenderer Vessel;
    ModelRenderer UpperCRodPlate;
    ModelRenderer LowerCRodPlate;
    ModelRenderer InletFront;
    ModelRenderer InletBack;
    ModelRenderer InletRight;
    ModelRenderer InletLeft;
    ModelRenderer OutletFront;
    ModelRenderer OutletBack;
    ModelRenderer OutletRight;
    ModelRenderer OutletLeft;
    ModelRenderer UpperFlange1;
    ModelRenderer UpperFlange2;
    ModelRenderer LowerFlange1;
    ModelRenderer LowerFlange2;
    ModelRenderer CRodPlateBolt1;
    ModelRenderer CRodPlateBolt2;
    ModelRenderer CRodPlateBolt3;
    ModelRenderer CRodPlateBolt4;
    public ModelRenderer CRodBundle;

    public ModelPressurizedWaterReactor() {
        textureWidth = 256;
        textureHeight = 256;

        Vessel = new ModelRenderer(this, 0, 0);
        Vessel.addBox(0F, 0F, 0F, 40, 80, 40);
        Vessel.setRotationPoint(-20F, -24F, -20F);
        Vessel.setTextureSize(256, 256);

        UpperCRodPlate = new ModelRenderer(this, 0, 175);
        UpperCRodPlate.addBox(0F, 0F, 0F, 32, 3, 32);
        UpperCRodPlate.setRotationPoint(-16F, -52F, -16F);
        UpperCRodPlate.setTextureSize(256, 256);

        LowerCRodPlate = new ModelRenderer(this, 0, 175);
        LowerCRodPlate.addBox(0F, 0F, 0F, 32, 3, 32);
        LowerCRodPlate.setRotationPoint(-16F, -38F, -16F);
        LowerCRodPlate.setTextureSize(256, 256);

        InletFront = new ModelRenderer(this, 0, 0);
        InletFront.addBox(0F, 0F, 0F, 10, 10, 4);
        InletFront.setRotationPoint(-5F, 27F, -24F);
        InletFront.setTextureSize(256, 256);

        InletBack = new ModelRenderer(this, 0, 0);
        InletBack.addBox(0F, 0F, 0F, 10, 10, 4);
        InletBack.setRotationPoint(5F, 27F, 24F);
        InletBack.setTextureSize(256, 256);
        setRotation(InletBack, 0F, -3.141593F, 0F);

        InletRight = new ModelRenderer(this, 0, 0);
        InletRight.addBox(0F, 0F, 0F, 10, 10, 4);
        InletRight.setRotationPoint(24F, 27F, -5F);
        InletRight.setTextureSize(256, 256);
        setRotation(InletRight, 0F, -1.570796F, 0F);

        InletLeft = new ModelRenderer(this, 0, 0);
        InletLeft.addBox(0F, 0F, 0F, 10, 10, 4);
        InletLeft.setRotationPoint(-24F, 27F, 5F);
        InletLeft.setTextureSize(256, 256);
        setRotation(InletLeft, 0F, 1.570796F, 0F);

        OutletFront = new ModelRenderer(this, 0, 0);
        OutletFront.addBox(0F, 0F, 0F, 10, 10, 4);
        OutletFront.setRotationPoint(5F, -5F, -20F);
        OutletFront.setTextureSize(256, 256);
        setRotation(OutletFront, 0F, 3.141593F, 0F);

        OutletBack = new ModelRenderer(this, 0, 0);
        OutletBack.addBox(0F, 0F, 0F, 10, 10, 4);
        OutletBack.setRotationPoint(-5F, -5F, 20F);
        OutletBack.setTextureSize(256, 256);

        OutletRight = new ModelRenderer(this, 0, 0);
        OutletRight.addBox(0F, 0F, 0F, 10, 10, 4);
        OutletRight.setRotationPoint(20F, -5F, 5F);
        OutletRight.setTextureSize(256, 256);
        setRotation(OutletRight, 0F, 1.570796F, 0F);

        OutletLeft = new ModelRenderer(this, 0, 0);
        OutletLeft.addBox(0F, 0F, 0F, 10, 10, 4);
        OutletLeft.setRotationPoint(-20F, -5F, -5F);
        OutletLeft.setTextureSize(256, 256);
        setRotation(OutletLeft, 0F, -1.570796F, 0F);

        UpperFlange1 = new ModelRenderer(this, 0, 122);
        UpperFlange1.addBox(0F, 0F, 0F, 48, 3, 48);
        UpperFlange1.setRotationPoint(-24F, -12F, -24F);
        UpperFlange1.setTextureSize(256, 256);

        UpperFlange2 = new ModelRenderer(this, 0, 122);
        UpperFlange2.addBox(0F, 0F, 0F, 48, 3, 48);
        UpperFlange2.setRotationPoint(-24F, -8F, -24F);
        UpperFlange2.setTextureSize(256, 256);

        LowerFlange1 = new ModelRenderer(this, 0, 122);
        LowerFlange1.addBox(0F, 0F, 0F, 48, 3, 48);
        LowerFlange1.setRotationPoint(-24F, 37F, -24F);
        LowerFlange1.setTextureSize(256, 256);

        LowerFlange2 = new ModelRenderer(this, 0, 122);
        LowerFlange2.addBox(0F, 0F, 0F, 48, 3, 48);
        LowerFlange2.setRotationPoint(-24F, 41F, -24F);
        LowerFlange2.setTextureSize(256, 256);

        CRodPlateBolt1 = new ModelRenderer(this, 170, 0);
        CRodPlateBolt1.addBox(0F, 0F, 0F, 2, 29, 2);
        CRodPlateBolt1.setRotationPoint(-15F, -53F, 13F);
        CRodPlateBolt1.setTextureSize(256, 256);

        CRodPlateBolt2 = new ModelRenderer(this, 170, 0);
        CRodPlateBolt2.addBox(0F, 0F, 0F, 2, 29, 2);
        CRodPlateBolt2.setRotationPoint(13F, -53F, 13F);
        CRodPlateBolt2.setTextureSize(256, 256);

        CRodPlateBolt3 = new ModelRenderer(this, 170, 0);
        CRodPlateBolt3.addBox(0F, 0F, 0F, 2, 29, 2);
        CRodPlateBolt3.setRotationPoint(-15F, -53F, -15F);
        CRodPlateBolt3.setTextureSize(256, 256);

        CRodPlateBolt4 = new ModelRenderer(this, 170, 0);
        CRodPlateBolt4.addBox(0F, 0F, 0F, 2, 29, 2);
        CRodPlateBolt4.setRotationPoint(13F, -53F, -15F);
        CRodPlateBolt4.setTextureSize(256, 256);

        CRodBundle = new ModelRenderer(this, 132, 175);
        CRodBundle.addBox(0F, 0F, 0F, 24, 30, 24);
        CRodBundle.setRotationPoint(-12F, -53F, -12F);
        CRodBundle.setTextureSize(256, 256);

    }

    private void setRotation(ModelRenderer cube, float x, float y, float z) {
        cube.rotateAngleX = x;
        cube.rotateAngleY = y;
        cube.rotateAngleZ = z;
    }

    public void render() {
        float size = 0.0625F;
        Vessel.render(size);
        UpperCRodPlate.render(size);
        LowerCRodPlate.render(size);
        InletFront.render(size);
        InletBack.render(size);
        InletRight.render(size);
        InletLeft.render(size);
        OutletFront.render(size);
        OutletBack.render(size);
        OutletRight.render(size);
        OutletLeft.render(size);
        UpperFlange1.render(size);
        UpperFlange2.render(size);
        LowerFlange1.render(size);
        LowerFlange2.render(size);
        CRodPlateBolt1.render(size);
        CRodPlateBolt2.render(size);
        CRodPlateBolt3.render(size);
        CRodPlateBolt4.render(size);
        CRodBundle.render(size);
    }
}