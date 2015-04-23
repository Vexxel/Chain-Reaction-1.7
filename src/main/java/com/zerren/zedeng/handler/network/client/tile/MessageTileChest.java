package com.zerren.zedeng.handler.network.client.tile;

import com.zerren.zedeng.tile.chest.TEChest;
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
public class MessageTileChest implements IMessage, IMessageHandler<MessageTileChest, IMessage> {

    public int x, y, z, dim;
    public byte orientation, state;
    public String customName;
    public UUID ownerUUID;
    public boolean locked;

    public MessageTileChest() {

    }

    public MessageTileChest(TEChest tile, boolean locked) {
        x = tile.xCoord;
        y = tile.yCoord;
        z = tile.zCoord;
        dim = tile.getWorldObj().provider.dimensionId;

        this.orientation = (byte) tile.getOrientation().ordinal();
        this.state = (byte) tile.getState();
        this.customName = tile.getCustomName();
        this.ownerUUID = tile.getOwnerUUID();
        this.locked = locked;
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
        if (buf.readBoolean()) {
            this.ownerUUID = new UUID(buf.readLong(), buf.readLong());
        }
        else {
            this.ownerUUID = null;
        }

        this.locked = buf.readBoolean();
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
        if (ownerUUID != null)
        {
            buf.writeBoolean(true);
            buf.writeLong(ownerUUID.getMostSignificantBits());
            buf.writeLong(ownerUUID.getLeastSignificantBits());
        }
        else
        {
            buf.writeBoolean(false);
        }

        buf.writeBoolean(locked);
    }

    @Override
    public IMessage onMessage(MessageTileChest message, MessageContext ctx)
    {
        TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);

        if (tileEntity instanceof TEChest) {

            ((TEChest) tileEntity).setOrientation(message.orientation);
            ((TEChest) tileEntity).setState(message.state);
            ((TEChest) tileEntity).setCustomName(message.customName);
            ((TEChest) tileEntity).setOwnerUUID(message.ownerUUID);

            ((TEChest) tileEntity).setChestLocked(message.locked);
        }

        return null;
    }
}
