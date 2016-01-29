package com.zerren.chainreaction.tile.reactor;

import chainreaction.api.block.IThermalTile;
import com.zerren.chainreaction.handler.PacketHandler;
import com.zerren.chainreaction.handler.network.client.tile.MessageTileMultiblock;
import com.zerren.chainreaction.reference.MultiblockCost;
import com.zerren.chainreaction.tile.TEMultiBlockBase;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.utility.NetworkUtility;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import chainreaction.api.reactor.ReactorType;

import java.util.UUID;

/**
 * Created by Zerren on 4/23/2015.
 */
public class TEPressurizedWaterReactor extends TEMultiBlockBase implements IThermalTile, IFluidHandler {

    private final int tankCapacity = 32000;
    public final FluidTank coolantInput = new FluidTank(this.tankCapacity);
    public final FluidTank coolantOutput = new FluidTank(this.tankCapacity);

    /**
     * Arbitrary thermal storage used to heat a fluid--10 TU can turn 1mb water to 160mb steam (RF:Steam :: 2:1), 1TU = 32 RF.
     * 5mb of hot coolant = 1TU = 200TU per 1000mb--Exchanger at peak performance consuming hot coolant produces 20TU/t = 640RF/t = 320Steam/t.
     * Each bucket of hot coolant can make 6400RF
     */
    private float thermalUnits;

    /**
     * Arbitrary thermal waste heat storage that is the 'leftover' of a energy conversion--this tile entity does not have a loss in efficiency, because it deals in
     * entirely thermal units. Loss of efficiency occurs when energy is transformed between two different types (eg. Heat <-> electricity, light <-> heat)
     */
    private int wasteHeatUnits;

    private short updateCounter;

    private final ReactorType reactorType;

    public TEPressurizedWaterReactor() {
        super();
        thermalUnits = 0;
        this.reactorType = ReactorType.PWR;
    }

    public void initiateController(UUID id, EntityPlayer player) {
        if (controllerID == null) {
            setControllerUUID(id);
        }
        if (ownerUUID == null) {
            setOwner(player);
        }

        if (!this.isMaster && !this.hasValidMaster()){
            checkMultiblock(player);
            MultiblockCost.takeMaterials(player, MultiblockCost.PRESSURIZED_WATER_REACTOR, true);
            PacketHandler.INSTANCE.sendToAllAround(new MessageTileMultiblock(this, true, true), NetworkUtility.makeTargetPoint(this));
        }
    }

    private void checkMultiblock(EntityPlayer player) {
        
    }

    public int[] getCoreBlock() {
        ForgeDirection direction = getOrientation();
        int cX = 0, cZ = 0;

        if (direction == ForgeDirection.NORTH) {
            cX = xCoord;
            cZ = zCoord + 1;
        }
        if (direction == ForgeDirection.EAST) {
            cX = xCoord - 1;
            cZ = zCoord;
        }
        if (direction == ForgeDirection.SOUTH) {
            cX = xCoord;
            cZ = zCoord - 1;
        }
        if (direction == ForgeDirection.WEST) {
            cX = xCoord + 1;
            cZ = zCoord;
        }

        return new int[] {cX, yCoord, cZ};
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    public ReactorType getReactorType() {
        return this.reactorType;
    }

    @Override
    public float getThermalUnits() {
        return thermalUnits;
    }
    @Override
    public void setThermalUnits(float units) {
        this.thermalUnits = units;
    }
    @Override
    public int getWasteHeat() {
        return wasteHeatUnits;
    }
    @Override
    public void setWasteHeat(int units) {
        this.wasteHeatUnits = units;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

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

        if (multiblockPartNumber != -1 && isMaster) {
            this.coolantInput.readFromNBT(tag.getCompoundTag(Names.NBT.TANK + "CoolantInput"));
            this.coolantOutput.readFromNBT(tag.getCompoundTag(Names.NBT.TANK + "CoolantOutput"));

            this.thermalUnits = tag.getFloat(Names.NBT.THERMAL_UNITS);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        if (multiblockPartNumber != -1 && isMaster) {
            tag.setTag(Names.NBT.TANK + "CoolantInput", coolantInput.writeToNBT(new NBTTagCompound()));
            tag.setTag(Names.NBT.TANK + "CoolantOutput", coolantOutput.writeToNBT(new NBTTagCompound()));

            tag.setFloat(Names.NBT.THERMAL_UNITS, thermalUnits);
        }
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        if (this.isMaster())
            return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord - 2, zCoord - 1, xCoord + 2, yCoord + 3, zCoord + 2);
        return super.getRenderBoundingBox();
    }
}
