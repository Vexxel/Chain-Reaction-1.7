package com.zerren.zedeng.proxy;

/**
 * Created by Zerren on 2/19/2015.
 */
public class ServerProxy extends CommonProxy {
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
}