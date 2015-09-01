package com.zerren.chainreaction.client.gui;

import com.zerren.chainreaction.tile.vault.TEVaultController;
import com.zerren.chainreaction.client.gui.button.GUIButtonWidgets;
import com.zerren.chainreaction.client.gui.button.GuiButtonVaultCycle;
import com.zerren.chainreaction.handler.PacketHandler;
import com.zerren.chainreaction.handler.network.server.tile.PacketVaultCycle;
import com.zerren.chainreaction.tile.container.ContainerVault;
import com.zerren.chainreaction.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

/**
 * Created by Zerren on 2/24/2015.
 */
@SideOnly(Side.CLIENT)
public class GuiVault extends GuiContainer {

    public EntityPlayer player;
    public TEVaultController controller;
    public int x, y;
    private int selection;

    public GuiVault(TEVaultController tile, InventoryPlayer inv, int pg) {
        super(new ContainerVault(inv, tile, pg));
        this.player = inv.player;

        this.xSize = 176;
        this.ySize = 222;

        this.controller = tile;
        this.selection = controller.selection;
    }

    @Override
    public void initGui() {
        super.initGui();

        x = (width - xSize) / 2;
        y = (height - ySize) / 2;

        buttonList.clear();

        GuiButton previous = new GuiButtonVaultCycle(0, x + 175, y + 21, true);
        GuiButton next = new GuiButtonVaultCycle(1, x + 175, y + 47, false);
        GuiButton confirm = new GUIButtonWidgets(2, x + 175, y + 72, 18, 0, 14, 14);

        buttonList.add(previous);
        buttonList.add(next);
        buttonList.add(confirm);
    }

    @Override
    protected void actionPerformed(GuiButton guibutton) {
        super.actionPerformed(guibutton);

        if (guibutton.id == 0) { //previous
            if (selection <= 0) selection = controller.numPages - 1;
            else selection--;
            PacketHandler.INSTANCE.sendToServer(new PacketVaultCycle(controller, selection, player, false));
        }
        if (guibutton.id == 1) { //next
            if (selection >= controller.numPages - 1) selection = 0;
            else selection++;
            PacketHandler.INSTANCE.sendToServer(new PacketVaultCycle(controller, selection, player, false));
        }
        if (guibutton.id == 2) { //confirm
            PacketHandler.INSTANCE.sendToServer(new PacketVaultCycle(controller, selection, player, true));
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1f, 1f, 1f, 1f);
        this.mc.getTextureManager().bindTexture(Reference.Textures.GUIs.VAULT);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        //background
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        //selection tab
        //xPos (to draw), yPos (to draw), xPos (of overlay), yPos (of overlay), xSize (overlay), ySize (overlay)
        drawTexturedModalRect(x + 173, y + 15, 177, 1, 21, 48); //selection tab
        drawTexturedModalRect(x + 173, y + 68, 177, 50, 21, 22); //accept tab
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String invTitle = controller.getInventoryName();

        fontRendererObj.drawString(invTitle + "  " + (controller.page + 1), 8, 6, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 94, 4210752);

        if (controller.selection < 9)
            fontRendererObj.drawString("" + (controller.selection + 1), 180, 35, 4210752);
        else
            fontRendererObj.drawString("" + (controller.selection + 1), 176, 35, 4210752);
    }
}