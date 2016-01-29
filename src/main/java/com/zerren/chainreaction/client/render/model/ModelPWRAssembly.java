package com.zerren.chainreaction.client.render.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * Created by Zerren on 10/20/2015.
 */
@SideOnly(Side.CLIENT)
public class ModelPWRAssembly extends ModelBase
{
    //fields
    ModelRenderer PlateBottom;
    ModelRenderer PlateTop;
    ModelRenderer RodFL;
    ModelRenderer RodFR;
    ModelRenderer RodBL;
    ModelRenderer RodBR;
    ModelRenderer Tube1;
    ModelRenderer Tube2;
    ModelRenderer Tube3;

    public ModelPWRAssembly()
    {
        textureWidth = 64;
        textureHeight = 64;

        PlateBottom = new ModelRenderer(this, 0, 0);
        PlateBottom.addBox(0F, 0F, 0F, 16, 1, 16);
        PlateBottom.setRotationPoint(-8F, 22F, -8F);
        PlateBottom.setTextureSize(64, 64);

        PlateTop = new ModelRenderer(this, 0, 0);
        PlateTop.addBox(0F, 0F, 0F, 16, 1, 16);
        PlateTop.setRotationPoint(-8F, 9F, -8F);
        PlateTop.setTextureSize(64, 64);

        RodFL = new ModelRenderer(this, 24, 17);
        RodFL.addBox(0F, 0F, 0F, 2, 16, 2);
        RodFL.setRotationPoint(-7F, 8F, -7F);
        RodFL.setTextureSize(64, 64);

        RodFR = new ModelRenderer(this, 24, 17);
        RodFR.addBox(0F, 0F, 0F, 2, 16, 2);
        RodFR.setRotationPoint(5F, 8F, -7F);
        RodFR.setTextureSize(64, 64);

        RodBL = new ModelRenderer(this, 24, 17);
        RodBL.addBox(0F, 0F, 0F, 2, 16, 2);
        RodBL.setRotationPoint(-7F, 8F, 5F);
        RodBL.setTextureSize(64, 64);

        RodBR = new ModelRenderer(this, 24, 17);
        RodBR.addBox(0F, 0F, 0F, 2, 16, 2);
        RodBR.setRotationPoint(5F, 8F, 5F);
        RodBR.setTextureSize(64, 64);

        Tube1 = new ModelRenderer(this, 0, 39);
        Tube1.addBox(0F, 0F, 0F, 16, 10, 10);
        Tube1.setRotationPoint(-8F, 11F, -5F);
        Tube1.setTextureSize(64, 64);

        Tube2 = new ModelRenderer(this, 0, 39);
        Tube2.addBox(0F, 0F, 0F, 16, 10, 10);
        Tube2.setRotationPoint(-5F, 11F, 8F);
        Tube2.setTextureSize(64, 64);
        setRotation(Tube2, 0F, 1.570796F, 0F);

        Tube3 = new ModelRenderer(this, 0, 39);
        Tube3.addBox(0F, 0F, 0F, 16, 10, 10);
        Tube3.setRotationPoint(-5F, 8F, -5F);
        Tube3.setTextureSize(64, 64);
        setRotation(Tube3, 1.570796F, 0F, 1.570796F);
    }

    public void render()
    {
        float size = 0.0625F;
        PlateBottom.render(size);
        PlateTop.render(size);
        RodFL.render(size);
        RodFR.render(size);
        RodBL.render(size);
        RodBR.render(size);
        Tube1.render(size);
        Tube2.render(size);
        Tube3.render(size);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}
