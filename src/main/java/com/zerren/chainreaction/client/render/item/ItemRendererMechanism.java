package com.zerren.chainreaction.client.render.item;

import com.zerren.chainreaction.client.render.model.ModelGasTank;
import com.zerren.chainreaction.client.render.model.ModelRTG;
import com.zerren.chainreaction.reference.Reference;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created by Zerren on 3/7/2015.
 */
@SideOnly(Side.CLIENT)
public class ItemRendererMechanism implements IItemRenderer {

    private final ModelRTG rtg = new ModelRTG();

    public ItemRendererMechanism() { }

    @Override
    public boolean handleRenderType(ItemStack itemStack, ItemRenderType itemRenderType) {
        int meta = itemStack.getItemDamage();

        switch(meta) {
            case 2: return true;
        }
        return false;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType itemRenderType, ItemStack itemStack, ItemRendererHelper itemRendererHelper) {
        int meta = itemStack.getItemDamage();
        switch(meta) {
            case 2: return true;
        }
        return false;
    }

    @Override
    public void renderItem(ItemRenderType itemRenderType, ItemStack itemStack, Object... data) {
        int meta = itemStack.getItemDamage();
        switch (itemRenderType) {
            case ENTITY: {
                render(0F, 1F, 0F, meta);
                break;
            }
            case EQUIPPED: {
                render(0.5F, 1.5F, 0.5F, meta);
                break;
            }
            case EQUIPPED_FIRST_PERSON: {
                render(0.5F, 1.5F, 0.5F, meta);
                break;
            }
            case INVENTORY: {
                render(0.0F, 1F, 0.0F, meta);
                break;
            }
            default:
                break;
        }
    }

    private void render(float x, float y, float z, int metaData) {
        //rtg
        if (metaData == 2) {
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(Reference.Textures.Models.RTG);

            GL11.glPushMatrix(); //start
            GL11.glTranslatef(x, y, z); //size
            GL11.glRotatef(180, 1, 0, 0);
            GL11.glRotatef(-90, 0, 1, 0);
            rtg.render();
            GL11.glPopMatrix(); //end
        }
    }
}