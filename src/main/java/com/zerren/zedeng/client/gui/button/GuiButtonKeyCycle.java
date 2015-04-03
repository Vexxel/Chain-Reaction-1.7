package com.zerren.zedeng.client.gui.button;

import com.zerren.zedeng.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

/**
 * Created by Zerren on 2/21/2015.
 */
@SideOnly(Side.CLIENT)
public class GuiButtonKeyCycle extends GuiButton {

    boolean previous;

    public GuiButtonKeyCycle(int par1, int par2, int par3, boolean previous) {
        super(par1, par2, par3, 14, 10, "");
        this.previous = previous;
    }

    @Override
    public void drawButton(Minecraft mc, int par2, int par3) {
        if(!enabled) {
            return;
        }
        final int x = 136;
        final int y = (previous ? 1 : 13);

        mc.getTextureManager().bindTexture(Reference.Textures.GUIs.KEY);
        drawTexturedModalRect(xPosition, yPosition, x, y, 14, 10);
    }
}