package com.zerren.zedeng.client.render.item;

import com.zerren.zedeng.client.render.model.ModelExchangerTubes;
import com.zerren.zedeng.reference.Reference;
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
public class ItemRendererExchanger implements IItemRenderer {

    private final ModelExchangerTubes modelTubes;

    public ItemRendererExchanger() {
        modelTubes = new ModelExchangerTubes();
    }

    @Override
    public boolean handleRenderType(ItemStack itemStack, ItemRenderType itemRenderType) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType itemRenderType, ItemStack itemStack, ItemRendererHelper itemRendererHelper) {
        return itemStack.getItemDamage() == 0;
    }

    @Override
    public void renderItem(ItemRenderType itemRenderType, ItemStack itemStack, Object... data) {
        switch (itemRenderType) {
            case ENTITY: {
                render(0F, 1F, 0F, itemStack.getItemDamage());
                break;
            }
            case EQUIPPED: {
                render(0.5F, 1.5F, 0.5F, itemStack.getItemDamage());
                break;
            }
            case EQUIPPED_FIRST_PERSON: {
                render(0.5F, 1.5F, 0.5F, itemStack.getItemDamage());
                break;
            }
            case INVENTORY: {
                render(0.0F, 1F, 0.0F, itemStack.getItemDamage());
                break;
            }
            default:
                break;
        }
    }

    private void render(float x, float y, float z, int metaData) {
        if (metaData == 0) {
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(Reference.Textures.Models.TUBES);

            GL11.glPushMatrix(); //start
            GL11.glTranslatef(x, y, z); //size
            GL11.glRotatef(180, 1, 0, 0);
            GL11.glRotatef(-90, 0, 1, 0);
            modelTubes.renderAll();
            GL11.glPopMatrix(); //end
        }
        else if (metaData == 1) {

        }
    }
}