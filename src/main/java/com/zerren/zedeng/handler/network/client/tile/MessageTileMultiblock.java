package com.zerren.zedeng.handler.network.client.tile;

import com.zerren.zedeng.tile.TEMultiBlockBase;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

import java.util.UUID;

/**
 * Created by Zerren on 2/28/2015.
 */
public class MessageTileMultiblock implements IMessage, IMessageHandler<MessageTileMultiblock, IMessage> {

    public int x, y, z, dim;
    public byte orientation, state;
    public String customName;
    public UUID ownerUUID;
    public boolean isMaster, hasMaster;

    public MessageTileMultiblock() {

    }

    public MessageTileMultiblock(TEMultiBlockBase tile, boolean isMaster, boolean hasMaster) {
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
    }

    @Override
    public IMessage onMessage(MessageTileMultiblock message, MessageContext ctx)
    {
        TileEntity tile = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);

        if (tile instanceof TEMultiBlockBase) {

            ((TEMultiBlockBase) tile).setOrientation(message.orientation);
            ((TEMultiBlockBase) tile).setState(message.state);
            ((TEMultiBlockBase) tile).setCustomName(message.customName);
            ((TEMultiBlockBase) tile).setOwnerUUID(message.ownerUUID);
            ((TEMultiBlockBase) tile).hasMaster = message.hasMaster;

            ((TEMultiBlockBase) tile).setAsMaster(message.isMaster);
        }

        return null;
    }
}
