package com.zerren.chainreaction.client.render.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * Created by Zerren on 3/7/2015.
 */
@SideOnly(Side.CLIENT)
public class ModelHeatExchanger extends ModelBase {

    ModelRenderer Body;
    ModelRenderer Leg3;
    ModelRenderer Plate2;
    ModelRenderer Plate1;
    ModelRenderer Leg2;
    ModelRenderer Leg1;
    ModelRenderer Leg4;
    ModelRenderer Input1;
    ModelRenderer Output1;
    ModelRenderer Input2;
    ModelRenderer Output2;

    public ModelHeatExchanger() {
        textureWidth = 256;
        textureHeight = 64;

        Body = new ModelRenderer(this, 0, 0);
        Body.addBox(0F, 0F, -7F, 80, 14, 14);
        Body.setRotationPoint(-40F, 9F, 0F);
        Body.setTextureSize(256, 64);
        //Bolted section near coolant entry
        Plate1 = new ModelRenderer(this, 190, 0);
        Plate1.addBox(0F, 0F, 0F, 3, 16, 16);
        Plate1.setRotationPoint(-28F, 8F, -8F);
        Plate1.setTextureSize(256, 64);

        Plate2 = new ModelRenderer(this, 190, 0);
        Plate2.addBox(0F, 0F, 0F, 3, 16, 16);
        Plate2.setRotationPoint(-24F, 8F, -8F);
        Plate2.setTextureSize(256, 64);

        Leg1 = new ModelRenderer(this, 0, 30);
        Leg1.addBox(0F, 0F, 0F, 4, 4, 16);
        Leg1.setRotationPoint(-33F, 20F, -8F);
        Leg1.setTextureSize(256, 64);

        Leg2 = new ModelRenderer(this, 0, 30);
        Leg2.addBox(0F, 0F, 0F, 4, 4, 16);
        Leg2.setRotationPoint(-12F, 20F, -8F);
        Leg2.setTextureSize(256, 64);

        Leg3 = new ModelRenderer(this, 0, 30);
        Leg3.addBox(0F, 0F, 0F, 4, 4, 16);
        Leg3.setRotationPoint(8F, 20F, -8F);
        Leg3.setTextureSize(256, 64);

        Leg4 = new ModelRenderer(this, 0, 30);
        Leg4.addBox(0F, 0F, 0F, 4, 4, 16);
        Leg4.setRotationPoint(29F, 20F, -8F);
        Leg4.setTextureSize(256, 64);
        //coolant entry
        Input1 = new ModelRenderer(this, 90, 30);
        Input1.addBox(0F, 0F, 0F, 1, 10, 10);
        Input1.setRotationPoint(-41F, 11F, -5F);
        Input1.setTextureSize(256, 64);
        //coolant exit
        Output1 = new ModelRenderer(this, 90, 30);
        Output1.addBox(0F, 0F, 0F, 1, 10, 10);
        Output1.setRotationPoint(40F, 11F, -5F);
        Output1.setTextureSize(256, 64);
        //recieving liquid entry
        Input2 = new ModelRenderer(this, 42, 30);
        Input2.addBox(0F, 0F, 0F, 10, 1, 10);
        Input2.setRotationPoint(-21F, 8F, -5F);
        Input2.setTextureSize(256, 64);
        //recieving liquid exit
        Output2 = new ModelRenderer(this, 42, 42);
        Output2.addBox(0F, 0F, 0F, 10, 1, 10);
        Output2.setRotationPoint(11F, 8F, -5F);
        Output2.setTextureSize(256, 64);
    }

    public void renderAll() {
        this.Body.render(0.0625F);
        this.Leg1.render(0.0625F);
        this.Leg2.render(0.0625F);
        this.Leg3.render(0.0625F);
        this.Leg4.render(0.0625F);
        this.Plate1.render(0.0625F);
        this.Plate2.render(0.0625F);
        this.Input1.render(0.0625F);
        this.Input2.render(0.0625F);
        this.Output1.render(0.0625F);
        this.Output2.render(0.0625F);

    }

}
