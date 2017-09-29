package chainreaction.api.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 4/15/2015.
 *
 * This interface is to be used with items that emit radiation and thermal heat.
 */
public interface IMachineUpgrade {

    /**
     * @param stack the ItemStack to check
     * @return what level of upgrade this item is
     */
    int getLevel(ItemStack stack);

    /**
     * @param stack the ItemStack to check
     * @return what type of upgrade this stack is
     */
    MachineUpgrade getUpgradeType(ItemStack stack);

    /**
     * @param stack the ItemStack to check
     * @return is this itemstack a valid machine upgrade
     */
    boolean isUpgrade(ItemStack stack);

    enum MachineUpgrade {
        CAPACITY(3),
        EFFICIENCY(1),
        RTG(3),
        OVERCLOCKER(3),
        UNKNOWN(0);

        private final int stacksTo;
        private final boolean canStack;

        MachineUpgrade(int stacksTo) {
            this.stacksTo = stacksTo;
            this.canStack = stacksTo > 1;
        }

        public int getStackLimit(MachineUpgrade upgrade) {
            return stacksTo;
        }

        public boolean canStack(MachineUpgrade upgrade) {
            return canStack;
        }
    }
}

