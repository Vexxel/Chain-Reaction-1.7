package com.zerren.zedeng.client.gui.button;

import com.zerren.zedeng.reference.Textures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

/**
 * Created by Zerren on 2/21/2015.
 */
@SideOnly(Side.CLIENT)
public class GUIButtonWidgets extends GuiButton {

    int u, v, sX = 18, sY = 18;
    //button ID, xPos, yPos, u, v, overlaySizeX, overlaySizeY
    public GUIButtonWidgets(int id, int xPos, int yPos, int u, int v, int xSize, int ySize) {
        super(id, xPos, yPos, xSize, ySize, "");
        this.u = u;
        this.v = v;
        this.sX = xSize;
        this.sY = ySize;
    }

    //button ID, xPos, yPos, u, v (size 18x18)
    public GUIButtonWidgets(int id, int xPos, int yPos, int u, int v) {
        super(id, xPos, yPos, 18, 18, "");
        this.u = u;
        this.v = v;
    }

    @Override
    public void drawButton(Minecraft mc, int par2, int par3) {
        if(!enabled) {
            return;
        }

        mc.getTextureManager().bindTexture(Textures.GUIs.WIDGETS);
        drawTexturedModalRect(xPosition, yPosition, u, v, sX, sY);
    }
}