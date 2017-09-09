package chainreaction.api.heat;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Zerren on 9/8/2017.
 */
public class HeatStorage implements IHeatStorage {

    protected int heat;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public HeatStorage(int capacity) {

        this(capacity, capacity, capacity);
    }

    public HeatStorage(int capacity, int maxTransfer) {

        this(capacity, maxTransfer, maxTransfer);
    }

    public HeatStorage(int capacity, int maxReceive, int maxExtract) {

        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    public HeatStorage readFromNBT(NBTTagCompound nbt) {

        this.heat = nbt.getInteger("Heat");

        if (heat > capacity) {
            heat = capacity;
        }
        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {

        if (heat < 0) {
            heat = 0;
        }
        nbt.setInteger("Heat", heat);
        return nbt;
    }

    public void setCapacity(int capacity) {

        this.capacity = capacity;

        if (heat > capacity) {
            heat = capacity;
        }
    }

    public void setMaxTransfer(int maxTransfer) {

        setMaxReceive(maxTransfer);
        setMaxExtract(maxTransfer);
    }

    public void setMaxReceive(int maxReceive) {

        this.maxReceive = maxReceive;
    }

    public void setMaxExtract(int maxExtract) {

        this.maxExtract = maxExtract;
    }

    public int getMaxReceive() {

        return maxReceive;
    }

    public int getMaxExtract() {

        return maxExtract;
    }

    /**
     * This function is included to allow for server -&gt; client sync. Do not call this externally to the containing Tile Entity, as not all IEnergyHandlers
     * are guaranteed to have it.
     *
     * @param energy
     */
    public void setHeatStored(int energy) {

        this.heat = energy;

        if (this.heat > capacity) {
            this.heat = capacity;
        } else if (this.heat < 0) {
            this.heat = 0;
        }
    }

    /**
     * This function is included to allow the containing tile to directly and efficiently modify the heat contained in the HeatStorage. Do not rely on this
     * externally, as not all IEnergyHandlers are guaranteed to have it.
     *
     * @param energy
     */
    public void modifyHeatStored(int energy) {

        this.heat += energy;

        if (this.heat > capacity) {
            this.heat = capacity;
        } else if (this.heat < 0) {
            this.heat = 0;
        }
    }

    /* IHeatStorage */
    @Override
    public int receiveHeat(int maxReceive, boolean simulate) {

        int heatReceived = Math.min(capacity - heat, Math.min(this.maxReceive, maxReceive));

        if (!simulate) {
            heat += heatReceived;
        }
        return heatReceived;
    }

    @Override
    public int extractHeat(int maxExtract, boolean simulate) {

        int heatExtracted = Math.min(heat, Math.min(this.maxExtract, maxExtract));

        if (!simulate) {
            heat -= heatExtracted;
        }
        return heatExtracted;
    }

    @Override
    public int getHeatStored() {

        return heat;
    }

    @Override
    public int getMaxHeatStored() {

        return capacity;
    }

}