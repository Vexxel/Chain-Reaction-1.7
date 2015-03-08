package com.zerren.zedeng.proxy;

import com.zerren.zedeng.block.tile.chest.TEChest;
import com.zerren.zedeng.block.tile.reactor.TEHeatExchanger;
import com.zerren.zedeng.client.render.item.ItemRendererHeatExchanger;
import com.zerren.zedeng.client.render.item.ItemRendererVaultChest;
import com.zerren.zedeng.client.render.tileentity.TESRChest;
import com.zerren.zedeng.client.render.tileentity.TESRHeatExchanger;
import com.zerren.zedeng.core.ModBlocks;
import com.zerren.zedeng.reference.RenderIDs;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Zerren on 2/19/2015.
 */
public class ClientProxy extends CommonProxy {

    @Override
    public ClientProxy getClientProxy() {
        return this;
    }

    @Override
    public void registerEventHandlers() {

    }

    @Override
    public void initRenderingAndTextures() {
        RenderIDs.vaultChest = RenderingRegistry.getNextAvailableRenderId();
        RenderIDs.exchanger = RenderingRegistry.getNextAvailableRenderId();

        //Vault Chest
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.chest), new ItemRendererVaultChest());
        ClientRegistry.bindTileEntitySpecialRenderer(TEChest.class, new TESRChest());
        //Heat Exchanger
        //MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.exchanger), new ItemRendererHeatExchanger());
        ClientRegistry.bindTileEntitySpecialRenderer(TEHeatExchanger.class, new TESRHeatExchanger());
    }

    @Override
    public void playSound(String soundName, float xCoord, float yCoord, float zCoord, float volume, float pitch) {

    }

    public static EntityPlayer getPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }
}
