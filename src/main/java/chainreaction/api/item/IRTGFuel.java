package chainreaction.api.item;

import chainreaction.api.reactor.ReactorType;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 4/15/2015.
 *
 * This interface is to be used with items that should act as a heat source in an RTG.
 */
public interface IRTGFuel extends IRadioactiveMaterial {

    /**
     * If this ItemStack is fissionable inside of a reactor
     * @param stack The ItemStack to check
     * @return if this stack is a valid fuel
     */
    boolean isRTGFuel(ItemStack stack);

    /**
     * Sets the fuel remaining in this ItemStack. Valid values are between 0.0 and 1.0
     * @param stack The ItemStack to set the remaining fuel with
     * @param fuel the fuel amount (from 0.0 to 1.0) to set
     */
    void setFuelRemaining(ItemStack stack, float fuel);

    /**
     * Returns a float value from 0.0 to 1.0 that determines the amount of fuel remaining in this ItemStack
     * @param stack The ItemStack to check
     * @return the remaining 'burnable' fuel in the stack
     */
    float getFuelRemaining(ItemStack stack);

    /**
     * Returns the FuelType that this ItemStack is associated with--a FuelType of FISSION_ROD for example can only go into a reactor that accepts that type
     * @param stack The ItemStack to associate the FuelType
     * @return the FuelType that this ItemStack is
     */
    ReactorType.FuelType getFuelType(ItemStack stack);

    /**
     * Returns an int value in rf/t that determines the base power output of this fuel
     * @param stack The ItemStack to check
     * @return the base power output in rf/t
     */
    int getBasePowerOutput(ItemStack stack);

}
