package chainreaction.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 4/1/2015.
 */
public interface IScanner {

    boolean canScan(EntityPlayer player, int x, int y, int z);

    void scan(EntityPlayer player, int x, int y, int z);

    byte getMode(ItemStack stack);

    void setMode(ItemStack stack, byte mode);
}
