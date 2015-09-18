package com.zerren.chainreaction.client.render.model.armor;

import com.zerren.chainreaction.client.render.CRRenderHelper;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * Created by Zerren on 9/16/2015.
 */
public class ModelO2Mask extends ModelBiped
{
    //fields
    ModelRenderer head;
    ModelRenderer visor;
    ModelRenderer breather;
    ModelRenderer rail;
    ModelRenderer canister0;
    ModelRenderer canister1;
    ModelRenderer tubes0;
    ModelRenderer tubes1;

    public ModelO2Mask()
    {
        textureWidth = 64;
        textureHeight = 32;

        head = new ModelRenderer(this, 0, 0);
        head.addBox(-4F, -8F, -4F, 8, 8, 8);
        head.setRotationPoint(0F, 0.5F, 0F);
        head.setTextureSize(64, 32);
        bipedHead.addChild(head);

        visor = new ModelRenderer(this, 35, 0);
        visor.addBox(-4F, -6F, -5F, 8, 3, 1);
        visor.setRotationPoint(0F, 0F, 0F);
        visor.setTextureSize(64, 32);
        head.addChild(visor);

        breather = new ModelRenderer(this, 32, 4);
        breather.addBox(-2F, -2F, -5F, 4, 2, 2);
        breather.setRotationPoint(0F, 0F, 0F);
        breather.setTextureSize(64, 32);
        head.addChild(breather);

        rail = new ModelRenderer(this, 35, 8);
        rail.addBox(-3F, -3F, -5F, 6, 1, 1);
        rail.setRotationPoint(0F, 0F, 0F);
        rail.setTextureSize(64, 32);
        head.addChild(rail);

        canister0 = new ModelRenderer(this, 0, 16);
        canister0.addBox(0F, -3.5F, -5.5F, 2, 2, 3);
        canister0.setRotationPoint(0F, 0F, 0F);
        canister0.setTextureSize(64, 32);
        setRotation(canister0, 0.3839724F, 0.6981317F, 0F);
        head.addChild(canister0);

        canister1 = new ModelRenderer(this, 0, 16);
        canister1.addBox(-2F, -3.5F, -5.5F, 2, 2, 3);
        canister1.setRotationPoint(0F, 0F, 0F);
        canister1.setTextureSize(64, 32);
        setRotation(canister1, 0.3839724F, -0.6981317F, 0F);
        head.addChild(canister1);

        tubes0 = new ModelRenderer(this, 0, 27);
        tubes0.addBox(-4.5F, -1.5F, -4.5F, 9, 1, 4);
        tubes0.setRotationPoint(0F, 0F, 0F);
        tubes0.setTextureSize(64, 32);
        head.addChild(tubes0);

        tubes1 = new ModelRenderer(this, 0, 21);
        tubes1.addBox(-4.5F, -2F, -0.5F, 9, 1, 5);
        tubes1.setRotationPoint(0F, 0F, 0F);
        tubes1.setTextureSize(64, 32);
        head.addChild(tubes1);

    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {

        bipedHead.cubeList.clear();

        float s = 1/14F;
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        CRRenderHelper.blendOn();
        bipedHead.render(s);
        CRRenderHelper.blendOff();
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

}