package com.zerren.zedeng.proxy;

import com.zerren.zedeng.block.tile.chest.TEChest;
import com.zerren.zedeng.block.tile.reactor.TEHeatExchanger;
import com.zerren.zedeng.client.render.item.ItemRendererHeatExchanger;
import com.zerren.zedeng.client.render.item.ItemRendererVaultChest;
import com.zerren.zedeng.client.render.tileentity.TESRChest;
import com.zerren.zedeng.client.render.tileentity.TESRHeatExchanger;
import com.zerren.zedeng.core.ModBlocks;
import com.zerren.zedeng.reference.RenderIDs;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Zerren on 2/19/2015.
 */
public class ClientProxy extends CommonProxy {

    public ClientProxy getClientProxy() {
        return this;
    }

    @Override
    public void registerEventHandlers() {

    }

    public static EntityPlayer getPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    public boolean isTheClientPlayer(EntityLivingBase entity) {
        return entity == Minecraft.getMinecraft().thePlayer;
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

    @SideOnly(Side.CLIENT)
    public void setShader(String shader) {
        while(FMLClientHandler.instance().getClient().entityRenderer.isShaderActive() ? !(FMLClientHandler.instance().getClient().entityRenderer.getShaderGroup().getShaderGroupName().equals("minecraft:shaders/post/" + shader + ".json")) : true)
            FMLClientHandler.instance().getClient().entityRenderer.activateNextShader();
    }

    @SideOnly(Side.CLIENT)
    public void removeShader() {
        FMLClientHandler.instance().getClient().entityRenderer.deactivateShader();
    }

    @SideOnly(Side.CLIENT)
    public boolean isShaderActive() {
        return FMLClientHandler.instance().getClient().entityRenderer.isShaderActive();
    }
}
