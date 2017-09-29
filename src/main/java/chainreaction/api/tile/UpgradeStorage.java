package chainreaction.api.tile;

import chainreaction.api.item.IMachineUpgrade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class UpgradeStorage implements IUpgradeStorage {

    private static final int CAPACITY_MODS[] = { 100000, 400000, 1000000 };
    private static final float COST_MODS[] = { 0.20F, 0.35F, 0.50F };
    private static final int RTG_MODS[] = { 32, 64, 128 };
    private static final float SPEED_MODS[][] = {
            //Level 1, 50% speed increase, 50% power increase (~100%)
            { 0.50F, 0.50F },
            //Level 2, 100% speed increase, 133% power increase (~75%)
            { 1.00F, 1.33F },
            //Level 3, 200% speed increase, 300% power increase (~66%)
            { 2.00F, 3.00F }
    };


    private int capacityMod;
    private float costMod;
    private float speedMod;
    private int rtgMod;

    public UpgradeStorage() {
        this(0, 0F, 0F, 0);
    }

    public UpgradeStorage(int capacity, float efficiency, float overclocker, int rtg) {
        this.capacityMod = capacity;
        this.costMod = efficiency;
        this.speedMod = overclocker;
        this.rtgMod = rtg;
    }

    public UpgradeStorage readFromNBT(NBTTagCompound nbt) {

        this.capacityMod = nbt.getInteger("CapacityModifier");
        this.costMod = nbt.getFloat("CostModifier");
        this.speedMod = nbt.getFloat("SpeedModifier");
        this.rtgMod = nbt.getInteger("RTGModifier");

        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {

        nbt.setInteger("CapacityModifier", capacityMod);
        nbt.setFloat("CostModifier", costMod);
        nbt.setFloat("SpeedModifier", speedMod);
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
    public float getCostMod() {
        return costMod;
    }

    @Override
    public void setCostMod(float amount) {
        if (amount < 0F) costMod = 0F;

        costMod = amount;
    }

    @Override
    public float getSpeedMod() {
        return speedMod;
    }

    @Override
    public void setSpeedMod(float amount) {
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
        float finalCost = 1F;
        float speed = 1F;
        float speedCost = 1F;
        int rtg = 0;

        for (ItemStack upgradeStack : upgrades) {
            if (upgradeStack != null) {
                IMachineUpgrade upgrade = (IMachineUpgrade)upgradeStack.getItem();
                switch (upgrade.getUpgradeType(upgradeStack)) {
                    case CAPACITY: {
                        cap += CAPACITY_MODS[upgrade.getLevel(upgradeStack) - 1];
                    } break;
                    case EFFICIENCY: {
                        if (finalCost >= 1F)
                            finalCost -= COST_MODS[upgrade.getLevel(upgradeStack) - 1];
                    } break;
                    case OVERCLOCKER: {
                        speed += SPEED_MODS[upgrade.getLevel(upgradeStack) - 1][0];
                        speedCost += SPEED_MODS[upgrade.getLevel(upgradeStack) - 1][1];
                    } break;
                    case RTG: {
                        rtg += RTG_MODS[upgrade.getLevel(upgradeStack) - 1];
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
