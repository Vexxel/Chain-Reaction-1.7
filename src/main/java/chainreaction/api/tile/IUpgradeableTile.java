package chainreaction.api.tile;

import chainreaction.api.item.IMachineUpgrade;
import com.zerren.chainreaction.tile.container.ContainerCR;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;

public interface IUpgradeableTile {

    IMachineUpgrade.MachineUpgrade[] getValidUpgrades();

    ItemStack[] getUpgradesInInventory();

    void installUpgrade();

    boolean areUpgradesActive();
}
