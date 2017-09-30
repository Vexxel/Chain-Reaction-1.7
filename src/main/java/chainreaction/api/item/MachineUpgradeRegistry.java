package chainreaction.api.item;

import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.utility.CoreUtility;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zerren on 4/3/2015.
 */
public class MachineUpgradeRegistry {

    private static List<MachineUpgradeRegistry> upgrades = new ArrayList<MachineUpgradeRegistry>();

    private final ItemStack upgradeStack;
    private final double value1;
    private final double value2;
    private final MachineUpgrade type;

    private MachineUpgradeRegistry(ItemStack input, double value1, double value2, MachineUpgrade type){
        this.upgradeStack = input;
        this.value1 = value1;
        this.value2 = value2;
        this.type = type;
    }

    private ItemStack getInput(){
        return upgradeStack;
    }

    public static double getValue1(ItemStack input){
        for(MachineUpgradeRegistry list : upgrades) {
            if (CoreUtility.softCompareItem(input, list.getInput())) {
                return list.value1;
            }
        }
        return 0F;
    }

    public static double getValue2(ItemStack input){
        for(MachineUpgradeRegistry list : upgrades) {
            if (CoreUtility.softCompareItem(input, list.getInput())) {
                return list.value2;
            }
        }
        return 0F;
    }

    public static MachineUpgrade getUpgradeType(ItemStack input){
        for(MachineUpgradeRegistry list : upgrades) {
            if (CoreUtility.softCompareItem(input, list.getInput())) {
                return list.type;
            }
        }
        return MachineUpgrade.UNKNOWN;
    }

    public static boolean isValidUpgrade(ItemStack input) {
        return getUpgradeType(input) != MachineUpgrade.UNKNOWN;
    }

    /**
     * Adds an ItemStack to the machine upgrade registry along with values for its associated upgrade type.
     * Only the overclocker upgrade type needs both values.
     * @param input Input ItemStack
     * @param value1 first value. Capacity in RF (1-1,000,000,000), Efficiency (0.99F-0.01F), RTG (1-4096), Overclocker (0.01F-100.0F, 0.00F-100.0F)
     * @param value2 second value (overclocker upgrade only for extra energy consumption)
     * @param type what MachineUpgrade type this itemstack is
     */
    public static void addMachineUpgrade(ItemStack input, double value1, double value2, MachineUpgrade type) {
        upgrades.add(new MachineUpgradeRegistry(input, value1, value2, type));
        ChainReaction.log.info("Added " + input.getDisplayName() + " to Machine Upgrades with type " + type + ".");
    }
}
