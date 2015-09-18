package chainreaction.api.item;

import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 9/17/2015.
 */
public interface IEnergyItem {

    public double getEnergy(ItemStack stack);

    public void setEnergy(ItemStack stack, double energy);

    public double getMaxEnergy(ItemStack stack);

    public boolean canSendEnergy(ItemStack stack);
}
