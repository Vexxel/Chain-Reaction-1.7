package com.zerren.chainreaction.handler.network;

import com.zerren.chainreaction.handler.network.client.player.MessageSetBonus;
import com.zerren.chainreaction.handler.network.client.player.MessageToolDamage;
import com.zerren.chainreaction.handler.network.client.tile.*;
import com.zerren.chainreaction.handler.network.server.player.MessageHotkey;
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
        int index = 0;
        //gui to tile
        INSTANCE.registerMessage(MessageVaultCycle.class, MessageVaultCycle.class, index++, Side.SERVER);
        //gui to player
        INSTANCE.registerMessage(MessageKeyCut.class, MessageKeyCut.class, index++, Side.SERVER);
        //client TE updates
        INSTANCE.registerMessage(MessageTileCR.class, MessageTileCR.class, index++, Side.CLIENT);
        INSTANCE.registerMessage(MessageTileVault.class, MessageTileVault.class, index++, Side.CLIENT);
        INSTANCE.registerMessage(MessageTileChest.class, MessageTileChest.class, index++, Side.CLIENT);
        INSTANCE.registerMessage(MessageTileMultiblock.class, MessageTileMultiblock.class, index++, Side.CLIENT);
        INSTANCE.registerMessage(MessageTileGasTank.class, MessageTileGasTank.class, index++, Side.CLIENT);
        INSTANCE.registerMessage(MessageHotkey.class, MessageHotkey.class, index++, Side.SERVER);
        INSTANCE.registerMessage(MessageTileBloomery.class, MessageTileBloomery.class, index++, Side.CLIENT);
        //server to player
        INSTANCE.registerMessage(MessageToolDamage.class, MessageToolDamage.class, index++, Side.CLIENT);
        INSTANCE.registerMessage(MessageSetBonus.class, MessageSetBonus.class, index++, Side.CLIENT);

    }
}
