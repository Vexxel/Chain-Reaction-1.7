package com.zerren.chainreaction.client.gui;

import chainreaction.api.tile.IUpgradeableTile;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import com.zerren.chainreaction.reference.Reference;
import com.zerren.chainreaction.tile.TileEntityCRBase;
import com.zerren.chainreaction.utility.CoreUtility;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zerren on 5/21/2016.
 */
abstract class GuiContainerCR extends GuiContainer {

    static final int FONT_LIGHT = 16777215;
    static final int FONT_DEFAULT = 4210752;
    private static final ResourceLocation WIDGETS = Reference.Textures.GUIs.WIDGETS;

    final TileEntityCRBase tile;

    GuiContainerCR(Container c, TileEntityCRBase tile) {
        super(c);
        this.tile = tile;
    }

    void drawInventoryName() {
        drawInventoryName(FONT_DEFAULT);
    }

    void drawInventoryName(int color) {
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 95 + 2, color);
    }

    void drawContainerName(String container) {
        drawContainerName(container, FONT_DEFAULT);
    }

    void drawContainerName(String container, int color) {
        fontRendererObj.drawString(container, 8, 6, color);
    }

    void drawBackground(int x, int y, ResourceLocation tex) {
        this.mc.getTextureManager().bindTexture(tex);

        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

    }

    void drawEnergyBar(int x, int y, EnergyStorage storage) {
        this.mc.getTextureManager().bindTexture(WIDGETS);

        int scaled = (int)(storage.getEnergyStored() > 0 ? (double)storage.getEnergyStored() / storage.getMaxEnergyStored() * 48 : 0);
        drawTexturedModalRect(x, y + 48 - scaled, 48, 20 + 48 - scaled, 8, scaled);
    }

    void drawProgressBarHorizontal(int x, int y, int width, int height, int u, int v, int scaled, ResourceLocation tex) {
        this.mc.getTextureManager().bindTexture(tex);
        drawTexturedModalRect(x, y, u, v, width + scaled, height);
    }

    void drawFluidTankTooltip(IFluidTank tank, int mouseX, int mouseY, int tankXStart, int tankYStart, int tankWidth, int tankHeight) {
        FluidStack stack = null;
        boolean draw = false;
        if (mouseY >= (guiTop + tankYStart) && mouseY < (guiTop + tankYStart + tankHeight)) {
            if (mouseX >= (guiLeft + tankXStart) && mouseX < (guiLeft + tankXStart + tankWidth)) {
                draw = true;
                stack = tank.getFluid();
            }
        }
        if (draw) {
            List<String> fluidTip = new ArrayList<String>();
            fluidTip.add((stack != null ? EnumChatFormatting.GOLD + stack.getLocalizedName() : "Empty"));
            if (stack != null) fluidTip.add(stack.amount + " / " + tank.getCapacity() + " mB");

            drawHoveringText(fluidTip, mouseX - guiLeft, mouseY - guiTop, fontRendererObj);
        }
    }

    void drawEnergyBarTooltip(EnergyStorage storage, int mouseX, int mouseY, int barXStart, int barYStart, int barWidth, int barHeight) {
        int energy = -1;
        if (mouseY >= (guiTop + barYStart) && mouseY < (guiTop + barYStart + barHeight)) {
            if (mouseX >= (guiLeft + barXStart) && mouseX < (guiLeft + barXStart + barWidth)) {
                energy = storage.getEnergyStored();
            }
        }
        if (energy >= 0) {
            List<String> energyTip = new ArrayList<String>();
            energyTip.add(EnumChatFormatting.GOLD + "Energy");
            energyTip.add(energy + " / " + storage.getMaxEnergyStored() + " RF");

            drawHoveringText(energyTip, mouseX - guiLeft, mouseY - guiTop, fontRendererObj);
        }
    }

    void drawUpgradeInstallTooltip(IUpgradeableTile tile, int mouseX, int mouseY, int buttonXStart, int buttonYStart) {
        if (mouseY >= (guiTop + buttonYStart) && mouseY < (guiTop + buttonYStart + 18)) {
            if (mouseX >= (guiLeft + buttonXStart) && mouseX < (guiLeft + buttonXStart + 18)) {
                List<String> buttonTip = new ArrayList<String>();
                if (tile.areUpgradesActive()) {
                    buttonTip.add(EnumChatFormatting.RED + CoreUtility.translate("gui.item.upgrade.uninstall.name"));
                }
                else {
                    buttonTip.add(EnumChatFormatting.GREEN + CoreUtility.translate("gui.item.upgrade.install.name"));
                }

                drawHoveringText(buttonTip, mouseX - guiLeft, mouseY - guiTop, fontRendererObj);
            }
        }
    }

    void drawFluidTank(FluidStack fluid, int x, int y, int maxCapacity) {
        drawFluid(fluid, x, y, 16, 58, maxCapacity, true);
    }

    void drawFluid(FluidStack fluid, int x, int y, int width, int height, int maxCapacity, boolean tankOverlay) {
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

        if (tankOverlay) {
            mc.renderEngine.bindTexture(WIDGETS);
            drawTexturedModalRect(x, y, 29, 20, 16, 58);
        }
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
    public static void setGLColorFromInt(int color, float alpha) {
        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;
        GL11.glColor4f(red, green, blue, alpha);
    }
}
