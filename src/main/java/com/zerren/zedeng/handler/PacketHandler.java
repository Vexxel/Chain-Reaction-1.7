package com.zerren.zedeng.handler;

import com.zerren.zedeng.handler.network.packet.PacketVaultCycle;
import com.zerren.zedeng.reference.Reference;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * Created by Zerren on 2/25/2015.
 */
public class PacketHandler {

    public static final SimpleNetworkWrapper netHandler = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_CHANNEL_SIMPLE);

    public static void init() {
        netHandler.registerMessage(PacketVaultCycle.class, PacketVaultCycle.class, 0, Side.SERVER);
    }
}
