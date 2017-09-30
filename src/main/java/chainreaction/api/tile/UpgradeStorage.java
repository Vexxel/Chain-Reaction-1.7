package chainreaction.api.tile;

import chainreaction.api.item.MachineUpgrade;
import chainreaction.api.item.MachineUpgradeRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class UpgradeStorage implements IUpgradeStorage {

    private int capacityMod;
    private double costMod;
    private double speedMod;
    private int rtgMod;

    public UpgradeStorage() {
        this(0, 0F, 0F, 0);
    }

    public UpgradeStorage(int capacity, double efficiency, double overclocker, int rtg) {
        this.capacityMod = capacity;
        this.costMod = efficiency;
        this.speedMod = overclocker;
        this.rtgMod = rtg;
    }

    public UpgradeStorage readFromNBT(NBTTagCompound nbt) {

        this.capacityMod = nbt.getInteger("CapacityModifier");
        this.costMod = nbt.getDouble("CostModifier");
        this.speedMod = nbt.getDouble("SpeedModifier");
        this.rtgMod = nbt.getInteger("RTGModifier");

        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {

        nbt.setInteger("CapacityModifier", capacityMod);
        nbt.setDouble("CostModifier", costMod);
        nbt.setDouble("SpeedModifier", speedMod);
        nbt.setInteger("RTGModifier", rtgMod);

        return nbt;
    }

    @Override
    public int getCapacityMod() {
        return capacityMod;
    }

    @Override
    public void setCapacityMod(int amount) {
        if (amount < 0) capacityMod = 0;

        capacityMod = amount;
    }

    @Override
    public double getCostMod() {
        return costMod;
    }

    @Override
    public void setCostMod(double amount) {
        if (amount < 0F) costMod = 0F;

        costMod = amount;
    }

    @Override
    public double getSpeedMod() {
        return speedMod;
    }

    @Override
    public void setSpeedMod(double amount) {
        if (amount < 0F) speedMod = 0F;

        speedMod = amount;
    }

    @Override
    public int getRTGMod() {
        return rtgMod;
    }

    @Override
    public void setRTGMod(int amount) {
        if (amount < 0) rtgMod = 0;

        rtgMod = amount;
    }

    public void clear() {
        setCapacityMod(0);
        setCostMod(1F);
        setSpeedMod(1F);
        setRTGMod(0);
    }

    public void upgradeTile(ItemStack[] upgrades) {
        int cap = 0;
        double finalCost = 1D;
        double speed = 1D;
        double speedCost = 1D;
        int rtg = 0;

        for (ItemStack upgradeStack : upgrades) {
            if (upgradeStack != null && MachineUpgradeRegistry.isValidUpgrade(upgradeStack)) {

                MachineUpgrade upgradeType = MachineUpgradeRegistry.getUpgradeType(upgradeStack);
                double value1 = MachineUpgradeRegistry.getValue1(upgradeStack);
                double value2 = MachineUpgradeRegistry.getValue2(upgradeStack);

                switch (upgradeType) {
                    case CAPACITY: {
                        cap += (int)value1;
                    } break;
                    case EFFICIENCY: {
                        if (finalCost >= 1F) {
                            finalCost -= value1;
                        }
                    } break;
                    case OVERCLOCKER: {
                        speed += value1;
                        speedCost += value2;
                    } break;
                    case RTG: {
                        rtg += (int)value1;
                    } break;
                }
            }
        }

        setCapacityMod(cap);
        setCostMod(speedCost * finalCost);
        setSpeedMod(speed);
        setRTGMod(rtg);
    }
}
