package com.zerren.chainreaction.utility;

import com.zerren.chainreaction.reference.Reference;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.io.IOException;

/**
 * Created by Zerren on 4/14/2015.
 */
public final class NetworkUtility {
    public static NetworkRegistry.TargetPoint makeTargetPoint(TileEntity tile, double range) {
        return new NetworkRegistry.TargetPoint(tile.getWorldObj().provider.dimensionId, tile.xCoord, tile.yCoord, tile.zCoord, range);
    }

    public static NetworkRegistry.TargetPoint makeTargetPoint(TileEntity tile) {
        return makeTargetPoint(tile, Reference.Tweaks.TILE_PACKET_RANGE);
    }

    public static NBTTagCompound readNBTTagCompound(ByteBuf dataIn) {
        try {
            short size = dataIn.readShort();
            if (size < 0) {
                return null;
            } else {
                byte[] buffer = new byte[size];
                dataIn.readBytes(buffer);
                return CompressedStreamTools.func_152457_a(buffer, NBTSizeTracker.field_152451_a);
            }
        } catch (IOException e) {
            FMLCommonHandler.instance().raiseException(e, "Custom Packet", true);
            return null;
        }
    }

    public static void writeNBTTagCompound(NBTTagCompound compound, ByteBuf dataout) {
        try {
            if (compound == null) {
                dataout.writeShort(-1);
            } else {
                byte[] buf = CompressedStreamTools.compress(compound);
                dataout.writeShort((short) buf.length);
                dataout.writeBytes(buf);
            }
        } catch (IOException e) {
            FMLCommonHandler.instance().raiseException(e, "PacketUtil.readTileEntityPacket.writeNBTTagCompound", true);
        }
    }
}
