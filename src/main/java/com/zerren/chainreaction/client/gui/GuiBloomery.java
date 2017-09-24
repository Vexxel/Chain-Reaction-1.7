package com.zerren.chainreaction.client.gui;

import com.zerren.chainreaction.reference.Reference;
import com.zerren.chainreaction.tile.container.ContainerBloomery;
import com.zerren.chainreaction.tile.mechanism.TEBloomery;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

/**
 * Created by Zerren on 5/20/2016.
 */
@SideOnly(Side.CLIENT)
public class GuiBloomery extends GuiContainerCR {

    public EntityPlayer player;
    public TEBloomery bloomery;
    public int x, y;

    public GuiBloomery(TEBloomery tile, InventoryPlayer inv) {
        super(new ContainerBloomery(inv, tile), tile);
        this.player = inv.player;

        this.bloomery = tile;
    }

    @Override
    public void initGui() {
        super.initGui();

        x = (width - xSize) / 2;
        y = (height - ySize) / 2;

        buttonList.clear();

        /*GuiButton previous = new GuiButtonVaultCycle(0, x + 175, y + 21, true);
        GuiButton next = new GuiButtonVaultCycle(1, x + 175, y + 47, false);
        GuiButton confirm = new GUIButtonWidgets(2, x + 175, y + 72, 18, 0, 14, 14);

        buttonList.add(previous);
        buttonList.add(next);
        buttonList.add(confirm);*/
    }

    @Override
    protected void actionPerformed(GuiButton guibutton) {
        super.actionPerformed(guibutton);

        /*if (guibutton.id == 0) { //previous
            if (selection <= 0) selection = controller.numPages - 1;
            else selection--;
            PacketHandler.INSTANCE.sendToServer(new MessageVaultCycle(controller, selection, player, false));
        }
        if (guibutton.id == 1) { //next
            if (selection >= controller.numPages - 1) selection = 0;
            else selection++;
            PacketHandler.INSTANCE.sendToServer(new MessageVaultCycle(controller, selection, player, false));
        }
        if (guibutton.id == 2) { //confirm
            PacketHandler.INSTANCE.sendToServer(new MessageVaultCycle(controller, selection, player, true));
        }*/
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1f, 1f, 1f, 1f);
        this.mc.getTextureManager().bindTexture(Reference.Textures.GUIs.BLOOMERY);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        //background
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        /*//selection tab
        //xPos (to draw), yPos (to draw), xPos (of overlay), yPos (of overlay), xSize (overlay), ySize (overlay)
        drawTexturedModalRect(x + 173, y + 15, 177, 1, 21, 48); //selection tab
        drawTexturedModalRect(x + 173, y + 68, 177, 50, 21, 22); //accept tab*/
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String invTitle = bloomery.getInventoryName();

        drawInventoryName();
        drawContainerName(invTitle);
    }
}