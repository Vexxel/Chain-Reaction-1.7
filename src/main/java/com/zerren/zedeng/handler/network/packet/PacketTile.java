package com.zerren.zedeng.handler.network.packet;

import com.zerren.zedeng.proxy.ClientProxy;
import com.zerren.zedeng.utility.CoreUtility;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Zerren on 2/25/2015.
 */
public abstract class PacketTile<T extends TileEntity> implements IMessage {

    protected int dim, x, y, z;
    protected transient T tile;
    protected transient EntityPlayer player;

    public PacketTile() {

    }

    public PacketTile(T tile) {
        this.tile = tile;

        this.x = tile.xCoord;
        this.y = tile.yCoord;
        this.z = tile.zCoord;
        this.dim = tile.getWorldObj().provider.dimensionId;
    }

    public PacketTile(T tile, EntityPlayer player) {
        this.tile = tile;

        this.x = tile.xCoord;
        this.y = tile.yCoord;
        this.z = tile.zCoord;
        this.dim = tile.getWorldObj().provider.dimensionId;
        this.player = player;
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(x);
        byteBuf.writeInt(y);
        byteBuf.writeInt(z);
        byteBuf.writeInt(dim);
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        x = byteBuf.readInt();
        y = byteBuf.readInt();
        z = byteBuf.readInt();
        dim = byteBuf.readInt();
    }

    public IMessage onMessage(PacketTile message, MessageContext ctx) {
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
        return null;
    }
}