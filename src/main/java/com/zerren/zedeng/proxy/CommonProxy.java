package com.zerren.zedeng.proxy;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Zerren on 2/19/2015.
 */
public class CommonProxy {

    public void initRenderingAndTextures() { }

    public void playSound(String soundName, float xCoord, float yCoord, float zCoord, float volume, float pitch) { }

    public boolean isTheClientPlayer(EntityLivingBase entity) { return false; }

    public void registerEventHandlers() { }

    public void setShader(String shader) { }

    public void removeShader() { }

    public boolean isShaderActive() { return false;}

}
