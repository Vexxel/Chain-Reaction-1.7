package chainreaction.api.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 4/15/2015.
 *
 * This interface is to be used with items that emit radiation and thermal energy.
 */
public interface IRadioactiveMaterial {

    /**
     * @param stack the ItemStack to check
     * @return if this ItemStack emits any form of radiation
     */
    boolean emitsRadiation(ItemStack stack);

    /**
     * @param stack the ItemStack to check
     * @return the arbitrary level of radioactivity that this ItemStack emits--0.1 for low level radiation, 10.0 for instant death beam
     */
    float getRadioactivity(ItemStack stack);

    /**
     * Sets this ItemStack to have a certain amount of radioactivity--this slowly reduces over time, and faster if put into certain chambers
     * @param stack the ItemStack to set
     * @param radioactivity the float to set--0.1-1.0 for low level radiation, 100.0 for instant mega death laser
     */
    void setRadioactivity(ItemStack stack, float radioactivity);

    /**
     * Gets how many blocks in all directions that this ItemStack's radiation will travel through air
     * @param stack the ItemStack to check
     * @return distance in block radius
     */
    double getEmissionDistance(ItemStack stack);

    /**
     * Called upon to do radiation things, most often inside of certain chambers--you can call this using onUpdate in the {@link Item} class
     * if you so choose
     * @param stack the ItemStack that will get pulsed
     */
    void pulseMaterial(ItemStack stack);

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
}
