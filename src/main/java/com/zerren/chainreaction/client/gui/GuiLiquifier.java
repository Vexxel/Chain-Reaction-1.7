package com.zerren.chainreaction.client.gui;

import com.zerren.chainreaction.client.gui.button.GUIButtonWidgets;
import com.zerren.chainreaction.handler.network.PacketHandler;
import com.zerren.chainreaction.handler.network.server.tile.MessageUpgradeInstall;
import com.zerren.chainreaction.reference.Reference;
import com.zerren.chainreaction.tile.container.ContainerLiquifier;
import com.zerren.chainreaction.tile.mechanism.TELiquifier;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by Zerren on 5/20/2016.
 */
@SideOnly(Side.CLIENT)
public class GuiLiquifier extends GuiContainerCR {

    public EntityPlayer player;
    public TELiquifier liquifier;
    public int x, y;
    private static final ResourceLocation GUI_TEX = Reference.Textures.GUIs.LIQUIFIER;

    public GuiLiquifier(TELiquifier tile, InventoryPlayer inv) {
        super(new ContainerLiquifier(inv, tile), tile);
        this.player = inv.player;
        this.liquifier = tile;

        this.xSize = 198;
        this.ySize = 177;
    }

    @Override
    public void initGui() {
        super.initGui();

        x = (width - xSize) / 2;
        y = (height - ySize) / 2;

        buttonList.clear();

        GuiButton install = new GUIButtonWidgets(0, x + 175, y + 66, 100, 0);
        buttonList.add(install);
    }

    @Override
    protected void actionPerformed(GuiButton guibutton) {
        super.actionPerformed(guibutton);

        if (guibutton.id == 0) {
            PacketHandler.INSTANCE.sendToServer(new MessageUpgradeInstall(liquifier));
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1f, 1f, 1f, 1f);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        drawBackground(x, y, GUI_TEX);

        if (liquifier != null) {
            drawFluidTank(liquifier.getInputFluid(), x + 53, y + 19, liquifier.inputTank.getCapacity());
            drawFluidTank(liquifier.getOutput1Fluid(), x + 107, y + 19, liquifier.outputTank1.getCapacity());

            drawEnergyBar(x + 16, y + 24, liquifier.getEnergyStorage());

            drawProgressBarHorizontal(x + 78, y + 39, 0, 18, 236, 0, liquifier.getProgressPercent(18), GUI_TEX);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String invTitle = liquifier.getInventoryName();

        drawInventoryName();
        drawContainerName(invTitle);
        drawUpgradeInstallTooltip(liquifier, mouseX, mouseY, 175, 66);

        if (liquifier != null) {
            drawEnergyBarTooltip(liquifier.getEnergyStorage(), mouseX, mouseY, 16, 24, 8, 48);

            drawFluidTankTooltip(liquifier.inputTank, mouseX, mouseY, 53, 19, 16, 58);
            drawFluidTankTooltip(liquifier.outputTank1, mouseX, mouseY, 107, 19, 16, 58);
        }
    }
}