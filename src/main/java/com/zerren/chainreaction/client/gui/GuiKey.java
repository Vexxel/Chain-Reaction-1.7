package com.zerren.chainreaction.client.gui;

import com.zerren.chainreaction.client.gui.button.GUIButtonWidgets;
import com.zerren.chainreaction.client.gui.button.GuiButtonKeyCycle;
import com.zerren.chainreaction.handler.PacketHandler;
import com.zerren.chainreaction.handler.network.server.player.MessageKeyCut;
import com.zerren.chainreaction.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

/**
 * Created by Zerren on 2/20/2015.
 */
public class GuiKey extends GuiScreen {

    public World world;
    public int x, y;
    public EntityPlayer player;

    protected int xSize = 134;
    protected int ySize = 96;

    private int keyTier;

    private short[] config = {0, 0, 0, 0, 0};

    public GuiKey(EntityPlayer player, World world, int x, int y, int z) {
        this.world = world;
        this.player = player;
        this.keyTier = player.inventory.getCurrentItem().getItemDamage();
    }

    @Override
    public void initGui() {
        super.initGui();
        x = (width - xSize) / 2;
        y = (height - ySize) / 2;

        buttonList.clear();

        for (int i = 0; i < keyTier + 1; i++) {
            GuiButton previous = new GuiButtonKeyCycle(i, x + 24 + (i * 18), y + 33, true);
            GuiButton next = new GuiButtonKeyCycle(i + 5, x + 24 + (i * 18), y + 63, false);

            buttonList.add(previous);
            buttonList.add(next);
        }

        GuiButton confirm = new GUIButtonWidgets(10, x + 58, y + 11, 0, 0);

        buttonList.add(confirm);
    }

    @Override
    protected void actionPerformed(GuiButton guibutton) {
        switch(guibutton.id) {
            case 0:
                if (config[0] <= 0)
                    config[0] = 5;
                else
                    config[0]--;
                return;
            case 1:
                if (config[1] <= 0)
                    config[1] = 5;
                else
                    config[1]--;
                return;
            case 2:
                if (config[2] <= 0)
                    config[2] = 5;
                else
                    config[2]--;
                return;
            case 3:
                if (config[3] <= 0)
                    config[3] = 5;
                else
                    config[3]--;
                return;
            case 4:
                if (config[4] <= 0)
                    config[4] = 5;
                else
                    config[4]--;
                return;

            case 5:
                if (config[0] < 5)
                    config[0]++;
                else
                    config[0] = 0;
                return;
            case 6:
                if (config[1] < 5)
                    config[1]++;
                else
                    config[1] = 0;
                return;
            case 7:
                if (config[2] < 5)
                    config[2]++;
                else
                    config[2] = 0;
                return;
            case 8:
                if (config[3] < 5)
                    config[3]++;
                else
                    config[3] = 0;
                return;
            case 9:
                if (config[4] < 5)
                    config[4]++;
                else
                    config[4] = 0;
                return;

            case 10:
                if (config[0] == 0 && config[1] == 0) {
                    return;
                }
                ItemStack stack = player.inventory.getCurrentItem();

                stack.stackTagCompound = new NBTTagCompound();
                stack.stackTagCompound.setString("code", codeBuilder(keyTier));

                PacketHandler.INSTANCE.sendToServer(new MessageKeyCut(codeBuilder(keyTier)));
                Minecraft.getMinecraft().displayGuiScreen(null);
        }
    }

    private String codeBuilder(int tier) {
        String code = "";
        for (int e = 0; e < tier + 1; e++) {
            code += config[e];
        }
        return code;
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        GL11.glColor4f(1f, 1f, 1f, 1f);
        this.mc.getTextureManager().bindTexture(Reference.Textures.GUIs.KEY);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        //location of the configuration overlays
        int patternU = 0;
        int patternV = 96;

        //where to draw the configuration overlays (determined by the configuration of each cell)
        //xPos (to draw), yPos (to draw), xPos (of overlay), yPos (of overlay), xSize (overlay), ySize(overlay)
        drawTexturedModalRect(x + 23, y + 45, patternU + (config[0] * 16), patternV, 16, 16);
        drawTexturedModalRect(x + 41, y + 45, patternU + (config[1] * 16), patternV, 16, 16);
        switch (keyTier) {
            case 1: //iron
                config[2] = 6;
                config[3] = 6;
                config[4] = 6;
                break;
            case 2: //gold
                config[3] = 6;
                config[4] = 6;
                break;
            case 3: //emerald
                config[4] = 6;
                break;
        }
        drawTexturedModalRect(x + 59, y + 45, patternU + (config[2] * 16), patternV, 16, 16);
        drawTexturedModalRect(x + 77, y + 45, patternU + (config[3] * 16), patternV, 16, 16);
        drawTexturedModalRect(x + 95, y + 45, patternU + (config[4] * 16), patternV, 16, 16);

        super.drawScreen(par1, par2, par3);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}