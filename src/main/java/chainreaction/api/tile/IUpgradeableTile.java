package chainreaction.api.tile;

import chainreaction.api.item.MachineUpgrade;
import net.minecraft.item.ItemStack;

public interface IUpgradeableTile {

    MachineUpgrade[] getValidUpgrades();

    ItemStack[] getUpgradesInInventory();

    void installUpgrades();

    boolean areUpgradesActive();

    UpgradeStorage getUpgradeStorage();

}
