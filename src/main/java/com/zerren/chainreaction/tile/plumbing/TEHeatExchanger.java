package com.zerren.chainreaction.tile.plumbing;

import com.zerren.chainreaction.ChainReaction;
import chainreaction.api.recipe.HeatingFluid;
import chainreaction.api.recipe.WorkingFluid;
import chainreaction.api.block.IThermalTile;
import com.zerren.chainreaction.tile.TEMultiBlockBase;
import com.zerren.chainreaction.handler.PacketHandler;
import com.zerren.chainreaction.handler.network.client.tile.MessageTileMultiblock;
import com.zerren.chainreaction.reference.MultiblockCost;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.reference.Reference;
import com.zerren.chainreaction.utility.NetworkUtility;
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
 * Created by Zerren on 3/7/2015. There be hot fluids here!
 */
public class TEHeatExchanger extends TEMultiBlockBase implements IFluidHandler, IThermalTile {

    private final int tankCapacity = 2000;

    public FluidTank coolantInletTank = new FluidTank(this.tankCapacity);
    public FluidTank waterTank = new FluidTank(this.tankCapacity);
    public FluidTank steamTank = new FluidTank(this.tankCapacity * 32);
    public FluidTank coolantOutputTank = new FluidTank(this.tankCapacity);

    /**
     * The byte, from left to right (when facing the complete multiblock), of this block's ID in the multiblock
     */
    public byte slaveLocation;

    /**
     * Arbitrary thermal storage used to heat a fluid--10 TU can turn 1mb water to 160mb steam (RF:Steam :: 2:1), 1TU = 32 RF.
     * 5mb of hot coolant = 1TU = 200TU per 1000mb--Exchanger at peak performance consuming hot coolant produces 20TU/t = 640RF/t = 320Steam/t.
     * Each bucket of hot coolant can make 6400RF
     */
    private int thermalUnits;

    /**
     * Arbitrary thermal waste heat storage that is the 'leftover' of a energy conversion--this tile entity does not have a loss in efficiency, because it deals in
     * entirely thermal units.
     */
    private int wasteHeatUnits;

    private short updateCounter;

    public TEHeatExchanger() {
        super();
        slaveLocation = -1;
        thermalUnits = 0;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (!worldObj.isRemote && isMaster()) {
            if (coolantInletTank.getFluidAmount() >= 100)
                processCoolant();
            if (waterTank.getFluidAmount() > 0)
                heatFluid();
            if (steamTank.getFluidAmount() > 0) {
                float amount = steamTank.getFluidAmount();
                float toPush = 500 + (amount * (amount / steamTank.getCapacity()));

                pushFluids(steamTank, (int)toPush);
            }

            if (coolantOutputTank.getFluidAmount() > 0)
                pushFluids(coolantOutputTank, 1600);

            updateCounter++;
            if (updateCounter >= 60) {
                int toLose = (int)Math.sqrt(thermalUnits) / 20;
                if (thermalUnits > toLose) thermalUnits -= toLose;

                updateCounter = 0;
            }
        }
    }

    private void processCoolant() {
        if (coolantInletTank.getFluid() == null) return;
        int amount = coolantInletTank.getFluid().amount;
        Fluid output = HeatingFluid.getOutput(coolantInletTank.getFluid().getFluid());
        int heat = HeatingFluid.getHeat(coolantInletTank.getFluid().getFluid());

        //20 TU/t at peak performance (for hot coolant)--plumbing can take up to 100mb/t maximum. Any faster and the fluids wouldn't have a chance to liberate their energy!
        int amountToDrain = 100;

        if (amount < amountToDrain) return;

        if (amount <= 0) return;
        if (output == null) {
            ChainReaction.log.warn("Input heating fluid has no output in exchanger at " + getStringLocale() + "!");
            ChainReaction.log.warn("How did that fluid get there anyway? I'll purge that tank.");
            coolantInletTank.setFluid(null);
            return;
        }

        int outputSpace = coolantOutputTank.getCapacity() - coolantOutputTank.getFluidAmount();

        if ((outputSpace - amountToDrain) < 0) return;

        coolantOutputTank.fill(new FluidStack(output, amountToDrain), true);
        coolantInletTank.drain(amountToDrain, true);

        if ((thermalUnits += heat) > 10000)
            thermalUnits = 10000;
    }

    private void heatFluid() {
        if (waterTank.getFluid() == null || waterTank.getFluidAmount() <= 0) return;
        int inputAmount = WorkingFluid.getInputRequiredAmount(waterTank.getFluid().getFluid());
        FluidStack output = WorkingFluid.getOutput(waterTank.getFluid().getFluid());
        if (inputAmount <= 0) return;

        //max the efficiency at 10--too much and the tank wouldn't be large enough for some fluids
        int speed = Math.min((int)(0.5F + ((thermalUnits + 1) * 0.001F)), 10);
        if (speed <= 0) return;

        if (output == null) {
            ChainReaction.log.warn("Input working fluid has no output in exchanger at " + getStringLocale() + "!");
            ChainReaction.log.warn("How did that fluid get there anyway? I'll purge that tank.");
            waterTank.setFluid(null);
            return;
        }

        int outputSpace = steamTank.getCapacity() - steamTank.getFluidAmount();
        if (outputSpace < output.amount * speed || thermalUnits < (speed * 10)) return;

        if (waterTank.getFluidAmount() < speed * inputAmount) return;

        thermalUnits -= (speed * 10);
        steamTank.fill(new FluidStack(output.getFluid(), output.amount * speed), true);
        waterTank.drain(speed * inputAmount, true);
    }

    private void pushFluids(IFluidTank tank, int flowCap) {
        ForgeDirection dir = getOrientation();
        TileEntity receiver = null;
        //int amount = flowCap;

        //steam tank
        if (tank == steamTank) {
            switch (dir) {
                case NORTH: receiver = worldObj.getTileEntity(xCoord - 1, yCoord + 1, zCoord); break;
                case EAST: receiver = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord - 1); break;
                case SOUTH: receiver = worldObj.getTileEntity(xCoord + 1, yCoord + 1, zCoord); break;
                case WEST: receiver = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord + 1); break;
            }
        }
        //coolant output
        else if (tank == coolantOutputTank) {
            switch (dir) {
                case NORTH: receiver = worldObj.getTileEntity(xCoord - 3, yCoord, zCoord); break;
                case EAST: receiver = worldObj.getTileEntity(xCoord, yCoord, zCoord - 3); break;
                case SOUTH: receiver = worldObj.getTileEntity(xCoord + 3, yCoord, zCoord); break;
                case WEST: receiver = worldObj.getTileEntity(xCoord, yCoord, zCoord + 3); break;
            }
        }
        else return;

        FluidStack fluid = tank.drain(flowCap, false);
        if (fluid != null && fluid.amount > 0 && receiver instanceof IFluidHandler) {
            int used;
            if (tank == steamTank) {
                used = ((IFluidHandler) receiver).fill(ForgeDirection.DOWN, fluid, true);
            }
            else {
                used = ((IFluidHandler) receiver).fill(spinRight(dir, false), fluid, true);
            }
            if (used > 0) {
                //amount -= used;
                tank.drain(used, true);
            }
        }
    }

    @Override
    public int getThermalUnits() {
        return thermalUnits;
    }
    @Override
    public void setThermalUnits(int units) {
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
    public boolean canUpdate() {
        return true;
    }

    public void initiateController(UUID id, EntityPlayer player) {
        if (controllerID == null) {
            setControllerUUID(id);
        }
        if (ownerUUID == null) {
            setOwner(player);
        }

        if (!this.isMaster && !this.hasValidMaster())
            if (checkMultiblock(player))  {
                MultiblockCost.takeMaterials(player, MultiblockCost.LIQUID_HEAT_EXCHANGER, true);
                ChainReaction.proxy.playSound(this.worldObj, xCoord, yCoord, zCoord, Reference.Sounds.LOCK_SUCCESS, 1.0F, 1.0F);
            }
    }

    @Override
    public void invalidateMultiblock() {
        TEMultiBlockBase commander = getCommandingController();
        if (commander == null) commander = this;

        commander.setAsMaster(false);
        ForgeDirection dir = commander.getOrientation();
        if (dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH) {
            for (int i = 0; i < 5; i++) {
                TEHeatExchanger tile = (TEHeatExchanger)worldObj.getTileEntity(commander.xCoord - 2 + i, commander.yCoord, commander.zCoord);
                if (tile != null) {
                    tile.removeController();
                    tile.setSlaveLocation((byte)-1);
                    PacketHandler.INSTANCE.sendToAllAround(new MessageTileMultiblock(tile, false, false), NetworkUtility.makeTargetPoint(this));
                }
            }
        }
        else {
            for (int i = 0; i < 5; i++) {
                TEHeatExchanger tile = (TEHeatExchanger)worldObj.getTileEntity(commander.xCoord, commander.yCoord, commander.zCoord - 2 + i);
                if (tile != null) {
                    tile.removeController();
                    tile.setSlaveLocation((byte)-1);
                    PacketHandler.INSTANCE.sendToAllAround(new MessageTileMultiblock(tile, false, false), NetworkUtility.makeTargetPoint(this));
                }
            }
        }
        MultiblockCost.refundMaterials(MultiblockCost.LIQUID_HEAT_EXCHANGER, worldObj, xCoord, yCoord, zCoord);
    }

    private boolean checkMultiblock(EntityPlayer player) {
        ForgeDirection direction = getOrientation();
        int counter = 0;

        //north & south +-x
        if (direction == ForgeDirection.NORTH || direction == ForgeDirection.SOUTH) {
            for (int i = 0; i < 5; i++) {
                TileEntity tile = worldObj.getTileEntity(xCoord - 2 + i, yCoord, zCoord);
                //tile exists and is a heat plumbing
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
                //tile exists and is a heat plumbing
                if (tile != null && tile instanceof TEHeatExchanger) {
                    //tile has no master
                    if (!((TEHeatExchanger) tile).hasValidMaster() || ((TEHeatExchanger) tile).getMasterUUID().compareTo(this.getControllerUUID()) == 0) {
                        counter++;
                    }
                }
            }
        }

        if (counter == 5) {
            if (!MultiblockCost.takeMaterials(player, MultiblockCost.LIQUID_HEAT_EXCHANGER, false)) return false;

            if (direction == ForgeDirection.NORTH || direction == ForgeDirection.SOUTH) {
                for (int i = 0; i < 5; i++) {
                    TEHeatExchanger tile = (TEHeatExchanger)worldObj.getTileEntity(xCoord - 2 + i, yCoord, zCoord);
                    tile.setController(controllerID, xCoord, yCoord, zCoord);
                    if (direction == ForgeDirection.SOUTH)
                        tile.setSlaveLocation((byte)i);
                    else
                        tile.setSlaveLocation((byte) (-i + 4));
                    tile.getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
                    tile.setOrientation(this.getOrientation());

                    setAsMaster(i == 2);
                    PacketHandler.INSTANCE.sendToAllAround(new MessageTileMultiblock(tile, i == 2, true), NetworkUtility.makeTargetPoint(this));
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
                    tile.setOrientation(this.getOrientation());

                    setAsMaster(i == 2);
                    PacketHandler.INSTANCE.sendToAllAround(new MessageTileMultiblock(tile, i == 2, true), NetworkUtility.makeTargetPoint(this));
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
        TEHeatExchanger master = (TEHeatExchanger)getCommandingController();

        if (slaveLocation != -1 && !this.isMaster){
            if (slaveLocation == 0) {
                if (canFill(from, resource.getFluid())) {
                    used += master.coolantInletTank.fill(filling, doFill);
                    filling.amount -= used;
                }
            }
            if (slaveLocation == 1) {
                if (canFill(from, resource.getFluid())) {
                    used += master.waterTank.fill(filling, doFill);
                    filling.amount -= used;
                }
            }
        }
        return used;
    }

    //Fluid output from tile
    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        /*TEHeatExchanger master = (TEHeatExchanger)getCommandingController();

        if (slaveLocation != -1 && !this.isMaster) {
            //steam tank
            if (slaveLocation == 3) {
                if (canDrain(from, resource.getFluid())) {
                    return master.steamTank.drain(resource.amount, doDrain);
                }
            }
            if (slaveLocation == 4) {
                if (canDrain(from, resource.getFluid())) {
                    return master.coolantOutputTank.drain(resource.amount, doDrain);
                }
            }
        }*/

        return null;
    }

    //Fluid output from tile, not specific
    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        /*TEHeatExchanger master = (TEHeatExchanger)getCommandingController();

        if (slaveLocation != -1 && !this.isMaster) {
            //steam tank
            if (slaveLocation == 3 && from == ForgeDirection.UP) {
                return master.steamTank.drain(maxDrain, doDrain);
            }
            if (slaveLocation == 4 && from == spinRight(getOrientation(), false)) {
                return master.coolantOutputTank.drain(maxDrain, doDrain);
            }
        }*/

        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        TEHeatExchanger master = (TEHeatExchanger)getCommandingController();
        ForgeDirection dir = master.getOrientation();

        //slave 0 (coolant inlet) and is a valid coolant fluid from the left side when facing the plumbing
        if (slaveLocation == 0 && HeatingFluid.validHeatingFluid(fluid)) {
            switch (dir) {
                case NORTH: return from == ForgeDirection.EAST;
                case EAST: return from == ForgeDirection.SOUTH;
                case SOUTH: return from == ForgeDirection.WEST;
                case WEST: return from == ForgeDirection.NORTH;
            }
        }

        //From above, valid working fluid, and slave 1 (water inlet)
        return (from == ForgeDirection.UP) && slaveLocation == 1 && WorkingFluid.validWorkingFluid(fluid);
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        /*TEHeatExchanger master = (TEHeatExchanger)getCommandingController();
        ForgeDirection dir = master.getOrientation();

        //coolant outlet
        if (slaveLocation == 4) {

            if (master.coolantOutputTank.getFluid() != null && master.coolantOutputTank.getFluidAmount() > 0)
                switch (dir) {
                    case NORTH: return from == ForgeDirection.WEST;
                    case EAST: return from == ForgeDirection.NORTH;
                    case SOUTH: return from == ForgeDirection.EAST;
                    case WEST: return from == ForgeDirection.SOUTH;
                }
        }

        //steam outlet
        return slaveLocation == 3 && from == ForgeDirection.UP && master.steamTank.getFluid() != null && master.steamTank.getFluidAmount() > 0;*/

        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        FluidTankInfo[] info = new FluidTankInfo[] {};
        TEHeatExchanger commander = (TEHeatExchanger)this.getCommandingController();

        if (commander == null) return info;

        if (slaveLocation == 0 && from == spinLeft(getOrientation(), false)) {
            info = new FluidTankInfo[] {commander.coolantInletTank.getInfo()};
        }
        else if (slaveLocation == 1 && from == ForgeDirection.UP) {
            info = new FluidTankInfo[] {commander.waterTank.getInfo()};
        }
        else if (slaveLocation == 3 && from == ForgeDirection.UP) {
            info = new FluidTankInfo[] {commander.steamTank.getInfo()};
        }
        else if (slaveLocation == 4 && from == spinRight(getOrientation(), false)) {
            info = new FluidTankInfo[] {commander.coolantOutputTank.getInfo()};
        }

        return info;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.slaveLocation = tag.getByte(Names.NBT.SLAVE_LOCATION);

        if (slaveLocation != -1 && isMaster) {
            this.coolantInletTank.readFromNBT(tag.getCompoundTag(Names.NBT.TANK + "CoolantInput"));
            this.waterTank.readFromNBT(tag.getCompoundTag(Names.NBT.TANK + "WorkingInput"));
            this.steamTank.readFromNBT(tag.getCompoundTag(Names.NBT.TANK + "WorkingOutput"));
            this.coolantOutputTank.readFromNBT(tag.getCompoundTag(Names.NBT.TANK + "CoolantOutput"));

            this.thermalUnits = tag.getInteger(Names.NBT.THERMAL_UNITS);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setByte(Names.NBT.SLAVE_LOCATION, slaveLocation);

        if (slaveLocation != -1 && isMaster) {
            tag.setTag(Names.NBT.TANK + "CoolantInput", coolantInletTank.writeToNBT(new NBTTagCompound()));
            tag.setTag(Names.NBT.TANK + "WorkingInput", waterTank.writeToNBT(new NBTTagCompound()));
            tag.setTag(Names.NBT.TANK + "WorkingOutput", steamTank.writeToNBT(new NBTTagCompound()));
            tag.setTag(Names.NBT.TANK + "CoolantOutput", coolantOutputTank.writeToNBT(new NBTTagCompound()));

            tag.setInteger(Names.NBT.THERMAL_UNITS, thermalUnits);
        }

    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        if (this.isMaster())
            return AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord, zCoord - 2, xCoord + 3, yCoord + 1, zCoord + 3);
        return super.getRenderBoundingBox();
    }
}
