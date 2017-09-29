package com.zerren.chainreaction.handler.network.server.tile;

import chainreaction.api.item.IKey;
import chainreaction.api.tile.IUpgradeableTile;
import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.core.PlayerSetBonus;
import com.zerren.chainreaction.handler.network.AbstractMessage;
import com.zerren.chainreaction.reference.GUIs;
import com.zerren.chainreaction.reference.Reference;
import com.zerren.chainreaction.tile.TileEntityCRBase;
import com.zerren.chainreaction.tile.vault.TEVaultController;
import com.zerren.chainreaction.utility.CoreUtility;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.io.IOException;

/**
 * Created by Zerren on 9/23/2015.
 */
public class MessageUpgradeInstall extends PacketTileCR<TileEntityCRBase> implements IMessageHandler<MessageUpgradeInstall, IMessage> {

    public MessageUpgradeInstall() {
        super();
    }

    public MessageUpgradeInstall(IUpgradeableTile tile) {
        super((TileEntityCRBase)tile);

    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        super.toBytes(byteBuf);
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        super.fromBytes(byteBuf);
    }

    @Override
    public IMessage onMessage(MessageUpgradeInstall message, MessageContext ctx) {
        super.onMessage(message, ctx);
        if (!ctx.side.isServer()) {
            throw new IllegalStateException("received PacketVault " + message + "on client side!");
        }

        if (message.tile instanceof IUpgradeableTile) {
            IUpgradeableTile tile = (IUpgradeableTile) message.tile;

            tile.installUpgrades();
            message.tile.getWorldObj().markBlockForUpdate(message.x, message.y, message.z);
        }
        return null;
    }
}