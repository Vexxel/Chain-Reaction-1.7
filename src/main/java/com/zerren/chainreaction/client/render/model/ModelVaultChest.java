package com.zerren.chainreaction.client.render.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * Created by Zerren on 3/1/2015.
 */
@SideOnly(Side.CLIENT)
public class ModelVaultChest extends ModelBase {

    public ModelRenderer chestLid = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
    public ModelRenderer chestBelow;
    public ModelRenderer chestKnob;
    public ModelRenderer chestKnobUnlocked;
    public ModelRenderer lidBolts;
    public ModelRenderer bolts;

    public ModelVaultChest() {
        this.chestLid.addBox(0.0F, -5.0F, -14.0F, 14, 5, 14, 0.0F);
        this.chestLid.rotationPointX = 1.0F;
        this.chestLid.rotationPointY = 7.0F;
        this.chestLid.rotationPointZ = 15.0F;
        this.chestKnob = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
        this.chestKnob.addBox(6.0F, -2.0F, -15.0F, 2, 4, 1, 0.0F);
        this.chestKnob.rotationPointX = 1.0F;
        this.chestKnob.rotationPointY = 7.0F;
        this.chestKnob.rotationPointZ = 15.0F;
        this.chestKnobUnlocked = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
        this.chestKnobUnlocked.addBox(6.0F, -4.0F, -15.0F, 2, 4, 1, 0.0F);
        this.chestKnobUnlocked.rotationPointX = 1.0F;
        this.chestKnobUnlocked.rotationPointY = 7.0F;
        this.chestKnobUnlocked.rotationPointZ = 15.0F;
        this.chestBelow = (new ModelRenderer(this, 0, 19)).setTextureSize(64, 64);
        this.chestBelow.addBox(0.0F, 0.0F, 0.0F, 14, 10, 14, 0.0F);
        this.chestBelow.rotationPointX = 1.0F;
        this.chestBelow.rotationPointY = 6.0F;
        this.chestBelow.rotationPointZ = 1.0F;

        this.lidBolts = (new ModelRenderer(this, 0, 7)).setTextureSize(64, 64);
        this.bolts = (new ModelRenderer(this, 0, 7)).setTextureSize(64, 64);

        //back bolts
        this.lidBolts.addBox(11F, -3F, -0.1F, 1, 1, 1, 0.0F);
        this.lidBolts.addBox(2F, -3F, -0.1F, 1, 1, 1, 0.0F);
        this.bolts.addBox(11F, 1F, -0.1F, 1, 1, 1, 0.0F);
        this.bolts.addBox(2F, 1F, -0.1F, 1, 1, 1, 0.0F);

        //front bolts
        this.lidBolts.addBox(11F, -3F, -14.9F, 1, 1, 1, 0.0F);
        this.lidBolts.addBox(2F, -3F, -14.9F, 1, 1, 1, 0.0F);
        this.bolts.addBox(11F, 1F, -14.9F, 1, 1, 1, 0.0F);
        this.bolts.addBox(2F, 1F, -14.9F, 1, 1, 1, 0.0F);

        //right bolts
        this.lidBolts.addBox(13.9F, -3F, -3F, 1, 1, 1, 0.0F);
        this.lidBolts.addBox(13.9F, -3F, -12F, 1, 1, 1, 0.0F);
        this.bolts.addBox(13.9F, 1F, -3F, 1, 1, 1, 0.0F);
        this.bolts.addBox(13.9F, 1F, -12F, 1, 1, 1, 0.0F);

        //left bolts
        this.lidBolts.addBox(-0.9F, -3F, -3F, 1, 1, 1, 0.0F);
        this.lidBolts.addBox(-0.9F, -3F, -12F, 1, 1, 1, 0.0F);
        this.bolts.addBox(-0.9F, 1F, -3F, 1, 1, 1, 0.0F);
        this.bolts.addBox(-0.9F, 1F, -12F, 1, 1, 1, 0.0F);


        this.lidBolts.rotationPointX = 1.0F;
        this.lidBolts.rotationPointY = 7.0F;
        this.lidBolts.rotationPointZ = 15.0F;

        this.bolts.rotationPointX = 1.0F;
        this.bolts.rotationPointY = 7.0F;
        this.bolts.rotationPointZ = 15.0F;
    }

    public void renderBody() {
        this.chestLid.render(0.0625F);
        this.chestBelow.render(0.0625F);
    }

    public void renderLocked() {
        this.chestKnob.rotateAngleX = this.chestLid.rotateAngleX;
        this.chestKnob.render(0.0625F);
    }
    public void renderUnlocked() {
        this.chestKnobUnlocked.rotateAngleX = this.chestLid.rotateAngleX;
        this.chestKnobUnlocked.render(0.0625F);
        this.lidBolts.render(0.0625F);
        this.bolts.render(0.0625F);
    }
}
