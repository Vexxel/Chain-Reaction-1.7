package com.zerren.zedeng.handler;

import com.zerren.zedeng.handler.network.client.player.MessageShader;
import com.zerren.zedeng.handler.network.client.tile.MessageTileChest;
import com.zerren.zedeng.handler.network.client.tile.MessageTileMultiblock;
import com.zerren.zedeng.handler.network.client.tile.MessageTileVault;
import com.zerren.zedeng.handler.network.client.tile.MessageTileZE;
import com.zerren.zedeng.handler.network.server.player.MessageKeyCut;
import com.zerren.zedeng.handler.network.server.tile.PacketVaultCycle;
import com.zerren.zedeng.reference.Reference;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * Created by Zerren on 2/25/2015.
 */
public class PacketHandler {

    public static final SimpleNetworkWrapper netHandler = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.ModInfo.MOD_CHANNEL_SIMPLE);

    public static void init() {
        //gui to tile
        netHandler.registerMessage(PacketVaultCycle.class, PacketVaultCycle.class, 0, Side.SERVER);

        //client TE updates
        netHandler.registerMessage(MessageTileZE.class, MessageTileZE.class, 1, Side.CLIENT);
        netHandler.registerMessage(MessageTileVault.class, MessageTileVault.class, 2, Side.CLIENT);
        netHandler.registerMessage(MessageTileChest.class, MessageTileChest.class, 3, Side.CLIENT);
        netHandler.registerMessage(MessageTileMultiblock.class, MessageTileMultiblock.class, 4, Side.CLIENT);

        netHandler.registerMessage(MessageShader.class, MessageShader.class, 5, Side.CLIENT);
        netHandler.registerMessage(MessageKeyCut.class, MessageKeyCut.class, 6, Side.SERVER);
    }
}
