package com.zerren.chainreaction.core.proxy;

import chainreaction.api.block.CRBlocks;
import chainreaction.api.item.CRItems;
import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.client.fx.EntityRadiationFX;
import com.zerren.chainreaction.client.fx.EntitySteamFX;
import com.zerren.chainreaction.client.render.block.ISBRHMechanism;
import com.zerren.chainreaction.client.render.block.ISBRHPlumbing;
import com.zerren.chainreaction.client.render.block.ISBRHReactor;
import com.zerren.chainreaction.client.render.item.ItemRendererExchanger;
import com.zerren.chainreaction.client.render.item.ItemRendererMechanism;
import com.zerren.chainreaction.client.render.item.ItemRendererVaultChest;
import com.zerren.chainreaction.client.render.model.armor.ModelO2Mask;
import com.zerren.chainreaction.client.render.model.armor.ModelThrustPack;
import com.zerren.chainreaction.client.render.tileentity.*;
import com.zerren.chainreaction.tile.chest.TEChest;
import com.zerren.chainreaction.tile.mechanism.TEBloomery;
import com.zerren.chainreaction.tile.mechanism.TERTG;
import com.zerren.chainreaction.tile.mechanism.TETeleporter;
import com.zerren.chainreaction.tile.plumbing.TEGasTank;
import com.zerren.chainreaction.tile.plumbing.TEHeatExchanger;
import com.zerren.chainreaction.tile.reactor.TEPressurizedWaterReactor;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.particle.EntityBubbleFX;
import net.minecraft.client.particle.EntityCritFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Zerren on 2/19/2015.
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    public static Random rand = new Random();
    private final Minecraft mc = Minecraft.getMinecraft();

    public static final Map<Item, ModelBiped> armorModels = new HashMap<Item, ModelBiped>();

    @SideOnly(Side.CLIENT)
    public static final IIcon[] tex_replacements = new IIcon[3];

    public ClientProxy getClientProxy() {
        return this;
    }

    @Override
    public void registerEventHandlers() {

    }

    @Override
    public void updateTileModel(TileEntity tile) {
        FMLClientHandler.instance().getClient().renderGlobal.markBlockForRenderUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
    }

    @Override
    public EntityPlayer getPlayerEntity(MessageContext ctx) {
        // Note that if you simply return 'Minecraft.getMinecraft().thePlayer',
        // your packets will not work as expected because you will be getting a
        // client player even when you are on the server!
        // Sounds absurd, but it's true.

        // Solution is to double-check side before returning the player:
        ChainReaction.log.info("Retrieving player from ClientProxy for message on side " + ctx.side);
        return (ctx.side.isClient() ? mc.thePlayer : super.getPlayerEntity(ctx));
    }

    public boolean isTheClientPlayer(EntityLivingBase entity) {
        return entity == Minecraft.getMinecraft().thePlayer;
    }

    //Things that update a lot in accordance to their state, or large block models that take up more than a single block. Need an ItemRenderer handler as well, for
    //items that look just like their block.
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
        //Bloomery
        ClientRegistry.bindTileEntitySpecialRenderer(TEBloomery.class, new TESRBloomery());
        //RTG
        ClientRegistry.bindTileEntitySpecialRenderer(TERTG.class, new TESRRTG());

    }

    //Simple block renderer--things that won't get updated (mostly ever). Good for static held items of blocks as well
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
        //Mechanism
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(CRBlocks.mechanism), new ItemRendererMechanism());

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
    public void playSoundAtEntity(World world, Entity entity, String soundName, float volume, float pitch) {
        world.playSoundAtEntity(entity, soundName, volume, pitch);
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
    public void magicCritFX(Entity entity, double velX, double velY, double velZ) {

        if (shouldSpawnParticle()) {
            EntityFX fx = new EntityCritFX(entity.worldObj, entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ, velX, velY, velZ);
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
}
