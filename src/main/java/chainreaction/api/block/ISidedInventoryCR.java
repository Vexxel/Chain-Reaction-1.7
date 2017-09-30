package chainreaction.api.block;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 5/21/2016.
 */
public interface ISidedInventoryCR extends ISidedInventory {
    ItemStack[] getInventory();

    void clearInventory();
}
