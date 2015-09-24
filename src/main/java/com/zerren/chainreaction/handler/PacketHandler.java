package com.zerren.chainreaction.handler;

import com.zerren.chainreaction.handler.network.client.tile.*;
import com.zerren.chainreaction.handler.network.server.player.MessageKeyCut;
import com.zerren.chainreaction.handler.network.server.tile.MessageVaultCycle;
import com.zerren.chainreaction.reference.Reference;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * Created by Zerren on 2/25/2015.
 */
public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.ModInfo.MOD_ID.toLowerCase());

    public static void init() {
        //gui to tile
        int i = 0;
        INSTANCE.registerMessage(MessageVaultCycle.class, MessageVaultCycle.class, 0, Side.SERVER);
        //gui to player
        INSTANCE.registerMessage(MessageKeyCut.class, MessageKeyCut.class, 1, Side.SERVER);
        //client TE updates
        INSTANCE.registerMessage(MessageTileCR.class, MessageTileCR.class, 2, Side.CLIENT);
        INSTANCE.registerMessage(MessageTileVault.class, MessageTileVault.class, 3, Side.CLIENT);
        INSTANCE.registerMessage(MessageTileChest.class, MessageTileChest.class, 4, Side.CLIENT);
        INSTANCE.registerMessage(MessageTileMultiblock.class, MessageTileMultiblock.class, 5, Side.CLIENT);
        INSTANCE.registerMessage(MessageTileGasTank.class, MessageTileGasTank.class, 6, Side.CLIENT);
        //server to player
        //INSTANCE.registerMessage(MessageShader.class, MessageShader.class, i++, Side.CLIENT);
    }
}
