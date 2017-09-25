package chainreaction.api.tile;

import cofh.api.energy.EnergyStorage;

public class MachineUpgrader {

    public static final int CAPACITY_MODS[] = { 100000, 400000, 1000000};
    public static final float EFFICIENCY_MODS[] = { 25.0F, 40.0F, 70.0F};

    private static void upgradeCapacity(IUpgradeableTile tile, EnergyStorage storage, int... levels) {

        int capacityTotal = 0;

        for (int level : levels) {
            capacityTotal += level;
        }

        storage.setCapacity((storage.getMaxEnergyStored() + capacityTotal));
    }

    private static void upgradeEfficiency(IUpgradeableTile tile, int... level) {

    }

    private static void upgradeRTG(IUpgradeableTile tile, int... level) {

    }

    private static void upgradeOverclocker(IUpgradeableTile tile, int... level) {

    }
}
