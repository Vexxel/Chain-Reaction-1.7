package com.zerren.zedeng.handler;

import com.zerren.zedeng.block.tile.chest.TEChest;
import com.zerren.zedeng.block.tile.vault.TEVaultController;
import com.zerren.zedeng.client.gui.GuiChestZE;
import com.zerren.zedeng.client.gui.GuiKey;
import com.zerren.zedeng.client.gui.GuiVault;
import com.zerren.zedeng.inventory.ContainerChestZE;
import com.zerren.zedeng.inventory.ContainerVault;
import com.zerren.zedeng.reference.GUIs;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by Zerren on 2/19/2015.
 */
public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

        if (id == GUIs.VAULT.ordinal()) {

            TEVaultController vault = (TEVaultController) world.getTileEntity(x, y, z);

            if (vault != null) return new ContainerVault(player.inventory, vault, vault.page);
        }
        if (id == GUIs.CHEST.ordinal()) {

            TEChest chest = (TEChest) world.getTileEntity(x, y, z);

            if (chest != null) return new ContainerChestZE(player.inventory, chest);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){

        if (id == GUIs.KEY.ordinal()) {
            return new GuiKey(player, world, x, y, z);
        }
        if (id == GUIs.VAULT.ordinal()) {

            TEVaultController vault = (TEVaultController) world.getTileEntity(x, y, z);

            if (vault != null) return new GuiVault(vault, player.inventory, vault.page);
        }
        if (id == GUIs.CHEST.ordinal()) {

            TEChest chest = (TEChest) world.getTileEntity(x, y, z);

            if (chest != null) return new GuiChestZE(player.inventory, chest);
        }
        return null;
    }
}