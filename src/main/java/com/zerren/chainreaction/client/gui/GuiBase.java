package com.zerren.chainreaction.client.gui;

import com.zerren.chainreaction.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.Container;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import org.lwjgl.opengl.GL11;

/**
 * Created by Zerren on 5/21/2016.
 */
public abstract class GuiBase extends GuiContainer {

    protected static final int FONT_LIGHT = 16777215;
    protected static final int FONT_DEFAULT = 4210752;

    public GuiBase(Container c) {
        super(c);
    }

    protected void drawInventoryName() {
        drawInventoryName(FONT_DEFAULT);
    }

    protected void drawInventoryName(int color) {
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 95 + 2, color);
    }

    protected void drawContainerName(String container) {
        drawContainerName(container, FONT_DEFAULT);
    }

    protected void drawContainerName(String container, int color) {
        fontRendererObj.drawString(container, 8, 6, color);
    }

    public void drawFluid(FluidStack fluid, int x, int y, int width, int height, int maxCapacity, ResourceLocation oldResource) {
        if (fluid == null || fluid.getFluid() == null) {
            return;
        }
        IIcon icon = fluid.getFluid().getIcon(fluid);

        if (icon == null) {
            icon = ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
        }

        mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        setGLColorFromInt(fluid.getFluid().getColor(fluid));
        int fullX = width / 16;
        int fullY = height / 16;
        int lastX = width - fullX * 16;
        int lastY = height - fullY * 16;
        int level = fluid.amount * height / maxCapacity;
        int fullLvl = (height - level) / 16;
        int lastLvl = (height - level) - fullLvl * 16;
        for (int i = 0; i < fullX; i++) {
            for (int j = 0; j < fullY; j++) {
                if (j >= fullLvl) {
                    drawCutIcon(icon, x + i * 16, y + j * 16, 16, 16, j == fullLvl ? lastLvl : 0);
                }
            }
        }
        for (int i = 0; i < fullX; i++) {
            drawCutIcon(icon, x + i * 16, y + fullY * 16, 16, lastY, fullLvl == fullY ? lastLvl : 0);
        }
        for (int i = 0; i < fullY; i++) {
            if (i >= fullLvl) {
                drawCutIcon(icon, x + fullX * 16, y + i * 16, lastX, 16, i == fullLvl ? lastLvl : 0);
            }
        }
        drawCutIcon(icon, x + fullX * 16, y + fullY * 16, lastX, lastY, fullLvl == fullY ? lastLvl : 0);
        mc.getTextureManager().bindTexture(oldResource);
    }

    private void drawCutIcon(IIcon icon, int x, int y, int width, int height, int cut) {
        Tessellator tess = Tessellator.instance;
        tess.startDrawingQuads();
        tess.addVertexWithUV(x, y + height, zLevel, icon.getMinU(), icon.getInterpolatedV(height));
        tess.addVertexWithUV(x + width, y + height, zLevel, icon.getInterpolatedU(width), icon.getInterpolatedV(height));
        tess.addVertexWithUV(x + width, y + cut, zLevel, icon.getInterpolatedU(width), icon.getInterpolatedV(cut));
        tess.addVertexWithUV(x, y + cut, zLevel, icon.getMinU(), icon.getInterpolatedV(cut));
        tess.draw();
    }

    public static void setGLColorFromInt(int color) {
        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;
        GL11.glColor4f(red, green, blue, 1.0F);
    }

}
