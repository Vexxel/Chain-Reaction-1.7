package com.zerren.chainreaction.handler.network.client.tile;

import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.tile.TEMultiBlockBase;
import com.zerren.chainreaction.tile.mechanism.TEBloomery;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

import java.util.UUID;

/**
 * Created by Zerren on 5/21/2016.
 */
public class MessageTileBloomery implements IMessage, IMessageHandler<MessageTileBloomery, IMessage> {

    public int x, y, z, dim;
    public byte orientation, state;
    public String customName;
    public UUID ownerUUID;
    public boolean isMaster, hasMaster, shouldRender, isActive;
    public short multiblockPartNumber;

    public MessageTileBloomery() {

    }

    public MessageTileBloomery(TEBloomery tile, boolean isMaster, boolean hasMaster, boolean shouldRender, boolean isActive) {
        x = tile.xCoord;
        y = tile.yCoord;
        z = tile.zCoord;
        dim = tile.getWorldObj().provider.dimensionId;

        this.orientation = (byte) tile.getOrientation().ordinal();
        this.state = (byte) tile.getState();
        this.customName = tile.getCustomName();
        this.ownerUUID = tile.getOwnerUUID();
        this.isMaster = isMaster;
        this.hasMaster = hasMaster;
        this.shouldRender = shouldRender;
        this.isActive = isActive;
        this.multiblockPartNumber = tile.getMultiblockPartNumber();
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.dim = buf.readInt();
        this.orientation = buf.readByte();
        this.state = buf.readByte();
        int customNameLength = buf.readInt();
        this.customName = new String(buf.readBytes(customNameLength).array());
        //owner UUID
        if (buf.readBoolean()) {
            this.ownerUUID = new UUID(buf.readLong(), buf.readLong());
        }
        else {
            this.ownerUUID = null;
        }

        this.isMaster = buf.readBoolean();
        this.hasMaster = buf.readBoolean();
        this.shouldRender = buf.readBoolean();
        this.isActive = buf.readBoolean();
        this.multiblockPartNumber = buf.readShort();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(dim);
        buf.writeByte(orientation);
        buf.writeByte(state);
        buf.writeInt(customName.length());
        buf.writeBytes(customName.getBytes());
        //owner UUID
        if (ownerUUID != null) {
            buf.writeBoolean(true);
            buf.writeLong(ownerUUID.getMostSignificantBits());
            buf.writeLong(ownerUUID.getLeastSignificantBits());
        }
        else {
            buf.writeBoolean(false);
        }

        buf.writeBoolean(isMaster);
        buf.writeBoolean(hasMaster);
        buf.writeBoolean(shouldRender);
        buf.writeBoolean(isActive);
        buf.writeShort(multiblockPartNumber);
    }

    @Override
    public IMessage onMessage(MessageTileBloomery message, MessageContext ctx)
    {
        TileEntity tile = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);

        if (tile instanceof TEBloomery) {

            ((TEBloomery) tile).setOrientation(message.orientation);

            ((TEBloomery) tile).setState(message.state);
            ((TEBloomery) tile).setCustomName(message.customName);
            ((TEBloomery) tile).setOwnerUUID(message.ownerUUID);
            ((TEBloomery) tile).hasMaster = message.hasMaster;
            ((TEBloomery) tile).setAsMaster(message.isMaster);
            ((TEBloomery) tile).setMultiblockPartNumber(message.multiblockPartNumber);
            //((TEBloomery) tile).isActive = message.isActive;

            if (message.shouldRender) {
                ChainReaction.proxy.updateTileModel(tile);
            }
        }

        return null;
    }
}
