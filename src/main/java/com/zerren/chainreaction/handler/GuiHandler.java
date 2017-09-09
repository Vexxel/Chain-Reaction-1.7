package com.zerren.chainreaction.handler;

import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.client.gui.*;
import com.zerren.chainreaction.tile.TileEntityCRBase;
import com.zerren.chainreaction.tile.chest.TEChest;
import com.zerren.chainreaction.tile.container.*;
import com.zerren.chainreaction.tile.mechanism.TEBloomery;
import com.zerren.chainreaction.tile.reactor.TERTG;
import com.zerren.chainreaction.tile.reactor.TEPressurizedWaterReactor;
import com.zerren.chainreaction.tile.vault.TEVaultController;
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
        if (id == Reference.GUIs.PWR.ordinal()) {

            TEPressurizedWaterReactor reactor = (TEPressurizedWaterReactor) world.getTileEntity(x, y, z);

            if (reactor != null) return new ContainerPWR(player.inventory, reactor);
        }
        if (id == Reference.GUIs.BLOOMERY.ordinal()) {

            TEBloomery bloomery = (TEBloomery) world.getTileEntity(x, y, z);

            if (bloomery != null) return new ContainerBloomery(player.inventory, bloomery);
        }
        if (id == Reference.GUIs.RTG.ordinal()) {

            TERTG rtg = (TERTG) world.getTileEntity(x, y, z);

            if (rtg != null) return new ContainerRTG(player.inventory, rtg);
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
        if (id == Reference.GUIs.PWR.ordinal()) {

            TEPressurizedWaterReactor reactor = (TEPressurizedWaterReactor) world.getTileEntity(x, y, z);

            if (reactor != null) return new GuiPWR(reactor, player.inventory);
        }
        if (id == Reference.GUIs.BLOOMERY.ordinal()) {

            TEBloomery bloomery = (TEBloomery) world.getTileEntity(x, y, z);

            if (bloomery != null) return new GuiBloomery(bloomery, player.inventory);
        }
        if (id == Reference.GUIs.RTG.ordinal()) {

            TERTG rtg = (TERTG) world.getTileEntity(x, y, z);

            if (rtg != null) return new GuiRTG(rtg, player.inventory);
        }
        return null;
    }

    public static void openGui(EntityPlayer player, Reference.GUIs gui, World world, int[] masterPosition) {
        player.openGui(ChainReaction.instance, gui.ordinal(), world, masterPosition[0], masterPosition[1], masterPosition[2]);
    }
    public static void openGui(EntityPlayer player, Reference.GUIs gui, World world, TileEntityCRBase tile) {
        player.openGui(ChainReaction.instance, gui.ordinal(), world, tile.xCoord, tile.yCoord, tile.zCoord);
    }
}