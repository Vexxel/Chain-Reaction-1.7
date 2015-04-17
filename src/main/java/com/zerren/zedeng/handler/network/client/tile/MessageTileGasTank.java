package com.zerren.zedeng.handler.network.client.tile;

import com.zerren.zedeng.ZederrianEngineering;
import com.zerren.zedeng.block.tile.chest.TEChest;
import com.zerren.zedeng.block.tile.plumbing.TEGasTank;
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
public class MessageTileGasTank implements IMessage, IMessageHandler<MessageTileGasTank, IMessage> {

    private int x, y, z, dim;
    private byte orientation, state;
    private int fluidAmount;

    public MessageTileGasTank() {

    }

    public MessageTileGasTank(TEGasTank tile, int amount) {
        x = tile.xCoord;
        y = tile.yCoord;
        z = tile.zCoord;
        dim = tile.getWorldObj().provider.dimensionId;

        this.orientation = (byte) tile.getOrientation().ordinal();
        this.state = (byte) tile.getState();

        this.fluidAmount = amount;
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

        this.fluidAmount = buf.readInt();
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

        buf.writeInt(fluidAmount);
    }

    @Override
    public IMessage onMessage(MessageTileGasTank message, MessageContext ctx) {
        TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);

        if (tileEntity instanceof TEGasTank) {

            ((TEGasTank) tileEntity).setOrientation(message.orientation);
            ((TEGasTank) tileEntity).setState(message.state);

            ((TEGasTank) tileEntity).fluidAmount = message.fluidAmount;
            ZederrianEngineering.log.info("Client fluid amount: " + message.fluidAmount + " of tile @ " + message.x + " " + message.y + " " + message.z);
        }

        return null;
    }
}
