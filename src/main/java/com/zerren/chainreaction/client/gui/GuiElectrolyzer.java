package com.zerren.chainreaction.client.gui;

import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.reference.Reference;
import com.zerren.chainreaction.tile.container.ContainerBloomery;
import com.zerren.chainreaction.tile.container.ContainerElectrolyzer;
import com.zerren.chainreaction.tile.mechanism.TEBloomery;
import com.zerren.chainreaction.tile.mechanism.TEElectrolyzer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

/**
 * Created by Zerren on 5/20/2016.
 */
@SideOnly(Side.CLIENT)
public class GuiElectrolyzer extends GuiBase {

    public EntityPlayer player;
    public TEElectrolyzer electrolyzer;
    public int x, y;

    public GuiElectrolyzer(TEElectrolyzer tile, InventoryPlayer inv) {
        super(new ContainerElectrolyzer(inv, tile));
        this.player = inv.player;
        this.electrolyzer = tile;

        this.xSize = 176;
        this.ySize = 177;
    }

    @Override
    public void initGui() {
        super.initGui();

        x = (width - xSize) / 2;
        y = (height - ySize) / 2;

        buttonList.clear();

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
        this.mc.getTextureManager().bindTexture(Reference.Textures.GUIs.ELECTROLYZER);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        //background
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        //fluid tanks
        if (electrolyzer != null) {
            drawFluid(electrolyzer.getInputFluid(), x + 55, y + 19, 16, 58, electrolyzer.inputTank.getCapacity(), Reference.Textures.GUIs.ELECTROLYZER);
            drawTexturedModalRect(x + 55, y + 19, 177, 1, 16, 58);

            drawFluid(electrolyzer.getOutput1Fluid(), x + 107, y + 19, 16, 58, electrolyzer.outputTank1.getCapacity(), Reference.Textures.GUIs.ELECTROLYZER);
            drawTexturedModalRect(x + 107, y + 19, 177, 1, 16, 58);

            drawFluid(electrolyzer.getOutput2Fluid(), x + 125, y + 19, 16, 58, electrolyzer.outputTank2.getCapacity(), Reference.Textures.GUIs.ELECTROLYZER);
            drawTexturedModalRect(x + 125, y + 19, 177, 1, 16, 58);

            //energy bar
            //drawTexturedModalRect(x + 16, y + 24, 198, 0, 8, 48);
            drawEnergyBar(x + 16, y + 24, 8, 48, 198, 0, electrolyzer.getEnergyPercent(48));

            drawProgressBarHorizontal(x + 79, y + 41, 0, 15, 176, 61, electrolyzer.getProgressPercent(18) );
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String invTitle = electrolyzer.getInventoryName();

        drawInventoryName();
        drawContainerName(invTitle);

        //fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 80 + 2, 4210752);
    }
}