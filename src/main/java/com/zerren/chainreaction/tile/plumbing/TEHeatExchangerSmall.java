package com.zerren.chainreaction.tile.plumbing;

import chainreaction.api.heat.IHeatHandler;
import chainreaction.api.recipe.HeatingFluid;
import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.tile.TEHeatHandlerBase;
import com.zerren.chainreaction.utility.TransferUtility;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by Zerren on 4/13/2015.
 */
public class TEHeatExchangerSmall extends TEHeatHandlerBase implements IFluidHandler, IHeatHandler {

    private final int tankCapacity = 1000;

    public final FluidTank inputTank = new FluidTank(tankCapacity);
    public final FluidTank outputTank = new FluidTank(tankCapacity);

    private short updateCounter;


    public TEHeatExchangerSmall() {
        super(0, TransferUtility.getAllSideDirections());
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (inputTank.getFluidAmount() >= 10)
                processCoolant();

            if (outputTank.getFluidAmount() > 0)
                pushFluids(1000);

            transferHeatToOneSide(getOrientation().getOpposite());

            updateCounter++;
            if (updateCounter >= 60) {

            }
        }
    }

    private void processCoolant() {
        if (inputTank.getFluid() == null) return;
        int amount = inputTank.getFluid().amount;
        Fluid output = HeatingFluid.getOutput(inputTank.getFluid().getFluid());
        //mb/t the exchanger processes
        int amountToProcess = amount > 50 ? 50 : amount;

        //62 heat per 100mb
        int heatFromAmountProcessed = (int)((double)amountToProcess / 1000 * HeatingFluid.getHeat(inputTank.getFluid().getFluid()));

        //62 TU/t at peak performance (for hot coolant)--exchanger can take up to 100mb/t maximum

        if (amount < amountToProcess) return;

        if (amount <= 0) return;
        if (output == null) {
            ChainReaction.log.warn("Input heating fluid has no output in exchanger at " + getStringLocale() + "!");
            ChainReaction.log.warn("How did that fluid get there anyway? I'll purge that tank.");
            inputTank.setFluid(null);
            return;
        }

        int outputSpace = outputTank.getCapacity() - outputTank.getFluidAmount();

        if ((outputSpace - amountToProcess) < 0) return;

        outputTank.fill(new FluidStack(output, amountToProcess), true);
        inputTank.drain(amountToProcess, true);

        heatStorage.modifyHeatStored(heatFromAmountProcessed);
    }

    private void pushFluids(int flowCap) {
        ForgeDirection dir = spinLeft(getOrientation(), false);
        TileEntity receiver = TransferUtility.getTileAdjacentFromDirection(this, dir);

        FluidStack fluid = outputTank.drain(flowCap, false);
        if (fluid != null && fluid.amount > 0 && receiver instanceof IFluidHandler) {
            int used = ((IFluidHandler) receiver).fill(spinRight(dir, false), fluid, true);

            if (used > 0) {
                //amount -= used;
                outputTank.drain(used, true);
            }
        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        int used = 0;
        FluidStack filling = resource.copy();

        if (canFill(from, resource.getFluid())) {
            used += inputTank.fill(filling, doFill);
            filling.amount -= used;
        }
        return used;
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
        //if the fluid is a valid heating fluid and the filling direction is from the right
        return HeatingFluid.validHeatingFluid(fluid) && from == spinRight(getOrientation(), false);
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        //from the left (coolant output)
        return from == spinLeft(getOrientation(), false);
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        if (from == spinRight(getOrientation(), false)) return new FluidTankInfo[]{this.inputTank.getInfo()};
        else if (from == spinLeft(getOrientation(), false)) return new FluidTankInfo[]{this.outputTank.getInfo()};

        return new FluidTankInfo[0];
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        readNBTFluidTank(tag, inputTank, "Input");
        readNBTFluidTank(tag, outputTank, "Output");
        //this.inputTank.readFromNBT(tag.getCompoundTag(Names.NBT.TANK + "Input"));
        //this.outputTank.readFromNBT(tag.getCompoundTag(Names.NBT.TANK + "Output"));
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        writeNBTFluidTank(tag, inputTank, "Input");
        writeNBTFluidTank(tag, outputTank, "Output");
        //tag.setTag(Names.NBT.TANK + "Input", inputTank.writeToNBT(new NBTTagCompound()));
        //tag.setTag(Names.NBT.TANK + "Output", outputTank.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public boolean canConnectHeat(ForgeDirection from) {
        return from == getOrientation().getOpposite();
    }

    /*@Override
    public Packet getDescriptionPacket() {
        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileGasTank(this, tank.getFluidAmount()));
    }*/
}
