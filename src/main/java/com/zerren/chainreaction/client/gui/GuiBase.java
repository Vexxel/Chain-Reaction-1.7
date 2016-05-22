package com.zerren.chainreaction.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.StatCollector;

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
}
