package com.zerren.chainreaction.handler.network.client.tile;

import com.zerren.chainreaction.tile.mechanism.TEElectrolyzer;
import com.zerren.chainreaction.tile.plumbing.TEGasTank;
import com.zerren.chainreaction.utility.NetworkUtility;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Zerren on 2/28/2015.
 */
public class MessageTileElectrolyzer implements IMessage, IMessageHandler<MessageTileElectrolyzer, IMessage> {

    private int x, y, z, dim;
    private byte orientation, state;

    private NBTTagCompound nbtRoot;

    public MessageTileElectrolyzer() {

    }

    public MessageTileElectrolyzer(TEElectrolyzer tile) {
        x = tile.xCoord;
        y = tile.yCoord;
        z = tile.zCoord;
        dim = tile.getWorldObj().provider.dimensionId;

        this.orientation = (byte) tile.getOrientation().ordinal();
        this.state = (byte) tile.getState();

        nbtRoot = new NBTTagCompound();
        if(tile.inputTank.getFluidAmount() > 0) {
            NBTTagCompound tankRoot = new NBTTagCompound();
            tile.inputTank.writeToNBT(tankRoot);
            nbtRoot.setTag("tank", tankRoot);
        }
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

        nbtRoot = NetworkUtility.readNBTTagCompound(buf);
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

        NetworkUtility.writeNBTTagCompound(nbtRoot, buf);
    }

    @Override
    public IMessage onMessage(MessageTileElectrolyzer message, MessageContext ctx) {
        TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);

        if (tileEntity != null && tileEntity instanceof TEElectrolyzer) {

            ((TEElectrolyzer) tileEntity).setOrientation(message.orientation);
            ((TEElectrolyzer) tileEntity).setState(message.state);

            if(message.nbtRoot.hasKey("tank")) {
                NBTTagCompound tankRoot = message.nbtRoot.getCompoundTag("tank");
                ((TEElectrolyzer) tileEntity).readFromNBT(tankRoot);
            } else {
                ((TEElectrolyzer) tileEntity).inputTank.setFluid(null);
            }
        }

        return null;
    }
}
