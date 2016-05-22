package chainreaction.api.block;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 5/21/2016.
 */
public interface IInventoryCR extends IInventory {
    ItemStack[] getInventory();

    void clearInventory();
}
