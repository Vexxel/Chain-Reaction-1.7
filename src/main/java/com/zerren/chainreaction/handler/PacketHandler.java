package com.zerren.chainreaction.handler;

import com.zerren.chainreaction.handler.network.client.tile.*;
import com.zerren.chainreaction.handler.network.server.player.MessageKeyCut;
import com.zerren.chainreaction.handler.network.server.tile.PacketVaultCycle;
import com.zerren.chainreaction.reference.Reference;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * Created by Zerren on 2/25/2015.
 */
public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.ModInfo.MOD_CHANNEL_SIMPLE);

    public static void init() {
        byte i = -1;
        //gui to tile
        INSTANCE.registerMessage(PacketVaultCycle.class, PacketVaultCycle.class, i++, Side.SERVER);
        //gui to player
        INSTANCE.registerMessage(MessageKeyCut.class, MessageKeyCut.class, i++, Side.SERVER);
        //client TE updates
        INSTANCE.registerMessage(MessageTileCR.class, MessageTileCR.class, i++, Side.CLIENT);
        INSTANCE.registerMessage(MessageTileVault.class, MessageTileVault.class, i++, Side.CLIENT);
        INSTANCE.registerMessage(MessageTileChest.class, MessageTileChest.class, i++, Side.CLIENT);
        INSTANCE.registerMessage(MessageTileMultiblock.class, MessageTileMultiblock.class, i++, Side.CLIENT);
        INSTANCE.registerMessage(MessageTileGasTank.class, MessageTileGasTank.class, i++, Side.CLIENT);
        //server to player
        //INSTANCE.registerMessage(MessageShader.class, MessageShader.class, i++, Side.CLIENT);
    }
}
