package com.zerren.zedeng.block.tile.reactor;

import com.zerren.zedeng.block.tile.TEMultiBlockBase;
import com.zerren.zedeng.handler.PacketHandler;
import com.zerren.zedeng.handler.network.client.tile.MessageTileMultiblock;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.util.UUID;

/**
 * Created by Zerren on 3/7/2015.
 */
public class TEHeatExchanger extends TEMultiBlockBase implements IFluidHandler {

    private final int tankCapacity = 4000;

    public FluidTank waterTank = new FluidTank(this.tankCapacity);
    public FluidTank steamTank = new FluidTank(this.tankCapacity);

    public FluidTank coolantInletTank = new FluidTank(this.tankCapacity);
    public FluidTank coolantOutputTank = new FluidTank(this.tankCapacity);

    public TEHeatExchanger() {
        super();
    }


    public void initiateController(UUID id, EntityPlayer player) {
        if (controllerID == null) {
            controllerID = id;
        }

        if (!worldObj.isRemote){
            if (checkMultiblock()) {

            }
        }
    }

    private boolean checkMultiblock() {
        ForgeDirection direction = getOrientation();
        int counter = 0;

        //north & south +-x
        if (direction == ForgeDirection.NORTH || direction == ForgeDirection.SOUTH) {
            for (int i = 0; i < 5; i++) {
                TileEntity tile = worldObj.getTileEntity(xCoord - 2 + i, yCoord, zCoord);
                //tile exists and is a heat exchanger
                if (tile != null && tile instanceof TEHeatExchanger) {
                    //tile has no master
                    if (!((TEHeatExchanger) tile).hasValidMaster() || ((TEHeatExchanger) tile).getMasterUUID().compareTo(this.getControllerUUID()) == 0) {
                        counter++;
                    }
                }
            }
        }
        //east & west +-z
        else {
            for (int i = 0; i < 5; i++) {
                TileEntity tile = worldObj.getTileEntity(xCoord, yCoord, zCoord - 2 + i);
                //tile exists and is a heat exchanger
                if (tile != null && tile instanceof TEHeatExchanger) {
                    //tile has no master
                    if (!((TEHeatExchanger) tile).hasValidMaster() || ((TEHeatExchanger) tile).getMasterUUID().compareTo(this.getControllerUUID()) == 0) {
                        counter++;
                    }
                }
            }
        }

        if (counter == 5) {
            if (direction == ForgeDirection.NORTH || direction == ForgeDirection.SOUTH) {
                for (int i = 0; i < 5; i++) {
                    TEHeatExchanger tile = (TEHeatExchanger)worldObj.getTileEntity(xCoord - 2 + i, yCoord, zCoord);
                    tile.setController(controllerID, xCoord, yCoord, zCoord);
                    tile.getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);

                    setAsMaster(i == 2);
                    PacketHandler.netHandler.sendToAllAround(new MessageTileMultiblock(tile, i == 2, true), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 32D));
                }
            }
            else {
                for (int i = 0; i < 5; i++) {
                    TEHeatExchanger tile = (TEHeatExchanger)worldObj.getTileEntity(xCoord, yCoord, zCoord - 2 + i);
                    tile.setController(controllerID, xCoord, yCoord, zCoord);
                    tile.getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);

                    setAsMaster(i == 2);
                    PacketHandler.netHandler.sendToAllAround(new MessageTileMultiblock(tile, i == 2, true), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 32D));
                }
            }
        }
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        setAsMaster(counter == 5);
        return counter == 5;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    //Fluid output from tile
    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    //Fluid output from tile, not specific
    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[0];
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        if (tag.hasKey("controllerIDMost") && tag.hasKey("controllerIDLeast")) {
            this.controllerID = new UUID(tag.getLong("controllerIDMost"), tag.getLong("controllerIDLeast"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        if (this.hasControllerID()) {
            tag.setLong("controllerIDMost", controllerID.getMostSignificantBits());
            tag.setLong("controllerIDLeast", controllerID.getLeastSignificantBits());
        }
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord, zCoord - 2, xCoord + 3, yCoord + 1, zCoord + 3);
    }
}
