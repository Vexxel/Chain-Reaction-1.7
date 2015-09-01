package com.zerren.chainreaction.core.proxy;

import com.zerren.chainreaction.client.render.tileentity.TESRPressurizedWaterReactor;
import com.zerren.chainreaction.tile.reactor.TEPressurizedWaterReactor;
import chainreaction.api.block.CRBlocks;
import com.zerren.chainreaction.tile.chest.TEChest;
import com.zerren.chainreaction.tile.plumbing.TEGasTank;
import com.zerren.chainreaction.tile.plumbing.TEHeatExchanger;
import com.zerren.chainreaction.client.fx.EntityRadiationFX;
import com.zerren.chainreaction.client.fx.EntitySteamFX;
import com.zerren.chainreaction.client.render.block.ISBRHPlumbing;
import com.zerren.chainreaction.client.render.item.ItemRendererExchanger;
import com.zerren.chainreaction.client.render.item.ItemRendererVaultChest;
import com.zerren.chainreaction.client.render.tileentity.TESRChest;
import com.zerren.chainreaction.client.render.tileentity.TESRGasTank;
import com.zerren.chainreaction.client.render.tileentity.TESRHeatExchanger;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

import java.util.Random;

/**
 * Created by Zerren on 2/19/2015.
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    public static Random rand = new Random();

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
    public void initTESR() {
        //Vault Chest
        ClientRegistry.bindTileEntitySpecialRenderer(TEChest.class, new TESRChest());
        //Heat Exchanger
        ClientRegistry.bindTileEntitySpecialRenderer(TEHeatExchanger.class, new TESRHeatExchanger());
        //Gas Tank
        ClientRegistry.bindTileEntitySpecialRenderer(TEGasTank.class, new TESRGasTank());
        //PWR
        ClientRegistry.bindTileEntitySpecialRenderer(TEPressurizedWaterReactor.class, new TESRPressurizedWaterReactor());
    }

    @Override
    public void initISBRH() {
        //Heat Exchanger
        RenderingRegistry.registerBlockHandler(ISBRHPlumbing.exchangerModel, new ISBRHPlumbing());
    }

    @Override
    public void initItemRender() {
        //Vault Chest
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(CRBlocks.chest), new ItemRendererVaultChest());
        //Plumbing
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(CRBlocks.plumbing), new ItemRendererExchanger());
    }

    @Override
    public void playSound(World world, float x, float y, float z, String soundName, float volume, float pitch) {
        world.playSoundEffect(Math.floor(x) + 0.5, Math.floor(y) + 0.5, Math.floor(z) + 0.5, soundName, volume, pitch);
    }

    @Override
    public void setShader(String shader) {
        while(!FMLClientHandler.instance().getClient().entityRenderer.isShaderActive() || !(FMLClientHandler.instance().getClient().entityRenderer.getShaderGroup().getShaderGroupName().equals("minecraft:shaders/post/" + shader + ".json")))
            FMLClientHandler.instance().getClient().entityRenderer.activateNextShader();
    }

    @Override
    public void removeShader() {
        FMLClientHandler.instance().getClient().entityRenderer.deactivateShader();
    }

    @Override
    public boolean isShaderActive() {
        return FMLClientHandler.instance().getClient().entityRenderer.isShaderActive();
    }

    @Override
    public void radiationFX(World world, double x, double y, double z, double velX, double velY, double velZ, int power) {

        if (shouldSpawnParticle()) {
            EntityRadiationFX fx = new EntityRadiationFX(world, x, y, z, velX, velY, velZ, power);
            FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
        }
    }

    @Override
    public void steamFX(World world, double x, double y, double z, double velX, double velY, double velZ, float scale) {

        if (shouldSpawnParticle()) {
            EntityFX fx = new EntitySteamFX(world, x, y, z, velX, velY, velZ, scale);
            FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
        }
    }

    private boolean shouldSpawnParticle() {
        Minecraft mc = FMLClientHandler.instance().getClient();

        int particleSetting = mc.gameSettings.particleSetting;

        if (particleSetting == 1 && rand.nextInt(3) == 0) {
            particleSetting = 2;
        }

        return particleSetting <= 1;
    }
}
