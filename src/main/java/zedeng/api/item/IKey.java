package zedeng.api.item;

import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 2/23/2015.
 */
public interface IKey {

    boolean hasCode(ItemStack stack);

    String getCode(ItemStack stack);
}
