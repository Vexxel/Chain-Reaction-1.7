package com.zerren.chainreaction.core.proxy;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

/**
 * Created by Zerren on 2/19/2015.
 */
public class CommonProxy {

    public void initTESR() { }

    public void initISBRH() { }

    public void registerTexReplacements(IIconRegister registry, String folder) { }

    public void initItemRender() { }

    public void initArmorRender() { }

    public void playSoundCentered(World world, float xCoord, float yCoord, float zCoord, String soundName, float volume, float pitch) { }
    public void playSound(World world, float xCoord, float yCoord, float zCoord, String soundName, float volume, float pitch) { }

    public boolean isTheClientPlayer(EntityLivingBase entity) { return false; }

    public void registerEventHandlers() { }

    public void setShader(String shader) { }

    public void removeShader() { }

    public boolean isShaderActive() { return false;}

    public void radiationFX(World world, double x, double y, double z, double velX, double velY, double velZ, int power) { }

    public void steamFX(World world, double x, double y, double z, double velX, double velY, double velZ, float scale) { }

    public void bubbleFX(Entity entity, double velX, double velY, double velZ) { }

}
