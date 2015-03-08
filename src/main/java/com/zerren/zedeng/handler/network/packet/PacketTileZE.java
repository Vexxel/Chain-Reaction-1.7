package com.zerren.zedeng.handler.network.packet;

import com.zerren.zedeng.block.tile.TileEntityZE;
import com.zerren.zedeng.proxy.ClientProxy;
import com.zerren.zedeng.utility.CoreUtility;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.UUID;

/**
 * Created by Zerren on 2/25/2015.
 */
public abstract class PacketTileZE<T extends TileEntityZE> implements IMessage {

    protected int dim, x, y, z;
    public byte orientation, state;
    public String customName;
    public UUID ownerUUID;

    protected transient T tile;
    protected transient EntityPlayer player;

    public PacketTileZE() {

    }

    public PacketTileZE(T tile) {
        this.tile = tile;

        this.x = tile.xCoord;
        this.y = tile.yCoord;
        this.z = tile.zCoord;
        this.dim = tile.getWorldObj().provider.dimensionId;

        this.orientation = (byte) tile.getOrientation().ordinal();
        this.state = (byte) tile.getState();
        this.customName = tile.getCustomName();
        this.ownerUUID = tile.getOwnerUUID();
    }

    public PacketTileZE(T tile, EntityPlayer player) {
        this.tile = tile;

        this.x = tile.xCoord;
        this.y = tile.yCoord;
        this.z = tile.zCoord;
        this.dim = tile.getWorldObj().provider.dimensionId;
        this.player = player;

        this.orientation = (byte) tile.getOrientation().ordinal();
        this.state = (byte) tile.getState();
        this.customName = tile.getCustomName();
        this.ownerUUID = tile.getOwnerUUID();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(x);
        byteBuf.writeInt(y);
        byteBuf.writeInt(z);
        byteBuf.writeInt(dim);

        byteBuf.writeByte(orientation);
        byteBuf.writeByte(state);
        byteBuf.writeInt(customName.length());
        byteBuf.writeBytes(customName.getBytes());
        if (ownerUUID != null)
        {
            byteBuf.writeBoolean(true);
            byteBuf.writeLong(ownerUUID.getMostSignificantBits());
            byteBuf.writeLong(ownerUUID.getLeastSignificantBits());
        }
        else
        {
            byteBuf.writeBoolean(false);
        }
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        x = byteBuf.readInt();
        y = byteBuf.readInt();
        z = byteBuf.readInt();
        dim = byteBuf.readInt();

        this.orientation = byteBuf.readByte();
        this.state = byteBuf.readByte();
        int customNameLength = byteBuf.readInt();
        this.customName = new String(byteBuf.readBytes(customNameLength).array());
        if (byteBuf.readBoolean())
        {
            this.ownerUUID = new UUID(byteBuf.readLong(), byteBuf.readLong());
        }
        else
        {
            this.ownerUUID = null;
        }
    }

    public IMessage onMessage(PacketTileZE message, MessageContext ctx) {
        MinecraftServer server = CoreUtility.server();
        if (ctx.side.isClient())
            message.player = ClientProxy.getPlayer();
        else {
            message.player = ctx.getServerHandler().playerEntity;
        }
        if (server != null) {
            World world = server.worldServerForDimension(message.dim);

            if (world == null) {
                CoreUtility.printCurrentStackTrace("No world found for dimension " + message.dim + "!");
                return null;
            }

            TileEntity tile = world.getTileEntity(message.x, message.y, message.z);
            if (tile != null) {
                message.tile = (T) tile;
            }
        }

        TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);

        if (tileEntity instanceof TileEntityZE) {

            ((TileEntityZE) tileEntity).setOrientation(message.orientation);
            ((TileEntityZE) tileEntity).setState(message.state);
            ((TileEntityZE) tileEntity).setCustomName(message.customName);
            ((TileEntityZE) tileEntity).setOwnerUUID(message.ownerUUID);
        }

        return null;
    }
}