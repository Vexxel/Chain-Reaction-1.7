package com.zerren.zedeng.block.tile.reactor;

import com.zerren.zedeng.block.tile.TEMultiBlockBase;
import com.zerren.zedeng.core.ModFluids;
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

    /**
     * The byte, from left to right (when facing the complete multiblock), of this block's ID in the multiblock
     */
    public byte slaveLocation;

    /**
     * Arbitrary thermal storage used to heat a fluid--1 TU can turn 1mb water @ 2250psi & 275K to 24mb steam @ 900psi & 550K
     */
    public int thermalUnits;

    public TEHeatExchanger() {
        super();
        slaveLocation = -1;
        thermalUnits = 0;
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

    @Override
    public void invalidateMultiblock() {
        TEMultiBlockBase commander = getCommandingController();

        if (commander != null) {
            commander.setAsMaster(false);
            ForgeDirection dir = commander.getOrientation();
            if (dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH) {
                for (int i = 0; i < 5; i++) {
                    TEHeatExchanger tile = (TEHeatExchanger)worldObj.getTileEntity(commander.xCoord - 2 + i, commander.yCoord, commander.zCoord);
                    if (tile != null) {
                        tile.removeController();
                        tile.setSlaveLocation((byte)-1);
                        PacketHandler.netHandler.sendToAllAround(new MessageTileMultiblock(tile, false, false),
                                new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 32D));
                    }
                }
            }
            else {
                for (int i = 0; i < 5; i++) {
                    TEHeatExchanger tile = (TEHeatExchanger)worldObj.getTileEntity(commander.xCoord, commander.yCoord, commander.zCoord - 2 + i);
                    if (tile != null) {
                        tile.removeController();
                        tile.setSlaveLocation((byte)-1);
                        PacketHandler.netHandler.sendToAllAround(new MessageTileMultiblock(tile, false, false),
                                new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 32D));
                    }
                }
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
                    if (direction == ForgeDirection.SOUTH)
                        tile.setSlaveLocation((byte)i);
                    else
                        tile.setSlaveLocation((byte) (-i + 4));
                    tile.getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);

                    setAsMaster(i == 2);
                    PacketHandler.netHandler.sendToAllAround(new MessageTileMultiblock(tile, i == 2, true), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 32D));
                }
            }
            else {
                for (int i = 0; i < 5; i++) {
                    TEHeatExchanger tile = (TEHeatExchanger)worldObj.getTileEntity(xCoord, yCoord, zCoord - 2 + i);
                    tile.setController(controllerID, xCoord, yCoord, zCoord);
                    if (direction == ForgeDirection.WEST)
                        tile.setSlaveLocation((byte)i);
                    else
                        tile.setSlaveLocation((byte)(-i + 4));
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

    public void setSlaveLocation(byte loc) {
        this.slaveLocation = loc;
    }

    public byte getSlaveLocation() {
        return slaveLocation;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        int used = 0;
        FluidStack filling = resource.copy();

        if (slaveLocation != -1){
            if (!this.isMaster()) {
                if (slaveLocation == 0) {
                    if (resource.getFluid() == ModFluids.coolantHotFluid) {
                        used += coolantInletTank.fill(filling, doFill);
                        filling.amount -= used;
                    }
                }
                if (slaveLocation == 1) {
                    if (resource.getFluid() == FluidRegistry.WATER) {
                        used += coolantInletTank.fill(filling, doFill);
                        filling.amount -= used;
                    }
                }
            }
        }
        return used;
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

        this.slaveLocation = tag.getByte("slaveLocation");

        if (tag.hasKey("controllerIDMost") && tag.hasKey("controllerIDLeast")) {
            this.controllerID = new UUID(tag.getLong("controllerIDMost"), tag.getLong("controllerIDLeast"));
        }

        if (slaveLocation != -1)
            switch (slaveLocation) {
                case 0:
                    this.coolantInletTank.readFromNBT(tag.getCompoundTag("coolantInletTank")); break;
                case 1:
                    this.waterTank.readFromNBT(tag.getCompoundTag("waterTank")); break;
                case 2:
                    this.thermalUnits = tag.getInteger("thermalUnits"); break;
                case 3:
                    this.steamTank.readFromNBT(tag.getCompoundTag("steamTank")); break;
                case 4:
                    this.coolantOutputTank.readFromNBT(tag.getCompoundTag("coolantOutputTank")); break;
            }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setByte("slaveLocation", slaveLocation);

        if (this.hasControllerID()) {
            tag.setLong("controllerIDMost", controllerID.getMostSignificantBits());
            tag.setLong("controllerIDLeast", controllerID.getLeastSignificantBits());
        }

        if (slaveLocation != -1)
            switch (slaveLocation) {
                case 0:
                    tag.setTag("coolantInletTank", coolantInletTank.writeToNBT(new NBTTagCompound())); break;
                case 1:
                    tag.setTag("waterTank", waterTank.writeToNBT(new NBTTagCompound())); break;
                case 2:
                    tag.setInteger("thermalUnits", thermalUnits); break;
                case 3:
                    tag.setTag("steamTank", steamTank.writeToNBT(new NBTTagCompound())); break;
                case 4:
                    tag.setTag("coolantOutputTank", coolantOutputTank.writeToNBT(new NBTTagCompound())); break;
            }
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        if (this.isMaster())
            return AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord, zCoord - 2, xCoord + 3, yCoord + 1, zCoord + 3);
        return super.getRenderBoundingBox();
    }
}
