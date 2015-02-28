package com.zerren.zedeng.proxy;

/**
 * Created by Zerren on 2/19/2015.
 */
public interface IProxy {

    public abstract ClientProxy getClientProxy();

    public abstract void registerEventHandlers();

    public abstract void initRenderingAndTextures();

    public abstract void playSound(String soundName, float xCoord, float yCoord, float zCoord, float volume, float pitch);
}