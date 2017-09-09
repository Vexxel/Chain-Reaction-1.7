package chainreaction.api.item;

import chainreaction.api.reactor.ReactorType;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 4/15/2015.
 *
 * This interface is to be used with items that should act as fuel in a respective reactor.
 */
public interface ISolidReactorFuel extends IRadioactiveMaterial {

    /**
     * If this ItemStack is fissionable inside of a reactor
     * @param stack The ItemStack to check
     * @return if this stack is a valid fuel
     */
    boolean isReactorFuel(ItemStack stack);

    /**
     * Method that the reactor will call on this fuel for every neutron that bombards this item to do things such as damage the fuel, add temperature, etc.
     * Add extra code here if you want some special effects. (Not REALLY every neutron, but for simplicities sake, each neutron flux 'event').
     * This method is called roughly every 5 seconds if the reactor is active (for server friendliness)
     * @param stack The ItemStack to pulse
     * @param neutronPulse How many times this will be pulsed in this event--e.g. a fuel pebble surrounded by 4 more will be pulsed 5 times
     */
    void pulseFuel(ItemStack stack, int neutronPulse);

    /**
     * Sets the fuel remaining in this ItemStack. For reactor fuel, this is in pulses.
     * @param stack The ItemStack to set the remaining fuel with
     * @param fuel the fuel amount to set
     */
    void setFuelRemaining(ItemStack stack, int fuel);

    /**
     * Returns a float value from 0.0 to 1.0 that determines the amount of fuel remaining in this ItemStack
     * @param stack The ItemStack to check
     * @return the remaining 'burnable' fuel in the stack
     */
    int getFuelRemaining(ItemStack stack);

    /**
     * Returns the FuelType that this ItemStack is associated with--a FuelType of FISSION_ROD for example can only go into a reactor that accepts that type
     * @param stack The ItemStack to associate the FuelType
     * @return the FuelType that this ItemStack is
     */
    ReactorType.FuelType getFuelType(ItemStack stack);

}
