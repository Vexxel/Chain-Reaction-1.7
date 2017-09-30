package com.zerren.chainreaction.client.gui;

import chainreaction.api.tile.IUpgradeableTile;
import com.zerren.chainreaction.client.gui.button.GUIButtonWidgets;
import com.zerren.chainreaction.handler.network.PacketHandler;
import com.zerren.chainreaction.handler.network.server.tile.MessageUpgradeInstall;
import com.zerren.chainreaction.handler.network.server.tile.MessageVaultCycle;
import com.zerren.chainreaction.reference.Reference;
import com.zerren.chainreaction.tile.container.ContainerElectrolyzer;
import com.zerren.chainreaction.tile.mechanism.TEElectrolyzer;
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
public class GuiElectrolyzer extends GuiContainerCR {

    public EntityPlayer player;
    public TEElectrolyzer electrolyzer;
    public int x, y;
    private static final ResourceLocation GUI_TEX = Reference.Textures.GUIs.ELECTROLYZER;

    public GuiElectrolyzer(TEElectrolyzer tile, InventoryPlayer inv) {
        super(new ContainerElectrolyzer(inv, tile), tile);
        this.player = inv.player;
        this.electrolyzer = tile;

        this.xSize = 198;
        this.ySize = 177;
    }

    @Override
    public void initGui() {
        super.initGui();

        x = (width - xSize) / 2;
        y = (height - ySize) / 2;

        buttonList.clear();

        GuiButton install = new GUIButtonWidgets(0, x + 175, y + 60, 100, 0);
        buttonList.add(install);

    }

    @Override
    protected void actionPerformed(GuiButton guibutton) {
        super.actionPerformed(guibutton);

        if (guibutton.id == 0) {
            PacketHandler.INSTANCE.sendToServer(new MessageUpgradeInstall(electrolyzer));
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1f, 1f, 1f, 1f);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        drawBackground(x, y, GUI_TEX);

        if (electrolyzer != null) {
            drawFluidTank(electrolyzer.getInputFluid(), x + 55, y + 19, electrolyzer.inputTank.getCapacity());
            drawFluidTank(electrolyzer.getOutput1Fluid(), x + 107, y + 19, electrolyzer.outputTank1.getCapacity());
            drawFluidTank(electrolyzer.getOutput2Fluid(), x + 125, y + 19, electrolyzer.outputTank2.getCapacity());

            drawEnergyBar(x + 16, y + 24, electrolyzer.getEnergyStorage());

            drawProgressBarHorizontal(x + 79, y + 41, 0, 15, 238, 0, electrolyzer.getProgressPercent(18), GUI_TEX);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String invTitle = electrolyzer.getInventoryName();

        drawInventoryName();
        drawContainerName(invTitle);
        drawUpgradeInstallTooltip(electrolyzer, mouseX, mouseY, 175, 60);
        drawMachineStatsTooltip(electrolyzer, mouseX, mouseY, 176, 80);

        if (electrolyzer != null) {
            drawEnergyBarTooltip(electrolyzer.getEnergyStorage(), mouseX, mouseY, 16, 24, 8, 48);

            drawFluidTankTooltip(electrolyzer.inputTank, mouseX, mouseY, 55, 19, 16, 58);
            drawFluidTankTooltip(electrolyzer.outputTank1, mouseX, mouseY, 107, 19, 16, 58);
            drawFluidTankTooltip(electrolyzer.outputTank2, mouseX, mouseY, 125, 19, 16, 58);
        }
    }
}