package com.zerren.zedeng.utility;

import com.zerren.zedeng.ZederrianEngineering;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Zerren on 4/10/2015.
 */
public final class TileCache {
    private final int x, y, z;
    private final World world;
    private TileEntity tile;
    private Block block = null;
    private ForgeDirection side;

    public TileCache(World world, int x, int y, int z, ForgeDirection side) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.side = side;

        refresh();
    }

    private void refresh() {
        tile = null;
        block = null;

        if (!world.blockExists(x, y, z)) return;

        block = world.getBlock(x, y, z);

        if (block != null && block.hasTileEntity(block.getDamageValue(world, x, y, z))) {
            tile = CoreUtility.getTileEntity(world, x, y, z);
        }

    }

    public TileEntity getTile() {
        if (tile != null && !tile.isInvalid()) return tile;
        return null;
    }

    public Block getBlock() {
        if (tile != null && !tile.isInvalid()) {
            refresh();
        }

        return block;
    }

    public ForgeDirection getOffsetDirection() {
        if (tile != null && !tile.isInvalid()) return side;
        return null;
    }

    public static TileCache[] buildCache(World world, int x, int y, int z) {
        TileCache[] cache = new TileCache[6];
        for (int i = 0; i < 6; i++) {
            ForgeDirection dir = ForgeDirection.getOrientation(i);
            cache[i] = new TileCache(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir);

            ZederrianEngineering.log.info("Added cache tile @ " + (x + dir.offsetX) + " " + (y + dir.offsetY) + " " + (z + dir.offsetZ) + " " + dir);
            ZederrianEngineering.log.info("Tile = " + cache[i].getTile());
        }
        return cache;
    }
}
