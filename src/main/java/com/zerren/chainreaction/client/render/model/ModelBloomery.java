package com.zerren.chainreaction.client.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Created by Zerren on 5/20/2016.
 */
public class ModelBloomery extends ModelBase
{
    //fields
    ModelRenderer Shape1;
    ModelRenderer Shape2;

    public ModelBloomery() {
        textureWidth = 256;
        textureHeight = 128;

        Shape1 = new ModelRenderer(this, 88, 42);
        Shape1.addBox(0F, 0F, 0F, 42, 16, 42);
        Shape1.setRotationPoint(-21F, 8F, -21F);
        Shape1.setTextureSize(256, 128);

        Shape2 = new ModelRenderer(this, 0, 0);
        Shape2.addBox(0F, 0F, 0F, 32, 48, 32);
        Shape2.setRotationPoint(-16F, -40F, -16F);
        Shape2.setTextureSize(256, 128);
    }

    public void render() {
        Shape1.render(0.0625F);
        Shape2.render(0.0625F);
    }
}
