package chainreaction.api.item;

import chainreaction.api.reactor.ReactorType;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 4/15/2015.
 *
 * This interface is to be used with items that should act as fuel in a respective reactor.
 */
public interface ISolidReactorFuel {
    /**
     * Returns the float value of this fuel item's temperature. NBT string is checked as "temperature". Reactor heat is calculated by adding fuel item heat to the
     * reactor core fluid, or directly into the reactor vessel if there is no fluid.
     * @param stack The ItemStack to check
     * @return temperature
     */
    float getTemperature(ItemStack stack);

    /**
     * Sets the float value of this fuel item's temperature. NBT string is checked as "temperature"
     * @param stack The ItemStack to set the temperature of
     * @param temp The temperature to set
     */
    void setTemperature(ItemStack stack, float temp);

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

}
