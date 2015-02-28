package com.zerren.zedeng.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Zerren on 2/19/2015.
 */
public class ClientProxy extends CommonProxy {
    @Override
    public ClientProxy getClientProxy() {
        return null;
    }

    @Override
    public void registerEventHandlers() {

    }

    @Override
    public void initRenderingAndTextures() {

    }

    @Override
    public void playSound(String soundName, float xCoord, float yCoord, float zCoord, float volume, float pitch) {

    }

    public static EntityPlayer getPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }
}
