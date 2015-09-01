package com.zerren.chainreaction.client.gui;

import com.zerren.chainreaction.tile.chest.TEChest;
import com.zerren.chainreaction.tile.container.ContainerChestCR;
import com.zerren.chainreaction.reference.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

/**
 * Created by Zerren on 2/28/2015.
 */
public class GuiChestCR extends GuiContainer {

    private TEChest chest;

    public GuiChestCR(InventoryPlayer inv, TEChest chest) {
        super(new ContainerChestCR(inv, chest));
        this.chest = chest;

        switch (chest.getState()) {
            case 0: //brick chest
                xSize = 176;
                ySize = 166;
                break;
            case 1: //thaumium chest
                xSize = 176;
                ySize = 202;
                break;
            case 2: //void chest
                xSize = 212;
                ySize = 238;
                break;
            default:
                xSize = 176;
                ySize = 166;
                break;
        }

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float opactiy, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        ResourceLocation texture;
        switch (chest.getState()) {
            case 0: texture = Reference.Textures.GUIs.CHEST_BRICK; break;
            case 1: texture = Reference.Textures.GUIs.CHEST_THAUMIUM; break;
            case 2: texture = Reference.Textures.GUIs.CHEST_VOID; break;

            default: texture = Reference.Textures.GUIs.CHEST_BRICK; break;
        }

        mc.getTextureManager().bindTexture(texture);

        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String invTitle = chest.getInventoryName();

        if (chest.getState() == 2) {
            fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 26, this.ySize - 95 + 2, 16764159);
            fontRendererObj.drawString(invTitle, 8, 6, 16764159);
        }
        else {
            fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 95 + 2, 4210752);
            fontRendererObj.drawString(invTitle, 8, 6, 4210752);
        }
    }
}
