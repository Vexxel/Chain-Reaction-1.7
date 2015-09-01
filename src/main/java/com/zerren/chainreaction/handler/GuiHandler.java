package com.zerren.chainreaction.handler;

import com.zerren.chainreaction.tile.chest.TEChest;
import com.zerren.chainreaction.tile.vault.TEVaultController;
import com.zerren.chainreaction.client.gui.GuiChestCR;
import com.zerren.chainreaction.client.gui.GuiKey;
import com.zerren.chainreaction.client.gui.GuiVault;
import com.zerren.chainreaction.tile.container.ContainerChestCR;
import com.zerren.chainreaction.tile.container.ContainerVault;
import com.zerren.chainreaction.reference.Reference;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by Zerren on 2/19/2015.
 */
public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

        if (id == Reference.GUIs.VAULT.ordinal()) {

            TEVaultController vault = (TEVaultController) world.getTileEntity(x, y, z);

            if (vault != null) return new ContainerVault(player.inventory, vault, vault.page);
        }
        if (id == Reference.GUIs.CHEST.ordinal()) {

            TEChest chest = (TEChest) world.getTileEntity(x, y, z);

            if (chest != null) return new ContainerChestCR(player.inventory, chest);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){

        if (id == Reference.GUIs.KEY.ordinal()) {
            return new GuiKey(player, world, x, y, z);
        }
        if (id == Reference.GUIs.VAULT.ordinal()) {

            TEVaultController vault = (TEVaultController) world.getTileEntity(x, y, z);

            if (vault != null) return new GuiVault(vault, player.inventory, vault.page);
        }
        if (id == Reference.GUIs.CHEST.ordinal()) {

            TEChest chest = (TEChest) world.getTileEntity(x, y, z);

            if (chest != null) return new GuiChestCR(player.inventory, chest);
        }
        return null;
    }
}