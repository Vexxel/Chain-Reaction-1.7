package com.zerren.chainreaction.client.render.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * Created by Zerren on 4/13/2015.
 */
@SideOnly(Side.CLIENT)
public class ModelRTG extends ModelBase {

    ModelRenderer Base1;
    ModelRenderer Base2;

    ModelRenderer Cylinder;

    ModelRenderer Fins1;
    ModelRenderer Fins2;
    ModelRenderer Fins3;
    ModelRenderer Fins4;
    ModelRenderer Fins5;
    ModelRenderer Fins6;

    ModelRenderer rod1;
    ModelRenderer rod2;
    ModelRenderer rod3;
    ModelRenderer rod4;

    ModelRenderer bind1;
    ModelRenderer bind2;

    public ModelRTG() {
        textureWidth = 128;
        textureHeight = 64;

        Base1 = new ModelRenderer(this, 0, 0);
        Base1.addBox(0F, 0F, 0F, 16, 1, 16);
        Base1.setRotationPoint(-8F, 23F, -8F);
        Base1.setTextureSize(128, 64);
        Base1.mirror = true;
        setRotation(Base1, 0F, 0F, 0F);
        Cylinder = new ModelRenderer(this, 1, 39);
        Cylinder.addBox(0F, 0F, 0F, 10, 14, 10);
        Cylinder.setRotationPoint(-5F, 9F, -5F);
        Cylinder.setTextureSize(128, 64);
        Cylinder.mirror = true;
        setRotation(Cylinder, 0F, 0F, 0F);
        Fins6 = new ModelRenderer(this, 66, 0);
        Fins6.addBox(0F, 0F, 0F, 1, 14, 16);
        Fins6.setRotationPoint(-0.5F, 9F, -8F);
        Fins6.setTextureSize(128, 64);
        Fins6.mirror = true;
        setRotation(Fins6, 0F, 0F, 0F);
        Base2 = new ModelRenderer(this, 0, 0);
        Base2.addBox(0F, 0F, 0F, 16, 1, 16);
        Base2.setRotationPoint(-8F, 8F, -8F);
        Base2.setTextureSize(128, 64);
        Base2.mirror = true;
        setRotation(Base2, 0F, 0F, 0F);
        Fins1 = new ModelRenderer(this, 66, 0);
        Fins1.addBox(0F, 0F, 0F, 1, 14, 16);
        Fins1.setRotationPoint(-8F, 9F, 0.5F);
        Fins1.setTextureSize(128, 64);
        Fins1.mirror = true;
        setRotation(Fins1, 0F, 1.570796F, 0F);
        Fins2 = new ModelRenderer(this, 66, 0);
        Fins2.addBox(-0.5F, 0F, -8F, 1, 14, 16);
        Fins2.setRotationPoint(0F, 9F, 0F);
        Fins2.setTextureSize(128, 64);
        Fins2.mirror = true;
        setRotation(Fins2, 0F, 0.4886922F, 0F);
        Fins3 = new ModelRenderer(this, 66, 0);
        Fins3.addBox(-0.5F, 0F, -8F, 1, 14, 16);
        Fins3.setRotationPoint(0F, 9F, 0F);
        Fins3.setTextureSize(128, 64);
        Fins3.mirror = true;
        setRotation(Fins3, 0F, -0.4886922F, 0F);
        Fins4 = new ModelRenderer(this, 66, 0);
        Fins4.addBox(-0.5F, 0F, -8F, 1, 14, 16);
        Fins4.setRotationPoint(0F, 9F, 0F);
        Fins4.setTextureSize(128, 64);
        Fins4.mirror = true;
        setRotation(Fins4, 0F, -1.082104F, 0F);
        Fins5 = new ModelRenderer(this, 66, 0);
        Fins5.addBox(-0.5F, 0F, -8F, 1, 14, 16);
        Fins5.setRotationPoint(0F, 9F, 0F);
        Fins5.setTextureSize(128, 64);
        Fins5.mirror = true;
        setRotation(Fins5, 0F, -2.059489F, 0F);
        rod1 = new ModelRenderer(this, 0, 20);
        rod1.addBox(0F, 0F, 0F, 1, 14, 1);
        rod1.setRotationPoint(7F, 9F, 7F);
        rod1.setTextureSize(128, 64);
        rod1.mirror = true;
        setRotation(rod1, 0F, 0F, 0F);
        rod2 = new ModelRenderer(this, 0, 20);
        rod2.addBox(0F, 0F, 0F, 1, 14, 1);
        rod2.setRotationPoint(-8F, 9F, -8F);
        rod2.setTextureSize(128, 64);
        rod2.mirror = true;
        setRotation(rod2, 0F, 0F, 0F);
        rod3 = new ModelRenderer(this, 0, 20);
        rod3.addBox(0F, 0F, 0F, 1, 14, 1);
        rod3.setRotationPoint(-8F, 9F, 7F);
        rod3.setTextureSize(128, 64);
        rod3.mirror = true;
        setRotation(rod3, 0F, 0F, 0F);
        rod4 = new ModelRenderer(this, 0, 20);
        rod4.addBox(0F, 0F, 0F, 1, 14, 1);
        rod4.setRotationPoint(7F, 9F, -8F);
        rod4.setTextureSize(128, 64);
        rod4.mirror = true;
        setRotation(rod4, 0F, 0F, 0F);
        bind1 = new ModelRenderer(this, 8, 19);
        bind1.addBox(0F, 0F, 0F, 12, 1, 12);
        bind1.setRotationPoint(-6F, 13F, -6F);
        bind1.setTextureSize(128, 64);
        bind1.mirror = true;
        setRotation(bind1, 0F, 0F, 0F);
        bind2 = new ModelRenderer(this, 8, 19);
        bind2.addBox(0F, 0F, 0F, 12, 1, 12);
        bind2.setRotationPoint(-6F, 18F, -6F);
        bind2.setTextureSize(128, 64);
        bind2.mirror = true;
        setRotation(bind2, 0F, 0F, 0F);

    }

    public void render() {
        this.Base1.render(0.0625F);
        this.Base2.render(0.0625F);

        this.Cylinder.render(0.0625F);

        this.Fins1.render(0.0625F);
        this.Fins2.render(0.0625F);
        this.Fins3.render(0.0625F);
        this.Fins4.render(0.0625F);
        this.Fins5.render(0.0625F);
        this.Fins6.render(0.0625F);

        this.rod1.render(0.0625F);
        this.rod2.render(0.0625F);
        this.rod3.render(0.0625F);
        this.rod4.render(0.0625F);

        this.bind1.render(0.0625F);
        this.bind2.render(0.0625F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
