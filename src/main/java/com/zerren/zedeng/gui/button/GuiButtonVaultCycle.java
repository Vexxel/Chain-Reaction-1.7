package com.zerren.zedeng.gui.button;

import com.zerren.zedeng.reference.Textures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

/**
 * Created by Zerren on 2/24/2015.
 */
@SideOnly(Side.CLIENT)
public class GuiButtonVaultCycle extends GuiButton {

    boolean previous;

    public GuiButtonVaultCycle(int par1, int par2, int par3, boolean previous) {
        super(par1, par2, par3, 14, 10, "");
        this.previous = previous;
    }

    @Override
    public void drawButton(Minecraft mc, int par2, int par3) {
        if(!enabled) {
            return;
        }
        final int x = 200;
        final int y = (previous ? 0 : 12);

        mc.getTextureManager().bindTexture(Textures.guis.VAULT);
        drawTexturedModalRect(xPosition, yPosition, x, y, 14, 10);
    }
}