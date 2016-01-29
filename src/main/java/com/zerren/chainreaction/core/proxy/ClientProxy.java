package com.zerren.chainreaction.core.proxy;

import chainreaction.api.block.CRBlocks;
import chainreaction.api.item.CRItems;
import com.zerren.chainreaction.client.fx.EntityRadiationFX;
import com.zerren.chainreaction.client.fx.EntitySteamFX;
import com.zerren.chainreaction.client.render.block.ISBRHMechanism;
import com.zerren.chainreaction.client.render.block.ISBRHPlumbing;
import com.zerren.chainreaction.client.render.block.ISBRHReactor;
import com.zerren.chainreaction.client.render.item.ItemRendererExchanger;
import com.zerren.chainreaction.client.render.item.ItemRendererVaultChest;
import com.zerren.chainreaction.client.render.model.armor.ModelO2Mask;
import com.zerren.chainreaction.client.render.model.armor.ModelThrustPack;
import com.zerren.chainreaction.client.render.tileentity.*;
import com.zerren.chainreaction.reference.Reference;
import com.zerren.chainreaction.tile.chest.TEChest;
import com.zerren.chainreaction.tile.mechanism.TETeleporter;
import com.zerren.chainreaction.tile.plumbing.TEGasTank;
import com.zerren.chainreaction.tile.plumbing.TEHeatExchanger;
import com.zerren.chainreaction.tile.reactor.TEPressurizedWaterReactor;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.particle.EntityBubbleFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.RenderPlayerEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Zerren on 2/19/2015.
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    public static Random rand = new Random();

    public static final Map<Item, ModelBiped> armorModels = new HashMap<Item, ModelBiped>();

    @SideOnly(Side.CLIENT)
    public static final IIcon[] tex_replacements = new IIcon[2];

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

    public void registerTexReplacements(IIconRegister registry, String folder) {
        tex_replacements[0] = registry.registerIcon(Reference.ModInfo.CR_RESOURCE_PREFIX + folder + "pipe_mouth");
        tex_replacements[1] = registry.registerIcon(Reference.ModInfo.CR_RESOURCE_PREFIX + folder + "distributionChamber_input");
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
        //Teleporter
        ClientRegistry.bindTileEntitySpecialRenderer(TETeleporter.class, new TESRTeleporter());
    }

    @Override
    public void initISBRH() {
        //Heat Exchanger
        RenderingRegistry.registerBlockHandler(ISBRHPlumbing.model, new ISBRHPlumbing());
        //Mechanism
        RenderingRegistry.registerBlockHandler(ISBRHMechanism.model, new ISBRHMechanism());
        //Reactor
        RenderingRegistry.registerBlockHandler(ISBRHReactor.model, new ISBRHReactor());
    }

    @Override
    public void initItemRender() {
        //Vault Chest
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(CRBlocks.chest), new ItemRendererVaultChest());
        //Plumbing
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(CRBlocks.plumbing), new ItemRendererExchanger());
    }

    @Override
    public void initArmorRender() {
        armorModels.put(CRItems.o2mask, new ModelO2Mask());
        armorModels.put(CRItems.thrustPack, new ModelThrustPack());
    }

    @Override
    public void playSoundCentered(World world, float x, float y, float z, String soundName, float volume, float pitch) {
        world.playSoundEffect(Math.floor(x) + 0.5, Math.floor(y) + 0.5, Math.floor(z) + 0.5, soundName, volume, pitch);
    }

    @Override
    public void playSound(World world, float x, float y, float z, String soundName, float volume, float pitch) {
        world.playSoundEffect(x, y, z, soundName, volume, pitch);
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

    @Override
    public void bubbleFX(Entity entity, double velX, double velY, double velZ) {

        if (shouldSpawnParticle() && entity.isInWater()) {
            EntityFX fx = new EntityBubbleFX(entity.worldObj, entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ, velX, velY, velZ);
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

    @SubscribeEvent
    public void onPostRender(RenderPlayerEvent.Specials.Post event) {
        AbstractClientPlayer player = (AbstractClientPlayer) event.entityPlayer;
        boolean flag = (event.entityPlayer.getCommandSenderName().equals("Zerren"));
    }
}
