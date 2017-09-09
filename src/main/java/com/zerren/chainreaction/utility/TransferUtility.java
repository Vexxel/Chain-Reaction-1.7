package com.zerren.chainreaction.utility;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

/**
 * Created by Zerren on 4/11/2015.
 */
public final class TransferUtility {

    /**
     * Sends fluids from the given tank to any consumer in a random order, trying to fill the first tank it finds and sending excess to any others.
     * For even distribution, see {@link TransferUtility#splitFluidToConsumers}. To distribute to potentially all six, set exclude
     * to ForgeDirection.UNKNOWN
     * @param tank the fluid handler to distribute fluid from
     * @param flowCap the max flow rate (in mb/t) of the fluid out of the tank
     * @param cache the {@link TileCache} containing the TileEntities to send fluid to
     * @param exclude the {@link ForgeDirection} to exclude from sending fluid to (usually the input)
     */
    public static void pushFluidToConsumers(IFluidTank tank, int flowCap, TileCache[] cache, ForgeDirection exclude) {
        int amountToPush = flowCap;
        for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
            if (side != exclude) {
                FluidStack fluidStack = tank.drain(amountToPush, false);
                if (fluidStack != null && fluidStack.amount > 0) {
                    TileEntity tile = cache[side.ordinal()].getTile();
                    if (tile != null && tile instanceof IFluidHandler) {
                        int used = ((IFluidHandler) tile).fill(side.getOpposite(), fluidStack, true);
                        if (used > 0) {
                            amountToPush -= used;
                            tank.drain(used, true);
                            if (amountToPush <= 0) {
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Evenly splits fluid contained in the given tank with the option of excluding a direction. To distribute to potentially all six, set exclude
     * to ForgeDirection.UNKNOWN
     * @param tank the fluid handler to distribute fluid from
     * @param flowCap the max flow rate (in mb/t) of the fluid out of the tank
     * @param cache the {@link TileCache} containing the TileEntities to send fluid to
     * @param exclude the {@link ForgeDirection} to exclude from sending fluid to (usually the input)
     */
    public static void splitFluidToConsumers(IFluidTank tank, int flowCap, TileCache[] cache, ForgeDirection exclude) {
        byte validtiles = 0;

        //count the valid tiles
        for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
            if (side != exclude)
                if (cache[side.ordinal()].getTile() != null && cache[side.ordinal()].getTile() instanceof IFluidHandler) {
                    validtiles++;
                }
        }

        if (validtiles <= 0) return;

        FluidStack fl1 = tank.drain(flowCap, false);

        //build a fluidstack the size of counted tiles
        FluidStack[] flood = new FluidStack[validtiles];
        if (fl1 == null || fl1.amount <= 0) return;

        int amount = (int)Math.floor((float)fl1.amount / validtiles);
        for (int i = 0; i < flood.length; i++) {
            flood[i] = new FluidStack(fl1.getFluid(), amount);
        }

        //loop through the valid tiles
        int loc2 = 0;
        for (TileCache c : cache) {
            //go to next loop entry if the side is the exclusion
            if (c.getOffsetDirection() == exclude) continue;

            //fluidstack array param isn't null and contains fluid
            if (flood[loc2] != null && flood[loc2].amount > 0) {
                TileEntity tile = c.getTile();

                if (tile != null && tile instanceof IFluidHandler) {
                    //the actual filling of the tank
                    int used = ((IFluidHandler) tile).fill(c.getOffsetDirection(), flood[loc2], true);
                    //a successful filling event
                    if (used > 0) {
                        tank.drain(used, true);
                    }

                    loc2++;
                    if (loc2 >= flood.length) return;
                }
            }
        }
    }

    public static TileEntity getTileAdjacentFromDirection(TileEntity tile, ForgeDirection dir) {
        return tile.getWorldObj().getTileEntity(tile.xCoord + dir.offsetX, tile.yCoord + dir.offsetY, tile.zCoord + dir.offsetZ);
    }

    public static ForgeDirection[] getAllSideDirections() {
        return new ForgeDirection[] {ForgeDirection.NORTH, ForgeDirection.EAST, ForgeDirection.SOUTH, ForgeDirection.WEST};
    }

    public static ForgeDirection[] getAllElevationDirections() {
        return new ForgeDirection[] {ForgeDirection.UP, ForgeDirection.DOWN};
    }

}
