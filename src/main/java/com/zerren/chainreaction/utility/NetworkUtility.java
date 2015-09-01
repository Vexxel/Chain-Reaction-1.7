package com.zerren.chainreaction.utility;

import com.zerren.chainreaction.reference.Reference;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.tileentity.TileEntity;

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
}
